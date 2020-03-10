package com.sug.minifinal;


import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class D_Slot_2 extends Fragment implements View.OnClickListener {
    EditText e1;
    TextView t1, t2;
    Button b;
    DatePickerDialog.OnDateSetListener dateSetListener;
    TimePickerDialog timePickerDialog;
    Calendar calendar;
    int currentHour, year1, month1, day1;
    int currentMinute;
    String amPm;
    BB db;
    String n, uid;
    private final String CHANNEL_ID = "personal notifications";
    private final int NOTIFICATION_ID = 001;

    public D_Slot_2() {
        // Required empty public constructor
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_d__slot_2, null);

        e1 = view.findViewById(R.id.editText5);
        e1.setEnabled(false);
        t1 = view.findViewById(R.id.tv1);
        t2 = view.findViewById(R.id.tv2);
        b = view.findViewById(R.id.button13);
        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(getContext(), android.R.style.Theme_Holo_Light_Dialog_MinWidth, dateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getDatePicker().setMinDate(System.currentTimeMillis() - 100);
                cal.add(Calendar.MONTH, +2);
                long oneMonthAhead = cal.getTimeInMillis();
                dialog.getDatePicker().setMaxDate(oneMonthAhead);
                dialog.show();

            }
        });
        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                t1.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                day1 = dayOfMonth;
                year1 = year;
                month1 = month;
            }
        };
        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                currentHour = calendar.get(Calendar.HOUR_OF_DAY);
                currentMinute = calendar.get(Calendar.MINUTE);

                timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        Calendar cal = Calendar.getInstance();
                        int year = cal.get(Calendar.YEAR);
                        int month = cal.get(Calendar.MONTH);
                        int day = cal.get(Calendar.DAY_OF_MONTH);
                        if (month1 == month && day == day1) {
                            if (currentHour <= (hourOfDay-1) && currentMinute <= minutes)
                                t2.setText(new StringBuilder().append(hourOfDay).append(":").append(minutes));
                            else
                                Toast.makeText(getContext(), "Cannot enter previous time", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            t2.setText(new StringBuilder().append(hourOfDay).append(":").append(minutes));
                        }
                    }


                }, currentHour, currentMinute, false);

                timePickerDialog.show();
            }
        });

        FirebaseAuth mAuth2 = FirebaseAuth.getInstance();
        FirebaseDatabase firebaseDatabase2 = FirebaseDatabase.getInstance();
        DatabaseReference mRef2 = firebaseDatabase2.getReference();
        FirebaseUser user = mAuth2.getCurrentUser();
        uid = user.getUid();

        FirebaseAuth.AuthStateListener authStateListener2 = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    //                  Toast.makeText(getContext(), "Signed in as "+user.getUid(), Toast.LENGTH_LONG).show();
                } else {
                    //                  Toast.makeText(getContext(), "Signed out ", Toast.LENGTH_LONG).show();
                }
            }
        };
        mRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //showData(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Bundle z = getArguments();
        int i = z.getInt("message");
        db = new BB(getContext());
        Cursor r = db.getAllData();
        r.moveToPosition(i - 1);
        e1.setText(r.getString(0));
        b.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button13:

                if (TextUtils.isEmpty(t1.getText().toString()) || TextUtils.isEmpty(t2.getText().toString())) {
                    Toast.makeText(getActivity(), "Date/Time is empty", Toast.LENGTH_LONG).show();
                    return;
                }
//                displayNotification();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel channel = new NotificationChannel("Mynotifications", "Mynotifications", NotificationManager.IMPORTANCE_DEFAULT);

                    NotificationManager manager = getActivity().getSystemService(NotificationManager.class);
                    manager.createNotificationChannel(channel);

                }
                NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(), "Mynotifications")
                        .setContentTitle("Donate Slot Booking").setSmallIcon(R.drawable.common_google_signin_btn_icon_dark).setAutoCancel(true).setContentText("Slot Booking Successful for " + t2.getText().toString() + " on " + t1.getText().toString());
                NotificationManagerCompat managerCompat = NotificationManagerCompat.from(getContext());
                managerCompat.notify(999, builder.build());

                Toast.makeText(getActivity(), "Slot Booking Successful", Toast.LENGTH_LONG).show();
                DatabaseReference dr = FirebaseDatabase.getInstance().getReference("usersthird").child(uid);
                dr.removeValue();
                Fragment x = new HomeFragment();
                FragmentTransaction trans = getFragmentManager().beginTransaction();
                trans.replace(R.id.fr1, x);
                trans.addToBackStack(null);
                trans.commit();
                break;
            default:
                break;
        }
    }

//    public void displayNotification(){
//        NotificationCompat.Builder builder =new NotificationCompat.Builder(getActivity(), CHANNEL_ID);
//        builder.setSmallIcon(R.drawable.giftoflifelogo);
//        builder.setContentTitle("Gift of Life");
//        builder.setContentText("Booking confirmed for "+t2.getText().toString()+" on "+t1.getText().toString());
//        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
//
//        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getActivity());
//        notificationManagerCompat.notify(NOTIFICATION_ID, builder.build());
//    }

    //    private void showData(DataSnapshot dataSnapshot) {
//        for (DataSnapshot ds : dataSnapshot.getChildren()) {
//            BB ui = new BB();
//            ui.setName(ds.child(uid).getValue(BB.class).getName());
//            n = ui.getName();
//        }
//    }
}
