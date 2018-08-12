package dockit.com.app.dockit.Repository;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import dockit.com.app.dockit.Data.Dao.OrderLocationDao;
import dockit.com.app.dockit.Data.LocalDatabase;
import dockit.com.app.dockit.Entity.OrderLocation;
import dockit.com.app.dockit.Entity.Result.OrderLocationResult;

/**
 * Created by michael on 25/07/18.
 */

public class OrderLocationRepository {

    private LocalDatabase localDatabase;
    private OrderLocationDao orderLocationDao;

    public OrderLocationRepository(Context context) {
        this.localDatabase = LocalDatabase.getInstance(context);
        orderLocationDao = localDatabase.orderLocationDao();
    }

    public LiveData<List<OrderLocationResult>> getLiveByOrderId(int orderId) {
        return orderLocationDao.getByOrderId();
    }

    public LiveData<OrderLocationResult> getLiveById(int id) {
        return orderLocationDao.getById(id);
    }


    public void update(OrderLocation orderLocation) {

        new UpdateItemsAsync(orderLocationDao).execute(orderLocation);
    }

    private static class UpdateItemsAsync extends AsyncTask<OrderLocation, Void, Void> {

        private OrderLocationDao orderLocationDao;

        UpdateItemsAsync(OrderLocationDao orderLocationDao) {
            this.orderLocationDao = orderLocationDao;
        }

        @Override
        protected Void doInBackground(OrderLocation... orderLocations) {
            orderLocationDao.update(orderLocations[0]);
            return null;
        }
    }


}
