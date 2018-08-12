package dockit.com.app.dockit.Tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import javax.xml.transform.Result;

import dockit.com.app.dockit.Data.Dao.OrderTransaction;
import dockit.com.app.dockit.Data.LocalDatabase;
import dockit.com.app.dockit.Entity.OrderLocation;

/**
 * Created by michael on 01/08/18.
 */

public class CreateOrderAsync extends AsyncTask<String, OrderLocation, OrderLocation> {

    private OrderTransaction orderTransaction;
    private ResultHandler<OrderLocation> resultHandler;

    public CreateOrderAsync(Context context) {
        this.orderTransaction = LocalDatabase.getInstance(context).orderTransaction();
    }
    public void setResultHandler(ResultHandler<OrderLocation> resultHandler) {
        this.resultHandler = resultHandler;
    }

    @Override
    protected OrderLocation doInBackground(String... strings) {
        int orderId = (int)orderTransaction.createOrderTransaction(strings[0]);

        Log.d(this.getClass().getSimpleName(), "Created order with id "+orderId);

        OrderLocation orderLocation = new OrderLocation();
        orderLocation.setOrderId(orderId);
        orderLocation.setLocationNumber(1);
        orderLocation.setLocationText("1");

        return orderTransaction.createOrderLocationTransaction(orderLocation);
    }

    @Override
    protected void onPostExecute(OrderLocation result) {
        if(resultHandler != null) {
            resultHandler.onResult(result);
        }
    }
}
