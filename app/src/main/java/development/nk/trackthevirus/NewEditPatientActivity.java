package development.nk.trackthevirus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import development.nk.trackthevirus.entity.Asthenis;
import development.nk.trackthevirus.utilities.DatePickerFragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


public class NewEditPatientActivity extends AppCompatActivity implements
        DatePickerDialog.OnDateSetListener,
        View.OnClickListener {

    private static final String TAG = "NewEditPatientActivity";
    private TextInputEditText mVat, mName, mSurname, mEmail, mMobile, mPhone_home, mPhone_work, mAge;
    private TextInputEditText mStreet_name, mStreet_number, mTown, mZip, mRegion, mCountry, mParatiriseis;
    private MaterialTextView label_date;
    Toolbar toolbar;

    //Selected due date, stored as a timestamp
    private long mDueDate = Long.MAX_VALUE;
    private TextView mDueDateView;

    private Asthenis asthenis;
    private boolean forUpdate = false;

    // Access a Cloud Firestore instance from your Activity
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.new_edit_patient_activity);
        System.out.println(TAG);

        toolbar = findViewById(R.id.toolbar);
//        toolbar.setTitle("Προσθήκη νέου Ασθενή");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // drop down menu for gender
        String[] gender = new String[]{"Άνδρας", "Γυναίκα"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, gender);
        AutoCompleteTextView editTextFilledExposedDropdown = findViewById(R.id.filled_exposed_dropdown);
        editTextFilledExposedDropdown.setAdapter(adapter);

        asthenis = new Asthenis();

        mVat = (TextInputEditText) findViewById(R.id.vat_tiet);
        mName = (TextInputEditText) findViewById(R.id.name_tiet);
        mSurname = (TextInputEditText) findViewById(R.id.surname_tiet);
        mDueDateView = (TextView) findViewById(R.id.text_date);
        View mSelectDate = findViewById(R.id.select_date);
        mSelectDate.setOnClickListener(this);
        label_date = (MaterialTextView) findViewById(R.id.label_date);
        mEmail = (TextInputEditText) findViewById(R.id.email_tiet);
        mMobile = (TextInputEditText) findViewById(R.id.mobile_tiet);
        mPhone_home = (TextInputEditText) findViewById(R.id.phone_home_tiet);
        mPhone_work = (TextInputEditText) findViewById(R.id.phone_work_tiet);
        mAge = (TextInputEditText) findViewById(R.id.age_tiet);
        mStreet_name = (TextInputEditText) findViewById(R.id.street_name_tiet);
        mStreet_number = (TextInputEditText) findViewById(R.id.street_name_tiet);
        mTown = (TextInputEditText) findViewById(R.id.town_tiet);
        mZip = (TextInputEditText) findViewById(R.id.zip_tiet);
        mRegion = (TextInputEditText) findViewById(R.id.region_tiet);
        mCountry = (TextInputEditText) findViewById(R.id.country_tiet);
        mParatiriseis = (TextInputEditText) findViewById(R.id.paratiriseis_tiet);
        updateDateDisplay();

        // get Parcelable data from Intent
        Intent i = getIntent();
        forUpdate = i.getExtras().getBoolean("toUpdate");
        if (forUpdate) {
            getSupportActionBar().setTitle("Διόρθωση πελάτη");
            if ((asthenis = (Asthenis) getIntent().getParcelableExtra("pelatisToEdit")) != null) {
                mVat.setText(String.valueOf(asthenis.getVat()));
                mName.setText(asthenis.getName());
                mSurname.setText(asthenis.getSurname());
                mDueDate = asthenis.getArxiki_imerominia();
                CharSequence formatted = DateUtils.formatDateTime(this, mDueDate, DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_NUMERIC_DATE | DateUtils.FORMAT_SHOW_YEAR);
                mDueDateView.setText(formatted);
                mEmail.setText(asthenis.getEmail());
                mMobile.setText(asthenis.getMobile());
                mPhone_home.setText(asthenis.getPhone_home());
                mPhone_work.setText(asthenis.getPhone_work());
                mAge.setText(asthenis.getAge());
                mStreet_name.setText(asthenis.getStreet_name());
                mStreet_number.setText(asthenis.getStreet_number());
                mTown.setText(asthenis.getTown());
                mZip.setText(asthenis.getZip());
                mRegion.setText(asthenis.getRegion());
                mCountry.setText(asthenis.getCountry());
                mParatiriseis.setText(asthenis.getParatiriseis());
            }
        }

//        checkIfSignedIn();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_neweditpatientactivity, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        if (forUpdate) {
            MenuItem titleItem = menu.findItem(R.id.action_save);
            titleItem.setTitle("UPDATE");
        }

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_save) {

//            if(validators.checkInputsNewPelatis()) {
//                if (forUpdate) {
//                    updateAstheni();
//                } else {
//                    saveAstheni();
//                }
//                return true;
//            }

        }
        return super.onOptionsItemSelected(item);
    }

    private void checkIfSignedIn() {

//        TextView tv = findViewById(R.id.textView5);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user == null) {
            // User is signed in
//            String email = user.getEmail();
//            tv.setText("You are signed in with email: " + email);

            // Create a new people with a first and last name
            asthenis.setVat(123123555);
            asthenis.setName("Tonia");
            asthenis.setSurname("Kontola");
            asthenis.setAge("12");
            asthenis.setGender("female");
            asthenis.setMobile("69995784946");

            // Add a new document with a generated ID
            db.collection("people")
                    .add(asthenis)
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
        } else {
            // No user is signed in
//            tv.setText("No one is signed in");
        }
    }


    public void signout(View view) {
        FirebaseAuth.getInstance().signOut();
        checkIfSignedIn();
    }


    private void updateDateDisplay() {
        if (getDateSelection() == Long.MAX_VALUE) {
            mDueDateView.setText("Not set");
        } else {
//            CharSequence formatted = DateUtils.getRelativeTimeSpanString(this, mDueDate);
            CharSequence formatted = DateUtils.formatDateTime(this, mDueDate, DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_NUMERIC_DATE | DateUtils.FORMAT_SHOW_YEAR);
            mDueDateView.setText(formatted);
            mDueDateView.setError(null);
            label_date.setText("Ημ/νια επιβεβ.ασθένειας");
        }
    }

    /* Manage the selected date value */
    public void setDateSelection(long selectedTimestamp) {
        mDueDate = selectedTimestamp;
        updateDateDisplay();
    }

    public long getDateSelection() {
        return mDueDate;
    }

    /* Click events on Due Date */
    @Override
    public void onClick(View v) {
        DatePickerFragment dialogFragment = new DatePickerFragment();
        dialogFragment.show(getSupportFragmentManager(), "datePicker");
    }

    /* Date set events from dialog */
    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        //Set to noon on the selected day
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);
//        c.set(Calendar.HOUR_OF_DAY, 12);
//        c.set(Calendar.MINUTE, 0);
//        c.set(Calendar.SECOND, 0);

        setDateSelection(c.getTimeInMillis());
    }

    private void saveAstheni() {


        setResult(asthenis, 5);

    }

    private void updateAstheni() {


        setResult(asthenis, 4);

    }

    private void setResult(Asthenis asthenis, int flag) {
        setResult(flag, new Intent().putExtra("asthenis", asthenis));
        finish();
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if (savedInstanceState != null) {
            mDueDate = savedInstanceState.getLong("date");
            updateDateDisplay();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        if (mDueDate != Long.MAX_VALUE) {
            outState.putLong("date", getDateSelection());
        }
        super.onSaveInstanceState(outState);
    }


}
