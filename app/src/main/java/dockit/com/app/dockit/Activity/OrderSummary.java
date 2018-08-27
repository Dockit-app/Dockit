package dockit.com.app.dockit.Activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.widget.TextView;


import dockit.com.app.dockit.Adapter.MenuItemListAdapter;
import dockit.com.app.dockit.Adapter.SummaryListAdapter;

import dockit.com.app.dockit.Entity.Result.OrderResult;
import dockit.com.app.dockit.R;

import dockit.com.app.dockit.Tasks.SharedPreferencesManager;
import dockit.com.app.dockit.ViewModel.OrderSummaryViewModel;

public class OrderSummary extends AppCompatActivity {

    private OrderSummaryViewModel orderSummaryViewModel;


    private String covers = "";
    private String server = "Nathan";
    private String time = "18:40";
    private int orderId = 0;
    private String tableName = "Table 1";

    SummaryListAdapter summaryListAdapter;
    MenuItemListAdapter mILA;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary);

        //Thomas I've passed the orderResult to your activity, saves you querying for it
        OrderResult orderResult = (OrderResult)getIntent().getSerializableExtra("OrderResult");
        orderId = orderResult.getId();
        setOrderSummaryViewModel(orderResult);
        tableName = orderSummaryViewModel.GetTable(orderResult);
        covers = orderSummaryViewModel.GetCovers(orderResult) + "\nCovers";
        time = orderSummaryViewModel.GetTime(orderResult);


        TextView tableText = findViewById(R.id.table_text);
        tableText.setText(tableName);

        server = SharedPreferencesManager.getInstance(this).getUsername();
        TextView serverText = findViewById(R.id.server_text);
        serverText.setText(server);

        TextView coversText = findViewById(R.id.covers_text);
        coversText.setText(covers);

        TextView timeText = findViewById(R.id.time_text);
        timeText.setText(orderResult.getTimeStamp());
        SetSummaryRecyclerView(orderResult);


    }

    private void setOrderSummaryViewModel(final OrderResult order) {
        orderSummaryViewModel = ViewModelProviders.of(this).get(OrderSummaryViewModel.class);
//        orderSummaryViewModel.RetrieveOrderInfo(orderId).observe(this, new Observer<OrderResult>() {
//            @Override
//            public void onChanged(@Nullable OrderResult orderResult) {
//                //set up listadapter and pass in sorted list
//                //
//                if (orderResult != null) {
//                    SetSummaryRecyclerView(order);
//
//                }
//                orderSummaryViewModel.RetrieveOrderInfo(orderId).removeObserver(this);
//            }
//        });


    }


    private void SetSummaryRecyclerView(OrderResult orderResult) {
        //TODO: Layout id doesn't exist
        RecyclerView recyclerView = findViewById(R.id.summary_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        //NEED TO TAKE IN ORDERED ITEMS AS List<MenuItemView>
        summaryListAdapter = new SummaryListAdapter(this, orderSummaryViewModel.CrappyMenu(orderResult));
        recyclerView.setAdapter(summaryListAdapter);
//        recyclerView.setAdapter(mILA);
    }


}
