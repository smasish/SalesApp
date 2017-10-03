
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
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
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
import sindabad.zerogvt.salesapp.R;
import sindabad.zerogvt.salesapp.SharedPreferencesHelper;


public class Customers extends Activity implements OnItemClickListener {

    private DatabaseHelper dbHelper;

    // private CustomerAdapter adapter;
    private static ProgressDialog pd;

    private String source;

    TempCustomers tempCustomers = null;

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

        new DataList().execute("");


    }

    @Override
    protected void onResume() {
        super.onResume();



    }

    private class DataList extends AsyncTask<String, String, String> {


        @Override
        protected String doInBackground(String... params) {

            try {




                data_parsing(Url.CUSTOMERS_URL);
                Log.d("==url======","----"+Url.CUSTOMERS_URL);


            } catch (Exception e) {
                e.printStackTrace();

            }
            return null;
        }


        @Override
        protected void onPostExecute(String result) {

            cust_list.setAdapter(data_adapter);
            pd.dismiss();
        }


        @Override
        protected void onPreExecute() {

        }


        @Override
        protected void onProgressUpdate(String... text) {


        }
    }


    private void data_parsing(String url) {


        Log.d(".response--.", "..");


            String URL = url;
        Log.d(".response--.", ".."+url);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                          //  TripData tripData = null;
                            Log.d(".response--.", ">>>"+response.toString());

                            Log.d("====x=", "..xx..>>");

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


                    data.put("page", "49");
                    data.put("page_size", "50");

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
