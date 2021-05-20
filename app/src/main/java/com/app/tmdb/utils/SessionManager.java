package com.app.tmdb.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.app.tmdb.activities.SignInActivity;

import java.util.HashMap;

public class SessionManager {

    //Class Variables
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;
    public static final int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "com.app.manoranjan.user";
    // All Shared Preferences Keys
    private static final String KEY_IS_LOGGED_IN = "IsLoggedIn";
    private static final String KEY_LOGGED_IN_VIA = "LoggedInVia";
    private static final String KEY_USER_ID = "UserID";
    public static final String KEY_NAME = "UserName";   // User name (make variable public to access from outside)
    public static final String KEY_EMAIL = "UserEmail"; // Email address (make variable public to access from outside)
    private static final String KEY_PASSWORD = "userPassword";
    public static final String KEY_PROFILE_PIC_URL = "UserProfileURL";

    // Constructor
    private SessionManager() {
        //empty private constructor to make it a singleton class
    }

    private static void getSharedPreferences(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
        editor.apply();
    }

    public static void createLoginSessionViaGoogle(Context context, String userId, String name, String email, String profileURL) {
        if (sharedPreferences == null)
            getSharedPreferences(context);

        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.putInt(KEY_LOGGED_IN_VIA, Constants.LOGGED_IN_VIA_GOOGLE);
        editor.putString(KEY_USER_ID, userId);
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_PROFILE_PIC_URL, profileURL);
        // commit changes
        editor.apply();
    }

    public static void createLoginSessionViaFacebook(Context context, String userId) {
        if (sharedPreferences == null)
            getSharedPreferences(context);

        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.putInt(KEY_LOGGED_IN_VIA, Constants.LOGGED_IN_VIA_FACEBOOK);
        editor.putString(KEY_USER_ID, userId);
        // commit changes
        editor.apply();
    }

    public static void createLoginSessionViaManualLogin(Context context, String name, String userID, String emailID, String password) {
        if (sharedPreferences == null)
            getSharedPreferences(context);

        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.putInt(KEY_LOGGED_IN_VIA, Constants.LOGGED_IN_VIA_MANUAL_LOGIN);
        editor.putString(KEY_USER_ID, userID);
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_EMAIL, emailID);
        editor.putString(KEY_PASSWORD, password);
        // commit changes
        editor.apply();
    }


    public static void putUserName(Context context, String userName) {
        getSharedPreferences(context);
        editor.putString(KEY_NAME, userName);
        editor.apply();
    }

    //      Get stored session data
    public static HashMap<String, String> getUserDetails() {
        HashMap<String, String> userData = new HashMap<>();
        userData.put(KEY_USER_ID, sharedPreferences.getString(KEY_USER_ID, null));
        userData.put(KEY_NAME, sharedPreferences.getString(KEY_NAME, null));
        userData.put(KEY_EMAIL, sharedPreferences.getString(KEY_EMAIL, null));
        userData.put(KEY_PROFILE_PIC_URL, sharedPreferences.getString(KEY_PROFILE_PIC_URL, null));
        return userData;
    }

    public static boolean isLoggedIn(Context context) {
        if (sharedPreferences == null)
            getSharedPreferences(context);
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public static int loggedInVia(Context context) {
        if (isLoggedIn(context)) {
            return sharedPreferences.getInt(KEY_LOGGED_IN_VIA, 0);
        } else return 0;
    }

    public static String getUserID() {
        return sharedPreferences.getString(KEY_USER_ID, null);
    }

    public static String getUserName() {
        return sharedPreferences.getString(KEY_NAME, null);
    }

    public static String getEmail() {
        return sharedPreferences.getString(KEY_EMAIL, null);
    }

    public static String getProfilePicURL() {
        return sharedPreferences.getString(KEY_PROFILE_PIC_URL, null);
    }


    /**
     * Clear session details
     */
    public static void logoutUser() {
        // Clearing all data from Shared Preferences
        try {
            editor.clear();
            editor.commit();
        } catch (Exception e) {

        }
    }

    private static void redirectToSignInActivity(Context context) {
        Intent intent = new Intent(context, SignInActivity.class);
        // Closing all the Activities
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // Add new Flag to start new Activity
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // Staring Login Activity
        context.startActivity(intent);
    }
}
