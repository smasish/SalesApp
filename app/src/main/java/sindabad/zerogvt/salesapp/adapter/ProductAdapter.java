package sindabad.zerogvt.salesapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

import sindabad.zerogvt.salesapp.ProductListActivity;
import sindabad.zerogvt.salesapp.R;
import sindabad.zerogvt.salesapp.model.Product;


public class ProductAdapter extends BaseAdapter {

    public static final String TAG = ProductAdapter.class.getSimpleName();
    public static final HashMap<String, Integer> LABEL_COLORS = new HashMap<String, Integer>() {{
        put("Low-Carb", R.color.colorLowCarb);
        put("Low-Fat", R.color.colorLowFat);
        put("Low-Sodium", R.color.colorLowSodium);
        put("Medium-Carb", R.color.colorMediumCarb);
        put("Vegetarian", R.color.colorVegetarian);
        put("Balanced", R.color.colorBalanced);
    }};

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<Product> mDataSource;


    public ProductAdapter(Context context, ArrayList<Product> items) {
        mContext = context;
        mDataSource = items;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return mDataSource.size();
    }


    @Override
    public Object getItem(int position) {
        return mDataSource.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        // check if the view already exists if so, no need to inflate and findViewById again!
        if (convertView == null) {

            // Inflate the custom row layout from your XML.
            convertView = mInflater.inflate(R.layout.list_row, parent, false);

            // create a new "Holder" with subviews
            holder = new ViewHolder();
            holder.productImageView = (ImageView) convertView.findViewById(R.id.list_image);
            holder.titleTextView = (TextView) convertView.findViewById(R.id.from_name);
            holder.price_text = (TextView) convertView.findViewById(R.id.plist_price_text);
            holder.quantity = (EditText) convertView.findViewById(R.id.cart_product_quantity_tv);

            // hang onto this holder for future recyclage
            convertView.setTag(holder);
        } else {

            // skip all the expensive inflation/findViewById and just get the holder you already made
            holder = (ViewHolder) convertView.getTag();
        }

        // Get relevant subviews of row view
        TextView titleTextView = holder.titleTextView;
        TextView price_text = holder.price_text;
        final EditText quantity = holder.quantity;
        ImageView productImageView = holder.productImageView;

        //Get corresponding recipe for row
        Product recipe = (Product) getItem(position);

        // Update row view's textviews to display recipe information
        titleTextView.setText(recipe.title);
        price_text.setText("TK" + recipe.price);
        quantity.setText("" + recipe.quantity);

        // Use Picasso to load the image. Temporarily have a placeholder in case it's slow to load
        Picasso.with(mContext).load(recipe.imageUrl).placeholder(R.mipmap
                .ic_launcher).into(productImageView);

//        // Style text views
//        Typeface titleTypeFace = Typeface.createFromAsset(mContext.getAssets(),
//                "fonts/JosefinSans-Bold.ttf");
//        titleTextView.setTypeface(titleTypeFace);
//        Typeface subtitleTypeFace = Typeface.createFromAsset(mContext.getAssets(),
//                "fonts/JosefinSans-SemiBoldItalic.ttf");
//        subtitleTextView.setTypeface(subtitleTypeFace);
//        Typeface detailTypeFace = Typeface.createFromAsset(mContext.getAssets(),
//                "fonts/Quicksand-Bold.otf");
//        detailTextView.setTypeface(detailTypeFace);
//        detailTextView.setTextColor(android.support.v4.content.ContextCompat.getColor(mContext, LABEL_COLORS
//                .get(recipe.label)));
//==================================================
        quantity.setTag(position);


        quantity.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                int pos = (Integer) view.getTag();
                final EditText edit = (EditText) view;
                String editable = edit.getText().toString().trim();
//        myItems.get(position).caption = Caption.getText().toString();

                int text = 0;
                if (editable.toString().length() > 0)
                    text = Integer.parseInt(editable.toString());
                ProductListActivity.recipeList.get(pos).quantity = text;

            }
        });


        return convertView;
    }

    private static class ViewHolder {
        public TextView titleTextView;
        public TextView price_text;
        public EditText quantity;
        public ImageView productImageView;

    }
}

