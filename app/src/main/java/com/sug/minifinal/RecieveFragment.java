package com.sug.minifinal;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecieveFragment extends Fragment implements View.OnClickListener{
    private Button b1,b2;
    private EditText  e2;
    private TextView t1, t2, t3, t4;
    private RadioGroup rg;
    private static String uid, name,address,bt;
    BB db;
    DB_new db1;
    public RecieveFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recieve, null);

        rg = view.findViewById(R.id.radiog1);
        b1 = view.findViewById(R.id.button9);
        b2 = view.findViewById(R.id.button12);
        e2 = view.findViewById(R.id.editText4);
        t1 = view.findViewById(R.id.textView17);
        t2 = view.findViewById(R.id.textView14);
        t3 = view.findViewById(R.id.textView15);
        t4 = view.findViewById(R.id.textView16);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.radioButton4){
                    t1.setVisibility(View.VISIBLE);
                    t2.setVisibility(View.VISIBLE);
                    t3.setVisibility(View.VISIBLE);
                    t4.setVisibility(View.VISIBLE);
                    b1.setVisibility(View.VISIBLE);
                    b1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            e2.setVisibility(View.VISIBLE);
                            t4.setVisibility(View.INVISIBLE);
                        }
                    });
                    b2.setVisibility(View.VISIBLE);
                }
                if(checkedId == R.id.radioButton3){
                    Fragment x=new R_Slot_1();
                    FragmentTransaction trans=getFragmentManager().beginTransaction();
                    trans.replace(R.id.fr1,x);
                    trans.addToBackStack(null);
                    trans.commit();
                }
            }
        });

//        FirebaseAuth mAuth = FirebaseAuth.getInstance();
//        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
//        DatabaseReference mRef = firebaseDatabase.getReference();
//        FirebaseUser user = mAuth.getCurrentUser();
//        FirebaseAuth.AuthStateListener authStateListener;
//        uid = user.getUid();
//        authStateListener = new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                FirebaseUser user = firebaseAuth.getCurrentUser();
//                if(user != null){
//                    //Toast.makeText(getApplicationContext(), "Signed in as "+user.getUid(), Toast.LENGTH_LONG).show();
//                }
//                else{
//                    //Toast.makeText(getApplicationContext(), "Signed out ", Toast.LENGTH_LONG).show();
//                }
//            }
//        };
//        mRef.addValueEventListener(new ValueEventListener() {
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
        db = new BB(getContext());
        Cursor r = db.getAllData();
        db1 = new DB_new(getContext());
        Cursor r1 = db1.getAllData();
        r.moveToPosition(getRandomNumberInRange(0,3));
        while (r1.moveToNext()){
            t1.setText("Name - "+r1.getString(0));
            t3.setText("Blood type - "+r1.getString(5));
            t4.setText("Address - "+r1.getString(3));
        }
        t2.setText(r.getString(0));
        b2.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button12:
                recu();
                break;

                default:
                    break;
        }
    }
//    public void showData(DataSnapshot dataSnapshot) {
//        for(DataSnapshot ds : dataSnapshot.getChildren()){
//            DB ui =new DB();
//            ui.setName(ds.child(uid).getValue(DB.class).getName());
//            ui.setAddress(ds.child(uid).getValue(DB.class).getAddress());
//            ui.setBloodtype(ds.child(uid).getValue(DB.class).getBloodtype());
//            name = ui.getName();
//            address = ui.getAddress();
//            bt= ui.getBloodtype();
//        }
//
//    }
    private static int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }
    private void recu() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel =new NotificationChannel("Mynotifications", "Mynotifications", NotificationManager.IMPORTANCE_DEFAULT);

            NotificationManager manager = getActivity().getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);

        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(), "Mynotifications")
                .setContentTitle("Recieve Slot Booking").setSmallIcon(R.drawable.common_google_signin_btn_icon_dark).setAutoCancel(true).setContentText("The required blood will be delivered within 2 days");
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(getContext());
        managerCompat.notify(999, builder.build());
        Fragment x=new R_Slot_12();
        FragmentTransaction trans=getFragmentManager().beginTransaction();
        trans.replace(R.id.fr1,x);
        trans.addToBackStack(null);
        trans.commit();
    }
}
