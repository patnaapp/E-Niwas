package bih.in.e_niwas.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;

import com.exp.e_niwas.R;

import java.io.IOException;
import java.util.ArrayList;

import bih.in.e_niwas.database.DataBaseHelper;
import bih.in.e_niwas.entity.Block;
import bih.in.e_niwas.entity.District;
import bih.in.e_niwas.entity.DivisionList;
import bih.in.e_niwas.entity.PanchayatData;
import bih.in.e_niwas.entity.PanchayatEntity;
import bih.in.e_niwas.utility.CommonPref;

public class NewEntryForm_Activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    Button btn_proceed;
    Spinner sp_building_div,sp_dist,sp_subdiv,sp_block,sp_ward_pan,sp_bulding_check;
    CheckBox chk_open_land,chk_existing_building,chk_urban,chk_rural;
    EditText edt_pincode,edt_thana,edt_khata,edt_khesra,edt_nrth_chauhaddi,edt_south_chauhaddi,edt_neast_chauhaddi,edt_west_chauhaddi,edt_land_area,edt_trees_no,et_trees_details;
    ArrayList<DivisionList> DivList = new ArrayList<DivisionList>();
    ArrayList<District> DistrictList = new ArrayList<District>();
    ArrayList<PanchayatData> PanchayatList = new ArrayList<PanchayatData>();
    ArrayList<Block> BlockList = new ArrayList<Block>();
    DataBaseHelper dataBaseHelper;
    ArrayAdapter<String> divtadapter;
    ArrayAdapter<String> distadapter;
    ArrayAdapter<String> blockadapter;
    ArrayAdapter<String> panchayatadapter;
    String _vardivID="",_vardivName="",_vardistID="",_vardistName="",block_id="",block_name="",panch_id="",panch_name="";
    String ben_type_aangan[] = {"-select-","YES","NO"};
    String Is_Building_Name="",Is_building_Code="";
    ArrayAdapter ben_type_aangan_aaray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_entry_form_);

        dataBaseHelper = new DataBaseHelper(NewEntryForm_Activity.this);
        try
        {
            dataBaseHelper.createDataBase();
        }
        catch (IOException ioe)
        {
            throw new Error("Unable to create database");
        }

        try {

            dataBaseHelper.openDataBase();
            //createTable();
            //modifyTable();
        } catch (SQLException sqle) {

            throw sqle;

        }
        initialisation();
        sp_bulding_check.setOnItemSelectedListener(this);
        loadDivisionSpinnerdata();
        loadDistrictSpinnerdata();

        sp_building_div.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                if (arg2 > 0) {

                    DivisionList dist = DivList.get(arg2-1);
                    _vardivID = dist.getDivId();
                    _vardivName = dist.getDivName();
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
                if (arg2 > 0) {

                    District dist = DistrictList.get(arg2-1);
                    _vardistID = dist.get_DistCode();
                    _vardistName = dist.get_DistName();
                    loadBlockSpinnerData(_vardistID);
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

        sp_bulding_check.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("arg2",""+position);
                if (position > 0) {
                    Is_Building_Name = ben_type_aangan[position].toString();

                    if (Is_Building_Name.equals("पुरुष")) {

                        Is_building_Code = "Y";
                    } else if (Is_Building_Name.equals("महिला")) {

                        Is_building_Code = "N";
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
                if (b) {
                    chk_existing_building.setChecked(false);

                }
            }
        });
        chk_existing_building.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    chk_open_land.setChecked(false);

                }
            }
        });

        chk_urban.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    chk_rural.setChecked(false);

                }
            }
        });
        chk_rural.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    chk_urban.setChecked(false);

                }
            }
        });

        btn_proceed.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i=new Intent(NewEntryForm_Activity.this,BuildingDetails_Activity.class);
                startActivity(i);
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
        sp_bulding_check=findViewById(R.id.sp_bulding_check);

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
        edt_south_chauhaddi=findViewById(R.id.edt_south_chauhaddi);
        edt_neast_chauhaddi=findViewById(R.id.edt_neast_chauhaddi);
        edt_west_chauhaddi=findViewById(R.id.edt_west_chauhaddi);
        edt_land_area=findViewById(R.id.edt_land_area);
        edt_trees_no=findViewById(R.id.edt_trees_no);
        et_trees_details=findViewById(R.id.et_trees_details);
    }

    public void loadDivisionSpinnerdata() {

        DivList = dataBaseHelper.getDivisionLocal();
        String[] typeNameArray = new String[DivList.size() + 1];
        typeNameArray[0] = "-Select-";
        int i = 1;
        for (DivisionList type : DivList) {
            typeNameArray[i] = type.getDivName();
            i++;
        }
        divtadapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, typeNameArray);
        divtadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_building_div.setAdapter(divtadapter);
        int setID=0;
        for ( i = 0; i < DivList.size(); i++) {

         //   if (DivList.get(i).getDivId().equalsIgnoreCase(CommonPref.getUserDetails(NewEntryForm_Activity.this).get_DivisionID())) {
            if (DivList.get(i).getDivId().equalsIgnoreCase("213")) {
                setID = i;
            }
            if(setID!=0) {
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
        for (District type : DistrictList) {
            typeNameArray[i] = type.get_DistName();
            i++;
        }
        distadapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, typeNameArray);
        distadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_dist.setAdapter(distadapter);
        int setID=0;
        for ( i = 0; i < DistrictList.size(); i++) {

            //   if (DivList.get(i).getDivId().equalsIgnoreCase(CommonPref.getUserDetails(NewEntryForm_Activity.this).get_DivisionID())) {
            if (DistrictList.get(i).get_DistCode().equalsIgnoreCase("213")) {
                setID = i;
            }
            if(setID!=0) {
                sp_dist.setSelection(setID+1);
                sp_dist.setEnabled(false);
            }
        }

//        if(getIntent().hasExtra("KeyId"))
//        {
//            sp_building_div.setSelection(((ArrayAdapter<String>) sp_building_div.getAdapter()).getPosition(_spin_div_nm));
//        }

    }

    public void loadBlockSpinnerData(String district){
        BlockList.clear();
        BlockList = dataBaseHelper.getBlock(district);
        ArrayList<String> list = new ArrayList<String>();
        list.add("-Select-");
        int index = 0;
        for (Block info: BlockList){
            list.add(info.getBlockName());
            //if(benDetails.get)
        }
        ArrayAdapter adaptor = new ArrayAdapter(this, android.R.layout.simple_spinner_item, list);
        adaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_block.setAdapter(adaptor);
//        if(benDetails.getBlockName()!=null){
//            spn_block_name.setSelection(((ArrayAdapter<String>) spn_block_name.getAdapter()).getPosition(benDetails.getBlockName()));
//
//        }
      //  sp_block.setEnabled(false);
    }

    public void loadPanchayatSpinnerData(String blockid)
    {
        PanchayatList = dataBaseHelper.getPanchayatLocal(blockid);
        ArrayList<String> list = new ArrayList<String>();
        list.add("-Select-");
        int index = 0;
        for (PanchayatData info: PanchayatList){
            list.add(info.getPname());
            //if(benDetails.get)
        }
        ArrayAdapter adaptor = new ArrayAdapter(this, android.R.layout.simple_spinner_item, list);
        adaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_ward_pan.setAdapter(adaptor);
//        if(benDetails.getPanchayatName()!=null)
//        {
//            spn_panch_name.setSelection(((ArrayAdapter<String>) spn_panch_name.getAdapter()).getPosition(benDetails.getPanchayatName()));
//
//        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
