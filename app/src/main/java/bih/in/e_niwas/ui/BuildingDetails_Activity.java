package bih.in.e_niwas.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Base64;
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
import android.widget.Spinner;
import android.widget.Toast;

import com.exp.e_niwas.R;

import java.util.ArrayList;

import bih.in.e_niwas.database.DataBaseHelper;
import bih.in.e_niwas.entity.Block;
import bih.in.e_niwas.entity.Item_MasterEntity;
import bih.in.e_niwas.entity.NiwasInspectionEntity;
import bih.in.e_niwas.utility.CommonPref;
import bih.in.e_niwas.utility.Utiilties;

public class BuildingDetails_Activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    EditText edt_building_name,edt_plinth_area,edt_builtup_area,edt_ofc_detail,edt_building_year,edt_remarks;
    CheckBox chk_judicial,chk_non_judicial,chk_residential,chk_non_residential,chk_gazetted,chk_non_gazetted,chk_mixedd;
    Spinner sp_buildingtype,sp_poolof_building,sp_buildin_status;
    ImageView img1,img2;
    private final static int CAMERA_PIC = 99;
    byte[] img,imgnew;
    Bitmap bmp,bmp1;
    private String latitude = "",latitude2="";
    private String longitude = "",longitude2="";
    NiwasInspectionEntity assetDetails;
    String building_type_class_id="",building_type_class_Nm="",_var_type_of_building="",_var_building_is="",var_gazetted_nongazetted="",building_status_id="",building_status_nm="",building_pool_id="",building_pool_nm="";
    Button btn_save;
    String ben_type_buldingstatus[] = {"-select-","GOOD","Satisfactory-Normal Repair","UnSatisfactory-Requires major repair","Dilapidated/Abadoned-to be demolished"};

    String status_id="",status_Nm="";
    ArrayAdapter ben_type_status_aaray;
    String str_img="N",img1String="",img2String="";
    String keyid = "";
    boolean edit;
    NiwasInspectionEntity assetDetails_edit;
    int ThumbnailSize =500;
    ArrayList<Item_MasterEntity> Item_List = new ArrayList<Item_MasterEntity>();
    ArrayList<Item_MasterEntity> Item_List1 = new ArrayList<Item_MasterEntity>();
    DataBaseHelper dataBaseHelper;
    String pool_id="",pool_nm="",buildintype_id="",buildintype_nm="";

    String var_buildingpool="",building_type="",status="";
    String _groupid="",_second_grupi_id="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building_details_);
        dataBaseHelper = new DataBaseHelper(BuildingDetails_Activity.this);
        initialise();
        try {


            keyid = getIntent().getExtras().getString("KeyId");
            String isEdit = "";
            isEdit = getIntent().getExtras().getString("isEdit");
            Log.d("kvfrgv", "" + keyid + "" + isEdit);
            if (Integer.parseInt(keyid) > 0 && isEdit.equals("Yes")) {
                edit = true;
                NiwasInspectionEntity assetDetails_editImage=dataBaseHelper.getimage(keyid);
                assetDetails_edit=(NiwasInspectionEntity)getIntent().getSerializableExtra("assetdata_edit");
                assetDetails_edit.setImage1(assetDetails_editImage.getImage1());
                assetDetails_edit.setImage2(assetDetails_editImage.getImage2());

                Log.e("id", "" + assetDetails_edit.getId());
                ShowEditEntryKhesra();

            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        loadpoolOfBuildingSpinnerData();



        assetDetails=(NiwasInspectionEntity)getIntent().getSerializableExtra("data");

        sp_buildingtype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    buildintype_id = Item_List.get(position-1).getItem_ID();
                    buildintype_nm = Item_List.get(position-1).getItem_Name();

                } else {
                    buildintype_id = "";
                    buildintype_nm = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                buildintype_id = "";
                buildintype_nm = "";
            }

        });

        sp_poolof_building.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    pool_id = Item_List1.get(position-1).getItem_ID();
                    pool_nm = Item_List1.get(position-1).getItem_Name();

                } else {
                    pool_id = "";
                    pool_nm = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                pool_id = "";
                pool_nm = "";
            }

        });

        sp_buildin_status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("arg2",""+position);
                if (position > 0) {
                    status_Nm = ben_type_buldingstatus[position].toString();

                    if (status_Nm.equals("GOOD")) {

                        status_id = "1";

                    } else if (status_Nm.equals("Satisfactory-Normal Repair")) {

                        status_id = "2";

                    }
                    else if (status_Nm.equals("UnSatisfactory-Requires major repair")) {

                        status_id = "3";

                    }
                    else if (status_Nm.equals("Dilapidated/Abadoned-to be demolished")) {

                        status_id = "4";

                    }

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }

        });

        chk_judicial.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    chk_non_judicial.setChecked(false);

                    _var_type_of_building="1";
                }
            }
        });
        chk_non_judicial.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    chk_judicial.setChecked(false);
                    _var_type_of_building="2";
                }
            }
        });


        chk_residential.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    chk_non_residential.setChecked(false);
                    _var_building_is="1";
                }
            }
        });
        chk_non_residential.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    chk_residential.setChecked(false);
                    _var_building_is="2";
                    loadBuildingTypeSpinnerData("17","0");
                }
            }
        });

        chk_gazetted.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    chk_non_gazetted.setChecked(false);
                    var_gazetted_nongazetted="3";
                    chk_mixedd.setChecked(false);
                    loadBuildingTypeSpinnerData("3","0");
                }
            }
        });
        chk_non_gazetted.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    chk_gazetted.setChecked(false);
                    chk_mixedd.setChecked(false);
                    var_gazetted_nongazetted="6";
                    loadBuildingTypeSpinnerData("6","0");
                }
            }
        });

        chk_mixedd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    chk_gazetted.setChecked(false);
                    chk_non_gazetted.setChecked(false);
                    var_gazetted_nongazetted="M";
                    loadBuildingTypeSpinnerData("6","3");
                }
            }
        });



        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (btn_save.getText().toString().equals("UPDATE ASSET RECORD"))
                {
                    UpdateIntoLocal();
                }
                else {
                    if(validateData())
                    {
                        InsertIntoLocal();
                    }
                }

            }
        });
    }

    public void initialise()
    {
        edt_building_name=findViewById(R.id.edt_building_name);
        edt_plinth_area=findViewById(R.id.edt_plinth_area);
        edt_builtup_area=findViewById(R.id.edt_builtup_area);
        edt_ofc_detail=findViewById(R.id.edt_ofc_detail);
        edt_building_year=findViewById(R.id.edt_building_year);
        edt_remarks=findViewById(R.id.edt_remarks);

        edt_remarks=findViewById(R.id.edt_remarks);
        chk_judicial=findViewById(R.id.chk_judicial);
        chk_non_judicial=findViewById(R.id.chk_non_judicial);
        chk_residential=findViewById(R.id.chk_residential);
        chk_non_residential=findViewById(R.id.chk_non_residential);
        chk_gazetted=findViewById(R.id.chk_gazetted);
        chk_non_gazetted=findViewById(R.id.chk_non_gazetted);
        chk_mixedd=findViewById(R.id.chk_mixedd);
        sp_buildingtype=findViewById(R.id.sp_buildingtype);
        sp_poolof_building=findViewById(R.id.sp_poolof_building);
        sp_buildin_status=findViewById(R.id.sp_buildin_status);
        ben_type_status_aaray = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, ben_type_buldingstatus);
        sp_buildin_status.setAdapter(ben_type_status_aaray);

        img1=findViewById(R.id.img1);
        img2=findViewById(R.id.img2);
        btn_save=findViewById(R.id.btn_save);

    }

    public void onClick(View view) {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

            buildAlertMessageNoGps();
        }
        if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER) == true) {

            Intent iCamera = new Intent(getApplicationContext(),CameraActivity.class);
            if (view.equals(img1))
                iCamera.putExtra("KEY_PIC", "1");

            else if (view.equals(img2))
                iCamera.putExtra("KEY_PIC", "2");


            startActivityForResult(iCamera, CAMERA_PIC);

        }
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.eniwaslogo);
        builder.setTitle("GPS ?");
        builder.setMessage("(GPS)जीपीएस बंद है\nस्थान के अक्षांश और देशांतर प्राप्त करने के लिए कृपया जीपीएस चालू करें")
