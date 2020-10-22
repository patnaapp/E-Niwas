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
import android.view.KeyEvent;
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
    ArrayList<Item_MasterEntity> Item_List = new ArrayList<Item_MasterEntity>();
    String divisionname="";
    String user_id="",div_id="",dist_code="";

    String div_name="",dist_name="",name="";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper=new DataBaseHelper(this);


        user_id = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("uid", "");
        div_id = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("div_id", "");
        dist_code = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("dist_id", "");
        dist_name = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("dist_name", "");
        div_name = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("div", "");
        name = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("u_name", "");
        tv_username = findViewById(R.id.tv_username);
        tv_district = findViewById(R.id.tv_district);
        tv_division = findViewById(R.id.tv_division);

        tv_username.setText(name);
        // tv_district.setText(userInfo.getDistName().equals("anyType{}") ? "NA" : userInfo.getDistName());
        tv_district.setText(dist_name);
        //tv_division.setText(userInfo.getDivisionName().equals("anyType{}") ? "NA" : userInfo.getDivisionName());
        tv_division.setText(div_name);

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
        }
        Item_List = dbHelper.getItemList("0","0");
        if (Item_List.size() <= 0)
        {
            new Sync_Item_Master().execute();
        }

       // getUserDetail();
    }

    private void getUserDetail()
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String username = prefs.getString("uid", "user");
        String password = prefs.getString("pass", "password");
        userInfo = dbHelper.getUserDetails(username, password);

        if(userInfo != null){
          //  tv_username.setText(userInfo.getUserID());
            tv_username.setText(name);
           // tv_district.setText(userInfo.getDistName().equals("anyType{}") ? "NA" : userInfo.getDistName());
            tv_district.setText(dist_name);
            //tv_division.setText(userInfo.getDivisionName().equals("anyType{}") ? "NA" : userInfo.getDivisionName());
            tv_division.setText(div_name);
        }
    }

    public void on_NewEntry(View view)
    {
        Intent i=new Intent(HomeActivity.this,AssetListActivity.class);
        i.putExtra("user", userInfo);
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            // do something on back.
            // Display alert message when back button has been pressed
            //moveTaskToBack(true);
            backButtonHandler();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    public void backButtonHandler() {
      AlertDialog.Builder alertDialog = new AlertDialog.Builder(HomeActivity.this);
        alertDialog.setTitle("Exit?");
        alertDialog.setMessage("Do you want to exit the app ?");
        alertDialog.setPositiveButton("[NO]", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.setNegativeButton("[YES]", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
//                Intent i=new Intent(HomeActivity.this,PreLoginActivity.class);
//                startActivity(i);

                finish();

            }
        });
        alertDialog.show();
    }

}
