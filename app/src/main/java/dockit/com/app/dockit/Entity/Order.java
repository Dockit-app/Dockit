package dockit.com.app.dockit.Entity;

import android.arch.persistence.room.Entity;

/**
 * Created by michael on 24/07/18.
 */

@Entity
public class Order {

    private Integer id;
    private String table;
    private String comments;
}
