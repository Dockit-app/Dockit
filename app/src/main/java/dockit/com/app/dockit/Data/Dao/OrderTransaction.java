package dockit.com.app.dockit.Data.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import dockit.com.app.dockit.Entity.Menu;
import dockit.com.app.dockit.Entity.MenuItem;
import dockit.com.app.dockit.Entity.MenuItemTemplate;
import dockit.com.app.dockit.Entity.MenuSection;
import dockit.com.app.dockit.Entity.Order;
import dockit.com.app.dockit.Entity.OrderLocation;
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
    public abstract void createAllMenuItems(List<MenuItem> menuItems);

    @Transaction
    public int createOrderTransaction(String tableName) {
        Order order = new Order();
        order.setTable(tableName);
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

            createMenuItems(sectionId, menuSectionTemplateResult.menuItemTemplateList);
        }

    }

    private void createMenuItems(int sectionId, List<MenuItemTemplate> menuItemTemplates) {

        List<MenuItem> menuItems = new ArrayList<>();
        for(MenuItemTemplate menuItemTemplate : menuItemTemplates) {
            menuItems.add(new MenuItem(sectionId, menuItemTemplate));
        }

        createAllMenuItems(menuItems);
    }
}
