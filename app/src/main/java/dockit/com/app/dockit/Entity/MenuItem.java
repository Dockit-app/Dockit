package dockit.com.app.dockit.Entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

import dockit.com.app.dockit.Entity.Result.MenuItemResult;
import dockit.com.app.dockit.Repository.MenuItemRepository;

/**
 * Created by michael on 27/07/18.
 */

@Entity(tableName = "menu_item")
public class MenuItem implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int menuSectionId;
    private String description;
    private String ingredients;
    private boolean selected;
    private Integer counter;
    private String comments;

    public MenuItem() {}

    public MenuItem(int menuSectionId, MenuItemTemplate menuItemTemplate) {
        this.menuSectionId = menuSectionId;
        this.description = menuItemTemplate.getDescription();
        this.ingredients = menuItemTemplate.getIngredients();
    }

    public MenuItem(MenuItemResult menuItemResult) {
        this.setId(menuItemResult.getId());
        this.setDescription(menuItemResult.getDescription());
        this.setMenuSectionId(menuItemResult.getMenuSectionId());
        this.setSelected(menuItemResult.isSelected());
        this.setComments(menuItemResult.getComments());
        this.setCounter(menuItemResult.getCounter());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMenuSectionId() {
        return menuSectionId;
    }

    public void setMenuSectionId(int menuSectionId) {
        this.menuSectionId = menuSectionId;
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

    public Integer getCounter() {
        return counter;
    }

    public void setCounter(Integer counter) {
        this.counter = counter;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
