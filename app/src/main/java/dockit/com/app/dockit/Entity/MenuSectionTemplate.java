package dockit.com.app.dockit.Entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by michael on 14/08/18.
 */
@Entity(tableName = "menu_section_template")
public class MenuSectionTemplate {

    @PrimaryKey(autoGenerate = true)
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
}
