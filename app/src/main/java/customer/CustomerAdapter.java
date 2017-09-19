
package customer;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AlphabetIndexer;
import android.widget.CursorAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import database.DatabaseHelper;
import sindabad.zerogvt.salesapp.R;

public class CustomerAdapter extends CursorAdapter implements SectionIndexer {

    AlphabetIndexer mAlphabetIndexer;

    public CustomerAdapter(Context context, Cursor cursor) {
        super(context, cursor);

        mAlphabetIndexer = new AlphabetIndexer(cursor,
                cursor.getColumnIndex(DatabaseHelper.CUST_NAME), " ABCDEFGHIJKLMNOPQRTSUVWXYZ");
        mAlphabetIndexer.setCursor(cursor);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView custName = (TextView)view.findViewById(R.id.custName);
        custName.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.CUST_NAME)));

        TextView custAddress = (TextView)view.findViewById(R.id.custAddress);
        custAddress.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.CUST_ADDRESS)));

        TextView custCode = (TextView)view.findViewById(R.id.custCode);
        custCode.setText("Code:"+cursor.getString(cursor.getColumnIndex(DatabaseHelper.CUST_CODE)));

        TextView discPercent = (TextView)view.findViewById(R.id.discPercent);
        discPercent.setText("Discount:"+cursor.getString(cursor.getColumnIndex(DatabaseHelper.DISC_PERCENT))+"%");
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.customer, parent, false);
        return view;
    }

    @Override
    public int getPositionForSection(int section) {
        // TODO Auto-generated method stub
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
    }

}
