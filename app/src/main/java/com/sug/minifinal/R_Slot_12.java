package com.sug.minifinal;


import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class R_Slot_12 extends Fragment {

    private static final int PICK_IMAGE_REQUEST = 1;
    Button b1,b2;
    EditText t1;
    ImageView imageView;
    ProgressBar progressBar;
    Uri mImageUri;
    StorageReference mStorageRef;


    public R_Slot_12() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_r__slot_12, null);

        b1 = view.findViewById(R.id.button11);
        b2 = view.findViewById(R.id.button14);
        b2.setEnabled(false);
        t1 = view.findViewById(R.id.editText);
        imageView = view.findViewById(R.id.imageView2);
        progressBar = view.findViewById(R.id.progressBar);
        mStorageRef = FirebaseStorage.getInstance().getReference("upload");


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadFile();
            }
        });



        return view;
    }

    private String getFileExt(Uri uri){
        ContentResolver cr = getActivity().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return (mimeTypeMap.getExtensionFromMimeType(cr.getType(uri)));
    }

    private void uploadFile() {
        final DatabaseReference dr = FirebaseDatabase.getInstance().getReference("users").child("upload");
        if(imageView != null){
            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()+"."+getFileExt(mImageUri));
            fileReference.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(0);
                        }
                    }, 1000);
                    Toast.makeText(getContext(), "Upload successful", Toast.LENGTH_SHORT).show();
                    Upload upload = new Upload(t1.getText().toString().trim(), taskSnapshot.getMetadata().getReference().getDownloadUrl().toString());
                    String uploadId = dr.push().getKey();
                    dr.child(uploadId).setValue(upload);
                    Fragment x=new R_Slot_1();
                    FragmentTransaction trans=getFragmentManager().beginTransaction();
                    trans.replace(R.id.fr1,x);
                    trans.addToBackStack(null);
                    trans.commit();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0*taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    progressBar.setProgress((int)progress);

                }
            });
        }else
        {
            Toast.makeText(getContext(), "No file selected", Toast.LENGTH_SHORT).show();
        }
    }

    private void openFileChooser() {
        Intent intent =new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
        b2.setEnabled(true);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
            mImageUri = data.getData();
            Picasso.with(getActivity()).load(mImageUri).into(imageView);
        }
    }

}
