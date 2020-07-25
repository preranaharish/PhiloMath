package com.rahuldevelops.philomathapp;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.rahuldevelops.philomathapp.Manifest;
import com.rahuldevelops.philomathapp.R;


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
    private static FirebaseAuth mAuth;
    public FirebaseUser currentUser;
    private SignInButton signInButton;
    private Button sigin;
    private static final int RC_SIGN_IN = 1;
    private GoogleApiClient mGoogleapiclient;

    private GoogleApiClient mGoogleApiClient;
    ImageView iv;
    TextView philomath,welcome;

    // Firebase instance variables

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        iv =(ImageView)findViewById(R.id.logo_image);
        philomath=(TextView)findViewById(R.id.philomath);
        welcome=(TextView)findViewById(R.id.welcome);
        welcome.setAlpha(0);
        handleanimation1(philomath);
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
                        editor.putString("Email",umail);
                        editor.putString("url",url);
                        editor.apply();

                    }
                    SharedPreferences prefs = getSharedPreferences("guide", MODE_PRIVATE);
                    int guidedone=prefs.getInt("guide",0);
                    if(guidedone==1){
                    Intent intent = new Intent(SignIn.this, splash.class);
                    startActivity(intent);
                    finish();
                    }
                    else{
                        Intent intent = new Intent(SignIn.this, Guidescreen.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        };
        sigin = (Button) findViewById(R.id.sign_in_button);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestId()
                .requestIdToken("62767728397-r1n46d335nppbu24olhp7j4r35lrpveo.apps.googleusercontent.com")
                .requestScopes(new Scope(Scopes.PLUS_LOGIN))
                .requestScopes(new Scope(Scopes.PLUS_ME))
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
        sigin.setOnClickListener(new View.OnClickListener() {
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
    public static void signOut() {
        // Firebase sign out
        mAuth.signOut();
        FirebaseAuth.getInstance().signOut();
        // Google sign out

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
                Toast.makeText(this,"Loading please wait",Toast.LENGTH_SHORT).show();
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
                mAuth.sendPasswordResetEmail( account.getEmail());

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
    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            return true;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            //resume tasks needing this permission
        }
    }

    public void handleanimation1(View view){
        welcome.animate().alpha(0).setDuration(1000).setInterpolator(new DecelerateInterpolator()).withEndAction(new Runnable() {
            @Override
            public void run() {
                welcome.animate().alpha(1).setDuration(1000).setInterpolator(new AccelerateInterpolator()).start();

            }
        }).start();

    }
}
