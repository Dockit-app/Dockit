package dockit.com.app.dockit.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import dockit.com.app.dockit.R;
import dockit.com.app.dockit.Tasks.SharedPreferencesManager;

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

        newOrder.setOnClickListener(v -> {
            //TODO: Send to activity Ordering
            Toast.makeText(getApplicationContext(), "creating new order!", Toast.LENGTH_SHORT).show();
        });

        existingOrder.setOnClickListener(v -> {
            //TODO: Send to activity OrderSummary
            Toast.makeText(getApplicationContext(), "fetch list of existing orders!", Toast.LENGTH_SHORT).show();
        });



    }
}