package development.nk.trackthevirus.utilities;

import android.util.Log;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import development.nk.trackthevirus.NewEditContactActivity;
import development.nk.trackthevirus.NewEditPatientActivity;
import development.nk.trackthevirus.R;

/**
 * Created by NKdevelopment on 30/4/2020.
 */
public class Validators {

//    public NewEditPatientActivity newEditPatientActivity;
//    public NewEditContactActivity newEditContactActivity;
//
//    private TextInputEditText mName, mSurname, mArxiko_poso, mKwdikos_ergou, mParatiriseis;
//    private TextInputEditText mPoso;
//    //Selected due date, stored as a timestamp
//    private long mDueDate = Long.MAX_VALUE;
//    private TextView mDueDateView;
//    private TextView mDueDateViewPl;
//
//    public Validators(NewEditPatientActivity newEditPelatisActivity) {
//        this.newEditPatientActivity = newEditPelatisActivity;
//    }
//
//    public Validators(NewEditContactActivity newEditPlirwmiActivity) {
//        this.newEditContactActivity = newEditPlirwmiActivity;
//    }
//
//    public boolean checkInputsNewPelatis() {
//        mName = (TextInputEditText) this.newEditPatientActivity.findViewById(R.id.til_name);
//        mSurname = (TextInputEditText) this.newEditPatientActivity.findViewById(R.id.til_surname);
//        mArxiko_poso = (TextInputEditText) this.newEditPatientActivity.findViewById(R.id.til_arxikoposo);
//        mDueDateView = (TextView) this.newEditPatientActivity.findViewById(R.id.text_date);
//
//        int arxiko_poso = -1;
//        try {
//            arxiko_poso = Integer.parseInt(mArxiko_poso.getText().toString());
//            Log.i("checkInputs","checkInputs arxiko_poso= "+arxiko_poso);
//        } catch (NumberFormatException e) {
//            // The format was incorrect
//        }
//
//        if (mName.getText().toString().trim().length() < 2 || mName.getText().toString().trim().length() > 80) {
//            mName.setError("Από 2 έως 80 γράμματα");
//            return false;
//        } else if (mSurname.getText().toString().trim().length() < 2 || mSurname.getText().toString().trim().length() > 80) {
//            mSurname.setError("Από 2 έως 80 γράμματα");
//            return false;
//        } else if (mArxiko_poso.getText().toString().equals("")) {
//            mArxiko_poso.setError("Δώστε αρχικό ποσό");
//            return false;
//        }else if (arxiko_poso < 1 || arxiko_poso >= Integer.MAX_VALUE) {
//            mArxiko_poso.setError("Δώστε αρχικό ποσό");
//            return false;
//        } else if(newEditPelatisActivity.getDateSelection() == Long.MAX_VALUE) {
//            mDueDateView.setError("Ορίστε ημερομηνία");
//            return false;
//        }
//        return true;
//    }
//
//    public boolean checkInputsNewPlirwmi() {
//        mPoso = (TextInputEditText) this.newEditContactActivity.findViewById(R.id.til_poso);
//        mDueDateViewPl = (TextView) this.newEditContactActivity.findViewById(R.id.text_date);
//
//        int posoPlirwmis = -1;
//        try {
//            posoPlirwmis = Integer.parseInt(mPoso.getText().toString());
//            Log.i("checkInputs","checkInputs arxiko_poso= "+posoPlirwmis);
//        } catch (NumberFormatException e) {
//            // The format was incorrect
//        }
//
//        if (mPoso.getText().toString().equals("")) {
//            mPoso.setError("Δώστε ποσό");
//            return false;
//        }else if (posoPlirwmis < 1 || posoPlirwmis >= Integer.MAX_VALUE) {
//            mPoso.setError("Δώστε ποσό");
//            return false;
//        } else if(newEditContactActivity.getDateSelection() == Long.MAX_VALUE) {
//            mDueDateViewPl.setError("Ορίστε ημερομηνία");
//            return false;
//        }
//        return true;
//    }

}
