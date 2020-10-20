package bih.in.e_niwas.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.SQLException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.exp.e_niwas.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import bih.in.e_niwas.database.DataBaseHelper;
import bih.in.e_niwas.entity.Block;
import bih.in.e_niwas.entity.District;
import bih.in.e_niwas.entity.DivisionList;
import bih.in.e_niwas.entity.Item_MasterEntity;
import bih.in.e_niwas.entity.NiwasInspectionEntity;
import bih.in.e_niwas.entity.PanchayatData;
import bih.in.e_niwas.entity.PanchayatEntity;
import bih.in.e_niwas.entity.Sub_DivisionList;
import bih.in.e_niwas.entity.WardList;
import bih.in.e_niwas.utility.CommonPref;

public class NewEntryForm_Activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    Button btn_proceed;
    Spinner sp_building_div,sp_dist,sp_subdiv,sp_block,sp_ward_pan,sp_bulding_check,sp_ward;
    CheckBox chk_open_land,chk_existing_building,chk_urban,chk_rural;
    EditText edt_pincode,edt_thana,edt_khata,edt_khesra,edt_nrth_chauhaddi,edt_south_chauhaddi,edt_neast_chauhaddi,edt_west_chauhaddi,edt_land_area,edt_trees_no,et_trees_details,edt_admin_dept;
    ArrayList<Item_MasterEntity> DivList = new ArrayList<Item_MasterEntity>();
    ArrayList<District> DistrictList = new ArrayList<District>();
    ArrayList<PanchayatData> PanchayatList = new ArrayList<PanchayatData>();
    ArrayList<WardList> WardList = new ArrayList<WardList>();
    ArrayList<Sub_DivisionList> SubDivList = new ArrayList<Sub_DivisionList>();
    ArrayList<Block> BlockList = new ArrayList<Block>();
    DataBaseHelper dataBaseHelper;
    ArrayAdapter<String> divtadapter;
    ArrayAdapter<String> distadapter;
    ArrayAdapter<String> blockadapter;
    ArrayAdapter<String> panchayatadapter;
    String _vardivID="",_vardivName="",_vardistID="",subdiv_id="",subdiv_Nm="",_vardistName="",block_id="",block_name="",panch_id="",panch_name="";
    String ben_type_aangan[] = {"-select-","YES","NO"};

    String Is_Building_Name="",Is_building_Code="";
    ArrayAdapter ben_type_aangan_aaray;
    LinearLayout ll_admindept;
    String area_type_id="",property_type_id="";
    NiwasInspectionEntity assetDetails;
    NiwasInspectionEntity assetDetails_edit;
    String ward_id="",ward_nm="";
    String keyid = "";
    boolean edit;
    String subdiv="",wardname="",pan_name="",user_id="",div_id="",dist_code="";
    byte[] image1server,image2server;
    String isServer="",div_name="",dist_name="";
    LinearLayout ll_isthere_building;
    String isEdit = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_entry_form_);

        user_id = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("uid", "");
        div_id = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("div_id", "");
        dist_code = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("dist_id", "");
        dist_name = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("dist_name", "");
        div_name = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("div", "");

        dataBaseHelper = new DataBaseHelper(NewEntryForm_Activity.this);
        try
        {
            dataBaseHelper.createDataBase();
        }
        catch (IOException ioe)
        {
            throw new Error("Unable to create database");
        }

        try
        {
            dataBaseHelper.openDataBase();
            //createTable();
            //modifyTable();
        }
        catch (SQLException sqle)
        {
            throw sqle;
        }
        initialisation();
        sp_bulding_check.setOnItemSelectedListener(this);

        try {


            keyid = getIntent().getExtras().getString("KeyId");

            isEdit = getIntent().getExtras().getString("isEdit");
            isServer = getIntent().getExtras().getString("isServer");
            Log.d("kvfrgv", "" + keyid + "" + isEdit);
            if (Integer.parseInt(keyid) > 0 && isEdit.equals("Yes")) {
                edit = true;
                if (isServer.equals("Yes")){
                    assetDetails_edit=(NiwasInspectionEntity)getIntent().getSerializableExtra("assetdata_server");
                    image1server = getIntent().getByteArrayExtra("image1");
                    image2server = getIntent().getByteArrayExtra("image2");

//                    assetDetails_edit.setImage1(image1server);
//                    assetDetails_edit.setImage2(image2server);
                }
                else {
                    assetDetails_edit=dataBaseHelper.getAllNewEntryDetail(keyid,user_id).get(0);
                }

                //       assetDetails_edit=(NiwasInspectionEntity)getIntent().getSerializableExtra("assetdata");
                ShowEditEntryKhesra();

            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        loadDivisionSpinnerdata();
        loadDistrictSpinnerdata();
        loadWardSpinnerData();




        sp_building_div.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                if (arg2 > 0) {

                    Item_MasterEntity dist = DivList.get(arg2-1);
                    _vardivID = dist.getItem_ID();
                    _vardivName = dist.getItem_Name();
                    //setBlockSpinnerData();
//                    packageList = dataBaseHelper.getPackageLocal();
//                    if (packageList.size() <= 0) {
//                        new Package_List().execute();
//                    } else {
//                        loadpackageSpinnerData(UserId,_vardivID);
//                    }

                } else {

//                    spBlock.setSelection(0);
//                    spPanchayat.setSelection(0);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        sp_dist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                if (arg2 > 0)
                {
                    District dist = DistrictList.get(arg2-1);
                    _vardistID = dist.get_DistCode();
                    _vardistName = dist.get_DistName();

                    loadSubDivSpinnerData(_vardistID);
                    //setBlockSpinnerData();
//                    packageList = dataBaseHelper.getPackageLocal();
//                    if (packageList.size() <= 0) {
//                        new Package_List().execute();
//                    } else {
//                        loadpackageSpinnerData(UserId,_vardivID);
//                    }

                } else {

//                    spBlock.setSelection(0);
//                    spPanchayat.setSelection(0);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        sp_subdiv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    subdiv_id = SubDivList.get(position-1).getSub_div_code();
                    subdiv_Nm = SubDivList.get(position-1).getSub_div_name();
                    loadBlockSpinnerData(_vardistID,subdiv_id);

                } else {
                    subdiv_id = "";
                    subdiv_Nm = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                subdiv_id = "";
                subdiv_Nm = "";
            }

        });

        sp_block.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    block_id = BlockList.get(position-1).getBlockCode();
                    block_name = BlockList.get(position-1).getBlockName();
                    loadPanchayatSpinnerData(block_id);

                } else {
                    block_id = "";
                    block_name = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                block_id = "";
                block_name = "";
            }

        });

        sp_ward_pan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    panch_id = PanchayatList.get(position-1).getPcode();
                    panch_name = PanchayatList.get(position-1).getPname();

                } else {
                    panch_id = "";
                    panch_name = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                panch_id = "";
                panch_name = "";

            }

        });

        sp_ward.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    ward_id = WardList.get(position-1).getWard_code();
                    ward_nm = WardList.get(position-1).getWard_name();

                } else {
                    ward_id = "";
                    ward_nm = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                ward_id = "";
                ward_nm = "";

            }

        });

        sp_bulding_check.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("arg2",""+position);
                if (position > 0) {
                    Is_Building_Name = ben_type_aangan[position].toString();

                    if (Is_Building_Name.equals("YES")) {

                        Is_building_Code = "Y";
                        ll_admindept.setVisibility(View.VISIBLE);
                    } else if (Is_Building_Name.equals("NO")) {

                        Is_building_Code = "N";
                        ll_admindept.setVisibility(View.GONE);
                    }

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }

        });

        chk_open_land.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                {
                    property_type_id="0";
                    chk_existing_building.setChecked(false);

                    if (isEdit.equals("Yes")) {
                        btn_proceed.setText("Update");
                    }
                    else {
                        btn_proceed.setText("Save");
                    }
                    ll_isthere_building.setVisibility(View.GONE);
                }
            }
        });
        chk_existing_building.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                {
                    ll_isthere_building.setVisibility(View.VISIBLE);
                    property_type_id="1";
                    chk_open_land.setChecked(false);

                    if (isEdit.equals("Yes")) {
                        btn_proceed.setText("Update building details");
                    }
                    else {
                        btn_proceed.setText("Add Building Details");
                    }
                }
            }
        });

        chk_urban.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                {
                    panch_id="";
                    panch_name="";
                    loadWardSpinnerData();
                    sp_ward_pan.setVisibility(View.GONE);
                    sp_ward.setVisibility(View.VISIBLE);
                    area_type_id="0";
                    chk_rural.setChecked(false);

                }
            }
        });
        chk_rural.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                {

                    loadPanchayatSpinnerData(block_id);
                    ward_id="";
                    ward_nm="";
                    sp_ward_pan.setVisibility(View.VISIBLE);
                    sp_ward.setVisibility(View.GONE);
                    area_type_id="1";
                    chk_urban.setChecked(false);

                }
            }
        });

        btn_proceed.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (chk_existing_building.isChecked())
                {
                    if (btn_proceed.getText().toString().equals("Update building details"))
                    {
                        updateData();
                        if(validateData())
                        {

                        if (isServer.equals("Yes")) {
                            Intent i = new Intent(NewEntryForm_Activity.this, BuildingDetails_Activity.class);
                            i.putExtra("assetdata_editserver", assetDetails_edit);
                            i.putExtra("image1_server", image1server);
                            i.putExtra("image2_server", image2server);
                            i.putExtra("KeyId", keyid);
                            i.putExtra("isEdit", "Yes");
                            i.putExtra("isServer", "Yes");
                            startActivity(i);
                        }
                        else {
                            Intent i = new Intent(NewEntryForm_Activity.this, BuildingDetails_Activity.class);
                            i.putExtra("assetdata_edit", assetDetails_edit);
//                            i.putExtra("image1_server", image1server);
//                            i.putExtra("image2_server", image2server);
                            i.putExtra("KeyId", assetDetails_edit.getId());
                            i.putExtra("isEdit", "Yes");
                            i.putExtra("isServer", "No");
                            startActivity(i);
                        }
                    }
                    }
                    else {

                        if(validateData())
                        {
                            setdataforintent();
                            Intent i=new Intent(NewEntryForm_Activity.this,BuildingDetails_Activity.class);
                            i.putExtra("data",assetDetails);
                            i.putExtra("isEdit", "No");
                            i.putExtra("isServer", "No");
                            startActivity(i);
                        }
                    }
                }
                else if (chk_open_land.isChecked())
                {

                    long id = 0;
                    if (btn_proceed.getText().toString().equals("Update"))
                    {
                        if (isServer.equals("Yes")) {
                            if(validateData())
                            {
                                updateData();
                                Intent i = new Intent(NewEntryForm_Activity.this, BuildingDetails_Activity.class);
                                i.putExtra("assetdata_editserver", assetDetails_edit);
                                i.putExtra("image1_server", image1server);
                                i.putExtra("image2_server", image2server);
                                i.putExtra("KeyId", keyid);
                                i.putExtra("isEdit", "Yes");
                                i.putExtra("isServer", "Yes");
                                startActivity(i);
//                                id = new DataBaseHelper(NewEntryForm_Activity.this).InsertAssetEntry_New(assetDetails);
//
//                                if (id > 0) {
//
//                                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(NewEntryForm_Activity.this);
//                                    alertDialog.setTitle("Saved");
//                                    alertDialog.setMessage("Data saved successfully");
//
//                                    alertDialog.setNegativeButton("[OK]", new DialogInterface.OnClickListener() {
//                                        public void onClick(DialogInterface dialog, int which) {
//                                            Toast.makeText(getApplicationContext(), "Data saved successfully", Toast.LENGTH_LONG).show();
//                                            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
//                                            startActivity(intent);
//                                            finish();
//                                        }
//                                    });
//                                    alertDialog.show();
//
//                                } else {
//                                    Toast.makeText(getApplicationContext(), "Not Success", Toast.LENGTH_LONG).show();
//                                }
                            }
                        }
                        else {
                            if(validateData()) {
                                updateData();
                                Intent i = new Intent(NewEntryForm_Activity.this, BuildingDetails_Activity.class);
                                i.putExtra("assetdata_edit", assetDetails_edit);
//                            i.putExtra("image1_server", image1server);
//                            i.putExtra("image2_server", image2server);
                              //  i.putExtra("KeyId", assetDetails_edit.getId());
                                i.putExtra("KeyId", keyid);
                                i.putExtra("isEdit", "Yes");
                                i.putExtra("isServer", "No");
                                startActivity(i);
//                                id = new DataBaseHelper(NewEntryForm_Activity.this).updateNewEntryKhesradetails(assetDetails);
//
//                                if (id > 0) {
//                                    Toast.makeText(getApplicationContext(), "Data updated successfully", Toast.LENGTH_LONG).show();
//
//                                    finish();
//
//                                } else {
//                                    Toast.makeText(getApplicationContext(), "अपडेट नहीं किया गया", Toast.LENGTH_LONG).show();
//                                }
                            }
                        }
                    }
                    else {


                        if(validateData())
                        {
                            setdataforintent();

                            Intent i=new Intent(NewEntryForm_Activity.this,BuildingDetails_Activity.class);
                            i.putExtra("data",assetDetails);
                            i.putExtra("isEdit", "No");
                            i.putExtra("isServer", "No");
                            startActivity(i);
//                            id = new DataBaseHelper(NewEntryForm_Activity.this).InsertAssetEntry_New(assetDetails);
//
//                            if (id > 0) {
//
//                                AlertDialog.Builder alertDialog = new AlertDialog.Builder(NewEntryForm_Activity.this);
//                                alertDialog.setTitle("Saved");
//                                alertDialog.setMessage("Data saved successfully");
//
//                                alertDialog.setNegativeButton("[OK]", new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        Toast.makeText(getApplicationContext(), "Data saved successfully", Toast.LENGTH_LONG).show();
//                                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
//                                        startActivity(intent);
//                                        finish();
//                                    }
//                                });
//                                alertDialog.show();
//
//                            } else {
//                                Toast.makeText(getApplicationContext(), "Not Success", Toast.LENGTH_LONG).show();
//                            }
                        }
                    }
                }

            }
        });
    }

    public void initialisation()
    {
        btn_proceed=findViewById(R.id.btn_proceed);
        sp_building_div=findViewById(R.id.sp_building_div);
        sp_dist=findViewById(R.id.sp_dist);
        sp_subdiv=findViewById(R.id.sp_subdiv);
        sp_block=findViewById(R.id.sp_block);
        sp_ward_pan=findViewById(R.id.sp_ward_pan);
        ll_admindept=findViewById(R.id.ll_admindept);
        sp_bulding_check=findViewById(R.id.sp_bulding_check);
        sp_ward=findViewById(R.id.sp_ward);

        ben_type_aangan_aaray = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, ben_type_aangan);
        sp_bulding_check.setAdapter(ben_type_aangan_aaray);

        chk_open_land=findViewById(R.id.chk_open_land);
        chk_existing_building=findViewById(R.id.chk_existing_building);
        chk_urban=findViewById(R.id.chk_urban);
        chk_rural=findViewById(R.id.chk_rural);
        edt_pincode=findViewById(R.id.edt_pincode);
        edt_thana=findViewById(R.id.edt_thana);
        edt_khata=findViewById(R.id.edt_khata);
        edt_khesra=findViewById(R.id.edt_khesra);
        edt_nrth_chauhaddi=findViewById(R.id.edt_nrth_chauhaddi);
        edt_nrth_chauhaddi.addTextChangedListener(inputTextWatcher1);
        edt_south_chauhaddi=findViewById(R.id.edt_south_chauhaddi);
        edt_south_chauhaddi.addTextChangedListener(inputTextWatcher2);
        edt_neast_chauhaddi=findViewById(R.id.edt_neast_chauhaddi);
        edt_neast_chauhaddi.addTextChangedListener(inputTextWatcher3);
        edt_west_chauhaddi=findViewById(R.id.edt_west_chauhaddi);
        edt_west_chauhaddi.addTextChangedListener(inputTextWatcher4);
        edt_land_area=findViewById(R.id.edt_land_area);
        edt_trees_no=findViewById(R.id.edt_trees_no);
        et_trees_details=findViewById(R.id.et_trees_details);
        et_trees_details.addTextChangedListener(inputTextWatcher5);
        edt_admin_dept=findViewById(R.id.edt_admin_dept);
        edt_admin_dept.addTextChangedListener(inputTextWatcher6);
        ll_isthere_building=findViewById(R.id.ll_isthere_building);
    }

    public void loadDivisionSpinnerdata() {

        DivList = dataBaseHelper.getItemList("19","0");
        String[] typeNameArray = new String[DivList.size() + 1];
        typeNameArray[0] = "-Select-";
        int i = 1;
        for (Item_MasterEntity type : DivList)
        {
            typeNameArray[i] = type.getItem_Name();
            i++;
        }
        divtadapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, typeNameArray);
        divtadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_building_div.setAdapter(divtadapter);
        int setID=0;
        for ( i = 0; i < DivList.size(); i++)
        {
            //   if (DivList.get(i).getDivId().equalsIgnoreCase(CommonPref.getUserDetails(NewEntryForm_Activity.this).get_DivisionID())) {
          //  if (DivList.get(i).getItem_ID().equalsIgnoreCase("329"))
            if (DivList.get(i).getItem_ID().equalsIgnoreCase(div_id))
            {
                setID = i;
            }
            if(setID!=0)
            {
                sp_building_div.setSelection(setID+1);
                sp_building_div.setEnabled(false);
            }
        }

