package com.gicbd.gicuitest;

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

import com.gicbd.gicuitest.database.Database;

public class MainActivity extends AppCompatActivity {

    private Database db;


    Toolbar toolbar;
    EditText name,password;

    Button signup,signin;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new Database(this);

        init();

    }


    private void init(){

        toolbar = findViewById(R.id.toolbar);

        name = findViewById(R.id.name);
        password = findViewById(R.id.password);


        signin = findViewById(R.id.signin);
        signup = findViewById(R.id.signup);

        signin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                hideKeyboard();

                String nameString = name.getText().toString();
                String passString = password.getText().toString();

                if (nameString.isEmpty() || passString.isEmpty()){
                    if (nameString.isEmpty())
                    name.setError("This can not be empty!");

                    if (passString.isEmpty())
                        password.setError("This can not be empty!");

                    return;
                }

                try{
                    if (db.getUserPasswordByName(nameString).equals(passString)){
                        name.setText("");
                        password.setText("");
                        print("Sign In Success");
                    }


                    else
                        print("Sign In UnSuccess");
                }catch (NullPointerException np){
                    np.printStackTrace();
                    print("User does not exist!!! Sign up first");
                }

            }
        });
        signup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,SignUpActivity.class);
                startActivity(i);
            }
        });
    }

    private void print(String msg){
        Toast.makeText(MainActivity.this,msg, Toast.LENGTH_LONG).show();
    }

    public void hideKeyboard() {
        View view =getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}
