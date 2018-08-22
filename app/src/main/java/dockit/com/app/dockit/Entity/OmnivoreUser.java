package dockit.com.app.dockit.Entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

//entity with information from omnivore users
@Entity
public class OmnivoreUser {

    @PrimaryKey
    @NonNull
    private String id;
    @SerializedName("login")
    private String pin;
    @SerializedName("check_name")
    private String name;

    public OmnivoreUser(String id, String pin, String name) {
        this.id = id;
        this.pin = pin;
        this.name = name;
    }

    public String getName(){ return name;}
    public String getPin(){
        return pin;
    }
    public String getId(){return id;}
}
