package sindabad.zerogvt.salesapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

import database.DatabaseHelper;
import sindabad.zerogvt.salesapp.model.Contact;
import sindabad.zerogvt.salesapp.model.Product;
import sindabad.zerogvt.salesapp.adapter.ProductAdapter;

public class ProductListActivity extends AppCompatActivity {

    //    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//    }
    public static final String TAG = ProductListActivity.class.getSimpleName();

    private ListView mListView;
    int p_index;
    EditText quantity;
    private DatabaseHelper db;
    public static ArrayList<Product> recipeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.productlist);

        final Context context = this;
        db=new DatabaseHelper(this);
        // Get data to display
//        recipeList = Product.getRecipesFromFile("product.json", this);

//        final ArrayList<Product> contacts = new ArrayList<>(db.getAllProduct());
        recipeList = new ArrayList<>(db.getAllProduct());
        // Create adapter
        ProductAdapter adapter = new ProductAdapter(this, recipeList);

        // Create list view
        mListView = (ListView) findViewById(R.id.products_listview);
        mListView.setAdapter(adapter);

        // Set what happens when a list view item is clicked

//        mLi

    }

    //==============
    public void minusFancyMethod(View v) {
        // does something very interesting
        View p = (View) v.getParent();


        Product selectedRecipe = recipeList.get(p_index);
        ImageView minusImageView = (ImageView) p.findViewById(R.id.cart_minus_img);
        ImageView plusImageView = (ImageView) p.findViewById(R.id.cart_plus_img);
        quantity = (EditText) p.findViewById(R.id.cart_product_quantity_tv);
        p_index = (Integer) quantity.getTag();

        recipeList.get(p_index).quantity = recipeList.get(p_index).quantity - 1;
        quantity.setText("" + recipeList.get(p_index).quantity);

//        Toast.makeText(ProductListActivity.this, "This is cart_minus_img---!" + p_index,
//                Toast.LENGTH_LONG).show();


    }

    public void plusFancyMethod(View v) {
        // does something very interesting
        View p = (View) v.getParent();


        Product selectedRecipe = recipeList.get(p_index);
        ImageView minusImageView = (ImageView) p.findViewById(R.id.cart_minus_img);
        ImageView plusImageView = (ImageView) p.findViewById(R.id.cart_plus_img);
        quantity = (EditText) p.findViewById(R.id.cart_product_quantity_tv);
        p_index = (Integer) quantity.getTag();

        recipeList.get(p_index).quantity = recipeList.get(p_index).quantity + 1;
        quantity.setText("" + recipeList.get(p_index).quantity);

//        Toast.makeText(ProductListActivity.this, "This is cart_minus_img---!" + p_index,
//                Toast.LENGTH_LONG).show();


    }
}

//}
