package wjwjwt.flashmeetprototype;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class LoginActivity extends Activity {
    protected TextView mSignUpTextView;

    protected EditText mUsername;
    protected EditText mPassword;
    protected Button mLogInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        Parse.initialize(this, "3e7kOFV3rZFAuNO7IDcga43n1n0AuorWjC27Y6na", "V01ZGOlXtqkUdahJOzRoXQUhtqGtnjxmfkkPFudl");





        mSignUpTextView = (TextView)findViewById(R.id.signUpText);
        mSignUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        mUsername = (EditText)findViewById(R.id.loginUsername);
        mPassword = (EditText)findViewById(R.id.loginPassword);
        mLogInButton = (Button)findViewById(R.id.loginButton);
        mLogInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = mUsername.getText().toString(); //editable to string get text from field
                String password = mPassword.getText().toString();

                username = username.trim();
                password = password.trim();
                //trim removes spaces and stores new value back in variable

                if(username.isEmpty() || password.isEmpty())
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    builder.setMessage(R.string.errormessage)
                            .setTitle(R.string.errortitle)
                            .setPositiveButton(android.R.string.ok, null);

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                else {
                    //login
                    ParseUser.logInInBackground(username, password, new LogInCallback() {
                        @Override
                        public void done(ParseUser user, ParseException e) {
                            if(e==null)
                            {
                                Intent intent = new Intent(LoginActivity.this, LoginActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);  //successful
                            }
                            else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setMessage(e.getMessage())
                                        .setTitle(R.string.errortitle)
                                        .setPositiveButton(android.R.string.ok, null);

                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }

                        }
                    });
                }
            }
        });
    }
}
