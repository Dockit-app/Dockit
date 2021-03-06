package dockit.com.app.dockit.Entity.Result;

import android.arch.persistence.room.Relation;

import java.io.Serializable;
import java.util.List;

import dockit.com.app.dockit.Entity.OrderLocation;

/**
 * Created by michael on 01/08/18.
 */

public class OrderResult implements Serializable {

    private Integer id;
    private String orderTable;
    private String comments;
    private Boolean counterSelection;
    private String timeStamp;

    @Relation(entity = OrderLocation.class, parentColumn = "id", entityColumn = "orderId")
    public List<OrderLocationResult> orderLocationResults;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderTable() {
        return orderTable;
    }

    public void setOrderTable(String orderTable) {
        this.orderTable = orderTable;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Boolean getCounterSelection() {
        return counterSelection;
    }

    public void setCounterSelection(Boolean counterSelection) {
        this.counterSelection = counterSelection;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
