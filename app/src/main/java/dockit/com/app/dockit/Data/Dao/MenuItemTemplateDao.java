package dockit.com.app.dockit.Data.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import dockit.com.app.dockit.Entity.MenuItem;
import dockit.com.app.dockit.Entity.MenuItemTemplate;

/**
 * Created by michael on 01/08/18.
 */

@Dao
public interface MenuItemTemplateDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void create(MenuItemTemplate menuItemTemplate);

    @Query("Select * from menu_item_template")
    List<MenuItemTemplate> getAll();
}
