package dockit.com.app.dockit.Entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by michael on 01/08/18.
 */

@Entity(tableName = "menu_item_template")
public class MenuItemTemplate {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int menuSectionTemplateId;
    private String description;
    private String ingredients;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMenuSectionTemplateId() {
        return menuSectionTemplateId;
    }

    public void setMenuSectionTemplateId(int menuSectionTemplateId) {
        this.menuSectionTemplateId = menuSectionTemplateId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }
}
