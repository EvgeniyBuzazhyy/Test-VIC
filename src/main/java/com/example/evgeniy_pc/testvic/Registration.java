package com.example.evgeniy_pc.testvic;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

// --- Class for registration new users ---
public class Registration extends Activity implements View.OnClickListener
{
    final String LOG_TAG = "myLogs";

    TextView textViewRegistration,textViewEmail,textViewLogin,textViewPassword,textViewSum;
    EditText editTextEmail,editTextLogin,editTextPassword,editTextSum;
    LinearLayout linearLayoutLogin;

    String strEmail, strlogin, strPass;
    InternetGetSet internetGetSet;

    final int DIALOG_EXIT = 1;

//---------------------------------------------------------------------------------- Create Activity
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);
        //Find TextView
        textViewRegistration = (TextView) findViewById(R.id.textViewRegistration);
        textViewEmail = (TextView) findViewById(R.id.textViewEmail);
        textViewLogin = (TextView) findViewById(R.id.textViewLogin);
        textViewPassword = (TextView) findViewById(R.id.textViewPassword);
        textViewSum = (TextView) findViewById(R.id.textViewSum);
        //Find EditText
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextLogin = (EditText) findViewById(R.id.editTextLogin);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextSum = (EditText) findViewById(R.id.editTextSum);

        linearLayoutLogin = (LinearLayout) findViewById(R.id.linearLayoutLogin);
    }
//------------------------------------------------------------------------------------------ OnClick
    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.editTextLoginOrPassword:
                //If the Internet is connected
                if (inspectionConnectedToInternet())
                {
                    //If data correct
                    if (inspection() == 3)
                    {
                        //The request to the server
                        internetGetSet = new InternetGetSet(this);
                    }
                }
                break;
            case R.id.editTextEmail:
                textViewRegistration.setText(getResources().getString(R.string.title_registration));
                textViewEmail.setTextColor(Color.BLACK);
                textViewEmail.setText(getResources().getString(R.string.text_email));
                break;
            case R.id.editTextLogin:
                textViewLogin.setTextColor(Color.BLACK);
                textViewLogin.setText(getResources().getString(R.string.text_login));
                break;
            case R.id.editTextPassword:
                textViewPassword.setTextColor(Color.BLACK);
                textViewPassword.setText(getResources().getString(R.string.text_password));
                break;
            case R.id.linearLayoutLogin:
                //Hide keyboard
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(linearLayoutLogin.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                break;
        }
    }
//--------------------------------------------- The method of testing the connection to the Internet
    public boolean inspectionConnectedToInternet()
    {
        boolean statusInternet;
        ConnectivityManager cm1 = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm1.getActiveNetworkInfo();
        if (activeNetwork != null)
        {statusInternet = true;}
        else
        {
            statusInternet = false;
            Toast.makeText(Registration.this, getResources().getString(R.string.internet_disconnected), Toast.LENGTH_SHORT) .show();
        }
        return statusInternet;
    }
//------------------------ The method checks the correctness of the data entered in the input fields
    public int inspection()
    {
        int resultInsp = 0;
        strEmail = String.valueOf((editTextEmail.getText()));
        strlogin = String.valueOf(editTextLogin.getText());
        strPass = String.valueOf(editTextPassword.getText());

        //--- Set text and color default
        textViewEmail.setTextColor(Color.BLACK);
        textViewEmail.setText(getResources().getString(R.string.text_email));
        //---
        textViewLogin.setTextColor(Color.BLACK);
        textViewLogin.setText(getResources().getString(R.string.text_login));
        //----
        textViewPassword.setTextColor(Color.BLACK);
        textViewPassword.setText(getResources().getString(R.string.text_password));
        // inspection
        if ( strEmail.contains("@"))
        { resultInsp++;}
        else
        {
            textViewEmail.setTextColor(Color.RED);
            textViewEmail.setText(getResources().getString(R.string.incorrect_email));
        }
        if ( strlogin.length()>=4)
        { resultInsp++;}
        else
        {
            textViewLogin.setTextColor(Color.RED);
            textViewLogin.setText(getResources().getString(R.string.length_login));
        }
        if ( strPass.length()>=6)
        { resultInsp++;}
        else
        {
            textViewPassword.setTextColor(Color.RED);
            textViewPassword.setText(getResources().getString(R.string.length_password));
        }
        return resultInsp;
    }

//------------------------------------------------------------------------------ Construction dialog
//It will be called in the InternetGetSet class
    public Dialog onCreateDialog(int id)
    {
        if (id == DIALOG_EXIT)
        {
            AlertDialog.Builder adb = new AlertDialog.Builder(this);
            adb.setTitle(R.string.exit);
            adb.setIcon(R.drawable.ic_ok);
            adb.setPositiveButton(R.string.ok, myClickListener);

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
                    //Return in LoginActivity result OK
                    Intent intent = new Intent();
                    intent.putExtra("login", editTextLogin.getText().toString());
                    setResult(RESULT_OK, intent);
                    finish();
                    break;
            }
        }
    };
}
