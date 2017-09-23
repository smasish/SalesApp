package sindabad.zerogvt.salesapp.model;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by LENOVO on 9/11/2017.
 */

public class Product {

    public static final String TAG = sindabad.zerogvt.salesapp.model.Product.class.getSimpleName();

    public String title;
    public String price;
    public String imageUrl;
    public String instructionUrl;
    public int quantity;
    public byte[] product_img;

    public static ArrayList<Product> getRecipesFromFile(String filename, Context context){
        final ArrayList<Product> recipeList = new ArrayList<>();

        try {
            // Load data
            String jsonString = loadJsonFromAsset("product.json", context);
            JSONObject json = new JSONObject(jsonString);
            JSONArray recipes = json.getJSONArray("recipes");

            // Get Recipe objects from data
            for(int i = 0; i < recipes.length(); i++){
                Product recipe = new Product();

                recipe.title = recipes.getJSONObject(i).getString("title");
                recipe.price = recipes.getJSONObject(i).getString("price");
                recipe.imageUrl = recipes.getJSONObject(i).getString("image");
                recipe.instructionUrl = recipes.getJSONObject(i).getString("url");
                recipe.quantity = Integer.parseInt(recipes.getJSONObject(i).getString("quantity"));

                recipeList.add(recipe);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return recipeList;
    }

    private static String loadJsonFromAsset(String filename, Context context) {
        String json = null;

        try {
            InputStream is = context.getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        }
        catch (java.io.IOException ex) {
            ex.printStackTrace();
            return null;
        }

        return json;
    }


    //====================================
    private long id;
    private String comment;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    // Will be used by the ArrayAdapter in the ListView
    @Override
    public String toString() {
        return comment;
    }

}
