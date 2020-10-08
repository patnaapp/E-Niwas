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
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.exp.e_niwas.R;

import bih.in.e_niwas.database.DataBaseHelper;

public class BuildingDetails_Activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    EditText edt_building_name,edt_plinth_area,edt_builtup_area,edt_ofc_detail,edt_building_year,edt_remarks;
    CheckBox chk_judicial,chk_non_judicial,chk_residential,chk_non_residential,chk_gazetted,chk_non_gazetted,chk_mixedd;
    Spinner sp_buildingtype,sp_poolof_building,sp_buildin_status;
    ImageView img1,img2;
    private final static int CAMERA_PIC = 99;
    byte[] img,imgnew;
    Bitmap bmp,bmp1;
    private String latitude = "";
    private String longitude = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building_details_);
        initialise();

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
        img1=findViewById(R.id.img1);
        img2=findViewById(R.id.img2);

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

                            latitude = String.valueOf(data.getStringExtra("Lat"));
                            longitude = String.valueOf(data.getStringExtra("Lng"));
                            break;
                        case 2:
                            imgnew = imgData;
                            Bitmap bmpImg2 = BitmapFactory.decodeByteArray(imgData, 0, imgData.length);
                            bmp1 = bmpImg2;
                            img2.setImageBitmap(bmpImg2);
                            break;
                    }
                }
        }
    }


}
