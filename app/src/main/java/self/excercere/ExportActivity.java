package self.excercere;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.ArrayList;

public class ExportActivity extends AppCompatActivity implements Serializable {

    public int Sets;
    public int Reps;
    public ArrayList<String> workList;

    Button Exp;
    Button Hm;
    DatabaseReference dR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export);
        Sets = getIntent().getExtras().getInt("S");
        Reps = getIntent().getExtras().getInt("R");
        workList = getIntent().getExtras().getStringArrayList("aW");

        Exp = (Button) findViewById(R.id.exp);
        Hm = (Button) findViewById(R.id.hm);

        Exp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dR = FirebaseDatabase.getInstance().getReference("Workouts");
                String id = dR.push().getKey();
                ExerObject eO = new ExerObject(workList,Reps, Sets);
                dR.child(id).setValue(eO);
                /*
                for(int i = 0; i < workList.length; i++) {
                    dR.child(id).setValue(workList[i]);
                    //dR.updateChildren(workList[i]);

                }
                */

                Intent intent = new Intent(ExportActivity.this, MainProgActivity.class);
                finish();
                startActivity(intent);
            }
        });

        Hm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ExportActivity.this, MainProgActivity.class);
                finish();
                startActivity(intent);

            }
        });

    }

}
