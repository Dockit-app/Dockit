package dockit.com.app.dockit.Data;

import android.os.AsyncTask;

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

    private MenuTemplateDao menuTemplateDao;
    private MenuItemTemplateDao menuItemTemplateDao;
    private MenuSectionTemplateDao menuSectionTemplateDao;
    private MandatoryItemTemplateDao mandatoryItemTemplateDao;
    private OptionalItemTemplateDao optionalItemTemplateDao;
    private IngredientItemTemplateDao ingredientItemTemplateDao;

    InsertTemplatesAsync(LocalDatabase localDatabase) {
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
        //List<MenuTemplate> menuTemplates = new ArrayList<>();

        String[] menuNames = { "A LA CARTE", "DRINKS"};
        String[] sections = { "Starters", "Mains", "Desserts", "Sides" };
        String[] mandatoryItems = {"Well Done", "Medium", "Rare"};
        String[] optionalItems = {"Sauce on side", "Gluten", "Vegan", "Vegetarian", "Coeliac"};

        String[] menuItemDescriptionStarters = {"Heirloom Tomato", "Country Style Terrine", "Salt Cod Beignets", "No Selection"};
        String[] menuItemIngredientsStarters = {"Burrata, Mint, Puff Wild Rice", "Foie Gras, Walnuts, Grapes, Quince", "Caeser Aioli, Pickled Cucumber", ""};
        String[][] ingredientItemsStarters = {{"Burrata", "Mint", "Puff Wild Rice"}, {"Foie Gras", "Walnuts", "Grapes", "Quince"}, {"Caeser Aioli", "Pickled Cucumber"}, {""}};

        String[] menuItemDescriptionMains = {"Roast Potato Gnocchi", "Organic Salmon", "Beef Shortrib", "No Selection"};
        String[] menuItemIngredientsMains = {"Peas, Broad Beans, Courgettes, 5 Mile Town Goats Cheese", "Green Pea Risotto, Shellfish Emulsion, Girolles, Dill", "Salt Baked Celeriac, Ox Tongue, Walnut Mushroom Duxelle, Kale Salsa", ""};
        String[][] ingredientItemsMains = {{"Peas", "Broad Beans", "Courgettes", "5 Mile Town Goats Cheese"}, {"Green Pea Risotto", "Shellfish Emulsion", "Girolles", "Dill"},{"Salt Baked Celeriac", "Ox Tongue", "Walnut Mushroom Duxelle", "Kale Salsa"},{""}};

        String[] menuItemDescriptionDesserts = {"Coffee Crème Brûlée", "Chocolate Mousse", "Yoghurt Panna Cotta", "No Selection"};
        String[] menuItemIngredientsDesserts = {"Malt Hob Nob", "Honeycomb, Creme Fraiche", "Strawberries, Herbs", ""};
        String[][] ingredientItemsDeserts = {{"Malt Hob Nob"},{"Honeycomb", "Creme Fraiche"},{"Strawberries", "Herbs"},{""}};

        String[] menuItemDescriptionSides = {"New Potatoes", "Chargrilled Broccoli", "Fries", "Rocket Salad, Parmesan", "No Selection"};
        String[] menuItemIngredientsSides = {"New Potatoes, Salsa Verde", "Chargrilled Broccoli, Smoked Almonds", "Fries", "Rocket Salad, Parmesan", ""};
        String[][] ingredientItemsSides = {{"New Potatoes", "Salsa Verde"}, {"Chargrilled Broccoli", "Smoked Almonds"}, {"Fries"}, {"Rocket Salad", "Parmesan"},{""}};

        String[] sectionsDrinks = {"Sparkling Wine & Champagne", "Wines by the Glass & Pichet", "Dessert Wine"};
        String[] optionalItemsDrinks = {"Glass", "Bottle", "Pichet"};

        String[] menuItemDescriptionSparklingWineAndChampagne = {"Prosecco", "Brimoncourt Boutique House", "Moët & Chandon", "Moët & Chandon, Rosé", "Bollinger", "Cristal", "No Selection"};
        String[] menuItemIngredientsSparklingWineAndChampagne = {"NV, Spumante, Gran Duca, Veneto", "NV, Champange", "NV, Impérial, Brut, Champange", "NV, Champange", "NV, Spécial Cuvée", "2009, Louis Roederer", ""};
        String[][] ingredientItemsSparklingWineAndChampagne = {{"NV, Spumante, Gran Duca, Veneto"}, {"NV, Champange"}, {"NV, Impérial, Brut, Champange"}, {"NV, Champange"}, {"NV, Spécial Cuvée"}, {"2009, Louis Roederer"}, {""}};

        String[] menuItemDescriptionGlassAndPichet = {"Tulbagh Winery Chenin Blanc", "Peramor Verdejo", "Les Espirons Chardonnay", "Conte Amato Pinot Grigio", "No Selection"};
        String[] menuItemIngredientsGlassAndPichet = {"Swartland, Chenin Blanc", "Rueda, Verdejo", "Languedoc", "Veneto, Pinot Grigio", ""};
        String[][] ingredientItemsGlassAndPichet = {{"Swartland, Chenin Blanc"}, {"Rueda, Verdejo"}, {"Languedoc"}, {"Veneto, Pinot Grigio"}, {""}};

        String[] menuItemDescriptionDessertWine = {"Móinéir Strawberry Wine", "Muscat de Rivesaltes, Dom. Lafage", "Sauternes, Les Lions des Suduirat", "Cuvée Eiswien, Alois Kracher", "No Selection"};
        String[] menuItemIngredientsDessertWine = {"Wicklow, Strawberry", "2014, Roussillon, Muscat", "2010, Bordeaux, Sauternes", "2009, Austria, Array", ""};
        String[][] ingredientItemsDessertWine = {{"Wicklow, Strawberry"}, {"2014, Roussillon, Muscat"}, {"2010, Bordeaux, Sauternes"}, {"2009, Austria, Array"}, {""}};


        for (String menuName : menuNames) {
            MenuTemplate menuTemplate = new MenuTemplate();
            menuTemplate.setMenuName(menuName);

            Integer menuId = (int) menuTemplateDao.create(menuTemplate);

            if (menuName.equals("A LA CARTE")){
            for (String section : sections) {

                MenuSectionTemplate menuSectionTemplate = new MenuSectionTemplate();
                menuSectionTemplate.setMenuTemplateId(menuId);
                menuSectionTemplate.setName(section);
                Integer sectionId = (int) menuSectionTemplateDao.create(menuSectionTemplate);

                switch (section) {
                    case "Starters":
                        for (int j = 0; j < menuItemDescriptionStarters.length; j++) {
                            MenuItemTemplate menuItemTemplate = new MenuItemTemplate();
                            menuItemTemplate.setMenuSectionTemplateId(sectionId);
                            menuItemTemplate.setDescription(menuItemDescriptionStarters[j]);
                            menuItemTemplate.setIngredients(menuItemIngredientsStarters[j]);
                            int id = (int) menuItemTemplateDao.create(menuItemTemplate);

                            if (menuItemTemplate.getDescription().equals("Country Style Terrine")) {
                                for (String mandatoryItem : mandatoryItems) {
                                    MandatoryItemTemplate mandatoryItemTemplate = new MandatoryItemTemplate();
                                    mandatoryItemTemplate.setMenuItemTemplateId(id);
                                    mandatoryItemTemplate.setName(mandatoryItem);
                                    mandatoryItemTemplateDao.insert(mandatoryItemTemplate);
                                }
                            }

                            for (String optionalItem : optionalItems) {
                                OptionalItemTemplate optionalItemTemplate = new OptionalItemTemplate();
                                optionalItemTemplate.setMenuItemTemplateId(id);
                                optionalItemTemplate.setName(optionalItem);
                                optionalItemTemplateDao.insert(optionalItemTemplate);
                            }

                            for (String item : ingredientItemsStarters[j]) {
                                IngredientItemTemplate ingredientItemTemplate = new IngredientItemTemplate();
                                ingredientItemTemplate.setMenuItemTemplateId(id);
                                ingredientItemTemplate.setName(item);
                                ingredientItemTemplateDao.insert(ingredientItemTemplate);
                            }
                        }
                        break;

                    case "Mains":
                        for (int j = 0; j < menuItemDescriptionMains.length; j++) {
                            MenuItemTemplate menuItemTemplate = new MenuItemTemplate();
                            menuItemTemplate.setMenuSectionTemplateId(sectionId);
                            menuItemTemplate.setDescription(menuItemDescriptionMains[j]);
                            menuItemTemplate.setIngredients(menuItemIngredientsMains[j]);
                            int id = (int) menuItemTemplateDao.create(menuItemTemplate);

                            if (menuItemTemplate.getDescription().equals("Beef Shortrib")) {
                                for (String mandatoryItem : mandatoryItems) {
                                    MandatoryItemTemplate mandatoryItemTemplate = new MandatoryItemTemplate();
                                    mandatoryItemTemplate.setMenuItemTemplateId(id);
                                    mandatoryItemTemplate.setName(mandatoryItem);
                                    mandatoryItemTemplateDao.insert(mandatoryItemTemplate);
                                }
                            }

                            for (String optionalItem : optionalItems) {
                                OptionalItemTemplate optionalItemTemplate = new OptionalItemTemplate();
                                optionalItemTemplate.setMenuItemTemplateId(id);
                                optionalItemTemplate.setName(optionalItem);
                                optionalItemTemplateDao.insert(optionalItemTemplate);
                            }

                            for (String item : ingredientItemsMains[j]) {
                                IngredientItemTemplate ingredientItemTemplate = new IngredientItemTemplate();
                                ingredientItemTemplate.setMenuItemTemplateId(id);
                                ingredientItemTemplate.setName(item);
                                ingredientItemTemplateDao.insert(ingredientItemTemplate);
                            }
                        }
                        break;

                    case "Desserts":
                        for (int j = 0; j < menuItemDescriptionDesserts.length; j++) {
                            MenuItemTemplate menuItemTemplate = new MenuItemTemplate();
                            menuItemTemplate.setMenuSectionTemplateId(sectionId);
                            menuItemTemplate.setDescription(menuItemDescriptionDesserts[j]);
                            menuItemTemplate.setIngredients(menuItemIngredientsDesserts[j]);
                            int id = (int) menuItemTemplateDao.create(menuItemTemplate);

                            for (String optionalItem : optionalItems) {
                                OptionalItemTemplate optionalItemTemplate = new OptionalItemTemplate();
                                optionalItemTemplate.setMenuItemTemplateId(id);
                                optionalItemTemplate.setName(optionalItem);
                                optionalItemTemplateDao.insert(optionalItemTemplate);
                            }

                            for (String item : ingredientItemsDeserts[j]) {
                                IngredientItemTemplate ingredientItemTemplate = new IngredientItemTemplate();
                                ingredientItemTemplate.setMenuItemTemplateId(id);
                                ingredientItemTemplate.setName(item);
                                ingredientItemTemplateDao.insert(ingredientItemTemplate);
                            }
                        }
                        break;

                    case "Sides":
                        for (int j = 0; j < menuItemDescriptionSides.length; j++) {
                            MenuItemTemplate menuItemTemplate = new MenuItemTemplate();
                            menuItemTemplate.setMenuSectionTemplateId(sectionId);
                            menuItemTemplate.setDescription(menuItemDescriptionSides[j]);
                            menuItemTemplate.setIngredients(menuItemIngredientsSides[j]);
                            int id = (int) menuItemTemplateDao.create(menuItemTemplate);

                            for (String optionalItem : optionalItems) {
                                OptionalItemTemplate optionalItemTemplate = new OptionalItemTemplate();
                                optionalItemTemplate.setMenuItemTemplateId(id);
                                optionalItemTemplate.setName(optionalItem);
                                optionalItemTemplateDao.insert(optionalItemTemplate);
                            }

                            for (String item : ingredientItemsSides[j]) {
                                IngredientItemTemplate ingredientItemTemplate = new IngredientItemTemplate();
                                ingredientItemTemplate.setMenuItemTemplateId(id);
                                ingredientItemTemplate.setName(item);
                                ingredientItemTemplateDao.insert(ingredientItemTemplate);
                            }
                        }
                        break;
                }
            }
            }
            if (menuName.equals("DRINKS")){
                for (String section : sectionsDrinks) {

                    MenuSectionTemplate menuSectionTemplate = new MenuSectionTemplate();
                    menuSectionTemplate.setMenuTemplateId(menuId);
                    menuSectionTemplate.setName(section);
                    Integer sectionId = (int) menuSectionTemplateDao.create(menuSectionTemplate);

                    switch (section) {
                        case "Sparkling Wine & Champagne":
                            for (int j = 0; j < menuItemDescriptionSparklingWineAndChampagne.length; j++) {
                                MenuItemTemplate menuItemTemplate = new MenuItemTemplate();
                                menuItemTemplate.setMenuSectionTemplateId(sectionId);
                                menuItemTemplate.setDescription(menuItemDescriptionSparklingWineAndChampagne[j]);
                                menuItemTemplate.setIngredients(menuItemIngredientsSparklingWineAndChampagne[j]);
                                int id = (int) menuItemTemplateDao.create(menuItemTemplate);

                                for (String optionalItem : optionalItemsDrinks) {
                                    OptionalItemTemplate optionalItemTemplate = new OptionalItemTemplate();
                                    optionalItemTemplate.setMenuItemTemplateId(id);
                                    optionalItemTemplate.setName(optionalItem);
                                    optionalItemTemplateDao.insert(optionalItemTemplate);
                                }

                                for (String item : ingredientItemsSparklingWineAndChampagne[j]) {
                                    IngredientItemTemplate ingredientItemTemplate = new IngredientItemTemplate();
                                    ingredientItemTemplate.setMenuItemTemplateId(id);
                                    ingredientItemTemplate.setName(item);
                                    ingredientItemTemplateDao.insert(ingredientItemTemplate);
                                }
                            }
                            break;

                        case "Wines by the Glass & Pichet":
                            for (int j = 0; j < menuItemDescriptionGlassAndPichet.length; j++) {
                                MenuItemTemplate menuItemTemplate = new MenuItemTemplate();
                                menuItemTemplate.setMenuSectionTemplateId(sectionId);
                                menuItemTemplate.setDescription(menuItemDescriptionGlassAndPichet[j]);
                                menuItemTemplate.setIngredients(menuItemIngredientsGlassAndPichet[j]);
                                int id = (int) menuItemTemplateDao.create(menuItemTemplate);

                                for (String optionalItem : optionalItemsDrinks) {
                                    OptionalItemTemplate optionalItemTemplate = new OptionalItemTemplate();
                                    optionalItemTemplate.setMenuItemTemplateId(id);
                                    optionalItemTemplate.setName(optionalItem);
                                    optionalItemTemplateDao.insert(optionalItemTemplate);
                                }

                                for (String item : ingredientItemsGlassAndPichet[j]) {
                                    IngredientItemTemplate ingredientItemTemplate = new IngredientItemTemplate();
                                    ingredientItemTemplate.setMenuItemTemplateId(id);
                                    ingredientItemTemplate.setName(item);
                                    ingredientItemTemplateDao.insert(ingredientItemTemplate);
                                }
                            }
                            break;

                        case "Dessert Wine":
                            for (int j = 0; j < menuItemDescriptionDessertWine.length; j++) {
                                MenuItemTemplate menuItemTemplate = new MenuItemTemplate();
                                menuItemTemplate.setMenuSectionTemplateId(sectionId);
                                menuItemTemplate.setDescription(menuItemDescriptionDessertWine[j]);
                                menuItemTemplate.setIngredients(menuItemIngredientsDessertWine[j]);
                                int id = (int) menuItemTemplateDao.create(menuItemTemplate);

                                for (String optionalItem : optionalItemsDrinks) {
                                    OptionalItemTemplate optionalItemTemplate = new OptionalItemTemplate();
                                    optionalItemTemplate.setMenuItemTemplateId(id);
                                    optionalItemTemplate.setName(optionalItem);
                                    optionalItemTemplateDao.insert(optionalItemTemplate);
                                }

                                for (String item : ingredientItemsDessertWine[j]) {
                                    IngredientItemTemplate ingredientItemTemplate = new IngredientItemTemplate();
                                    ingredientItemTemplate.setMenuItemTemplateId(id);
                                    ingredientItemTemplate.setName(item);
                                    ingredientItemTemplateDao.insert(ingredientItemTemplate);
                                }
                            }
                            break;
                    }
                }
            }
        }
    }
}
