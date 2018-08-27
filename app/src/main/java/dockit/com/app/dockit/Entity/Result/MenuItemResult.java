package dockit.com.app.dockit.Entity.Result;

import android.arch.persistence.room.Relation;

import java.io.Serializable;
import java.util.List;

import dockit.com.app.dockit.Entity.IngredientItem;
import dockit.com.app.dockit.Entity.MandatoryItem;
import dockit.com.app.dockit.Entity.OptionalItem;

/**
 * Created by michael on 27/08/18.
 */

public class MenuItemResult implements Serializable {
    private int id;
    private int menuSectionId;
    private String description;
    private String ingredients;
    private boolean selected;
    private Integer counter;
    private String comments;


    @Relation(entity = MandatoryItem.class, parentColumn = "id", entityColumn = "menuItemId")
    public List<MandatoryItem> mandatoryItems;
    @Relation(entity = OptionalItem.class, parentColumn = "id", entityColumn = "menuItemId")
    public List<OptionalItem> optionalItems;
    @Relation(entity = IngredientItem.class, parentColumn = "id", entityColumn = "menuItemId")
    public List<IngredientItem> ingredientItems;

    public MenuItemResult() {}

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

        String ingredients = "";
        for(IngredientItem ingredientItem : ingredientItems) {
            ingredients = ingredients.concat(ingredientItem.getName());
            if(ingredientItems.indexOf(ingredientItem) != ingredientItems.size()-1) {
                ingredients = ingredients.concat(", ");
            }
        }

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
