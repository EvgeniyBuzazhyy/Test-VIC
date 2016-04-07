package com.example.evgeniy_pc.testvic;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

// --- This fragment show answer in question8 ---
public class Fragment8 extends Fragment implements onSomeEventListener
{
    EditText other;

    onSomeEventListener someEventListener;
//----------------------------------- Attaching the fragment to the Activity and getting links to it
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
        View v = inflater.inflate(R.layout.fragment8, null);
        other = (EditText) v.findViewById(R.id.other);
        return v;
    }

//-------------------------------------------------------------------------- View more not available
    public void onDestroyView()
    {
        super.onDestroyView();
        //Save answer in Test Activity
        someEventListener.seveData(10,other.getText().toString());
    }
//-------------------------------------------------- Override method onSomeEventListener - interface
    @Override
    public void seveData(int nomberQuestion, String answer) {

    }
}