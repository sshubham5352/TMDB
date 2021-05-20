package com.app.tmdb.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.app.tmdb.R;
import com.app.tmdb.databinding.ActivitySignInBinding;
import com.app.tmdb.dialogs.UserLoginDialog;
import com.app.tmdb.models.MovieResponse;
import com.app.tmdb.retrofit.ApiManager;
import com.app.tmdb.retrofit.ApiResponseInterface;
import com.app.tmdb.utils.Constants;
import com.app.tmdb.utils.Helper;
import com.app.tmdb.utils.SessionManager;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener, ApiResponseInterface, UserLoginDialog.UserLoginInterface {

    //Global Variables
    ActivitySignInBinding binding;
    GoogleSignInClient mGoogleSignInClient;
    CallbackManager callbackManager;
    ProgressDialog progressDialog;
    UserLoginDialog loginDialog;
    List<String> posterPaths;
    private final int RC_GOOGLE_SIGNUP = 10;           //Sign in Request code for google
    private final int RC_GOOGLE_LOGIN = 20;           //login Request code for google
    private final int RC_FACEBOOK = 64206;     //Sign in Request code for facebook

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in);
        getWindow().setNavigationBarColor(getResources().getColor(R.color.colorAppTheme));
        //progress dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        //setting on click listeners
        binding.signInWithGoogle.setOnClickListener(this);
        binding.signInWithFacebook.setOnClickListener(this);
        binding.manualSignIn.setOnClickListener(this);
        binding.login.setOnClickListener(this);

        // Configure sign-in to request the user's ID, email address, and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        //getting trending movie image for the background
        new ApiManager(this, this).getTrendingResponse(Constants.TODAY, 1);
    }

    @Override
    public void apiCallSuccess(Object response, int ServiceCode) {
        if (ServiceCode == Constants.TRENDING_RESPONSE) {
            MovieResponse mResponse = (MovieResponse) response;
            posterPaths = new ArrayList<>();
            int size = mResponse.getResults().size(), i = 0;
            while (i < size) {
                if (mResponse.getResults().get(i).getPoster_path() != null && mResponse.getResults().get(i).getPoster_path().length() != 0)
                    posterPaths.add(mResponse.getResults().get(i).getPoster_path());
                i++;
            }
            setBackgroundImage();
            progressDialog.dismiss();
        }
    }

    private void setBackgroundImage() {
        Timer mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            int position;

            @Override
            public void run() {
                // As timer is not a Main/UI thread need to do all UI task on runOnUiThread
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        position = Helper.randomInt(posterPaths.size());

                        Picasso.get().load(Constants.IMG_BLUETONE_BASE_URL_Portrait + posterPaths.get(position))
                                .placeholder(binding.backgroundImg.getDrawable())
                                .into(binding.backgroundImg, new Callback() {
                                    @Override
                                    public void onSuccess() {
                                        progressDialog.dismiss();
                                    }

                                    @Override
                                    public void onError(Exception e) {
                                        Toast.makeText(SignInActivity.this, "Network error", Toast.LENGTH_SHORT).show();
                                        progressDialog.dismiss();
                                    }
                                });
                    }
                });
            }
        }, 0, 5000);
    }

    @Override
    public void apiCallFailure(String errorCode) {
        Toast.makeText(this, errorCode, Toast.LENGTH_LONG).show();
        progressDialog.dismiss();
    }


    @Override
    protected void onPause() {
        super.onPause();
        progressDialog.dismiss();
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.sign_in_with_google:
                progressDialog.show();
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_GOOGLE_SIGNUP);
                break;
            case R.id.sign_in_with_facebook:
                progressDialog.show();
                LoginManager.getInstance().logInWithReadPermissions(this, Collections.singletonList("public_profile"));
                break;
            case R.id.manual_sign_in:
                startActivity(new Intent(SignInActivity.this, ManualSignInActivity.class));
                break;
            case R.id.login:
                loginDialog = new UserLoginDialog(this, this);
                loginDialog.setContentView();
                loginDialog.show();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RC_GOOGLE_SIGNUP:
                SignInViaGoogle(GoogleSignIn.getSignedInAccountFromIntent(data));
                break;
            case RC_FACEBOOK:
                SignInViaFacebook(requestCode, resultCode, data);
                break;
            case RC_GOOGLE_LOGIN:
                loginViaGoogle(GoogleSignIn.getSignedInAccountFromIntent(data));
                break;

        }
    }

    private void SignInViaGoogle(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            SessionManager.createLoginSessionViaGoogle(this, account.getId(),
                    account.getDisplayName(),
                    account.getEmail(),
                    String.valueOf(account.getPhotoUrl()));
            startMainActivity(account.getDisplayName());
        } catch (ApiException e) {
            if (e.getStatusCode() == 12501)
                Toast.makeText(this, "Permission denied", Toast.LENGTH_LONG).show();
            else if (e.getStatusCode() == 7)
                Toast.makeText(this, "Internet not available", Toast.LENGTH_LONG).show();
            else
                Toast.makeText(this, "signInResult:failed code=" + e.getStatusCode(), Toast.LENGTH_LONG).show();
        }
    }

    private void SignInViaFacebook(int requestCode, int resultCode, Intent data) {
        //creating callbackManager for facebook sign in
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                try {
                                    if (object.has("name")) {
                                        SessionManager.putUserName(SignInActivity.this, object.getString("name"));
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "name");
                request.setParameters(parameters);
                request.executeAsync();

                if (SessionManager.getUserName() == null) {
                    progressDialog.dismiss();
                    Toast.makeText(SignInActivity.this, "network error", Toast.LENGTH_SHORT).show();
                    return;
                }
                SessionManager.createLoginSessionViaFacebook(SignInActivity.this, loginResult.getAccessToken().getUserId());
                startMainActivity(SessionManager.getUserName());
            }

            @Override
            public void onCancel() {
                Toast.makeText(SignInActivity.this, "Permission denied", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException exception) {
                Toast.makeText(SignInActivity.this, "oops something seems wrong", Toast.LENGTH_SHORT).show();
            }
        });
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


    private void loginViaGoogle(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            SessionManager.createLoginSessionViaGoogle(this, account.getId(),
                    account.getDisplayName(),
                    account.getEmail(),
                    String.valueOf(account.getPhotoUrl()));
            /*
             * check whether userID exists or not
             * if exists then createLoginSession and show welcome back text and if not then just show
             * email id does not exists in database.Please sign in*/
            Toast.makeText(this, "Email id doesn't exist in our database. Please sign up", Toast.LENGTH_LONG).show();
        } catch (ApiException e) {
            if (e.getStatusCode() == 12501)
                Toast.makeText(this, "Permission denied", Toast.LENGTH_LONG).show();
            else if (e.getStatusCode() == 7)
                Toast.makeText(this, "Internet not available", Toast.LENGTH_LONG).show();
            else
                Toast.makeText(this, "signInResult:failed code=" + e.getStatusCode(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void loginWithGoogle() {
        progressDialog.show();
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_GOOGLE_LOGIN);
    }

    private void startMainActivity(final String userName) {
        startActivity(new Intent(SignInActivity.this, MainActivity.class));
        overridePendingTransition(R.anim.anim_fade_in, R.anim.anim_fade_out);
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
                Toast.makeText(SignInActivity.this, "Welcome " + userName, Toast.LENGTH_LONG).show();
                finishAffinity();
            }
        }, 800);
    }
}



 /*
  *     Check for existing Google Sign In account, if the user is already signed in the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null) {
            openMainActivity();
            return;
        }

       * checking whether the user is already logged in with facebook
       *  AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken != null && !accessToken.isExpired()) {
            openMainActivity();
            return;
        }
      */
