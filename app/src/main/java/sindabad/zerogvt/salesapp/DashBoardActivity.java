package sindabad.zerogvt.salesapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;


public class DashBoardActivity extends Activity implements OnClickListener {
	/** Called when the activity is first created. */

	private ImageView neworder, draft, sent, customer, products, sync_data,
			about, logout;

	//private DatabaseHelper dbHelper;
	String cus = "", prod = "",draf="",send="";
	private Context con;
	Cursor cursor;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dashboardscreen);
		con = this;
//		dbHelper = new DatabaseHelper(this);
		neworder = (ImageView) findViewById(R.id.order_id);
		draft = (ImageView) findViewById(R.id.draft_id);
		sent = (ImageView) findViewById(R.id.sent_id);
		customer = (ImageView) findViewById(R.id.customer_id);
		products = (ImageView) findViewById(R.id.product_id);
		sync_data = (ImageView) findViewById(R.id.sync_id);
		about = (ImageView) findViewById(R.id.about_id);
		logout = (ImageView) findViewById(R.id.logout_id);

		neworder.setOnClickListener(this);
		draft.setOnClickListener(this);
		sent.setOnClickListener(this);
		customer.setOnClickListener(this);
		products.setOnClickListener(this);
		sync_data.setOnClickListener(this);
		about.setOnClickListener(this);
		logout.setOnClickListener(this);
		
		
		TelephonyManager telemamanger = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		String getSimSerialNumber = telemamanger.getSimSerialNumber();
		
		String s = telemamanger.getDeviceId();
		
		Log.d("......"+s, ">>>>"+getSimSerialNumber);

	}

	public void back(View v) {
		finish();

	}
	
	
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {

		case 1: {
			final AlertDialog alertbuilder = new AlertDialog.Builder(
					DashBoardActivity.this).create();
			 alertbuilder.setTitle("Sales Order");
			alertbuilder.setMessage("Do you want to exit?");
			alertbuilder.setButton(DialogInterface.BUTTON_POSITIVE, "No",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {


						}
					});
			alertbuilder.setButton(DialogInterface.BUTTON_NEGATIVE, "Yes",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							//Main.getInstance().mRedrawHandler.removeMessages(0);
							//Main.getInstance().finish();
							finish();
						}
					});

			return alertbuilder;
		}
		}
		return super.onCreateDialog(id);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
		case R.id.order_id:
			// dbHelper.open();
			// long rowId = dbHelper.createNewOrder();
			// Constants.setOrderId(dbHelper.getOrderId(rowId));
			// dbHelper.close();

			cus = SharedPreferencesHelper.getCust(con);
			prod = SharedPreferencesHelper.getProd(con);
			 

//			if (cus.equalsIgnoreCase("cust") && prod.equalsIgnoreCase("prod")) {
//				Intent intent = new Intent(this, Master.class);
//
//				intent.putExtra("neworder", "new");
//				Master.selected = false;
//				startActivity(intent);
//				overridePendingTransition(R.anim.fadein, R.anim.fadeout);
//			} else {
//				AlertMessage
//						.showMessage(con, "No data found.",
//								"Please Syncing customers and products with server and try again.");
//
//			}

			break;

		case R.id.draft_id:

			draf = SharedPreferencesHelper.getDraft(con);
			//prod = SharedPreferencesHelper.getProd(con);
			
//			 dbHelper.open();
//
//		      cursor = dbHelper.getOrdersByStatus("D");
//
//			if (draf.equalsIgnoreCase("draft") && cursor.moveToFirst()) {
//				Intent draft = new Intent(this, Drafts.class);
//				startActivity(draft);
//				overridePendingTransition(R.anim.fadein, R.anim.fadeout);
//			} else {
//				AlertMessage
//						.showMessage(con, getString(R.string.draft),
//								getString(R.string.draftmsg));
//
//			}
			break;
		case R.id.sent_id:
			send = SharedPreferencesHelper.getSent(con);
			//prod = SharedPreferencesHelper.getProd(con);

//			if (send.equalsIgnoreCase("sent")) {
//				Intent sent = new Intent(this, SentsNew.class);
//				startActivity(sent);
//				overridePendingTransition(R.anim.fadein, R.anim.fadeout);
//			} else {
//				AlertMessage
//						.showMessage(con, getString(R.string.sent),getString(R.string.sentmsg));
//
//			}
			break;
		case R.id.customer_id:
			cus = SharedPreferencesHelper.getCust(con);
		
//
//			if (cus.equalsIgnoreCase("cust")) {
//				Intent customerIntent = new Intent(this, Customers.class);
//				customerIntent.putExtra("SOURCE", "GENERAL");
//				startActivity(customerIntent);
//				overridePendingTransition(R.anim.fadein, R.anim.fadeout);
//			} else {
//				AlertMessage
//						.showMessage(con, getString(R.string.customers),
//								getString(R.string.custmsg));
//
//			}
			break;
		case R.id.product_id:
			cus = SharedPreferencesHelper.getCust(con);
			prod = SharedPreferencesHelper.getProd(con);

//			if (prod.equalsIgnoreCase("prod")) {
//				Intent product = new Intent(this, Products.class);
//				startActivity(product);
//				overridePendingTransition(R.anim.fadein, R.anim.fadeout);
//			} else {
//				AlertMessage
//						.showMessage(con, getString(R.string.products),
//								getString(R.string.prodmsg));
//
//			}
			break;
		case R.id.sync_id:
//			Intent sync = new Intent(this, SyncApps.class);
//			startActivity(sync);
			break;
		case R.id.about_id:
//			Intent aboutIntent = new Intent(this, About.class);
//			startActivity(aboutIntent);
//			overridePendingTransition(R.anim.fadein, R.anim.fadeout);
			break;
		case R.id.logout_id:
//			for (int i = 0; i < OrderInstance.v_context.size(); i++) {
//				   (OrderInstance.v_context.elementAt(i)).finish();
//				   Log.e("Activity :" + i, "finished...");
//			}
			
			showDialog(1);
			//finish();
			break;
		}

	}
	
	
}
