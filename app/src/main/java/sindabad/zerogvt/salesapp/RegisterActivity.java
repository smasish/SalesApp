
package sindabad.zerogvt.salesapp;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends Activity implements OnClickListener {
    /** Called when the activity is first created. */

    private EditText userName, password, retypePassword,mob_no ; //, regionCode, territoryCode, marketCode;

    private Button cancel, register;

    String name, pw, region, territory, marketcode, rePW,mobNo;

    private Context con;

    String allinfo = "";
    private static ProgressDialog pd;
    boolean flag = false;
    

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        con = this;

        userName = (EditText)findViewById(R.id.userName_id);
        password = (EditText)findViewById(R.id.password_id);
        retypePassword = (EditText)findViewById(R.id.retypePassword_id);
        mob_no = (EditText)findViewById(R.id.mob_id);
        
        mob_no.setText("+880");
//        regionCode = (EditText)findViewById(R.id.regionCode_id);
//        territoryCode = (EditText)findViewById(R.id.territoryCode_id);
//        marketCode = (EditText)findViewById(R.id.marketCode_id);

        cancel = (Button)findViewById(R.id.btnCancel);
        register = (Button)findViewById(R.id.btnRegister);
       

        cancel.setOnClickListener(this);
        register.setOnClickListener(this);
        
//        WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
//        WifiInfo wInfo = wifiManager.getConnectionInfo();
//        String macAddress = wInfo.getMacAddress();
        
 //       Log.d("----- ", "...."+macAddress);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.btnCancel:
                finish();
                break;

            case R.id.btnRegister:

                pw = password.getText().toString();
                rePW = retypePassword.getText().toString();
                name = userName.getText().toString();
                mobNo = mob_no.getText().toString();
//                region = regionCode.getText().toString();
//                territory = territoryCode.getText().toString();
//                marketcode = marketCode.getText().toString();

                allinfo = name ; // +pw+"+8801755534755"; +region+territory+marketcode;
                
                if(name.equalsIgnoreCase("")){
                	 AlertMessage.showMessage(con, getString(R.string.reg),
                     getString(R.string.b_userid));
                }
                else if(pw.equalsIgnoreCase("")){
               	 AlertMessage.showMessage(con, getString(R.string.reg),
                         getString(R.string.b_pass));
                    }
                else if(rePW.equalsIgnoreCase("")){
                  	 AlertMessage.showMessage(con, "Registration",
                            getString(R.string.b_retype_pass));
                       }

                else if (!pw.equalsIgnoreCase(rePW)) {
                    AlertMessage.showMessage(con, getString(R.string.reg),
                            getString(R.string.pass_mismatch));
                }
                
                else if(mobNo.length() !=14){
                  	 AlertMessage.showMessage(con, getString(R.string.reg),
                            getString(R.string.mobno));
                       }

//                else if (name.length() < 4 || pw.length() < 4) {
//                    AlertMessage.showMessage(con, "Warning!!!",
//                            "Wrong username or password.Please try again.");
//                } 
                else {
                    // http://202.59.131.4/salesorder/send_order.php
                    // http://202.59.131.4/salesorder/login.php
                    flag = false;
                    String title = getString(R.string.reg);
                    String message = "Registration processing \nPlease wait...";
                    pd = ProgressDialog.show(con, title, message, true, true);
                    
                    new SendOperation().execute("");

                    if (flag) {
                        finish();
                        Log.d("----- ", "finish");
                    }
                }

                break;
        }

    }

    private class SendOperation extends AsyncTask<String, Void, String> {
        String url = "";

        @Override
        protected String doInBackground(String... params) {



            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            // if(url.equalsIgnoreCase("yes")){
            // AlertMessage.showMessage(con, "Welcome!!!",
            // "Logged in successfully.");
            // //finish();
            // }else{
            // AlertMessage.showMessage(con, "Warning!!",
            // "Sorry, Username is not available.");
            // }
            if (flag) {
                Toast.makeText(con, "Successfully Registered.", Toast.LENGTH_LONG).show();
                RegisterActivity.this.finish();
                Log.d("---//-- ", "finish");
            } else {
                Toast.makeText(con, getString(R.string.reg_failed), Toast.LENGTH_LONG).show();
            }

        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }

}
