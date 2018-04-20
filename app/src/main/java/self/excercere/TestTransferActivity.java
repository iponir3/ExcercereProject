package self.excercere;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

public class TestTransferActivity extends AppCompatActivity {

    private ArrayList<String> exerList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_transfer);
        exerList = getIntent().getExtras().getStringArrayList("ELIST");
        Toast.makeText(TestTransferActivity.this, exerList.toString(), Toast.LENGTH_SHORT).show();
    }
}
