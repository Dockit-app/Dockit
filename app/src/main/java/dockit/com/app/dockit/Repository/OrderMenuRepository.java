package dockit.com.app.dockit.Repository;

import android.content.Context;

import java.util.List;

import dockit.com.app.dockit.Data.Dao.MenuDao;
import dockit.com.app.dockit.Data.Dao.MenuItemDao;
import dockit.com.app.dockit.Data.LocalDatabase;
import dockit.com.app.dockit.Entity.Menu;
import dockit.com.app.dockit.Entity.MenuItem;

/**
 * Created by michael on 27/07/18.
 */

public class OrderMenuRepository {


    MenuDao menuDao;
    MenuItemDao menuItemDao;

    public OrderMenuRepository(Context context) {
        menuDao = LocalDatabase.getInstance(context).menuDao();
        menuItemDao = LocalDatabase.getInstance(context).menuItemDao();
    }


    public int createMenu(Menu menu) {
        return (int) menuDao.insert(menu);
    }

    public void createAllMenuItems(List<MenuItem> menuItems) {
        menuItemDao.insertAll(menuItems);
    }

}
