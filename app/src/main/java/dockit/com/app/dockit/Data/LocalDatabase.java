package dockit.com.app.dockit.Data;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.support.annotation.NonNull;

import dockit.com.app.dockit.Data.Dao.IngredientItemDao;
import dockit.com.app.dockit.Data.Dao.IngredientItemTemplateDao;
import dockit.com.app.dockit.Data.Dao.MandatoryItemDao;
import dockit.com.app.dockit.Data.Dao.MandatoryItemTemplateDao;
import dockit.com.app.dockit.Data.Dao.MenuSectionTemplateDao;
import dockit.com.app.dockit.Data.Dao.OptionalItemDao;
import dockit.com.app.dockit.Data.Dao.OptionalItemTemplateDao;
import dockit.com.app.dockit.Data.Dao.OrderTransaction;
import dockit.com.app.dockit.Data.Dao.MenuDao;
import dockit.com.app.dockit.Data.Dao.MenuItemDao;
import dockit.com.app.dockit.Data.Dao.MenuItemTemplateDao;
import dockit.com.app.dockit.Data.Dao.MenuTemplateDao;
import dockit.com.app.dockit.Data.Dao.OrderDao;
import dockit.com.app.dockit.Data.Dao.OrderLocationDao;
import dockit.com.app.dockit.Data.Dao.UserDao;
import dockit.com.app.dockit.Entity.IngredientItem;
import dockit.com.app.dockit.Entity.IngredientItemTemplate;
import dockit.com.app.dockit.Entity.MandatoryItem;
import dockit.com.app.dockit.Entity.MandatoryItemTemplate;
import dockit.com.app.dockit.Entity.Menu;
import dockit.com.app.dockit.Entity.MenuItem;
import dockit.com.app.dockit.Entity.MenuItemTemplate;
import dockit.com.app.dockit.Entity.MenuSection;
import dockit.com.app.dockit.Entity.MenuSectionTemplate;
import dockit.com.app.dockit.Entity.MenuTemplate;
import dockit.com.app.dockit.Entity.OptionalItem;
import dockit.com.app.dockit.Entity.OptionalItemTemplate;
import dockit.com.app.dockit.Entity.Order;
import dockit.com.app.dockit.Entity.OrderLocation;
import dockit.com.app.dockit.Entity.OrderLocationAmount;
import dockit.com.app.dockit.Entity.User;

/**
 * Created by michael on 24/07/18.
 */

@Database(entities = {
        Order.class, OrderLocation.class, OrderLocationAmount.class,
        Menu.class, MenuSection.class, MenuItem.class, MandatoryItem.class, OptionalItem.class, IngredientItem.class,
        MenuTemplate.class, MenuSectionTemplate.class, MenuItemTemplate.class,
        MandatoryItemTemplate.class, OptionalItemTemplate.class, IngredientItemTemplate.class, User.class }, version = 1)
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
                            .addCallback(insertUserCallback)
                            .build();
        }
        return instance;
    }

    public abstract OrderDao orderDao();
    public abstract OrderLocationDao orderLocationDao();

    public abstract MenuDao menuDao();
    public abstract MenuItemDao menuItemDao();
    public abstract MandatoryItemDao mandatoryItemDao();
    public abstract OptionalItemDao optionalItemDao();
    public abstract IngredientItemDao ingredientItemDao();

    public abstract MenuTemplateDao menuTemplateDao();
    public abstract MenuSectionTemplateDao menuSectionTemplateDao();
    public abstract MenuItemTemplateDao menuItemTemplateDao();
    public abstract MandatoryItemTemplateDao mandatoryItemTemplateDao();
    public abstract OptionalItemTemplateDao optionalItemTemplateDao();
    public abstract IngredientItemTemplateDao ingredientItemTemplateDao();


    public abstract OrderTransaction orderTransaction();

    public abstract UserDao userDao();

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

    //following code populate the user table for testing
    private static LocalDatabase.Callback insertUserCallback =
            new LocalDatabase.Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new InsertUserAsync(instance).execute();
                }
            };
}
