package dockit.com.app.dockit.Activity;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

        //buttons send to the next activities
        newOrder = findViewById(R.id.newOrder);
        existingOrder = findViewById(R.id.existingOrders);
        final LoggedUserViewModel loggedUserViewModel;
        loggedUserViewModel = ViewModelProviders.of(this).get(LoggedUserViewModel.class);

        newOrder.setOnClickListener(v -> {
            loggedUserViewModel.newTable(LoggedUser.this);
            Toast.makeText(getApplicationContext(), "creating new order!", Toast.LENGTH_SHORT).show();
        });

        existingOrder.setOnClickListener(v -> {
            loggedUserViewModel.listExistingTables(LoggedUser.this);
            Toast.makeText(getApplicationContext(), "fetch list of existing orders!", Toast.LENGTH_SHORT).show();
        });



    }
}