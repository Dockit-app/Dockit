package dockit.com.app.dockit.Data.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import dockit.com.app.dockit.Entity.MenuSectionTemplate;

/**
 * Created by michael on 14/08/18.
 */
@Dao
public interface MenuSectionTemplateDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long create(MenuSectionTemplate menuSectionTemplate);

    @Query("Select * from menu_section_template")
    List<MenuSectionTemplate> getAll();

    @Query("Delete from menu_section_template")
    void deleteAll();
}
