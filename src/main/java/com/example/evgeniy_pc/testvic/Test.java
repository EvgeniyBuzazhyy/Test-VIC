package com.example.evgeniy_pc.testvic;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

// --- Class for Activity with Fragment ---
// Save answers from fragments, save their in array, DB and server
public class Test extends Activity implements View.OnClickListener , onSomeEventListener
{
    final String LOG_TAG = "myLogs";
    int countPush=0;
    DBHelper dbHelper;

    //Objects Fragments
    Fragment1 frag1; Fragment2 frag2; Fragment3 frag3; Fragment4 frag4; Fragment5 frag5;
    Fragment6 frag6; Fragment7 frag7; Fragment8 frag8; Fragment9 frag9; Fragment10 frag10;

    //Objects saved data
    SharedPreferences sharPrefsaveData;
    final String SAVED_TEXT = "saved_email";
    final String SAVED_LOCATION = "saved_location";

    //Fragment Transaction
    android.app.FragmentTransaction fTrans;

    TextView textViewQustions,textViewNumberQve;
    ScrollView scrollView;
    Button buttonBack, buttonNext;

    //Array
    String mas[] =new String[13];

//---------------------------------------------------------------------------------- Create Activity
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activiti_with_fragments);
        //Find button and textView
        textViewQustions = (TextView)findViewById(R.id.qustions);
        textViewNumberQve = (TextView)findViewById(R.id.numberQve);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        buttonBack = (Button) findViewById(R.id.back);
        buttonNext = (Button) findViewById(R.id.next);
        //Create Fragment
        frag1 = new Fragment1(); frag2 = new Fragment2(); frag3 = new Fragment3();
        frag4 = new Fragment4(); frag5 = new Fragment5(); frag6 = new Fragment6();
        frag7 = new Fragment7(); frag8 = new Fragment8(); frag9 = new Fragment9();
        frag10 = new Fragment10();

        //Create activity with first Fragment
        fTrans = getFragmentManager().beginTransaction();
        buttonBack.setVisibility(View.GONE);
        textViewNumberQve.setText(getResources().getString(R.string.question_number)+(countPush+1));
        textViewQustions.setText(getResources().getString(R.string.question_1));
        fTrans.add(R.id.frgmCont, frag1);
        countPush++;
        fTrans.addToBackStack(null);
        fTrans.commit();

        //Create  DBHelper
        dbHelper = new DBHelper(this);
    }
//-------------------------------------------------------------- Method called when push button back
    @Override
    public void onBackPressed()
    {//Does not action
    }
