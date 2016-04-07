package com.example.evgeniy_pc.testvic;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.AsyncTask;

import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

// --- Class processes requests to save data from three Activity (Login, Registration, SendData) ---
public class InternetGetSet
{
    //Object classes for getting links on them
    Login loginClass;
    Registration registrationClass;
    SendData sendDataClass;

    SharedPreferences sharPrefsaveData;
    final String SAVED_TEXT = "saved_email";

    //Variable to determine what activity is working with this class
    int formActivity;
    final int DIALOG_EXIT = 1;

    Context context;
    //String arrays for SQL query
    String[] selectionArgs = new String[] { "false" };
    String[] columns = new String[] {"email","location","question1","question2","question3",
                                                        "question4","question5","question6",
                                                        "question7","question8","question9",
                                                                               "question10"};

//---------------------------------------------------------------------- The constructors this class
    // For Login class
    public InternetGetSet(Login loginClass)
    {
        this.loginClass=loginClass;
        context=loginClass;
        formActivity = 1;
        startInternet();
    }
    // For Registration class
    public InternetGetSet(Registration registrationClass)
    {
        this.registrationClass=registrationClass;
        context=registrationClass;
        formActivity=2;
        startInternet();
    }
    // For SendData class
    public InternetGetSet(SendData sendDataClass)
    {
        this.sendDataClass=sendDataClass;
        context=sendDataClass;
        formActivity=3;
        startInternet();
    }

//----------------------------------------------------------------------------------- Start Internet
    public void startInternet()
    {
            //Start AsyncTask for connect to server and sending data
            ProgressTask pt = new ProgressTask(this);
            pt.execute();
    }

//----------------------------------------------------------- Create JSON file for sending in server
    public String CreateJson()
    {
        String strEmail;
        String strlogin;
        String strPass;

        String json = null;
        JSONObject jsonObject = new JSONObject();
        switch (formActivity)
        {
            case 1://------------------------------------------------ Create Json for Login Activity

                strPass = String.valueOf(loginClass.editTextPassword.getText());
                strlogin = String.valueOf(loginClass.editTextLoginOrEmail.getText());

                try{
                    jsonObject.put("login", strlogin);
                    jsonObject.put("pass", strPass);

                    json = jsonObject.toString();
                }
                catch (JSONException e){}
                break;
            case 2://----------------------------------------- Create Json for Registration Activity

                strEmail = String.valueOf((registrationClass.editTextEmail.getText()));
                strlogin = String.valueOf(registrationClass.editTextLogin.getText());
                strPass = String.valueOf(registrationClass.editTextPassword.getText());

                try{
                    jsonObject.put("email", strEmail);
                    jsonObject.put("login", strlogin);
                    jsonObject.put("pass", strPass);

                    json = jsonObject.toString();
                }
                catch (JSONException e){}
                break;
            case 3://--------------------------------------------- Create Json for SendData Activity
                //Get answers from DB
                Cursor c = sendDataClass.db.query("data", columns, "send = ?", selectionArgs, null, null, null);

                if (c!=null)
                {
                    //Copy the data from the Database for JSON file
                    if (c.moveToFirst())
                    {
                        try{
                            jsonObject.put("email",      c.getString(0));
                            jsonObject.put("location",   c.getString(1));
                            jsonObject.put("question1",  c.getString(2));
                            jsonObject.put("question2",  c.getString(3));
                            jsonObject.put("question3",  c.getString(4));
                            jsonObject.put("question4",  c.getString(5));
                            jsonObject.put("question5",  c.getString(6));
                            jsonObject.put("question6",  c.getString(7));
                            jsonObject.put("question7",  c.getString(8));
                            jsonObject.put("question8",  c.getString(9));
                            jsonObject.put("question9",  c.getString(10));
                            jsonObject.put("question10", c.getString(11));

                            json = jsonObject.toString();
                            break;
                        }
                        catch (JSONException e){}
                    }
                }
                break;
        }
        return json;
    }

//--------------------------------------------------------------------------------------- Parse Json
    public void parseJson(String mas)
    {
        String readyString;
        //If data from server got with title
        //Determine index where is char "<"
        int counIndex = mas.indexOf("<");
        //Copy in buffer
        StringBuilder bufStr=new StringBuilder(mas);
        //Delete the unnecessary part of the string
        if (counIndex!=-1)
        { bufStr.delete(counIndex, mas.length());}
        //Copy ready string
        readyString = String.valueOf( bufStr);

        switch (formActivity)
        {
            case 1://-------------------------------------------------------- Parse - Login Activity
                String dataLogin = "";
                String emailLogin = "";
                try {
                    JSONObject jsonRootObject = new JSONObject(readyString);
                    //Get the instance of JSONArray that contains JSONObjects
                    JSONArray jsonArray = jsonRootObject.optJSONArray("login");

                    //Iterate the jsonArray and print the info of JSONObjects
                    for (int i = 0; i < jsonArray.length(); i++)
                    {
                        //Get JsonArray
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        //Get data by key
                        dataLogin = jsonObject.optString("success").toString();
                        emailLogin = jsonObject.optString("email").toString();
                    }
                    //If answer equals "correct" we will save email and close Activity "Login"
                    if (dataLogin.equals("correct"))
                    {
                        //Save email
                        sharPrefsaveData = loginClass.getSharedPreferences("dataEmailAndLoc", Context.MODE_PRIVATE);
                        SharedPreferences.Editor ed = sharPrefsaveData.edit();
                        ed.putString(SAVED_TEXT, emailLogin);
                        ed.commit();

                        //Close Activity "Login"
                        loginClass.finish();
                    }
                    else
                    {
                        loginClass.textViewIncorrect.setText(context.getResources().getString(R.string.incorrect_log_or_pass));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case 2://------------------------------------------------- Parse - Registration Activity
                String dataRegistration = "";

                try {
                    JSONObject jsonRootObject = new JSONObject(readyString);
                    //Get the instance of JSONArray that contains JSONObjects
                    JSONArray jsonArray = jsonRootObject.optJSONArray("registration");

                    //Iterate the jsonArray and print the info of JSONObjects
                    for (int i = 0; i < jsonArray.length(); i++)
                    {
                        //Get JsonArray
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        //Get data by key
                        dataRegistration = jsonObject.optString("success").toString();
                    }

                    // The result is output in TextView

                    switch (dataRegistration)
                    {
                        case "add":
                            registrationClass.showDialog(DIALOG_EXIT);
                            break;
                        case "error":
                            registrationClass.textViewRegistration.setText(context.getResources().getString(R.string.error_registration));
                            break;
                        case "loginExist":
                            registrationClass.textViewLogin.setTextColor(Color.RED);
                            registrationClass.textViewLogin.setText(context.getResources().getString(R.string.login_exists));
                            break;
                        case "emailExist":
                            registrationClass.textViewEmail.setTextColor(Color.RED);
                            registrationClass.textViewEmail.setText(context.getResources().getString(R.string.email_exists));
                            break;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case 3://----------------------------------------------------- Parse - SendData Activity
                String data = "";

                try {
                    JSONObject jsonRootObject = new JSONObject(readyString);
                    //Get the instance of JSONArray that contains JSONObjects
                    JSONArray jsonArray = jsonRootObject.optJSONArray("answer");

                    //Iterate the jsonArray and print the info of JSONObjects
                    for (int i = 0; i < jsonArray.length(); i++)
                    {
                        //Get JsonArray
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        //Get data by key
                        data = jsonObject.optString("success").toString();
                    }

                    // The result is output in TextView and  Toast message

                    switch (data)
                    {
                        case "add":
                            Toast.makeText(sendDataClass,context.getResources().getString(R.string.data_added),Toast.LENGTH_LONG).show();
                            //Updating the last record in database if  the data are added to the server
                            ContentValues cv = new ContentValues();
                            cv.put("send", true);
                            int updCount = sendDataClass.db.update("data", cv, "send = ?",new String[] { "false" });
                            //Close connection to DB
                            sendDataClass.dbHelper.close();
                            //Close the possibility of re-add
                            sendDataClass.sendDataOk = false;
                            break;
                        case "error":
                            Toast.makeText(sendDataClass,context.getResources().getString(R.string.error),Toast.LENGTH_LONG).show();
                            break;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
//**************************************************************************************** AsyncTask
    class ProgressTask extends AsyncTask<String, Void, String>
    {
        InternetGetSet internetGetSet;
//---------------------------------------------------------------------- The constructors this class
        ProgressTask(InternetGetSet internetGetSet )
        {
            this.internetGetSet=internetGetSet;
        }

//---------------------------------------------------------------------------------- Background Work
        @Override
        protected String doInBackground(String... path)
        {
            String content =null;
            try
            {
                //Call create JsonFile
                String json = CreateJson();

                //Request for three Activity
                switch (formActivity)
                {
                    case 1:
                        content = getContent("http://vic.zzz.com.ua/testLoginPass.php?login="+json);
                        break;
                    case 2:
                        content = getContent("http://vic.zzz.com.ua/registration.php?registration="+json);
                        break;
                    case 3:
                        content = getContent("http://vic.zzz.com.ua/answers.php?answer=" + json);
                        break;
                }
            }
            catch (IOException ex)
            {
                content = ex.getMessage();
            }
            return content;
        }
//--------------------------------------------------------------------------------------------------
        @Override
        protected void onProgressUpdate(Void... items) {
        }
        //-------------------------------------------------------------------------------- execute()
        @Override
        protected void onPostExecute(String content)
        {
            //Parse JSON file
            internetGetSet.parseJson(content);
            //Toast.makeText(context, "Connected", Toast.LENGTH_SHORT) .show();
        }
//------------------------------------------------------------------- Connect to Server and get data
        private String getContent(String path) throws IOException
        {
            BufferedReader reader=null;
            StringBuilder buf = null;

            try
            {
                URL url=new URL(path);
                HttpURLConnection c=(HttpURLConnection)url.openConnection();
                c.setRequestMethod("GET");

                c.setReadTimeout(10000);
                c.connect();

                reader= new BufferedReader(new InputStreamReader(c.getInputStream()));
                buf=new StringBuilder();
                String line=null;
                while ((line=reader.readLine()) != null)
                {
                    buf.append(line + "\n");
                }
                return(buf.toString());
            }

            finally
            {
                if (reader != null)
                {
                    reader.close();
                }
            }
        }
    }
}
