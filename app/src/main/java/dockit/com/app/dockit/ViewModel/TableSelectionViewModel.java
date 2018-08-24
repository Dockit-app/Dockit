package dockit.com.app.dockit.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import java.util.List;

import dockit.com.app.dockit.Entity.Order;
import dockit.com.app.dockit.Repository.TableRepository;

public class TableSelectionViewModel extends AndroidViewModel {

    private TableRepository tableRepository;
    public MutableLiveData<Boolean> isTableNameValidated;

    public TableSelectionViewModel(Application application) {
        super(application);
        tableRepository = new TableRepository(application);
        isTableNameValidated = new MutableLiveData();
        isTableNameValidated.setValue(false);
    }

    public LiveData<List<Order>> getTables(){
        return tableRepository.getExistingTables();
    }

    public LiveData<List<Order>> createNewTable(String table){
        return tableRepository.getTablesWithInputNumber(table);
    }


}