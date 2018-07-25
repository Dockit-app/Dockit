package dockit.com.app.dockit.Data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.content.res.Resources;

import dockit.com.app.dockit.Data.Dao.OrderLocationDao;
import dockit.com.app.dockit.Entity.Order;
import dockit.com.app.dockit.Entity.OrderLocation;

/**
 * Created by michael on 24/07/18.
 */

@Database(entities = { Order.class, OrderLocation.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class LocalDatabase extends RoomDatabase {

    private static LocalDatabase instance;
    private static Resources resources;
    private static String packageName;

    public static LocalDatabase getInstance(final Context context) {
        if (instance == null) {

            resources = context.getResources();
            packageName = context.getPackageName();

            instance =
                    Room.databaseBuilder(context.getApplicationContext(), LocalDatabase.class, "local_database")
                            .build();

        }
        return instance;
    }


    public abstract OrderLocationDao orderLocationDao();
}
