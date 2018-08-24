package dockit.com.app.dockit.Entity.Decorator;

import dockit.com.app.dockit.Entity.MenuItem;
import dockit.com.app.dockit.Entity.Result.MenuSectionResult;

public class SummaryItemView extends MenuItem{

    private int count = 0;

    public SummaryItemView(MenuItem menuItem) {
        this.setId(menuItem.getId());
        this.setDescription(menuItem.getDescription());
        this.setIngredients(menuItem.getIngredients());
        this.setMenuSectionId(menuItem.getMenuSectionId());
        this.setSelected(menuItem.isSelected());
        this.setCounter(menuItem.getCounter());
        this.setComments(menuItem.getComments());
        this.count = 1;
    }

    public SummaryItemView(MenuSectionResult menuSectionResult) {
        this.setSection(true);
        this.setDescription(menuSectionResult.getName());
    }

    public int getCount() { return count; }

    public void incrementCount() { count += 1; }

    //Not useful now, but may be down the line
    public void decrementCount() { count -= 1; }

    private boolean isSection = false;

    public boolean isSection() {
        return isSection;
    }

    public void setSection(boolean section) {
        isSection = section;
    }
}
