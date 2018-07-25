package dockit.com.app.dockit.Activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import dockit.com.app.dockit.Entity.Order;
import dockit.com.app.dockit.ListAdapter.OrderLocationListAdapter;
import dockit.com.app.dockit.R;
import dockit.com.app.dockit.VIewModel.OrderViewModel;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class Ordering extends AppCompatActivity {

    private int orderId = -1;
    private OrderViewModel orderViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordering);

        setViewModel();

        //TODO: Receive order id
    }

    private void setOrderLocationRecyclerView() {

        RecyclerView recyclerView = findViewById(R.id.order_location_recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        OrderLocationListAdapter orderLocationListAdapter = new OrderLocationListAdapter(this, orderId);
        recyclerView.setAdapter(orderLocationListAdapter);

    }

    private void setViewModel() {

        orderViewModel = ViewModelProviders.of(this).get(OrderViewModel.class);

        orderViewModel.getLiveOrders().observe(this, new Observer<Order>() {
            @Override
            public void onChanged(@Nullable Order order) {
                //TODO: Implement logic on change
            }
        });

    }
}
