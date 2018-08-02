package dockit.com.app.dockit.Entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;
import java.util.List;

/**
 * Created by michael on 26/07/18.
 */

@Entity(tableName = "menu")
public class Menu {

    @PrimaryKey(autoGenerate = true)
    private Integer id;
    private Integer locationId;
    private String menuName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

}
