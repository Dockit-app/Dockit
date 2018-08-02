package dockit.com.app.dockit.Data.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;

import java.util.List;

import dockit.com.app.dockit.Entity.MenuTemplate;
import dockit.com.app.dockit.Entity.Result.MenuTemplateResult;

/**
 * Created by michael on 01/08/18.
 */
@Dao
public interface MenuTemplateDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long create(MenuTemplate menuTemplate);

    @Query("select * from menu_template")
    @Transaction
    List<MenuTemplateResult> getAll();
}
