package com.example.axel.assignmentgame;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

public class GameToasts
{
    private static Toast toast;

    public static void makeToast(Context c, String text)
    {
        toast = Toast.makeText(c, text, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
