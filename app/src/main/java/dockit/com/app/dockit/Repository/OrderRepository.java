package dockit.com.app.dockit.Repository;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import dockit.com.app.dockit.Data.Dao.CreateOrderTransaction;
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
    private CreateOrderTransaction createOrderTransaction;

    public OrderRepository(Context context) {
        this.orderDao = LocalDatabase.getInstance(context).orderDao();
        this.createOrderTransaction = LocalDatabase.getInstance(context).createOrderTransaction();
    }

    public LiveData<List<OrderResult>> getLiveOrders() {
        return orderDao.getLiveOrders();
    }

    public int createOrder(Order order) {
        return (int)orderDao.insert(order);
    }

    public void createOrderTransaction(String tableName) {
        createOrderTransaction.createOrder(tableName);
    }
}
