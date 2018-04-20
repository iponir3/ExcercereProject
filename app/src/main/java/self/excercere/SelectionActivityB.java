package self.excercere;

import android.content.Intent;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.ArrayList;

public class SelectionActivityB extends AppCompatActivity {

    ArrayList<String> bdParts = new ArrayList<>();
    private GestureDetectorCompat gestureObject;
    RadioButton Chest;
    RadioButton Back;
    RadioButton Triceps;
    RadioButton Biceps;
    RadioButton Shoulders;
    RadioButton Forearms;
    RadioButton Legs;
    RadioButton Calves;
    RadioButton Abs;

    Button clr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);
        gestureObject = new GestureDetectorCompat(this, new SelectionActivityB.LearnGesture());
        Chest = (RadioButton) findViewById(R.id.ch);
        Back = (RadioButton) findViewById(R.id.bk);
        Triceps = (RadioButton) findViewById(R.id.tri);
        Biceps = (RadioButton) findViewById(R.id.bi);
        Shoulders = (RadioButton) findViewById(R.id.sh);
        Forearms = (RadioButton) findViewById(R.id.fore);
        Legs = (RadioButton) findViewById(R.id.leg);
        Calves = (RadioButton) findViewById(R.id.calv);
        Abs = (RadioButton) findViewById(R.id.ab);

        clr = (Button) findViewById(R.id.clear);
        //bdParts.clear();
        clr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Chest.setSelected(false);
                Chest.setChecked(false);
                Back.setSelected(false);
                Back.setChecked(false);
                Triceps.setSelected(false);
                Triceps.setChecked(false);
                Biceps.setSelected(false);
                Biceps.setChecked(false);
                Shoulders.setSelected(false);
                Shoulders.setChecked(false);
                Forearms.setSelected(false);
                Forearms.setChecked(false);
                Legs.setSelected(false);
                Legs.setChecked(false);
                Calves.setSelected(false);
                Calves.setChecked(false);
                Abs.setSelected(false);
                Abs.setChecked(false);

                bdParts.clear();

            }
        });

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        this.gestureObject.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

class LearnGesture extends GestureDetector.SimpleOnGestureListener {

    @Override
    public boolean onFling(MotionEvent event1, MotionEvent event2, float veloicityX,
                           float veloicityY) {

        //Left -> Right
        if (event2.getX() > event1.getX()) {

            Intent intent = new Intent (SelectionActivityB.this, SelectionActivityB.class);
            finish();
            startActivity(intent);

            //Right <- Left

        } else {
            bdParts.clear();
            if (Chest.isChecked()) {

                bdParts.add("ch");
            }
            if (Back.isChecked()) {

                bdParts.add("bk");
            }
            if (Triceps.isChecked()) {

                bdParts.add("tr");
            }
            if (Biceps.isChecked()) {

                bdParts.add("bi");
            }
            if (Shoulders.isChecked()) {

                bdParts.add("sh");
            }
            if (Forearms.isChecked()) {

                bdParts.add("fore");
            }
            if (Legs.isChecked()) {

                bdParts.add("lg");
            }
            if (Calves.isChecked()) {

                bdParts.add("cv");
            }
            if (Abs.isChecked()) {

                bdParts.add("ab");
            }

            Intent intent = new Intent(SelectionActivityB.this, SelectionActivityC.class);
            intent.putExtra("PARTS", bdParts);
            finish();
            startActivity(intent);
        }
        return true;
    }
}
}
