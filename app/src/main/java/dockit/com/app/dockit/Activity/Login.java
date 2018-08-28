package dockit.com.app.dockit.Activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import dockit.com.app.dockit.ViewModel.UserViewModel;
import dockit.com.app.dockit.R;


public class Login extends AppCompatActivity {

    Button b1, b2, b3, b4, b5, b6, b7, b8, b9, b0, bEnter;
    String pinView = "";
    TextView textPin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        textPin = findViewById(R.id.pinEntered);

        //Declare all buttons and values in string variables
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

        final UserViewModel userViewModel;
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        //on click, add the value of the button the the string pin
        b1.setOnClickListener(v -> updatePin(button1));
        b2.setOnClickListener(v -> updatePin(button2));
        b3.setOnClickListener(v -> updatePin(button3));
        b4.setOnClickListener(v -> updatePin(button4));
        b5.setOnClickListener(v -> updatePin(button5));
        b6.setOnClickListener(v -> updatePin(button6));
        b7.setOnClickListener(v -> updatePin(button7));
        b8.setOnClickListener(v -> updatePin(button8));
        b9.setOnClickListener(v -> updatePin(button9));
        b0.setOnClickListener(v -> updatePin(button0));

        //when the user press enter, compare pin with the database
        bEnter.setOnClickListener(v -> userViewModel.doLogin(pinView));

        //If toast is an error, make Toast
        userViewModel.toast().observe(this, (String toast) -> {
            assert toast != null;
            if (toast.equals("Wrong Pin!")) {
                pinView = "WRONG PIN";
                textPin.setText(pinView);
            } else if (toast.equals("Success!")){
                //Send to the next Activity
                Intent i = new Intent(this, TableSelection.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                this.startActivity(i);
            }
        });


    }

    //Update the pin adding the number pressed by the user
    public void updatePin(String number){
        if(pinView.equals("WRONG PIN")){
            pinView = "";
            textPin.setText(pinView);
        }
        pinView = pinView + number;
        textPin.setText(pinView);
    }


}