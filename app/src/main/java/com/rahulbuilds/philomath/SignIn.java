package com.rahulbuilds.philomath;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;



public class SignIn extends AppCompatActivity {
    private static final String TAG = "SignInActivity";
    private FirebaseAuth.AuthStateListener mAuthlistener;
    public static final String SHARED_PREFS = "sharedPrefss";
    public static final String KEY_NAME = "keyname";
    public static final String KEY_MAIL = "keymail";
    private SignInButton mSignInButton;
    private FirebaseAuth mFirebaseAuth;
    public static String username;
    public static String umail;
    public static String url;
    private FirebaseAuth mAuth;
    public FirebaseUser currentUser;
    private SignInButton signInButton;
    private static final int RC_SIGN_IN = 1;
    private GoogleApiClient mGoogleapiclient;

    private GoogleApiClient mGoogleApiClient;

    // Firebase instance variables

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        mFirebaseAuth = FirebaseAuth.getInstance();
        mAuth = FirebaseAuth.getInstance();
        // Assign fields
        mAuthlistener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {
                    currentUser = mAuth.getCurrentUser();
                    if(currentUser != null){
                        username = currentUser.getDisplayName();
                        umail = currentUser.getEmail();
                        url = currentUser.getPhotoUrl().toString();
                        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString(KEY_NAME, username);
                        editor.apply();

                    }
                    Intent intent = new Intent(SignIn.this,splash.class);
                    startActivity(intent);
                    finish();
                }
            }
        };
        signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleapiclient = new GoogleApiClient.Builder(getApplicationContext())
                .enableAutoManage(SignIn.this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Toast.makeText(SignIn.this, "sorry connection failed!!! Try again", Toast.LENGTH_SHORT);
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });


    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        currentUser = mAuth.getCurrentUser();
        mAuth.addAuthStateListener(mAuthlistener);

    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleapiclient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else { Toast.makeText(SignIn.this, "SORRY LOGIN FAILED TRY AGAIN", Toast.LENGTH_LONG).show();
                Log.d("Failed:",result.getStatus().toString());
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "Sign In" + task.isSuccessful());
                        if (!task.isSuccessful()) {
                            Toast.makeText(SignIn.this, "SORRY LOGIN FAILED TRY AGAIN" + task.getException(), Toast.LENGTH_LONG).show();

                        }

                        // ...
                    }
                });

    }
}