//------------------------------------------------------------------------------------------ OnClick
    public void onClick(View v)
    {
        switch (v.getId())
        {
            //Push button Back
            case R.id.back:
                countPush-=2;
                //Call previous fragment
                moveFragment(countPush);
                break;
            //Push button Next
            case R.id.next:
                //Hide the keyboard
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(buttonNext.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
                //Call next fragment
                moveFragment(countPush);
                break;
            default:
                break;
        }
    }
//------------------------------------------------------------------- Loading the necessary fragment
    public void moveFragment (int con)
    {
        //Set transaction
        fTrans = getFragmentManager().beginTransaction();
        //Raise scroll up
        scrollView.scrollTo(50,20);
        switch (con)
        {
            case 0:
                fTrans.remove(frag2);
                buttonBack.setVisibility(View.GONE);
                textViewNumberQve.setText(getResources().getString(R.string.question_number)+(countPush+1));
                textViewQustions.setText(getResources().getString(R.string.question_1));
                fTrans.add(R.id.frgmCont, frag1);
                countPush++;
                break;
            case 1:
                buttonBack.setVisibility(View.VISIBLE);
                textViewNumberQve.setText(getResources().getString(R.string.question_number)+(countPush+1));
                textViewQustions.setText(getResources().getString(R.string.question_2));
                fTrans.remove(frag3);
                fTrans.remove(frag1);
                fTrans.replace(R.id.frgmCont, frag2);
                countPush++;
                break;
            case 2:
                textViewNumberQve.setText(getResources().getString(R.string.question_number)+(countPush+1));
                textViewQustions.setText(getResources().getString(R.string.question_3));
                fTrans.remove(frag4);
                fTrans.remove(frag2);
                fTrans.replace(R.id.frgmCont, frag3);
                countPush++;
                break;
            case 3:
                textViewNumberQve.setText(getResources().getString(R.string.question_number)+(countPush+1));
                textViewQustions.setText(getResources().getString(R.string.question_4));
                fTrans.remove(frag5);
                fTrans.remove(frag3);
                fTrans.replace(R.id.frgmCont, frag4);
                countPush++;

                break;
            case 4:
                textViewNumberQve.setText(getResources().getString(R.string.question_number)+(countPush+1));
                textViewQustions.setText(getResources().getString(R.string.question_5));
                fTrans.remove(frag6);
                fTrans.remove(frag4);
                fTrans.replace(R.id.frgmCont, frag5);
                countPush++;
                break;
            case 5:
                textViewNumberQve.setText(getResources().getString(R.string.question_number)+(countPush+1));
                textViewQustions.setText(getResources().getString(R.string.question_6));
                fTrans.remove(frag7);
                fTrans.remove(frag5);
                fTrans.replace(R.id.frgmCont, frag6);
                countPush++;
                break;
            case 6:
                textViewNumberQve.setText(getResources().getString(R.string.question_number)+(countPush+1));
                textViewQustions.setText(getResources().getString(R.string.question_7));
                fTrans.remove(frag8);
                fTrans.remove(frag6);
                fTrans.replace(R.id.frgmCont, frag7);
                countPush++;
                break;
            case 7:
                textViewNumberQve.setText(getResources().getString(R.string.question_number)+(countPush+1));
                textViewQustions.setText(getResources().getString(R.string.question_8));
                fTrans.remove(frag9);
                fTrans.remove(frag7);
                fTrans.replace(R.id.frgmCont, frag8);
                countPush++;
                break;
            case 8:
                buttonNext.setText(getResources().getString(R.string.button_next));
                textViewNumberQve.setText(getResources().getString(R.string.question_number)+(countPush+1));
                textViewQustions.setText(getResources().getString(R.string.question_9));
                fTrans.remove(frag10);
                fTrans.remove(frag8);
                fTrans.replace(R.id.frgmCont, frag9);
                countPush++;
                break;
            case 9:
                buttonNext.setText(getResources().getString(R.string.button_end));
                textViewNumberQve.setText(getResources().getString(R.string.question_number)+(countPush+1));
                textViewQustions.setText(getResources().getString(R.string.question_10));

                fTrans.remove(frag9);
                fTrans.replace(R.id.frgmCont, frag10);
                countPush++;
                break;
            case 10:
                //Start Activity SendData
                Intent intentSendData = new Intent(this, SendData.class);
                startActivity(intentSendData);
                this.finish();
                break;

            default:
                break;
        }
        //Commit transaction
        fTrans.addToBackStack(null);
        fTrans.commit();
    }
//--------------------------------------------- Get saved email and location from Shared Preferences
    public String getSavedEmailLoca(String type)
    {
        String savedText;
        if (type.equals("email"))
        {
            //Read email
            sharPrefsaveData = getSharedPreferences("dataEmailAndLoc", MODE_PRIVATE);
            savedText = sharPrefsaveData.getString(SAVED_TEXT, "");
        }
        else
        {
            //Read location
            sharPrefsaveData = getSharedPreferences("dataEmailAndLoc", MODE_PRIVATE);
            savedText = sharPrefsaveData.getString(SAVED_LOCATION, "");
        }
        return savedText;
    }
//-------------------------------------- Save answers from Fragments (interface onSomeEventListener)

    @Override
    public void seveData(int nomberQuestion, String answer)
    {
        //Create object for save data in DataBase
        ContentValues cv = new ContentValues();
        //Connect to DataBase
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        //Save answers in array
        switch (nomberQuestion)
        {
            case 3:
                mas[0] = getSavedEmailLoca("email");
                mas[1] = getSavedEmailLoca("location");
                mas[2] = answer;
                break;
            case 4:
                mas[3] = answer;
                break;
            case 5:
                mas[4] = answer;
                break;
            case 6:
                mas[5] = answer;
                break;
            case 7:
                mas[6] = answer;
                break;
            case 8:
                mas[7] = answer;
                break;
            case 9:
                mas[8] = answer;
                break;
            case 10:
                mas[9] = answer;
                break;
            case 11:
                mas[10] = answer;
                break;
            case 12:
                mas[11] = answer;
                mas[12] = "false";

                //Add to DataBase
                cv.put("email",     mas[0]);
                cv.put("location",  mas[1]);
                cv.put("question1", mas[2]);
                cv.put("question2", mas[3]);
                cv.put("question3", mas[4]);
                cv.put("question4", mas[5]);
                cv.put("question5", mas[6]);
                cv.put("question6", mas[7]);
                cv.put("question7", mas[8]);
                cv.put("question8", mas[9]);
                cv.put("question9", mas[10]);
                cv.put("question10", mas[11]);
                cv.put("send", mas[12]);

                long rowID = db.insert("data", null, cv);
                Log.d(LOG_TAG, "ЗАТРОНУТА СТРОКА row inserted, ID = " + rowID);

                break;
        }
        //Close DB
        dbHelper.close();
    }
}
