package dockit.com.app.dockit.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.util.Log;

import java.util.List;

import dockit.com.app.dockit.Entity.User;
import dockit.com.app.dockit.Repository.UserRepository;
import dockit.com.app.dockit.Tasks.ResultHandler;
import dockit.com.app.dockit.Tasks.SharedPreferencesManager;

public class UserViewModel extends AndroidViewModel implements ResultHandler<List<User>> {

    private UserRepository mRepository;
    //integration Omnivore -> private OmnivoreUserRepository mOmniRepository;
    private String pinView = "";
    private Context context;

    private MutableLiveData<String> toast;

    public LiveData<String> toast() {
        if (toast == null) {
            toast = new MutableLiveData<>();
            toast.setValue("Welcome Back!");
        }
        return toast;
    }


    public UserViewModel(Application application) {
        super(application);
        mRepository = new UserRepository(application);
        //integration Omnivore -> mOmniRepository = new OmnivoreUserRepository(application);
        this.context = application;
    }

    public void updatePin(String number){
        pinView = pinView + number;
    }

    public void doLogin(){
        //Final Idea: compares the data with the data on the API database and if matches, goes to next screen
        Log.d("My Tag", "User is: " + pinView);

        mRepository.comparePin(pinView, this);

        pinView = "";

        //integration Omnivore -> mOmniRepository.refreshDatabase();


    }


    @Override
    public void onResult(List<User> result) {

        if (result.toString() != "[]"){
            Log.d("My Tag", "it's not empty,  save nameUser and ID in a shared Preference");

            boolean saveUser = SharedPreferencesManager.getInstance(this.context).userLogin(result);
            Log.d("My Tag", "Saved user? " + saveUser);
            String nameUser = SharedPreferencesManager.getInstance(this.context).getUsername();
            String idUser = SharedPreferencesManager.getInstance(this.context).getID();
            Log.d("My tag", "User saved is " + nameUser + " and id is " + idUser);
            toast.postValue(nameUser);
        }
        else {
            toast.postValue("Wrong Pin!");
            Log.d("My Tag", "toast Wrong Pin");
        }
    }
}