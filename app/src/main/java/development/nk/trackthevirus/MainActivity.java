package development.nk.trackthevirus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.widget.SearchView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import development.nk.trackthevirus.entity.Asthenis;

public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener,
        AdapterAsthenis.ListItemClickListener {

    private static final String TAG = "MainActivity";
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private TextInputEditText email_tiet, passwrod_tiet;
    private TextInputLayout email_til, password_til;
    private Button signupBtn, loginBtn;
    private LinearLayout main_linear;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private Query query;

    List<Asthenis> asthenisList;
    AdapterAsthenis mAsthenisAdapter;

    private FirestoreRecyclerAdapter<Asthenis, AsthenisViewHolder> adapter;
    private boolean isSignedIn = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer);
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.navigationView);
        toolbar.setTitle("Track the Virus");
        setSupportActionBar(toolbar);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawerOpen, R.string.drawerClose);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        recyclerView = findViewById(R.id.main_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Firestore query to get all Patients and show them at recyclerview (if isSignedIn)
        db = FirebaseFirestore.getInstance();
        query = db.collection("people");


        /**
         * The following runs if i use my custom separate AdapterAsthenis
         * this is slower..
         */
//        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                mAsthenisAdapter = new AdapterAsthenis(task.getResult().toObjects(Asthenis.class), MainActivity.this);
//                recyclerView.setAdapter(mAsthenisAdapter);
//                mAsthenisAdapter.notifyDataSetChanged();
//            }
//        });


        /**
         * The following is used when using the ready-made FirestoreRecyclerAdapter adapter
         * this is fast !!!
         */
        getAsthenis(query);


        /**
         * the following puts all people objects <Asthenis> inside an Arraylist
         *
         * !! only date and geopoints are not added in the Asthenis object !!
         */
//        final ArrayList<Asthenis> finalListOfAsthenis = new ArrayList<>();
//        query.get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
//                                Log.e(TAG, document.getId() + " => " + document.getData());
//                                Asthenis asthenis = document.toObject(Asthenis.class);
//                                finalListOfAsthenis.add(asthenis);
//                            }
//                        } else {
//                            Log.e(TAG, "Error getting documents: ", task.getException());
//                        }
//                    }
//                });

        mAuth = FirebaseAuth.getInstance();

        signupBtn = findViewById(R.id.signup_button);
        loginBtn = findViewById(R.id.login_button);
        email_til = findViewById(R.id.email_til);
        password_til = findViewById(R.id.pass_til);
        email_tiet = findViewById(R.id.email_tiet);
        passwrod_tiet = findViewById(R.id.pass_tiet);
        progressBar = findViewById(R.id.progress_login);
        main_linear = findViewById(R.id.main_linear);

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerNewUser();
            }
        });
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUserAccount();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);


        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (adapter != null) {
            adapter.stopListening();
        }
    }

    private void updateUI(FirebaseUser user) {

        if (user != null) { // is signed in
            isSignedIn=true; invalidateOptionsMenu();

            progressBar.setVisibility(View.GONE);
            loginBtn.setVisibility(View.INVISIBLE);
            signupBtn.setVisibility(View.INVISIBLE);
            email_tiet.getText().clear();
            passwrod_tiet.getText().clear();
            email_til.setVisibility(View.INVISIBLE);
            password_til.setVisibility(View.INVISIBLE);
            main_linear.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
            toolbar.setSubtitle(user.getEmail());

        } else { // is signed out
            isSignedIn =false; invalidateOptionsMenu();

            loginBtn.setVisibility(View.VISIBLE);
            signupBtn.setVisibility(View.VISIBLE);
            email_tiet.getText().clear();
            passwrod_tiet.getText().clear();
            email_til.setVisibility(View.VISIBLE);
            password_til.setVisibility(View.VISIBLE);
            main_linear.setVisibility(View.INVISIBLE);
            toolbar.setSubtitle("");

        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.signup:
                Toast.makeText(MainActivity.this, "Signup selected", Toast.LENGTH_LONG).show();
                break;
            case R.id.login:
                Toast.makeText(MainActivity.this, "Login selected", Toast.LENGTH_LONG).show();
                break;
            case R.id.addPatient:
                Toast.makeText(MainActivity.this, "Add Patient selected", Toast.LENGTH_LONG).show();
                Intent intentP = new Intent(MainActivity.this, NewEditPatientActivity.class);
                intentP.putExtra("editFlag", false);
                startActivityForResult(intentP, 100);
                break;
            case R.id.addContact:
                Toast.makeText(MainActivity.this, "Add Contact selected", Toast.LENGTH_LONG).show();
//                Intent intentC = new Intent(MainActivity.this, NewEditContactActivity.class);
//                intentC.putExtra("editFlag", false);
//                startActivityForResult(intentC, 100);
                break;
            case R.id.about:
                Toast.makeText(MainActivity.this, "About us selected", Toast.LENGTH_LONG).show();
                insertDummyData();
                break;
        }
        return false;
    }


    /**
     * insert Dummy data to Firestore database
     */
    private void insertDummyData() {
        System.out.println(TAG+" insertDummyData");

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        long vats[] = {123456789, 222555687, 958749562, 741236587};
        long dates[] = {1588342399638L, 1588342399638L, 1588342399638L, 1588342399638L};
        String names[] = {"Νεκτάριος", "Κώστας", "Δημήτρης", "Μαρία"};
        String surnames[] = {"Κοντολαιμάκης", "Ανδρουλάκης", "Παπαμιχαήλ", "Σάκαρη"};
        String ages[] = {"45", "18", "25", "22", "32"};
        String towns [] = {"Agios Nikolaos", "Messara", "Ierapetra", "Sitia" };
        String regions [] = {"Λασίθι", "Ηράκλειο", "Λασίθι", "Λασίθι"};
        String mobiles [] = {"6944753855", "1234567899", "699999445", "6945454565"};
        String phone_homes [] = {"2841022252", "2810456785", "2842012378", "2843041138"};
        double latitude [] = {35.207445, 32.276984, 35.010282, 32.276984};
        double longitude [] = {25.698751, 25.718664, 25.745443, 25.698751};


        if (user != null) {

            System.out.println(TAG+" checkIfSignedIn");
            // User is signed in

            // add document to collection 'people'
            for (int i=0; i<4; i++) {
                System.out.println(TAG+" int= "+i);

                Map<String, Object> docData = new HashMap<>();
                docData.put("vat", vats[i]);
                docData.put("name", names[i]);
                docData.put("surname", surnames[i]);
                docData.put("date_long", dates[i]);
                docData.put("date", new Timestamp(new Date(dates[i])));
                docData.put("age", ages[i]);
                docData.put("town", towns[i]);
                docData.put("region", regions[i]);
                docData.put("mobile", mobiles[i]);
                docData.put("phone_home", phone_homes[i]);
                docData.put("geopoint", new GeoPoint(latitude[i], longitude[i]));

                // Add a new document with a generated ID
                db.collection("people")
                        .add(docData)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.w(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.e(TAG, "Error adding document - error", e);
                            }
                        });
            }


            //add collection to document
            final Map<String, Object> connection_place = new HashMap<>();
            connection_place.put("name", "Γιωργάκης");
            connection_place.put("surname", "Παπανδρέας");
            connection_place.put("town", "Χανιά");
            query.get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        private String documentId;
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                                    Log.e(TAG, document.getId() + " => " + document.getData());

                                    if(document.getString("name").equals("Κώστας")) {
                                        Log.e(TAG, "INSIDE");
                                        documentId = "/"+document.getId();
                                        db.collection("people").document(documentId).collection("places").add(connection_place);
                                    }
                                }
                            } else {
                                Log.e(TAG, "Error getting documents: ", task.getException());
                            }
                        }
                    });
        } else {
            // No user is signed in
//            tv.setText("No one is signed in");
        }
    }


    private void getAsthenis(Query query){

        FirestoreRecyclerOptions<Asthenis> response = new FirestoreRecyclerOptions.Builder<Asthenis>()
                .setQuery(query, Asthenis.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<Asthenis, AsthenisViewHolder>(response) {

            @Override
            protected void onBindViewHolder(@NonNull AsthenisViewHolder holder, int position, @NonNull Asthenis asthenis) {
                holder.setVatNumber(asthenis.getVat());
                holder.setDate(asthenis.getDate_long());
                holder.setNameString(asthenis.getName());
                holder.setSurnameString(asthenis.getSurname());
                holder.setAgeString(asthenis.getAge());
                holder.setTownString(asthenis.getTown());
                holder.setRegionString(asthenis.getRegion());
                holder.setMobileString(asthenis.getMobile());
                holder.setPhoneHomeString(asthenis.getPhone_home());
            }

            @NonNull
            @Override
            public AsthenisViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_asthenis_item, parent, false);
                return new AsthenisViewHolder(view);
            }
        };
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_mainactivity, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                getAsthenis(db.collection("people"));
                adapter.startListening();

