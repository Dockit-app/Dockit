package dockit.com.app.dockit.Entity.Decorator;

import dockit.com.app.dockit.Entity.OrderLocation;

/**
 * Created by michael on 08/08/18.
 */

public class OrderLocationView extends OrderLocation {

    private Integer selected;
    private Boolean created;

    public Integer getSelected() {
        return selected;
    }

    public void setSelected(Integer selected) {
        this.selected = selected;
    }

    public Boolean isCreated() {
        return created;
    }

    public void setCreated(Boolean created) {
        this.created = created;
    }
}
