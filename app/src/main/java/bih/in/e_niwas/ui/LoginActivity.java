package bih.in.e_niwas.ui;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.exp.e_niwas.R;

import bih.in.e_niwas.database.DataBaseHelper;
import bih.in.e_niwas.entity.UserDetails;
import bih.in.e_niwas.utility.CommonPref;
import bih.in.e_niwas.utility.GlobalVariables;
import bih.in.e_niwas.utility.MarshmallowPermission;
import bih.in.e_niwas.utility.Utiilties;
import bih.in.e_niwas.web_services.WebServiceHelper;


public class LoginActivity extends Activity {

    ConnectivityManager cm;
    public static String UserPhoto;
    String version;
    TelephonyManager tm;
    private static String imei;
    //TODO setup Database
    //DatabaseHelper1 localDBHelper;
    Context context;
    String uid = "";
    String pass = "";
    EditText userName;
    EditText userPass;
    String[] param;
    DataBaseHelper localDBHelper;

    UserDetails userInfo;
    TextView app_ver;
    MarshmallowPermission MARSHMALLOW_PERMISSION;
    private PopupWindow mPopupWindow;
    String newpass="",cnf_pass="";
    UserDetails data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button loginBtn = (Button) findViewById(R.id.btn_login);
        TextView signUpBtn = (TextView) findViewById(R.id.tv_signup);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i=new Intent(LoginActivity.this,HomeActivity.class);
//                startActivity(i);

                userName = (EditText) findViewById(R.id.et_username);
                userPass = (EditText) findViewById(R.id.et_password);
                param = new String[2];
                param[0] = userName.getText().toString();
                param[1] = userPass.getText().toString();

                if (param[0].length() < 1){
                    Toast.makeText(LoginActivity.this, "Enter Valid User Id", Toast.LENGTH_SHORT).show();
                }else if (param[1].length() < 1){
                    Toast.makeText(LoginActivity.this, "Enter Valid Password", Toast.LENGTH_SHORT).show();
                }else{
                    new LoginTask(param[0], param[1]).execute(param);
                }

            }
        });

