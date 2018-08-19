package dockit.com.app.dockit.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import dockit.com.app.dockit.Entity.Result.OrderResult;
import dockit.com.app.dockit.Repository.OrderRepository;
import dockit.com.app.dockit.Entity.Order;

public class OrderSummaryViewModel extends AndroidViewModel {
    private LiveData<List<OrderResult>> liveOrderResults;
    private int maxOrderLocations = 50; //TODO: Persist in DB
    private int orderId = 1;

    private OrderRepository orderRepository;

    public OrderSummaryViewModel(@NonNull Application application) {
        super(application);

        this.orderRepository = new OrderRepository(application);

        liveOrderResults = orderRepository.getLiveOrders();
    }


//    public Order RetrieveOrderInfo(int id) {
//        return orderRepository.retrieveOrder(id);
//    }
}
