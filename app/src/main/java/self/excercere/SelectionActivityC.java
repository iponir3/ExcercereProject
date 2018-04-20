package self.excercere;

import android.content.Intent;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SelectionActivityC extends AppCompatActivity {

    private TextView Num;
    private TextView SetText;
    private ImageButton Increment;
    private ImageButton Decrement;
    private Button Next;
    private ArrayList<String> transBD = new ArrayList<>();
    private ArrayList<Integer> transBDCnt = new ArrayList<>();
    private static int Count;
    private static int refInt = 0;
    private GestureDetectorCompat gestureObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection_c);
        Count = 0;
        transBD = getIntent().getExtras().getStringArrayList("PARTS");

        Num = (TextView) findViewById(R.id.num);
        Increment = (ImageButton) findViewById(R.id.up);
        Decrement = (ImageButton) findViewById(R.id.down);
        SetText = (TextView) findViewById(R.id.setText);
        Next = (Button) findViewById(R.id.next);
        if (transBD.size() == 1) {
            Next.setVisibility(View.INVISIBLE);
        }
        updateText();

        Increment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Count == 5) {

                    Count = 5;
                } else {
                    Count++;
                    Num.setText(String.valueOf(Count));
                }


            }
        });

        Decrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Count == 0 ) {

                    Count = 0;
                } else {

                    Count--;
                    Num.setText(String.valueOf(Count));
                }
            }
        });

        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (refInt == transBD.size() - 1) {

                    Next.setVisibility(View.INVISIBLE);
                    transBDCnt.add(Count);
                    Count = 0;
                    Num.setText(String.valueOf(0));

                } else {
                    transBDCnt.add(Count);
                    refInt++;
                    updateText();
                    Count = 0;
                    Num.setText(String.valueOf(0));


                }
            }
        });

        gestureObject = new GestureDetectorCompat(this, new SelectionActivityC.LearnGesture());
    }

    private void updateText() {
        String refStr = transBD.get(refInt);
        switch(refStr) {
            case "ch" :
                SetText.setText("Chest?");
                break;
            case "bk" :
                SetText.setText("Back?");
                break;
            case "sh" :
                SetText.setText("Shoulders?");
                break;
            case "fore" :
                SetText.setText("Forearms?");
                break;
            case "bi" :
                SetText.setText("Biceps?");
                break;
            case "tr" :
                SetText.setText("Triceps?");
                break;
            case "lg" :
                SetText.setText("Legs?");
                break;
            case "cv" :
                SetText.setText("Calves?");
                break;
            case "ab" :
                SetText.setText("Abs?");
                break;
        }
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

            if (event2.getX() > event1.getX()) {

                Intent intent = new Intent(SelectionActivityC.this, SelectionActivityC.class);
                finish();
                startActivity(intent);

            } else if (refInt == transBD.size() - 1){

                if (transBD.size() == 1) {
                    transBDCnt.add(Count);
                }
                /*
                for (int i = 0; i < transBD.size(); i++) {
                    Toast.makeText(getApplicationContext(), transBDCnt.get(i) + "    " +
                            transBD.get(i),
                            Toast.LENGTH_LONG).show();
                }
                */
                Intent intent = new Intent(SelectionActivityC.this, SelectRandActivity.class);
                intent.putExtra("PARTSB", transBD);
                intent.putExtra("CNT", Count);
                intent.putExtra("PARTSC", transBDCnt);
                finish();
                startActivity(intent);

            }

            return true;
        }
    }
}
