package dockit.com.app.dockit.Tasks;

import android.content.Context;
import android.os.AsyncTask;

import dockit.com.app.dockit.Data.Dao.OrderTransaction;
import dockit.com.app.dockit.Data.LocalDatabase;
import dockit.com.app.dockit.Entity.Order;
import dockit.com.app.dockit.Entity.OrderLocation;
import dockit.com.app.dockit.Repository.OrderRepository;

/**
 * Created by michael on 08/08/18.
 */

public class CreateOrderLocationTransactionAsync extends AsyncTask<OrderLocation, Void, Void> {

    private OrderTransaction orderTransaction;
    private OrderRepository orderRepository;

    public CreateOrderLocationTransactionAsync(Context context) {
        orderTransaction = LocalDatabase.getInstance(context).orderTransaction();
        orderRepository = new OrderRepository(context);
    }


    @Override
    protected Void doInBackground(OrderLocation... orderLocations) {
        orderTransaction.createOrderLocationTransaction(orderLocations[0]);

        Order order = orderRepository.getById(orderLocations[0].getOrderId()).get(0);
        order.setCounterSelection(false);
        orderRepository.update(order);

        return null;
    }
}
