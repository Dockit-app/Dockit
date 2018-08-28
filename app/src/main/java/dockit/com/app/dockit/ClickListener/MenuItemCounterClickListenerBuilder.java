package dockit.com.app.dockit.ClickListener;

import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import dockit.com.app.dockit.Adapter.MenuItemListAdapter;
import dockit.com.app.dockit.Entity.MenuItem;
import dockit.com.app.dockit.Entity.Result.MenuItemResult;
import dockit.com.app.dockit.Popup.MenuItemPopup;
import dockit.com.app.dockit.R;
import dockit.com.app.dockit.ViewModel.MenuItemViewModel;

/**
 * Created by michael on 03/08/18.
 */

public class MenuItemCounterClickListenerBuilder {

    MenuItemViewModel menuItemViewModel;
    MenuItemListAdapter menuItemListAdapter;

    public MenuItemCounterClickListenerBuilder(MenuItemViewModel menuItemViewModel, MenuItemListAdapter menuItemListAdapter) {
        this.menuItemListAdapter = menuItemListAdapter;
        this.menuItemViewModel = menuItemViewModel;
    }

    public void setOnClickListener(final RecyclerView recyclerView) {
        recyclerView.clearOnChildAttachStateChangeListeners();
        recyclerView.addOnItemTouchListener(new RecyclerViewClickListener(recyclerView.getContext(), recyclerView, new RecyclerViewClickListener.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {

                MenuItemResult menuItem = menuItemListAdapter.getItemAtPosition(position);
                boolean isMandatoryOptionItem = menuItem.getDescription().equals("Country Style Terrine") ? true : false;

                int counterValue = menuItem.getCounter() == null ? 0 : menuItem.getCounter();
                boolean isCounterItem = counterValue > 0 ? true : false;

                if (view instanceof AppCompatButton) {

                    if(view.getId() == R.id.decrement) {
                        int decrementedValue = counterValue - 1 < 0 ? 0 : counterValue - 1;
                        menuItem.setCounter(decrementedValue);
                        if(decrementedValue == 0 && menuItem.isSelected()) {
                            menuItem.setSelected(false);
                        }
                    }
                    else if(view.getId() == R.id.increment) {
                        menuItem.setCounter(counterValue + 1);
                        if(!menuItem.isSelected() && !isMandatoryOptionItem) {
                            menuItem.setSelected(true);
                        }
                    }

                    if(isMandatoryOptionItem) {
                        menuItem.setSelected(false);
                    }

                }
                else {
                    if(menuItem.isSelected()) {
                        menuItem.setSelected(false);
                    }
                    else if(isMandatoryOptionItem) {
                        MenuItemPopup.openMandatoryCounterPopup(view.getContext(), recyclerView);
                        menuItem.setSelected(true);
                    }
                    else if(!isCounterItem) {
                        menuItem.setSelected(true);
                    }
                }

                menuItemViewModel.update(new MenuItem((menuItem)));
            }

            @Override
            public void onDoubleClick(View view, int position) {
                MenuItemPopup.openOptionalPopup(view.getContext(), recyclerView);
            }

            @Override
            public void onLongItemClick(View view, int position) {
            }

        }));
    }
}
