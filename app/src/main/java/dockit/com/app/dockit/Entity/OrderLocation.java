package dockit.com.app.dockit.Entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import dockit.com.app.dockit.Entity.Result.OrderLocationResult;

/**
 * Created by michael on 25/07/18.
 */

@Entity(tableName = "order_location")
public class OrderLocation {

    public OrderLocation() {};

    public OrderLocation(OrderLocationResult orderLocationResult) {
        this.id = orderLocationResult.getId();
        this.orderId = orderLocationResult.getOrderId();
        this.locationNumber = orderLocationResult.getLocationNumber();
        this.locationText = orderLocationResult.getLocationText();
        this.selected = orderLocationResult.getSelected();
    }

    @PrimaryKey(autoGenerate = true)
    private Integer id;
    private Integer orderId;
    private Integer locationNumber;
    private String locationText;
    private Integer selected = 0;

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

    public String getLocationText() {
        return locationText;
    }

    public void setLocationText(String locationText) {
        this.locationText = locationText;
    }

    public Integer getSelected() {
        return selected;
    }

    public void setSelected(Integer selected) {
        this.selected = selected;
    }
}
