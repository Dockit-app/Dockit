package dockit.com.app.dockit.ViewModel;

public class UserViewModel {
    private String pinView = "";

    public String updatePin(String number){
        pinView = pinView + number;
        return pinView;
    }

    public boolean doLogin(){
        //Final Idea: compares the data with the data on the API database and if matches, goes to next screen

        //success
        if (pinView.equals("12345")) {
            pinView="";
            return true;
        } else {
            //failed
            pinView="";
            return false;
        }

    }
}
