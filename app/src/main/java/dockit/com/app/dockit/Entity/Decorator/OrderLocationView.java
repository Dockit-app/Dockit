package dockit.com.app.dockit.Entity.Decorator;

import dockit.com.app.dockit.Entity.OrderLocation;

/**
 * Created by michael on 08/08/18.
 */

public class OrderLocationView extends OrderLocation {

    private boolean selected;
    private boolean created;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isCreated() {
        return created;
    }

    public void setCreated(boolean created) {
        this.created = created;
    }
}