//        signUpBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent singUpInt = new Intent(LoginActivity.this, SignUpActivity.class);
//                LoginActivity.this.startActivity(singUpInt);
//            }
//        });

        try {
            version = getPackageManager().getPackageInfo(getPackageName(),
                    0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void getUserDetail(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String username = prefs.getString("uid", "user");
        String password = prefs.getString("pass", "password");
        //userInfo = localDBHelper.getUserDetails(username.toLowerCase(), password);
    }

    @Override
    protected void onResume() {
        super.onResume();
        readPhoneState();
        //getIMEI();

    }


    private class LoginTask extends AsyncTask<String, Void, UserDetails> {
        String username,password;

        LoginTask(String username, String password){
            this.username = username;
            this.password = password;
        }

        private final ProgressDialog dialog = new ProgressDialog(
                LoginActivity.this);

        private final AlertDialog alertDialog = new AlertDialog.Builder(
                LoginActivity.this).create();

        @Override
        protected void onPreExecute() {

            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage(getResources().getString(R.string.authenticating));
            this.dialog.show();
        }

        @Override
        protected UserDetails doInBackground(String... param) {

            if (!Utiilties.isOnline(LoginActivity.this)) {
                UserDetails userDetails = new UserDetails();
                userDetails.setAuthenticated(true);
                return userDetails;
            } else {
                return WebServiceHelper.Login(username, password,"");
            }

        }

        @Override
        protected void onPostExecute(final UserDetails result) {

            if (this.dialog.isShowing()) this.dialog.dismiss();

            if (result != null && result.isAuthenticated() == false) {

                alertDialog.setTitle(getResources().getString(R.string.failed));
                alertDialog.setMessage(getResources().getString(R.string.authentication_failed));
                alertDialog.show();

            } else if (!(result != null)) {

                AlertDialog.Builder ab = new AlertDialog.Builder(LoginActivity.this);
                ab.setTitle(getResources().getString(R.string.server_down_title));
                ab.setMessage(getResources().getString(R.string.server_down_text));
                ab.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int whichButton) {

                        dialog.dismiss();
                    }
                });

                ab.create().getWindow().getAttributes().windowAnimations = android.R.style.Animation_Dialog;
                ab.show();

            }
            else
            {

                //-----------------------------------------Online-------------------------------------
                if (Utiilties.isOnline(LoginActivity.this)) {

//                    if(imei.equalsIgnoreCase(imei))
//                    {
//                        imei = result.getIMEI();
//                        //imei = "862183044263136";
//                        //imei = "359376097764329";
//                        //imei = "866778040112652";
//                    }
                    uid = param[0];
                    pass = param[1];

                    if (result != null && result.isAuthenticated() == true && result.get_Utype().equals("2"))
                    {
                        data=result;
                        //if (imei.equalsIgnoreCase(result.getIMEI())) {

                        // uid=result.getUserID();
                        if (result.get_is_passwordChanged().equals("N"))
                        {
                            LayoutInflater inflater = (LayoutInflater) LoginActivity.this.getSystemService(LAYOUT_INFLATER_SERVICE);

                            // Inflate the custom layout/view
                            View customView = inflater.inflate(R.layout.dialog_details, null);

                            // Initialize a new instance of popup window
                            mPopupWindow = new PopupWindow(customView, ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);

                            if (Build.VERSION.SDK_INT >= 21)
                            {
                                mPopupWindow.setElevation(5.0f);
                            }
                            TextView tv_pass = (TextView) customView.findViewById(R.id.tv_pass);
                            TextView tv_cnfpass = (TextView) customView.findViewById(R.id.tv_cnfpass);
                            final EditText edt_pass = (EditText) customView.findViewById(R.id.edt_pass);
                            final EditText edt_cnf_pass = (EditText) customView.findViewById(R.id.edt_cnf_pass);
                            Button button = (Button) customView.findViewById(R.id.button);

                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    newpass=edt_pass.getText().toString();
                                    cnf_pass=edt_cnf_pass.getText().toString();
                                    if (cnf_pass.length()>=4){
                                        if (cnf_pass.equals(newpass))
                                        {
                                            new ChangePassword().execute();
                                        }
                                        else {
                                            Toast.makeText(LoginActivity.this,"Password doesnot match",Toast.LENGTH_LONG).show();
                                        }
                                    }
                                    else {
                                        Toast.makeText(LoginActivity.this,"Password should be of minimum 4 characters",Toast.LENGTH_LONG).show();
                                    }

                                }
                            });

                            mPopupWindow.showAtLocation(tv_pass, Gravity.CENTER, 0, 0);
                            mPopupWindow.setFocusable(true);
                            mPopupWindow.setOutsideTouchable(false);
                            mPopupWindow.update();
                        }
                        else if (result.get_is_passwordChanged().equals("Y")){


                            uid = param[0];
                            pass = param[1];

                            try {

                                GlobalVariables.LoggedUser = result;
                                GlobalVariables.LoggedUser.setUserID(userName
                                        .getText().toString().trim().toLowerCase());

                                GlobalVariables.LoggedUser.setPassword(userPass
                                        .getText().toString().trim());


                                CommonPref.setUserDetails(getApplicationContext(),
                                        GlobalVariables.LoggedUser);


                                long c = setLoginStatus(GlobalVariables.LoggedUser);

                                if (c > 0) {
                                    start();
                                } else {
                                    Toast.makeText(LoginActivity.this, getResources().getString(R.string.authentication_failed),
                                            Toast.LENGTH_SHORT).show();
                                }

                            } catch (Exception ex) {
                                ex.printStackTrace();
                                Toast.makeText(LoginActivity.this, getResources().getString(R.string.authentication_failed),
                                        Toast.LENGTH_SHORT).show();
                            }
//                        } else {
//                            alertDialog.setTitle("डिवाइस पंजीकृत नहीं है");
//                            alertDialog.setMessage("क्षमा करें, आपका डिवाइस पंजीकृत नहीं है.\r\nकृपया अपने व्यवस्थापक से संपर्क करें.");
//                            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int which) {
//                                    userName.setFocusable(true);
//                                }
//                            });
//                            alertDialog.show();
//
//                        }

                        }
                    }
                    // offline -------------------------------------------------------------------------

                } else {

                    if (localDBHelper.getUserCount() > 0) {

                        //GlobalVariables.LoggedUser = localDBHelper.getUserDetails(userName.getText().toString().trim().toLowerCase(),userPass.getText().toString());

                        if (GlobalVariables.LoggedUser != null) {

                            CommonPref.setUserDetails(
                                    getApplicationContext(),
                                    GlobalVariables.LoggedUser);

                            SharedPreferences.Editor editor = SplashActivity.prefs.edit();
                            editor.putBoolean("username", true);
                            editor.putBoolean("password", true);
                            editor.putString("uid", uid);
                            editor.putString("pass", pass);
                            editor.commit();
                            start();

                        } else {

                            Toast.makeText(
                                    getApplicationContext(),
                                    getResources().getString(R.string.username_password_notmatched),
                                    Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(
                                getApplicationContext(),
                                getResources().getString(R.string.enable_internet_for_firsttime),
                                Toast.LENGTH_LONG).show();
                    }
                }
            }

        }
    }

    private long setLoginStatus(UserDetails details) {
        details.setPassword(pass);
        SharedPreferences.Editor editor = SplashActivity.prefs.edit();
        editor.putBoolean("username", true);
        editor.putBoolean("password", true);
        editor.putString("uid", uid);
        editor.putString("div", details.getDivisionName());
        editor.putString("u_name", details.getName());
        editor.putString("div_id", details.getDivision());
        editor.putString("dist_id", details.getDistrictCode());
        editor.putString("dist_name", details.getDistName());
        editor.putString("pass", pass);
        editor.putString("role", details.getUserrole());
        editor.putString("u_type", details.get_Utype());
        editor.commit();
        //PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("USER_ID", uid).commit();
        //PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("uid", uid).commit();
        localDBHelper = new DataBaseHelper(getApplicationContext());
        long c = localDBHelper.insertUserDetails(details);

        return c;
    }

    public void start() {
        //getUserDetail();
        //new SyncPanchayatData().execute("");
        Intent iUserHome = new Intent(getApplicationContext(), HomeActivity.class);
        iUserHome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(iUserHome);
        finish();
    }


    public void readPhoneState() {
        MARSHMALLOW_PERMISSION = new MarshmallowPermission(LoginActivity.this, android.Manifest.permission.READ_PHONE_STATE);
        if (MARSHMALLOW_PERMISSION.result == -1 || MARSHMALLOW_PERMISSION.result == 0) {
            try {
                tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                //if (tm != null) imei = tm.getDeviceId();
                if (tm != null) imei = getDeviceIMEI();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                //if (tm != null) imei = tm.getDeviceId();
                if (tm != null) imei = getDeviceIMEI();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

//        EditText userName = (EditText) findViewById(R.id.UserText);
//        userName.setText(CommonPref.getUserDetails(getApplicationContext()).get_UserID());

        try {
            version = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            TextView tv = (TextView) findViewById(R.id.app_ver);
            tv.setText("ऐप का वर्जन : " + version + " ( " + imei + " )");

        } catch (PackageManager.NameNotFoundException e) {

        }
    }



    public String getDeviceIMEI() {
        //String deviceUniqueIdentifier = null;
        MarshmallowPermission permission = new MarshmallowPermission(this, Manifest.permission.READ_PHONE_STATE);
//        try {
//            TelephonyManager tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
//            if (null != tm) {
//                imei = tm.getDeviceId();
//            }
//            if (null == imei || 0 == imei.length()) {
//                imei = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
//            }
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
        {
            imei = Settings.Secure.getString(
                    this.getContentResolver(),
                    Settings.Secure.ANDROID_ID);
        }
        else
        {
            final TelephonyManager mTelephony = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
            if (mTelephony.getDeviceId() != null)
            {
                imei = mTelephony.getDeviceId();
            }
            else
            {
                imei = Settings.Secure.getString(this.getContentResolver(),Settings.Secure.ANDROID_ID);
            }
        }


        return imei;
    }


    private class ChangePassword extends AsyncTask<String, Void, String> {


        private final ProgressDialog dialog = new ProgressDialog(LoginActivity.this);
        private final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(LoginActivity.this).create();


        @Override
        protected void onPreExecute() {

            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage("UpLoading...");
            this.dialog.show();
        }

        @Override
        protected String doInBackground(String... param)
        {
            //    String res = WebServiceHelper.UploadSingleData(data, devicename, app_version,PreferenceManager.getDefaultSharedPreferences(mContext).getString("USERID", ""));
            String res = WebServiceHelper.ChangePassword(uid,cnf_pass);
            return res;
        }

        @Override
        protected void onPostExecute(String result)
        {
            if (this.dialog.isShowing())
            {
                this.dialog.dismiss();
            }
            Log.d("Responsevalue", "" + result);
            if (result != null)
            {
//                String string = result;
//                String[] parts = string.split(",");
//                String part1 = parts[0]; // 004-
//                String part2 = parts[1];

                if (result.equals("1"))
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    // builder.setIcon(R.drawable.logo3);
                    builder.setTitle("Success!!");
                    // Ask the final question
                    builder.setMessage("Password Uploaded Successfully");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            uid = param[0];
                            pass = param[1];

                            try
                            {
                                GlobalVariables.LoggedUser = data;
                                GlobalVariables.LoggedUser.setUserID(userName.getText().toString().trim().toLowerCase());

                                GlobalVariables.LoggedUser.setPassword(userPass.getText().toString().trim());
                                CommonPref.setUserDetails(getApplicationContext(),GlobalVariables.LoggedUser);

                                long c = setLoginStatus(GlobalVariables.LoggedUser);

                                if (c > 0)
                                {
                                    start();
                                }
                                else
                                    {
                                    Toast.makeText(LoginActivity.this, getResources().getString(R.string.authentication_failed),Toast.LENGTH_SHORT).show();
                                }

                            }
                            catch (Exception ex)
                            {
                                ex.printStackTrace();
                                Toast.makeText(LoginActivity.this, getResources().getString(R.string.authentication_failed),Toast.LENGTH_SHORT).show();
                            }
//                        } else {
//                            alertDialog.setTitle("डिवाइस पंजीकृत नहीं है");
//                            alertDialog.setMessage("क्षमा करें, आपका डिवाइस पंजीकृत नहीं है.\r\nकृपया अपने व्यवस्थापक से संपर्क करें.");
//                            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int which) {
//                                    userName.setFocusable(true);
//                                }
//                            });
//                            alertDialog.show();
//
//                        }
                        }
                    });

                    AlertDialog dialog = builder.create();

                    dialog.show();

                } else if (result.equals("0")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    //builder.setIcon(R.drawable.uploaderror);
                    builder.setTitle("Alert!!");
                    // Ask the final question
                    builder.setMessage("Record  Not Uploaded ");

                    // Set the alert dialog yes button click listener
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.dismiss();
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();

                }

                else {
                    Toast.makeText(LoginActivity.this, "Your password is not uploaded Successfully ! ", Toast.LENGTH_SHORT).show();
                }
            } else {

                Toast.makeText(LoginActivity.this, "Uploading failed...Please Try Later", Toast.LENGTH_SHORT).show();
            }

        }
    }
}
