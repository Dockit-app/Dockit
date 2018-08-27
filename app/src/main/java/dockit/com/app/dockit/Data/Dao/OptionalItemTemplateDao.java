package dockit.com.app.dockit.Data.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;

import dockit.com.app.dockit.Entity.OptionalItemTemplate;

/**
 * Created by michael on 27/08/18.
 */
@Dao
public interface OptionalItemTemplateDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(OptionalItemTemplate optionalItemTemplate);
}
