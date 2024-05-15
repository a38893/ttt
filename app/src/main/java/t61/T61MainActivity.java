package t61;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ttt.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.Buffer;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class T61MainActivity extends AppCompatActivity {
    private ListView listView;
    private ProductAdapter adapter;
    private List<Product> productList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_t61_main);
        listView = findViewById(R.id.t61listview);
        productList= new ArrayList<>();
        adapter = new ProductAdapter( this, productList);
        listView.setAdapter(adapter);
        new FetchProductsTask().execute();
    }
    private class FetchProductsTask extends AsyncTask<Void,Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            StringBuilder response = new StringBuilder();
            try {
                URL url = new URL("https://hungnttg.github.io/shopgiay.json");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                BufferedReader reader =     new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line = "";
                while ((line =reader.readLine())!= null) {
                    response.append(line);
                }
                reader.close();
            }
            catch (MalformedURLException e){
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            return response.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            if (s!=null && !s.isEmpty()){
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray productArry = jsonObject.getJSONArray("products");
                    for (int i =0; i< productArry.length(); i++){
                        JSONObject productObject = productArry.getJSONObject(i);
                        String styleId= productObject.getString("styleid");
                        String brand= productObject.getString("brands_filter_facet");
                        String price= productObject.getString("price");
                        String additionalInfo= productObject.getString("product_additional_info");
                        String searchImage= productObject.getString("search_image");
                        Product product = new Product(styleId, brand, price, additionalInfo, searchImage);
                        productList.add(product);

                    }
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                }
            else {
                Toast.makeText(T61MainActivity.this, "Loi doc du lieu",Toast.LENGTH_LONG).show();
            }
                    }
    }

}