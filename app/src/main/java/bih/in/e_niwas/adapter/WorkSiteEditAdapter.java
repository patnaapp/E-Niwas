package bih.in.e_niwas.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Build;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.exp.e_niwas.R;

import java.util.ArrayList;

import bih.in.e_niwas.database.DataBaseHelper;
import bih.in.e_niwas.entity.NiwasInspectionEntity;
import bih.in.e_niwas.ui.NewEntryForm_Activity;
import bih.in.e_niwas.utility.Utiilties;
import bih.in.e_niwas.web_services.WebServiceHelper;

public class WorkSiteEditAdapter extends RecyclerView.Adapter<WorkSiteEditAdapter.ViewHolder> {

    Activity activity;
    LayoutInflater mInflater;
    ArrayList<NiwasInspectionEntity> ThrList=new ArrayList<>();

    Boolean isShowDetail = false;
    String singlerowid="",user_id="";
    // WorkReqrmntListener listener;
    DataBaseHelper dataBaseupload;
    String version;
    String asset_new="";

    public WorkSiteEditAdapter(Activity listViewshowedit, ArrayList<NiwasInspectionEntity> rlist,String assetlistnew) {
        this.activity=listViewshowedit;
        this.ThrList=rlist;
        mInflater = (LayoutInflater)activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        //this.listener = listener;
        asset_new = assetlistnew;
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
     //   holder.tv_div_name.setText(info.getDiv_name());
        holder.tv_div_name.setText("Madhepura");
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

        if (asset_new.equals("Y")){
holder.iv_delete.setVisibility(View.GONE);
holder.iv_upload.setVisibility(View.GONE);
        }
        else if (asset_new.equals("N")){
            holder.iv_delete.setVisibility(View.VISIBLE);
            holder.iv_upload.setVisibility(View.VISIBLE);
        }

        holder.iv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(activity, NewEntryForm_Activity.class);
               // i.putExtra("assetdata",ThrList.get(position));
                i.putExtra("KeyId",info.getId());
                i.putExtra("isEdit", "Yes");
                i.putExtra("isServer", "No");
                activity.startActivity(i);

            }
        });
        holder.iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
              //  builder.setIcon(R.drawable.del1);
                builder.setTitle("Delete Data");
                builder.setMessage("Are you sure want to Delete the Record");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        DataBaseHelper dataBaseHelper = new DataBaseHelper(activity);
                        String _uid = ThrList.get(position).getId();
                        dataBaseHelper.deleteEditRec(_uid, user_id);
                        //  ((Edit_entry) activity).setReportListViewDataForAdapter();
                        ThrList = dataBaseHelper.getAllNewEntryDetail("0",user_id);
                        refresh(ThrList);

                        ThrList = dataBaseHelper.getAllNewEntryDetail("0",user_id);

                    }

                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                AlertDialog dialog = builder.create();
                // Display the alert dialog on interface
                dialog.show();
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
                                NiwasInspectionEntity assetDetails_editImage=dataBaseHelper.getimage(singlerowid,user_id);
                                data.setImage1(assetDetails_editImage.getImage1());
                                data.setImage2(assetDetails_editImage.getImage2());

                                new UPLOADDATA(data).execute();
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

    private class UPLOADDATA extends AsyncTask<String, Void, String> {
        NiwasInspectionEntity data;
        String rowid;
        private final ProgressDialog dialog = new ProgressDialog(activity);
        private final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(activity).create();


        UPLOADDATA(NiwasInspectionEntity data) {
            this.data = data;
            //_uid = data.getId();
            rowid = data.getId();

        }

        @Override
        protected void onPreExecute() {

            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage("UpLoading...");
            this.dialog.show();
        }

        @Override
        protected String doInBackground(String... param) {

            String devicename = getDeviceName();
            String app_version = getAppVersion();
            boolean isTablet = isTablet(activity);
            if (isTablet) {
                devicename = "Tablet::" + devicename;
                Log.e("DEVICE_TYPE", "Tablet");
            } else {
                devicename = "Mobile::" + devicename;
                Log.e("DEVICE_TYPE", "Mobile");
            }

            String res = WebServiceHelper.UploadBasicDetails(data,app_version,user_id);
            return res;

        }

        @Override
        protected void onPostExecute(String result) {
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
            }
            Log.d("Responsevalue", "" + result);
            if (result != null) {
                String string = result;
                String[] parts = string.split("-");
                String part1 = parts[0]; // 004-
                String part2 = parts[1];

                if (part1.equals("1")) {
                    Toast.makeText(activity, part2, Toast.LENGTH_SHORT).show();

                    dataBaseupload = new DataBaseHelper(activity);
                    long c = dataBaseupload.deleteRec(rowid);
//                    Utilitties.vibrate(context);
                   showCustomDialoguploadsuccess();
                    ThrList = dataBaseupload.getAllNewEntryDetail("0",user_id);

                    refresh(ThrList);
                    ThrList = dataBaseupload.getAllNewEntryDetail("0",user_id);


                }
                else if (part1.equals("2")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(activity);

                    builder.setTitle("Alert!!");
                    // Ask the final question
                    builder.setMessage(part2);

                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.dismiss();
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                    // Display the alert dialog on interface
//                    if (!activity.isFinishing()) {
//                        dialog.show();
//                    }

                }

                else {
                    Toast.makeText(activity, "Your data is not uploaded Successfully ! ", Toast.LENGTH_SHORT).show();
                }

            } else {

                Toast.makeText(activity, "Uploading failed...Please Try Later", Toast.LENGTH_SHORT).show();
            }

        }
    }



    private void showCustomDialoguploadsuccess() {


        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
     //   builder.setIcon(R.drawable.fsyicluncher);
        builder.setTitle("Uploaded Successfully!!");
        // Ask the final question
        builder.setMessage("Your data is uploaded Successfully");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();


    }
    public void refresh(ArrayList<NiwasInspectionEntity> events) {
        this.ThrList.clear();
        this.ThrList.addAll(events);
        notifyDataSetChanged();
    }

    public static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {

            return model.toUpperCase();
        } else {

            return manufacturer.toUpperCase() + " " + model;
        }
    }


    public String getAppVersion() {
        try {

            version = activity.getPackageManager().getPackageInfo(activity.getPackageName(), 0).versionName;

        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return version;
    }

    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }
}
