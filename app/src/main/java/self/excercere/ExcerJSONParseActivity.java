package self.excercere;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class ExcerJSONParseActivity extends Activity {

    /*
        JSON layout = name | pid | B/N | incr | rig
     */
    private ArrayList<String> numList = new ArrayList<>();
    private ArrayList<String> exerList = new ArrayList<>();
    private ArrayList<String> rigList = new ArrayList<>();
    private String[] exerArr;
    private Button saveData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excer_jsonparse);
        //get_json();
        get_exer();
        saveData = (Button) findViewById(R.id.switchButton);
        saveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ExcerJSONParseActivity.this, TestTransferActivity.class);
                intent.putExtra("EARR", exerArr);
                intent.putExtra("ELIST", exerList);
                startActivity(intent);
            }
        });
    }

    public void get_exer() {

        String json;
        try {

            InputStream inputStream = getAssets().open("execSampleJSON.json");
            int size = inputStream.available();
            byte[] arr = new byte[size];
            inputStream.read(arr);
            inputStream.close();

            json = new String(arr, "UTF-8");
            JSONArray jsonArray = new JSONArray(json);

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jObj = jsonArray.getJSONObject(i);
                if (jObj.getString("type").equals("weight")) {
                    JSONArray exArr = jObj.getJSONArray("exercises");
                    for (int j = 0; j < exArr.length(); j++) {
                        JSONObject exObj = exArr.getJSONObject(j);
                        if (exObj.getString("pid").equals("ch")) {
                            exerList.add(exObj.getString("name"));
                            rigList.add(exObj.getString("rig"));
                        }
                    }
                }
            }
            Toast.makeText(ExcerJSONParseActivity.this, rigList.toString(), Toast.LENGTH_SHORT).show();

            exerArr = exerList.toArray(new String[exerList.size()]);
            ListAdapter adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, exerArr);
            ListView list = (ListView)findViewById(R.id.view);
            list.setAdapter(adapter);


        } catch (IOException e) {

            e.printStackTrace();

        } catch (JSONException f) {

            Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_LONG).show();
            f.printStackTrace();
        }
    }

    public void get_json() {

        String json;
        try {

            InputStream inputStream = getAssets().open("excerJSON.json");
            int size = inputStream.available();
            byte[] arr = new byte[size];
            inputStream.read(arr);
            inputStream.close();

            json = new String(arr, "UTF-8");
            JSONArray jsonArray = new JSONArray(json);

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jObj = jsonArray.getJSONObject(i);
                if (jObj.getString("city").equals("isb")) {

                    numList.add(jObj.getString("number"));
                }
            }

            Toast.makeText(ExcerJSONParseActivity.this, numList.toString(), Toast.LENGTH_SHORT).show();
        } catch (IOException e) {

            e.printStackTrace();

        } catch (JSONException f) {

            Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_LONG).show();
            f.printStackTrace();
        }
    }
}
