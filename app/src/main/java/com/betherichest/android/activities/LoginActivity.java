package com.betherichest.android.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.betherichest.android.App;
import com.betherichest.android.R;
import com.betherichest.android.database.DatabaseManager;
import com.betherichest.android.mangers.GUIManager;
import com.betherichest.android.mangers.Game;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;

public class LoginActivity extends AppCompatActivity {
    GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 9001;
    TextView userTextView;
    TextView emailTextView;
    TextView nameCircleTextView;
    Button signOutButton;
    RelativeLayout accountDetailLayout;
    RelativeLayout signInDetailLayout;
    Toast toast;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        userTextView = findViewById(R.id.username);
        emailTextView = findViewById(R.id.email);
        nameCircleTextView = findViewById(R.id.name_circle);
        signOutButton = findViewById(R.id.sign_out_button);
        accountDetailLayout = findViewById(R.id.account_details);
        signInDetailLayout = findViewById(R.id.sign_in_details);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(LoginActivity.this, gso);

        findViewById(R.id.sign_in_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInClick();
            }
        });

        findViewById(R.id.sign_out_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOutClick();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        updateUI(account);
    }

    private void updateUI(GoogleSignInAccount account) {
        if (account == null) {
            accountDetailLayout.setVisibility(View.INVISIBLE);
            signOutButton.setVisibility(View.INVISIBLE);
            signInDetailLayout.setVisibility(View.VISIBLE);
        } else {
            accountDetailLayout.setVisibility(View.VISIBLE);
            signOutButton.setVisibility(View.VISIBLE);
            signInDetailLayout.setVisibility(View.INVISIBLE);
            setProfilePage(account);
        }
    }

    private void setProfilePage(GoogleSignInAccount account) {
        userTextView.setText(account.getDisplayName());
        emailTextView.setText(account.getEmail());
        nameCircleTextView.setText(account.getGivenName().substring(0, 2));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN && resultCode == -1) {
            GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
            updateUI(account);
            showToast("Login Successful!", Toast.LENGTH_SHORT);
        }
    }

    private void showToast(String text, int toastLength) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(App.getContext(), text, toastLength);
        toast.show();
    }

    private void signInClick() {
        if (isNetworkConnected() && isOnline()) {
            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, RC_SIGN_IN);
        }
        else{
            showToast("Please check your internet connection!", Toast.LENGTH_SHORT);
        }
    }

    public void signOutClick() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        showToast("Signed out successfully!", Toast.LENGTH_SHORT);
                        updateUI(null);
                    }
                });
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    public boolean isOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public void onBackPressed() {
        GUIManager.setActivityOpened(false);
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                GUIManager.setActivityOpened(false);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Game.setTimerPaused(false);
    }

    @Override
    protected void onPause() {
        Game.setTimerPaused(true);
        super.onPause();
    }

    @Override
    protected void onStop() {
        DatabaseManager.instance.saveStateToDb();
        super.onStop();
    }
}
