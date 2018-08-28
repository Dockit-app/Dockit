package dockit.com.app.dockit.Repository;


import android.arch.lifecycle.LiveData;
import android.content.Context;

import java.util.List;

import dockit.com.app.dockit.Data.Dao.OrderDao;
import dockit.com.app.dockit.Data.LocalDatabase;
import dockit.com.app.dockit.Entity.Order;
import dockit.com.app.dockit.Entity.Result.OrderResult;

//Functions that connect to database
public class TableRepository {

    private OrderDao orderDao;

    public TableRepository(Context context) {
        this.orderDao = LocalDatabase.getInstance(context).orderDao();

    }

    public LiveData<List<Order>> getTablesWithInputNumber(String table) {
        return orderDao.getTablesNumbers(table);
    }

    public LiveData<List<Order>> getExistingTables() {
        return orderDao.getExistingTables();
    }

    public LiveData<List<OrderResult>> getOrder(String table){ return orderDao.getOrderByTable(table); }
}
