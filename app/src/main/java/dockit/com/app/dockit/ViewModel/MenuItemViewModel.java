package dockit.com.app.dockit.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import dockit.com.app.dockit.Entity.MenuItem;
import dockit.com.app.dockit.Repository.MenuItemRepository;

/**
 * Created by michael on 08/08/18.
 */

public class MenuItemViewModel extends AndroidViewModel {

    private MenuItemRepository menuItemRepository;

    public MenuItemViewModel(@NonNull Application application) {
        super(application);

        menuItemRepository = new MenuItemRepository(application);
    }

    public void update(MenuItem menuItem) {
        menuItemRepository.update(menuItem);
    }

    public LiveData<List<MenuItem>> getLiveMenuItemsByMenuId(int menuId) {
        return menuItemRepository.getLiveByMenuId(menuId);
    }
}
