package dockit.com.app.dockit.Data.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Update;

import java.util.List;

import dockit.com.app.dockit.Entity.OptionalItem;

/**
 * Created by michael on 27/08/18.
 */
@Dao
public interface OptionalItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(OptionalItem optionalItem);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(OptionalItem optionalItem);
}
