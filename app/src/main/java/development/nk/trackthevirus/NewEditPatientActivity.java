package development.nk.trackthevirus;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import development.nk.trackthevirus.entity.Asthenis;
import development.nk.trackthevirus.utilities.DatePickerFragment;


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
    private FirebaseFirestore db;


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

        db = FirebaseFirestore.getInstance();

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
        mStreet_number = (TextInputEditText) findViewById(R.id.street_number_tiet);
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
            Log.e(TAG, "TO UPDATE");
            getSupportActionBar().setTitle("Διόρθωση πελάτη");
            if ((asthenis = (Asthenis) getIntent().getParcelableExtra("pelatisToEdit")) != null) {
                mVat.setText(String.valueOf(asthenis.getVat()));
                mName.setText(asthenis.getName());
                mSurname.setText(asthenis.getSurname());
                mDueDate = asthenis.getDate_long();
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


            saveAstheni();

        }
        return super.onOptionsItemSelected(item);
    }


    public void signout(View view) {
        FirebaseAuth.getInstance().signOut();
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

        GeoPoint p =null;
        String addressStr = mStreet_name.getText().toString()+" "+mStreet_number.getText().toString()+", "+mTown.getText().toString()+", "+mZip.getText().toString()+", "+mCountry.getText().toString();
        try {
//            getLocationFromAddress("Αργυροπούλων 4, Άγιος Νικόλαος, 72100, Ελλάδα");
            System.out.println("addressStr= "+addressStr);
            p = getLocationFromAddress(addressStr);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Map<String, Object> docData = new HashMap<>();
        docData.put("vat", Long.parseLong(mVat.getText().toString()));
        docData.put("name", mName.getText().toString());
        docData.put("surname", mSurname.getText().toString());
        docData.put("date_long", getDateSelection());
        docData.put("date", new Timestamp(new Date(getDateSelection())));
        docData.put("age", mAge.getText().toString());
        docData.put("town", mTown.getText().toString());
        docData.put("region", mRegion.getText().toString());
        docData.put("mobile", mMobile.getText().toString());
        docData.put("phone_home", mPhone_home.getText().toString());
        docData.put("geopoint", p);

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

//        setResult(asthenis, 5);
    }

    public GeoPoint getLocationFromAddress(String strAddress) throws IOException {
        Geocoder geoCoder = new Geocoder(this);
        List<Address> address;
        GeoPoint p1 = null;
        double latitude = 0.0;
        double longtitude = 0.0;

        try {
            List<Address> addresses = geoCoder.getFromLocationName(strAddress, 1);
            if (addresses.size() >  0) {
                latitude = addresses.get(0).getLatitude();
                longtitude = addresses.get(0).getLongitude();
                p1 = new GeoPoint(latitude, longtitude);
                Log.e(TAG, "latitude= "+latitude+" longtitude= "+longtitude);
                return p1;
            }
        } catch (IOException e) { // TODO Auto-generated catch block
            e.printStackTrace(); }

        return p1;
    }

    private void updateAstheni() {


        setResult(asthenis, 4);

    }

    private void setResult(Asthenis asthenis, int flag) {
        Log.e(TAG, "SET RESULT");
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


    public void goMap(View view) {

        Intent iMap = new Intent(NewEditPatientActivity.this, MapsActivity.class);
        startActivityForResult(iMap, 200);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e(TAG, "onActivityResult, requestCode=" + requestCode + " resultCode=" + resultCode + " data=" + data);
        if (requestCode == 200 && resultCode > 0) {
            if (resultCode == 9) { // resultCode=5 means ADD new pelatis
                Log.e(TAG, "onActivityResult and resultCode=9");

                Address a = data.getParcelableExtra("address");
                String all = a.toString();
                String fullAdd = a.getAddressLine(0);
                String aCountryName = a.getCountryName();
                String aLocality = a.getLocality();  // town
                String aThoroughfare = a.getThoroughfare(); // street name
                String aFeatureName = a.getFeatureName(); // street number
                String aPostalCode = a.getPostalCode(); // zip code

                System.out.println("all= " + all);
                System.out.println("fullAdd= " + fullAdd);
                System.out.println("aCountryName= " + aCountryName);
                System.out.println("aLocality= " + aLocality);
                System.out.println("aThoroughfare= " + aThoroughfare);
                System.out.println("aFeatureName= " + aFeatureName);
                System.out.println("aPostalCode= " + aPostalCode);

            } else if (resultCode == 4) { // resultCode==4 means UPDATE pelatis
//                pelatesList.set(pos, (Pelates) data.getParcelableExtra("pelatis"));
//                mPelatesAdapter.notifyDataSetChanged();
//                recyclerView.setAdapter(mPelatesAdapter);
            } else if (resultCode == 3) { // resultCode=3 means DELETE pelatis
//                pelatesList.remove(pos);
//                mPelatesAdapter.notifyDataSetChanged();
//                recyclerView.setAdapter(mPelatesAdapter);
            } else if (resultCode == 6) { // resultCode=6 means DELETE pirwmi
//                plirwmesList.remove(pos);
//                mPlirwmesAdapter.notifyDataSetChanged();
//                recyclerView.setAdapter(mPlirwmesAdapter);
            } else if (resultCode == 7) {// resultCode==7 means RETURN HOME/UP key from DetailPlirwmiActivity
//                Log.i(TAG, "return pressing homeAsUp from DETAILPLIRWMIACTIVITY");
//                plirwmesList.set(pos, (Plirwmes) data.getParcelableExtra("plirwmi"));
//                mPlirwmesAdapter.notifyDataSetChanged();
//                recyclerView.setAdapter(mPlirwmesAdapter);
            } else if (resultCode == 8) { // resultCode == 8 means ADD new Plirwmi
//                plirwmesList.add((Plirwmes) data.getParcelableExtra("plirwmi"));
//                mPlirwmesAdapter.notifyDataSetChanged();
//                recyclerView.setAdapter(mPlirwmesAdapter);
            }
        }
    }
}
