package dockit.com.app.dockit.Entity.Result;

import android.arch.persistence.room.Relation;

import java.io.Serializable;
import java.util.List;

import dockit.com.app.dockit.Entity.MenuItem;

/**
 * Created by michael on 14/08/18.
 */

public class MenuSectionResult implements Serializable {
    private Integer id;
    private Integer menuId;
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Relation(entity = MenuItem.class, parentColumn = "id", entityColumn = "menuSectionId")
    public List<MenuItem> menuItemList;
}
