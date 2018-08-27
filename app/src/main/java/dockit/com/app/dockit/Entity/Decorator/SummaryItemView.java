package dockit.com.app.dockit.Entity.Decorator;

import java.util.ArrayList;
import java.util.List;

import dockit.com.app.dockit.Entity.MenuItem;
import dockit.com.app.dockit.Entity.Result.MenuItemResult;
import dockit.com.app.dockit.Entity.Result.MenuSectionResult;

public class SummaryItemView extends MenuItemResult {

    private int count = 0;
    private String sectionName;
    private boolean isSection = false;
    private boolean isHighlighted = false;

    public List<MandatoryItemView> mandatoryItemViewList = new ArrayList<>();

    public SummaryItemView(MenuItemResult menuItem) {
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

    public boolean isSection() {
        return isSection;
    }

    public void setHighlighting(boolean highlighting) { isHighlighted = highlighting; }

    public boolean isHighlighted() { return isHighlighted; }

    public void setSection(boolean section) {
        isSection = section;
    }

    public String getSectionName() {
        return sectionName == null ? "" : sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }
}

