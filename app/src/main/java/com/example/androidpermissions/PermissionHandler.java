package com.example.androidpermissions;

import android.content.Context;
import android.content.SharedPreferences;

public class PermissionHandler {

    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public PermissionHandler(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(context.getString(R.string.permission_preferences), Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void updatePermissionPreferences(String permission) {
        switch (permission) {
            case "camera_permission":
                editor.putBoolean(context.getString(R.string.permission_camera), true);
                editor.commit();
                break;

            case "storage_permission":
                editor.putBoolean(context.getString(R.string.permission_storage), true);
                editor.commit();
                break;

            case "contact_permission":
                editor.putBoolean(context.getString(R.string.permission_contacts), true);
                editor.commit();
                break;
        }
    }


    public boolean checkPermissionPrefernces(String permission) {

        boolean b = false;
        switch (permission) {
            case "camera":
                b = sharedPreferences.getBoolean(context.getString(R.string.permission_camera), false);
                break;

            case "storage":
                b = sharedPreferences.getBoolean(context.getString(R.string.permission_storage), false);
                break;

            case "contacts":
                b = sharedPreferences.getBoolean(context.getString(R.string.permission_contacts), false);
                break;
        }
        return b;
    }

}
