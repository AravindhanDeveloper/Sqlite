package com.angler.task;


import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesHelper {
    private static final String USER_PREFERENCES = "userPreferences";
    public static final String PREF_TOKEN = USER_PREFERENCES+".token";
    public static final String PREF_USERID = USER_PREFERENCES+".user_id";

    public static void setPreference(Context context, String mobileNumber, String details) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.putString(mobileNumber, details);
        editor.apply();
    }
    public static void setIntPreference(Context context, String mobileNumber, int details) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.putInt(mobileNumber, details);
        editor.apply();
    }
    private static SharedPreferences.Editor getEditor(Context context) {
        SharedPreferences preferences = getSharedPreferences(context);
        return preferences.edit();
    }
    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE);
    }
    public static String getPreference(Context context, String mobileNumber) {
        SharedPreferences preferences = getSharedPreferences(context);
        return preferences.getString(mobileNumber, "");
    }
    public static int getIntPreference(Context context, String mobileNumber) {
        SharedPreferences preferences = getSharedPreferences(context);
        return preferences.getInt(mobileNumber, 0);
    }

    public static void signOut(Context context) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.remove(USER_PREFERENCES);
        editor.remove(PREF_TOKEN);
        editor.remove(PREF_USERID);
        editor.apply();
        editor.clear();

    }

}

