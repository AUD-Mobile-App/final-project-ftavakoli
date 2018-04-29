package com.example.ftavakoli.bucketlistapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    //declaring the variables
    Button loginBtn, signupButton;
    EditText passwordEditTxt, emailEditTxt;
    TextView errorTxtView;
    //declaring instance of firebase

    private FirebaseAuth myFireBaseAuth;
    private String TAG = "EmailAndPassword";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        signupButton = findViewById(R.id.signupButton);
        emailEditTxt = findViewById(R.id.emailEditText);
        passwordEditTxt = findViewById(R.id.passwordEditText);
        loginBtn = findViewById(R.id.loginButton);
        errorTxtView = findViewById(R.id.ErrorTextView);

        //initializing the instance of firebase
        myFireBaseAuth = FirebaseAuth.getInstance();

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //calling a login function
                if(emailEditTxt.getText().toString().equals("") && passwordEditTxt.getText().toString().equals(""))
                    errorTxtView.setText("Provided any information!!!!!");
                else if(emailEditTxt.getText().toString().equals(""))
                    errorTxtView.setText("Enter the Email Address");
                else if(passwordEditTxt.getText().toString().equals(""))
                    errorTxtView.setText("Enter the Password!");
                    else
                login(emailEditTxt.getText().toString(), passwordEditTxt.getText().toString());
            }
        });


        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

    }
    //check if the user is login
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = myFireBaseAuth.getCurrentUser();
        //currentUser.getUid();

        if(currentUser != null)
        {
            Intent intent = new Intent(LoginActivity.this, ItemListActivity.class);
            startActivity(intent);
        }
    }





    //login function
    private void login(String email, String password){

        //login and adding a listener to check if the email and password are correct/no
        myFireBaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    //if it is successful get the user!
                    FirebaseUser user = myFireBaseAuth.getCurrentUser();
                   //Toast.makeText(LoginActivity.this, "Hiii User", Toast.LENGTH_SHORT).show();
                    //going to next pageee
                    Intent intent = new Intent(LoginActivity.this, ItemListActivity.class);
                    startActivity(intent);
                }else{
                    //if it fails Toast !
                    Log.d(TAG, "Faaaaaaiiiiillllllled!", task.getException());
                    //Toast.makeText(LoginActivity.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                    errorTxtView.setText("Wrong Information");
                }
            }
        });
    }

}
