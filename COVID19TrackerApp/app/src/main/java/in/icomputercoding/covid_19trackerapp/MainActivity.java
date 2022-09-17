package in.icomputercoding.covid_19trackerapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.Adapter;
import android.widget.ListAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import in.icomputercoding.covid_19trackerapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    List<Model> models = new ArrayList<>();
    Model model;
    private RequestQueue mQueue;
    private Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mQueue = Volley.newRequestQueue(this);
        fetch();
    }

    private void fetch() {
        String JSON_URL = "https://data.covid19india.org/state_district_wise.json";

        StringRequest request = new StringRequest(Request.Method.GET, JSON_URL, response -> {
            try {

                JSONObject jsonObject = new JSONObject(response);
                Iterator<String> keys = jsonObject.keys();
                while (keys.hasNext()) {
                    String key = keys.next();
                    JSONObject object1 = jsonObject.getJSONObject(key);
                    JSONObject object2 = object1.getJSONObject("districtData");
                    Iterator<String> superkeys = object2.keys();
                    while (superkeys.hasNext()) {
                        key = superkeys.next();
                        JSONObject object3 = object2.getJSONObject(key);
                        JSONObject object4 = object3.getJSONObject("delta");
                        String active = object3.getString("active");
                        String confirmed = object3.getString("confirmed");
                        String deceased = object3.getString("deceased");
                        String recovered = object3.getString("recovered");

                        String confirmedDelta = object4.getString("confirmed");
                        String deceasedDelta = object4.getString("deceased");
                        String recoveredDelta = object4.getString("recovered");
                        model = new Model(key, active, confirmed, recovered, deceased, confirmedDelta, deceasedDelta, recoveredDelta);
                        models.add(model);
                    }
                }
                adapter = new CityAdapter(MainActivity.this, models);
                binding.listCity.setAdapter((ListAdapter) adapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        },
                error -> Log.d("tag", "onErrorResponse: " + error.getMessage())
        );
        mQueue.add(request);
    }
}
























