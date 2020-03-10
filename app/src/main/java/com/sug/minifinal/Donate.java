package com.sug.minifinal;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class Donate extends Fragment implements View.OnClickListener {
    RadioGroup rg;
    EditText t1, t2, t3, t4, t5;
    Button b;

    String name, email, address, phone, aadhar, uid;

    public Donate() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_donate, null);
        rg = view.findViewById(R.id.radiog);
        t1 = view.findViewById(R.id.editText22);
        t2 = view.findViewById(R.id.editText25);
        t3 = view.findViewById(R.id.editText26);
        t4 = view.findViewById(R.id.editText27);
        t5 = view.findViewById(R.id.editText28);
        b = view.findViewById(R.id.button10);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mRef = firebaseDatabase.getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        FirebaseAuth.AuthStateListener authStateListener;
        uid = user.getUid();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    //Toast.makeText(getApplicationContext(), "Signed in as "+user.getUid(), Toast.LENGTH_LONG).show();
                } else {
                    //Toast.makeText(getApplicationContext(), "Signed out ", Toast.LENGTH_LONG).show();
                }
            }
        };
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showData(dataSnapshot);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioButton2) {
                    t1.setVisibility(View.VISIBLE);
                    t2.setVisibility(View.VISIBLE);
                    t3.setVisibility(View.VISIBLE);
                    t4.setVisibility(View.VISIBLE);
                    t5.setVisibility(View.VISIBLE);
                    b.setVisibility(View.VISIBLE);
                    t1.setText(name);
                    t2.setText(email);
                    t3.setText(phone);
                    t4.setText(address);
                }
                if (checkedId == R.id.radioButton) {
                    donuser();
                }
            }
        });

        b.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button10:
                don();
                break;
            default:
                break;
        }
    }

    public void showData(DataSnapshot dataSnapshot) {
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            DB ui = new DB();
            ui.setName(ds.child(uid).getValue(DB.class).getName());
            ui.setEmail(ds.child(uid).getValue(DB.class).getEmail());
            ui.setPhone(ds.child(uid).getValue(DB.class).getPhone());
            ui.setAddress(ds.child(uid).getValue(DB.class).getAddress());
            ui.setBloodtype(ds.child(uid).getValue(DB.class).getBloodtype());

            name = ui.getName();
            email = ui.getEmail();
            phone = ui.getPhone();
            address = ui.getAddress();
            aadhar = ui.getAadhaar();
        }

    }

    public void don() {
        if (!t5.getText().toString().equals("")) {
            if (Integer.parseInt(t5.getText().toString()) < 3) {
                Toast.makeText(getActivity(), "Cannot donate as last donated is within 3 months", Toast.LENGTH_LONG).show();
                Fragment x = new HomeFragment();
                FragmentTransaction trans = getFragmentManager().beginTransaction();
                trans.replace(R.id.fr1, x);
                trans.addToBackStack(null);
                trans.commit();
                return;
            }
        }
        else{
            return;
        }
        Fragment x = new D_Slot_1();
        FragmentTransaction trans = getFragmentManager().beginTransaction();
        trans.replace(R.id.fr1, x);
        trans.addToBackStack(null);
        trans.commit();
    }

    public void donuser() {
        Fragment x = new ThirdDonor();
        FragmentTransaction trans = getFragmentManager().beginTransaction();
        trans.replace(R.id.fr1, x);
        trans.addToBackStack(null);
        trans.commit();
    }
}
