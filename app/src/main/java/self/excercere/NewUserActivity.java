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

public class NewUserActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText EditUsername, EditPassword;
    private Button ButtonReg;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        firebaseAuth = FirebaseAuth.getInstance();
        EditUsername = (EditText) findViewById(R.id.user);
        EditPassword = (EditText) findViewById(R.id.password);
        ButtonReg = (Button) findViewById(R.id.signUp);
        ButtonReg.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {

        register();
    }

    private void register() {

        String username = EditUsername.getText().toString().trim();
        String password = EditPassword.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {

            return;
        }

        firebaseAuth.createUserWithEmailAndPassword(username, password)
        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {

                    Toast.makeText(NewUserActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(NewUserActivity.this, RegistrationActivity.class);
                    finish();
                    startActivity(intent);
                } else {

                    Toast.makeText(NewUserActivity.this, "Failure", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}