//        if(getIntent().hasExtra("KeyId"))
//        {
//            sp_building_div.setSelection(((ArrayAdapter<String>) sp_building_div.getAdapter()).getPosition(_spin_div_nm));
//        }

    }

    public void loadDistrictSpinnerdata() {

        DistrictList = dataBaseHelper.getDistrict();
        String[] typeNameArray = new String[DistrictList.size() + 1];
        typeNameArray[0] = "-Select-";
        int i = 1;
        for (District type : DistrictList)
        {
            typeNameArray[i] = type.get_DistName();
            i++;
        }
        distadapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, typeNameArray);
        distadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_dist.setAdapter(distadapter);
        int setID=0;
        for ( i = 0; i < DistrictList.size(); i++)
        {
            //   if (DivList.get(i).getDivId().equalsIgnoreCase(CommonPref.getUserDetails(NewEntryForm_Activity.this).get_DivisionID())) {
            //if (DistrictList.get(i).get_DistCode().equalsIgnoreCase("213"))
            if (DistrictList.get(i).get_DistCode().equalsIgnoreCase(dist_code))
            {
                setID = i;
            }
            if(setID!=0)
            {
                sp_dist.setSelection(setID+1);
                sp_dist.setEnabled(false);
            }
        }

//        if(getIntent().hasExtra("KeyId"))
//        {
//            sp_building_div.setSelection(((ArrayAdapter<String>) sp_building_div.getAdapter()).getPosition(_spin_div_nm));
//        }

    }

    public void loadBlockSpinnerData(String district,String subdiv){
        BlockList.clear();
        BlockList = dataBaseHelper.getBlock(district,subdiv);
        ArrayList<String> list = new ArrayList<String>();
        list.add("-Select-");
        int index = 0;
        String name="";
        for (Block info: BlockList){
            if(getIntent().hasExtra("KeyId") && info.getBlockCode().equals(assetDetails_edit.getBlk_code())) {
                name= info.getBlockName();
            }
                list.add(info.getBlockName());
                //if(benDetails.get)

        }
        ArrayAdapter adaptor = new ArrayAdapter(this, android.R.layout.simple_spinner_item, list);
        adaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_block.setAdapter(adaptor);
        if(getIntent().hasExtra("KeyId"))
        {
            sp_block.setSelection(((ArrayAdapter<String>) sp_block.getAdapter()).getPosition(name));

        }
        //  sp_block.setEnabled(false);
    }

    public void loadPanchayatSpinnerData(String blockid)
    {
        PanchayatList = dataBaseHelper.getPanchayatLocal(blockid);
        ArrayList<String> list = new ArrayList<String>();
        list.add("-Select-");
        int index = 0;
        String name="";
        for (PanchayatData info: PanchayatList){
            if(getIntent().hasExtra("KeyId") && info.getPcode().equals(assetDetails_edit.getPanchayat_code())) {
                name= info.getPname();
            }

                list.add(info.getPname());
            //if(benDetails.get)
        }
        ArrayAdapter adaptor = new ArrayAdapter(this, android.R.layout.simple_spinner_item, list);
        adaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_ward_pan.setAdapter(adaptor);
        if(getIntent().hasExtra("KeyId"))
        {
            sp_ward_pan.setSelection(((ArrayAdapter<String>) sp_ward_pan.getAdapter()).getPosition(name));

        }
    }

    public void loadWardSpinnerData()
    {
        WardList = dataBaseHelper.getWardLocal();
        ArrayList<String> list = new ArrayList<String>();
        list.add("-Select-");
        int index = 0;
        String name="";
        for (WardList info: WardList){
            if(getIntent().hasExtra("KeyId") && info.getWard_code().equals(assetDetails_edit.getWard_id())) {
                name= info.getWard_name();
            }

                list.add(info.getWard_name());
            //if(benDetails.get)
        }
        ArrayAdapter adaptor = new ArrayAdapter(this, android.R.layout.simple_spinner_item, list);
        adaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_ward.setAdapter(adaptor);
        if(getIntent().hasExtra("KeyId"))
        {
            sp_ward.setSelection(((ArrayAdapter<String>) sp_ward.getAdapter()).getPosition(name));

        }
    }

    public void loadSubDivSpinnerData(String blockid)
    {
        SubDivList = dataBaseHelper.getSubDivLocal(blockid);
        ArrayList<String> list = new ArrayList<String>();
        list.add("-Select-");
        int index = 0;
        for (Sub_DivisionList info: SubDivList){
            list.add(info.getSub_div_name());
            //if(benDetails.get)
        }
        ArrayAdapter adaptor = new ArrayAdapter(this, android.R.layout.simple_spinner_item, list);
        adaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_subdiv.setAdapter(adaptor);
        // if(benDetails.getPanchayatName()!=null)
        if(getIntent().hasExtra("KeyId"))

        {
            sp_subdiv.setSelection(((ArrayAdapter<String>) sp_subdiv.getAdapter()).getPosition(subdiv));

        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private boolean validateData() {
        View focusView = null;
        boolean validate = true;

        if(_vardivID.equalsIgnoreCase(""))
        {
            Toast.makeText(getApplicationContext(), "Please select building division", Toast.LENGTH_LONG).show();
            validate = false;
        }

//        if((sp_bulding_check != null && sp_bulding_check.getSelectedItem() !=null ))
//        {
//            if((String)sp_building_div.getSelectedItem()=="-select-")
//            {
//                Toast.makeText(NewEntryForm_Activity.this, "Please Select is there building", Toast.LENGTH_LONG).show();
//                validate = false;
//            }
//        }

        if(subdiv_id.equalsIgnoreCase(""))
        {
            Toast.makeText(getApplicationContext(), "Please select sub division", Toast.LENGTH_LONG).show();
            validate = false;
        }

        if(_vardistID.equalsIgnoreCase(""))
        {
            Toast.makeText(getApplicationContext(), "Please select district", Toast.LENGTH_LONG).show();
            validate = false;
        }

        if(block_id.equalsIgnoreCase(""))
        {
            Toast.makeText(getApplicationContext(), "Please select block", Toast.LENGTH_LONG).show();
            validate = false;
        }
//        if(panch_id.equalsIgnoreCase(""))
//        {
//            Toast.makeText(getApplicationContext(), "Please select ward/panchayat", Toast.LENGTH_LONG).show();
//            validate = false;
//        }


        if(edt_pincode.getText().toString().length()<6){
            Toast.makeText(getApplicationContext(), "Please enter correct pincode", Toast.LENGTH_LONG).show();
            validate = false;
        }

        if(edt_land_area.getText().toString().equalsIgnoreCase("")){
            Toast.makeText(getApplicationContext(), "Please enter area of land in sq feet", Toast.LENGTH_LONG).show();
            validate = false;
        }

        if(area_type_id.equalsIgnoreCase("")){
            Toast.makeText(getApplicationContext(), "Please select area type", Toast.LENGTH_LONG).show();
            validate = false;
        }

        if(property_type_id.equalsIgnoreCase("")){
            Toast.makeText(getApplicationContext(), "Please select property type", Toast.LENGTH_LONG).show();
            validate = false;
        }


        if(focusView != null && focusView.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
        return validate;

    }


    public void updateData()
    {
       // assetDetails=new NiwasInspectionEntity();

       assetDetails_edit.setId(String.valueOf(keyid));
       assetDetails_edit.setDiv_code(_vardivID);
       assetDetails_edit.setDiv_name(_vardivName);
       assetDetails_edit.setProperty_type(property_type_id);
       assetDetails_edit.setArea_type(area_type_id);
       assetDetails_edit.setDist_code(_vardistID);
       assetDetails_edit.setDist_name(_vardistName);
       assetDetails_edit.setBlk_code(block_id);
       assetDetails_edit.setBlk_name(block_name);
       assetDetails_edit.setSub_Div_code(subdiv_id);
       assetDetails_edit.setPanchayat_code(panch_id);
       assetDetails_edit.setPanchayat_name(panch_name);
       assetDetails_edit.setWard_id(ward_id);
       assetDetails_edit.setWard_name(ward_nm);
       assetDetails_edit.setIs_there_building(Is_building_Code);
       assetDetails_edit.setPincode(edt_pincode.getText().toString());
       assetDetails_edit.setThana_no(edt_thana.getText().toString());
       assetDetails_edit.setKahta_no(edt_khata.getText().toString());
       assetDetails_edit.setKhesra_no(edt_khesra.getText().toString());
       assetDetails_edit.setChauhaddi_north(edt_nrth_chauhaddi.getText().toString());
       assetDetails_edit.setChauhaddi_south(edt_south_chauhaddi.getText().toString());
       assetDetails_edit.setChauhaddi_east(edt_neast_chauhaddi.getText().toString());
       assetDetails_edit.setChauhaddi_west(edt_west_chauhaddi.getText().toString());
       assetDetails_edit.setLand_area(edt_land_area.getText().toString());
       assetDetails_edit.setNo_of_trees(edt_trees_no.getText().toString());
       assetDetails_edit.setTree_details(et_trees_details.getText().toString());
       assetDetails_edit.setAdmin_dept(edt_admin_dept.getText().toString());
       assetDetails_edit.setEntryby(user_id);



    }

    public void setdataforintent()
    {
        assetDetails=new NiwasInspectionEntity();
        assetDetails.setDiv_code(_vardivID);
        assetDetails.setDiv_name(_vardivName);
        assetDetails.setProperty_type(property_type_id);
        assetDetails.setArea_type(area_type_id);
        assetDetails.setDist_code(_vardistID);
        assetDetails.setDist_name(_vardistName);
        assetDetails.setBlk_code(block_id);
        assetDetails.setBlk_name(block_name);
        assetDetails.setSub_Div_code(subdiv_id);
        assetDetails.setPanchayat_code(panch_id);
        assetDetails.setPanchayat_name(panch_name);
        assetDetails.setWard_id(ward_id);
        assetDetails.setWard_name(ward_nm);
        assetDetails.setIs_there_building(Is_building_Code);
        assetDetails.setPincode(edt_pincode.getText().toString());
        assetDetails.setThana_no(edt_thana.getText().toString());
        assetDetails.setKahta_no(edt_khata.getText().toString());
        assetDetails.setKhesra_no(edt_khesra.getText().toString());
        assetDetails.setChauhaddi_north(edt_nrth_chauhaddi.getText().toString());
        assetDetails.setChauhaddi_south(edt_south_chauhaddi.getText().toString());
        assetDetails.setChauhaddi_east(edt_neast_chauhaddi.getText().toString());
        assetDetails.setChauhaddi_west(edt_west_chauhaddi.getText().toString());
        assetDetails.setLand_area(edt_land_area.getText().toString());
        assetDetails.setNo_of_trees(edt_trees_no.getText().toString());
        assetDetails.setTree_details(et_trees_details.getText().toString());
        assetDetails.setAdmin_dept(edt_admin_dept.getText().toString());
        assetDetails.setEntryby(user_id);
        if (isServer.equals("Yes")){
            assetDetails.setAsset_Id(assetDetails_edit.getAsset_Id());
        }
        else {
            assetDetails.setAsset_Id("");
        }



    }

    public void ShowEditEntryKhesra()
    {

        subdiv=dataBaseHelper.getNameFor("Sub_division_master","Sd_Code2","Sd_Name_En",assetDetails_edit.getSub_Div_code());
        wardname=assetDetails_edit.getWard_name();
        pan_name=assetDetails_edit.getPanchayat_name();
        edt_khata.setText(assetDetails_edit.getKahta_no());
        edt_khesra.setText(assetDetails_edit.getKhesra_no());
        edt_thana.setText(assetDetails_edit.getThana_no());
        edt_pincode.setText(assetDetails_edit.getPincode());
        edt_nrth_chauhaddi.setText(assetDetails_edit.getChauhaddi_north());
        edt_south_chauhaddi.setText(assetDetails_edit.getChauhaddi_south());
        edt_neast_chauhaddi.setText(assetDetails_edit.getChauhaddi_east());
        edt_west_chauhaddi.setText(assetDetails_edit.getChauhaddi_west());
        edt_trees_no.setText(assetDetails_edit.getNo_of_trees());
        edt_land_area.setText(assetDetails_edit.getLand_area());
        et_trees_details.setText(assetDetails_edit.getTree_details());

        loadSubDivSpinnerData(assetDetails_edit.getBlk_code());
        loadBlockSpinnerData(assetDetails_edit.getDist_code(),assetDetails_edit.getSub_Div_code());

        if (assetDetails_edit.getProperty_type().equals("0")){
            chk_open_land.setChecked(true);
            property_type_id="0";
            btn_proceed.setText("Update");
        }
        else if (assetDetails_edit.getProperty_type().equals("1"))
        {
            chk_existing_building.setChecked(true);
            property_type_id="1";
            btn_proceed.setText("Update building details");
        }

        if (assetDetails_edit.getArea_type().equals("0")){
            chk_urban.setChecked(true);
            area_type_id="0";
            sp_ward_pan.setVisibility(View.GONE);
            sp_ward.setVisibility(View.VISIBLE);
            loadWardSpinnerData();
        }
        else if (assetDetails_edit.getArea_type().equals("1"))
        {
            chk_rural.setChecked(true);
            area_type_id="1";
            sp_ward_pan.setVisibility(View.VISIBLE);
            sp_ward.setVisibility(View.GONE);
            loadPanchayatSpinnerData(assetDetails_edit.getBlk_code());
        }

        if (assetDetails_edit.getProperty_type().equals("1")) {
            if (assetDetails_edit.getIs_there_building().equals("Y")) {
                sp_bulding_check.setSelection(1);
                ll_admindept.setVisibility(View.VISIBLE);
                edt_admin_dept.setText(assetDetails_edit.getAdmin_dept());
            } else if (assetDetails_edit.getIs_there_building().equals("N")) {
                sp_bulding_check.setSelection(2);
                ll_admindept.setVisibility(View.GONE);
            }
        }
        else {
            ll_isthere_building.setVisibility(View.GONE);
        }

    }

    public void checkForEnglish(EditText etxt) {
        if (etxt.getText().length() > 0) {
            String s = etxt.getText().toString();
            if (isInputInEnglish(s)) {
                //OK
            } else {
                Toast.makeText(this, "Please type in english", Toast.LENGTH_SHORT).show();
                etxt.setText("");
            }
        }
    }
    public static boolean isInputInEnglish(String txtVal) {

        String regx = "^[A-Z0-9 ]+$";
        Pattern pattern = Pattern.compile(regx, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(txtVal);
        return matcher.find();
    }

    private TextWatcher inputTextWatcher1 = new TextWatcher() {

        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {

        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (edt_nrth_chauhaddi.getText().length() >0) {
                checkForEnglish(edt_nrth_chauhaddi);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {}
    };

    private TextWatcher inputTextWatcher2 = new TextWatcher() {

        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {

        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (edt_south_chauhaddi.getText().length() >0) {
                checkForEnglish(edt_south_chauhaddi);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {}
    };

    private TextWatcher inputTextWatcher3 = new TextWatcher() {

        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {

        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (edt_neast_chauhaddi.getText().length() >0) {
                checkForEnglish(edt_neast_chauhaddi);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {}
    };

    private TextWatcher inputTextWatcher4 = new TextWatcher() {

        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {

        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (edt_west_chauhaddi.getText().length() >0) {
                checkForEnglish(edt_west_chauhaddi);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {}
    };

    private TextWatcher inputTextWatcher5 = new TextWatcher() {

        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {

        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (et_trees_details.getText().length() >0) {
                checkForEnglish(et_trees_details);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {}
    };

    private TextWatcher inputTextWatcher6 = new TextWatcher() {

        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {

        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (edt_admin_dept.getText().length() >0) {
                checkForEnglish(edt_admin_dept);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {}
    };


}
