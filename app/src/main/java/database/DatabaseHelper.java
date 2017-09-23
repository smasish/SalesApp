
package database;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import sindabad.zerogvt.salesapp.SharedPreferencesHelper;
import sindabad.zerogvt.salesapp.model.Contact;
import sindabad.zerogvt.salesapp.model.Product;


public class DatabaseHelper {
    /**
     * Database Tables
     */
    public static final String PRODUCTS = "products";

    public static final String CUSTOMERS = "customers";

    public static final String ORDER_MASTER = "order_master";

    public static final String ORDER_DETAILS = "order_details";

    public static final String SEND_ORDER = "send_order";

    /**
     * Columns
     */
    public static final String ROW_ID = "_id";

    public static final String PRODUCT_CODE = "product_code";

    public static final String PRODUCT_NAME = "product_name";

    public static final String PACK_SIZE = "pack_size";

    public static final String UNIT_TP = "unit_tp";
    public static final String ORDER_TP = "order_tp";

    public static final String UNIT_VAT = "unit_vat";
    public static final String PRODUCT_STATUS = "product_status";
    public static final String PRODUCT_VERSION = "product_version";

    public static final String CUST_CODE = "cust_code";

    public static final String CUST_STATUS = "cust_status";
    public static final String CUST_VERSION = "cust_version";

    public static final String CUST_NAME = "cust_name";

    public static final String CUST_ADDRESS = "cust_address";

    public static final String DISC_PERCENT = "disc_percent";

    public static final String ORDER_ID = "order_id";

    public static final String ORDER_NO = "order_no";

    public static final String ORDER_DATE = "order_date";

    public static final String DELIVERY_DATE = "delivery_date";

    public static final String DELIVERY_TIME = "delivery_time";

    public static final String ORDER_STATUS = "order_status";

    public static final String ORDER_QTY = "order_qty";

    /**
     * Private variables and constants uses on DatabaseOpenHelper class
     */
    private final String DATABASE_NAME = "salesorder.db";

    private final int DATABASE_VERSION = 1;

    private final String TAG = "DatabaseHelper";

    private final String CREATE_PRODUCTS = "CREATE TABLE " + PRODUCTS + " ( " + ROW_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + PRODUCT_CODE + " TEXT, " + PRODUCT_NAME
            + " TEXT, " + PACK_SIZE + " TEXT, " + UNIT_TP + " TEXT, " + UNIT_VAT + " TEXT, " + PRODUCT_STATUS + " TEXT, " + PRODUCT_VERSION + " TEXT );";

    private final String CREATE_CUSTOMERS = "CREATE TABLE " + CUSTOMERS + " ( " + ROW_ID
            + " INTEGER PRIMARY KEY, " + CUST_CODE + " TEXT, " + CUST_NAME + " TEXT, "
            + CUST_ADDRESS + " TEXT, " + DISC_PERCENT + " TEXT , "
            + CUST_STATUS + " TEXT, " + CUST_VERSION + " TEXT);";

    private final String CREATE_ORDER_MASTER = "CREATE TABLE " + ORDER_MASTER + " ( " + ROW_ID
            + " INTEGER PRIMARY KEY, " + ORDER_ID + " TEXT, " + ORDER_NO + " TEXT, " + ORDER_DATE
            + " TEXT, " + DELIVERY_DATE + " TEXT, " + DELIVERY_TIME + " TEXT, " + CUST_CODE
            + " TEXT, " + CUST_NAME + " TEXT, " + CUST_ADDRESS + " TEXT, " + DISC_PERCENT
            + " TEXT, " + ORDER_STATUS + " TEXT, " + ORDER_QTY + " TEXT, " + ORDER_TP + " TEXT );";

    private final String CREATE_ORDER_DETAILS = "CREATE TABLE " + ORDER_DETAILS + " ( " + ROW_ID
            + " INTEGER PRIMARY KEY, " + ORDER_ID + " TEXT, " + PRODUCT_CODE + " TEXT, "
            + PRODUCT_NAME + " TEXT, " + PACK_SIZE + " TEXT, " + UNIT_TP + " TEXT, " + UNIT_VAT
            + " TEXT, " + ORDER_QTY + " TEXT );";
    //===============================================================
    // Contacts table name
    private static final String TABLE_CONTACTS = "contacts";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_FNAME = "fname";
    private static final String KEY_POTO = "poto";


