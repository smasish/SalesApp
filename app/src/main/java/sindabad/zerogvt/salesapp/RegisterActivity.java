
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
                    
                    new SendOperation().execute("");

                    if (flag) {
                        finish();
                        Log.d("----- ", "finish");
                    }
                }

                break;
        }

    }

    private class SendOperation extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {

            try {


                Log.d("response---", "********" );
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Url.REG_URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("response---", "********" + response.toString());


                                JSONArray mArray;
                                try {
                                    mArray = new JSONArray(response.toString());
                                    for (int i = 0; i < mArray.length(); i++) {
                                        JSONObject mJsonObject = mArray.getJSONObject(i);
                                        Log.d("OutPut---", mJsonObject.getString("name"));
                                        Log.d("OutPut---", mJsonObject.getString("email"));
                                        name = mJsonObject.getString("StaffID");

                                        flag = true;

                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                Log.d("response--flag-", ""+flag );
                                Toast.makeText(RegisterActivity.this, "Success "+ name, Toast.LENGTH_SHORT).show();

                                // if(flag) {
//                                Intent i = new Intent(RegisterActivity.this, MainActivity.class);
//                                i.putExtra("staffid",name);
//                                startActivity(i);

                                pd.dismiss();


                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                pd.dismiss();
                                Toast.makeText(RegisterActivity.this,error.toString(),Toast.LENGTH_LONG).show();

                                NetworkResponse networkResponse = error.networkResponse;
                                if (networkResponse != null) {
                                    Log.e("Volley", "Error. HTTP Status Code:"+networkResponse.statusCode);
                                }
                                Toast.makeText(RegisterActivity.this, "Invalid response", Toast.LENGTH_SHORT).show();
                                if (error instanceof TimeoutError) {
                                    Log.e("Volley", "TimeoutError");
                                }else if(error instanceof NoConnectionError){
                                    Log.e("Volley", "NoConnectionError");
                                }
                            }
                        }){
                    @Override
                    protected Map<String,String> getParams(){
                        Map<String,String> params = new HashMap<String, String>();
                        params.put(KEY_USERNAME,user);
                        params.put(KEY_PASSWORD,pass);

                        try {
                            JSONObject data = new JSONObject();
                            data.put("loginName", user);
                            data.put("loginPass", pass);
                            Log.e("request",data.toString());
                            params.put("data login-------", data.toString());
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        //params.put("data", "{'username':'"+username+"','password':'"+password+"'}");
                        return params;
                    }

                };

                RequestQueue requestQueue = Volley.newRequestQueue(RegisterActivity.this);
                requestQueue.add(stringRequest);
                Log.d("====22====","----"+flag);

            } catch (Exception e) {
                e.printStackTrace();

            }
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
