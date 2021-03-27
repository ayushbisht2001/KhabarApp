 package com.example.khabar;

 import android.os.Bundle;
 import android.widget.Toast;

 import androidx.appcompat.app.AppCompatActivity;
 import androidx.recyclerview.widget.LinearLayoutManager;
 import androidx.recyclerview.widget.RecyclerView;

 import com.android.volley.AuthFailureError;
 import com.android.volley.Request;
 import com.android.volley.RequestQueue;
 import com.android.volley.Response;
 import com.android.volley.VolleyError;
 import com.android.volley.toolbox.StringRequest;
 import com.android.volley.toolbox.Volley;
 import com.example.khabar.topheadlines.TopHeadlinesAdapter;
 import com.example.khabar.topheadlines.TopHeadlinesModel;

 import org.json.JSONArray;
 import org.json.JSONException;
 import org.json.JSONObject;

 import java.util.ArrayList;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;

 public class MainActivity extends AppCompatActivity {

     private RecyclerView topHeadlinesRecyclerView;
     private TopHeadlinesAdapter topHeadlinesAdapter;
     private List<TopHeadlinesModel> topHeadlinesModelList = new ArrayList<>();
     private  String apiKEY = "c7c73449dfdc4de695e288070a5efd22";
     private String topheadlinesURL = "https://newsapi.org/v2/everything?q=Apple&from=2021-03-26&sortBy=popularity&apiKey="+ apiKEY;
     private int pageNumber = 1, per_page=20;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        topHeadlinesRecyclerView = findViewById(R.id.top_headlines_recyclerview);

        topHeadlinesModelList.add(new TopHeadlinesModel( "Hello",  "world",  "I am here"));

        topHeadlinesAdapter = new TopHeadlinesAdapter(topHeadlinesModelList,   this);
        LinearLayoutManager layoutManager = new LinearLayoutManager( this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        topHeadlinesRecyclerView.setLayoutManager(layoutManager);
        topHeadlinesRecyclerView.setAdapter(topHeadlinesAdapter);
        topHeadlinesAdapter.notifyDataSetChanged();


    }

    private void fetchData(){

        StringRequest request = new StringRequest(Request.Method.GET, topheadlinesURL + "&page" + pageNumber + "&per_page" + per_page, new Response.Listener<String>(){

            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);

                    JSONArray jsonArray = jsonObject.getJSONArray("articles");
                    int length = jsonArray.length();
                    for(int i=0;i<length;i++){

                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String source = "adf";
                        String title = jsonObject1.getString("title");
                        String desp = jsonObject1.getString("description");
                        topHeadlinesModelList.add(new TopHeadlinesModel(title, desp, source));
                     }
                    topHeadlinesAdapter.notifyDataSetChanged();
                    pageNumber++;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "news not found", Toast.LENGTH_SHORT).show();
            }
        } ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("key", apiKEY);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }


 }