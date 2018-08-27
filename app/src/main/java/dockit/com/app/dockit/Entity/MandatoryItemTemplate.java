package dockit.com.app.dockit.Entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by michael on 27/08/18.
 */

@Entity(tableName = "mandatory_item_template")
public class MandatoryItemTemplate {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int menuItemTemplateId;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMenuItemTemplateId() {
        return menuItemTemplateId;
    }

    public void setMenuItemTemplateId(int menuItemTemplateId) {
        this.menuItemTemplateId = menuItemTemplateId;
    }
}
