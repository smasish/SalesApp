
package customer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import constant.Url;
import database.DatabaseHelper;
import sindabad.zerogvt.salesapp.AlertMessage;
import sindabad.zerogvt.salesapp.ProductListActivity;
import sindabad.zerogvt.salesapp.R;
import sindabad.zerogvt.salesapp.SharedPreferencesHelper;


public class Customers extends Activity implements OnItemClickListener {

    private DatabaseHelper dbHelper;

    // private CustomerAdapter adapter;
    private static ProgressDialog pd;

    private String source;

    TempCustomers tempCustomers = null;
    ArrayList<HashMap<String, String>> contactList;
//    private IndexableListView mListView;

    EditText inputsearch;
    String cust="";
    private Context con;
    private ListView cust_list;

    ArrayList<TempCustomers> arraylist = new ArrayList<TempCustomers>();

    String[] data_array;
    JSONArray mArray,historyArray;
    Boolean flag  = false;

    ArrayAdapter<String> adapter,data_adapter;
    String name = "",history_name="";
    String data = "";
    private String jsonResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.customers);
        con = this;


        cust_list = (ListView)findViewById(R.id.customers);


        dbHelper = new DatabaseHelper(this);
        dbHelper.open();

        contactList = new ArrayList<>();

        inputsearch = (EditText)findViewById(R.id.inputSearch);
        inputsearch.setFocusableInTouchMode(true);

        // Capture Text in EditText
        inputsearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                String text = inputsearch.getText().toString().toLowerCase(Locale.getDefault());
               // adapter.filter(text);

              //  adapter.notifyDataSetChanged();
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub
            }
        });

        dbHelper.close();


        String title = "loading";
        String message = "Loading customer name \nPlease wait...";
        pd = ProgressDialog.show(con, title, message, true, true);

        Log.d("==onresume theke===","----");

        new SendOperation().execute("");


    }

    @Override
    protected void onResume() {
        super.onResume();



    }

    private class SendOperation extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {

            try {


                Log.d("response---", "********" );
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Url.CUSTOMERS_URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("response---", "********" + response.toString());


                                Log.d("OutPut---", "----");
                                JSONArray mArray;
                                try {
                                    // Log.d("OutPut---", "----");
                                    JSONObject reader = new JSONObject(response.toString());

                                    Log.d("OutPut---", "-arry---"+reader.getJSONArray("customer_list"));
                                    JSONArray ja = reader.getJSONArray("customer_list");
                                    for (int i = 0; i < ja.length(); i++) {


                                        Log.d("OutPut---", "----"+ja.get(i));
                                        Log.d("OutPut---", "---ll-"+ja.length());

                                        // JSONArray jj = ja.getJSONArray(i);
                                        HashMap<String, String> contact = new HashMap<>();

                                        for (int j = 0; j < ja.length(); j++) {

                                            JSONObject c = ja.getJSONObject(j);
                                            String name = c.getString("email");
                                            contact.put("email", name);
                                            String firstname = c.getString("firstname");
                                            contact.put("firstname", firstname);

                                            String lastname = c.getString("lastname");
                                            contact.put("lastname", lastname);
                                            String id = c.getString("id");
                                            contact.put("id", id);
//                                            String type = c.getString("type_id");
//                                            contact.put("type", type);

                                            Log.d("OutPut---", "---nam-"+name);
                                            contactList.add(contact);


                                        }

                                        Log.d("OutPut---", "----"+contactList.size());


                                    }
//                                    Log.d("OutPut---", "----"+reader.getString("name"));



                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                Log.d("response--flag-", ""+contactList.size() );

                                ListAdapter adapter = new SimpleAdapter(Customers.this, contactList,
                                        R.layout.triprow, new String[]{ "email","firstname","lastname","id",},
                                        new int[]{R.id.offertext, R.id.km_id,R.id.name_id,R.id.sku_id});
                                cust_list.setAdapter(adapter);
                                Toast.makeText(Customers.this, "Success "+ name, Toast.LENGTH_SHORT).show();

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
                                Toast.makeText(Customers.this,error.toString(),Toast.LENGTH_LONG).show();

                                NetworkResponse networkResponse = error.networkResponse;
                                if (networkResponse != null) {
                                    Log.e("Volley", "Error. HTTP Status Code:"+networkResponse.statusCode);
                                }
                                Toast.makeText(Customers.this, "Invalid response", Toast.LENGTH_SHORT).show();
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
                      //  params.put("category_id","5");
                        params.put("page","1");
                        params.put("page_size","2");

                        try {
                            JSONObject data = new JSONObject();
                            // data.put("loginName", user);
                            // data.put("loginPass", pass);
                           // data.put("category_id","18");
                            data.put("page","1");
                            data.put("page_size","2");
                            Log.e("request",data.toString());
                            params.put("data login-------", data.toString());
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        //params.put("data", "{'username':'"+username+"','password':'"+password+"'}");
                        return params;
                    }

                };

                stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                RequestQueue requestQueue = Volley.newRequestQueue(Customers.this);
                requestQueue.add(stringRequest);
                Log.d("====22====","----"+flag);

            } catch (Exception e) {
                e.printStackTrace();

            }
            return "";
        }


        @Override
        protected void onPostExecute(String result) {

//            ListAdapter adapter = new SimpleAdapter(Customers.this, contactList,
//                    R.layout.triprow, new String[]{ "name","type","short_des_id","sku","price"},
//                    new int[]{R.id.offertext, R.id.km_id,R.id.name_id,R.id.sku_id,R.id.price_id});
//            cust_list.setAdapter(adapter);
//            cust_list.invalidate();
            Log.d("**********. ", "out----" + contactList.size());
        }

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(String... text) {


        }
    }

    private void data_parsing(String url) {


        Log.d(".response--.", "..");


            String URL = url;
        Log.d(".response--.", ".."+url);
            StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                          //  TripData tripData = null;
                            Log.d(".response--.", ">>>"+response.toString());

                            Log.d("====x=", "..xx..>>");
                            try {
                                // Parsing json object response
                                // response will be a json object
//                                String name = response.getString("name");
//
//                                String email = response.getString("email");
//                                JSONObject phone = response.getJSONObject("phone");
//                                String home = phone.getString("home");
//                                String mobile = phone.getString("mobile");
//
//                                jsonResponse = "";
//                                jsonResponse += "Name: " + name + "\n\n";
//                                jsonResponse += "Email: " + email + "\n\n";
//                                jsonResponse += "Home: " + home + "\n\n";
//                                jsonResponse += "Mobile: " + mobile + "\n\n";

                              //  txtResponse.setText(jsonResponse);

                            } catch (Exception e) {
                                e.printStackTrace();
                                Toast.makeText(getApplicationContext(),
                                        "Error: " + e.getMessage(),
                                        Toast.LENGTH_LONG).show();
                            }


                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(Customers.this,error.toString(), Toast.LENGTH_LONG ).show();
                            Log.d(".error.toString--.", ".."+error.toString());
                        }
                    }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> data = new HashMap<String,String>();


                    data.put("page", "1");
                    data.put("page_size", "2");

                    Log.d(".data--.", ".."+data);
                    return data;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        Log.d(".stringRequest--.", ".."+stringRequest);
        }



    private void data_parsing33(String url) {

        Log.d("response=======","-->>"+url);
        RequestQueue mVolleyQueue = Volley.newRequestQueue(con);

        Log.d("response=======","--");
        JsonArrayRequest jReq = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                // Utils.LoadingControl(false, "loading");
                //  listView.setAdapter(new OptionAdapter(context,response,2));
                Log.d("response=======","");
                Log.d("response=======",response.toString());



                try {
                    data_array  = new String[response.length()];
                    historyArray = new JSONArray(response.toString());
                    for (int i = 0; i < historyArray.length(); i++) {
                        JSONObject mJsonObject = historyArray.getJSONObject(i);
                        Log.d("OutPut---", mJsonObject.getString("email"));
                        Log.d("OutPut---", mJsonObject.getString("firstname"));
                        history_name = "Name: "+mJsonObject.getString("id");
                        data_array[i] = history_name;
                        flag = true;


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }



                data_adapter = new ArrayAdapter<String>(Customers.this,
                        android.R.layout.simple_list_item_1, android.R.id.text1, data_array);

                cust_list.setAdapter(data_adapter);
                pd.dismiss();

                // **************************************************
                ///history list item listener



//				adapter=new ArrayAdapter<String>(
//						MainActivity.this,android.R.layout.simple_list_item_1, nevg_array){

                JSONArray mArray2;
                try {
                    mArray2 = new JSONArray(response.toString());
                    for (int i = 0; i < mArray2.length(); i++) {
                        JSONObject mJsonObject = mArray2.getJSONObject(i);
                        Log.d("OutPut---", mJsonObject.getString("PassengerName"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //sToast(error.toString());
                //Log.e("Exception",error.toString());
            }
        });

        jReq.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mVolleyQueue.add(jReq);
    }


    public void back(View v) {
        finish();
    }



    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
        // TODO Auto-generated method stub
        Log.d("......on item click..." + position, "===");
    }
}
