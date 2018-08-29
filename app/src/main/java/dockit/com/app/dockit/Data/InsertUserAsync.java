package dockit.com.app.dockit.Data;

import android.os.AsyncTask;

import dockit.com.app.dockit.Data.Dao.UserDao;
import dockit.com.app.dockit.Entity.User;

public class InsertUserAsync extends AsyncTask<Void, Void, Void> {

    private final UserDao uDao;

    InsertUserAsync(LocalDatabase db) {
        uDao = db.userDao();
    }

    @Override
    protected Void doInBackground(final Void... params) {
        uDao.deleteAll();
        User user = new User("1","12","Peter");
        uDao.insert(user);
        User user2 = new User("2","54321","Deirdre");
        uDao.insert(user2);
        return null;
    }
}
