package dockit.com.app.dockit.Activity;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.sql.Time;

import dockit.com.app.dockit.Entity.Order;
import dockit.com.app.dockit.R;

import dockit.com.app.dockit.ViewModel.OrderSummaryViewModel;

public class OrderSummary extends AppCompatActivity {

    private OrderSummaryViewModel orderSummaryViewModel;


    private int covers = 0;
    private String server = "Nathan";
    private String time = "18:40";
    private int orderId = 0;
    private String tableName = "Table 1";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary);

        //TODO: Receive Order ID for existing order

        retrieveOrderInfo(orderId);

        //TODO: Retreive Table Name, server name, time and covers from database using Order ID


        TextView tableText = findViewById(R.id.table_text);
        tableText.setText(tableName);

        TextView serverText = findViewById(R.id.server_text);
        serverText.setText(server);

        TextView coversText = findViewById(R.id.covers_text);
        coversText.setText(covers);

        TextView timeText = findViewById(R.id.time_text);
        timeText.setText(time);

        setOrderSummaryViewModel();



    }

    private void setOrderSummaryViewModel() {
        orderSummaryViewModel = ViewModelProviders.of(this).get(OrderSummaryViewModel.class);


    }

    private void retrieveOrderInfo(int id) {
        //Order order = orderSummaryViewModel.retrieveOrder(id);
        //tableName = order.getTable();
        //ToDo Retrieve rest of order data to display
    }
}
