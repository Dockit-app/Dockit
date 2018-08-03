package dockit.com.app.dockit.Data.Dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
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

    @Query("Select * From order_location")
    LiveData<List<OrderLocation>> getAllLive();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<OrderLocation> orderLocations);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(OrderLocation orderLocation);
}
