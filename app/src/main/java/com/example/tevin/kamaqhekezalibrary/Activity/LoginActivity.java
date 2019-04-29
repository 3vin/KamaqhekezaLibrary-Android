package com.example.tevin.kamaqhekezalibrary.Activity;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.tevin.kamaqhekezalibrary.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN =101;
    private static final String TAG = "Google_sign_in_attempt";
    private GoogleSignInClient mGoogleSignInClient;
    private static FirebaseAuth mAuth;
    private ProgressBar mProgressBar;

    private static final String USER_OBJECT = "user";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mProgressBar = findViewById(R.id.progressLogin);

        mGoogleSignInClient = GoogleSignIn.getClient(this,gso);
        mAuth = FirebaseAuth.getInstance();
    }

    private void signIn(){
//        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
//        startActivityForResult(signInIntent, RC_SIGN_IN);
        this.startActivity(new Intent(this, MainActivity.class));

    }
    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(mGoogleSignInClient.getSignInIntent());
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                    updateUI(null);
                // ...
            }
        }
    }


    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            Toast.makeText(getApplicationContext(),"Login Successful",Toast.LENGTH_LONG).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);

                            mProgressBar.setProgress(50);
                            //finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(getApplicationContext(),"Oops, Something has gone wrong...\n\t\t\t We could'nt Login.",Toast.LENGTH_LONG).show();
                           // updateUI(null);
                        }

                        // ...
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
       if(user != null){
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.putExtra(USER_OBJECT, user);
        startActivity(intent);
       }
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetworkInfo != null &&
                                activeNetworkInfo.isConnectedOrConnecting();
        if(currentUser != null) {
            updateUI(currentUser);
        }else
            if(!isConnected){
                mProgressBar.setVisibility(View.VISIBLE);
                mProgressBar.animate();
               // Snackbar.make(this.getCurrentFocus().getRootView(),"Eish... it seems that you do not have an Internet Connection, please connect to WIFI or Mobile data and try again.",Snackbar.LENGTH_INDEFINITE);
                //Toast.makeText(this, "Eish... it seems that you do not have an Internet Connection, please connect to WIFI or Mobile data and try again.",Toast.LENGTH_LONG).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Eish... it seems that you do not have an Internet Connection, please connect to WIFI or Mobile data and try again.");

                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                signOut();
                mProgressBar.setVisibility(View.INVISIBLE);
                updateUI(null);
            }
        else{
            updateUI(null);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        /*if(requestCode == RESULT_OK){
            //Successfully signed in
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        }*/
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

            if(result.isSuccess()) {
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
                mProgressBar.setProgress(100);
                mProgressBar.setVisibility(View.INVISIBLE);
            }else{
                mProgressBar.setProgress(0);
                mProgressBar.setVisibility(View.INVISIBLE);
            }
        }
    }


    public void onClick(View view){

            signIn();
            mProgressBar.setVisibility(View.VISIBLE);
            mProgressBar.animate();
        //startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }

    public static void signOut(){
        //FirebaseAuth.getInstance().signOut();
        mAuth.signOut();
    }
}
