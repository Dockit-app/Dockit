package dockit.com.app.dockit.Tasks;

import android.content.Context;
import android.os.AsyncTask;

import dockit.com.app.dockit.Data.Dao.OrderTransaction;
import dockit.com.app.dockit.Data.LocalDatabase;
import dockit.com.app.dockit.Entity.OrderLocation;

/**
 * Created by michael on 08/08/18.
 */

public class CreateOrderLocationTransactionAsync extends AsyncTask<OrderLocation, Void, Void> {

    private OrderTransaction orderTransaction;

    public CreateOrderLocationTransactionAsync(Context context) {
        orderTransaction = LocalDatabase.getInstance(context).orderTransaction();
    }


    @Override
    protected Void doInBackground(OrderLocation... orderLocations) {
        orderTransaction.createOrderLocationTransaction(orderLocations[0]);
        return null;
    }
}
