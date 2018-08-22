package dockit.com.app.dockit.Repository;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import java.util.List;

import dockit.com.app.dockit.Data.Dao.OrderTransaction;
import dockit.com.app.dockit.Data.Dao.OrderDao;
import dockit.com.app.dockit.Data.LocalDatabase;
import dockit.com.app.dockit.Entity.Order;
import dockit.com.app.dockit.Entity.Result.OrderResult;

/**
 * Created by michael on 24/07/18.
 */

public class OrderRepository {

    private OrderDao orderDao;

    public OrderRepository(Context context) {
        this.orderDao = LocalDatabase.getInstance(context).orderDao();
    }

    public LiveData<List<OrderResult>> getLiveOrders() {
        return orderDao.getLiveOrders();
    }

    public List<Order> getById(int id) {
        List<Order> order = orderDao.getOrders(id);

        return order;
    }

    public LiveData<List<Order>> getLiveByMenuId(int menuId) {
        return orderDao.getLiveByMenuId(menuId);
    }

    public void update(Order order) {
        orderDao.update(order);
    }
}