    String CREATE_TABLE_CONTACTS = "CREATE TABLE " + TABLE_CONTACTS + "("
            + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_FNAME + " TEXT,"
            + KEY_POTO + " BLOB" + ")";


    private DatabaseOpenHelper dbOpenHelper;

    private SQLiteDatabase database;

    private Context con;

    private class DatabaseOpenHelper extends SQLiteOpenHelper {

        public DatabaseOpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);

            con = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_PRODUCTS);
            db.execSQL(CREATE_CUSTOMERS);
            db.execSQL(CREATE_ORDER_MASTER);
            db.execSQL(CREATE_ORDER_DETAILS);
            db.execSQL(CREATE_TABLE_CONTACTS);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.d(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion
                    + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + PRODUCTS);
            db.execSQL("DROP TABLE IF EXISTS " + CUSTOMERS);
            db.execSQL("DROP TABLE IF EXISTS " + ORDER_MASTER);
            db.execSQL("DROP TABLE IF EXISTS " + ORDER_DETAILS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
            onCreate(db);
        }

    }

    public DatabaseHelper(Context context) {
        dbOpenHelper = new DatabaseOpenHelper(context);
    }

    public void open() throws SQLException {
        database = dbOpenHelper.getWritableDatabase();
    }

    public void close() {
        database.close();
    }

    public Cursor getAllProducts() {
        String[] columns = {
                ROW_ID, PRODUCT_CODE, PRODUCT_NAME, PACK_SIZE, UNIT_TP, UNIT_VAT
        };
        return database.query(PRODUCTS, columns, null, null, null, null, PRODUCT_NAME + " ASC");
    }

    // public List<Product> getAllComments() {
    // List<Product> datas = new ArrayList<Product>();
    // Cursor cursor = database.query(CREATE_PRODUCTS,
    // allColumns, null, null, null, null, null);
    // cursor.moveToFirst();
    // while (!cursor.isAfterLast()) {
    // Product data = cursorToComment(cursor);
    // datas.add(data);
    // cursor.moveToNext();
    // }
    // // Make sure to close the cursor
    // cursor.close();
    // return datas;
    // }

//    public long createProduct(Product product) {
//        ContentValues values = new ContentValues();
//        values.put(PRODUCT_CODE, product.getProductCode());
//        values.put(PRODUCT_NAME, product.getProductName());
//        values.put(PACK_SIZE, product.getPackSize());
//        values.put(UNIT_TP, product.getUnitTp());
//        values.put(UNIT_VAT, product.getUnitVat());
//        values.put(PRODUCT_STATUS, product.getProductStatus());
//        values.put(PRODUCT_VERSION, product.getProductVersion());
//
//        return database.insert(PRODUCTS, null, values);
//    }

    public void deleteAllProducts() {
        database.delete(PRODUCTS, null, null);
    }

