package dockit.com.app.dockit.Repository;

import android.content.Context;

import java.util.List;

import dockit.com.app.dockit.Data.Dao.MenuItemTemplateDao;
import dockit.com.app.dockit.Data.Dao.MenuTemplateDao;
import dockit.com.app.dockit.Data.LocalDatabase;
import dockit.com.app.dockit.Entity.MenuItemTemplate;
import dockit.com.app.dockit.Entity.MenuTemplate;
import dockit.com.app.dockit.Entity.Result.MenuTemplateResult;

/**
 * Created by michael on 01/08/18.
 */

public class MenuTemplateRepository {

    public MenuTemplateDao menuTemplateDao;
    public MenuItemTemplateDao menuItemTemplateDao;

    public MenuTemplateRepository(Context context) {
        menuTemplateDao = LocalDatabase.getInstance(context).menuTemplateDao();
        menuItemTemplateDao = LocalDatabase.getInstance(context).menuItemTemplateDao();
    }

    public List<MenuTemplateResult> getAll() {
        return menuTemplateDao.getAll();
    }
}
