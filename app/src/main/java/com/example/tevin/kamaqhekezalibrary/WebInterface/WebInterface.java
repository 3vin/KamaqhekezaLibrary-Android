package com.example.tevin.kamaqhekezalibrary.WebInterface;

import android.content.Context;
import android.support.annotation.NonNull;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class WebInterface {
    private Context mContext;
    private DatabaseReference  mDatabaseRef = FirebaseDatabase.getInstance().getReference("Suggestions");

    public WebInterface(Context mContext) {
        this.mContext = mContext;
    }

    @JavascriptInterface
    public void alert(String alert){
        Toast.makeText(mContext,alert,Toast.LENGTH_SHORT).show();
    }

    @JavascriptInterface
    public void saveSuggestion(String suggestion, final String message){
        String uploadId = mDatabaseRef.push().getKey();
        mDatabaseRef.child(uploadId).setValue(suggestion).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                alert(message);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                alert("Oop, Something went wrong.");
            }
        });
    }
}
