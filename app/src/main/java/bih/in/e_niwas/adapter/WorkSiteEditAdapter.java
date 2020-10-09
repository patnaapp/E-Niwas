package bih.in.e_niwas.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.exp.e_niwas.R;

import java.util.ArrayList;

import bih.in.e_niwas.database.DataBaseHelper;
import bih.in.e_niwas.entity.NiwasInspectionEntity;
import bih.in.e_niwas.ui.NewEntryForm_Activity;

public class WorkSiteEditAdapter extends RecyclerView.Adapter<WorkSiteEditAdapter.ViewHolder> {

    Activity activity;
    LayoutInflater mInflater;
    ArrayList<NiwasInspectionEntity> ThrList=new ArrayList<>();

    Boolean isShowDetail = false;
    String singlerowid="",user_id="";
    // WorkReqrmntListener listener;

    public WorkSiteEditAdapter(Activity listViewshowedit, ArrayList<NiwasInspectionEntity> rlist) {
        this.activity=listViewshowedit;
        this.ThrList=rlist;
        mInflater = (LayoutInflater)activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        //this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.adaptor_work_site_edit, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final NiwasInspectionEntity info = ThrList.get(position);
        user_id = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext()).getString("uid", "");


        holder.tv_slno.setText(String.valueOf(position+1));
        holder.tv_div_name.setText(info.getDiv_name());
        if (info.getArea_type().equals("U"))
        {
            holder.tv_areaType.setText("Urban");
        }
        else if (info.getArea_type().equals("R"))
        {
            holder.tv_areaType.setText("Rural");
        }

        if (info.getProperty_type().equals("1"))
        {
            holder.tv_property_TYpe.setText("Open Land");
        }
        else if (info.getProperty_type().equals("2"))
        {
            holder.tv_property_TYpe.setText("Land with existing building");
        }

        holder.tv_pincode.setText(info.getPincode());
        holder.tv_thana.setText(info.getThana_no());
        holder.tv_khata.setText(info.getKahta_no());
        holder.tv_khesra.setText(info.getKhesra_no());


        holder.iv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(activity, NewEntryForm_Activity.class);
               // i.putExtra("assetdata",ThrList.get(position));
                i.putExtra("KeyId",info.getId());
                i.putExtra("isEdit", "Yes");
                activity.startActivity(i);

            }
        });
        holder.iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.iv_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);

                builder.setTitle("Data upload");
                builder.setMessage("Are you sure want to Upload the Record");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DataBaseHelper dataBaseHelper = new DataBaseHelper(activity);
                        singlerowid = (ThrList.get(position).getId());
                        DataBaseHelper dbHelper = new DataBaseHelper(activity);
                        dialog.dismiss();

                        ArrayList<NiwasInspectionEntity> dataProgress = dbHelper.getAllNewEntryDetail(singlerowid, user_id);
                        if (dataProgress.size() > 0) {

                            for (NiwasInspectionEntity data : dataProgress) {

                               // new UPLOADDATA(data).execute();
                            }

                        }
                    }

                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }


        });


    }

    @Override
    public int getItemCount()
    {
        return ThrList.size();
    }

    public class ViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_slno,tv_skill_cat,tv_skill_name,tv_no_perosn,tv_gendar,tv_start_date,tv_exp,tv_exp_max,tv_salary,tv_salary_max,tv_status1;
        ImageView iv_edit,iv_delete,iv_upload;
        TextView tv_div_name,tv_areaType,tv_property_TYpe,tv_pincode,tv_thana,tv_khata,tv_khesra;

        ViewHolder(View itemView) {
            super(itemView);
            tv_slno=itemView.findViewById(R.id.tv_slno);
            tv_div_name=itemView.findViewById(R.id.tv_div_name);
            tv_areaType=itemView.findViewById(R.id.tv_areaType);
            tv_property_TYpe=itemView.findViewById(R.id.tv_property_TYpe);
            tv_pincode=itemView.findViewById(R.id.tv_pincode);
            tv_thana=itemView.findViewById(R.id.tv_thana);
            tv_khata=itemView.findViewById(R.id.tv_khata);
            tv_khesra=itemView.findViewById(R.id.tv_khesra);
            iv_edit=itemView.findViewById(R.id.iv_edit);
            iv_delete=itemView.findViewById(R.id.iv_delete);
            iv_upload=itemView.findViewById(R.id.iv_upload);

        }

        @Override
        public void onClick(View v) {

        }

    }

    public String getGenderHindi(String gender)
    {
        return gender;
    }

}
