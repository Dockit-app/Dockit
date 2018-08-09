package dockit.com.app.dockit.Activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import dockit.com.app.dockit.Adapter.OrderMenuAdapter;
import dockit.com.app.dockit.Adapter.OrderLocationListAdapter;
import dockit.com.app.dockit.ClickListener.OrderLocationClickBuilder;
import dockit.com.app.dockit.Entity.Decorator.OrderLocationView;
import dockit.com.app.dockit.Entity.Result.OrderLocationResult;
import dockit.com.app.dockit.Entity.Result.OrderResult;
import dockit.com.app.dockit.R;
import dockit.com.app.dockit.ViewModel.OrderViewModel;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Ordering extends AppCompatActivity {

    private OrderViewModel orderViewModel;
    private OrderLocationClickBuilder orderLocationClickBuilder;
    private OrderLocationListAdapter orderLocationListAdapter;

    OrderMenuAdapter mPagerAdapter;
    private String tableName = "TABLE 1";
    private int orderId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordering);

        //TODO: Receive order id for existing

        //TODO: Receive user name, tablename from intent
        TextView textView = findViewById(R.id.table_text);
        textView.setText(tableName);

        orderLocationListAdapter = new OrderLocationListAdapter(this);

        setOrderViewModel();

        orderLocationClickBuilder = new OrderLocationClickBuilder(orderViewModel, orderLocationListAdapter);
        setOrderLocationRecyclerView();

        createNewOrder();

    }

    private void setOrderLocationRecyclerView() {

        int orderLocationAmount = orderViewModel.getOrderLocationAmount();
        List<OrderLocationView> orderLocationViews = new ArrayList<>();
        for(int i = 1; i <= orderLocationAmount; i++) {
            OrderLocationView orderLocationView = new OrderLocationView();
            orderLocationView.setLocationNumber(i);
            orderLocationView.setLocationText(Integer.toString(i));
            if(i == 1) {
                orderLocationView.setSelected(true);
                orderLocationView.setCreated(true);
                orderLocationView.setId(1);
            }
            orderLocationViews.add(orderLocationView);
        }

        RecyclerView recyclerView = findViewById(R.id.order_location_recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);


        orderLocationListAdapter.setOrderLocationViews(orderLocationViews);
        recyclerView.setAdapter(orderLocationListAdapter);

        orderLocationClickBuilder.setOnClickListener(recyclerView);

        orderViewModel.setSelectedOrderLocation(orderLocationViews.get(0));

    }

    private void setOrderViewModel() {
        orderViewModel = ViewModelProviders.of(this).get(OrderViewModel.class);

        orderViewModel.getLiveOrderResults().observe(this, new Observer<List<OrderResult>>() {
            @Override
            public void onChanged(@Nullable List<OrderResult> orderResults) {
                if(orderResults.size() > 0) {
                    Log.d(this.getClass().getSimpleName(), "Creating menu pager");
                    createMenuPager(orderResults.get(orderResults.size()-1)); //TODO: Enable proper order selection
                }
            }
        });

//        orderViewModel.getOrderLocationsByOrderId(orderId).observe(this, new Observer<List<OrderLocationResult>>() {
//            @Override
//            public void onChanged(@Nullable List<OrderLocationResult> orderLocations) {
//                Log.d(this.getClass().getSimpleName(), "OrderLocation change observed");
//                if(orderViewModel.getLiveSelectedOrderLocationView().getId() != null) {
//                    Log.d(this.getClass().getSimpleName(), "Updating menu pager");
//                    updateMenuPager(orderLocations.get(orderViewModel.getLiveSelectedOrderLocationView().getId()));
//                }
//            }
//        });

        orderViewModel.getLiveSelectedOrderLocationView().observe(this, new Observer<OrderLocationView>() {
            @Override
            public void onChanged(@Nullable OrderLocationView orderLocationView) {
                Log.d(this.getClass().getSimpleName(), "Updating menu pager");
//                updateMenuPager(orderViewModel.getOrderLocationById(orderLocationView.getId()));
            }
        });
    }

    private void createNewOrder() {
        orderViewModel.createOrder(tableName);
    }

    public void createMenuPager(OrderResult orderResult) {

        ViewPager menuPager = (ViewPager) findViewById(R.id.menu_view_pager);
        mPagerAdapter = new OrderMenuAdapter(getSupportFragmentManager());
        mPagerAdapter.setOrderResult(orderResult.orderLocationResults.get(0).menus);
        menuPager.setAdapter(mPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.menu_tab_layout);
        tabLayout.setupWithViewPager(menuPager);
        menuPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }

    public void updateMenuPager(OrderLocationResult orderLocationResult) {
        if(mPagerAdapter != null) {
            mPagerAdapter.setOrderResult(orderLocationResult.menus);
            mPagerAdapter.notifyDataSetChanged();
            Log.d(Ordering.class.getSimpleName(), "Order location updated");
        }
    }
}
