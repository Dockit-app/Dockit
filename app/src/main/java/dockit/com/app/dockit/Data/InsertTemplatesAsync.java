package dockit.com.app.dockit.Data;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import dockit.com.app.dockit.Data.Dao.IngredientItemTemplateDao;
import dockit.com.app.dockit.Data.Dao.MandatoryItemTemplateDao;
import dockit.com.app.dockit.Data.Dao.MenuItemTemplateDao;
import dockit.com.app.dockit.Data.Dao.MenuSectionTemplateDao;
import dockit.com.app.dockit.Data.Dao.MenuTemplateDao;
import dockit.com.app.dockit.Data.Dao.OptionalItemTemplateDao;
import dockit.com.app.dockit.Entity.IngredientItemTemplate;
import dockit.com.app.dockit.Entity.MandatoryItemTemplate;
import dockit.com.app.dockit.Entity.MenuItemTemplate;
import dockit.com.app.dockit.Entity.MenuSectionTemplate;
import dockit.com.app.dockit.Entity.MenuTemplate;
import dockit.com.app.dockit.Entity.OptionalItemTemplate;

/**
 * Created by michael on 01/08/18.
 */

public class InsertTemplatesAsync extends AsyncTask<Void, Void, Void> {

    MenuTemplateDao menuTemplateDao;
    MenuItemTemplateDao menuItemTemplateDao;
    MenuSectionTemplateDao menuSectionTemplateDao;
    MandatoryItemTemplateDao mandatoryItemTemplateDao;
    OptionalItemTemplateDao optionalItemTemplateDao;
    IngredientItemTemplateDao ingredientItemTemplateDao;

    public InsertTemplatesAsync(LocalDatabase localDatabase) {
        menuTemplateDao = localDatabase.menuTemplateDao();
        menuItemTemplateDao = localDatabase.menuItemTemplateDao();
        menuSectionTemplateDao = localDatabase.menuSectionTemplateDao();
        mandatoryItemTemplateDao = localDatabase.mandatoryItemTemplateDao();
        optionalItemTemplateDao = localDatabase.optionalItemTemplateDao();
        ingredientItemTemplateDao = localDatabase.ingredientItemTemplateDao();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        createMenuTemplate();
        return null;
    }

    private void createMenuTemplate() {
        List<MenuTemplate> menuTemplates = new ArrayList<>();

        String[] menuNames = { "A LA CARTE", "DRINKS"};
        String[] sections = { "Appetiser", "Mains", "Dessert", "Sides" };
        String[] menuItemDescription = {"Heirloom Tomato", "Country Style Terrine", "Salt Cod Beignets", "No Selection"};
        String[] menuItemIngredients = {"Burrata, Mint, Puff Wild Rice", "Foie Gras, Walnuts, Grapes, Quince", "Caeser Aioli, Pickled Cucumber", ""};
        String[] mandatoryItems = {"Well Done", "Medium", "Rare"};
        String[] optionalItems = {"Sauce on side", "Gluten", "Vegan", "Vegetarian", "Coeliac"};
        String[] ingredientItems = {"Foie Gras", "Walnuts", "Pickled Cucumber"};

        for(int i = 0; i < menuNames.length; i++) {
            MenuTemplate menuTemplate = new MenuTemplate();
            menuTemplate.setMenuName(menuNames[i]);

            Integer menuId = (int)menuTemplateDao.create(menuTemplate);

            for(int k = 0; k < sections.length; k++) {

                MenuSectionTemplate menuSectionTemplate = new MenuSectionTemplate();
                menuSectionTemplate.setMenuTemplateId(menuId);
                menuSectionTemplate.setName(sections[k]);
                Integer sectionId = (int)menuSectionTemplateDao.create(menuSectionTemplate);

                for (int j = 0; j < menuItemDescription.length; j++) {
                    MenuItemTemplate menuItemTemplate = new MenuItemTemplate();
                    menuItemTemplate.setMenuSectionTemplateId(sectionId);
                    menuItemTemplate.setDescription(menuItemDescription[j]);
                    menuItemTemplate.setIngredients(menuItemIngredients[j]);
                    int id = (int)menuItemTemplateDao.create(menuItemTemplate);

                    for(int l = 0; l < mandatoryItems.length; l++) {
                        MandatoryItemTemplate mandatoryItemTemplate = new MandatoryItemTemplate();
                        mandatoryItemTemplate.setMenuItemTemplateId(id);
                        mandatoryItemTemplate.setName(mandatoryItems[l]);
                        mandatoryItemTemplateDao.insert(mandatoryItemTemplate);
                    }

                    for(int m = 0; m < optionalItems.length; m++) {
                        OptionalItemTemplate optionalItemTemplate= new OptionalItemTemplate();
                        optionalItemTemplate.setMenuItemTemplateId(id);
                        optionalItemTemplate.setName(optionalItems[m]);
                        optionalItemTemplateDao.insert(optionalItemTemplate);
                    }

                    for(int n = 0; n < ingredientItems.length; n++) {
                        IngredientItemTemplate ingredientItemTemplate = new IngredientItemTemplate();
                        ingredientItemTemplate.setMenuItemTemplateId(id);
                        ingredientItemTemplate.setName(ingredientItems[n]);
                        ingredientItemTemplateDao.insert(ingredientItemTemplate);
                    }
                }

            }

        }
    }
}
