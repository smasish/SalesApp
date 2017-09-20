
package customer;

import java.util.ArrayList;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;

import database.DatabaseHelper;
import sindabad.zerogvt.salesapp.AlertMessage;
import sindabad.zerogvt.salesapp.R;
import sindabad.zerogvt.salesapp.SharedPreferencesHelper;


public class Customers extends Activity implements OnItemClickListener {

    private DatabaseHelper dbHelper;

    // private CustomerAdapter adapter;
    private CustomersAdapter adapter;

    private String source;

    TempCustomers tempCustomers = null;

//    private IndexableListView mListView;

    EditText inputsearch;
    String cust="";
    private Context con;
    private ListView cust_list;

    ArrayList<TempCustomers> arraylist = new ArrayList<TempCustomers>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.customers);
        con = this;
       // source = getIntent().getStringExtra("SOURCE");
       // Log.d("Customers", source);

        cust_list = (ListView)findViewById(R.id.customers);


        dbHelper = new DatabaseHelper(this);
        dbHelper.open();
//        cust = SharedPreferencesHelper.getCust(con);
//        if(cust.equalsIgnoreCase("cust")){
//        Cursor cursor = dbHelper.getAllCustomers();
//        cursor.moveToFirst();
//
//        AllTempCustomers.removeAllData();


        // adapter = new CustomerAdapter(this, cursor,arraylist);

    //    adapter = new CustomersAdapter(this, cursor, arraylist);

        // customers = (ListView)findViewById(R.id.customers);
        // customers.setAdapter(adapter);
        // customers.setOnItemClickListener(this);



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
    }
//    else{
//    	AlertMessage.showMessage(con, "No data found.", "Please Syncing customers with server and try again.");
//    	  dbHelper.close();
//    }
 //   }

    public void back(View v) {
        finish();
    }



    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
        // TODO Auto-generated method stub
        Log.d("......on item click..." + position, "===");
    }
}
