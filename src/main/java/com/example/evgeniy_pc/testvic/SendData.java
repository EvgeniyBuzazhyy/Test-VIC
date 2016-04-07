package com.example.evgeniy_pc.testvic;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

// --- Class for send data on server and getting answer ---
public class SendData extends Activity implements View.OnClickListener
{
    final int DIALOG_EXIT = 1;
    //DB
    DBHelper dbHelper;
    SQLiteDatabase db;

    TextView textViewTestIsOver;

    InternetGetSet internetGetSet;
    //Tag resending data to the server
    Boolean sendDataOk = true;
//---------------------------------------------------------------------------------- Create Activity
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_data);

        textViewTestIsOver = (TextView) findViewById(R.id.textViewTestIsOver);
        //Create DB
        dbHelper = new DBHelper(this);
        //Connect to DB
        db = dbHelper.getWritableDatabase();
    }
//------------------------------------------------------------------------------------------ OnClick
    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.buttonSendData:
                if(sendDataOk)
                {
                    //If the Internet is connected
                    if (inspectionConnectedToInternet())
                    {
                        //The request to the server
                        internetGetSet = new InternetGetSet(this);
                    }
                }
                else
                {
                    Toast.makeText(this,getResources().getString(R.string.data_already_added),Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.buttonNewTest:
                finish();
                break;
            case R.id.buttonExit:
                //Exit from app
                exitApp();
                break;
        }
    }
//--------------------------------------------- The method of testing the connection to the Internet
    public boolean inspectionConnectedToInternet()
    {
        boolean statusInternet;
        ConnectivityManager cm2 = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm2.getActiveNetworkInfo();
        if (activeNetwork != null)
        {statusInternet = true;}
        else
        {
            statusInternet = false;
            Toast.makeText(SendData.this, getResources().getString(R.string.internet_disconnected), Toast.LENGTH_SHORT) .show();
        }
        return statusInternet;
    }
//---------------------------------------------------------------------------- This method close app
    public void exitApp()
    {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("finish", true);
        startActivity(intent);
    }

//-------------------------------------------------------------- Method called when push button back
    @Override
    public void onBackPressed()
    {
        //Call dialog
        showDialog(DIALOG_EXIT);
    }
//------------------------------------------------------------------------------ Construction dialog
    public Dialog onCreateDialog(int id)
    {
        if (id == DIALOG_EXIT)
        {
            AlertDialog.Builder adb = new AlertDialog.Builder(this);
            adb.setTitle(R.string.exitLogin);
            adb.setIcon(R.drawable.ic_exit);
            adb.setPositiveButton(R.string.yes, myClickListener);
            adb.setNegativeButton(R.string.no, myClickListener);

            return adb.create();
        }
        return super.onCreateDialog(id);
    }
    //Listener button in dialog
    DialogInterface.OnClickListener myClickListener = new DialogInterface.OnClickListener()
    {
        public void onClick(DialogInterface dialog, int which)
        {
            switch (which)
            {
                case Dialog.BUTTON_POSITIVE:
                    //Exit app
                    exitApp();
                    finish();
                    break;
                case Dialog.BUTTON_NEGATIVE:
                    break;
            }
        }
    };
}
