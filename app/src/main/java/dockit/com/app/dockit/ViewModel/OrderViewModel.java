package dockit.com.app.dockit.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import dockit.com.app.dockit.Entity.OrderLocation;
import dockit.com.app.dockit.Entity.Result.OrderResult;
import dockit.com.app.dockit.Repository.OrderLocationRepository;
import dockit.com.app.dockit.Repository.OrderRepository;
import dockit.com.app.dockit.Tasks.CreateOrderAsync;

/**
 * Created by michael on 24/07/18.
 */

public class OrderViewModel extends AndroidViewModel {

    private LiveData<List<OrderLocation>> liveOrderLocations;
    private LiveData<List<OrderResult>> liveOrderResults;
    private int maxOrderLocations = 50; //TODO: Persist in DB
    private int orderId;

    private OrderRepository orderRepository;
    private OrderLocationRepository orderLocationRepository;
    private CreateOrderAsync createOrderAsync;

    public OrderViewModel(@NonNull Application application) {
        super(application);

        this.orderRepository = new OrderRepository(application);
        this.orderLocationRepository = new OrderLocationRepository(application);
        this.createOrderAsync = new CreateOrderAsync(application);

        liveOrderResults = orderRepository.getLiveOrders();
        liveOrderLocations = orderLocationRepository.getAllLive();
    }

    public LiveData<List<OrderLocation>> getOrderLocations() { return liveOrderLocations; }

    public LiveData<List<OrderResult>> getLiveOrderResults() { return liveOrderResults;}

    public void createOrder(String table) {

        createOrderAsync.execute(table);
    }

}
