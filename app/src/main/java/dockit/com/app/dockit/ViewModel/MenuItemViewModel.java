package dockit.com.app.dockit.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import java.util.List;

import dockit.com.app.dockit.Entity.IngredientItem;
import dockit.com.app.dockit.Entity.MandatoryItem;
import dockit.com.app.dockit.Entity.MenuItem;
import dockit.com.app.dockit.Entity.OptionalItem;
import dockit.com.app.dockit.Entity.Order;
import dockit.com.app.dockit.Repository.MenuItemRepository;
import dockit.com.app.dockit.Repository.OptionsRepository;
import dockit.com.app.dockit.Repository.OrderRepository;

/**
 * Created by michael on 08/08/18.
 */

public class MenuItemViewModel extends AndroidViewModel {

    private OrderRepository orderRepository;
    private MenuItemRepository menuItemRepository;
    private OptionsRepository optionsRepository;

    public MenuItemViewModel(@NonNull Application application) {
        super(application);

        menuItemRepository = new MenuItemRepository(application);
        orderRepository = new OrderRepository(application);
        optionsRepository = new OptionsRepository(application);
    }

    public void updateMandatory(MenuItem menuItem) {
        menuItemRepository.update(menuItem);
    }

    public LiveData<List<MenuItem>> getLiveMenuItemsByMenuId(int menuId) {
        return menuItemRepository.getLiveByMenuId(menuId);
    }

    public LiveData<List<Order>> getLiveOrderByMenuId(int menuId) {
        return orderRepository.getLiveByMenuId(menuId);
    }

    public void updateMandatory(List<MandatoryItem> mandatoryItems) {
        optionsRepository.updateAllMandatory(mandatoryItems);
    }

    public void updateOptional(OptionalItem optionalItem) {
        optionsRepository.updateOptionalItem(optionalItem);
    }

    public void updateIngredient(IngredientItem ingredientItem) {
        optionsRepository.updateIngredientItem(ingredientItem);
    }
}
