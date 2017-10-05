
package sindabad.zerogvt.salesapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import constant.Url;

public class RegisterActivity extends Activity implements OnClickListener {
    /** Called when the activity is first created. */

    private EditText userName, password, retypePassword,mob_no ; //, regionCode, territoryCode, marketCode;

    private Button cancel, register;

    String name, pw, region, territory, marketcode, rePW,mobNo;

    private Context con;

    String allinfo = "";
    private static ProgressDialog pd;
    boolean flag = false;
    public static final String KEY_USERNAME = "loginName";
    public static final String KEY_PASSWORD = "loginPass";
    String user="",pass="";

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
                    
                    new LoginOperation().execute("");


                    if (flag) {
                        finish();
                        Log.d("----- ", "finish");
                    }
                }

                break;
        }

    }

    public void testRequest(){
        StringRequest sr = new StringRequest(Request.Method.POST,"https://staging.sindabad.com/restapi/index.php/api/customer_add", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("test_disable_sess","Test Reqest"+ response.toString());
                Toast.makeText(RegisterActivity.this, "This is my " + response.toString(),
                        Toast.LENGTH_LONG).show();
//            mPostCommentResponse.requestCompleted();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("test_disable_sess","Error Conn"+ error.toString());
                Toast.makeText(RegisterActivity.this, "This is my " + error.toString(),
                        Toast.LENGTH_LONG).show();
//            mPostCommentResponse.requestEndedWithError(error);
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> data = new HashMap<String, String>();
                data.put("customer_email", "asish@gmail.com");
                data.put("customer_firstname", "01911612622");
                data.put("customer_lastname", "Dada");
                data.put("customer_password", "xx123456");
//            params.put("user",userAccount.getUsername());
//            params.put("pass",userAccount.getPassword());
//            params.put("comment", Uri.encode(comment));
//            params.put("comment_post_ID",String.valueOf(postId));
//            params.put("blogId",String.valueOf(blogId));

                return data;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };
        RequestQueue requstQueue = Volley.newRequestQueue(RegisterActivity.this);
        requstQueue.add(sr);
    }

    private class LoginOperation extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {

             testRequest();
            return "";
        }


        @Override
        protected void onPostExecute(String result) {

            // lv.setAdapter(adapter);
            Log.d("========","----"+flag);
//            if(flag) {
//                Intent i = new Intent(LoginActivity.this, MainActivity.class);
//                startActivity(i);
//            }
        }

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(String... text) {


        }
    }

}
