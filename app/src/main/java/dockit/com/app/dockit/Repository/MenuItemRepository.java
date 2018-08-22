package dockit.com.app.dockit.Repository;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import dockit.com.app.dockit.Data.Dao.MenuItemDao;
import dockit.com.app.dockit.Data.LocalDatabase;
import dockit.com.app.dockit.Entity.MenuItem;

/**
 * Created by michael on 08/08/18.
 */

public class MenuItemRepository {

    MenuItemDao menuItemDao;

    public MenuItemRepository(Context context) {
        menuItemDao = LocalDatabase.getInstance(context).menuItemDao();
    }

    public LiveData<List<MenuItem>> getLiveByMenuId(int menuId) {
        return menuItemDao.getLiveByMenuId(menuId);
    }

    public LiveData<List<MenuItem>> getLiveByOrderId(int orderId) {
        return menuItemDao.getLiveByOrderId(orderId);
    }

    public void createAllMenuItems(List<MenuItem> menuItems) {
        menuItemDao.insertAll(menuItems);
    }

    public void update(MenuItem menuItem) {
        new UpdateAsync(menuItemDao).execute(menuItem);
    }

    private class UpdateAsync extends AsyncTask<MenuItem, Void, Void> {

        MenuItemDao menuItemDao;

        public UpdateAsync(MenuItemDao menuItemDao) {
            this.menuItemDao = menuItemDao;
        }

        @Override
        protected Void doInBackground(MenuItem... menuItems) {
            menuItemDao.update(menuItems[0]);
            return null;
        }
    }
}
