package dockit.com.app.dockit.VIewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import dockit.com.app.dockit.Entity.Order;
import dockit.com.app.dockit.Repository.OrderRepository;

/**
 * Created by michael on 24/07/18.
 */

public class OrderViewModel extends AndroidViewModel {

    private OrderRepository orderRepository;
    private LiveData<Order> liveOrders;

    public OrderViewModel(@NonNull Application application) {
        super(application);

        this.orderRepository = new OrderRepository(application);
        this.liveOrders = orderRepository.getOrders();
    }

    public LiveData<Order> getLiveOrders() {
        return liveOrders;
    }
}
