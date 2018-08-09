package dockit.com.app.dockit.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.support.annotation.NonNull;

import java.util.List;

import dockit.com.app.dockit.Entity.Decorator.OrderLocationView;
import dockit.com.app.dockit.Entity.OrderLocation;
import dockit.com.app.dockit.Entity.Result.OrderLocationResult;
import dockit.com.app.dockit.Entity.Result.OrderResult;
import dockit.com.app.dockit.Repository.OrderLocationRepository;
import dockit.com.app.dockit.Repository.OrderRepository;
import dockit.com.app.dockit.Tasks.CreateOrderAsync;
import dockit.com.app.dockit.Tasks.CreateOrderLocationAsync;

/**
 * Created by michael on 24/07/18.
 */

public class OrderViewModel extends AndroidViewModel {

    private LiveData<List<OrderResult>> liveOrderResults;
    private int maxOrderLocations = 50; //TODO: Persist in DB
    private int orderId = 1;
    private MutableLiveData<OrderLocationView> liveSelectedOrderLocationView;

    private OrderRepository orderRepository;
    private OrderLocationRepository orderLocationRepository;
    private CreateOrderAsync createOrderAsync;

    public OrderViewModel(@NonNull Application application) {
        super(application);

        this.orderRepository = new OrderRepository(application);
        this.orderLocationRepository = new OrderLocationRepository(application);

        this.createOrderAsync = new CreateOrderAsync(application);

        liveOrderResults = orderRepository.getLiveOrders();

        liveSelectedOrderLocationView = new MutableLiveData();

//        OrderLocationView orderLocationView = new OrderLocationView();
//        orderLocationView.setId(1);
//        liveSelectedOrderLocationView.setValue(orderLocationView);
    }

    public LiveData<List<OrderLocationResult>> getOrderLocationsByOrderId(int orderId) {
        return orderLocationRepository.getLiveByOrderId(orderId);
    }

    public LiveData<List<OrderResult>> getLiveOrderResults() { return liveOrderResults;}

    public void createOrder(String table) {
        createOrderAsync.execute(table);
    }

    public void createOrderLocation(Context context, OrderLocationView orderLocationView) {

        orderLocationView.setOrderId(orderId);

        new CreateOrderLocationAsync(context).execute((OrderLocation)orderLocationView);

        updateOrderLocationView(orderLocationView);
    }

    public void updateOrderLocationView(OrderLocationView orderLocationView) {
        liveSelectedOrderLocationView.getValue().setSelected(false);
        liveSelectedOrderLocationView.setValue(orderLocationView);
    }

    public int getOrderLocationAmount() {
        return  maxOrderLocations;
    }


    public LiveData<OrderLocationView> getLiveSelectedOrderLocationView() {
        return liveSelectedOrderLocationView;
    }

    public OrderLocationResult getOrderLocationById(int id) {
        return orderLocationRepository.getLiveById(id);
    }

    public void setSelectedOrderLocation(OrderLocationView selectedOrderLocationView) {
        this.liveSelectedOrderLocationView.setValue(selectedOrderLocationView);
    }
}