//		builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("[(GPS) जीपीएस चालू करे ]", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("[ बंद करें ]", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CAMERA_PIC:
                if (resultCode == RESULT_OK) {
                    byte[] imgData = data.getByteArrayExtra("CapturedImage");
                    // byte[] imgData = data.getByteArrayExtra("CapturedImage");
                    DataBaseHelper placeData = new DataBaseHelper(getApplicationContext());

                    switch (data.getIntExtra("KEY_PIC", 0)) {
                        case 1:
                            img = imgData;
                            Bitmap bmpImg = BitmapFactory.decodeByteArray(imgData, 0, imgData.length);
                            bmp = bmpImg;
                            img1.setImageBitmap(bmpImg);
                            img1String = Base64.encodeToString(img,
                                    Base64.NO_WRAP);

                            latitude = String.valueOf(data.getStringExtra("Lat"));
                            longitude = String.valueOf(data.getStringExtra("Lng"));
                            break;
                        case 2:
                            imgnew = imgData;
                            img2String = Base64.encodeToString(imgnew,
                                    Base64.NO_WRAP);
                            Bitmap bmpImg2 = BitmapFactory.decodeByteArray(imgData, 0, imgData.length);
                            latitude2 = String.valueOf(data.getStringExtra("Lat"));
                            longitude2 = String.valueOf(data.getStringExtra("Lng"));
                            bmp1 = bmpImg2;
                            img2.setImageBitmap(bmpImg2);
                            str_img="Y";
                            break;
                    }
                }
        }
    }


    private boolean validateData() {
        View focusView = null;
        boolean validate = true;

        if(buildintype_id.equalsIgnoreCase(""))
        {
            Toast.makeText(getApplicationContext(), "Please select building type", Toast.LENGTH_LONG).show();
            validate = false;
        }

        if(_var_type_of_building.equalsIgnoreCase(""))
        {
            Toast.makeText(getApplicationContext(), "Please select type of building", Toast.LENGTH_LONG).show();
            validate = false;
        }

        if(_var_building_is.equalsIgnoreCase(""))
        {
            Toast.makeText(getApplicationContext(), "Please select building is", Toast.LENGTH_LONG).show();
            validate = false;
        }


        if(edt_building_name.getText().toString().equalsIgnoreCase("")){
            Toast.makeText(getApplicationContext(), "Please enter correct building name", Toast.LENGTH_LONG).show();
            validate = false;
        }

        if(edt_plinth_area.getText().toString().equalsIgnoreCase("")){
            Toast.makeText(getApplicationContext(), "Please enter plinth area", Toast.LENGTH_LONG).show();
            validate = false;
        }



        if(focusView != null && focusView.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
        return validate;

    }


    private void InsertIntoLocal()
    {

        long id = 0;
        //setValue();
        DataBaseHelper placeData = new DataBaseHelper(BuildingDetails_Activity.this);
        NiwasInspectionEntity newEntryEntity=new NiwasInspectionEntity();

        assetDetails.setBuilding_name(edt_building_name.getText().toString());
        assetDetails.setBuilding_type(_var_type_of_building);
        assetDetails.setBuilding_is(_var_building_is);
        assetDetails.setGazeted_nongazeted(var_gazetted_nongazetted);
        assetDetails.setBuilding_type_class(buildintype_id);
        assetDetails.setPool_building(pool_id);
        assetDetails.setPlinth_area(edt_plinth_area.getText().toString());
        assetDetails.setBuiltup_area(edt_builtup_area.getText().toString());
        assetDetails.setOffice_details(edt_ofc_detail.getText().toString());
        assetDetails.setYear_of_completion(edt_building_year.getText().toString());
        assetDetails.setBuilding_status(status_id);
        assetDetails.setRemarks(edt_remarks.getText().toString());
        assetDetails.setImage1(img1String);
        assetDetails.setImage2(img2String);
        assetDetails.setLat1(latitude);
        assetDetails.setLong1(longitude);
        assetDetails.setLat2(latitude2);
        assetDetails.setLong2(longitude2);




        newEntryEntity.setEntryby(CommonPref.getUserDetails(getApplicationContext()).getUserID());
        if(str_img.equalsIgnoreCase("Y")) {

            id = new DataBaseHelper(BuildingDetails_Activity.this).InsertAssetEntry_New(assetDetails);

            if (id > 0) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(BuildingDetails_Activity.this);
                alertDialog.setTitle("Saved");
                alertDialog.setMessage("Data saved successfully");

                alertDialog.setNegativeButton("[OK]", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "Data saved successfully", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
                alertDialog.show();

            } else {
                Toast.makeText(getApplicationContext(), "Not Success", Toast.LENGTH_LONG).show();
            }


        }else {
            Toast.makeText(BuildingDetails_Activity.this,"Please Take photo",Toast.LENGTH_SHORT).show();
        }
    }


    public void ShowEditEntryKhesra() {

        btn_save.setText("UPDATE ASSET RECORD");
//        building_type=dataBaseHelper.getNameFor("Item_Master","Item_Id","Item_Name",assetDetails_edit.getBuilding_type_class());
//        var_buildingpool=dataBaseHelper.getNameFor("Item_Master","Item_Id","Item_Name",assetDetails_edit.getPool_building());
        edt_building_name.setText(assetDetails_edit.getBuilding_name());
        edt_plinth_area.setText(assetDetails_edit.getPlinth_area());
        edt_builtup_area.setText(assetDetails_edit.getBuiltup_area());
        edt_ofc_detail.setText(assetDetails_edit.getOffice_details());
        edt_building_year.setText(assetDetails_edit.getYear_of_completion());
        edt_remarks.setText(assetDetails_edit.getRemarks());
        if (assetDetails_edit.getBuilding_type().equals("1")){
            chk_judicial.setChecked(true);
        }
        else if(assetDetails_edit.getBuilding_type().equals("2")){
            chk_non_judicial.setChecked(true);
        }
        if (assetDetails_edit.getBuilding_is().equals("1")){
            chk_residential.setChecked(true);
        }
        else if(assetDetails_edit.getBuilding_is().equals("2")){
            chk_non_residential.setChecked(true);
            _groupid="17";
            _second_grupi_id="0";
        }


        if (assetDetails_edit.getGazeted_nongazeted().equals("3")){
            chk_gazetted.setChecked(true);
            _groupid="3";
            _second_grupi_id="0";
        }
        else if(assetDetails_edit.getGazeted_nongazeted().equals("6")){
            chk_non_gazetted.setChecked(true);
            _groupid="6";
            _second_grupi_id="0";
        }

        else if(assetDetails_edit.getGazeted_nongazeted().equals("M")){
            chk_mixedd.setChecked(true);
            _groupid="6";
            _second_grupi_id="3";
        }
        latitude = assetDetails_edit.getLat1();
        latitude2 = assetDetails_edit.getLat2();

        longitude = assetDetails_edit.getLong1();
        longitude2 = assetDetails_edit.getLong2();

        if (assetDetails_edit != null) {

            str_img = "N";

            img1String = assetDetails_edit.getImage1();
            img2String = assetDetails_edit.getImage2();

            img = Base64.decode(img1String, Base64.DEFAULT);
            imgnew = Base64.decode(img2String, Base64.DEFAULT);



            if (!img.equals(null))
            {
                Bitmap bmpImg = BitmapFactory.decodeByteArray(img, 0, img.length);
                img1.setScaleType(ImageView.ScaleType.FIT_XY);
                img1.setImageBitmap(Utiilties.GenerateThumbnail(bmpImg, ThumbnailSize, ThumbnailSize));

            }

            if (!imgnew.equals(null))
            {
                Bitmap bmpImg2 = BitmapFactory.decodeByteArray(imgnew, 0, imgnew.length);
                img2.setScaleType(ImageView.ScaleType.FIT_XY);
                img2.setImageBitmap(Utiilties.GenerateThumbnail(bmpImg2, ThumbnailSize, ThumbnailSize));

            }


        }

        if (assetDetails_edit.getBuilding_status().equals("1")){
            sp_buildin_status.setSelection(1);
        }
        else if (assetDetails_edit.getBuilding_status().equals("2")){
            sp_buildin_status.setSelection(2);
        }
        else if (assetDetails_edit.getBuilding_status().equals("3")){
            sp_buildin_status.setSelection(3);
        }
        else if (assetDetails_edit.getBuilding_status().equals("4")){
            sp_buildin_status.setSelection(4);
        }
        loadpoolOfBuildingSpinnerData();

        loadBuildingTypeSpinnerData(_groupid,_second_grupi_id);
    }

    public void UpdateIntoLocal() {

        long id = 0;
        DataBaseHelper placeData = new DataBaseHelper(BuildingDetails_Activity.this);
        NiwasInspectionEntity newEntryEntity=new NiwasInspectionEntity();

      //  assetDetails.setId(assetDetails_edit.getId());

        assetDetails_edit.setBuilding_name(edt_building_name.getText().toString());
        assetDetails_edit.setBuilding_type(_var_type_of_building);
        assetDetails_edit.setBuilding_is(_var_building_is);
        assetDetails_edit.setGazeted_nongazeted(var_gazetted_nongazetted);
        assetDetails_edit.setBuilding_type_class(buildintype_id);
        assetDetails_edit.setPool_building(pool_id);
        assetDetails_edit.setPlinth_area(edt_plinth_area.getText().toString());
        assetDetails_edit.setBuiltup_area(edt_builtup_area.getText().toString());
        assetDetails_edit.setOffice_details(edt_ofc_detail.getText().toString());
        assetDetails_edit.setYear_of_completion(edt_building_year.getText().toString());
        assetDetails_edit.setBuilding_status(status_id);
        assetDetails_edit.setRemarks(edt_remarks.getText().toString());
        assetDetails_edit.setImage1(img1String);
        assetDetails_edit.setImage2(img2String);
        assetDetails_edit.setLat1(latitude);
        assetDetails_edit.setLong1(longitude);
        assetDetails_edit.setLat2(latitude2);
        assetDetails_edit.setLong2(longitude2);

        id = new DataBaseHelper(BuildingDetails_Activity.this).updateNewEntryKhesradetails(assetDetails_edit);

        if (id > 0) {
            Toast.makeText(getApplicationContext(), "Data updated successfully", Toast.LENGTH_LONG).show();
            Intent i=new Intent(BuildingDetails_Activity.this,HomeActivity.class);
            startActivity(i);

            finish();

        } else {
            Toast.makeText(getApplicationContext(), "अपडेट नहीं किया गया", Toast.LENGTH_LONG).show();
        }


    }


    public void loadpoolOfBuildingSpinnerData(){
        Item_List1.clear();
        Item_List1 = dataBaseHelper.getItemList("8","0");
        ArrayList<String> list = new ArrayList<String>();
        list.add("-Select-");
        int index = 0;
        String name="";
        for (Item_MasterEntity info: Item_List1){
            if(getIntent().hasExtra("KeyId") && info.getItem_ID().equals(assetDetails_edit.getPool_building())){
                name= info.getItem_Name();
            }
            list.add(info.getItem_Name());
            //if(benDetails.get)
        }
        ArrayAdapter adaptor = new ArrayAdapter(this, android.R.layout.simple_spinner_item, list);
        adaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_poolof_building.setAdapter(adaptor);
        if(getIntent().hasExtra("KeyId"))
        {
            sp_poolof_building.setSelection(((ArrayAdapter<String>) sp_poolof_building.getAdapter()).getPosition(name));

        }
        //  sp_block.setEnabled(false);
    }

    public void loadBuildingTypeSpinnerData(String groupid,String secondgroupid){
        Item_List.clear();

        Item_List = dataBaseHelper.getItemList(groupid,secondgroupid);
        ArrayList<String> list = new ArrayList<String>();
        list.add("-Select-");
        int index = 0;
   String name="";
        for (Item_MasterEntity info: Item_List){
            if(getIntent().hasExtra("KeyId") && info.getItem_ID().equals(assetDetails_edit.getBuilding_type_class())){
                name= info.getItem_Name();
            }
            list.add(info.getItem_Name());
            //if(benDetails.get)
        }
        ArrayAdapter adaptor = new ArrayAdapter(this, android.R.layout.simple_spinner_item, list);
        adaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_buildingtype.setAdapter(adaptor);
        if(getIntent().hasExtra("KeyId"))
        {
            sp_buildingtype.setSelection(((ArrayAdapter<String>) sp_buildingtype.getAdapter()).getPosition(name));

        }
        //  sp_block.setEnabled(false);
    }

}
