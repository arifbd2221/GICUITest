package com.gicbd.gicuitest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.andreabaccega.widget.FormEditText;
import com.gicbd.gicuitest.database.Database;
import com.gicbd.gicuitest.model.User;

public class SignUpActivity extends AppCompatActivity {


    Toolbar toolbar;
    FormEditText email,name,phone,password;


    Button signin,signup;

    private Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        database = new Database(this);

        init();


    }

    private void init(){

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        name = findViewById(R.id.name);
        password = findViewById(R.id.password);

        signup =findViewById(R.id.signup);

        signin = (Button) findViewById(R.id.signin);

        signup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                hideKeyboard();

                FormEditText[] allFields = {name,email,phone,password};

                boolean allValid  = true;

                for (FormEditText field : allFields){
                    allValid = field.testValidity() && allValid;
                }

                if (allValid){
                    User user = new User(name.getText().toString(),email.getText().toString(),phone.getText().toString(),password.getText().toString());

                    if(database.insertUser(user) > 0){
                        name.setText("");
                        email.setText("");
                        phone.setText("");
                        password.setText("");
                        print("SignUp Successfull");
                    }

                    else
                        print("SignUp Unsuccessfull");


                }



            }
        });

        signin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SignUpActivity.this,MainActivity.class);
                startActivity(i);
            }
        });

    }

    private void print(String msg){
        Toast.makeText(SignUpActivity.this,msg, Toast.LENGTH_LONG).show();
    }

    public void hideKeyboard() {
        View view =getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}
