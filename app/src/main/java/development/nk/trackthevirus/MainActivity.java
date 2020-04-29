package development.nk.trackthevirus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;

    private FirebaseAuth mAuth;
    private EditText emailTV, passwordTV;
    private Button loginBtn;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer);
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.navigationView);
        toolbar.setTitle("Track the Virus");
        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawerOpen, R.string.drawerClose);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);


        mAuth = FirebaseAuth.getInstance();
        emailTV = findViewById(R.id.email);
        passwordTV = findViewById(R.id.password);
        loginBtn = findViewById(R.id.login_button);
        progressBar = findViewById(R.id.progress_login);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUserAccount();
            }
        });

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
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
                break;
        }


        return false;
    }

    private void loginUserAccount() {
        progressBar.setVisibility(View.VISIBLE);

        String email, password;
        email = emailTV.getText().toString();
        password = passwordTV.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Please enter email...", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Please enter password!", Toast.LENGTH_LONG).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Login successful!", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);

//                            Intent intent = new Intent(_LoginActivity.this, _DashboardActivity.class);
//                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Login failed! Please try again later", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
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
