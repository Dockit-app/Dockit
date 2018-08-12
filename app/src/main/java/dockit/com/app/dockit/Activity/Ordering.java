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
import dockit.com.app.dockit.Entity.OrderLocation;
import dockit.com.app.dockit.Entity.Result.OrderLocationResult;
import dockit.com.app.dockit.Entity.Result.OrderResult;
import dockit.com.app.dockit.R;
import dockit.com.app.dockit.Tasks.ResultHandler;
import dockit.com.app.dockit.ViewModel.OrderViewModel;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Ordering extends AppCompatActivity implements ResultHandler<OrderLocation> {

    private OrderViewModel orderViewModel;
    private OrderLocationClickBuilder orderLocationClickBuilder;
    private OrderLocationListAdapter orderLocationListAdapter;

    OrderMenuAdapter orderMenuAdapter;
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
        setOrderLocationRecycler();

        createNewOrder();

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

        orderViewModel.setSelectedOrderLocation(orderLocations.get(0));

    }

    private void setOrderViewModel() {
        orderViewModel = ViewModelProviders.of(this).get(OrderViewModel.class);

        orderViewModel.getLiveOrderResults().observe(this, new Observer<List<OrderResult>>() {
            @Override
            public void onChanged(@Nullable List<OrderResult> orderResults) {
                if(orderResults.size() > 0) {
                    Log.i(this.getClass().getSimpleName(), "Creating menu pager for order "+orderResults.get(orderResults.size()-1).getId());
                    createMenuPager(orderResults.get(orderResults.size()-1));
                }
            }
        });

        orderViewModel.getLiveOrderLocationsByOrderId(orderId).observe(this, new Observer<List<OrderLocationResult>>() {
            @Override
            public void onChanged(@Nullable List<OrderLocationResult> orderLocations) {

                List<OrderLocationResult> selectedOrderLocations = new ArrayList<>();
                for(OrderLocationResult orderLocation : orderLocations) {
                    if(orderLocation.getOrderId() != null && orderLocation.getOrderId() == orderId) {
                        selectedOrderLocations.add(orderLocation);
                    }
                }

                for(OrderLocationResult orderLocationResult : orderLocations) {
                    if(orderLocationResult.getLocationNumber().equals(orderViewModel.getLiveSelectedOrderLocation().getValue().getLocationNumber())) {
                        orderViewModel.setSelectedOrderLocation(new OrderLocation(orderLocationResult));
                    }
                }

                if(orderLocationListAdapter.setOrderLocationResults(selectedOrderLocations)) {

                    updateMenuPager(orderLocationListAdapter.getSelectedOrderLocationId());
                    Log.i(this.getClass().getSimpleName(), "Updating menu pager");
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
    }

    public void createMenuPager(OrderResult orderResult) {

        ViewPager menuPager = (ViewPager) findViewById(R.id.menu_view_pager);
        orderMenuAdapter = new OrderMenuAdapter(getSupportFragmentManager());
        orderMenuAdapter.setOrderResult(orderResult.orderLocationResults.get(0).menus);
        menuPager.setAdapter(orderMenuAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.menu_tab_layout);
        tabLayout.setupWithViewPager(menuPager);
        menuPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }

    public void updateMenuPager(int orderLocationId) {
        Log.i(Ordering.class.getSimpleName(), "Invoking menuPager update "+orderLocationId);


        orderViewModel.getOrderLocationById(orderLocationId).observe(this, new Observer<OrderLocationResult>() {
            @Override
            public void onChanged(@Nullable OrderLocationResult orderLocationResult) {

                if(orderMenuAdapter != null && orderLocationResult != null && orderLocationResult.menus != null && orderLocationResult.menus.size() > 0) {

                    orderMenuAdapter.setOrderResult(orderLocationResult.menus);
                    orderMenuAdapter.notifyDataSetChanged();

                    Log.i(Ordering.class.getSimpleName(), "Order location "+orderLocationResult.getId()+" updated");
                }
            }
        });

    }
}
