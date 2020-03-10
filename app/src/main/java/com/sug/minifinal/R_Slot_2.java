package com.sug.minifinal;


import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.TimePickerDialog;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.text.TextUtils;
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
public class R_Slot_2 extends Fragment implements View.OnClickListener {
    EditText e1, e2, e3;
    TextView t1, t2;
    Button b;
    DatePickerDialog.OnDateSetListener dateSetListener;
    TimePickerDialog timePickerDialog;
    Calendar calendar;
    int currentHour;
    int currentMinute;
    int day1,year1,month1;
    String amPm;
    BB db;
    DB_new db1;
    public static String bt, t;

    public R_Slot_2() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_r__slot_2, null);
        e1 = view.findViewById(R.id.editText50);
        e2 = view.findViewById(R.id.editText51);
        e2.setEnabled(false);
        e3 = view.findViewById(R.id.editText52);
//        FirebaseAuth mAuth1 = FirebaseAuth.getInstance();
//        FirebaseDatabase firebaseDatabase1 = FirebaseDatabase.getInstance();
//        DatabaseReference mRef1 = firebaseDatabase1.getReference();
//        FirebaseUser user = mAuth1.getCurrentUser();
//        FirebaseAuth.AuthStateListener authStateListener;
//        uid = user.getUid();
//        authStateListener = new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                FirebaseUser user = firebaseAuth.getCurrentUser();
//                if (user != null) {
//                    //Toast.makeText(getApplicationContext(), "Signed in as "+user.getUid(), Toast.LENGTH_LONG).show();
//                } else {
//                    //Toast.makeText(getApplicationContext(), "Signed out ", Toast.LENGTH_LONG).show();
//                }
//            }
//        };
//        mRef1.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                showData(dataSnapshot);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
        t1 = view.findViewById(R.id.tv3);
        t2 = view.findViewById(R.id.tv4);
        b = view.findViewById(R.id.button20);
        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(getContext(), android.R.style.Theme_Holo_Light_Dialog_MinWidth, dateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getDatePicker().setMinDate(System.currentTimeMillis()-100);
                cal.add(Calendar.DAY_OF_MONTH, +10);
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
                        if (day == day1) {
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

        Bundle z = getArguments();
        int i = z.getInt("message");
        db = new BB(getContext());
        Cursor r = db.getAllData();
        r.moveToPosition(i - 1);
        e1.setText(r.getString(0));
        db1 = new DB_new(getContext());
        Cursor r1 = db1.getAllData();
        while (r1.moveToNext()) {
            bt = r1.getString(5);
        }
        if (bt.equals("A +ve")) {
            e2.setText("Available amount - " + r.getString(1));
            t = r.getString(1);
        }
        if (bt.equals("A -ve")) {
            e2.setText("Available amount - " + r.getString(2));
            t = r.getString(2);
        }
        if (bt.equals("B +ve")) {
            e2.setText("Available amount - " + r.getString(3));
            t = r.getString(3);
        }
        if (bt.equals("B -ve")) {
            e2.setText("Available amount - " + r.getString(4));
            t = r.getString(4);
        }
        if (bt.equals("AB +ve")) {
            e2.setText("Available amount - " + r.getString(5));
            t = r.getString(5);
        }
        if (bt.equals("AB -ve")) {
            e2.setText("Available amount - " + r.getString(6));
            t = r.getString(6);
        }
        if (bt.equals("O +ve")) {
            e2.setText("Available amount - " + r.getString(7));
            t = r.getString(7);
        }
        if (bt.equals("O -ve")) {
            e2.setText("Available amount - " + r.getString(8));
            t = r.getString(8);
        }
        b.setOnClickListener(this);
        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button20:
                if (!e3.getText().toString().equals("")) {
                    if ((Integer.parseInt(e3.getText().toString()) > Integer.parseInt(t))) {
                        Toast.makeText(getActivity(), "Required amount not available", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } else {
                    return;
                }
                if (TextUtils.isEmpty(t1.getText().toString()) || TextUtils.isEmpty(t2.getText().toString())) {
                    Toast.makeText(getActivity(), "Date/Time is empty", Toast.LENGTH_LONG).show();
                    return;
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel channel = new NotificationChannel("Mynotifications", "Mynotifications", NotificationManager.IMPORTANCE_DEFAULT);

                    NotificationManager manager = getActivity().getSystemService(NotificationManager.class);
                    manager.createNotificationChannel(channel);

                }
                NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(), "Mynotifications")
                        .setContentTitle("Blood Recieve Slot Booking").setSmallIcon(R.drawable.common_google_signin_btn_icon_dark).setAutoCancel(true).setContentText("Slot Booking Successful for " + t2.getText().toString() + " on " + t1.getText().toString());
                NotificationManagerCompat managerCompat = NotificationManagerCompat.from(getContext());
                managerCompat.notify(999, builder.build());
                Toast.makeText(getActivity(), "Slot Booking Successful", Toast.LENGTH_LONG).show();
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

//    public void showData(DataSnapshot dataSnapshot) {
//        for (DataSnapshot ds : dataSnapshot.getChildren()) {
//            DB ui = new DB();
//            ui.setName(ds.child(uid).getValue(DB.class).getName());
//            ui.setEmail(ds.child(uid).getValue(DB.class).getEmail());
//            ui.setPhone(ds.child(uid).getValue(DB.class).getPhone());
//            ui.setAddress(ds.child(uid).getValue(DB.class).getAddress());
//            ui.setBloodtype(ds.child(uid).getValue(DB.class).getBloodtype());
//            name = ui.getName();
//            email = ui.getEmail();
//            phone = ui.getPhone();
//            address = ui.getAddress();
//            bt = ui.getBloodtype();
//        }
//
//    }
}
