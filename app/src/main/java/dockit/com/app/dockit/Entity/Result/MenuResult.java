package dockit.com.app.dockit.Entity.Result;

import android.arch.persistence.room.Relation;

import java.io.Serializable;
import java.util.List;

import dockit.com.app.dockit.Entity.MenuSection;

/**
 * Created by michael on 01/08/18.
 */

public class MenuResult implements Serializable {

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

    @Relation(entity = MenuSection.class, parentColumn = "id", entityColumn = "menuId")
    public List<MenuSectionResult> menuSectionResults;
}
