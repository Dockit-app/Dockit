package dockit.com.app.dockit.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import dockit.com.app.dockit.Repository.UserRepository;
import dockit.com.app.dockit.Tasks.ResultHandler;

public class UserViewModel extends AndroidViewModel implements ResultHandler {

    private UserRepository mRepository;
    private String pinView = "";

    private MutableLiveData<String> toast;

    public LiveData<String> toast() {
        if (toast == null) {
            toast = new MutableLiveData<>();
            toast.setValue("Success!");
        }
        return toast;
    }



    public UserViewModel(Application application) {
        super(application);
        mRepository = new UserRepository(application);
    }

    public String updatePin(String number){
        pinView = pinView + number;
        //Only returns for testing purposes TODO(2): Remove return
        return pinView;
    }

    public boolean doLogin(){
        //Final Idea: compares the data with the data on the API database and if matches, goes to next screen
        Log.d("My Tag", "User is: " + pinView);

        mRepository.comparePin(pinView, this);

        pinView = "";
        return false;

    }

    @Override
    public void onResult(Object result) {
        Log.d("My Tag", "User name and id is: " + result);

        if (result.toString() == ""){
            Log.d("My Tag", "it's not empty,  goes to next screen with the nameUser and ID");
        }
        else {
            toast.postValue("Wrong Pin!");
            Log.d("My Tag", "toast Wrong Pin");
        }

    }
}
