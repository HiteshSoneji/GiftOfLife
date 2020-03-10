package com.sug.minifinal;


import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ThirdDonor extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    EditText e1, e2, e3, e4;
    String a;
    Button b;
    Spinner s;
    TextView displaydate;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private static final String TAG = "ThirdActivity";

    public ThirdDonor() {
        // Required empty public constructor
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_third_donor, null);
        e1 = view.findViewById(R.id.editText8);
        e2 = view.findViewById(R.id.editText9);
        e3 = view.findViewById(R.id.editText10);
        e4 = view.findViewById(R.id.editText11);
        b = view.findViewById(R.id.button1);
        s = view.findViewById(R.id.spinner3);
        s.setSelection(0);
        displaydate = view.findViewById(R.id.textView10);
        displaydate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(getContext(), android.R.style.Theme_Holo_Light_Dialog_MinWidth, dateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                displaydate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
            }
        };

        s.setOnItemSelectedListener(this);
        List<String> categories = new ArrayList<String>();
        categories.add("Select Your Blood Group");
        categories.add("A +ve");
        categories.add("A -ve");
        categories.add("B +ve");
        categories.add("B -ve");
        categories.add("O +ve");
        categories.add("O -ve");
        categories.add("AB +ve");
        categories.add("AB -ve");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(dataAdapter);
        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (s.getItemAtPosition(position).toString().equals("Select Your Blood Group")) {

                } else {
                    a = s.getItemAtPosition(position).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        b.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                DatabaseReference databaseReference = firebaseDatabase.getReference("users").child("usersthird");
                FirebaseUser user = mAuth.getCurrentUser();
                String id = user.getUid();
                if (!TextUtils.isEmpty(e1.getText().toString()) && !TextUtils.isEmpty(e2.getText().toString()) &&
                        !TextUtils.isEmpty(id) && !TextUtils.isEmpty(displaydate.getText().toString()) && !TextUtils.isEmpty(e4.getText().toString())
                        && !TextUtils.isEmpty(e3.getText().toString()) && !TextUtils.isEmpty(a)) {
                    String y = e4.getText().toString();
                    boolean result = Verhoeff.validateVerhoeff(y);
                    if (result == false) {
                        Toast.makeText(getActivity(), "Entered Aadhaar card number is wrong", Toast.LENGTH_LONG).show();
                        return;
                    } else {
                        DB3 db = new DB3(e1.getText().toString(), e2.getText().toString(), a, id, displaydate.getText().toString(), e4.getText().toString(), e3.getText().toString());
                        databaseReference.child(id).setValue(db);
                        dont();
                    }
                } else {
                    Toast.makeText(getActivity(), "Fields empty", Toast.LENGTH_LONG).show();
                    return;
                }
                break;
            default:
                break;
        }
    }

    private void dontt() {

    }

    private void dont() {
        Fragment x = new D_Slot_1();
        FragmentTransaction trans = getFragmentManager().beginTransaction();
        trans.replace(R.id.fr1, x);
        trans.addToBackStack(null);
        trans.commit();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
