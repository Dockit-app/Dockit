package dockit.com.app.dockit.Data;

import android.content.Context;
import android.os.AsyncTask;

import dockit.com.app.dockit.Data.Dao.OrderLocationDao;
import dockit.com.app.dockit.Entity.OrderLocationAmount;

/**
 * Created by michael on 08/08/18.
 */

public class InsertOrderLocationAmountAsync extends AsyncTask<Void, Void, Void> {

    private OrderLocationDao orderLocationDao;
    private OrderLocationAmount orderLocationAmount;

    public InsertOrderLocationAmountAsync(LocalDatabase db, int amount) {
        orderLocationDao = db.orderLocationDao();
        orderLocationAmount = new OrderLocationAmount();
        orderLocationAmount.setAmount(amount);
    }
    @Override
    protected Void doInBackground(Void... voids) {
        orderLocationDao.insert(orderLocationAmount);
        return null;
    }
}
