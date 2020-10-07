package bih.in.e_niwas.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.exp.e_niwas.R;

public class NewEntryForm_Activity extends AppCompatActivity {

    Button btn_proceed;

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
