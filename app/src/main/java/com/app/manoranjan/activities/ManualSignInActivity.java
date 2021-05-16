package com.app.manoranjan.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.app.manoranjan.R;
import com.app.manoranjan.databinding.ActivityManualSignInBinding;
import com.app.manoranjan.utils.Helper;
import com.app.manoranjan.utils.SessionManager;

public class ManualSignInActivity extends AppCompatActivity {
    //class Variables
    ActivityManualSignInBinding binding;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_manual_sign_in);
        getWindow().setNavigationBarColor(getResources().getColor(R.color.colorAppTheme));

        //progress dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
    }

    public void signUpUser(View view) {
        if (Helper.isFieldEmpty(binding.userName) ||
                Helper.isFieldEmpty(binding.userID) ||
                Helper.isFieldEmpty(binding.emailId) ||
                Helper.isFieldEmpty(binding.password) ||
                Helper.isFieldEmpty(binding.confirmPassword))
            return;

        if (Helper.isTextOutOfLimits(binding.userName, "Name", 4, 24) ||
                Helper.isTextOutOfLimits(binding.userID, "user ID", 6, 18) ||
                Helper.isTextOutOfLimits(binding.password, "Password", 4, 12))
            return;

        if (binding.userID.getText().toString().contains(" ")) {
            binding.userID.setError("user ID can't contain spaces");
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(binding.emailId.getText().toString().trim()).matches()) {
            binding.emailId.setError("Enter a valid email id");
            return;
        }

        if (binding.password.getText().toString().contains(" ")) {
            binding.password.setError("Password can't contain spaces");
            return;
        }

        if (!binding.password.getText().toString().matches(binding.confirmPassword.getText().toString())) {
            binding.confirmPassword.setError("Password doesn't match");
            return;
        }

        // if(user id exists)
        // return;
        progressDialog.show();

        SessionManager.createLoginSessionViaManualLogin(this,
                binding.userName.getText().toString().trim(),
                binding.userID.getText().toString().trim(),
                binding.emailId.getText().toString().trim(),
                binding.password.getText().toString().trim());

        startMainActivity(binding.userName.getText().toString());
    }

    public void loginUser(View view) {
    }


    private void startMainActivity(final String userName) {
        Intent intent = new Intent(ManualSignInActivity.this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.anim_fade_in, R.anim.anim_fade_out);
        progressDialog.dismiss();
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(ManualSignInActivity.this, "Welcome " + userName, Toast.LENGTH_LONG).show();
                finishAffinity();
            }
        }, 800);
    }
}
