package com.example.fireregister;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.lang.reflect.Field;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonSignIn;
    private EditText EditTextEmail;
    private  EditText EditTextpassword;
    private   TextView TextViewSignUp;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser()!=null ){
            //profile activity here
            finish();
            startActivity(new Intent(this,MainActivity.class));

            // as the user is already loged in so directly opening the profile activit
        }

         EditTextEmail = (EditText)findViewById(R.id.editTextEmail);
         EditTextpassword = (EditText)findViewById(R.id.editTextPassword);

         buttonSignIn = (Button)findViewById(R.id.buttonSignIn);
         TextViewSignUp = (TextView)findViewById(R.id.textViewSignUp);


         buttonSignIn.setOnClickListener(this);
         TextViewSignUp.setOnClickListener(this);

    }


    private void userLogin(){
        String email = EditTextEmail.getText().toString().trim();
        String password = EditTextpassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            // check email is entered or not

            Toast.makeText(this," enter the email", Toast.LENGTH_SHORT).show();

        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"enter the password",Toast.LENGTH_SHORT).show();
        }

        progressDialog.setMessage("logging in");
        progressDialog.show();


        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                progressDialog.dismiss();

                if(task.isSuccessful()){
                    // start the profile acivity

                    finish();
                    startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
                    //getapplication cintext is used because as we we are in addoncomplete listener
                    // so we cannot use this,profileactivity.class


                }

            }
        });

    }




    @Override
    public void onClick(View view) {

        if(view == buttonSignIn){
            userLogin();

        }

        if(view == TextViewSignUp){
            finish();
            startActivity(new Intent(this,MainActivity.class));
        }



    }
}
