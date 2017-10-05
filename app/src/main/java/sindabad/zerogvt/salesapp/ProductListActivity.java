package sindabad.zerogvt.salesapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import constant.Url;
import database.DatabaseHelper;
import sindabad.zerogvt.salesapp.model.Contact;
import sindabad.zerogvt.salesapp.model.Product;
import sindabad.zerogvt.salesapp.adapter.ProductAdapter;

public class ProductListActivity extends AppCompatActivity {

    public static final String TAG = ProductListActivity.class.getSimpleName();

    private ListView mListView;
    int p_index;
    EditText quantity;
    private DatabaseHelper db;
    public static ArrayList<Product> recipeList;

    String name, pw, region, territory, marketcode, rePW,mobNo;

    private Context con;

    String allinfo = "";
    private static ProgressDialog pd;
    boolean flag = false;
    public static final String KEY_USERNAME = "loginName";
    public static final String KEY_PASSWORD = "loginPass";
    String user="",pass="";
    ArrayList<HashMap<String, String>> contactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.productlist);

        final Context context = this;
        db=new DatabaseHelper(this);
        con  = this;
        contactList = new ArrayList<>();
//        recipeList = new ArrayList<>(db.getAllProduct());
//        // Create adapter
//        ProductAdapter adapter = new ProductAdapter(this, recipeList);

        // Create list view
        mListView = (ListView) findViewById(R.id.products_listview);
      //  mListView.setAdapter(adapter);


        String title = getString(R.string.product);
        String message = "Processing \nPlease wait...";
        pd = ProgressDialog.show(con, title, message, true, true);

        new SendOperation().execute("");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("**********. ", "out----" + contactList.size());
//        ListAdapter adapter = new SimpleAdapter(ProductListActivity.this, contactList,
//                R.layout.triprow, new String[]{ "name","short_des_id","sku","price"},
//                new int[]{R.id.name_id, R.id.short_des_id,R.id.sku_id,R.id.price_id});
//        mListView.setAdapter(adapter);
       // mListView.invalidate();

    }

    //    private class TripAdapter extends ArrayAdapter<TripData> {
