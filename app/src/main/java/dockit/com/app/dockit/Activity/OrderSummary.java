package dockit.com.app.dockit.Activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.sql.Time;

import dockit.com.app.dockit.Entity.Order;
import dockit.com.app.dockit.Entity.Result.OrderResult;
import dockit.com.app.dockit.R;

import dockit.com.app.dockit.Repository.OrderRepository;
import dockit.com.app.dockit.Tasks.SharedPreferencesManager;
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

        //Thomas I've passed the orderResult to your activity, saves you querying for it
        OrderResult orderResult = (OrderResult)getIntent().getSerializableExtra("OrderResult");

        //TODO: Retreive Table Name, server name, time and covers from database using Order ID


        TextView tableText = findViewById(R.id.table_text);
        tableText.setText(tableName);

        server = SharedPreferencesManager.getInstance(this).getUsername();
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
        orderSummaryViewModel.RetrieveOrderInfo(orderId).observe(this, new Observer<OrderResult>() {
            @Override
            public void onChanged(@Nullable OrderResult orderResult) {
                if (orderResult != null) {
                    tableName = orderSummaryViewModel.GetTable(orderResult) + "\nTable";
                    covers = orderSummaryViewModel.GetCovers(orderResult) + "\nCovers";


    }

    private void retrieveOrderInfo(int id) {
        //Order order = orderSummaryViewModel.retrieveOrder(id);
        //tableName = order.getTable();
        //ToDo Retrieve rest of order data to display
    }
}
