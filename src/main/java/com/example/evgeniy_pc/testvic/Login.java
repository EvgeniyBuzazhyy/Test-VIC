package com.example.evgeniy_pc.testvic;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

// --- Entry class to app  ---
public class Login extends Activity implements View.OnClickListener
{
    TextView textViewIncorrect;
    EditText editTextLoginOrEmail,editTextPassword;
    LinearLayout  linearLayoutLogin;

    final int DIALOG_EXIT = 1;
    InternetGetSet internetGetSet;

//---------------------------------------------------------------------------------- Create Activity
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        //Find View
        textViewIncorrect = (TextView) findViewById(R.id.textViewIncorrect);
        editTextLoginOrEmail = (EditText) findViewById(R.id.editTextLoginOrEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        linearLayoutLogin = (LinearLayout) findViewById(R.id.linearLayoutLogin);
    }
//------------------------------------------------------------------------------------------ OnClick
    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.buttonEnter:
                //If the Internet is connected
                if (inspectionConnectedToInternet())
                {
                    //The request to the server
                    internetGetSet = new InternetGetSet(this);
                }
                break;
            case R.id.buttonExit:
                //Exit
                exitApp();
                break;
            case R.id.buttonRegistration:
                //If the Internet is connected
                if (inspectionConnectedToInternet())
                {
                    // Call Activity Registration
                    Intent intentRegistration = new Intent(this, Registration.class);
                    startActivityForResult(intentRegistration, 1);
                }
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
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null)
        {statusInternet = true;}
        else
        {
            statusInternet = false;
            Toast.makeText(Login.this, getResources().getString(R.string.internet_disconnected), Toast.LENGTH_SHORT) .show();
        }
        return statusInternet;
    }
//---------------------------------------------------------------- Result from Activity Registration
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (data == null) {return;}
        String loginRes = data.getStringExtra("login");
        editTextLoginOrEmail.setText(loginRes);

    }
//-------------------------------------------------------------------- This method close oll program
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
            switch (which) {
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
