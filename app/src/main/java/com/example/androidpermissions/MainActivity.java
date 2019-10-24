package com.example.androidpermissions;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CAMERA = 1;
    private static final int REQUEST_STORAGE = 2;
    private static final int REQUEST_CONTACTS = 3;
    private static final int REQUEST_GROUP_PERMISSION = 4;

    private static final int CAMERA = 1;
    private static final int STORAGE = 2;
    private static final int CONTACT = 3;

    private PermissionHandler permissionHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        permissionHandler = new PermissionHandler(this);

    }

    private int checkPermission(int permission) {
        int permissionStatus = PackageManager.PERMISSION_DENIED;
        switch (permission) {
            case CAMERA:
                permissionStatus = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
                break;

            case STORAGE:
                permissionStatus = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                break;

            case CONTACT:
                permissionStatus = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS);
                break;
        }
        return permissionStatus;
    }

    // Request New Permission
    private void requestPermission(int permission) {
        switch (permission) {
            case CAMERA:
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA);
                break;

            case STORAGE:
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_STORAGE);
                break;

            case CONTACT:
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_CONTACTS}, REQUEST_CONTACTS);
                break;
        }
    }

    // display permission explanation

    private void showPermissionExplanation(final int permission) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        if (permission == CAMERA) {
            builder.setMessage("Camera permission need... Please allow.!");
            builder.setTitle("Camera Permission Needed");
        } else if (permission == CONTACT) {
            builder.setMessage("Contact permission need... Please allow.!");
            builder.setTitle("Contacts Permission Needed");
        } else if (permission == STORAGE) {
            builder.setMessage("Storage permission need... Please allow.!");
            builder.setTitle("Storage Permission Needed");
        }

        builder.setPositiveButton("Allow", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (permission == CAMERA)
                    requestPermission(CAMERA);

                else if (permission == STORAGE)
                    requestPermission(STORAGE);

                else if (permission == CONTACT)
                    requestPermission(CONTACT);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    // group permission request
    private void requestGroupPermissions(ArrayList<String> permission) {
        String[] permissionList = new String[permission.size()];
        permission.toArray(permissionList);
        ActivityCompat.requestPermissions(MainActivity.this, permissionList, REQUEST_GROUP_PERMISSION);
    }


    //*************************************************************
    // Camera Button Method start
    public void cameraPermissionMethod(View view) {

        if (checkPermission(CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.CAMERA)) {
                showPermissionExplanation(CAMERA);
            } else if (!permissionHandler.checkPermissionPrefernces("camera_permission")) {
                requestPermission(CAMERA);
                permissionHandler.updatePermissionPreferences("camera_permission");
            } else {
                Toast.makeText(this, "Allow camera permission in your app setting", Toast.LENGTH_LONG).show();
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", this.getPackageName(), null);
                intent.setData(uri);
                this.startActivity(intent);
            }
        } else {
            Toast.makeText(this, "Camera permission success", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(MainActivity.this, ResultActivity.class);
            intent.putExtra("result", "Camera permission success");
            startActivity(intent);
        }
    }

    // Camera Button Method end


    //*************************************************************
    // Storage Button Method
    public void storagePermission(View view) {
        if (checkPermission(STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                showPermissionExplanation(STORAGE);
            } else if (!permissionHandler.checkPermissionPrefernces("storage_permission")) {
                requestPermission(STORAGE);
                permissionHandler.updatePermissionPreferences("storage_permission");
            } else {
                Toast.makeText(this, "Allow storage permission in your app setting", Toast.LENGTH_LONG).show();
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", this.getPackageName(), null);
                intent.setData(uri);
                this.startActivity(intent);
            }
        } else {
            Toast.makeText(this, "Storage permission success", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(MainActivity.this, ResultActivity.class);
            intent.putExtra("result", "Storage permission success");
            startActivity(intent);
        }
    }
    // Storage Button Method


    //*************************************************************
    // Contact Button Method
    public void contactPermission(View view) {
        if (checkPermission(CONTACT) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.READ_CONTACTS)) {
                showPermissionExplanation(CONTACT);
            } else if (!permissionHandler.checkPermissionPrefernces("contact_permission")) {
                requestPermission(CONTACT);
                permissionHandler.updatePermissionPreferences("contact_permission");
            } else {
                Toast.makeText(this, "Allow contact permission in your app setting", Toast.LENGTH_LONG).show();
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", this.getPackageName(), null);
                intent.setData(uri);
                this.startActivity(intent);
            }
        } else {
            Toast.makeText(this, "Contact permission success", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(MainActivity.this, ResultActivity.class);
            intent.putExtra("result", "Contact permission success");
            startActivity(intent);
        }
    }
    // Contact Button Method


    //*************************************************************
    // Group Permission Button Method start
    public void allPermission(View view) {
        ArrayList<String> arrayList = new ArrayList<>();
        ArrayList<String> permissionIsAvailable = new ArrayList<>();
        permissionIsAvailable.add(Manifest.permission.READ_CONTACTS);
        permissionIsAvailable.add(Manifest.permission.CAMERA);
        permissionIsAvailable.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);

        for (String permission : permissionIsAvailable) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED)
                arrayList.add(permission);
        }
        requestGroupPermissions(arrayList);
    }
    // Group Permission Button Method end


}

