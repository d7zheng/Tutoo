package com.parse.tutoo.view.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TimePicker;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.tutoo.R;
import com.parse.tutoo.model.Booking;
import com.parse.tutoo.model.Notification;
import com.parse.tutoo.util.NotificationListAdapter;
import com.parse.tutoo.view.ViewPostActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Notification {@link Fragment} subclass.
 */
public class NotificationFragment extends Fragment {

    protected Context context;
    protected List<Notification> notifications = new ArrayList<>();
    private Calendar startTime = Calendar.getInstance();
    private Calendar endTime = Calendar.getInstance();

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //super.onCreate(savedInstanceState);

        final View rootView = inflater.inflate(R.layout.fragment_notification, container, false);
        //View rootView = inflater.inflate(R.layout.main_list_view, container, false);
        //setContentView(R.layout.main_list_view);

        //setContentView(R.layout.main_list_view);
        context = getActivity().getApplicationContext();

        //adapter = new MenuListAdapter(notifications, getActivity());

        // Query for new notifications
        ParseQuery unchecked = new ParseQuery("Notification");
        unchecked.whereEqualTo("toUser", ParseUser.getCurrentUser().getObjectId());
        unchecked.whereNotEqualTo("checked", true);

        // Query for recent notification
        ParseQuery recent = new ParseQuery("Notification");
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DAY_OF_YEAR,-Notification.recencyDayLimit);
        Date recentDate = cal.getTime();
        recent.whereGreaterThan("updatedAt", recentDate);
        recent.whereEqualTo("toUser", ParseUser.getCurrentUser().getObjectId());

        // List of queries
        List<ParseQuery<Notification>> queryList = new ArrayList<ParseQuery<Notification>>();
        queryList.add(unchecked);
        queryList.add(recent);

        // Combine the queries using OR
        ParseQuery query = ParseQuery.or(queryList);
        query.orderByDescending("createdAt");
        if (notifications.size() == 0) {
            query.findInBackground(new FindCallback <Notification> () {
                   @Override
                   public void done(List<Notification> results, ParseException e) {
                       if (e == null) {
                           notifications = results;
                           createListView(rootView);
                       }
                       else {
                           System.out.println(e.getMessage());
                       }
                   }
               }
            );
        }
        else {
            createListView(rootView);
        }
        return rootView;
    }

    private void createListView(View rootView) {
        // Create List View
        NotificationListAdapter adapter = new NotificationListAdapter(notifications,getActivity());
        final ListView listView = (ListView) rootView.findViewById(R.id.list);
        listView.setAdapter(adapter);

        // ListView Item Click Listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Notification note = notifications.get(position);
                note.setChecked(true);
                note.saveInBackground();
                if ((note.getType().equals(Notification.notificationType.BOOKING_REQUEST.toString())) ||
                        (note.getType().equals(Notification.notificationType.BOOKING_RESCHEDULE.toString())) ||
                        (note.getType().equals(Notification.notificationType.BOOKING_ACCEPT.toString())) ||
                        (note.getType().equals(Notification.notificationType.BOOKING_DECLINE.toString()))) {
                    ParseQuery<Booking> bookingQuery = new ParseQuery("Booking");
                    bookingQuery.whereEqualTo("objectId", note.getPostId());
                    bookingQuery.getInBackground(note.getPostId(), new GetCallback<Booking>() {
                        @Override
                        public void done(Booking booking, ParseException e) {
                            if ((booking.getStatus().equals(Booking.status.REQUEST.toString())) ||
                                    (booking.getStatus().equals(Booking.status.RESCHEDULE.toString()))) {
                                showBookingDialog(booking);
                            } else if (booking.getStatus().equals(Booking.status.ACCEPTED.toString())) {
                                // TODO:Schedule on Calendar
                                addEventToCalendar(booking);
                            }
                        }
                    });
                } else if (note.getType().equals(Notification.notificationType.REPLY.toString())) {
                    Intent intent = new Intent(context, ViewPostActivity.class);
                    intent.putExtra("post_id", note.getPostId());
                    startActivity(intent);
                }
            }
        });
    }

    private void showBookingDialog(final Booking booking) {
        LayoutInflater inflater = (LayoutInflater) getActivity()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.booking_popup,
                (ViewGroup) getActivity().findViewById(R.id.popup_element));

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

        alertDialog.setView(layout);

        // Setting Dialog Title
        alertDialog.setTitle("Booking Confirmation");

        // Setting Dialog Message
        alertDialog.setMessage("Please accept, reschedule, or decline");

        // Set Requester Selected Dates
        Date[] dates = booking.getDateTime();
        startTime.setTime(dates[0]);
        endTime.setTime(dates[1]);
        final Calendar startCal = Calendar.getInstance();
        final Calendar endCal = Calendar.getInstance();
        startCal.setTime(startTime.getTime());
        endCal.setTime(endTime.getTime());
        String initDate = dateToString(startCal.get(Calendar.YEAR), startCal.get(Calendar.MONTH), startCal.get(Calendar.DAY_OF_MONTH));
        String initStartTime = timeToString(startCal.get(Calendar.HOUR_OF_DAY),startCal.get(Calendar.MINUTE));
        String initEndTime = timeToString(endCal.get(Calendar.HOUR_OF_DAY),endCal.get(Calendar.MINUTE));
        Button datepicker = (Button) layout.findViewById(R.id.datepicker1);
        Button timepicker1 = (Button) layout.findViewById(R.id.timepicker1);
        Button timepicker2 = (Button) layout.findViewById(R.id.timepicker2);
        datepicker.setText(initDate);
        datepicker.setTextAppearance(getActivity().getApplicationContext(), android.R.attr.textAppearanceSmall);
        timepicker1.setText(initStartTime);
        timepicker1.setTextAppearance(getActivity().getApplicationContext(), android.R.attr.textAppearanceSmall);
        timepicker2.setText(initEndTime);
        timepicker2.setTextAppearance(getActivity().getApplicationContext(), android.R.attr.textAppearanceSmall);

        datepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(v);
            }
        });

        timepicker1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(v);
            }
        });

        timepicker2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(v);
            }
        });

        // On pressing Book button
        alertDialog.setPositiveButton("Accept", new DialogInterface.OnClickListener() {
            public void onClick(final DialogInterface dialog,int which) {
                final ParseUser currentUser = ParseUser.getCurrentUser();
                // Create new booking
                final Booking bookingReply = new Booking();
                bookingReply.setRequester(currentUser.getObjectId(),currentUser.getString("name"));
                bookingReply.setRequested(booking.getRequester(), booking.getRequesterName());
                // Check calendar time
                if ((calendarEquals(startCal, startTime)) && (calendarEquals(endCal, endTime))) {
                    bookingReply.setStatus(Booking.status.ACCEPTED);
                    // TODO: Add to Calendar
                    addEventToCalendar(booking);
                }
                else {
                    bookingReply.setStatus(Booking.status.RESCHEDULE);
                }
                Date start = startTime.getTime();
                Date end = endTime.getTime();
                bookingReply.setDateTime(start, end);
                bookingReply.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            ParseUser currentUser = ParseUser.getCurrentUser();
                            // Create notification
                            Notification note = new Notification();
                            note.setFromUser(currentUser.getObjectId(), currentUser.getString("name"));
                            note.setToUser(booking.getRequester(), booking.getRequesterName());
                            if (bookingReply.getStatus().equals(Booking.status.ACCEPTED.toString())) {
                                note.setType(Notification.notificationType.BOOKING_ACCEPT);
                            }
                            else {
                                note.setType(Notification.notificationType.BOOKING_RESCHEDULE);
                            }
                            note.setPostId(bookingReply.getObjectId());
                            note.saveInBackground();
                        }
                    }
                });
                dialog.dismiss();
            }
        });


        // On pressing cancel button
        alertDialog.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // On pressing Decline button
        alertDialog.setNegativeButton("Decline", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                final ParseUser currentUser = ParseUser.getCurrentUser();
                // Create new booking
                final Booking bookingReply = new Booking();
                bookingReply.setRequester(currentUser.getObjectId(),currentUser.getString("name"));
                bookingReply.setRequested(booking.getRequester(), booking.getRequesterName());
                bookingReply.setStatus(Booking.status.DECLINED);
                bookingReply.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            ParseUser currentUser = ParseUser.getCurrentUser();
                            // Create notification
                            Notification note = new Notification();
                            note.setFromUser(currentUser.getObjectId(), currentUser.getString("name"));
                            note.setToUser(booking.getRequester(), booking.getRequesterName());
                            note.setType(Notification.notificationType.BOOKING_DECLINE);
                            note.setPostId(bookingReply.getObjectId());
                            note.saveInBackground();
                        }
                    }
                });
                dialog.dismiss();
            }
        });

        alertDialog.create();

        // Showing Alert Message
        alertDialog.show();
    }

    private void addEventToCalendar(Booking booking) {
        Calendar beginTime = Calendar.getInstance();
        Date[] dates = booking.getDateTime();
        beginTime.setTime(dates[0]);
        Calendar endTime = Calendar.getInstance();
        endTime.setTime(dates[1]);
        Intent intent = new Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis())
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis())
                .putExtra(CalendarContract.Events.TITLE, "Session with " + booking.getRequesterName())
                .putExtra(CalendarContract.Events.DESCRIPTION, "Tutoring")
                .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY);
        startActivity(intent);

    }

    private boolean calendarEquals(Calendar cal1, Calendar cal2) {
        if ((cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)) &&
                (cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH)) &&
                (cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH)) &&
                (cal1.get(Calendar.HOUR_OF_DAY) == cal2.get(Calendar.HOUR_OF_DAY)) &&
                (cal1.get(Calendar.MINUTE) == cal2.get(Calendar.MINUTE))) {
            return true;
        }
        return false;
    }

    private String dateToString(int year, int month, int day) {
        StringBuilder dateString = new StringBuilder()
                .append(year).append("-")
                        // Month is 0 based, just add 1
                .append(month + 1).append("-")
                .append(day).append(" ");
        return dateString.toString();
    }

    private String timeToString(int hour, int minute) {
        StringBuilder timeString = new StringBuilder().append(hour)
                .append(":").append(minute);
        return timeString.toString();
    }

    public void showDatePickerDialog(View v) {
        final Button b = (Button) v;
        DatePickerDialog.OnDateSetListener dpListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String s = dateToString(year, monthOfYear, dayOfMonth);
                b.setText(s);
                b.setTextAppearance(getActivity().getApplicationContext(), android.R.attr.textAppearanceSmall);
                startTime.set(year, monthOfYear, dayOfMonth);
                endTime.set(year, monthOfYear, dayOfMonth);
            }
        };
        DatePickerFragment dpFragment = DatePickerFragment.newInstance(dpListener);
        dpFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
    }

    public void showTimePickerDialog(View v) {
        final Button b = (Button) v;
        TimePickerDialog.OnTimeSetListener tpListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String s = timeToString(hourOfDay, minute);
                b.setText(s);
                b.setTextAppearance(getActivity().getApplicationContext(), android.R.attr.textAppearanceSmall);
                if (b.getId() == R.id.timepicker1) {
                    startTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    startTime.set(Calendar.MINUTE,minute);
                }
                else if (b.getId() == R.id.timepicker2) {
                    endTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    endTime.set(Calendar.MINUTE,minute);
                }
            }
        };
        TimePickerFragment tpFragment = TimePickerFragment.newInstance(tpListener);
        tpFragment.show(getActivity().getSupportFragmentManager(), "timePicker");
    }
}
