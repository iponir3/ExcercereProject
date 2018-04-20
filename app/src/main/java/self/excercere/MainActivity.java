package self.excercere;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button reg;
    private Button gen;
    private Button exec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        reg = findViewById(R.id.register);
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                regActivity();

            }
        });
        /*
        gen = findViewById(R.id.generate);
            gen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                genActivity();

            }
        });

        exec = findViewById(R.id.execute);
        exec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                execActivity();

            }
        });
        */

}
    public void  regActivity() {

        Intent regIntent = new Intent(this, RegistrationActivity.class);
        startActivity(regIntent);

    }

    public void genActivity() {

        Intent regIntent = new Intent(this, SelectionActivity.class);
        startActivity(regIntent);
    }

    public void execActivity() {

        Intent regIntent = new Intent(this, GenWorkoutActivity.class);
        startActivity(regIntent);
    }

}

