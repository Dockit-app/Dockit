package dockit.com.app.dockit.Data.Dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;
import android.arch.persistence.room.Update;

import java.util.List;

import dockit.com.app.dockit.Entity.Order;
import dockit.com.app.dockit.Entity.Result.OrderResult;

/**
 * Created by michael on 26/07/18.
 */
@Dao
public interface OrderDao {

    @Query("Select * from order_item")
    LiveData<List<OrderResult>> getLiveOrders();

    @Query("Select * from order_item where id = :id")
    List<Order> getOrders(int id);

    @Query("select * from menu m " +
            "join order_location ol on m.locationId = ol.id " +
            "join order_item o on ol.orderId = o.id " +
            "where m.id = :menuId")
    LiveData<List<Order>> getLiveByMenuId(int menuId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Order order);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Order order);

    @Query ("Select id from order_item")
    LiveData<OrderResult> retrieve(int id);
}
