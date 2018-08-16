package dockit.com.app.dockit.Tasks;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.List;

import dockit.com.app.dockit.Entity.User;

public class SharedPreferencesManager {
    private static SharedPreferencesManager mInstance;
    private Context mCtx;
    private static final String SHARED_PREF_NAME = "dockitSharedPref";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_USER_ID = "id";

    private SharedPreferencesManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPreferencesManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPreferencesManager(context);
        }
        return mInstance;
    }

    public boolean userLogin(List<User> user) {

        for(User userInfo : user){
            String id = userInfo.getId();
            String name = userInfo.getName();

            SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putString(KEY_USER_ID, id);
            editor.putString(KEY_USERNAME, name);
            editor.apply();
        }
        return true;
    }

    public boolean logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;
    }

    public String getID() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_ID, null);
    }

    public String getUsername() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME, null);
    }
}
