package dockit.com.app.dockit.ClickListener;

import android.content.Context;
import android.graphics.Point;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

import dockit.com.app.dockit.Adapter.MenuItemListAdapter;
import dockit.com.app.dockit.Entity.MenuItem;
import dockit.com.app.dockit.Popup.MenuItemPopup;
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

    public void setOnClickListener(final RecyclerView recyclerView) {
        recyclerView.clearOnChildAttachStateChangeListeners();
        recyclerView.addOnItemTouchListener(new RecyclerViewClickListener(recyclerView.getContext(), recyclerView, new RecyclerViewClickListener.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {

                MenuItem menuItem = menuItemListAdapter.getItemAtPosition(position);
                boolean isMandatoryOptionItem = menuItem.getDescription().equals("Country Style Terrine") ? true : false;

                if(menuItem.isSelected()) {
                    menuItem.setSelected(false);
                }
                else if(isMandatoryOptionItem) {
                    MenuItemPopup.openMandatoryPopup(view.getContext(), recyclerView);
                    menuItem.setSelected(true);
                }
                else {
                    menuItem.setSelected(true);
                }

                menuItemViewModel.update(menuItem);
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
