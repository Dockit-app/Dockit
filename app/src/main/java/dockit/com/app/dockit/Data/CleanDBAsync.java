package dockit.com.app.dockit.Data;

import android.os.AsyncTask;

import dockit.com.app.dockit.Data.Dao.MenuItemTemplateDao;
import dockit.com.app.dockit.Data.Dao.MenuTemplateDao;
import dockit.com.app.dockit.Data.LocalDatabase;
import dockit.com.app.dockit.Entity.MenuItemTemplate;
import dockit.com.app.dockit.Entity.MenuTemplate;

/**
 * Created by michael on 02/08/18.
 */

public class CleanDBAsync extends AsyncTask<Void, Void, Void> {

    private MenuTemplateDao menuTemplateDao;
    private MenuItemTemplateDao menuItemTemplateDao;

    public CleanDBAsync(LocalDatabase db) {
        this.menuTemplateDao = db.menuTemplateDao();
        this.menuItemTemplateDao = db.menuItemTemplateDao();
    }

    @Override
    protected Void doInBackground(Void... voids) {

        menuTemplateDao.deleteAll();

        menuItemTemplateDao.deleteAll();

        return null;
    }
}
