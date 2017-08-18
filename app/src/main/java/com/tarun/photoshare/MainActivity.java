package com.tarun.photoshare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.*;

public class MainActivity extends AppCompatActivity implements View.OnKeyListener,View.OnClickListener {

    boolean signup=true;
    EditText password;

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        if(i == KeyEvent.KEYCODE_ENTER && keyEvent.getAction()==KeyEvent.ACTION_DOWN){

            signUp(view);

        }
        return false;
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.backgroundRL || view.getId()==R.id.cameraImageView){
            InputMethodManager input = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            input.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }

    public void showUserList(){
        Intent intent = new Intent(getApplicationContext(),UserListActivity.class);
        startActivity(intent);
    }
    public void login(View view){
        Button button = (Button) findViewById(R.id.signIn);
        TextView text = (TextView) findViewById(R.id.loginTextView);

        if(signup){
            button.setText("Login");
            text.setText("Sign Up");
            signup=false;
        }
        else{
            button.setText("Sign Up");
            text.setText("Login");
            signup=true;
        }
    }
    public void signUp(View view){

        EditText username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);

        if(username.getText().toString().equals("") || password.getText().toString().equals("")){

            Toast.makeText(this, "Both username and password required", Toast.LENGTH_SHORT).show();
        }
        else{
            Button button = (Button) findViewById(R.id.signIn);


            if(button.getText().equals("Sign Up")) {
                ParseUser user = new ParseUser();
                user.setUsername(username.getText().toString());
                user.setPassword(password.getText().toString());

                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            Toast.makeText(MainActivity.this, "SignUp Successfull", Toast.LENGTH_SHORT).show();
                            showUserList();
                        } else {
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
            else{
                ParseUser.logInInBackground(username.getText().toString(), password.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if(e == null){
                            Toast.makeText(MainActivity.this, "Login Successfull", Toast.LENGTH_SHORT).show();
                            showUserList();
                        }
                        else{
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RelativeLayout rl = (RelativeLayout) findViewById(R.id.backgroundRL);
        ImageView iv = (ImageView) findViewById(R.id.cameraImageView);

        rl.setOnClickListener(this);
        iv.setOnClickListener(this);

        password = (EditText) findViewById(R.id.password);
        password.setOnKeyListener(this);

        if(ParseUser.getCurrentUser()!=null){
            showUserList();
        }

        ParseAnalytics.trackAppOpenedInBackground(getIntent());
    }

}
