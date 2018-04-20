package self.excercere;

import android.content.Intent;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.ArrayList;

public class SelectionActivity extends AppCompatActivity {

    ArrayList <String> bd = new ArrayList<>();
    private GestureDetectorCompat gestureObject;
    RadioButton Rand;
    RadioButton Cust;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection_b);
        gestureObject = new GestureDetectorCompat(this, new SelectionActivity.LearnGesture());
        Rand = (RadioButton) findViewById(R.id.rand);
        Cust = (RadioButton) findViewById(R.id.cust);

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

                Toast.makeText(SelectionActivity.this, "LEFT <- RIGHT", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent (SelectionActivity.this, SelectionActivity.class);
                finish();
                startActivity(intent);
                //Right <- Left
            } else {

                if (event2.getX() < event1.getX()) {

                    if (Rand.isChecked()) {

                        Intent intent = new Intent (SelectionActivity.this, SelectionActivityB.class);
                        finish();
                        startActivity(intent);
                    }
                    if (Cust.isChecked()) {

                        Intent intent = new Intent (SelectionActivity.this, SelectCustActivity.class);
                        finish();
                        startActivity(intent);

                    }

                }

            }
            return true;
        }
    }
}




