package dockit.com.app.dockit.Repository;

import android.content.Context;

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


}
