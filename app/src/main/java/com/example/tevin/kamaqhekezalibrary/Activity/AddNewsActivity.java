package com.example.tevin.kamaqhekezalibrary.Activity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.tevin.kamaqhekezalibrary.R;
import com.example.tevin.kamaqhekezalibrary.POJO.Upload;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

public class AddNewsActivity extends AppCompatActivity {

    private static final int PICK_IMAGE = 100;
    private DatabaseReference mDatabaseRef;
    private StorageReference mStorageRef;
    private ImageView ivAddImage;
    private Button btAddImage;
    private Button btUpload;
    private Uri mImageUri;
    private ProgressBar mProgressBar;
    private StorageTask mUploadTask;
    private EditText etTitle;
    private EditText etFullStory;
    private String url="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_news);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("News");
        mStorageRef = FirebaseStorage.getInstance().getReference("Images");

        ivAddImage = findViewById(R.id.ivUpload);
        btAddImage = findViewById(R.id.btAddStory);
        btUpload = findViewById(R.id.btUpload);
        mProgressBar = findViewById(R.id.progressUpload);
        mProgressBar.setVisibility(View.INVISIBLE);

        btAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
    }

    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            mImageUri = data.getData();
            ivAddImage.setImageURI(mImageUri);
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile() {

        mProgressBar.setVisibility(View.VISIBLE);
        btUpload.setEnabled(false);
        if (mImageUri != null) {
            final StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                    + "." + getFileExtension(mImageUri));

            mUploadTask = fileReference.putFile(mImageUri)
                    .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull final Task<UploadTask.TaskSnapshot> task) {
                        task.getResult().getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                if(task.isSuccessful()){

                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            mProgressBar.setProgress(0);
                                        }
                                    }, 500);

                                    Upload upload = new Upload(etTitle.getText().toString().trim(),
                                            uri.toString(),etFullStory.getText().toString());
                                    String uploadId = mDatabaseRef.push().getKey();
                                    mDatabaseRef.child(uploadId).setValue(upload);

                                    mProgressBar.setVisibility(View.INVISIBLE);
                                    Toast.makeText(AddNewsActivity.this, "Upload successful", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(AddNewsActivity.this, MainActivity.class);
                                    startActivity(intent);
                                }
                            }
                        });
                    }
                     })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddNewsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            mProgressBar.setProgress((int) progress);
                        }
                    });
        } else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }

    public void onUpload(View view) {
        etTitle = findViewById(R.id.etAddStoryTitle);
        etFullStory = findViewById(R.id.etFullStory);

        String title, story;
        title = etTitle.getText().toString();
        story = etFullStory.getText().toString();

        if(!title.equals("") && !story.equals("")) {
            btAddImage.setEnabled(false);
            uploadFile();
        }else {
            Snackbar.make(view, "Please fill in all fields.", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }

    }

}
