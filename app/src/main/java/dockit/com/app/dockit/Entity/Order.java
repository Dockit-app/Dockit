package dockit.com.app.dockit.Entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by michael on 24/07/18.
 */

@Entity(tableName = "order_item")
public class Order {

    @PrimaryKey(autoGenerate = true)
    private Integer id;
    private String table;
    private String comments;
    private Boolean counterSelection = Boolean.TRUE;

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
