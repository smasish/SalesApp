package sindabad.zerogvt.salesapp;

import java.util.List;
import java.util.Random;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import sindabad.zerogvt.salesapp.model.Product;

public class CustomerDBActivity extends ListActivity {
	private ProductsDataSource datasource;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// dhgdh
		datasource = new ProductsDataSource(this);
		datasource.open();

		List<Product> values = datasource.getAllComments();

		// Use the SimpleCursorAdapter to show the
		// elements in a ListView
		ArrayAdapter<Product> adapter = new ArrayAdapter<Product>(this,
				android.R.layout.simple_list_item_1, values);
		setListAdapter(adapter);
	}

	// Will be called via the onClick attribute
	// of the buttons in main.xml
	public void onClick(View view) {
		@SuppressWarnings("unchecked")
		ArrayAdapter<Product> adapter = (ArrayAdapter<Product>) getListAdapter();
		Product product = null;
		switch (view.getId()) {
		case R.id.add:
			String[] comments = new String[] { "Cool", "Very nice", "Hate it" };
			int nextInt = new Random().nextInt(3);
			// Save the new product to the database
			product = datasource.createComment(comments[nextInt]);
			adapter.add(product);
			break;
		case R.id.delete:
			if (getListAdapter().getCount() > 0) {
				product = (Product) getListAdapter().getItem(0);
				datasource.deleteComment(product);
				adapter.remove(product);
			}
			break;
		}
		adapter.notifyDataSetChanged();
	}

	@Override
	protected void onResume() {
		datasource.open();
		super.onResume();
	}

	@Override
	protected void onPause() {
		datasource.close();
		super.onPause();
	}
}