package com.example.evgeniy_pc.testvic;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

// --- This fragment show answer in question2 ---
public class Fragment2 extends Fragment implements onSomeEventListener
{

    EditText age ;

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
//------------------------------- Creating a View, which will track the content, and give his system
    @Override
     public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        //Get age from EditText
        View v = inflater.inflate(R.layout.fragment2, null);
        age = (EditText) v.findViewById(R.id.editText);
        return v;
    }

//-------------------------------------------------------------------------- View more not available
    public void onDestroyView()
    {
        super.onDestroyView();
        //Save answer in Test Activity
        someEventListener.seveData(4,age.getText().toString());
    }
//-------------------------------------------------- Override method onSomeEventListener - interface
    @Override
    public void seveData(int nomberQuestion, String answer) {
    }
}