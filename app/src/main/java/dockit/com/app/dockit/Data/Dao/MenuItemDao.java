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

    @Query("select * from menu_item where menuId = :menuId")
    LiveData<List<MenuItem>> getLiveByMenuId(int menuId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<MenuItem> menuItems);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(MenuItem menuItem);

}
