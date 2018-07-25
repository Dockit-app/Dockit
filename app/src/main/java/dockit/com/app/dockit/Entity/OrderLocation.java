package dockit.com.app.dockit.Entity;

import android.arch.persistence.room.Entity;

/**
 * Created by michael on 25/07/18.
 */

@Entity
public class OrderLocation {

    private Integer id;
    private Integer orderId;
    private Integer locationNumber;

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
}
