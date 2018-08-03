package dockit.com.app.dockit.Activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import dockit.com.app.dockit.Adapter.OrderMenuAdapter;
import dockit.com.app.dockit.Adapter.OrderLocationListAdapter;
import dockit.com.app.dockit.Entity.MenuItem;
import dockit.com.app.dockit.Entity.Result.MenuResult;
import dockit.com.app.dockit.Entity.Result.OrderResult;
import dockit.com.app.dockit.R;
import dockit.com.app.dockit.ViewModel.OrderViewModel;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Ordering extends AppCompatActivity {

    private OrderViewModel orderViewModel;
    private String tableName = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordering);

        //TODO: Receive order id for existing

        //TODO: Receive user name, tablename

        setOrderViewModel();
        setOrderLocationRecyclerView();
        createNewOrder();

    }

    private void setOrderLocationRecyclerView() {

        RecyclerView recyclerView = findViewById(R.id.order_location_recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        OrderLocationListAdapter orderLocationListAdapter = new OrderLocationListAdapter(this, orderViewModel.getOrderLocations().getValue());
        recyclerView.setAdapter(orderLocationListAdapter);

//        recyclerView.addOnItemTouchListener(new OrderLocationClickBuilder()); TODO: add clickListener

    }

    private void setOrderViewModel() {
        orderViewModel = ViewModelProviders.of(this).get(OrderViewModel.class);

        orderViewModel.getLiveOrderResults().observe(this, new Observer<List<OrderResult>>() {
            @Override
            public void onChanged(@Nullable List<OrderResult> orderResults) {
                if(orderResults.size() > 0) {
                    Log.d(this.getClass().getSimpleName(), "OrderResults updated ");
                    setMenuPager(orderResults.get(orderResults.size()-1));
                }
            }
        });
    }

    private void createNewOrder() {
        orderViewModel.createOrder(tableName);
    }

    public void setMenuPager(OrderResult orderResult) {

        ViewPager menuPager = (ViewPager) findViewById(R.id.menu_view_pager);
        OrderMenuAdapter mPagerAdapter = new OrderMenuAdapter(getSupportFragmentManager());
        mPagerAdapter.addOrderResult(orderResult.orderLocationResults.get(0).menus);
        menuPager.setAdapter(mPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.menu_tab_layout);
        tabLayout.setupWithViewPager(menuPager);
        menuPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }

}
