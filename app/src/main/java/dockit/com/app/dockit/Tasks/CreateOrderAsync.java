package dockit.com.app.dockit.Tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import dockit.com.app.dockit.Data.LocalDatabase;
import dockit.com.app.dockit.Entity.Menu;
import dockit.com.app.dockit.Entity.MenuItem;
import dockit.com.app.dockit.Entity.MenuItemTemplate;
import dockit.com.app.dockit.Entity.Order;
import dockit.com.app.dockit.Entity.OrderLocation;
import dockit.com.app.dockit.Entity.Result.MenuTemplateResult;
import dockit.com.app.dockit.Repository.MenuTemplateRepository;
import dockit.com.app.dockit.Repository.OrderLocationRepository;
import dockit.com.app.dockit.Repository.OrderMenuRepository;
import dockit.com.app.dockit.Repository.OrderRepository;

/**
 * Created by michael on 01/08/18.
 */

public class CreateOrderAsync extends AsyncTask<String, Void, Void> {

    private OrderRepository orderRepository;
    private OrderLocationRepository orderLocationRepository;
    private OrderMenuRepository orderMenuRepository;
    private MenuTemplateRepository menuTemplateRepository;
    private LocalDatabase db;

    public CreateOrderAsync(Context context) {
        this.orderRepository = new OrderRepository(context);
        this.orderLocationRepository = new OrderLocationRepository(context);
        this.orderMenuRepository = new OrderMenuRepository(context);
        this.menuTemplateRepository = new MenuTemplateRepository(context);
        this.db = LocalDatabase.getInstance(context);
    }

//    @Override
//    protected Void doInBackground(String... strings) {
//        db.beginTransaction();
//        createNewOrder(strings[0]);
//        db.endTransaction();
//        return null;
//    }

    @Override
    protected Void doInBackground(String... strings) {
        orderRepository.createOrderTransaction(strings[0]);
        return  null;
    }

    private void createNewOrder(String tableNumber) {
        Order order = new Order();
        order.setTable(tableNumber);
        int orderId = orderRepository.createOrder(order);

        createNewOrderLocation(orderId);
        db.setTransactionSuccessful();
    }

    private void createNewOrderLocation(int orderId) {

        OrderLocation orderLocation = new OrderLocation();
        orderLocation.setOrderId(orderId);
        orderLocation.setLocationNumber(1);

        int locationId = orderLocationRepository.create(orderLocation);

        createNewMenus(locationId);
    }

    private void createNewMenus(int locationId) {

        List<MenuTemplateResult> menuTemplates = menuTemplateRepository.getAll();

        for(MenuTemplateResult menuTemplateResult : menuTemplates) {

            Menu menu = new Menu();
            menu.setLocationId(locationId);
            menu.setMenuName(menuTemplateResult.getMenuName());

            int menuId = orderMenuRepository.createMenu(menu);
            createMenuItems(menuId, menuTemplateResult.menuItemTemplates);

            Log.d(this.getClass().getSimpleName(), "Created menu with id "+menuId);
        }

    }

    private void createMenuItems(int menuId, List<MenuItemTemplate> menuItemTemplates) {

        List<MenuItem> menuItems = new ArrayList<>();
        for(MenuItemTemplate menuItemTemplate : menuItemTemplates) {
            menuItems.add(new MenuItem(menuId, menuItemTemplate));
        }

        orderMenuRepository.createAllMenuItems(menuItems);
    }

//    private void createNewOrderLocations(int orderId) {
//
//        List<OrderLocation> orderLocations = new ArrayList<>();
//        for(int location = 1; location <= maxOrderLocations; location++) {
//            OrderLocation orderLocation = new OrderLocation();
//            orderLocation.setOrderId(orderId);
//            orderLocation.setLocationNumber(location);
//            if(location == 1) {
//                orderLocation.setLocationText(Integer.toString(location));
//            }
//            else {
//                orderLocation.setLocationText("+");
//            }
//
//            orderLocations.add(orderLocation);
//        }
//
//        orderLocationRepository.createOrderLocations(orderLocations);
//    }
}
