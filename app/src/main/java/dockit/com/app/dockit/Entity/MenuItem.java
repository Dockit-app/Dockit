package dockit.com.app.dockit.Entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

/**
 * Created by michael on 27/07/18.
 */

@Entity(tableName = "menu_item")
public class MenuItem implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int menuId;
    private String description;
    private String ingredients;
    private boolean selected;

    public MenuItem() {}

    public MenuItem(int menuId, MenuItemTemplate menuItemTemplate) {
        this.menuId = menuId;
        this.description = menuItemTemplate.getDescription();
        this.ingredients = menuItemTemplate.getIngredients();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
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

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
