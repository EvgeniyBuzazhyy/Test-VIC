package com.example.evgeniy_pc.testvic;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

// --- Class for More activity  (location setting) ---
public class More extends Activity implements View.OnClickListener
{
    SharedPreferences sharPrefsaveData;
    RadioGroup radiogroup;
    RadioButton radioButtonMykolaiv, radioButtonKyiv, radioButtonKharkiv,radioButtonOdesa,radioButtonLviv,radioButtonZhytomyr;
    final String SAVED_LOCATION = "saved_location";
//---------------------------------------------------------------------------------- Create Activity
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.more);

        radioButtonMykolaiv = (RadioButton) findViewById(R.id.radioButtonMykolaiv);
        radioButtonKyiv = (RadioButton) findViewById(R.id.radioButtonKyiv);
        radioButtonKharkiv = (RadioButton) findViewById(R.id.radioButtonKharkiv);
        radioButtonOdesa = (RadioButton) findViewById(R.id.radioButtonOdesa);
        radioButtonLviv = (RadioButton) findViewById(R.id.radioButtonLviv);
        radioButtonZhytomyr = (RadioButton) findViewById(R.id.radioButtonZhytomyr);
        //Who is Checked
        readLocation();

        radiogroup = (RadioGroup) findViewById(R.id.radioGroup);

        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                switch (checkedId)
                {
                    case R.id.radioButtonMykolaiv:
                        writeLocation("Mykolaiv");
                        break;
                    case R.id.radioButtonKyiv:
                        writeLocation("Kyiv");
                        break;
                    case R.id.radioButtonKharkiv:
                        writeLocation("Kharkiv");
                        break;
                    case R.id.radioButtonOdesa:
                        writeLocation("Odesa");
                        break;
                    case R.id.radioButtonLviv:
                        writeLocation("Lviv");
                        break;
                    case R.id.radioButtonZhytomyr:
                        writeLocation("Zhytomyr");
                        break;

                    default:
                        break;
                }
            }
        });
    }
//------------------------------------------------------------- Write Location in shared Preferences
    public void writeLocation(String str)
    {
        sharPrefsaveData = getSharedPreferences("dataEmailAndLoc", Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sharPrefsaveData.edit();
        ed.putString(SAVED_LOCATION, str);
        ed.commit();
    }
//----------------------------- Read Location from shared Preferences and setChecked for radioButton
    public void readLocation()
    {
        sharPrefsaveData = getSharedPreferences("dataEmailAndLoc", MODE_PRIVATE);
        String savedLocation = sharPrefsaveData.getString(SAVED_LOCATION, "");
        switch (savedLocation)
        {
            case "Mykolaiv":
                radioButtonMykolaiv.setChecked(true);
                break;
            case "Kyiv":
                radioButtonKyiv.setChecked(true);
                break;
            case "Kharkiv":
                radioButtonKharkiv.setChecked(true);
                break;
            case "Odesa":
                radioButtonOdesa.setChecked(true);
                break;
            case "Lviv":
                radioButtonLviv.setChecked(true);
                break;
            case "Zhytomyr":
                radioButtonZhytomyr.setChecked(true);
                break;
        }
    }
//------------------------------------------------------------------------------------------ OnClick
    @Override
    public void onClick(View v)
    {
        finish();
    }
}
