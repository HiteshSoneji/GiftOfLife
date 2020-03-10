package com.sug.minifinal;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class RegisterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Spinner s;
    private Button b,b1,b2;
    private EditText e1,e2,e3,e4,e5,e6,e7,e8;
    private static final String TAG = "RegisterActivity";
    private TextView displaydate;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseUser user;
    private ProgressDialog progressDialog;
    String a,vc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        e1 = findViewById(R.id.editText17);
        e2 = findViewById(R.id.editText19);
        e3 = findViewById(R.id.editText15);
        e4 = findViewById(R.id.editText18);
        e5 = findViewById(R.id.editText20);
        e6 = findViewById(R.id.editText21);
        e7 = findViewById(R.id.editText23);
        b2=findViewById(R.id.button17);
        e8 = findViewById(R.id.editOtp);
        displaydate = findViewById(R.id.textView2);
        mAuth = FirebaseAuth.getInstance();
        progressDialog =new ProgressDialog(this);
        displaydate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(RegisterActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, dateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }
        });
        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Log.d(TAG, "onDateSet:date:" + year + "/" + month + "/" + dayOfMonth);
                displaydate = findViewById(R.id.textView2);
                displaydate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
            }
        };

        s = findViewById(R.id.spinner2);
        s.setSelection(0);
        b = findViewById(R.id.button4);
        b1 = findViewById(R.id.button16);
        s.setOnItemSelectedListener(RegisterActivity.this);
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

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(dataAdapter);
        b = findViewById(R.id.button4);
        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(s.getItemAtPosition(position).toString().equals("Select Your Blood Group")){

                }
                else{
                    a = s.getItemAtPosition(position).toString();
                    Toast.makeText(getBaseContext(), a, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(e8.getVisibility() == View.INVISIBLE){
                    e8.setVisibility(View.VISIBLE);
                    PhoneAuthProvider.getInstance().verifyPhoneNumber("+91"+e4.getText().toString(), 60, TimeUnit.SECONDS, RegisterActivity.this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                        @Override
                        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                        }
                        @Override
                        public void onVerificationFailed(FirebaseException e) {
                            Toast.makeText(RegisterActivity.this, "Not verified", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCodeSent(String st, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                            super.onCodeSent(st, forceResendingToken);
                            vc = st;
                            b1.setVisibility(View.INVISIBLE);
                            b2.setVisibility(View.VISIBLE);

                        }

                    });

                }
                else{

                }

            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(vc, e8.getText().toString());
                //Toast.makeText(RegisterActivity.this, credential.getSmsCode()+","+vc, Toast.LENGTH_SHORT).show();
                if(credential.getSmsCode().equals(e8.getText().toString())){
                    Toast.makeText(RegisterActivity.this, "Verified", Toast.LENGTH_SHORT).show();
                    b.setVisibility(View.VISIBLE);
                    b2.setVisibility(View.INVISIBLE);
                    e8.setVisibility(View.INVISIBLE);
                }else{
                    Toast.makeText(RegisterActivity.this, "Wrong OTP", Toast.LENGTH_SHORT).show();
                }
            }
        });
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                e1 = findViewById(R.id.editText17);
                e2 = findViewById(R.id.editText19);
                e3 = findViewById(R.id.editText15);
                e4 = findViewById(R.id.editText18);
                e5 = findViewById(R.id.editText20);
                e6 = findViewById(R.id.editText21);
                e7 = findViewById(R.id.editText23);
                String y = e6.getText().toString();
                boolean result = Verhoeff.validateVerhoeff(y);
                if(result == false){
                    Toast.makeText(RegisterActivity.this, "Entered Aadhaar card number is wrong", Toast.LENGTH_LONG).show();
                    return;
                }
                if(e4.getText().toString().length() != 10 || e7.getText().toString().length() != 10){
                    Toast.makeText(RegisterActivity.this, "Entered Mobile number is wrong", Toast.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(e3.getText().toString())){
                    Toast.makeText(RegisterActivity.this, "Entered Name is empty", Toast.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(e5.getText().toString())){
                    Toast.makeText(RegisterActivity.this, "Entered Address is empty", Toast.LENGTH_LONG).show();
                    return;
                }
                displaydate = findViewById(R.id.textView2);
                if(TextUtils.isEmpty(displaydate.getText().toString())){
                    Toast.makeText(RegisterActivity.this, "Enter Birthdate", Toast.LENGTH_LONG).show();
                    return;
                }
                String email = e1.getText().toString();
                String pass = e2.getText().toString();
                if(TextUtils.isEmpty(email) || TextUtils.isEmpty(pass)){
                    Toast.makeText(RegisterActivity.this, "Email/Password Empty", Toast.LENGTH_LONG).show();
                    return;
                }
                if(a.equals("Select Your Blood Group")){
                    Toast.makeText(RegisterActivity.this, "Select Blood Group", Toast.LENGTH_LONG).show();
                    return;
                }

                progressDialog.setMessage("Registering user");
                progressDialog.show();
                if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(pass)){
                    mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                databaseReference = FirebaseDatabase.getInstance().getReference("users");
                                user = mAuth.getCurrentUser();
                                String id = user.getUid();
                                DB db =new DB(id, e3.getText().toString(), e1.getText().toString(), e5.getText().toString(), e2.getText().toString(),
                                        a, e6.getText().toString(), e4.getText().toString(), e7.getText().toString(), displaydate.getText().toString());
                                databaseReference.child(id).setValue(db);
                                mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){

                                        }
                                        else{
                                            Toast.makeText(RegisterActivity.this,"Unable To Send Verification Mail. Please Try Again",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                                sendtomain();
                            }
                            else {
                                String errormessage = task.getException().getMessage();
                                Toast.makeText(RegisterActivity.this, errormessage, Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentuser = mAuth.getCurrentUser();
        if(currentuser != null)
            sendtomain();
    }

    private void sendtomain() {
        Intent intent =new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
