package dockit.com.app.dockit.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.support.annotation.NonNull;

import java.util.List;

import dockit.com.app.dockit.Entity.MenuItem;
import dockit.com.app.dockit.Entity.OrderLocation;
import dockit.com.app.dockit.Entity.Result.OrderLocationResult;
import dockit.com.app.dockit.Entity.Result.OrderResult;
import dockit.com.app.dockit.Repository.MenuItemRepository;
import dockit.com.app.dockit.Repository.OrderLocationRepository;
import dockit.com.app.dockit.Repository.OrderRepository;
import dockit.com.app.dockit.Tasks.CreateOrderAsync;
import dockit.com.app.dockit.Tasks.CreateOrderLocationTransactionAsync;
import dockit.com.app.dockit.Tasks.ResultHandler;

/**
 * Created by michael on 24/07/18.
 */

public class OrderViewModel extends AndroidViewModel {

    private int maxOrderLocations = 50; //TODO: Persist in DB
    private LiveData<List<OrderResult>> liveOrderResults;
    private MutableLiveData<OrderLocation> liveSelectedOrderLocation;
    private MutableLiveData<OrderResult> liveOrderResult;

    private OrderRepository orderRepository;
    private OrderLocationRepository orderLocationRepository;
    private MenuItemRepository menuItemRepository;
    private CreateOrderAsync createOrderAsync;

    public OrderViewModel(@NonNull Application application) {
        super(application);

        orderRepository = new OrderRepository(application);
        orderLocationRepository = new OrderLocationRepository(application);
        menuItemRepository = new MenuItemRepository(application);
        createOrderAsync = new CreateOrderAsync(application);

        liveOrderResults = orderRepository.getLiveAll();
        liveSelectedOrderLocation = new MutableLiveData();
        liveOrderResult = new MutableLiveData<>();
    }

    public LiveData<List<OrderResult>> getLiveOrderResults() {
        return liveOrderResults;
    }

    public LiveData<List<OrderResult>> getLiveOrderById(int id) {
        return orderRepository.getLiveById(id);
    }

    public LiveData<List<OrderLocationResult>> getLiveOrderLocationsByOrderId(int orderId) {
        return orderLocationRepository.getLiveByOrderId(orderId);
    }

    public LiveData<List<MenuItem>> getLiveMenuItemsByOrderId(int orderId) {
        //Used for counter implementation
        return menuItemRepository.getLiveByOrderId(orderId);
    }

    public void createOrder(String table, ResultHandler resultHandler) {
        createOrderAsync.setResultHandler(resultHandler);
        createOrderAsync.execute(table);
    }

    public void createOrderLocation(Context context, OrderLocation orderLocation) {

        OrderLocation oldOrderLocation = liveSelectedOrderLocation.getValue();
        oldOrderLocation.setSelected(0);

        liveSelectedOrderLocation.setValue(orderLocation);

        orderLocationRepository.update(oldOrderLocation);
        new CreateOrderLocationTransactionAsync(context).execute(orderLocation);
    }

    public void updateOrderLocation(OrderLocation orderLocation) {

        OrderLocation oldOrderLocation = liveSelectedOrderLocation.getValue();

        liveSelectedOrderLocation.setValue(orderLocation);

        oldOrderLocation.setSelected(0);
        orderLocationRepository.update(oldOrderLocation);
        orderLocationRepository.update(orderLocation);
    }

    public int getOrderLocationAmount() {
        return  maxOrderLocations;
    }

    public LiveData<OrderLocationResult> getOrderLocationById(int id) {
        return orderLocationRepository.getLiveById(id);
    }
    public LiveData<OrderLocation> getOrderLocation(int id) {
        return orderLocationRepository.getOrderLocation(id);
    }

    public LiveData<OrderLocation> getLiveSelectedOrderLocation() {
        return liveSelectedOrderLocation;
    }

    public void setLiveSelectedOrderLocation(OrderLocation selectedOrderLocation) {
        this.liveSelectedOrderLocation.setValue(selectedOrderLocation);
    }

    public MutableLiveData<OrderResult> getLiveOrderResult() {
        return liveOrderResult;
    }

    public void setLiveOrderResult(OrderResult liveOrderResult) {
        this.liveOrderResult.setValue(liveOrderResult);
    }

    public void removeAllObservers(LifecycleOwner lifecycleOwner, int orderId) {
        this.getLiveOrderResults().removeObservers(lifecycleOwner);
        this.getLiveOrderById(orderId).removeObservers(lifecycleOwner);
    }
}
