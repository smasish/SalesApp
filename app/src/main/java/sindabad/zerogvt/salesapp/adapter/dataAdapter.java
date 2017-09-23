package sindabad.zerogvt.salesapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import database.DatabaseHelper;
import sindabad.zerogvt.salesapp.R;
import sindabad.zerogvt.salesapp.model.Contact;


public class dataAdapter extends ArrayAdapter<Contact> {

    Context context;
    ArrayList<Contact> mcontact;
    Activity activity;


    public dataAdapter(Context context, ArrayList<Contact> contact,Activity activity) {
        super(context, R.layout.add_product_row, contact);
        this.context = context;
        this.mcontact = contact;
        this.activity=activity;
    }

    public class Holder {
        TextView nameFV;
        ImageView pic;
        Button btn_delted;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position

        final Contact data = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view

        Holder viewHolder; // view lookup cache stored in tag

        if (convertView == null) {


            viewHolder = new Holder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.add_product_row, parent, false);

            viewHolder.nameFV = (TextView) convertView.findViewById(R.id.txtViewer);
            viewHolder.pic = (ImageView) convertView.findViewById(R.id.imgView);
            viewHolder.btn_delted = (Button) convertView.findViewById(R.id.btn_delted);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (Holder) convertView.getTag();
        }


        viewHolder.nameFV.setText("First Name: " + data.getFName());
        viewHolder.pic.setImageBitmap(convertToBitmap(data.getImage()));

        viewHolder.btn_delted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper    db=new DatabaseHelper(context);
                db.deleteContact(data.getID());
                activity.finish();

            }
        });
        // Return the completed view to render on screen
        return convertView;
    }
    //get bitmap image from byte array

    private Bitmap convertToBitmap(byte[] b) {

        return BitmapFactory.decodeByteArray(b, 0, b.length);

    }

}

