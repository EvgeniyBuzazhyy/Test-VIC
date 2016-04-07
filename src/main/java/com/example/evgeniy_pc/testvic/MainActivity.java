package com.example.evgeniy_pc.testvic;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity implements View.OnClickListener
{
    SharedPreferences sharPrefsaveData;
    final String SAVED_LOCATION = "saved_location";
    final String SAVED_TEXT = "saved_email";
    TextView textViewLoginOrEmail;
//---------------------------------------------------------------------------------- Create Activity
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewLoginOrEmail = (TextView) findViewById(R.id.textViewLoginOrEmail);

        //Save location
        sharPrefsaveData = getSharedPreferences("dataEmailAndLoc", Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sharPrefsaveData.edit();
        ed.putString(SAVED_LOCATION, "Mykolaiv");
        ed.commit();

        //Process the output label from the app
        if (getIntent().getBooleanExtra("finish", false))
        {
            finish();
            android.os.Process.killProcess(android.os.Process.myPid());
        }
        else
        {
            //Call first activity this app
            Intent intentLogin = new Intent(this, Login.class);
            startActivity(intentLogin);
        }
    }
//---------------------------------------------------- Method called when Activity will be show user
    @Override
    protected void onResume()
    {
        super.onResume();
        //Read email
        sharPrefsaveData = getSharedPreferences("dataEmailAndLoc", MODE_PRIVATE);
        //Set email in TextView
        textViewLoginOrEmail.setText(sharPrefsaveData.getString(SAVED_TEXT, ""));
    }
//------------------------------------------------------- Method called when push system button back
    @Override
    public void onBackPressed()
    {
        Intent intentLogin = new Intent(this, Login.class);
        startActivity(intentLogin);
    }
//------------------------------------------------------------------------------------------ OnClick
    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.buttonMore:
                //Call activity More
                Intent intentMore = new Intent(this, More.class);
                startActivity(intentMore);
                break;
            case R.id.buttonStartTest:
                //Call activity Test
                Intent intentTest = new Intent(this, Test.class);
                startActivity(intentTest);
                break;
        }
    }
}