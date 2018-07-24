package dockit.com.app.dockit.API;

/**
 * Created by michael on 24/07/18.
 */

public interface BaseCallback<T> {

    void onSuccess(T response);

    void onError();
}
