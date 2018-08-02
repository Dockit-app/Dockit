package dockit.com.app.dockit.Repository;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import dockit.com.app.dockit.Data.Dao.OrderLocationDao;
import dockit.com.app.dockit.Data.LocalDatabase;
import dockit.com.app.dockit.Entity.OrderLocation;

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

    public List<OrderLocation> getByOrderId(int id) {
        return orderLocationDao.getByOrderId(id);
    }

    public int create(OrderLocation orderLocation) {
        return (int)orderLocationDao.insert(orderLocation);
    }

    public void createOrderLocations(List<OrderLocation> orderLocations) {
        new CreateItemsAsync(orderLocationDao).execute(orderLocations);
    }

    private static class CreateItemsAsync extends AsyncTask<List<OrderLocation>, Void, Void> {

        private OrderLocationDao orderLocationDao;

        CreateItemsAsync(OrderLocationDao orderLocationDao) {
            this.orderLocationDao = orderLocationDao;
        }

        @Override
        protected Void doInBackground(List<OrderLocation>... orderLocations) {
            orderLocationDao.insertAll(orderLocations[0]);
            return null;
        }
    }


}
