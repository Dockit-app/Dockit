package dockit.com.app.dockit.Data.Dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;

import java.util.List;

import dockit.com.app.dockit.Entity.Order;
import dockit.com.app.dockit.Entity.Result.OrderResult;

/**
 * Created by michael on 26/07/18.
 */
@Dao
public interface OrderDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Order order);

    @Query("Select * from order_item")
    LiveData<List<OrderResult>> getLiveOrders();

//  @Query ("Select id from order_item")
//  long retrieve(Order id);
}
