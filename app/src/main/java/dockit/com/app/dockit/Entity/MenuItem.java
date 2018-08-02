package dockit.com.app.dockit.Entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by michael on 27/07/18.
 */

@Entity
public class MenuItem {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int menuId;
    private String description;
    private String ingredients;

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
}
