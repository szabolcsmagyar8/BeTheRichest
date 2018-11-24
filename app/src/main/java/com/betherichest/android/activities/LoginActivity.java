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

import com.betherichest.android.ActionType;
import com.betherichest.android.App;
import com.betherichest.android.R;
import com.betherichest.android.database.DatabaseManager;
import com.betherichest.android.mangers.GUIManager;
import com.betherichest.android.mangers.Game;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private TextView userTextView;
    private TextView emailTextView;
    private TextView nameCircleTextView;
    private Button signOutButton;
    private RelativeLayout accountDetailLayout;
    private RelativeLayout signInDetailLayout;

    private GoogleSignInClient mGoogleSignInClient;
    public static GoogleSignInAccount account;

    private static final int RC_SIGN_IN = 9001;
    public static String BEARER_TOKEN;

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
                .requestIdToken(getString(R.string.server_client_id))
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

        account = GoogleSignIn.getLastSignedInAccount(this);
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
        nameCircleTextView.setText(account.getDisplayName().substring(0, 2));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            account = completedTask.getResult(ApiException.class);

            String idToken = account.getIdToken();

            Map<String, Object> requestParams = new HashMap<>();
            requestParams.put("idToken", idToken);

            App.createConnection("/muser/tokensignin", requestParams, ActionType.LOGIN);

            updateUI(account);
            GUIManager.showToast(R.string.login_successful);
        } catch (ApiException e) {
            updateUI(null);
            GUIManager.showToast(R.string.login_failed);
        }
    }


    private void signInClick() {
        if (isNetworkConnected() && App.isOnline()) {
            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, RC_SIGN_IN);
        } else {
            GUIManager.showToast(R.string.check_net_connection);
        }
    }

    private void signOutClick() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        account = null;
                        GUIManager.showToast(R.string.signed_out);
                        updateUI(account);
                    }
                });
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
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
