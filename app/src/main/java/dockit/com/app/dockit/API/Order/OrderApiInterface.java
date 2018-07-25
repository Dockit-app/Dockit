package dockit.com.app.dockit.API.Order;

import java.util.List;

import dockit.com.app.dockit.Entity.Order;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

/**
 * Created by michael on 24/07/18.
 */

public interface OrderApiInterface {

    @Headers("Cache-Control: public, max-age=640000, s-maxage=640000 , max-stale=2419200")
    @GET("api/UserExpression")
    Call<List<Order>> getAll();

    //Get by id

    //Update

}
