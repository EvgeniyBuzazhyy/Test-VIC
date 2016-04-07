package com.example.evgeniy_pc.testvic;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

// --- This fragment show variants answers in question4 ---
public class Fragment4 extends Fragment implements onSomeEventListener
{
    RadioGroup radiogroup;
    String ans = "0";

    onSomeEventListener someEventListener;

//----------------------------------- Attaching the fragment to the Activity and getting links to it
    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        try
        {
            someEventListener = (onSomeEventListener) activity;
        } catch (ClassCastException e)
        {
            throw new ClassCastException(activity.toString() + " must implement onSomeEventListener");
        }
    }
//------------------------------- Creating a View, which will track the content, and give his system
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        View v =  inflater.inflate(R.layout.fragment4, null);
        radiogroup = (RadioGroup) v.findViewById(R.id.radioG);

        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                switch (checkedId)
                {
                    case R.id.radioButton1:
                        ans = "1";
                        break;
                    case  R.id.radioButton2:
                        ans = "2";
                        break;
                    case  R.id.radioButton3:
                        ans = "3";
                        break;
                    case  R.id.radioButton4:
                        ans = "4";
                        break;
                    case  R.id.radioButton5:
                        ans = "5";
                        break;
                    case  R.id.radioButton6:
                        ans = "6";
                        break;

                    default:
                        break;
                }
            }
        });
        return v;
    }

//-------------------------------------------------------------------------- View more not available
    public void onDestroyView()
    {
        super.onDestroyView();
        //Save answer in Test Activity
        someEventListener.seveData(6,ans);
    }
//-------------------------------------------------- Override method onSomeEventListener - interface
    @Override
    public void seveData(int nomberQuestion, String answer) {

    }
}