package dockit.com.app.dockit.API.Order;

import android.content.Context;
import android.widget.ListAdapter;

import java.util.List;

import dockit.com.app.dockit.API.BaseApiClient;
import dockit.com.app.dockit.API.BaseCallback;
import dockit.com.app.dockit.Entity.Order;

/**
 * Created by michael on 24/07/18.
 */

public class OrderApiClient extends BaseApiClient<Order> {

    private OrderApiInterface orderApiInterface;

    public OrderApiClient(Context context) {
        super(context);

        orderApiInterface = getRetrofitCaller().create(OrderApiInterface.class);
    }

    public void getAll(BaseCallback<List<Order>> callback ) {

        executeGet(callback, orderApiInterface.getAll());
    }
}
