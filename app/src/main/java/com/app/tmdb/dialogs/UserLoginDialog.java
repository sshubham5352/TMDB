package com.app.tmdb.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.airbnb.lottie.LottieAnimationView;
import com.app.tmdb.R;
import com.app.tmdb.utils.Helper;

import java.util.Objects;


public class UserLoginDialog extends Dialog {
    //Class Variables
    EditText userID;
    EditText password;
    LottieAnimationView passwordEye;
    TextView forgotPassword;
    TextView login;
    TextView loginWithGoogle;
    UserLoginInterface mInterface;


    public UserLoginDialog(@NonNull Context context, UserLoginInterface userLoginInterface) {
        super(context);
        mInterface = userLoginInterface;
        setContentView();
    }

    public void setContentView() {
        super.setContentView(R.layout.dialog_user_login);
        Objects.requireNonNull(this.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        getWindow().getAttributes().width = LinearLayout.LayoutParams.MATCH_PARENT;

        //Variable Initialization
        userID = findViewById(R.id.user_ID);
        password = findViewById(R.id.password);
        passwordEye = findViewById(R.id.password_eye);
        forgotPassword = findViewById(R.id.forgot_password);
        loginWithGoogle = findViewById(R.id.login_with_google);
        login = findViewById(R.id.login);

        //passwordEye onClickListener
        passwordEye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (passwordEye.getProgress() == 0) {
                    passwordEye.setSpeed(1);
                    passwordEye.playAnimation();
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    password.setSelection(password.getText().length());
                } else if (passwordEye.getProgress() > 0.98) {
                    passwordEye.setSpeed(-1F);
                    passwordEye.playAnimation();
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    password.setSelection(password.getText().length());
                }
            }
        });

        //login onClickListener
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Helper.isFieldEmpty(userID) ||
                        Helper.isFieldEmpty(password))
                    return;
                if (userID.getText().toString().contains(" ")) {
                    userID.setError("user ID can't contain spaces");
                    return;
                }
                if (password.getText().toString().contains(" ")) {
                    password.setError("Password can't contain spaces");
                    return;
                }

                /*
                 * check the credentials whether exists in the database or not
                 * if exists create a session via SessionManager and start HomeActivity
                 * if not then simply show Toast
                 **/
                Toast.makeText(getContext(), "User ID doesn't exist. Please sign up", Toast.LENGTH_LONG).show();
                dismiss();
            }
        });
        //loginWithGoogle onClickListener
        loginWithGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mInterface.loginWithGoogle();
                dismiss();
            }
        });
    }

    public interface UserLoginInterface {
        void loginWithGoogle();
    }
}