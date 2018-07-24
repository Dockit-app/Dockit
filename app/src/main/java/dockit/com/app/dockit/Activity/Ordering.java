package dockit.com.app.dockit.Activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import dockit.com.app.dockit.Entity.Order;
import dockit.com.app.dockit.R;
import dockit.com.app.dockit.VIewModel.OrderViewModel;

public class Ordering extends AppCompatActivity {

    private OrderViewModel orderViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordering);

        setViewModel();
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
