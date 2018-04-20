package self.excercere;

import android.content.Intent;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;

public class InitialScreenActivity extends AppCompatActivity {

    private GestureDetectorCompat gestureObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial_screen);

        gestureObject = new GestureDetectorCompat(this, new LearnGesture());
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

                Intent intent = new Intent (InitialScreenActivity.this, RegistrationActivity.class);
                finish();
                startActivity(intent);

            //Right <- Left
            } else {

                if (event2.getX() < event1.getX()) {

                    Intent intent = new Intent (InitialScreenActivity.this, RegistrationActivity.class);
                    finish();
                    startActivity(intent);

                }
            }
            return true;
        }
    }
}
