package dockit.com.app.dockit.Data.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import dockit.com.app.dockit.Entity.IngredientItem;
import dockit.com.app.dockit.Entity.IngredientItemTemplate;

/**
 * Created by michael on 27/08/18.
 */
@Dao
public interface IngredientItemTemplateDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(IngredientItemTemplate ingredientItemTemplate );

//    @Query("Select * from ingredient_item_template where menuItemTemplateId = :menuItemId")
//    List<IngredientItem> getByMenuItemId(int menuItemId);
}
