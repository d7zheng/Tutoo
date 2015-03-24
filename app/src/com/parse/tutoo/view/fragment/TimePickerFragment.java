package com.parse.tutoo.view.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TimePicker;

import com.parse.tutoo.R;
import com.parse.tutoo.view.CreatePostActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TimePickerFragment} interface
 * to handle interaction events.
 * Use the {@link TimePickerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TimePickerFragment extends DialogFragment {

    private TimePickerDialog.OnTimeSetListener onTimeSetListener;

    public static TimePickerFragment newInstance(TimePickerDialog.OnTimeSetListener onTimeSetListener) {
        TimePickerFragment pickerFragment = new TimePickerFragment();
        pickerFragment.setOnTimeSetListener(onTimeSetListener);

        //Pass the date in a bundle.
        Bundle bundle = new Bundle();
        pickerFragment.setArguments(bundle);
        return pickerFragment;
    }

    private void setOnTimeSetListener(TimePickerDialog.OnTimeSetListener listener) {
        this.onTimeSetListener = listener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), onTimeSetListener, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

/*    public Calendar getCalendar() {
        return this.c;
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        //Button activityButton = (Button)getActivity().findViewById(R.id.);
        //activityButton.setText ();
        c.set(c.HOUR_OF_DAY, hourOfDay);
        c.set(c.MINUTE, minute);
        CreatePostActivity cp = (CreatePostActivity) getActivity();
        cp.setTimeOnButton();
    }*/
}