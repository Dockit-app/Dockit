package dockit.com.app.dockit.Repository;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import dockit.com.app.dockit.Data.Dao.MandatoryItemDao;
import dockit.com.app.dockit.Data.LocalDatabase;
import dockit.com.app.dockit.Entity.MandatoryItem;

/**
 * Created by michael on 27/08/18.
 */

public class OptionsRepository {

    MandatoryItemDao mandatoryItemDao;

    public OptionsRepository(Context context) {
        this.mandatoryItemDao = LocalDatabase.getInstance(context).mandatoryItemDao();
    }

    public void updateAllMandatory(List<MandatoryItem> mandatoryItems) {
        new UpdateAllAsync(mandatoryItemDao).execute(mandatoryItems);
    }

    private class UpdateAllAsync extends AsyncTask<List<MandatoryItem>, Void, Void> {

        MandatoryItemDao mandatoryItemDao;

        public UpdateAllAsync(MandatoryItemDao mandatoryItemDao) {
            this.mandatoryItemDao = mandatoryItemDao;
        }

        @Override
        protected Void doInBackground(List<MandatoryItem>[] lists) {
            mandatoryItemDao.update(lists[0]);
            return null;
        }
    }
}
