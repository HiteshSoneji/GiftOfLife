package com.sug.minifinal;


import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    //private String uid, phone, name, email, address, bt, emergency, aadhar;
    TextView t1, t2, t3, t4, t5;
    EditText e;
    public DB_new db1;

    public ProfileFragment() {
        // Required empty public constructor
    }

//    public void getdata(String uid, String phone, String name, String email, String address, String bt, String emergency, String aadhar) {
//        this.uid = uid;
//        this.phone = phone;
//        this.name = name;
//        this.email = email;
//        this.address = address;
//        this.bt = bt;
//        this.emergency = emergency;
//        this.aadhar = aadhar;
//    }





    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, null);

        t1 = view.findViewById(R.id.textView3);
        t2 = view.findViewById(R.id.textView4);
        t3 = view.findViewById(R.id.textView5);
        t4 = view.findViewById(R.id.textView6);
        t5 = view.findViewById(R.id.textView7);
//        progressDialog =new ProgressDialog(getActivity());
//        FirebaseAuth mAuth = FirebaseAuth.getInstance();
//        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
//        DatabaseReference mRef = firebaseDatabase.getReference();
//        progressDialog.setMessage("Fetching data");
//        progressDialog.show();
//        FirebaseDatabase.getInstance().getReference().keepSynced(true);
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
//
//        mRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                showData(dataSnapshot);
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });


        db1 =new DB_new(getContext());
        Cursor r = db1.getAllData();
        while(r.moveToNext()) {
            t1.setText("Name : "+r.getString(0));
            t2.setText("email id : "+r.getString(1));
            t3.setText("Mobile Number : "+r.getString(2));
            t4.setText("Blood group : "+r.getString(5));
            t5.setText("Address : "+r.getString(3));
        }
        return view;
    }

//    public void showData(DataSnapshot dataSnapshot) {
//        for(DataSnapshot ds : dataSnapshot.getChildren()){
//            DB ui =new DB();
//            ui.setName(ds.child(uid).getValue(DB.class).getName());
//            ui.setEmail(ds.child(uid).getValue(DB.class).getEmail());
//            ui.setPhone(ds.child(uid).getValue(DB.class).getPhone());
//            ui.setAddress(ds.child(uid).getValue(DB.class).getAddress());
//            ui.setBloodtype(ds.child(uid).getValue(DB.class).getBloodtype());
//            name = ui.getName();
//            email = ui.getEmail();
//            phone = ui.getPhone();
//            address = ui.getAddress();
//            bt= ui.getBloodtype();
//        }
//
//    }
}
