package dockit.com.app.dockit.Tasks;

import android.content.Context;
import android.os.AsyncTask;

import dockit.com.app.dockit.Data.Dao.OrderTransaction;
import dockit.com.app.dockit.Data.LocalDatabase;
import dockit.com.app.dockit.Entity.OrderLocation;

/**
 * Created by michael on 08/08/18.
 */

public class CreateOrderLocationAsync extends AsyncTask<OrderLocation, Void, Void> {

    private OrderTransaction orderTransaction;

    public CreateOrderLocationAsync(Context context) {
        orderTransaction = LocalDatabase.getInstance(context).orderTransaction();
    }

    @Override
    protected Void doInBackground(OrderLocation... orderLocations) {

        int orderId = orderLocations[0].getOrderId();
        int locationNumber = orderLocations[0].getLocationNumber();

        orderTransaction.createOrderLocationTransaction(orderId, locationNumber);

        return null;
    }
}
