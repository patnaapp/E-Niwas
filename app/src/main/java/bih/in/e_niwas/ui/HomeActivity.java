package bih.in.e_niwas.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.SQLException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.exp.e_niwas.R;

import java.io.IOException;
import java.util.ArrayList;

import bih.in.e_niwas.database.DataBaseHelper;
import bih.in.e_niwas.entity.Item_MasterEntity;
import bih.in.e_niwas.web_services.WebServiceHelper;

public class HomeActivity extends Activity
{
    DataBaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper=new DataBaseHelper(this);

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
            //createTable();
            //modifyTable();
        }
        catch (SQLException sqle)
        {
            throw sqle;
        }

        new Sync_Item_Master().execute();
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
