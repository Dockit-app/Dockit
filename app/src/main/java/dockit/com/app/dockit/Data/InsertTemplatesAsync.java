package dockit.com.app.dockit.Data;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import dockit.com.app.dockit.Data.Dao.MenuItemTemplateDao;
import dockit.com.app.dockit.Data.Dao.MenuTemplateDao;
import dockit.com.app.dockit.Entity.MenuItemTemplate;
import dockit.com.app.dockit.Entity.MenuTemplate;

/**
 * Created by michael on 01/08/18.
 */

public class InsertTemplatesAsync extends AsyncTask<Void, Void, Void> {

    MenuTemplateDao menuTemplateDao;
    MenuItemTemplateDao menuItemTemplateDao;

    public InsertTemplatesAsync(LocalDatabase localDatabase) {
        menuTemplateDao = localDatabase.menuTemplateDao();
        menuItemTemplateDao = localDatabase.menuItemTemplateDao();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        createMenuTemplate();
        return null;
    }

    private void createMenuTemplate() {
        List<MenuTemplate> menuTemplates = new ArrayList<>();

        String[] menuNames = { "Set Menu", "A La Carte", "€49.95 Menu" };
        String[] menuItemDescription = {"Heirloom Tomato", "Country Style Terrine", "Salt Cod Beignets"};
        String[] sections = { "Appetiser", "Mains", "Dessert", "Sides" };
        String[] menuItemIngredients = {"Burrata, Mint, Puff Wild Rice", "Foie Gras, Walnuts, Grapes, Quince", "Caeser Aioli, Pickled Cucumber"};

        for(int i = 0; i < menuNames.length; i++) {
            MenuTemplate menuTemplate = new MenuTemplate();
            menuTemplate.setMenuName(menuNames[i]);

            Integer menuId = (int)menuTemplateDao.create(menuTemplate);

            for(int k = 0; k < sections.length; k++) {

                MenuItemTemplate menuDescription = new MenuItemTemplate();
                menuDescription.setMenuTemplateId(menuId);
                menuDescription.setDescription(sections[k]);
                menuItemTemplateDao.create(menuDescription);

                for (int j = 0; j < menuItemDescription.length; j++) {
                    MenuItemTemplate menuItemTemplate = new MenuItemTemplate();
                    menuItemTemplate.setMenuTemplateId(menuId);
                    menuItemTemplate.setDescription(menuItemDescription[j]);
                    menuItemTemplate.setIngredients(menuItemIngredients[j]);
                    menuItemTemplateDao.create(menuItemTemplate);
                }

            }

        }
    }
}
