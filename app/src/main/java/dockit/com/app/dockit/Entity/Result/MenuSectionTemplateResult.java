package dockit.com.app.dockit.Entity.Result;

import android.arch.persistence.room.Relation;

import java.util.List;

import dockit.com.app.dockit.Entity.MenuItemTemplate;

/**
 * Created by michael on 14/08/18.
 */

public class MenuSectionTemplateResult {

    private Integer id;
    private Integer menuTemplateId;
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMenuTemplateId() {
        return menuTemplateId;
    }

    public void setMenuTemplateId(Integer menuTemplateId) {
        this.menuTemplateId = menuTemplateId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Relation(entity = MenuItemTemplate.class, parentColumn = "id", entityColumn = "menuSectionTemplateId")
    public List<MenuItemTemplateResult> menuItemTemplateList;

}
