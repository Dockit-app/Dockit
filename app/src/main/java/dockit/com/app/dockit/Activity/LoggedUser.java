package dockit.com.app.dockit.Activity;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import dockit.com.app.dockit.Entity.Order;
import dockit.com.app.dockit.R;
import dockit.com.app.dockit.Tasks.SharedPreferencesManager;
import dockit.com.app.dockit.ViewModel.LoggedUserViewModel;

public class LoggedUser extends AppCompatActivity {
    Button newOrder, existingOrder;
    TextView nameUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_user);

        //retrieve username from Shared Preference
        String name = SharedPreferencesManager.getInstance(this).getUsername();

        //Update the textView to show the name of the user
        nameUser = findViewById(R.id.nameUser);
        nameUser.setText(name);

        //buttons shows dialogs
        newOrder = findViewById(R.id.newOrder);
        existingOrder = findViewById(R.id.existingOrders);

        final LoggedUserViewModel loggedUserViewModel;
        loggedUserViewModel = ViewModelProviders.of(this).get(LoggedUserViewModel.class);

        newOrder.setOnClickListener(v -> {
            //Create table number and send to Ordering activity

            final EditText tableNumberEditText = new EditText(this);
            tableNumberEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("Table number:")
                    .setView(tableNumberEditText)
                    .setPositiveButton("New Order", (dialog1, which) -> {
                        //Get user input and send value to createTableNumber
                        String numberTable = String.valueOf(tableNumberEditText.getText().toString());
                        loggedUserViewModel.createTableNumber(numberTable, this);
                    })
                    .setNegativeButton("Cancel", null)
                    .create();
            dialog.show();

        });

        existingOrder.setOnClickListener(v -> loggedUserViewModel.getTables().observe(this, orders -> {

            ArrayList<String> tables = new ArrayList<>();

            //If there are results, transforms in an Array[string]
            if (orders != null) {
                for (Order orderTables : orders) {
                    tables.add(orderTables.getOrderTable());
                }
            }
            loggedUserViewModel.listExistingTables(this, tables);
        }));

    }




}