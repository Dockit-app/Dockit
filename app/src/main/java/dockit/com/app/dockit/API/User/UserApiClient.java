package dockit.com.app.dockit.API.User;

//execute the specific get that select list of employees from omnivore

import android.content.Context;

import java.util.List;

import dockit.com.app.dockit.API.BaseApiClient;
import dockit.com.app.dockit.API.BaseCallback;
import dockit.com.app.dockit.Entity.OmnivoreUser;

public class UserApiClient extends BaseApiClient<OmnivoreUser> {

    private UserApiInterface userApiInterface;

    public UserApiClient(Context context) {
        super(context);
        userApiInterface = getRetrofitCaller().create(UserApiInterface.class);
    }

    public void getAll(BaseCallback<List<OmnivoreUser>> callback) {

        executeGet(callback, userApiInterface.getAll());
    }
}