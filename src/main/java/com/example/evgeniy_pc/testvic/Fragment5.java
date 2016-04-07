package com.example.evgeniy_pc.testvic;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

// --- This fragment show variants answers in question5 ---
public class Fragment5 extends Fragment4 implements onSomeEventListener
{

//------------------------------- Creating a View, which will track the content, and give his system
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        View v =  inflater.inflate(R.layout.fragment5, null);
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
        someEventListener.seveData(7,ans);
    }
}