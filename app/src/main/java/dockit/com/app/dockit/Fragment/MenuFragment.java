package dockit.com.app.dockit.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dockit.com.app.dockit.Adapter.MenuItemListAdapter;
import dockit.com.app.dockit.Entity.Menu;
import dockit.com.app.dockit.Entity.Result.MenuResult;
import dockit.com.app.dockit.R;

/**
 * Created by michael on 26/07/18.
 */

public class MenuFragment extends Fragment {

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

        return rootView;
    }

    private void setMenuItemRecyclerView(ViewGroup rootView) {
        RecyclerView recyclerView = rootView.findViewById(R.id.menu_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new MenuItemListAdapter(rootView.getContext(), menu.menuItems));
    }
}
