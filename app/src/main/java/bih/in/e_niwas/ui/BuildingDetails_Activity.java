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

import bih.in.e_niwas.database.DataBaseHelper;
import bih.in.e_niwas.entity.NiwasInspectionEntity;
import bih.in.e_niwas.utility.CommonPref;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building_details_);
        initialise();
        assetDetails=(NiwasInspectionEntity)getIntent().getSerializableExtra("data");

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


                }
            }
        });
        chk_non_judicial.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    chk_judicial.setChecked(false);

                }
            }
        });


        chk_residential.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    chk_non_residential.setChecked(false);
                }
            }
        });
        chk_non_residential.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    chk_residential.setChecked(false);
                }
            }
        });

        chk_gazetted.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    chk_non_gazetted.setChecked(false);
                    chk_mixedd.setChecked(false);
                                    }
            }
        });
        chk_non_gazetted.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    chk_gazetted.setChecked(false);
                    chk_mixedd.setChecked(false);
                                }
            }
        });

        chk_mixedd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    chk_gazetted.setChecked(false);
                    chk_non_gazetted.setChecked(false);

                }
            }
        });



        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateData())
                {
                   InsertIntoLocal();
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

        if(building_type_class_id.equalsIgnoreCase(""))
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

        newEntryEntity.setBuilding_name(edt_building_name.getText().toString());
        newEntryEntity.setBuilding_type(_var_type_of_building);
        newEntryEntity.setBuilding_is(_var_building_is);
        newEntryEntity.setGazeted_nongazeted(var_gazetted_nongazetted);
        newEntryEntity.setBuilding_type_class(building_type_class_id);
        newEntryEntity.setPool_building(building_pool_id);
        newEntryEntity.setPlinth_area(edt_plinth_area.getText().toString());
        newEntryEntity.setBuiltup_area(edt_builtup_area.getText().toString());
        newEntryEntity.setOffice_details(edt_ofc_detail.getText().toString());
        newEntryEntity.setYear_of_completion(edt_building_year.getText().toString());
        newEntryEntity.setBuilding_status(building_status_id);
        newEntryEntity.setRemarks(edt_remarks.getText().toString());
        newEntryEntity.setImage1(img1String);
        newEntryEntity.setImage2(img2String);
        newEntryEntity.setLat1(latitude);
        newEntryEntity.setLong1(longitude);
        newEntryEntity.setLat2(latitude2);
        newEntryEntity.setLong2(longitude2);

        newEntryEntity.setEntryby(CommonPref.getUserDetails(getApplicationContext()).getUserID());
        if(str_img.equalsIgnoreCase("Y")) {



                id = new DataBaseHelper(BuildingDetails_Activity.this).InsertAssetEntry_New(BuildingDetails_Activity.this, newEntryEntity,assetDetails);

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

}
