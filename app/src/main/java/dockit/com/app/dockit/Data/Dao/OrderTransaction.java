package dockit.com.app.dockit.Data.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;
import android.support.annotation.TransitionRes;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import dockit.com.app.dockit.Entity.Menu;
import dockit.com.app.dockit.Entity.MenuItem;
import dockit.com.app.dockit.Entity.MenuItemTemplate;
import dockit.com.app.dockit.Entity.Order;
import dockit.com.app.dockit.Entity.OrderLocation;
import dockit.com.app.dockit.Entity.Result.MenuTemplateResult;

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
    public abstract void createAllMenuItems(List<MenuItem> menuItems);

    @Transaction
    public void createOrderTransaction(String tableName) {
        Order order = new Order();
        order.setTable(tableName);
        int orderId = (int)createOrder(order);

        createOrderLocation(orderId, 1);
    }

    @Transaction
    public void createOrderLocationTransaction(int orderId, int orderLocationNumber) {
        createOrderLocation(orderId, orderLocationNumber);
    }

    private void createOrderLocation(int orderId, int orderLocationNumber) {

        OrderLocation orderLocation = new OrderLocation();
        orderLocation.setOrderId(orderId);
        orderLocation.setLocationNumber(orderLocationNumber);

        int locationId = (int)createOrderLocation(orderLocation);

        createMenus(locationId);
    }

    private void createMenus(int locationId) {

        List<MenuTemplateResult> menuTemplates = getAllMenuTemplates();

        for(MenuTemplateResult menuTemplateResult : menuTemplates) {

            Menu menu = new Menu();
            menu.setLocationId(locationId);
            menu.setMenuName(menuTemplateResult.getMenuName());

            int menuId = (int)createMenu(menu);
            createMenuItems(menuId, menuTemplateResult.menuItemTemplates);

            Log.d(this.getClass().getSimpleName(), "Created menu with id "+menuId);
        }

    }

    private void createMenuItems(int menuId, List<MenuItemTemplate> menuItemTemplates) {

        List<MenuItem> menuItems = new ArrayList<>();
        for(MenuItemTemplate menuItemTemplate : menuItemTemplates) {
            menuItems.add(new MenuItem(menuId, menuItemTemplate));
        }

        createAllMenuItems(menuItems);
    }
}
