package bih.in.e_niwas.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.exp.e_niwas.R;

import java.util.ArrayList;

import bih.in.e_niwas.adapter.WorkSiteEditAdapter;
import bih.in.e_niwas.database.DataBaseHelper;
import bih.in.e_niwas.entity.NiwasInspectionEntity;

public class EditAssetEntry_Activity extends AppCompatActivity {

    RecyclerView listView;
    TextView tv_Norecord;
    DataBaseHelper dataBaseHelper;
    ArrayList<NiwasInspectionEntity> assetList = new ArrayList<>();
    WorkSiteEditAdapter labourSearchAdaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit_asset_entry_);

        initialise();

        assetList=dataBaseHelper.getAllNewEntryDetail();
        populateData();
    }

    public void initialise() {
        dataBaseHelper = new DataBaseHelper(this);

        tv_Norecord = findViewById(R.id.tv_Norecord);
        listView = findViewById(R.id.listviewshow);

    }

    public void populateData()
    {
        if (assetList != null && assetList.size() > 0)
        {
            Log.e("data", "" + assetList.size());

            tv_Norecord.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);

            labourSearchAdaptor = new WorkSiteEditAdapter(this, assetList);
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
        assetList=dataBaseHelper.getAllNewEntryDetail();
        populateData();
    }
}
