package dockit.com.app.dockit.Data.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import dockit.com.app.dockit.Entity.IngredientItem;

/**
 * Created by michael on 27/08/18.
 */
@Dao
public interface IngredientItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(IngredientItem ingredientItem);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(IngredientItem ingredientItem);

}
