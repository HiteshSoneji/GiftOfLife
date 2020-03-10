package com.sug.minifinal;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class D_Slot_1 extends Fragment implements View.OnClickListener {

    CardView c1, c2, c3, c4;
    Button b1, b2, b3, b4;
    public static final String TAG = "DSLOT1";

    public D_Slot_1() {
        // Required empty public constructor
    }
    OnMessageSendListener onMessageSendListener;
    public interface OnMessageSendListener{
        public void onMessageSend(int index);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_d__slot_1, null);
        c1 = view.findViewById(R.id.card_view1);
        c2 = view.findViewById(R.id.card_view2);
        c3 = view.findViewById(R.id.card_view3);
        c4 = view.findViewById(R.id.card_view4);
        b1 = view.findViewById(R.id.bbb1);
        b2 = view.findViewById(R.id.bbb2);
        b3 = view.findViewById(R.id.bbb3);
        b4 = view.findViewById(R.id.bbb4);
        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                stdb(1);

            }
        });
        c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                stdb(2);
            }
        });
        c3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                stdb(3);
            }
        });
        c4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                stdb(4);
            }
        });

        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
        b4.setOnClickListener(this);

        return view;
    }

    private void stdb(int i) {
//        FirebaseAuth mAuth;
//        FirebaseDatabase firebaseDatabase;
//        DatabaseReference databaseReference;
//        FirebaseUser user;
//        mAuth = FirebaseAuth.getInstance();
//        firebaseDatabase = FirebaseDatabase.getInstance();
//        databaseReference = firebaseDatabase.getReference("selectedBB");
//        user = mAuth.getCurrentUser();
//        String id = user.getUid();
//        String name = c.toString();
//
//        BB y = new BB(name, id);
//        databaseReference.child(id).setValue(y);
        //Bundle z = new Bundle();
        //z.putInt("k", i);

        onMessageSendListener.onMessageSend(i);
//        Fragment x=new D_Slot_2();
//        FragmentTransaction trans=getFragmentManager().beginTransaction();
//        trans.replace(R.id.fr1,x);
//        trans.addToBackStack(null);
//        trans.commit();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bbb1:
                ubb1();
                break;
            case R.id.bbb2:
                ubb2();
                break;
            case R.id.bbb3:
                ubb3();
                break;
            case R.id.bbb4:
                ubb4();
                break;
            default:
                break;
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity=(Activity) context;
        try {
            onMessageSendListener = (OnMessageSendListener) activity;
        }catch (ClassCastException e){
            throw new ClassCastException(activity.toString());
        }

    }
    public void ubb1() {
        goToUrl("http://rcbw.org/blood-bank/");
    }

    public void ubb2() {
        goToUrl("http://www.sanjeevanihospital.com/");
    }

    public void ubb3() {
        goToUrl("http://www.samarpanbloodbank.org/");
    }

    public void ubb4() {
        goToUrl("http://www.cordlifeindia.com/");
    }

    private void goToUrl(String url) {
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }


}
