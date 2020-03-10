package com.sug.minifinal;

import android.Manifest;
import android.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.util.zip.Inflater;

public class MasterActivity extends AppCompatActivity implements D_Slot_1.OnMessageSendListener, R_Slot_1.OnMessageSendListener1 {
    private HomeFragment homeFragment;
    private ProfileFragment profileFragment;
    private FrameLayout r;
    private static final int REQUEST_CALL = 1;
    public static String uid, emergency;
    public  String name, address, aadhar, bt, email, phone;
    public BB db;
    public DB_new db1;
    boolean doubleBackToExitPressedOnce = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master);
        r = findViewById(R.id.fr1);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        uid = user.getUid();
        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference();
        if(!user.isEmailVerified()){
            Toast.makeText(MasterActivity.this, "Email not verified", Toast.LENGTH_SHORT).show();
            mAuth.signOut();
            Intent intent = new Intent(MasterActivity.this, LoginActivity.class);
            startActivity(intent);
        }
        FirebaseAuth.AuthStateListener authStateListener3 = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    //Toast.makeText(getApplicationContext(), "Signed in as "+user.getUid(), Toast.LENGTH_LONG).show();
                }
                else{
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

        Bundle b2 = new Bundle();
        b2.putString("name", name);
        b2.putString("email", email);
        b2.putString("phone", phone);
        b2.putString("address", address);
        b2.putString("bt", bt);
        db = new BB(this);
        boolean x;
        x = db.InsertData("Rotary DG Goenkar Blood Bank", "5", "6", "0", "2", "3", "0", "1", "1");
        if(x == false){
            //Toast.makeText(MasterActivity.this, "Error1", Toast.LENGTH_LONG).show();
        }
        x = db.InsertData("Sanjeevani Blood Bank", "3", "0", "5", "3", "0", "2", "3", "0");
        if(x == false){
            //Toast.makeText(MasterActivity.this, "Error2", Toast.LENGTH_LONG).show();
        }
        x = db.InsertData("Samarpan Blood Bank", "7", "4", "9", "0", "5", "4", "0", "3");
        if(x == false){
            //Toast.makeText(MasterActivity.this, "Error3", Toast.LENGTH_LONG).show();
        }
        x = db.InsertData("Cordlife", "0", "3", "1", "5", "4", "1", "3", "2");
        if(x == false){
            //Toast.makeText(MasterActivity.this, "Error4", Toast.LENGTH_LONG).show();
        }

        db1 = new DB_new(this);


        homeFragment = new HomeFragment();
        profileFragment = new ProfileFragment();
        if(savedInstanceState == null) {
            setFragment(homeFragment);
        }
//        profileFragment.setArguments(b2);
        BottomNavigationView navigation = findViewById(R.id.navigation5);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_CALL){
            if(grantResults.length > 0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){

            }
        }
    }

    public void showData(DataSnapshot dataSnapshot) {
        for(DataSnapshot ds : dataSnapshot.getChildren()){
            DB ui =new DB();
            ui.setName(ds.child(uid).getValue(DB.class).getName());
            ui.setEmail(ds.child(uid).getValue(DB.class).getEmail());
            ui.setPhone(ds.child(uid).getValue(DB.class).getPhone());
            ui.setAddress(ds.child(uid).getValue(DB.class).getAddress());
            ui.setAadhaar(ds.child(uid).getValue(DB.class).getAadhaar());
            ui.setBloodtype(ds.child(uid).getValue(DB.class).getBloodtype());
            ui.setEmergency(ds.child(uid).getValue(DB.class).getEmergency());

            name = ui.getName();
            email = ui.getEmail();
            phone = ui.getPhone();
            aadhar = ui.getAadhaar();
            address = ui.getAddress();
            emergency= ui.getEmergency();
            bt= ui.getBloodtype();
            Cursor r = db1.getAllData();

                boolean x = db1.InsertData(name, email, phone, address, aadhar, bt);
                if (x == false) {
                    //Toast.makeText(MasterActivity.this, "Error5", Toast.LENGTH_LONG).show();

            }
        }

    }
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    setFragment(homeFragment);
                    return true;

                case R.id.navigation_profile:
                    setFragment(profileFragment);
                    return true;

                case R.id.navigation_emer:
                    if(ContextCompat.checkSelfPermission(MasterActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                        ActivityCompat.requestPermissions(MasterActivity.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
                    }else{
                        startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+emergency)));
                    }
                    return true;

            }
            return false;
        }
    };

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fr1, fragment).addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menutop, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.sign:
                db1.deleteData(email);
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(MasterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;

//            case R.id.share:
////                db1 = new DB_new(this);
////                Cursor r = db1.getAllData();
////                StringBuffer sb =new StringBuffer();
////                while (r.moveToNext()){
////                    sb.append(r.getString(0)+"\n"
////                    +r.getString(1)+"\n"
////                            +r.getString(2)+"\n"
////                            +r.getString(3)+"\n"
////                            +r.getString(4)+"\n");
////                }
////                String out = sb.toString();
////                Toast.makeText(getApplicationContext(), out, Toast.LENGTH_LONG).show();
//
//                ApplicationInfo api = getApplicationContext().getApplicationInfo();
//                String apkpath = api.sourceDir;
//
//                Intent intent1= new Intent(Intent.ACTION_SEND);
//                intent1.setType("application/vnd.android.package-archive");
//                intent1.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(apkpath)));
//                startActivity(Intent.createChooser(intent1, "Share App using .. "));
//                break;

                default:
                    break;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            ActivityCompat.finishAffinity(this);
            return;
        }
        super.onBackPressed();
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(MasterActivity.this, "Press Again To Exit", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    @Override
    public void onMessageSend(int index) {
        D_Slot_2 u =new D_Slot_2();
        Bundle z =new Bundle();
        z.putInt("message", index);
        u.setArguments(z);
        setFragment(u);
    }

    @Override
    public void onMessageSend1(int index) {

        R_Slot_2 u =new R_Slot_2();
        Bundle z = new Bundle();
        z.putInt("message", index);
        u.setArguments(z);
        Fragment x = new R_Slot_2();
        FragmentTransaction trans = getSupportFragmentManager().beginTransaction().addToBackStack(null);
        setFragment(u);
    }
}
