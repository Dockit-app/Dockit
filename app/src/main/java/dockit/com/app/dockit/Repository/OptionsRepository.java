package dockit.com.app.dockit.Repository;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import dockit.com.app.dockit.Data.Dao.IngredientItemDao;
import dockit.com.app.dockit.Data.Dao.MandatoryItemDao;
import dockit.com.app.dockit.Data.Dao.OptionalItemDao;
import dockit.com.app.dockit.Data.LocalDatabase;
import dockit.com.app.dockit.Entity.IngredientItem;
import dockit.com.app.dockit.Entity.MandatoryItem;
import dockit.com.app.dockit.Entity.OptionalItem;

/**
 * Created by michael on 27/08/18.
 */

public class OptionsRepository {

    MandatoryItemDao mandatoryItemDao;
    OptionalItemDao optionalItemDao;
    IngredientItemDao ingredientItemDao;

    public OptionsRepository(Context context) {
        this.mandatoryItemDao = LocalDatabase.getInstance(context).mandatoryItemDao();
        this.optionalItemDao = LocalDatabase.getInstance(context).optionalItemDao();
        this.ingredientItemDao = LocalDatabase.getInstance(context).ingredientItemDao();
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

    public void updateOptionalItem(OptionalItem optionalItem) {
        new UpdateOptionalAsync(optionalItemDao).execute(optionalItem);
    }

    private class UpdateOptionalAsync extends AsyncTask<OptionalItem, Void, Void> {

        OptionalItemDao optionalItemDao;

        public UpdateOptionalAsync(OptionalItemDao optionalItemDao) {
            this.optionalItemDao = optionalItemDao;
        }

        @Override
        protected Void doInBackground(OptionalItem... optionalItems) {
            optionalItemDao.update(optionalItems[0]);
            return null;
        }

    }

    public void updateIngredientItem(IngredientItem ingredientItem) {
        new UpdateIngredientAsync(ingredientItemDao).execute(ingredientItem);
    }

    private class UpdateIngredientAsync extends AsyncTask<IngredientItem, Void, Void> {

        IngredientItemDao ingredientItemDao;

        public UpdateIngredientAsync(IngredientItemDao ingredientItemDao) {
            this.ingredientItemDao = ingredientItemDao;
        }

        @Override
        protected Void doInBackground(IngredientItem... ingredientItems) {
            ingredientItemDao.update(ingredientItems[0]);
            return null;
        }

    }
}
