package dockit.com.app.dockit.Activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import dockit.com.app.dockit.Adapter.OrderMenuAdapter;
import dockit.com.app.dockit.Adapter.OrderLocationListAdapter;
import dockit.com.app.dockit.ClickListener.OrderLocationClickBuilder;
import dockit.com.app.dockit.Entity.MenuItem;
import dockit.com.app.dockit.Entity.Order;
import dockit.com.app.dockit.Entity.OrderLocation;
import dockit.com.app.dockit.Entity.Result.OrderLocationResult;
import dockit.com.app.dockit.Entity.Result.OrderResult;
import dockit.com.app.dockit.R;
import dockit.com.app.dockit.Tasks.ResultHandler;
import dockit.com.app.dockit.ViewModel.OrderViewModel;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Ordering extends AppCompatActivity implements ResultHandler<OrderLocation> {

    private OrderViewModel orderViewModel;
    private OrderLocationClickBuilder orderLocationClickBuilder;
    private OrderLocationListAdapter orderLocationListAdapter;

    OrderMenuAdapter orderMenuAdapter;
    private String tableName;
    private int orderId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordering);
        Intent intent = getIntent();
        tableName = "Table " + intent.getStringExtra("table");

        //TODO: Receive order id for existing

        TextView textView = findViewById(R.id.table_text);
        textView.setText(tableName);

        orderLocationListAdapter = new OrderLocationListAdapter(this);

        setOrderViewModel();

        orderLocationClickBuilder = new OrderLocationClickBuilder(orderViewModel, orderLocationListAdapter);
        setOrderLocationRecycler();

        createNewOrder();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();



    }

    private void setOrderLocationRecycler() {

        int orderLocationAmount = orderViewModel.getOrderLocationAmount();
        List<OrderLocation> orderLocations = new ArrayList<>();
        for(int i = 1; i <= orderLocationAmount; i++) {
            OrderLocation orderLocation = new OrderLocation();
            orderLocation.setLocationNumber(i);
            orderLocation.setLocationText(Integer.toString(i));
            if(i == 1) {
                orderLocation.setSelected(1);
            }
            orderLocations.add(orderLocation);
        }

        RecyclerView recyclerView = findViewById(R.id.order_location_recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);


        orderLocationListAdapter.setOrderLocations(orderLocations);
        recyclerView.setAdapter(orderLocationListAdapter);

        orderLocationClickBuilder.setOnClickListener(recyclerView);

        orderViewModel.setLiveSelectedOrderLocation(orderLocations.get(0));

    }

    private void setOrderViewModel() {
        orderViewModel = ViewModelProviders.of(this).get(OrderViewModel.class);

        createOrderLocationObserver();

    }

    private void createMenuPageObserver() {

        orderViewModel.getLiveOrderLocationsByOrderId(orderId).observe(this, new Observer<List<OrderLocationResult>>() {
            @Override
            public void onChanged(@Nullable List<OrderLocationResult> orderLocations) {
                createMenuPager(orderLocations.get(orderLocations.size()-1));
                orderViewModel.getLiveOrderLocationsByOrderId(orderId).removeObserver(this);
            }
        });
    }

    private void createOrderLocationObserver() {
        orderViewModel.getLiveOrderLocationsByOrderId(orderId).observe(this, new Observer<List<OrderLocationResult>>() {
            @Override
            public void onChanged(@Nullable List<OrderLocationResult> orderLocations) {

                Log.i(this.getClass().getSimpleName(), "Order locations observed");

                //Ensure created orderLocation has id set
                for(OrderLocationResult orderLocationResult : orderLocations) {
                    if(orderLocationResult.getLocationNumber().equals(orderViewModel.getLiveSelectedOrderLocation().getValue().getLocationNumber())) {
                        orderViewModel.setLiveSelectedOrderLocation(new OrderLocation(orderLocationResult));
                    }
                }

                if(orderLocationListAdapter.setOrderLocationResults(orderLocations)) {

                    Log.i(this.getClass().getSimpleName(), "Begin update menu pager");
                    updateMenuPager(orderLocationListAdapter.getSelectedOrderLocationId());
                    Log.i(this.getClass().getSimpleName(), "End update menu pager");
                }
            }
        });
    }

    private void createNewOrder() {
        orderViewModel.createOrder(tableName, this);
    }

    public void onResult(OrderLocation result) {
        Log.i(this.getClass().getSimpleName(), "Handling create order result: locationId "+result.getId());
        orderId = result.getOrderId();
        orderLocationClickBuilder.setOrderId(orderId);
        orderLocationListAdapter.setFirstSelectedOrderLocationId(result.getId());

        createMenuPageObserver();
        createOrderLocationObserver();
    }

    private void createItemCounterObserver(int orderId) {

        //Used for counter implementation
        orderViewModel.getLiveMenuItemsByOrderId(orderId).observe(this, new Observer<List<MenuItem>>() {
            @Override
            public void onChanged(@Nullable List<MenuItem> menuItems) {
                if(findViewById(R.id.order_location_recyclerView).getVisibility() == View.VISIBLE) {
                    for (MenuItem menuItem : menuItems) {
                        if (menuItem.getCounter() != null && menuItem.getCounter() > 0) {
                            findViewById(R.id.order_location_recyclerView).setVisibility(View.INVISIBLE);
                            break;
                        }
                    }
                }
                else if(findViewById(R.id.order_location_recyclerView).getVisibility() == View.INVISIBLE) {
                    boolean allZero = true;
                    for (MenuItem menuItem : menuItems) {
                        if (menuItem.getCounter() != null && menuItem.getCounter() > 0) {
                            allZero = false;
                            break;
                        }
                    }

                    if(allZero) {
                        findViewById(R.id.order_location_recyclerView).setVisibility(View.VISIBLE);
                    }

                }
            }
        });
    }

    public void createMenuPager(OrderResult orderResult) {

        if(orderResult.orderLocationResults.size() > 0 &&
                orderResult.orderLocationResults.get(orderResult.orderLocationResults.size()-1).menus.size() > 0) {

            ViewPager menuPager = (ViewPager) findViewById(R.id.menu_view_pager);
            orderMenuAdapter = new OrderMenuAdapter(getSupportFragmentManager());
            orderMenuAdapter.setOrderResult(orderResult.orderLocationResults.get(0).menus);
            menuPager.setAdapter(orderMenuAdapter);

            TabLayout tabLayout = (TabLayout) findViewById(R.id.menu_tab_layout);
            tabLayout.setupWithViewPager(menuPager);
            menuPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

            setOrderFinishButton(orderResult);
        }
    }

    public void createMenuPager(OrderLocationResult orderLocationResult) {

            ViewPager menuPager = (ViewPager) findViewById(R.id.menu_view_pager);
            orderMenuAdapter = new OrderMenuAdapter(getSupportFragmentManager());
            orderMenuAdapter.setOrderResult(orderLocationResult.menus);
            menuPager.setAdapter(orderMenuAdapter);

            TabLayout tabLayout = (TabLayout) findViewById(R.id.menu_tab_layout);
            tabLayout.setupWithViewPager(menuPager);
            menuPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

//            setOrderFinishButton(orderResult);
    }

    public void updateMenuPager(final int orderLocationId) {
        orderViewModel.getOrderLocationById(orderLocationId).observe(this, new Observer<OrderLocationResult>() {
            @Override
            public void onChanged(@Nullable OrderLocationResult orderLocationResult) {

                if(orderMenuAdapter != null && orderLocationResult != null && orderLocationResult.menus != null && orderLocationResult.menus.size() > 0) {

                    if(orderLocationResult.getSelected() == 1 && orderMenuAdapter.getOrderLocationId() != orderLocationResult.getId()) {
                        orderMenuAdapter.setOrderResult(orderLocationResult.menus);
                        orderMenuAdapter.notifyDataSetChanged();
                        orderViewModel.getOrderLocationById(orderLocationId).removeObserver(this);
                        Log.i(Ordering.class.getSimpleName(), "Order location "+orderLocationResult.getId()+" updated");
                    }
                }
            }
        });
    }

    private void setOrderFinishButton(OrderResult orderResult) {
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                orderResult.setTimeStamp(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date()));

                Intent startUserActivity = new Intent(view.getContext(), OrderSummary.class);
                startUserActivity.putExtra("OrderResult", orderResult);

                startUserActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                view.getContext().startActivity(startUserActivity);
            }
        });
    }
}
