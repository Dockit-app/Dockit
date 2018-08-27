package dockit.com.app.dockit.Fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import dockit.com.app.dockit.Adapter.MenuItemListAdapter;
import dockit.com.app.dockit.ClickListener.MenuItemClickListenerBuilder;
import dockit.com.app.dockit.Entity.Decorator.MenuItemView;
import dockit.com.app.dockit.Entity.MenuItem;
import dockit.com.app.dockit.Entity.Order;
import dockit.com.app.dockit.Entity.Result.MenuItemResult;
import dockit.com.app.dockit.Entity.Result.MenuResult;
import dockit.com.app.dockit.Entity.Result.MenuSectionResult;
import dockit.com.app.dockit.R;
import dockit.com.app.dockit.ViewModel.MenuItemViewModel;

/**
 * Created by michael on 26/07/18.
 */

public class MenuFragment extends Fragment {

    MenuItemViewModel menuItemViewModel;
    MenuItemListAdapter menuItemListAdapter;
    MenuResult menu;

    public static MenuFragment newInstance(MenuResult menu) {
        
        Bundle args = new Bundle();

        args.putSerializable("menu", menu);
        
        MenuFragment fragment = new MenuFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_menu, container, false);

        menu = (MenuResult)getArguments().getSerializable("menu");

        menuItemViewModel = ViewModelProviders.of(this).get(MenuItemViewModel.class);

        menuItemViewModel.getLiveMenuItemsByMenuId(menu.getId()).observe(this, new Observer<List<MenuItem>>() {
            @Override
            public void onChanged(@Nullable List<MenuItem> menuItems) {
                menuItemListAdapter.notifyDataSetChanged();
            }
        });

        setCounterObserver();

        setMenuItemRecyclerView(rootView, menu);

        return rootView;
    }

    private void setCounterObserver() {
        //Used for counter implementation
        menuItemViewModel.getLiveOrderByMenuId(menu.getId()).observe(this, new Observer<List<Order>>() {
            @Override
            public void onChanged(@Nullable List<Order> orders) {
                if(orders != null && orders.get(0).getCounterSelection() != null) {
//                    menuItemListAdapter.setCounter(orders.get(0).getCounterSelection());
                }

                menuItemListAdapter.notifyDataSetChanged();
                Log.i(this.getClass().getSimpleName(), "Order change observed");
            }
        });
    }

    private void setMenuItemRecyclerView(ViewGroup rootView, MenuResult menu) {
        RecyclerView recyclerView = rootView.findViewById(R.id.menu_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext(), LinearLayoutManager.VERTICAL, false));
        menuItemListAdapter = new MenuItemListAdapter(rootView.getContext(), buildMenuItemViewList(menu.menuSectionResults));
        recyclerView.setAdapter(menuItemListAdapter);

        new MenuItemClickListenerBuilder(menuItemViewModel, menuItemListAdapter).setOnClickListener(recyclerView);
    }

    private List<MenuItemView> buildMenuItemViewList(List<MenuSectionResult> menuSectionResults) {
            List<MenuItemView> menuItemViews = new ArrayList<>();
            for(MenuSectionResult menuSectionResult : menuSectionResults) {
                menuItemViews.add(new MenuItemView((menuSectionResult)));
                for(MenuItemResult menuItemResult : menuSectionResult.menuItemResultList) {
                    menuItemViews.add(new MenuItemView(menuItemResult));
                }
            }
            return menuItemViews;
    }

    public MenuResult getMenuResult() {
        return menu;
    }
}
