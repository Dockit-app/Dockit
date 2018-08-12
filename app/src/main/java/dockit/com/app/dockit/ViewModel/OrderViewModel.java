package dockit.com.app.dockit.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.support.annotation.NonNull;

import java.util.List;

import dockit.com.app.dockit.Data.Dao.OrderTransaction;
import dockit.com.app.dockit.Data.LocalDatabase;
import dockit.com.app.dockit.Entity.OrderLocation;
import dockit.com.app.dockit.Entity.Result.OrderLocationResult;
import dockit.com.app.dockit.Entity.Result.OrderResult;
import dockit.com.app.dockit.Repository.OrderLocationRepository;
import dockit.com.app.dockit.Repository.OrderRepository;
import dockit.com.app.dockit.Tasks.CreateOrderAsync;
import dockit.com.app.dockit.Tasks.CreateOrderLocationTransactionAsync;
import dockit.com.app.dockit.Tasks.ResultHandler;

/**
 * Created by michael on 24/07/18.
 */

public class OrderViewModel extends AndroidViewModel {

    private LiveData<List<OrderResult>> liveOrderResults;
    private int maxOrderLocations = 50; //TODO: Persist in DB
    private int orderId = 1;
    private MutableLiveData<OrderLocation> liveSelectedOrderLocation;

    private OrderRepository orderRepository;
    private OrderLocationRepository orderLocationRepository;
    private CreateOrderAsync createOrderAsync;
    private OrderLocation createdOrderLocation;
    private OrderTransaction orderTransaction;

    public OrderViewModel(@NonNull Application application) {
        super(application);

        this.orderRepository = new OrderRepository(application);
        this.orderLocationRepository = new OrderLocationRepository(application);

        this.createOrderAsync = new CreateOrderAsync(application);

        liveOrderResults = orderRepository.getLiveOrders();

        liveSelectedOrderLocation = new MutableLiveData();

        orderTransaction = LocalDatabase.getInstance(application).orderTransaction();
    }


    public LiveData<List<OrderLocationResult>> getLiveOrderLocationsByOrderId(int orderId) {
        return orderLocationRepository.getLiveByOrderId(orderId);
    }

    public LiveData<List<OrderResult>> getLiveOrderResults() { return liveOrderResults;}

    public void createOrder(String table, ResultHandler resultHandler) {
        createOrderAsync.setResultHandler(resultHandler);
        createOrderAsync.execute(table);
    }

    public void createOrderLocation(Context context, OrderLocation orderLocation) {

        OrderLocation oldOrderLocation = liveSelectedOrderLocation.getValue();
        oldOrderLocation.setSelected(0);
        orderLocationRepository.update(oldOrderLocation);

        liveSelectedOrderLocation.setValue(orderLocation);
        new CreateOrderLocationTransactionAsync(context).execute(orderLocation);
    }

    public void updateOrderLocation(OrderLocation orderLocation) {

        OrderLocation oldOrderLocation = liveSelectedOrderLocation.getValue();
        oldOrderLocation.setSelected(0);
        orderLocationRepository.update(oldOrderLocation);

        liveSelectedOrderLocation.setValue(orderLocation);
        orderLocationRepository.update(orderLocation);
    }

    public int getOrderLocationAmount() {
        return  maxOrderLocations;
    }


    public LiveData<OrderLocation> getLiveSelectedOrderLocation() {
        return liveSelectedOrderLocation;
    }

    public LiveData<OrderLocationResult> getOrderLocationById(int id) {
        return orderLocationRepository.getLiveById(id);
    }

    public void setSelectedOrderLocation(OrderLocation selectedOrderLocation) {
        this.liveSelectedOrderLocation.setValue(selectedOrderLocation);
    }
}
