package dockit.com.app.dockit.Data.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;

import java.util.List;

import dockit.com.app.dockit.Entity.MenuItem;

/**
 * Created by michael on 01/08/18.
 */
@Dao
public interface MenuItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<MenuItem> menuItems);

}
