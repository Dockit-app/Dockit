package dockit.com.app.dockit;

import android.test.InstrumentationTestCase;

import org.junit.Test;

import dockit.com.app.dockit.API.Order.OrderApiClient;
import dockit.com.app.dockit.TestClasses.SampleOrderApiCallback;

/**
 * Created by michael on 24/07/18.
 */

public class ApiSampleTest extends InstrumentationTestCase {

    @Test
    public void Api_Sample() {

        new OrderApiClient(getInstrumentation().getContext()).getAll(new SampleOrderApiCallback());
    }
}
