package dockit.com.app.dockit.Repository;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import dockit.com.app.dockit.Data.LocalDatabase;
import dockit.com.app.dockit.Entity.User;
import dockit.com.app.dockit.Tasks.ResultHandler;
import dockit.com.app.dockit.Data.Dao.UserDao;

public class UserRepository{

    private UserDao UserDao;

    public UserRepository(Context context) {
        this.UserDao = LocalDatabase.getInstance(context).userDao();

    }


    public void comparePin(String pin, ResultHandler resultHandler) {
        ComparePinAsync comparePinAsync = new ComparePinAsync();
        comparePinAsync.execute(pin);
        comparePinAsync.setResultHandler(resultHandler);
    }

    class ComparePinAsync extends AsyncTask<String, Void , List<User>> {

        ResultHandler resultHandler;

        public ComparePinAsync () {
        }

        public void setResultHandler(ResultHandler<User> resultHandler) {
            this.resultHandler = resultHandler;
        }

        @Override
        protected List<User> doInBackground(String... strings) {
            return UserDao.compareUserPin(strings[0]);
        }

        protected void onPostExecute(List<User> result) {
            if(resultHandler != null) {
                resultHandler.onResult(result);
            }
        }

    }
}
