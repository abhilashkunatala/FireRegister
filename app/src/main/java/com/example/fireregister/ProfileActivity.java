package com.example.fireregister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;
    private TextView textViewUserEmail;
    private Button buttonLogout;
    private DatabaseReference databaseReference;
    private EditText editTextName;
    private EditText editTextAdress;
    private Button buttonSave;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser()==null ){

            finish();
            //profile activity her
            startActivity(new Intent(this,LoginActivity.class));
        }

        databaseReference = FirebaseDatabase.getInstance().getReference();
        editTextAdress =  (EditText)findViewById(R.id.editTextadress);
        editTextName = (EditText)findViewById(R.id.editTextName);
        buttonSave = (Button)findViewById(R.id.buttonSave);


        FirebaseUser user = firebaseAuth.getCurrentUser();




        textViewUserEmail = (TextView)findViewById(R.id.textViewUserEmail);

        textViewUserEmail.setText("welcome "+ user.getEmail());
        buttonLogout = (Button)findViewById(R.id.buttonLogout);
        buttonLogout.setOnClickListener(this);

        buttonSave.setOnClickListener(this);


    }

    private void saveUserInformation(){
        String name = editTextName.getText().toString().trim();
        String address = editTextAdress.getText().toString().trim();

        Userinformation userinformation = new Userinformation(name ,address);
        FirebaseUser user = firebaseAuth.getCurrentUser();
        databaseReference.child(user.getUid()).setValue(userinformation);


        Toast.makeText(this ,"Infomration saved ",Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onClick(View view) {
        if(view == buttonLogout){

            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this,LoginActivity.class));


        }

        if(view==buttonSave){
            saveUserInformation();
        }
    }
}
