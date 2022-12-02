package com.sinhvien.kinny;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.Toast;

public class AlertConFirmLogOut {

    public static void showAlertDialog(final Context context)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        //set tille and message
        builder.setTitle("Confirmation").setMessage("Do you want to log out?");

        builder.setCancelable(true);

        //Create "yes" button
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Toast.makeText(context,"You choose Yes button",
                        Toast.LENGTH_SHORT).show();
                Activity activity = (Activity) context;
                activity.finish();
            }
        });
        // Create "No" button with OnClickListener.
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Toast.makeText(context,"You choose No button",
                        Toast.LENGTH_SHORT).show();
                //  Cancel
                dialog.cancel();
            }
        });

        // Create AlertDialog:
        AlertDialog alert = builder.create();
        alert.show();
    }
}
