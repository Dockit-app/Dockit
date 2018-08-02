package dockit.com.app.dockit.Entity.Result;

import android.arch.persistence.room.Relation;

import java.util.List;

import dockit.com.app.dockit.Entity.Menu;

/**
 * Created by michael on 01/08/18.
 */

public class OrderLocationResult {

    private Integer id;
    private Integer orderId;
    private Integer locationNumber;
    private String locationText;
    private boolean selected;

    @Relation(entity = Menu.class, parentColumn = "id", entityColumn = "locationId")
    public List<MenuResult> menus;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getLocationNumber() {
        return locationNumber;
    }

    public void setLocationNumber(Integer locationNumber) {
        this.locationNumber = locationNumber;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getLocationText() {
        return locationText;
    }

    public void setLocationText(String locationText) {
        this.locationText = locationText;
    }
}