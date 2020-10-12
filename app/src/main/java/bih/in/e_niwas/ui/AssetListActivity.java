package bih.in.e_niwas.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.exp.e_niwas.R;

import java.util.ArrayList;

import bih.in.e_niwas.adapter.AssetListAdapter;
import bih.in.e_niwas.adapter.WorkSiteEditAdapter;
import bih.in.e_niwas.database.DataBaseHelper;
import bih.in.e_niwas.entity.NiwasInspectionEntity;
import bih.in.e_niwas.entity.UserDetails;
import bih.in.e_niwas.web_services.WebServiceHelper;

public class AssetListActivity extends AppCompatActivity {

    RecyclerView listView;
    TextView tv_Norecord;
    DataBaseHelper dataBaseHelper;
    ArrayList<NiwasInspectionEntity> assetList = new ArrayList<>();
    AssetListAdapter labourSearchAdaptor;
String  user_id="";
    UserDetails userInfo;
    String assetlistnew="Y";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asset_list);

        initialise();
        user_id = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("uid", "");

        new SyncAssetList().execute();
       // assetList=dataBaseHelper.getAllNewEntryDetail("0", user_id);
      //  populateData();

    }

    public void onAddNewAsser(View view) {
        Intent i=new Intent(this,NewEntryForm_Activity.class);
        startActivity(i);
    }



    public void initialise() {
        dataBaseHelper = new DataBaseHelper(this);

        tv_Norecord = findViewById(R.id.tv_Norecord);
        listView = findViewById(R.id.rvlist);

        userInfo = (UserDetails) getIntent().getSerializableExtra("user");
    }

    public void populateData()
    {
        if (assetList != null && assetList.size() > 0)
        {
            Log.e("data", "" + assetList.size());

            tv_Norecord.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);

            labourSearchAdaptor = new AssetListAdapter(this, assetList,assetlistnew);
            listView.setLayoutManager(new LinearLayoutManager(this));
            listView.setAdapter(labourSearchAdaptor);

        } else {
            listView.setVisibility(View.GONE);
            tv_Norecord.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        new SyncAssetList().execute();
      //  assetList=dataBaseHelper.getAllNewEntryDetail("0", userInfo.getUserID());
        //populateData();
    }

    private class SyncAssetList extends AsyncTask<String, Void, ArrayList<NiwasInspectionEntity>> {
        private final ProgressDialog dialog = new ProgressDialog(AssetListActivity.this);
        int optionType;

        @Override
        protected void onPreExecute() {
            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage("लोड हो रहा है...");
            this.dialog.show();
        }

        @Override
        protected ArrayList<NiwasInspectionEntity> doInBackground(String...arg) {
            return WebServiceHelper.GetAssetList(user_id);
        }

        @Override
        protected void onPostExecute(ArrayList<NiwasInspectionEntity> result) {
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
            }

            if(result!=null) {
                assetList=result;

                populateData();
            }

        }
    }
}
