package dockit.com.app.dockit.ClickListener;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import dockit.com.app.dockit.Adapter.MenuItemListAdapter;
import dockit.com.app.dockit.Entity.Decorator.MenuItemView;
import dockit.com.app.dockit.Entity.MenuItem;
import dockit.com.app.dockit.Entity.Result.MenuItemResult;
import dockit.com.app.dockit.Popup.MenuItemPopup;
import dockit.com.app.dockit.ViewModel.MenuItemViewModel;

/**
 * Created by michael on 03/08/18.
 */

public class MenuItemClickListenerBuilder {

    private MenuItemViewModel menuItemViewModel;
    private MenuItemListAdapter menuItemListAdapter;

    public MenuItemClickListenerBuilder(MenuItemViewModel menuItemViewModel, MenuItemListAdapter menuItemListAdapter) {
        this.menuItemListAdapter = menuItemListAdapter;
        this.menuItemViewModel = menuItemViewModel;
    }

    public void setOnClickListener(final RecyclerView recyclerView) {
        recyclerView.clearOnChildAttachStateChangeListeners();
        recyclerView.addOnItemTouchListener(new RecyclerViewClickListener(recyclerView.getContext(), recyclerView, new RecyclerViewClickListener.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {

                MenuItemResult menuItemResult = menuItemListAdapter.getItemAtPosition(position);

                if(!((MenuItemView)menuItemResult).isSection()) {
                    boolean isMandatoryOptionItem = menuItemResult.mandatoryItems.size() > 0;

                    if (menuItemResult.isSelected()) {
                        menuItemResult.setSelected(false);
                    } else if (isMandatoryOptionItem) {
                        MenuItemPopup.openMandatoryPopup(view.getContext(), recyclerView, menuItemResult, menuItemViewModel);
                        menuItemResult.setSelected(true);
                    } else {
                        menuItemResult.setSelected(true);
                    }

                    menuItemViewModel.updateMandatory(new MenuItem(menuItemResult));
                }
            }

            @Override
            public void onDoubleClick(View view, int position) {
                MenuItemResult menuItemResult = menuItemListAdapter.getItemAtPosition(position);
                if(!((MenuItemView)menuItemResult).isSection()) {
                    MenuItemPopup.openOptionalPopup(view.getContext(), recyclerView, menuItemResult, menuItemViewModel);
                }
            }

            @Override
            public void onLongItemClick(View view, int position) {
            }

        }));
    }
}