//        // StateListActivty context;
//        private Context con;
//
//        public TripAdapter(final Context c) {
//            super(c, R.layout.triprow, datasource.getAllComments());// locallist
//
//            con = c;
//        }
//
//        @Override
//        public View getView(final int position, final View convertView,
//                            final ViewGroup parent) {
//            View v = convertView;
//
//            if (v == null) {
//                final LayoutInflater vi = (LayoutInflater) con
//                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                v = vi.inflate(R.layout.triprow, null);
//                //Log.d("**************. ", "--v--");
//            }
//            ///Log.d("**************. ", "out----" + position);
//
//
//
//            //Log.d("******check********. ", "===" +datasource.getAllComments());
//            //String place = datasource.getAllComments().get(position).getStartplace();
//            //Log.d("******ss***datr*****. ", "===" +place);
//            String s_place = datasource.getAllComments().get(position).getStartplace().toString();
//
//            //Log.d("******im*******. ", "===" +im);
//            final TextView textView = (TextView) v.findViewById(R.id.offertext);
//            textView.setText(s_place);
//
//            String s_km = datasource.getAllComments().get(position).getStartkm().toString();
//            final TextView startkm = (TextView) v.findViewById(R.id.s_km_id);
//            startkm.setText(s_km);
//
//            String s_time = datasource.getAllComments().get(position).getStartdate().toString();
//            final TextView timeid = (TextView) v.findViewById(R.id.time_id);
//            timeid.setText(s_time);
//
//            String end_place = datasource.getAllComments().get(position).getEndplace().toString();
//            final TextView endplace = (TextView) v.findViewById(R.id.endplace_id);
//            endplace.setText(end_place);
//
//            String end_km = datasource.getAllComments().get(position).getEndKM().toString();
//            final TextView endkm = (TextView) v.findViewById(R.id.endkm_id);
//            endkm.setText(end_km);
//
//            String end_date = datasource.getAllComments().get(position).getEnddate().toString();
//            final TextView enddate = (TextView) v.findViewById(R.id.endtime_id);
//            enddate.setText(end_date);
//
//
//            return v;
//
//        }
//    }

    private class SendOperation extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {

            try {


                Log.d("response---", "********" );
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Url.PRODUCT_URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("response---", "********" + response.toString());


                                Log.d("OutPut---", "----");
                                JSONArray mArray;
                                try {
                                   // Log.d("OutPut---", "----");
                                    JSONObject reader = new JSONObject(response.toString());

                                    Log.d("OutPut---", "-arry---"+reader.getJSONArray("product_list"));
                                    JSONArray ja = reader.getJSONArray("product_list");
                                    for (int i = 0; i < ja.length(); i++) {


                                        Log.d("OutPut---", "----"+ja.get(i));
                                        Log.d("OutPut---", "---ll-"+ja.length());

                                       // JSONArray jj = ja.getJSONArray(i);
                                        HashMap<String, String> contact = new HashMap<>();

                                        for (int j = 0; j < ja.length(); j++) {

                                            JSONObject c = ja.getJSONObject(j);
                                            String name = c.getString("name");
                                            contact.put("name", name);
                                            String short_description = c.getString("short_description");
                                            contact.put("short_description", short_description);

                                            String sku = c.getString("sku");
                                            contact.put("sku", sku);
                                            String price = c.getString("price");
                                            contact.put("price", price);
                                            String type = c.getString("type_id");
                                            contact.put("type", type);

                                            Log.d("OutPut---", "----"+name);
                                            contactList.add(contact);


                                        }

                                        Log.d("OutPut---", "----");


                                    }
//                                    Log.d("OutPut---", "----"+reader.getString("name"));



                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                Log.d("response--flag-", ""+contactList.size() );

                                ListAdapter adapter = new SimpleAdapter(ProductListActivity.this, contactList,
                                        R.layout.triprow, new String[]{ "name","type","short_des_id","sku","price"},
                                        new int[]{R.id.offertext, R.id.km_id,R.id.name_id,R.id.sku_id,R.id.price_id});
                                mListView.setAdapter(adapter);
                                Toast.makeText(ProductListActivity.this, "Success "+ name, Toast.LENGTH_SHORT).show();

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
                                Toast.makeText(ProductListActivity.this,error.toString(),Toast.LENGTH_LONG).show();

                                NetworkResponse networkResponse = error.networkResponse;
                                if (networkResponse != null) {
                                    Log.e("Volley", "Error. HTTP Status Code:"+networkResponse.statusCode);
                                }
                                Toast.makeText(ProductListActivity.this, "Invalid response", Toast.LENGTH_SHORT).show();
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
                        params.put("category_id","5");
                        params.put("page","1");
                        params.put("page_size","3");

                        try {
                            JSONObject data = new JSONObject();
                           // data.put("loginName", user);
                           // data.put("loginPass", pass);
                            data.put("category_id","18");
                            data.put("page","1");
                            data.put("page_size","3");
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

                RequestQueue requestQueue = Volley.newRequestQueue(ProductListActivity.this);
                requestQueue.add(stringRequest);
                Log.d("====22====","----"+flag);

            } catch (Exception e) {
                e.printStackTrace();

            }
            return "";
        }


        @Override
        protected void onPostExecute(String result) {

            ListAdapter adapter = new SimpleAdapter(ProductListActivity.this, contactList,
                    R.layout.triprow, new String[]{ "name","type","short_des_id","sku","price"},
                    new int[]{R.id.offertext, R.id.km_id,R.id.name_id,R.id.sku_id,R.id.price_id});
            mListView.setAdapter(adapter);
            mListView.invalidate();
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

}

//}
