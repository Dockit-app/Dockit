package dockit.com.app.dockit.Data.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

import dockit.com.app.dockit.Entity.OrderLocation;

/**
 * Created by michael on 25/07/18.
 */
@Dao
public interface OrderLocationDao {

    @Query("Select * From order_location Where id = :id")
    List<OrderLocation> getByOrderId(int id);
}
