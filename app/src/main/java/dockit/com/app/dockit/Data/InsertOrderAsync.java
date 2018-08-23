package dockit.com.app.dockit.Data;

import android.os.AsyncTask;

import dockit.com.app.dockit.Data.Dao.OrderDao;
import dockit.com.app.dockit.Entity.Order;

public class InsertOrderAsync extends AsyncTask<Void, Void, Void> {

    private final OrderDao oDao;

    InsertOrderAsync(LocalDatabase db) {
        oDao = db.orderDao();
    }

    @Override
    protected Void doInBackground(final Void... params) {
        Order order = new Order();
        order.setId(1);
        order.setOrderTable("1");
        order.setComments("comment");
        order.setCounterSelection(true);
        oDao.insert(order);
        Order order2 = new Order();
        order2.setId(2);
        order2.setOrderTable("1A");
        order2.setComments("comment2");
        order2.setCounterSelection(true);
        oDao.insert(order2);
        return null;
    }
}
