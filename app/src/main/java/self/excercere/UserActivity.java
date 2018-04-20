package self.excercere;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class UserActivity extends AppCompatActivity implements View.OnClickListener{

    private Button buttonSign;
    private EditText EnterUsername;
    private EditText EnterPassword;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        EnterUsername = (EditText) findViewById(R.id.user);
        EnterPassword = (EditText) findViewById(R.id.password);
        buttonSign = (Button) findViewById(R.id.button);
        firebaseAuth = FirebaseAuth.getInstance();
        buttonSign.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        signIn();
    }

    private void signIn() {

        String username = EnterUsername.getText().toString().trim();
        String password = EnterPassword.getText().toString().trim();

        firebaseAuth.signInWithEmailAndPassword(username, password)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {

                        Toast.makeText(UserActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(UserActivity.this, MainProgActivity.class);
                        finish();
                        startActivity(intent);
                    } else {

                        Toast.makeText(UserActivity.this, "Failure", Toast.LENGTH_SHORT).show();
                    }
                }
            });


    }
}
