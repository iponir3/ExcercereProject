package self.excercere;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainProgActivity extends AppCompatActivity {

    Button Gen;
    Button sO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_prog);
        Gen = (Button) findViewById(R.id.gen);
        sO = (Button) findViewById(R.id.signOut);

        Gen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainProgActivity.this, SelectionActivityB.class);
                finish();
                startActivity(intent);
            }
        });

        sO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainProgActivity.this, InitialScreenActivity.class);
                finish();
                startActivity(intent);
            }
        });
    }
}
