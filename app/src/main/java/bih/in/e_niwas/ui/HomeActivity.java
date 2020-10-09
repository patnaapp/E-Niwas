package bih.in.e_niwas.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.exp.e_niwas.R;

import java.util.ArrayList;

public class HomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

//    private class Sync_Item_Master extends AsyncTask<String, Void, ArrayList<Item>> {
//
//        private final ProgressDialog dialog = new ProgressDialog(Phase1HomeActivity.this);
//
//        private final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(Phase1HomeActivity.this).create();
//
//        @Override
//        protected void onPreExecute() {
//
//            this.dialog.setCanceledOnTouchOutside(false);
//            this.dialog.setMessage("Loading...");
//            this.dialog.show();
//        }
//
//        @Override
//        protected ArrayList<Financial_Year> doInBackground(String... param) {
//
//
//            return WebServiceHelper.getFinancialYear();
//
//        }
//
//        @Override
//        protected void onPostExecute(ArrayList<Financial_Year> result) {
//            if (this.dialog.isShowing()) {
//                this.dialog.dismiss();
//            }
//
//            if (result != null) {
//                Log.d("Resultgfg", "" + result);
//
//                DataBaseHelper helper = new DataBaseHelper(Phase1HomeActivity.this);
//
//
//                long i = helper.setFinancialYear(result);
//                if (i > 0) {
//                    Toast.makeText(Phase1HomeActivity.this, "Agricultural Year Loaded", Toast.LENGTH_SHORT).show();
//                } else {
//
//                }
//
//            } else {
//                Toast.makeText(Phase1HomeActivity.this, "Result:null", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
}
