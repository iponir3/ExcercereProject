package self.excercere;

import android.app.Activity;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;

public class GenWorkoutActivity extends Activity {
    private static final long START_TIME_IN_MILLIS = 6000;
    private ArrayList<String> jsList;
    private int totScore;
    private int usrScore;
    private static int Sets;
    private static int Reps;
    private boolean tst = true;

    private TextView mTextViewCountDown;
    private TextView SetsLeft;
    private TextView Exercise;
    private Button mButtonStartPause;
    private Button Complete;

    private CountDownTimer mCountDownTimer;
    private CountDownTimer getmCountDownTimerRest;

    private boolean mTimerRunning;

    private long mTimeLeftInMillis;
    private long mEndTime;

    private static String[] workName;
    private static String[] workRig;

    private static HashMap<String, String> hMap = new HashMap<>();
    private static Map<String, String> sortMap = new HashMap();

    private static int ref;
    private String exer;
    private String rig;
    private int setRef;


    private int totalSet;

    private ArrayList<String> recExer = new ArrayList<>();
    private ArrayList<String> recRig = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        jsList =  getIntent().getExtras().getStringArrayList("LIST");
        Sets = getIntent().getExtras().getInt("SETS");
        Reps = getIntent().getExtras().getInt("REPS");

        /*
        for (int i = 0; i < jsList.size(); i++) {
            Toast.makeText(GenWorkoutActivity.this,"" + jsList.get(i), Toast.LENGTH_SHORT).show();
        }

        Toast.makeText(GenWorkoutActivity.this, "" + Sets, Toast.LENGTH_SHORT).show();
        Toast.makeText(GenWorkoutActivity.this, "" + Reps, Toast.LENGTH_SHORT).show();
        */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gen_workout);

        mTextViewCountDown = findViewById(R.id.text_view_countdown);
        mButtonStartPause = findViewById(R.id.button_start_pause);
        Complete = (Button) findViewById(R.id.comp);
        SetsLeft = (TextView) findViewById(R.id.set);
        Exercise = (TextView) findViewById(R.id.exer);
        Complete.setVisibility(View.INVISIBLE);

        workName = new String[jsList.size()];
        workRig = new String[jsList.size()];

        for (int i = 0; i < workName.length; i++) {
            try {
                JSONObject jObj = new JSONObject(jsList.get(i));
                workName[i] = jObj.getString("name");
                workRig[i] = jObj.getString("rig");

            } catch (JSONException e) {

                e.printStackTrace();
            }

        }
        for (int i = 0; i < workName.length; i++) {

            hMap.put(workName[i], workRig[i]);
        }

        sortMap = sortByValues(hMap);
        Set eSet = sortMap.entrySet();
        Iterator iter = eSet.iterator();
        int index = 0;
        while (iter.hasNext() && index < workName.length) {

            Map.Entry mE = (Map.Entry) iter.next();
            //Toast.makeText(GenWorkoutActivity.this, mE.getKey() + " " + mE.getValue(), Toast.LENGTH_SHORT).show();
            workName[index] = (String) mE.getKey();
            workRig[index] = (String) mE.getValue();
            index++;
        }

        for (int i = 0; i < workName.length; i++) {
            //Toast.makeText(GenWorkoutActivity.this, workName[i] + " HISONE " + workRig[i], Toast.LENGTH_SHORT).show();
        }

        totalSet = Sets * workName.length;
        for (int i = 0; i < workName.length; i++) {
            for (int j = 0; j < Sets; j++) {
                recExer.add(workName[i]);
            }
        }
        for (int i = 0; i < workRig.length; i++) {
            for (int j = 0; j < Sets; j++) {
                recRig.add(workRig[i]);
            }
        }

        //Toast.makeText(GenWorkoutActivity.this, totalSet + "", Toast.LENGTH_SHORT).show();
        /*
        for (int i = 0; i < recExer.size(); i++) {

            Toast.makeText(GenWorkoutActivity.this, recExer.get(i) + " " + recRig.get(i), Toast.LENGTH_SHORT).show();

        }
        */


        mButtonStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mButtonStartPause.setVisibility(View.INVISIBLE);
                Complete.setVisibility(View.VISIBLE);
                //startExer();

                ref = 0;
                startExer();

            }
        });

        Complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCountDownTimer.cancel();
                export();
            }
        });
    }

    public void export() {
        Intent intent = new Intent(GenWorkoutActivity.this, ExportActivity.class);
        intent.putExtra("S", Sets);
        intent.putExtra("R", Reps);
        ArrayList<String> lsW = new ArrayList<>();
        List<String> lsR = new ArrayList<>();

        for (int i = 0; i < workName.length; i++) {
            lsW.add(workName[i]);
            lsR.add(workRig[i]);
        }

        intent.putExtra("aW", lsW);
        //intent.putExtra("aR", workRig);
        //Toast.makeText(GenWorkoutActivity.this, "" + work, Toast.LENGTH_SHORT).show();

        finish();
        startActivity(intent);

    }
    boolean rest = true;
    int iter = 1;
    private void startExer() {

        //Toast.makeText(GenWorkoutActivity.this, iters++ +"", Toast.LENGTH_SHORT).show();
        if (iter % 2 == 0) {
            mTimeLeftInMillis = 30000;
            updateCountDownText();
        } else {
            mTimeLeftInMillis = retTime(recRig.get(ref)) * Reps;
            updateCountDownText();
        }
        updateCountDownText();
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {


                    @Override
                    public void onTick(long millisUntilFinished) {
                        //Toast.makeText(GenWorkoutActivity.this, mTimeLeftInMillis+"", Toast.LENGTH_SHORT).show();
                            updateCountDownText();
                            if (iter % 2 == 0) {

                                Exercise.setText("REST");
                                SetsLeft.setText("");
                                rest = false;
                            } else {

                                Exercise.setText(recExer.get(ref));
                                SetsLeft.setText((ref + 1) + " out of " + totalSet);
                            }

                            mTimeLeftInMillis = millisUntilFinished;
                            updateCountDownText();
                    }

                    @Override
                    public void onFinish() {
                        updateCountDownText();
                        iter++;
                        if (iter % 2 == 0) {
                            Exercise.setText("REST");
                            SetsLeft.setText("");
                            //rest = false;
                            startExer();
                        } else if (ref < totalSet - 1 && iter % 2 == 1) {
                            ref++;
                            mTimeLeftInMillis = retTime(recRig.get(ref)) * Reps;
                            rest = true;
                            startExer();
                        }
                        if (iter == (recExer.size() * Sets)) {
                            //Toast.makeText(GenWorkoutActivity.this, workName.length + "  "+ iter, Toast.LENGTH_SHORT).show();
                            //Intent intent = new Intent(GenWorkoutActivity.this, ExportActivity.class);
                            /*
                            intent.putExtra("S", Sets);
                            intent.putExtra("R", Reps);
                            intent.putExtra("aW", workName);
                            intent.putExtra("aR", workRig);
                            */
                            /*
                            Toast.makeText(GenWorkoutActivity.this, "" + Sets, Toast.LENGTH_SHORT).show();
                            Toast.makeText(GenWorkoutActivity.this, "" + Reps, Toast.LENGTH_SHORT).show();
                            Toast.makeText(GenWorkoutActivity.this, "" + workName.length, Toast.LENGTH_SHORT).show();
                            Toast.makeText(GenWorkoutActivity.this, "" + workRig.length, Toast.LENGTH_SHORT).show();
                            //finish();
                            //startActivity(intent);
                            */
                            //export();
                        }
                    }
                }.start();



                //SetsLeft.setText("");
                //Exercise.setText("REST");
                /*
                getmCountDownTimerRest = new CountDownTimer(3000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        mTimeLeftInMillis = millisUntilFinished;
                        updateCountDownText();
                    }

                    @Override
                    public void onFinish() {

                    }
                }.start();
                */


    }
    /*
    private void restPeriod() {

        pauseTimer();

        CountDownTimer delay = new CountDownTimer(6000, 1000) {
            @Override
            public void onTick(long l) {

                Toast.makeText(GenWorkoutActivity.this, "REST", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinish() {

                startTimer();
            }
        }.start();
    }
    */

    private void startTimer() {
        final int iters = 0;
        //mEndTime = System.currentTimeMillis() + mTimeLeftInMillis;

        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {

                String timeLeftFormatted = String.format(Locale.getDefault(), "00:00");
                //restPeriod();
                mTextViewCountDown.setText(timeLeftFormatted);
                //iters++;

                if (iters < workName.length && mTimeLeftInMillis != 0) {
                    resetTimer();
                    Toast.makeText(GenWorkoutActivity.this, workName[iters], Toast.LENGTH_SHORT).show();
                    //restPeriod();
                    startTimer();

                } else {

                    mTimerRunning = false;
                    updateButtons();
                }
            }
        }.start();

        mTimerRunning = true;
        updateButtons();

    }
    private static int retTime(String val) {


        if (val.equals("1")) {

            return 7000;
        }
        if (val.equals("2")) {

            return 6000;
        }
        if (val.equals("3")) {

            return 5000;
        } else {

            return 1000;
        }
    }
    private static HashMap sortByValues(HashMap hm) {
        List list = new LinkedList(hm.entrySet());
        Collections.sort(list, new Comparator() {
            @Override
            public int compare(Object o, Object t1) {
                return ((String) ((Map.Entry) (o)).getValue()).compareTo(
                        (String)(((Map.Entry) t1)).getValue());
            }
        });
        HashMap sorted = new LinkedHashMap();
        for (Iterator it = list.iterator(); it.hasNext();) {
            Map.Entry entry = (Map.Entry) it.next();
            sorted.put(entry.getKey(), entry.getValue());
        }
        return sorted;
    }

    private void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;
        updateButtons();
    }

    private void resetTimer() {
        //mTimeLeftInMillis = START_TIME_IN_MILLIS;
        updateCountDownText();
        updateButtons();
    }

    private void updateCountDownText() {

        if (mTimeLeftInMillis <= (long) 0000) {
            mTextViewCountDown.setText("00:00");
        } else {

            int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
            int seconds = (int) (mTimeLeftInMillis / 1000) % 60;
            String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
            mTextViewCountDown.setText(timeLeftFormatted);
        }

    }

    private void updateButtons() {
        if (mTimerRunning) {
            //mButtonReset.setVisibility(View.INVISIBLE);
            mButtonStartPause.setText("Pause");
        } else {
            mButtonStartPause.setText("Start");

            if (mTimeLeftInMillis < 1000) {
                //mButtonStartPause.setVisibility(View.INVISIBLE);
            } else {
                //mButtonStartPause.setVisibility(View.VISIBLE);
            }
            /*
            if (mTimeLeftInMillis < START_TIME_IN_MILLIS) {
                mButtonReset.setVisibility(View.VISIBLE);
            } else {
                mButtonReset.setVisibility(View.INVISIBLE);
            }
            */
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong("millisLeft", mTimeLeftInMillis);
        outState.putBoolean("timerRunning", mTimerRunning);
        outState.putLong("endTime", mEndTime);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        mTimeLeftInMillis = savedInstanceState.getLong("millisLeft");
        mTimerRunning = savedInstanceState.getBoolean("timerRunning");
        updateCountDownText();
        updateButtons();

        if (mTimerRunning) {
            mEndTime = savedInstanceState.getLong("endTime");
            mTimeLeftInMillis = mEndTime - System.currentTimeMillis();
            startTimer();
        }
    }
}