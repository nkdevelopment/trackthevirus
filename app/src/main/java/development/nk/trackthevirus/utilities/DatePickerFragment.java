package development.nk.trackthevirus.utilities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

/**
 * Created by NKdevelopment on 28/4/2020.
 */

/* Wrapper to show a managed date picker */
public class DatePickerFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), (DatePickerDialog.OnDateSetListener) getActivity(), year, month, day);
//        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        return datePickerDialog;

//        return new DatePickerDialog(getActivity(),
//                (DatePickerDialog.OnDateSetListener) getActivity(), year, month, day).getDatePicker().setMinDate();

    }
}
