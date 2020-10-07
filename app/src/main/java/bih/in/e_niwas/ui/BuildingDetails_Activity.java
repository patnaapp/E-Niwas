package bih.in.e_niwas.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.exp.e_niwas.R;

public class BuildingDetails_Activity extends AppCompatActivity {

    EditText edt_building_name,edt_plinth_area,edt_builtup_area,edt_ofc_detail,edt_building_year,edt_remarks;
    CheckBox chk_judicial,chk_non_judicial,chk_residential,chk_non_residential,chk_gazetted,chk_non_gazetted,chk_mixedd;
    Spinner sp_buildingtype,sp_poolof_building,sp_buildin_status;
    ImageView img1,img2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building_details_);
    }
}
