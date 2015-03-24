package com.parse.tutoo.view.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

import com.parse.tutoo.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DatePickerFragment} interface
 * to handle interaction events.
 * Use the {@link DatePickerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DatePickerFragment extends DialogFragment{

    private DatePickerDialog.OnDateSetListener onDateSetListener;

    public static DatePickerFragment newInstance(DatePickerDialog.OnDateSetListener onDateSetListener) {
        DatePickerFragment pickerFragment = new DatePickerFragment();
        pickerFragment.setOnDateSetListener(onDateSetListener);

        //Pass the date in a bundle.
        Bundle bundle = new Bundle();
        pickerFragment.setArguments(bundle);
        return pickerFragment;
    }

    private void setOnDateSetListener(DatePickerDialog.OnDateSetListener listener) {
        this.onDateSetListener = listener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), onDateSetListener, year, month, day);
    }

    /*public Calendar getCalendar() {
        return this.c;
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        c.set(year, month, day);
    }*/


}