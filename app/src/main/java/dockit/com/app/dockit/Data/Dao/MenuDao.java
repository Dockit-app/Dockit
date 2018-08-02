package dockit.com.app.dockit.Data.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;

import dockit.com.app.dockit.Entity.Menu;

/**
 * Created by michael on 27/07/18.
 */
@Dao
public interface MenuDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Menu menu);
}
