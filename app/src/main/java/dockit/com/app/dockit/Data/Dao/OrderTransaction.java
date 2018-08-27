package dockit.com.app.dockit.Data.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import dockit.com.app.dockit.Entity.IngredientItem;
import dockit.com.app.dockit.Entity.IngredientItemTemplate;
import dockit.com.app.dockit.Entity.MandatoryItem;
import dockit.com.app.dockit.Entity.MandatoryItemTemplate;
import dockit.com.app.dockit.Entity.Menu;
import dockit.com.app.dockit.Entity.MenuItem;
import dockit.com.app.dockit.Entity.MenuSection;
import dockit.com.app.dockit.Entity.OptionalItem;
import dockit.com.app.dockit.Entity.OptionalItemTemplate;
import dockit.com.app.dockit.Entity.Order;
import dockit.com.app.dockit.Entity.OrderLocation;
import dockit.com.app.dockit.Entity.Result.MenuItemTemplateResult;
import dockit.com.app.dockit.Entity.Result.MenuTemplateResult;
import dockit.com.app.dockit.Entity.Result.MenuSectionTemplateResult;

/**
 * Created by michael on 02/08/18.
 */
@Dao
public abstract class OrderTransaction {

    @Query("select * from menu_template")
    public abstract List<MenuTemplateResult> getAllMenuTemplates();

    @Insert
    public abstract long createOrder(Order order);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract long createOrderLocation(OrderLocation orderLocation);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract long createMenu(Menu menu);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract long createMenuSection(MenuSection menuSection);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract long createMenuItem(MenuItem menuItems);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void createMandatoryItems(List<MandatoryItem> mandatoryItems);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void createOptionalItems(List<OptionalItem> optionalItems);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void createIngredientItems(List<IngredientItem> ingredientItems);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void createAllMenuItems(List<MenuItem> menuItems);

    @Transaction
    public int createOrderTransaction(String tableName) {
        Order order = new Order();
        order.setOrderTable(tableName);
        int orderId = (int)createOrder(order);

        return orderId;
    }

    @Transaction
    public OrderLocation createOrderLocationTransaction(OrderLocation orderLocation) {
        int orderLocationId = performCreateOrderLocation(orderLocation);
        //Stores result for handling only
        OrderLocation result = new OrderLocation();
        result.setId(orderLocationId);
        result.setOrderId(orderLocation.getOrderId());
        return result;
    }

    private int performCreateOrderLocation(OrderLocation orderLocation) {

        int locationId = (int)createOrderLocation(orderLocation);

        createMenus(locationId);

        return locationId;
    }

    private void createMenus(int locationId) {

        List<MenuTemplateResult> menuTemplates = getAllMenuTemplates();

        for(MenuTemplateResult menuTemplateResult : menuTemplates) {

            Menu menu = new Menu();
            menu.setLocationId(locationId);
            menu.setMenuName(menuTemplateResult.getMenuName());

            int menuId = (int)createMenu(menu);
            createMenuSection(menuId, menuTemplateResult.menuSectionTemplates);

            Log.i(this.getClass().getSimpleName(), "Created menu with id "+menuId+ " for location "+locationId);
        }

    }

    private void createMenuSection(int menuId, List<MenuSectionTemplateResult> menuSectionTemplateResults) {

        for(MenuSectionTemplateResult menuSectionTemplateResult : menuSectionTemplateResults) {
            MenuSection menuSection = new MenuSection();
            menuSection.setMenuId(menuId);
            menuSection.setName(menuSectionTemplateResult.getName());

            int sectionId = (int)createMenuSection(menuSection);

            createMenuItem(sectionId, menuSectionTemplateResult.menuItemTemplateList);
        }

    }

    private void createMenuItem(int sectionId, List<MenuItemTemplateResult> menuItemTemplates) {

        for(MenuItemTemplateResult menuItemTemplate : menuItemTemplates) {
            MenuItem menuItem = new MenuItem();
            menuItem.setMenuSectionId(sectionId);
            menuItem.setDescription(menuItemTemplate.getDescription());
            int id = (int)createMenuItem(menuItem);

            List<MandatoryItem> mandatoryItems = new ArrayList<>();
            for(MandatoryItemTemplate mandatoryItemTemplate : menuItemTemplate.mandatoryItemTemplates) {
                MandatoryItem mandatoryItem = new MandatoryItem();
                mandatoryItem.setName(mandatoryItemTemplate.getName());
                mandatoryItem.setMenuItemId(id);

                mandatoryItems.add(mandatoryItem);

            }
            createMandatoryItems(mandatoryItems);

            List<OptionalItem> optionalItems = new ArrayList<>();
            for(OptionalItemTemplate optionalItemTemplate : menuItemTemplate.optionalItemTemplates) {
                OptionalItem optionalItem = new OptionalItem();
                optionalItem.setName(optionalItemTemplate.getName());
                optionalItem.setMenuItemId(id);

                optionalItems.add(optionalItem);
            }
            createOptionalItems(optionalItems);

            List<IngredientItem> ingredientItems = new ArrayList<>();
            for(IngredientItemTemplate ingredientItemTemplate : menuItemTemplate.ingredientItemTemplates) {
                IngredientItem ingredientItem = new IngredientItem();
                ingredientItem.setName(ingredientItemTemplate.getName());
                ingredientItem.setMenuItemId(id);

                ingredientItems.add(ingredientItem);
            }
            createIngredientItems(ingredientItems);
        }
    }
}
