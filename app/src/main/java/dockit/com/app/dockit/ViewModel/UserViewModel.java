package dockit.com.app.dockit.ViewModel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.content.Intent;
import java.util.List;

import dockit.com.app.dockit.Activity.TableSelection;
import dockit.com.app.dockit.Entity.User;
import dockit.com.app.dockit.Repository.UserRepository;
import dockit.com.app.dockit.Tasks.ResultHandler;
import dockit.com.app.dockit.Tasks.SharedPreferencesManager;

public class UserViewModel extends AndroidViewModel implements ResultHandler<List<User>> {

    private UserRepository mRepository;
    //integration Omnivore -> private OmnivoreUserRepository mOmniRepository;
    private String pinView = "";
    @SuppressLint("StaticFieldLeak")
    private Context context;
    private MutableLiveData<String> toast;

    public UserViewModel(Application application) {
        super(application);
        mRepository = new UserRepository(application);
        //integration Omnivore -> mOmniRepository = new OmnivoreUserRepository(application);
        this.context = application;
    }

    //Set the default Toast
    public LiveData<String> toast() {
        if (toast == null) {
            toast = new MutableLiveData<>();
            toast.setValue("Welcome Back!");
        }
        return toast;
    }

    //Update the pin adding the number pressed by the user
    public void updatePin(String number){
        pinView = pinView + number;
    }

    //Try to find username and ID by pin
    public void doLogin(){
        mRepository.comparePin(pinView, this);
        //integration Omnivore -> mOmniRepository.refreshDatabase();


    }


    @Override
    public void onResult(List<User> result) {

        //If the result isn't empty, save user name and id on Shared Preference. Else, user can try again
        if (!result.toString().equals("[]")) {

            SharedPreferencesManager.getInstance(this.context).userLogin(result);
            toast.postValue("Success!");

            //Send to the next Activity
            Intent i = new Intent(context, TableSelection.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }
        else {
            toast.postValue("Wrong Pin!");
            pinView = "";

        }
    }
}