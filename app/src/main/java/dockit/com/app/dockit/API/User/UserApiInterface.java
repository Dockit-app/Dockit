package dockit.com.app.dockit.API.User;

import java.util.List;

import dockit.com.app.dockit.Entity.OmnivoreUser;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface UserApiInterface {
    @Headers("Cache-Control: public, max-age=640000, s-maxage=640000 , max-stale=2419200")
    @GET("api/UserExpression")
    Call<List<OmnivoreUser>> getAll();
}
