package bih.in.e_niwas.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.exp.e_niwas.R;

import java.util.ArrayList;

import bih.in.e_niwas.entity.NiwasInspectionEntity;
import bih.in.e_niwas.ui.NewEntryForm_Activity;

public class AssetListAdapter extends RecyclerView.Adapter<AssetListAdapter.ViewHolder> {

    Activity activity;
    LayoutInflater mInflater;
    ArrayList<NiwasInspectionEntity> ThrList=new ArrayList<>();

    Boolean isShowDetail = false;

    public AssetListAdapter(Activity listViewshowedit, ArrayList<NiwasInspectionEntity> rlist) {
        this.activity=listViewshowedit;
        this.ThrList=rlist;
        mInflater = (LayoutInflater)activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.adaptor_asset_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final NiwasInspectionEntity info = ThrList.get(position);

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

        holder.sblist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(activity, NewEntryForm_Activity.class);
                // i.putExtra("assetdata",ThrList.get(position));
                i.putExtra("KeyId",info.getId());
                i.putExtra("isEdit", "Yes");
                activity.startActivity(i);

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
        LinearLayout sblist;

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
            sblist=itemView.findViewById(R.id.sblist);
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