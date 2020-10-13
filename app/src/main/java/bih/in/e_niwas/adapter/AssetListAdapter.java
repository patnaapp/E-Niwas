package bih.in.e_niwas.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.exp.e_niwas.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import bih.in.e_niwas.database.DataBaseHelper;
import bih.in.e_niwas.entity.NiwasInspectionEntity;
import bih.in.e_niwas.ui.NewEntryForm_Activity;
import bih.in.e_niwas.utility.Utiilties;

public class AssetListAdapter extends RecyclerView.Adapter<AssetListAdapter.ViewHolder> {

    Activity activity;
    LayoutInflater mInflater;
    ArrayList<NiwasInspectionEntity> ThrList=new ArrayList<>();
    DataBaseHelper dataBaseHelper;

    Boolean isShowDetail = false;

    public AssetListAdapter(Activity listViewshowedit, ArrayList<NiwasInspectionEntity> rlist,String assetlistnew) {
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
        dataBaseHelper=new DataBaseHelper(activity);
        holder.tv_slno.setText(String.valueOf(position+1));
        holder.tv_div_name.setText(info.getDiv_name());
        if (info.getArea_type().equals("0"))
        {
            holder.tv_areaType.setText("Urban");
        }
        else if (info.getArea_type().equals("1"))
        {
            holder.tv_areaType.setText("Rural");
        }

        if (info.getProperty_type().equals("0"))
        {
            holder.tv_property_TYpe.setText("Open Land");
        }
        else if (info.getProperty_type().equals("1"))
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
                NiwasInspectionEntity asset_server=ThrList.get(position);
                Long c=dataBaseHelper.getAssetCount(asset_server.getAsset_Id());

                if (c > 0) {
                    new AlertDialog.Builder(activity)
                            .setTitle("Alert !!")
                            .setIcon(R.drawable.eniwaslogo)
                            .setMessage("Records already updated..kindly upload to server first to make any changes")
                            .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.dismiss();
                                }
                            })

                            .show();
                }
                else {
                    Intent i = new Intent(activity, NewEntryForm_Activity.class);


//                byte[] image1Data = Base64.decode(asset_server.getImage1(), Base64.DEFAULT);
//                byte[] image2Data = Base64.decode(asset_server.getImage2(), Base64.DEFAULT);
                    // Bitmap bmp1=Utiilties.StringToBitMap(asset_server.getImage1());

                    i.putExtra("image1",Utiilties.StringToBitMap(asset_server.getImage1()));
                    i.putExtra("image2",Utiilties.StringToBitMap(asset_server.getImage2()));
                    asset_server.setImage1("");
                    asset_server.setImage2("");
                    i.putExtra("assetdata_server",asset_server);
                    i.putExtra("KeyId",info.getAsset_Id());
                    i.putExtra("isEdit", "Yes");
                    i.putExtra("isServer", "Yes");

                    activity.startActivity(i);
                }


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
