package dockit.com.app.dockit.Data.Dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import dockit.com.app.dockit.Entity.OrderLocation;
import dockit.com.app.dockit.Entity.OrderLocationAmount;
import dockit.com.app.dockit.Entity.Result.OrderLocationResult;

/**
 * Created by michael on 25/07/18.
 */
@Dao
public interface OrderLocationDao {

    @Query("Select * From order_location where orderId = :orderId")
    LiveData<List<OrderLocationResult>> getByOrderId(int orderId);

    @Query("Select * From order_location")
    LiveData<List<OrderLocationResult>> getAll();

    @Query("Select * From order_location Where id = :id")
    LiveData<OrderLocationResult> getById(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<OrderLocation> orderLocations);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(OrderLocation orderLocation);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    int update(OrderLocation orderLocation);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(OrderLocationAmount orderLocationAmount);
}
