
package customer;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AlphabetIndexer;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import database.DatabaseHelper;
import sindabad.zerogvt.salesapp.R;


public class CustomersAdapter extends BaseAdapter implements SectionIndexer {

    AlphabetIndexer mAlphabetIndexer;

    Context mContext;

    LayoutInflater inflater;

    private List<TempCustomers> mCustomerlist = null;

    private ArrayList<TempCustomers> arraylist;

    Cursor cursor;

    public CustomersAdapter(Context context, Cursor cursor, List<TempCustomers> customerlist) {

        mContext = context;
        this.cursor = cursor;
        mCustomerlist = customerlist;
        inflater = LayoutInflater.from(mContext);
        arraylist = new ArrayList<TempCustomers>();
        arraylist.addAll(customerlist);

        mAlphabetIndexer = new AlphabetIndexer(cursor,
                cursor.getColumnIndex(DatabaseHelper.CUST_NAME), " ABCDEFGHIJKLMNOPQRTSUVWXYZ");
        mAlphabetIndexer.setCursor(cursor);
        cursor.moveToFirst();

        Log.d("..[=====]....", "......");
        Log.d("..[=====]...." + arraylist.size(), "......" + customerlist.size());

        // mAlphabetIndexer = new AlphabetIndexer(cursor,
        // cursor.getColumnIndex(DatabaseHelper.PRODUCT_NAME),
        // " ABCDEFGHIJKLMNOPQRTSUVWXYZ");
        // mAlphabetIndexer.setCursor(cursor);
    }

    public class ViewHolder {

        TextView custName, custAddress, custCode, discPercent;
    }

    @Override
    public int getCount() {
        return mCustomerlist.size();
    }

    @Override
    public TempCustomers getItem(int position) {
        return mCustomerlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.customer, null);
            // Locate the TextViews in listview_item.xml
            holder.custName = (TextView)convertView.findViewById(R.id.custName);
            holder.custAddress = (TextView)convertView.findViewById(R.id.custAddress);

            holder.custCode = (TextView)convertView.findViewById(R.id.custCode);

            holder.discPercent = (TextView)convertView.findViewById(R.id.discPercent);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }
        AllTempCustomers.getData(position);
        holder.custName.setText(mCustomerlist.get(position).getCustName());
        holder.custAddress.setText(mCustomerlist.get(position).getCustAddress());
        holder.custCode.setText("Code:"+mCustomerlist.get(position).getCustCode());
        holder.discPercent.setText("Discount:"+mCustomerlist.get(position).getDiscPercent()+"%");

        // Listen for ListView Item Click
        convertView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // Send single item click data to SingleItemView Class
                // Intent intent = new Intent(mContext, SingleItemView.class);
                // // Pass all data rank
                // intent.putExtra("productname",
                // (worldpopulationlist.get(position).getProduct_name()));
                // // Pass all data country
                // intent.putExtra("productcode",
                // (worldpopulationlist.get(position).getProduct_code()));
                // // Pass all data population
                // intent.putExtra("packsize",
                // (worldpopulationlist.get(position).getPacket_size()));
                // // Pass all data flag
                // intent.putExtra("unitp",
                // (worldpopulationlist.get(position).getUnit()));
                // intent.putExtra("unitvat",
                // (worldpopulationlist.get(position).getUnitvat()));
                // // Start SingleItemView Class
                // mContext.startActivity(intent);
            }
        });

        return convertView;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        mCustomerlist.clear();
        if (charText.length() == 0) {
            mCustomerlist.addAll(arraylist);
        } else {
            for (TempCustomers wp : arraylist) {
                if (wp.getCustName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    Log.d("..[][][][]...." + charText, "......" + wp.getCustName());
                    mCustomerlist.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getPositionForSection(int section) {
        // TODO Auto-generated method stub
        // for (int i = section; i >= 0; i--) {
        // for (int j = 0; j < getCount(); j++) {
        // if (i == 0) {
        // // For numeric section
        // for (int k = 0; k <= 9; k++) {
        // if (StringMatcher.match(String.valueOf(getItem(j).getProduct_name()),
        // String.valueOf(k)))
        // return j;
        // }
        // } else {
        // if (StringMatcher.match(String.valueOf(getItem(j).getProduct_name()),
        // String.valueOf(mSections.charAt(i))))
        // return j;
        // }
        // }
        // }
        // return 0;
        return mAlphabetIndexer.getPositionForSection(section);
    }

    @Override
    public int getSectionForPosition(int position) {
        // TODO Auto-generated method stub
        return mAlphabetIndexer.getSectionForPosition(position);
    }

    @Override
    public Object[] getSections() {
        // TODO Auto-generated method stub
        return mAlphabetIndexer.getSections();
        // String[] sections = new String[mSections.length()];
        // for (int i = 0; i < mSections.length(); i++)
        // sections[i] = String.valueOf(mSections.charAt(i));
        // return sections;
    }

}
