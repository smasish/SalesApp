
package customer;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import database.DatabaseHelper;
import sindabad.zerogvt.salesapp.R;


public class CustomersSelection extends Activity implements OnItemClickListener {
    private DatabaseHelper dbHelper;

    private CustomerAdapter adapter;

    private String source;

  //  private IndexableListView mListView;

   

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.customers);
        source = getIntent().getStringExtra("SOURCE");
        Log.d("Customers", source);

        dbHelper = new DatabaseHelper(this);
        dbHelper.open();
        
        Cursor cursor = dbHelper.getAllCustomers();
        adapter = new CustomerAdapter(this, cursor);

        // customers = (ListView)findViewById(R.id.customers);
        // customers.setAdapter(adapter);
        // customers.setOnItemClickListener(this);

//        mListView = (IndexableListView)findViewById(R.id.listview);
//        mListView.setAdapter(adapter);
//        mListView.setFastScrollEnabled(true);
//        mListView.setOnItemClickListener(this);

//        if (Master.flagdate == false) {
//            long rowId = dbHelper.createNewOrder();
//            OrderInstance.setOrderId(dbHelper.getOrderId(rowId));
//            Log.d("......on item click..." + dbHelper.getOrderId(rowId), "===" + rowId);
//        }

        dbHelper.close();
    }

    public void back(View v) {
        finish();

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (source.equals("ORDER")) {
            // Cursor cursor = (Cursor)parent.getItemAtPosition(position);
            // dbHelper = new DatabaseHelper(this);
            dbHelper.open();

            Cursor cursor = dbHelper.getCustomerById(id);
            cursor.moveToFirst();
            Log.d("......on item click..." + position, "===");
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.CUST_CODE,
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.CUST_CODE)));
            values.put(DatabaseHelper.CUST_NAME,
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.CUST_NAME)));
            values.put(DatabaseHelper.CUST_ADDRESS,
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.CUST_ADDRESS)));
            values.put(DatabaseHelper.DISC_PERCENT,
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.DISC_PERCENT)));

//            Master.selected = true;
//            OrderInstance.setChangedCust(cursor.getString(cursor.getColumnIndex(DatabaseHelper.CUST_NAME)));
            
            // dbHelper.open();
         //   dbHelper.updateOrder(values, OrderInstance.getOrderId());
            dbHelper.close();

            finish();
        }
    }

    /*
     * (non-Javadoc)
     * @see android.app.Activity#onPause()
     */
    @Override
    protected void onPause() {
        // TODO Auto-generated method stub

        dbHelper.close();
        super.onPause();
    }

}