//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        pelatesList = App.get().getDB().pelatesDao().getAll();
//                        populatePelates(pelatesList);
//                    }
//                }).start();

                Toast t = Toast.makeText(MainActivity.this, "close", Toast.LENGTH_SHORT);
                t.show();

                return false;
            }
        });

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String text) {
                // filter recycler view when query submitted
                Query querySearch = db.collection("people").whereGreaterThanOrEqualTo("name", text);
                if (!text.trim().isEmpty()){
                    getAsthenis(querySearch);
                    adapter.startListening();
                } else {
                    getAsthenis(db.collection("people"));
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                if (!query.trim().isEmpty()){
//                    getList(queryFinal);
//                    adapter.startListening();
                }
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            return true;
        } else if (id == R.id.action_neosAsthenis) {
            Intent intent = new Intent(MainActivity.this, NewEditPatientActivity.class);
            intent.putExtra("editFlag", false);
            startActivityForResult(intent, 100);
            return false;
        } else if (id == R.id.action_neaEpafi) {
//            Intent intent = new Intent(MainActivity.this, NewEditContactActivity.class);
//            intent.putExtra("editContactFlag", false);
//            startActivityForResult(intent, 100);
            return false;
        } else if (id == R.id.action_signout) {
            if(isSignedIn) {
                signoutUserAccount();
            } else {
                loginUserAccount();
            }
            return false;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem titleItem = menu.findItem(R.id.action_signout);
        if(isSignedIn) {
            titleItem.setTitle("Αποσύνδεση");
        } else {
            titleItem.setTitle("Σύνδεση");
        }
        return super.onPrepareOptionsMenu(menu);
    }


    private void loginUserAccount() {
        progressBar.setVisibility(View.VISIBLE);

        String email, password;
        email = email_tiet.getText().toString();
        password = passwrod_tiet.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Please enter email...", Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.GONE);
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Please enter password!", Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.GONE);
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(getApplicationContext(), "Login successful!", Toast.LENGTH_LONG).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);

//                            Intent intent = new Intent(_LoginActivity.this, _DashboardActivity.class);
//                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(MainActivity.this, "Login failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                            updateUI(null);

                        }
                    }
                });
    }


    private void registerNewUser() {
        progressBar.setVisibility(View.VISIBLE);

        String email, password;
        email = email_tiet.getText().toString();
        password = passwrod_tiet.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Please enter email...", Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.GONE);
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Please enter password!", Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.GONE);
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        Log.e("task= ", "HERE");
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Registration successful!", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);

                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);

