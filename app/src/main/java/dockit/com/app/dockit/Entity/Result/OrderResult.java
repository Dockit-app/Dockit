package dockit.com.app.dockit.Entity.Result;

import android.arch.persistence.room.Relation;

import java.util.List;

import dockit.com.app.dockit.Entity.Menu;
import dockit.com.app.dockit.Entity.OrderLocation;

/**
 * Created by michael on 01/08/18.
 */

public class OrderResult {

    private Integer id;
    private String table;
    private String comments;
    private Boolean counterSelection;

    @Relation(entity = OrderLocation.class, parentColumn = "id", entityColumn = "orderId")
    public List<OrderLocationResult> orderLocationResults;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
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
}
