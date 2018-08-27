package dockit.com.app.dockit.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import java.util.List;

import dockit.com.app.dockit.Entity.User;
import dockit.com.app.dockit.Repository.UserRepository;
import dockit.com.app.dockit.Tasks.ResultHandler;
import dockit.com.app.dockit.Tasks.SharedPreferencesManager;

public class UserViewModel extends AndroidViewModel implements ResultHandler<List<User>> {

    //integration Omnivore -> private OmnivoreUserRepository mOmniRepository;
    private UserRepository mRepository;
    private MutableLiveData<String> toast;

    public UserViewModel(Application application) {
        super(application);
        mRepository = new UserRepository(application);
        //integration Omnivore -> mOmniRepository = new OmnivoreUserRepository(application);
    }

    //Try to find username and ID by pin
    public void doLogin(String pinView){
        mRepository.comparePin(pinView, this);
        //integration Omnivore -> mOmniRepository.refreshDatabase();


    }

    //Set the default Toast
    public LiveData<String> toast() {
        if (toast == null) {
            toast = new MutableLiveData<>();
            toast.setValue("Welcome Back!");
        }
        return toast;
    }

    @Override
    public void onResult(List<User> result) {

        //If the result isn't empty, save user name and id on Shared Preference. Else, user can try again
        if (!result.toString().equals("[]")) {
            SharedPreferencesManager.getInstance(getApplication()).userLogin(result);
            toast.postValue("Success!");
        }
        else {
            toast.postValue("Wrong Pin!");
        }
    }
}