package dockit.com.app.dockit.Entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class User {

    @PrimaryKey
    @NonNull
    private String id;
    private String pin;
    private String name;

    public User(String id, String pin, String name) {
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