package bih.in.e_niwas.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.exp.e_niwas.R;

public class NewEntryForm_Activity extends AppCompatActivity {

    Button btn_proceed;
    Spinner sp_building_div,sp_dist,sp_subdiv,sp_block,sp_ward_pan,sp_bulding_check;
    CheckBox chk_open_land,chk_existing_building,chk_urban,chk_rural;
    EditText edt_pincode,edt_thana,edt_khata,edt_khesra,edt_nrth_chauhaddi,edt_south_chauhaddi,edt_neast_chauhaddi,edt_west_chauhaddi,edt_land_area,edt_trees_no,et_trees_details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_entry_form_);
        initialisation();


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
    }
}
