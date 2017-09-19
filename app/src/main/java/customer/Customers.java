
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

    ArrayList<TempCustomers> arraylist = new ArrayList<TempCustomers>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.customers);
        con = this;
        source = getIntent().getStringExtra("SOURCE");
        Log.d("Customers", source);

        dbHelper = new DatabaseHelper(this);
        dbHelper.open();
        cust = SharedPreferencesHelper.getCust(con);
        if(cust.equalsIgnoreCase("cust")){
        Cursor cursor = dbHelper.getAllCustomers();
        cursor.moveToFirst();

        AllTempCustomers.removeAllData();
        do {

            tempCustomers = new TempCustomers();

            // temp.put("name",
            // cursor.getString(cursor.getColumnIndex(DatabaseHelper.PRODUCT_NAME)));
            Log.d("====",
                    "....." + cursor.getString(cursor.getColumnIndex(DatabaseHelper.CUST_NAME)));
            // temp1.addElement(cursor.getString(cursor.getColumnIndex(DatabaseHelper.PRODUCT_NAME)));
            tempCustomers.setCustName(cursor.getString(cursor
                    .getColumnIndex(DatabaseHelper.CUST_NAME)));
            tempCustomers.setCustAddress(cursor.getString(cursor
                    .getColumnIndex(DatabaseHelper.CUST_ADDRESS)));
            tempCustomers.setCustCode(cursor.getString(cursor
                    .getColumnIndex(DatabaseHelper.CUST_CODE)));
            tempCustomers.setDiscPercent(cursor.getString(cursor
                    .getColumnIndex(DatabaseHelper.DISC_PERCENT)));

            AllTempCustomers.setData(tempCustomers);

            arraylist.add(tempCustomers);

            tempCustomers = null;

            // add the row to the ArrayList
            // originalValues.add(temp);
            // mItems.add(cursor.getString(cursor.getColumnIndex(DatabaseHelper.PRODUCT_NAME)));
        } while (cursor.moveToNext());

        // adapter = new CustomerAdapter(this, cursor,arraylist);

        adapter = new CustomersAdapter(this, cursor, arraylist);

        // customers = (ListView)findViewById(R.id.customers);
        // customers.setAdapter(adapter);
        // customers.setOnItemClickListener(this);

//        mListView = (IndexableListView)findViewById(R.id.listview);
//        mListView.setAdapter(adapter);
//        mListView.setFastScrollEnabled(true);
//        mListView.setOnItemClickListener(this);

        inputsearch = (EditText)findViewById(R.id.inputSearch);
        inputsearch.setFocusableInTouchMode(true);

        // Capture Text in EditText
        inputsearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                String text = inputsearch.getText().toString().toLowerCase(Locale.getDefault());
                adapter.filter(text);

                adapter.notifyDataSetChanged();
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
    else{
    	AlertMessage.showMessage(con, "No data found.", "Please Syncing customers with server and try again.");
    	  dbHelper.close();
    }
    }

    public void back(View v) {
        finish();

    }

    //
    // @Override
    // public void onItemClick(AdapterView<?> parent, View view, int position,
    // long id) {
    // Log.d("......on item click..."+position, "===");
    // if (source.equals("ORDER")){
    // //Cursor cursor = (Cursor)parent.getItemAtPosition(position);
    // dbHelper.open();
    // Cursor cursor = dbHelper.getCustomerById(id);
    // cursor.moveToFirst();
    // Log.d("......on item click..."+position, "===");
    // ContentValues values = new ContentValues();
    // values.put(DatabaseHelper.CUST_CODE,
    // cursor.getString(cursor.getColumnIndex(DatabaseHelper.CUST_CODE)));
    // values.put(DatabaseHelper.CUST_NAME,
    // cursor.getString(cursor.getColumnIndex(DatabaseHelper.CUST_NAME)));
    // values.put(DatabaseHelper.CUST_ADDRESS,
    // cursor.getString(cursor.getColumnIndex(DatabaseHelper.CUST_ADDRESS)));
    // values.put(DatabaseHelper.DISC_PERCENT,
    // cursor.getString(cursor.getColumnIndex(DatabaseHelper.DISC_PERCENT)));
    //
    // // dbHelper.open();
    // dbHelper.updateOrder(values, Constants.getOrderId());
    // dbHelper.close();
    //
    // finish();
    // }
    // }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
        // TODO Auto-generated method stub
        Log.d("......on item click..." + position, "===");
    }
}