//    public long createCustomer(Customer customer) {
//        ContentValues values = new ContentValues();
//        values.put(CUST_CODE, customer.getCustCode());
//        values.put(CUST_NAME, customer.getCustName());
//        values.put(CUST_ADDRESS, customer.getCustAddress());
//        values.put(DISC_PERCENT, customer.getDiscPercent());
//        values.put(CUST_STATUS, customer.getCustStatus());
//        values.put(CUST_VERSION, customer.getCustVersion());
//
//
//        return database.insert(CUSTOMERS, null, values);
//    }

    public void deleteAllCustomers() {
        database.delete(CUSTOMERS, null, null);
    }

    public Cursor getAllCustomers() {
        String[] columns = {
                ROW_ID, CUST_CODE, CUST_NAME, CUST_ADDRESS, DISC_PERCENT
        };
        return database.query(CUSTOMERS, columns, null, null, null, null, CUST_NAME + " ASC");
    }

    public void deleteEntry(long row) {

        // Deletes a row given its rowId, but I want to be able to pass
        // in the name of the KEY_NAME and have it delete that row.
        database.delete(ORDER_MASTER, ROW_ID + "=" + row, null);
    }

    public void deleteOrder(String orderid) {

        // Deletes a row given its rowId, but I want to be able to pass
        // in the name of the KEY_NAME and have it delete that row.
        database.delete(ORDER_MASTER, ORDER_ID + "=" + orderid, null);
    }

    public void deleteRow(String row) {

        // Deletes a row given its rowId, but I want to be able to pass
        // in the name of the KEY_NAME and have it delete that row.
        // database.delete(ORDER_MASTER, ORDER_NO + "=" + row, null);
        database.delete(ORDER_MASTER, ORDER_ID + "=" + row, null);
    }

    public void deleteRowforNull(String row, String qty) {

        // Deletes a row given its rowId, but I want to be able to pass
        // in the name of the KEY_NAME and have it delete that row.
        // database.delete(ORDER_MASTER, ORDER_NO + "=" + row, null);
        database.delete(ORDER_DETAILS, ORDER_ID + " = ? AND " + ORDER_QTY + " = ?", new String[]{row, qty});
    }

    public void deleteSummaryItem(String productCode, String orderid) {

        // Deletes a row given its rowId, but I want to be able to pass
        // in the name of the KEY_NAME and have it delete that row.
        // database.delete(ORDER_MASTER, ORDER_NO + "=" + row, null);
        //database.delete(ORDER_DETAILS, PRODUCT_CODE + "=" + productCode, null);

        database.delete(ORDER_DETAILS, PRODUCT_CODE + " = ? AND " + ORDER_ID + " = ?", new String[]{productCode, orderid + ""});
    }

    public void deleteAllOrder() {
        //database.delete(ORDER_MASTER, null, null);

        database.delete(ORDER_MASTER, ORDER_STATUS + "=" + "'D'", null);
    }

    public void deleteAllSentOrder() {
        //database.delete(ORDER_MASTER, null, null);

        database.delete(ORDER_MASTER, ORDER_STATUS + "=" + "'S'", null);
    }

    public long createNewOrder() {
        ContentValues values = new ContentValues();
        values.put(ORDER_DATE, getTodaysDate());
        values.put(ORDER_ID, getNextOrderId()); // ORDER_ID
        values.put(DELIVERY_TIME, "M");
        values.put(ORDER_STATUS, "D");
        return database.insert(ORDER_MASTER, null, values);
    }

    private String getNextOrderId() {
        Cursor cursor = database.rawQuery("SELECT IFNULL(MAX(" + ROW_ID + "), 0) + 1 FROM "
                + ORDER_MASTER, null);   // replaced ORDER_ID by ROW_ID
        cursor.moveToFirst();
        Log.d("----======...", "---->..." + cursor.getString(0));
        int a = Integer.parseInt(SharedPreferencesHelper.getNewId(con));
        a = a + 1;
        int b = Integer.parseInt(cursor.getString(0));

        String c = "0";
        if (b > a)
            c = Integer.toString(b);
        else
            c = Integer.toString(a);
        SharedPreferencesHelper.setNewId(con, c);

        Log.d("->>>>>a b>>>>>>>>>>>==" + a + "...b" + b, "---->..." + c);

        return c; //cursor.getString(0)
    }

    private String getTodaysDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
        Date date = new Date(System.currentTimeMillis());
        String todaysDate = dateFormat.format(date);
        return todaysDate;
    }

    public int updateOrder(ContentValues values, String orderId) {
        return database.update(ORDER_MASTER, values, ORDER_ID + " = '" + orderId + "'", null);
    }

    public Cursor getOrder(String orderId) {
        String[] columns = {
                ROW_ID, ORDER_ID, ORDER_NO, ORDER_DATE, DELIVERY_DATE, DELIVERY_TIME, CUST_CODE,
                CUST_NAME, CUST_ADDRESS, DISC_PERCENT, ORDER_STATUS, ORDER_QTY, ORDER_TP
        };
        return database.query(ORDER_MASTER, columns, ORDER_ID + " = '" + orderId + "'", null, null,
                null, null);
    }

    public Cursor getDraftOrder(String custId) {
        String[] columns = {
                ROW_ID, ORDER_ID, ORDER_NO, ORDER_DATE, DELIVERY_DATE, DELIVERY_TIME, CUST_CODE,
                CUST_NAME, CUST_ADDRESS, DISC_PERCENT, ORDER_STATUS, ORDER_QTY, ORDER_TP
        };
        return database.query(ORDER_MASTER, columns, CUST_CODE + " = '" + custId + "'", null, null,
                null, null);
    }

    public Cursor getOrdersByStatus(String status) {
        String[] columns = {
                ROW_ID, ORDER_ID, ORDER_NO, ORDER_DATE, DELIVERY_DATE, DELIVERY_TIME, CUST_CODE,
                CUST_NAME, CUST_ADDRESS, DISC_PERCENT, ORDER_STATUS, ORDER_QTY, ORDER_TP
        };
        return database.query(ORDER_MASTER, columns, ORDER_STATUS + " = '" + status + "'", null,
                null, null, ORDER_DATE + " DESC");
    }

    public Cursor getOrdersByStatusForDraft(String status) {
        String[] columns = {
                ROW_ID, ORDER_ID, ORDER_NO, ORDER_DATE, DELIVERY_DATE, DELIVERY_TIME, CUST_CODE,
                CUST_NAME, CUST_ADDRESS, DISC_PERCENT, ORDER_STATUS, ORDER_QTY, ORDER_TP
        };
        return database.query(ORDER_MASTER, columns, ORDER_STATUS + " = '" + status + "'", null,
                null, null, ORDER_DATE + " DESC");
    }

    public Cursor getCustomerById(long id) {
        String[] columns = {
                ROW_ID, CUST_CODE, CUST_NAME, CUST_ADDRESS, DISC_PERCENT
        };
        return database.query(CUSTOMERS, columns, ROW_ID + " = " + id, null, null, null, null);
    }

    public String getOrderId(long id) {
        String[] columns = {
                ORDER_ID
        };
        open();
        Log.d(">>>>....", "...id.... " + id);
        Cursor cursor = database.query(ORDER_MASTER, columns, ROW_ID + " = " + id, null, null,
                null, null);
        cursor.moveToFirst();

        String orderId = cursor.getString(cursor.getColumnIndex(ORDER_ID));//ORDER_ID
        Log.d(">>>>.db...", ".....orderId id.. " + orderId);
        close();
        return orderId;
    }

    public Cursor copyrowFull(String row) {
        Cursor cursor = database
                .rawQuery(
                        "select * from order_details where order_id = '"
                                + row + "' ", new String[]{});

        return cursor;
    }

    public Cursor copyrow(String row) {
        Cursor cursor = database
                .rawQuery(
                        "select * from order_master where order_id = '"
                                + row + "' ", new String[]{});

        return cursor;
    }

    public Cursor getOrderedName() {
        Cursor cursor = database
                .rawQuery(
                        "SELECT * FROM order_master WHERE order_status = '"
                                + "D" + "' ORDER BY cust_name ASC", new String[]{});

//    	 Cursor cursor = database
//         .rawQuery(
//                 "SELECT * FROM order_master WHERE order_status = '"
//         + "D" + "' ORDER BY order_date ASC",new String[]{});
        return cursor;
    }

    public Cursor getOrderedNameDes() {
        Cursor cursor = database
                .rawQuery(
                        "SELECT * FROM order_master WHERE order_status = '"
                                + "D" + "' ORDER BY cust_name DESC", new String[]{});


        return cursor;
    }

    public Cursor getByOrderDate() {

        Cursor cursor = database
                .rawQuery(
                        "SELECT * FROM order_master WHERE order_status = '"
                                + "D" + "' ORDER BY order_date ASC", new String[]{});
        return cursor;
    }

    public Cursor getByOrderDateDes() {

        Cursor cursor = database
                .rawQuery(
                        "SELECT * FROM order_master WHERE order_status = '"
                                + "D" + "' ORDER BY order_date DESC", new String[]{});
        return cursor;
    }

    public Cursor getSentOrderedName() {
        Cursor cursor = database
                .rawQuery(
                        "SELECT * FROM order_master WHERE order_status = '"
                                + "S" + "' ORDER BY cust_name ASC", new String[]{});

//    	 Cursor cursor = database
//         .rawQuery(
//                 "SELECT * FROM order_master WHERE order_status = '"
//         + "D" + "' ORDER BY order_date ASC",new String[]{});
        return cursor;
    }

    public Cursor getSentOrderedNameDes() {
        Cursor cursor = database
                .rawQuery(
                        "SELECT * FROM order_master WHERE order_status = '"
                                + "S" + "' ORDER BY cust_name DESC", new String[]{});

//       Cursor cursor = database
//         .rawQuery(
//                 "SELECT * FROM order_master WHERE order_status = '"
//         + "D" + "' ORDER BY order_date ASC",new String[]{});
        return cursor;
    }

    public Cursor getSentOrderByDate() {

        Cursor cursor = database
                .rawQuery(
                        "SELECT * FROM order_master WHERE order_status = '"
                                + "S" + "' ORDER BY order_date ASC", new String[]{});
        return cursor;
    }

    public Cursor getSentOrderByDateDesc() {

        Cursor cursor = database
                .rawQuery(
                        "SELECT * FROM order_master WHERE order_status = '"
                                + "S" + "' ORDER BY order_date DESC", new String[]{});
        return cursor;
    }

    public Cursor getOrderItems(String orderId) {
        Cursor cursor = database
                .rawQuery(
                        "SELECT p._id, d.order_id, p.product_code, p.product_name, p.pack_size, p.unit_tp, p.unit_vat, d.order_qty FROM products p LEFT OUTER JOIN (SELECT * FROM order_details WHERE order_id = '"
                                + orderId + "') d ON d.product_code = p.product_code" + " ORDER BY p.product_name ASC", null);
        return cursor;
    }

    public void updateOrderItems(String orderId, String productCode, String productName,
                                 String packSize, String unitTp, String unitVat, String orderQty) {
        Cursor cursor = database.rawQuery("SELECT COUNT(1) FROM order_details WHERE order_id = '"
                + orderId + "' AND product_code = '" + productCode + "'", null);
        cursor.moveToFirst();
        String count = cursor.getString(0);
        if (Integer.valueOf(count) > 0) {
            ContentValues values = new ContentValues();
            values.put(PRODUCT_NAME, productName);
            values.put(PACK_SIZE, packSize);
            values.put(UNIT_TP, unitTp);
            values.put(UNIT_VAT, unitVat);
            values.put(ORDER_QTY, orderQty);
            database.update(ORDER_DETAILS, values, ORDER_ID + " = '" + orderId + "' AND "
                    + PRODUCT_CODE + " = '" + productCode + "'", null);
        } else {
            ContentValues values = new ContentValues();
            values.put(ORDER_ID, orderId);
            values.put(PRODUCT_CODE, productCode);
            values.put(PRODUCT_NAME, productName);
            values.put(PACK_SIZE, packSize);
            values.put(UNIT_TP, unitTp);
            values.put(UNIT_VAT, unitVat);
            values.put(ORDER_QTY, orderQty);
            database.insert(ORDER_DETAILS, null, values);
        }

    }

    public Cursor getOrderSummary(String orderId) {
        return database
                .rawQuery(
                        "SELECT d._id, d.order_id, d.product_code, d.product_name, d.pack_size, d.unit_tp, d.unit_vat, d.order_qty FROM order_details d WHERE d.order_id = '"
                                + orderId + "'" + " ORDER BY d.product_name ASC", null);
    }

    public boolean updateOrderStatus(String orderId, String status) {
        ContentValues values = new ContentValues();
        values.put(ORDER_STATUS, status);
        return database.update(ORDER_MASTER, values, ORDER_ID + " = '" + orderId + "'", null) > 0;
    }

    public boolean updateDeliveryTime(String orderId, String deliveryTime) {
        ContentValues values = new ContentValues();
        values.put(DELIVERY_TIME, deliveryTime);
        return database.update(ORDER_MASTER, values, ORDER_ID + " = '" + orderId + "'", null) > 0;
    }

    //=================================================
    //
    //===================================================
    //Insert values to the table contacts
    public void addContacts(Contact contact) {
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_FNAME, contact.getFName());
        values.put(KEY_POTO, contact.getImage());


        db.insert(TABLE_CONTACTS, null, values);
        db.close();
    }


    public List<Contact> getAllContacts() {
        List<Contact> contactList = new ArrayList<Contact>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact();
                contact.setID(Integer.parseInt(cursor.getString(0)));
                contact.setFName(cursor.getString(1));
                contact.setImage(cursor.getBlob(2));


                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
        return contactList;
    }

    public List<Product> getAllProduct() {
        List<Product> contactList = new ArrayList<Product>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Product product = new Product();
//                contact.setID(Integer.parseInt(cursor.getString(0)));
//                contact.setFName(cursor.getString(1));
//                contact.setImage(cursor.getBlob(2));
                product.quantity = Integer.parseInt(cursor.getString(0));
                product.title = cursor.getString(1);
                product.product_img = cursor.getBlob(2);
                product.price="100200";

                // Adding contact to list
                contactList.add(product);
            } while (cursor.moveToNext());
        }
        return contactList;
    }


    public void deleteContact(int Id) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
                new String[] { String.valueOf(Id) });
        db.close();
    }

}