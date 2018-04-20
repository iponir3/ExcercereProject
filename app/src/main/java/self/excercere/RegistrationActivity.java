package self.excercere;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import static android.R.attr.button;

public class RegistrationActivity extends AppCompatActivity {

    private Button user;
    private Button newUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        user = findViewById(R.id.User);
        newUser = findViewById(R.id.New);
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userActivity();

            }
        });

        newUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                newActivity();

            }
        });

    }
    public void userActivity() {

        Intent regIntent = new Intent(this, UserActivity.class);
        startActivity(regIntent);

    }

    public void newActivity() {

        Intent regIntent = new Intent(this, NewUserActivity.class);
        startActivity(regIntent);

    }

}
