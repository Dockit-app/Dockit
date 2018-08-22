package dockit.com.app.dockit.Repository;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import dockit.com.app.dockit.API.BaseCallback;
import dockit.com.app.dockit.Data.Dao.UserDao;
import dockit.com.app.dockit.Data.LocalDatabase;
import dockit.com.app.dockit.Entity.OmnivoreUser;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
//here the data from the Omnivore API goes to the database
//Open database and refresh the table users -> delete all and save the updated omnivore data when the app is open

public class OmnivoreUserRepository implements BaseCallback<OmnivoreUser> {

    private UserDao userDao;

    public OmnivoreUserRepository(Context context) {
        this.userDao = LocalDatabase.getInstance(context).userDao();
    }


    @Override
    public void onSuccess(OmnivoreUser response) {
        //to catch information from the API, use the function UserApiClient.getAll(callback);

        Callback<List<OmnivoreUser>> apiUsers = new Callback<List<OmnivoreUser>>() {
            @Override
            public void onResponse(Call<List<OmnivoreUser>> call, Response<List<OmnivoreUser>> response) {
                if (response.isSuccessful()) {
                    List<OmnivoreUser> data = new ArrayList<>(response.body());
                    RefreshDatabaseAsync refreshDatabaseAsync = new RefreshDatabaseAsync();
                    refreshDatabaseAsync.execute(data);

                } else {
                    Log.d("QuestionsCallback", "Code: " + response.code() + " Message: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<OmnivoreUser>> call, Throwable t) {
                t.printStackTrace();
            }
        };


    }

    @Override
    public void onError() {
        Log.d("My tag", "Api Error");

    }


    class RefreshDatabaseAsync extends AsyncTask<List<OmnivoreUser>, Void, Boolean> {

        private RefreshDatabaseAsync(){

        }

        @Override
        protected Boolean doInBackground(List<OmnivoreUser>... lists) {
            //clean user table
            userDao.deleteAll();

            for(List<OmnivoreUser> listOmniUser : lists){
                for(OmnivoreUser omniUser : listOmniUser) {
                    String id = omniUser.getId();
                    String name = omniUser.getName();
                    String pin = omniUser.getPin();

                    //save each new user to database
                    OmnivoreUser omnivoreUser = new OmnivoreUser(id, pin, name);
                    //userDao.insert(omnivoreUser);
                }
            }
            return true;
        }

    }


}