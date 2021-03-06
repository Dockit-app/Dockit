package dockit.com.app.dockit.Entity.Decorator;

import dockit.com.app.dockit.Entity.MenuItem;
import dockit.com.app.dockit.Entity.Result.MenuItemResult;
import dockit.com.app.dockit.Entity.Result.MenuSectionResult;

/**
 * Created by michael on 14/08/18.
 */

public class MenuItemView extends MenuItemResult {

    public MenuItemView(MenuItemResult menuItem) {
        this.setId(menuItem.getId());
        this.setDescription(menuItem.getDescription());
        this.setIngredients(menuItem.getIngredients());
        this.setMenuSectionId(menuItem.getMenuSectionId());
        this.setSelected(menuItem.isSelected());
        this.setCounter(menuItem.getCounter());
        this.setComments(menuItem.getComments());
        this.mandatoryItems = menuItem.mandatoryItems;
        this.optionalItems = menuItem.optionalItems;
        this.ingredientItems = menuItem.ingredientItems;
    }

    public MenuItemView(MenuSectionResult menuSectionResult) {
        this.setSection(true);
        this.setDescription(menuSectionResult.getName());
    }

    private boolean isSection = false;

    public boolean isSection() {
        return isSection;
    }

    public void setSection(boolean section) {
        isSection = section;
    }
}
