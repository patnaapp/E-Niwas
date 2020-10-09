package bih.in.e_niwas.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.SQLException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.exp.e_niwas.R;

import java.io.IOException;
import java.util.ArrayList;

import bih.in.e_niwas.database.DataBaseHelper;
import bih.in.e_niwas.entity.Item_MasterEntity;
import bih.in.e_niwas.entity.UserDetails;
import bih.in.e_niwas.web_services.WebServiceHelper;

public class HomeActivity extends Activity
{
    DataBaseHelper dbHelper;

    UserDetails userInfo;

    TextView tv_username,tv_district,tv_division;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper=new DataBaseHelper(this);

        tv_username = findViewById(R.id.tv_username);
        tv_district = findViewById(R.id.tv_district);
        tv_division = findViewById(R.id.tv_division);

        try
        {
            dbHelper.createDataBase();
        }
        catch (IOException ioe)
        {
            throw new Error("Unable to create database");
        }

        try
        {
            dbHelper.openDataBase();
        }
        catch (SQLException sqle)
        {
            throw sqle;
        }

        new Sync_Item_Master().execute();

        getUserDetail();
    }

    private void getUserDetail(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String username = prefs.getString("uid", "user");
        String password = prefs.getString("pass", "password");
        userInfo = dbHelper.getUserDetails(username, password);

        if(userInfo != null){
            tv_username.setText(userInfo.getUserID());
            tv_district.setText(userInfo.getDistName().equals("anyType{}") ? "NA" : userInfo.getDistName());
            tv_division.setText(userInfo.getDivisionName().equals("anyType{}") ? "NA" : userInfo.getDivisionName());
        }
    }

    public void on_NewEntry(View view)
    {
        Intent i=new Intent(HomeActivity.this,NewEntryForm_Activity.class);
        startActivity(i);
    }

    public void on_EditEntry(View view)
    {
        Intent i=new Intent(HomeActivity.this,EditAssetEntry_Activity.class);
        startActivity(i);
    }

    public void OnClickLogout(View view) {
        //getUserDetail();
        new AlertDialog.Builder(this)
                .setTitle("Logout")
                .setIcon(R.drawable.eniwaslogo)
                .setMessage("Are you sure you want logout from your profile? \n ")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        confirmLogout();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void confirmLogout(){
        SplashActivity.prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = SplashActivity.prefs.edit();
        editor.putBoolean("username", false);
        editor.putBoolean("password", false);
        editor.commit();

        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private class Sync_Item_Master extends AsyncTask<String, Void, ArrayList<Item_MasterEntity>> {

        private final ProgressDialog dialog = new ProgressDialog(HomeActivity.this);

        private final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(HomeActivity.this).create();

        @Override
        protected void onPreExecute() {

            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage("Loading...");
            this.dialog.show();
        }

        @Override
        protected ArrayList<Item_MasterEntity> doInBackground(String... param) {

            return WebServiceHelper.getItem_Master();
        }

        @Override
        protected void onPostExecute(ArrayList<Item_MasterEntity> result) {
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
            }

            if (result != null) {
                Log.d("Resultgfg", "" + result);

                DataBaseHelper helper = new DataBaseHelper(HomeActivity.this);


                long i = helper.setItem_Master(result);
                if (i > 0) {
                    Toast.makeText(HomeActivity.this, "Item Master Loaded", Toast.LENGTH_SHORT).show();
                } else {

                }

            } else {
                Toast.makeText(HomeActivity.this, "Result:null", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
