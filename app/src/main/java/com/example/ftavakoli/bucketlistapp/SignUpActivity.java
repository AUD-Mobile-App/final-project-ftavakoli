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
import com.google.firebase.auth.UserProfileChangeRequest;

public class SignUpActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_sign_up);


        emailEditTxt = findViewById(R.id.emailEditText);
        passwordEditTxt = findViewById(R.id.passwordEditText);
        signupButton = findViewById(R.id.loginButton);
        errorTxtView = findViewById(R.id.ErrorTextView);

        //initializing the instance of firebase
        myFireBaseAuth = FirebaseAuth.getInstance();

        signupButton.setOnClickListener(new View.OnClickListener() {
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
                    signUp(emailEditTxt.getText().toString(), passwordEditTxt.getText().toString());
            }
        });



    }
    //check if the user is login
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = myFireBaseAuth.getCurrentUser();
    }

    //signUp function for getting the email and passswrod
    private void signUp(String email, String password){
        myFireBaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        //check if the user signed up before or no!
                        if(task.isSuccessful()) {
                            Intent intent = new Intent(SignUpActivity.this, ItemListActivity.class);
                            startActivity(intent);
                        }
                        else{
                            errorTxtView.setText("User already exists...");
                        }
                    }
                });
    }

}
