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
//    private OrderTransaction orderTransaction;

    public OrderRepository(Context context) {
        this.orderDao = LocalDatabase.getInstance(context).orderDao();
//        this.orderTransaction = LocalDatabase.getInstance(context).orderTransaction();
    }

    public LiveData<List<OrderResult>> getLiveOrders() {
        return orderDao.getLiveOrders();
    }

//    public int createOrder(Order order) {
//        return (int)orderDao.insert(order);
//    }
//
//    public void orderTransaction(String tableName) {
//        orderTransaction.orderTransaction(tableName);
//    }
    //retrieves Order from the OrderDao using the Order id
//    public Order retrieveOrder(int id) {
//        this.orderDao.retrieve(id);
//    }

}