//                            Intent intent = new Intent(_RegistrationActivity.this, _LoginActivity.class);
//                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(MainActivity.this, "User Sign up Authentication Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                            updateUI(null);
                        }
                    }
                });
    }

    public void signoutUserAccount() {

        mAuth.signOut();
        updateUI(null);

    }

    @Override
    public void onListItemClick(int clickedItemIndex) {

    }

    private class AsthenisViewHolder extends RecyclerView.ViewHolder {
        private View view;

        AsthenisViewHolder(View itemView) {
            super(itemView);
            view = itemView;
        }
        void setVatNumber(long vatNumber) {
            TextView textView = view.findViewById(R.id.row_vat);
            textView.setText("ΑΦΜ: "+Long.toString(vatNumber));
        }
        void setDate(long date) {

            TextView textView = view.findViewById(R.id.row_date);
            if(date == 0 ) {
                textView.setText("zero");
            } else {
                CharSequence formatted = DateUtils.formatDateTime(getApplicationContext(), date, DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_NUMERIC_DATE | DateUtils.FORMAT_SHOW_YEAR);
                textView.setText("Ημ.ασθ.: "+formatted);
                textView.setError(null);
            }
        }

        void setNameString(String name) {
            TextView textView = view.findViewById(R.id.row_name);
            textView.setText(name);
        }
        void setSurnameString(String surname) {
            TextView textView = view.findViewById(R.id.row_surname);
            textView.setText(surname);
        }
        void setAgeString(String age) {
            TextView textView = view.findViewById(R.id.row_age);
            textView.setText("Ηλ.:"+age);
        }
        void setTownString(String town) {
            TextView textView = view.findViewById(R.id.row_town);
            textView.setText(town);
        }
        void setRegionString(String region) {
            TextView textView = view.findViewById(R.id.row_region);
            textView.setText(region);
        }
        void setMobileString(String mobile) {
            TextView textView = view.findViewById(R.id.row_mobile);
            textView.setText("Κιν.: "+mobile);
        }
        void setPhoneHomeString(String phone_home) {
            TextView textView = view.findViewById(R.id.row_phone_home);
            textView.setText("Σπίτι: "+phone_home);
        }
    }


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        Log.e(TAG, "onActivityResult, requestCode=" + requestCode + " resultCode=" + resultCode + " data=" + data);
//        if (requestCode == 100 && resultCode > 0) {
//            if (resultCode == 5) { // resultCode=5 means ADD new pelatis
//                Log.e(TAG, "onActivityResult and resultCode=5");
//                pelatesList.add((Pelates) data.getParcelableExtra("pelatis"));
//                mPelatesAdapter.notifyDataSetChanged();
//                recyclerView.setAdapter(mPelatesAdapter);
//            } else if (resultCode == 4) { // resultCode==4 means UPDATE pelatis
//                pelatesList.set(pos, (Pelates) data.getParcelableExtra("pelatis"));
//                mPelatesAdapter.notifyDataSetChanged();
//                recyclerView.setAdapter(mPelatesAdapter);
//            } else if (resultCode == 3) { // resultCode=3 means DELETE pelatis
//                pelatesList.remove(pos);
//                mPelatesAdapter.notifyDataSetChanged();
//                recyclerView.setAdapter(mPelatesAdapter);
//            } else if (resultCode == 6) { // resultCode=6 means DELETE pirwmi
//                plirwmesList.remove(pos);
//                mPlirwmesAdapter.notifyDataSetChanged();
//                recyclerView.setAdapter(mPlirwmesAdapter);
//            } else if (resultCode == 7) {// resultCode==7 means RETURN HOME/UP key from DetailPlirwmiActivity
//                Log.i(TAG, "return pressing homeAsUp from DETAILPLIRWMIACTIVITY");
//                plirwmesList.set(pos, (Plirwmes) data.getParcelableExtra("plirwmi"));
//                mPlirwmesAdapter.notifyDataSetChanged();
//                recyclerView.setAdapter(mPlirwmesAdapter);
//            } else if (resultCode == 8) { // resultCode == 8 means ADD new Plirwmi
//                plirwmesList.add((Plirwmes) data.getParcelableExtra("plirwmi"));
//                mPlirwmesAdapter.notifyDataSetChanged();
//                recyclerView.setAdapter(mPlirwmesAdapter);
//            }
//        }
//    }
}
