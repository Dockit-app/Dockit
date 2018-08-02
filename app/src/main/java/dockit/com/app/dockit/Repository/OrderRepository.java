package dockit.com.app.dockit.Repository;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import dockit.com.app.dockit.Data.Dao.OrderDao;
import dockit.com.app.dockit.Data.Dao.OrderLocationDao;
import dockit.com.app.dockit.Data.LocalDatabase;
import dockit.com.app.dockit.Entity.Order;
import dockit.com.app.dockit.Entity.Result.OrderResult;

/**
 * Created by michael on 24/07/18.
 */

public class OrderRepository {

    private OrderDao orderDao;

    public OrderRepository(Context context) {
        orderDao = LocalDatabase.getInstance(context).orderDao();
    }

    public LiveData<List<OrderResult>> getLiveOrders() {
        return orderDao.getLiveOrders();
    }

    public int createOrder(Order order) {
        return (int)orderDao.insert(order);
    }
}
