package dockit.com.app.dockit.Repository;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import dockit.com.app.dockit.Data.LocalDatabase;
import dockit.com.app.dockit.Entity.Order;

/**
 * Created by michael on 24/07/18.
 */

public class OrderRepository {

    LocalDatabase localDatabase;

    public OrderRepository(Context context) {
        localDatabase = LocalDatabase.getInstance(context);
    }

    public LiveData<Order> getOrders() {
        return null;
    }
}
