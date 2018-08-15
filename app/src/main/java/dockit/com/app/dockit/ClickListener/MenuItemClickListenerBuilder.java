package dockit.com.app.dockit.ClickListener;

import android.graphics.Color;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import dockit.com.app.dockit.Adapter.MenuItemListAdapter;
import dockit.com.app.dockit.Entity.MenuItem;
import dockit.com.app.dockit.R;
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
        view.clearOnChildAttachStateChangeListeners();
        view.addOnItemTouchListener(new RecyclerViewClickListener(view.getContext(), view, new RecyclerViewClickListener.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {
                MenuItem menuItem = menuItemListAdapter.getItemAtPosition(position);

                if (view instanceof AppCompatButton) {

                    int counterValue = menuItem.getCounter() == null ? 0 : menuItem.getCounter();

                    if(view.getId() == R.id.decrement) {
                        int decrementedValue = counterValue - 1 < 0 ? 0 : counterValue - 1;
                        menuItem.setCounter(decrementedValue);
                    }
                    else if(view.getId() == R.id.increment) {
                        menuItem.setCounter(counterValue + 1);
                    }

                }
                else {
                    if(menuItem.isSelected()) {
                        menuItem.setSelected(false);
                    }
                    else {
                        menuItem.setSelected(true);
                    }
                }

                menuItemViewModel.update(menuItem);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
    }
}
