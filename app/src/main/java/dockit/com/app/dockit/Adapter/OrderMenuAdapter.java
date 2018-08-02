package dockit.com.app.dockit.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import dockit.com.app.dockit.Entity.Menu;
import dockit.com.app.dockit.Entity.Order;
import dockit.com.app.dockit.Entity.Result.MenuResult;
import dockit.com.app.dockit.Entity.Result.OrderResult;
import dockit.com.app.dockit.Fragment.MenuFragment;
import dockit.com.app.dockit.Repository.OrderRepository;

/**
 * Created by michael on 26/07/18.
 */

public class OrderMenuAdapter extends FragmentStatePagerAdapter {

    private static final int PAGE_COUNT = 3;
    private List<MenuResult> menus;
    public OrderMenuAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    public void addOrderResult(List<MenuResult> menus)  {
        this.menus = menus;
    }

    @Override
    public Fragment getItem(int position) {
        MenuResult menu = menus.get(position);
        return MenuFragment.newInstance(menu);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }
}
