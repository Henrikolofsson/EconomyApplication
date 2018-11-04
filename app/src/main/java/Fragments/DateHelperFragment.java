package Fragments;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Calendar;

import henrik.mau.economyapplication.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DateHelperFragment extends DialogFragment {
    private DatePickerDialog.OnDateSetListener listener;

    public Dialog onCreateDialog(Bundle savedInstancState){
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), listener, year, month, day);
    }

    public void setOnDateListener(DatePickerDialog.OnDateSetListener listener){
        this.listener = listener;
    }

}
