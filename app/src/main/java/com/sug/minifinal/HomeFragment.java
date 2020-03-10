package com.sug.minifinal;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {

   static String uid, phone, name, email, address, bt, emergency, aadhar;
    public HomeFragment() {
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
        View view = inflater.inflate(R.layout.fragment_home, null);
        Button b = view.findViewById(R.id.button3);
        Button b1 = view.findViewById(R.id.button5);
        Button b2 = view.findViewById(R.id.button6);

        b.setOnClickListener(this);
        b1.setOnClickListener(this);
        b2.setOnClickListener(this);


        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.button3:
                serv();
                break;
            case R.id.button5:
                hosp();
                break;
            case R.id.button6:
                bloodb();
                break;
            default:
                break;
        }
    }

    public void serv()
    {
        Fragment x=new DRFragment();
        FragmentTransaction trans=getFragmentManager().beginTransaction();
        trans.replace(R.id.fr1,x);
        trans.addToBackStack("Home");
        trans.commit();
    }
    public void hosp()
    {
        Uri gmmIntentUri = Uri.parse("geo:0,0?q=Hospitals Near Me");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }
    public void bloodb()
    {
        Uri gmmIntentUri = Uri.parse("geo:0,0?q=blood Banks Near Me");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }


}
