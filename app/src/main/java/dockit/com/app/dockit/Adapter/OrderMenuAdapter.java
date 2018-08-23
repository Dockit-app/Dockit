package dockit.com.app.dockit.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

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

    private static final int PAGE_COUNT = 2;
    private List<MenuResult> menus;
    private int orderLocationId;
    public OrderMenuAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    public void setOrderResult(List<MenuResult> menus)  {
        this.orderLocationId = menus.get(0).getLocationId();
        this.menus = menus;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return menus.get(position).getMenuName();
    }

    @Override
    public Fragment getItem(int position) {
        Log.d(this.getClass().getSimpleName(), "Getting MenuFragment");
        MenuResult menu = menus.get(position);
        return MenuFragment.newInstance(menu);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    public int getItemPosition(Object item) {
        MenuFragment fragment = (MenuFragment)item;
        MenuResult menuResult = fragment.getMenuResult();
        int position = menus.indexOf(menuResult);

        if (position >= 0) {
            return position;
        } else {
            return POSITION_NONE;
        }
    }

    public int getOrderLocationId() {
        return orderLocationId;
    }
}
