package com.example.evgeniy_pc.testvic;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

// --- This fragment show variants answers in question1 ---
public class Fragment1 extends Fragment implements onSomeEventListener , CompoundButton.OnCheckedChangeListener
{
    int arrayAnsw[] = new int[8];

    CheckBox checkBox1,checkBox2,checkBox3,checkBox4,checkBox5,checkBox6,checkBox7,checkBox8;

    final String LOG_TAG = "myLogs";

    onSomeEventListener someEventListener;

//----------------------------------- Attaching the fragment to the Activity and getting links to it
    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        try {
            someEventListener = (onSomeEventListener) activity;
        } catch (ClassCastException e)
        {
            throw new ClassCastException(activity.toString() + " must implement onSomeEventListener");
        }
    }
//----------------------------------------------------------------------------------------- OnCreate
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //filling an array
       for(int i=0;i<arrayAnsw.length;i++)
       {
           arrayAnsw[i]=0;
       }
    }
//------------------------------- Creating a View, which will track the content, and give his system
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment1, null);
        checkBox1 = (CheckBox) v.findViewById(R.id.checkBox1);
        checkBox2 = (CheckBox) v.findViewById(R.id.checkBox2);
        checkBox3 = (CheckBox) v.findViewById(R.id.checkBox3);
        checkBox4 = (CheckBox) v.findViewById(R.id.checkBox4);
        checkBox5 = (CheckBox) v.findViewById(R.id.checkBox5);
        checkBox6 = (CheckBox) v.findViewById(R.id.checkBox6);
        checkBox7 = (CheckBox) v.findViewById(R.id.checkBox7);
        checkBox8 = (CheckBox) v.findViewById(R.id.checkBox8);

        checkBox1.setOnCheckedChangeListener(this);
        checkBox2.setOnCheckedChangeListener(this);
        checkBox3.setOnCheckedChangeListener(this);
        checkBox4.setOnCheckedChangeListener(this);
        checkBox5.setOnCheckedChangeListener(this);
        checkBox6.setOnCheckedChangeListener(this);
        checkBox7.setOnCheckedChangeListener(this);
        checkBox8.setOnCheckedChangeListener(this);

        return v;
    }

//-------------------------------------------------------------------------- View more not available
    public void onDestroyView()
    {
        super.onDestroyView();
        //Add result checker
        String strAns = ""+arrayAnsw[0]+arrayAnsw[1]+arrayAnsw[2]+arrayAnsw[3]+arrayAnsw[4]+arrayAnsw[5]+arrayAnsw[6]+arrayAnsw[7]+"";
        //Save answer in Test Activity
        someEventListener.seveData(3,strAns);
    }
//---------------------------------------------------------------- Listening CheckedChanged CheckBox
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
    {
        //Get isChecked
        boolean checked = buttonView.isChecked();

        //Verify who from checkBox is checked
        switch(buttonView.getId())
        {
            case R.id.checkBox1:
                if (checked)
                {
                    arrayAnsw[0]=1;
                }
                else
                {
                    arrayAnsw[0]=0;
                }
                break;
            case R.id.checkBox2:
                if (checked)
                {
                    arrayAnsw[1]=2;
                }
                else
                {
                    arrayAnsw[1]=0;
                }
                break;
            case R.id.checkBox3:
                if (checked)
                {
                    arrayAnsw[2]=3;
                }
                else
                {
                    arrayAnsw[2]=0;
                }
                break;
            case R.id.checkBox4:
                if (checked)
                {
                    arrayAnsw[3]=4;
                }
                else
                {
                    arrayAnsw[3]=0;
                }
                break;
            case R.id.checkBox5:
                if (checked)
                {
                    arrayAnsw[4]=5;
                }
                else
                {
                    arrayAnsw[4]=0;
                }
                break;
            case R.id.checkBox6:
                if (checked)
                {
                    arrayAnsw[5]=6;
                }
                else
                {
                    arrayAnsw[5]=0;
                }
                break;
            case R.id.checkBox7:
                if (checked)
                {
                    arrayAnsw[6]=7;
                }
                else
                {
                    arrayAnsw[6]=0;
                }
                break;
            case R.id.checkBox8:
                if (checked)
                {
                    arrayAnsw[7]=8;
                }
                else
                {
                    arrayAnsw[7]=0;
                }
                break;
        }
    }

//-------------------------------------------------- Override method onSomeEventListener - interface
    @Override
    public void seveData(int nomberQuestion, String answer) { }
}
