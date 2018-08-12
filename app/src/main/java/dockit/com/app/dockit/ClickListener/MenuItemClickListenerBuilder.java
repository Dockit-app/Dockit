package dockit.com.app.dockit.ClickListener;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import dockit.com.app.dockit.Adapter.MenuItemListAdapter;
import dockit.com.app.dockit.Entity.MenuItem;
import dockit.com.app.dockit.ViewModel.MenuItemViewModel;

/**
 * Created by michael on 03/08/18.
 */

public class MenuItemClickListenerBuilder {

    MenuItemViewModel menuItemViewModel;
    MenuItemListAdapter menuItemListAdapter;

    public MenuItemClickListenerBuilder(MenuItemViewModel menuItemViewModel, MenuItemListAdapter menuItemListAdapter) {
        this.menuItemListAdapter = menuItemListAdapter;
        this.menuItemViewModel = menuItemViewModel;
    }

    public void setOnClickListener(RecyclerView view) {
        view.addOnItemTouchListener(new RecyclerViewClickListener(view.getContext(), view, new RecyclerViewClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                MenuItem menuItem = menuItemListAdapter.getItemAtPosition(position);

                if(menuItem.isSelected()) {
                    menuItem.setSelected(false);
                }
                else {
                    menuItem.setSelected(true);
                }

                menuItemViewModel.update(menuItem);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
    }
}
