package dockit.com.app.dockit.Activity;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import dockit.com.app.dockit.ViewModel.UserViewModel;
import dockit.com.app.dockit.R;



public class Login extends AppCompatActivity {

    Button b1, b2, b3, b4, b5, b6, b7, b8, b9, b0, bEnter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //declares all buttons and values in string variables
        b1 = findViewById(R.id.button1);
        final String button1 = b1.getText().toString();

        b2 = findViewById(R.id.button2);
        final String button2 = b2.getText().toString();

        b3 = findViewById(R.id.button3);
        final String button3 = b3.getText().toString();

        b4 = findViewById(R.id.button4);
        final String button4 = b4.getText().toString();

        b5 = findViewById(R.id.button5);
        final String button5 = b5.getText().toString();

        b6 = findViewById(R.id.button6);
        final String button6 = b6.getText().toString();

        b7 = findViewById(R.id.button7);
        final String button7 = b7.getText().toString();

        b8 = findViewById(R.id.button8);
        final String button8 = b8.getText().toString();

        b9 = findViewById(R.id.button9);
        final String button9 = b9.getText().toString();

        b0 = findViewById(R.id.button0);
        final String button0 = b0.getText().toString();

        bEnter = findViewById(R.id.enter);

        final UserViewModel userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        //on click, adds the value of the button the the string pin
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userViewModel.updatePin(button1);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userViewModel.updatePin(button2);
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userViewModel.updatePin(button3);
            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userViewModel.updatePin(button4);
            }
        });
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userViewModel.updatePin(button5);
            }
        });
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userViewModel.updatePin(button6);
            }
        });
        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userViewModel.updatePin(button7);
            }
        });
        b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userViewModel.updatePin(button8);
            }
        });
        b9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userViewModel.updatePin(button9);
            }
        });
        b0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userViewModel.updatePin(button0);
            }
        });

        userViewModel.toast().observe(this, toast -> {
            if(toast.equals("Wrong Pin!") || toast.equals("Welcome Back!")) {
                Toast.makeText(getApplicationContext(), toast, Toast.LENGTH_SHORT).show();
            }
            else {
                Intent myIntent = new Intent(Login.this, LoggedUser.class);
                myIntent.putExtra("name", toast); //Optional parameters
                Login.this.startActivity(myIntent);
            }
        });

        //when the user press enter, compares if pin = 12345
        bEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userViewModel.doLogin();
            }
        });
    }
}


