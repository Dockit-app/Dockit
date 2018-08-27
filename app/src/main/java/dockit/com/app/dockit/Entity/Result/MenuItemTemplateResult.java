package dockit.com.app.dockit.Entity.Result;

import android.arch.persistence.room.Relation;

import java.util.List;

import dockit.com.app.dockit.Entity.IngredientItemTemplate;
import dockit.com.app.dockit.Entity.MandatoryItemTemplate;
import dockit.com.app.dockit.Entity.OptionalItemTemplate;

/**
 * Created by michael on 27/08/18.
 */

public class MenuItemTemplateResult {
    private int id;
    private int menuSectionTemplateId;
    private String description;
    private String ingredients;

    @Relation(entity = MandatoryItemTemplate.class, parentColumn = "id", entityColumn = "menuItemTemplateId")
    public List<MandatoryItemTemplate> mandatoryItemTemplates;
    @Relation(entity = OptionalItemTemplate.class, parentColumn = "id", entityColumn = "menuItemTemplateId")
    public List<OptionalItemTemplate> optionalItemTemplates;
    @Relation(entity = IngredientItemTemplate.class, parentColumn = "id", entityColumn = "menuItemTemplateId")
    public List<IngredientItemTemplate> ingredientItemTemplates;

    public MenuItemTemplateResult() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMenuSectionTemplateId() {
        return menuSectionTemplateId;
    }

    public void setMenuSectionTemplateId(int menuSectionTemplateId) {
        this.menuSectionTemplateId = menuSectionTemplateId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

}
