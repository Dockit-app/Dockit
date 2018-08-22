package dockit.com.app.dockit.Data.Dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import dockit.com.app.dockit.Entity.MenuItem;

/**
 * Created by michael on 01/08/18.
 */
@Dao
public interface MenuItemDao {

    @Query("select * from menu_item where menuSectionId = :menuId")
    LiveData<List<MenuItem>> getLiveByMenuId(int menuId);

    @Query("select * from menu_item mi " +
            "join menu_section ms on mi.menuSectionId = ms.id " +
            "join menu m on ms.menuId = m.id " +
            "join order_location ol on m.locationId = ol.id " +
            "where ol.orderId = :orderId")
    LiveData<List<MenuItem>> getLiveByOrderId(int orderId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<MenuItem> menuItems);
    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(MenuItem menuItem);

}
