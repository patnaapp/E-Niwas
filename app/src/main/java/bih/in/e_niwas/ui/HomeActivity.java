package bih.in.e_niwas.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.exp.e_niwas.R;

public class HomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void on_NewEntry(View view)
    {
        Intent i=new Intent(HomeActivity.this,NewEntryForm_Activity.class);
        startActivity(i);
    }

    public void on_EditEntry(View view)
    {

    }
}
