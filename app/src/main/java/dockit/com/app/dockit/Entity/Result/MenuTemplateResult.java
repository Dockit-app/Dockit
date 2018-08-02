package dockit.com.app.dockit.Entity.Result;

import android.arch.persistence.room.Relation;

import java.util.List;

import dockit.com.app.dockit.Entity.MenuItemTemplate;

/**
 * Created by michael on 01/08/18.
 */

public class MenuTemplateResult {

    private Integer id;
    private String menuName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    @Relation(entity = MenuItemTemplate.class, parentColumn = "id", entityColumn = "menuTemplateId")
    public List<MenuItemTemplate> menuItemTemplates;
}
