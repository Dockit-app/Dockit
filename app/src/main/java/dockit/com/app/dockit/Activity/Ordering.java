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
        tableName = intent.getStringExtra("table");
        OrderResult existingOrder = null;
        existingOrder = (OrderResult) getIntent().getSerializableExtra("Order");

        TextView textView = findViewById(R.id.table_text);
        textView.setText(tableName);

        orderLocationListAdapter = new OrderLocationListAdapter(this);

        setOrderViewModel();

        orderLocationClickBuilder = new OrderLocationClickBuilder(orderViewModel, orderLocationListAdapter);
        setOrderLocationRecycler();

        if(existingOrder == null) {
            createNewOrder();
            Log.d("order", "Created table " + tableName);
        } else {
            Log.d("order", "ID: " + existingOrder.getId() + " Table: " + existingOrder.getOrderTable());
            //will retrieve order location
            setExistingOrderLocationObserver(existingOrder.orderLocationResults.get(0));
        }


        setOrderFinishButton();



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

    public void setExistingOrderLocationObserver(OrderLocationResult orderLocation){

        onExistingResult(orderLocation);

//        orderViewModel.getOrderLocation(id).observe(this, new Observer<OrderLocation>() {
//            boolean isCalled = false;
//            @Override
//            public void onChanged(@Nullable OrderLocation result) {
//                if (result != null && !isCalled){
//                    isCalled = true;
//                    onExistingResult(result);
//                }
//                orderViewModel.getOrderLocation(id).removeObserver(this);
//            }
//        });
    }


    private void createOrderObserver(int orderId) {
        orderViewModel.getLiveOrderById(orderId).observe(this, new Observer<List<OrderResult>>() {
            @Override
            public void onChanged(@Nullable List<OrderResult> orderResults) {
                orderViewModel.setLiveOrderResult(orderResults.get(0));
                orderViewModel.getLiveOrderById(orderId).removeObserver(this);
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

                    Log.i(this.getClass().getSimpleName(), "Begin updateMandatory menu pager");
                    updateMenuPager(orderLocationListAdapter.getSelectedOrderLocationId());
                    Log.i(this.getClass().getSimpleName(), "End updateMandatory menu pager");
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
        createOrderObserver(orderId);
    }

    public void onExistingResult(OrderLocationResult result) {

        orderId = result.getOrderId();
        orderLocationClickBuilder.setOrderId(orderId);
        orderLocationListAdapter.setFirstSelectedOrderLocationId(result.getId());

        createExistingMenuPageObserver(orderId);
        createOrderLocationObserver();
        createOrderObserver(orderId);

//        updateMenuPager(result.getLocationNumber());
    }

    private void createMenuPageObserver() {
        orderViewModel.getLiveOrderResults().observe(this, new Observer<List<OrderResult>>() {
            @Override
            public void onChanged(@Nullable List<OrderResult> orderResults) {
                if(orderResults.size() > 0) {
                    OrderResult orderResult = orderResults.get(orderResults.size()-1);
                    Log.i(this.getClass().getSimpleName(), "Creating menu pager for order "+orderResults.get(orderResults.size()-1).getId());
                    createMenuPager(orderResult.orderLocationResults.get(orderResult.orderLocationResults.size()-1));
                    orderViewModel.getLiveOrderResults().removeObserver(this);
                }
            }
        });
    }

    private void createExistingMenuPageObserver(int orderId) {
        orderViewModel.getLiveOrderResults().observe(this, new Observer<List<OrderResult>>() {
            @Override
            public void onChanged(@Nullable List<OrderResult> orderResults) {
                OrderResult orderResult = null;
                //TODO: Replace with getOrderById
                if(orderResults.size() > 0) {
                    for(OrderResult currentOrderResult : orderResults) {
                        if(currentOrderResult.getId() == orderId) {
                            orderResult = currentOrderResult;
                        }
                    }
                    Log.i(this.getClass().getSimpleName(), "Creating existing menu pager for order "+orderResults.get(orderResults.size()-1).getId());
                    createMenuPager(orderResult.orderLocationResults.get(0));
                    orderViewModel.getLiveOrderResults().removeObserver(this);
                }
            }
        });
    }

    public void createMenuPager(OrderLocationResult orderLocationResult) {

            ViewPager menuPager = (ViewPager) findViewById(R.id.menu_view_pager);
            menuPager.clearOnPageChangeListeners();

            orderMenuAdapter = new OrderMenuAdapter(getSupportFragmentManager());
            orderMenuAdapter.setOrderResult(orderLocationResult.menus);
            menuPager.setAdapter(orderMenuAdapter);

            TabLayout tabLayout = (TabLayout) findViewById(R.id.menu_tab_layout);
            tabLayout.setupWithViewPager(menuPager);
            menuPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

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

    private void setOrderFinishButton() {

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createOrderSummaryActivity(orderViewModel.getLiveOrderResult().getValue());
            }
        });
    }

    private void createOrderSummaryActivity(OrderResult orderResult) {
        Intent startUserActivity = new Intent(this, OrderSummary.class);

        orderResult.setTimeStamp(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date()));
        startUserActivity.putExtra("OrderResult", orderResult);

        startUserActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startActivity(startUserActivity);
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


}
