package self.excercere;

import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import io.netopen.hotbitmapgg.library.view.RingProgressBar;

public class WorkoutExecActivity extends AppCompatActivity {

    private FloatingActionButton play;
    private FloatingActionButton stop;
    private RingProgressBar rProg;
    private int progress;
    private Handler handle = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {

                if (progress < 100) {

                    progress++;
                    rProg.setProgress(progress);
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_gen);

        play = findViewById(R.id.playButton);
        stop = findViewById(R.id.stopButton);
        rProg = findViewById(R.id.ringProg);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                countdown();

            }
        });
    }

    public void countdown() {

        rProg.setOnProgressListener(new RingProgressBar.OnProgressListener(){

            @Override
            public  void progressToComplete() {

                Toast.makeText(WorkoutExecActivity.this, "Complete", Toast.LENGTH_SHORT).show();
            }
        });

        new Thread(new Runnable() {

            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {

                    try {
                        Thread.sleep(100);
                        handle.sendEmptyMessage(0);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }
}
