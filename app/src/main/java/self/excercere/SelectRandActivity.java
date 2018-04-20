package self.excercere;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class SelectRandActivity extends AppCompatActivity {


    private static ArrayList<String> bd = new ArrayList<>();
    private static ArrayList<Integer> bdCnt = new ArrayList<>();
    private static ArrayList<String> finList = new ArrayList<>();
    private static int ref = 0;
    private static int indCnt = 0;
    private static int totalTime = 0;
    private ImageButton UpSet;
    private ImageButton DownSet;
    private ImageButton UpReps;
    private ImageButton DownReps;
    private ImageButton UpRest;
    private ImageButton DownRest;
    private TextView Sets;
    private TextView Total;
    private static int tmSets = 1;
    private TextView Reps;
    private static int tmReps = 8;
    private TextView Rest;
    private static int tmRest = 30;
    private Button Generate;
    private Button Start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_rand);
        bd = getIntent().getExtras().getStringArrayList("PARTSB");
        bdCnt = getIntent().getExtras().getIntegerArrayList("PARTSC");
        //cnt = getIntent().getExtras().getInt("CNT");
        UpSet = (ImageButton) findViewById(R.id.upSet);
        DownSet = (ImageButton) findViewById(R.id.downSet);
        UpReps = (ImageButton) findViewById(R.id.upReps);
        DownReps = (ImageButton) findViewById(R.id.downReps);
        Sets = (TextView) findViewById(R.id.sets);
        Reps = (TextView) findViewById(R.id.reps);
        Start = (Button) findViewById(R.id.start);
        Generate = (Button) findViewById(R.id.gen);
        Start.setVisibility(View.INVISIBLE);


        finList = chooseWorkout();

        Generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calcTime();
                //Toast.makeText(SelectRandActivity.this, "" + finList.size(), Toast.LENGTH_SHORT).show();
                Start.setVisibility(View.VISIBLE);
            }
        });

        UpReps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (tmReps == 14) {

                    tmReps = 14;
                } else {
                    tmReps += 2;
                    Reps.setText(String.valueOf(tmReps));

                }
            }
        });

        DownReps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (tmReps == 8) {

                    tmReps = 8;
                } else {
                    tmReps -= 2;
                    Reps.setText(String.valueOf(tmReps));

                }
            }
        });


        UpSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (tmSets == 5) {

                    tmSets = 5;
                } else {
                    tmSets++;
                    Sets.setText(String.valueOf(tmSets));

                }
            }
        });

        DownSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (tmSets == 1) {

                    tmSets = 1;
                } else {
                    tmSets--;
                    Sets.setText(String.valueOf(tmSets));


                }
            }
        });

        Start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(SelectRandActivity.this, GenWorkoutActivity.class);
                intent.putExtra("LIST", finList);
                intent.putExtra("REPS", tmReps);
                intent.putExtra("SETS", tmSets);
                finish();
                startActivity(intent);

            }
        });
    }

    private void calcTime() {

        int retTime = 0;
        try {
            //Toast.makeText(SelectRandActivity.this,"" + finList.size(), Toast.LENGTH_SHORT).show();
            for (int i = 1; i < finList.size(); i++) {
                JSONObject obj = new JSONObject(finList.get(i));
                if (obj.getString("rig").equals("1")) {

                    retTime += 20000 * tmReps;
                    //Toast.makeText(SelectRandActivity.this, 8 * tmReps + " ONE " + retTime, Toast.LENGTH_SHORT).show();

                }
                if (obj.getString("rig").equals("2")) {

                    retTime += 15000 * tmReps;
                    //Toast.makeText(SelectRandActivity.this, 4 * tmReps + " TW0 " + retTime, Toast.LENGTH_SHORT).show();

                }
                if (obj.getString("rig").equals("3")) {

                    retTime += 10000 * tmReps;
                    //Toast.makeText(SelectRandActivity.this, 2 * tmReps + " THREE " + retTime , Toast.LENGTH_SHORT).show();

                }
            }
        } catch (JSONException e) {


        }
        retTime *= tmSets;
        //Toast.makeText(SelectRandActivity.this, (retTime * tmSets) + " HERE", Toast.LENGTH_SHORT).show();
        retTime += ((finList.size() - 1) * tmRest);
        //Toast.makeText(SelectRandActivity.this, ((finList.size() - 1) * tmRest) + "THERE", Toast.LENGTH_SHORT).show();
        totalTime = retTime;
        Total = (TextView) findViewById(R.id.total);
        long minRef = totalTime / (60 * 1000) % 60;
        long secRef = totalTime / 1000 % 60;
        Total.setText(String.valueOf(minRef + " Minutes : " + secRef + " Seconds"));
        //Toast.makeText(SelectRandActivity.this, "" + retTime, Toast.LENGTH_SHORT).show();

    }
    private ArrayList<String> chooseWorkout() {

        ArrayList<String> arlExer = new ArrayList<>();
        ArrayList<String> retArlExer = new ArrayList<>();
        Set<String> setExer = new HashSet<>();
        int setCnt = 0;
        /*
        while(setExer.size() != cnt) {
            Random rand = new Random();
            int randInt = rand.nextInt(bd.size());
            String refPID = bd.get(randInt);
            String json;
        */
            try {

                InputStream inputStream = getAssets().open("exerJSONImp.json");
                int size = inputStream.available();
                byte[] arr = new byte[size];
                inputStream.read(arr);
                inputStream.close();

                String json = new String(arr, "UTF-8");
                JSONObject jsonObj = new JSONObject(json);
                JSONArray jsonExer = jsonObj.getJSONArray("exercises");
                while (indCnt !=  bdCnt.size()) {
                    String refId = bd.get(ref);
                    int incrTrack = 0;
                    for (int i = 0; i < jsonExer.length(); i++) {
                        JSONObject typeObj = jsonExer.getJSONObject(i);
                        if (typeObj.has(refId)) {
                            JSONArray pidArr = typeObj.getJSONArray(refId);
                            JSONObject refAmount = pidArr.getJSONObject(0);
                            int refCnt = refAmount.getInt("incrCnt");
                            while (incrTrack != bdCnt.get(ref)) {
                                Random rand = new Random();
                                int randEx = rand.nextInt(refCnt);
                                String posAdd = pidArr.getString(randEx);
                                if (!(posAdd.contains("incrCnt"))) {
                                    int setRef = setExer.size();
                                    setExer.add(posAdd);
                                    if (setExer.size() > setRef) {
                                        incrTrack++;
                                    }
                                }
                            }
                        }
                    }
                    indCnt++;
                    ref++;
                }
                    /*
                    for (int i = 0; i < jsonExer.length(); i++) {
                        JSONObject typeObj = jsonExer.getJSONObject(i);
                        if (typeObj.has(refPID)) {
                            JSONArray pidArr = typeObj.getJSONArray(refPID);
                            JSONObject refAmount = pidArr.getJSONObject(0);
                            int refCnt = refAmount.getInt("incrCnt");
                            Random randPID = new Random();
                            int finRand = randPID.nextInt(refCnt);
                            String posAdd = pidArr.getString(finRand);
                            if (!(posAdd.contains("incrCnt"))) {
                                setExer.add(posAdd);
                            }
                        }
                    }
                    */



            } catch (IOException e) {

                e.printStackTrace();

            } catch (JSONException f) {

                Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_LONG).show();
                f.printStackTrace();
            }



        int arlCnt = 0;
        for (String s : setExer) {

                try {

                    JSONObject addArl = new JSONObject(s);
                    String addStr = addArl.getString("name");
                    arlExer.add(addStr);
                    retArlExer.add(s);
                } catch (JSONException e) {

                }
        }
        //Toast.makeText(SelectRandActivity.this, arlExer.size() + "", Toast.LENGTH_SHORT).show();
        /*
        for (int i = 0; i < arlExer.size(); i++) {

            ity.this, i + arlExer.get(i) + "", Toast.LENGTH_SHORT).show();
        }
        */

        String[] exerArr = arlExer.toArray(new String[arlExer.size()]);
        ListAdapter adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, exerArr);
        ListView list = (ListView) findViewById(R.id.exerView);
        list.setAdapter(adapter);
        //Toast.makeText(SelectRandActivity.this, arlExer.size() + "", Toast.LENGTH_SHORT).show();
        return retArlExer;

    }
}