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

public class MainActivity extends AppCompatActivity  implements View.OnClickListener {
 //initializing the views
    private Button buttonRegister;
    private EditText editTextEmail;
    private EditText editTextPasword;
    private TextView textViewSignIn;
    private ProgressDialog  progressDialog;
    //initializing the firebase auth object

    private FirebaseAuth firebaseAuth;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser()!=null ){
            //profile activity here
            finish();
            startActivity(new Intent(this,MainActivity.class));

            // as the user is already loged in so directly opening the profile activit
        }



progressDialog = new ProgressDialog(this);
        buttonRegister = (Button)findViewById(R.id.buttonRegister);
        editTextEmail = (EditText)findViewById(R.id.editTextEmail);
        editTextPasword = (EditText)findViewById(R.id.editTextPassword);
        textViewSignIn = (TextView)findViewById(R.id.textViewSignin);


        buttonRegister.setOnClickListener(this);
         textViewSignIn.setOnClickListener(this);

    }

    private void registerUser(){
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPasword.getText().toString().trim();


        if(TextUtils.isEmpty(email)){
            //email is empty

            Toast.makeText(this," email is empty ",Toast.LENGTH_SHORT).show();
            //stop the function from executing further
            return;
        }

        if(TextUtils.isEmpty(password)){
            //password is empty


            Toast.makeText(this,"enter the passord ", Toast.LENGTH_SHORT).show();

            //stop the function from executing further
                    return;
        }


        //if validations are ok


        progressDialog.setMessage("Registering the user");
        //ProgressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override

            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    //user registered successfully
                    //dispaly the next activity
                    //Toast.makeText(MainActivity.this,"registered successfully",Toast.LENGTH_SHORT).show();

                    finish();
                    startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                }
                else{
                    Toast.makeText(MainActivity.this," Account already exit ",Toast.LENGTH_SHORT).show();


                }

                progressDialog.setMessage("please wiat");
                progressDialog.show();

            }
        });

    }

    @Override
    public void onClick(View view) {
        if(view == buttonRegister){
            registerUser();
        }
            if(view == textViewSignIn){
                // we will open the sign in activity

                startActivity(new Intent(this, LoginActivity.class));
            }
    }
}
