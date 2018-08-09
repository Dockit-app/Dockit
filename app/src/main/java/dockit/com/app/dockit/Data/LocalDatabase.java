package dockit.com.app.dockit.Data;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.support.annotation.NonNull;

import dockit.com.app.dockit.Data.Dao.OrderTransaction;
import dockit.com.app.dockit.Data.Dao.MenuDao;
import dockit.com.app.dockit.Data.Dao.MenuItemDao;
import dockit.com.app.dockit.Data.Dao.MenuItemTemplateDao;
import dockit.com.app.dockit.Data.Dao.MenuTemplateDao;
import dockit.com.app.dockit.Data.Dao.OrderDao;
import dockit.com.app.dockit.Data.Dao.OrderLocationDao;
import dockit.com.app.dockit.Entity.Menu;
import dockit.com.app.dockit.Entity.MenuItem;
import dockit.com.app.dockit.Entity.MenuItemTemplate;
import dockit.com.app.dockit.Entity.MenuTemplate;
import dockit.com.app.dockit.Entity.Order;
import dockit.com.app.dockit.Entity.OrderLocation;
import dockit.com.app.dockit.Entity.OrderLocationAmount;

/**
 * Created by michael on 24/07/18.
 */

@Database(entities = { Order.class, OrderLocation.class, OrderLocationAmount.class, Menu.class, MenuItem.class, MenuTemplate.class, MenuItemTemplate.class }, version = 1)
@TypeConverters({Converters.class})
public abstract class LocalDatabase extends RoomDatabase {

    private static LocalDatabase instance;
    private final static int OrderLocationAmount = 50;

    public static LocalDatabase getInstance(final Context context) {
        if (instance == null) {

            instance =
                    Room.databaseBuilder(context.getApplicationContext(), LocalDatabase.class, "local_database")
                            .addCallback(cleanDatabaseAsync)
                            .addCallback(insertTemplatesCallback)
                            .addCallback(insertOrderLocationAmount)
                            .build();
        }
        return instance;
    }

    public abstract OrderDao orderDao();
    public abstract OrderLocationDao orderLocationDao();

    public abstract MenuDao menuDao();
    public abstract MenuItemDao menuItemDao();

    public abstract MenuTemplateDao menuTemplateDao();
    public abstract MenuItemTemplateDao menuItemTemplateDao();

    public abstract OrderTransaction orderTransaction();

    private static LocalDatabase.Callback insertTemplatesCallback =
        new LocalDatabase.Callback() {
            @Override
            public void onCreate(SupportSQLiteDatabase db) {
                super.onCreate(db);
                new InsertTemplatesAsync(instance).execute();
            }
        };

    private static LocalDatabase.Callback cleanDatabaseAsync =
        new LocalDatabase.Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
                new CleanDBAsync(instance).execute();
            }
    };

    private static  LocalDatabase.Callback insertOrderLocationAmount = new LocalDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new InsertOrderLocationAmountAsync(instance, OrderLocationAmount).execute();
        }
    };
}
