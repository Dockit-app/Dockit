package dockit.com.app.dockit.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.MenuRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import dockit.com.app.dockit.Adapter.MenuItemListAdapter;
import dockit.com.app.dockit.ClickListener.RecyclerViewClickListener;
import dockit.com.app.dockit.Entity.MenuItem;
import dockit.com.app.dockit.Entity.OrderLocation;
import dockit.com.app.dockit.Entity.Result.MenuResult;
import dockit.com.app.dockit.Entity.Result.MenuSectionResult;
import dockit.com.app.dockit.Entity.Result.OrderLocationResult;
import dockit.com.app.dockit.Entity.Result.OrderResult;
import dockit.com.app.dockit.R;
import dockit.com.app.dockit.Repository.OrderRepository;
import dockit.com.app.dockit.Entity.Order;

public class OrderSummaryViewModel extends AndroidViewModel {
    private LiveData<List<OrderResult>> liveOrderResults;
    private int maxOrderLocations = 50; //TODO: Persist in DB
    private int orderId = 1;
    List<String> groupedItems;

    private OrderRepository orderRepository;

    public OrderSummaryViewModel(@NonNull Application application) {
        super(application);

        this.orderRepository = new OrderRepository(application);

        liveOrderResults = orderRepository.getLiveOrders();
    }


    public LiveData<OrderResult> RetrieveOrderInfo(int id) {
        return orderRepository.retrieveOrder(id);
    }

    public String GetTable(OrderResult order) {
        return order.getTable();
    }

    public String GetComments(OrderResult order) {
        return order.getComments();
    }

    public int GetCovers(OrderResult order) {
        List<OrderLocationResult> orderLocationResults = order.orderLocationResults;
        return orderLocationResults.size();
    }

    public List<MenuResult> GetMenu(OrderResult order) {
        List<OrderLocationResult> orderLocationResults = order.orderLocationResults;
        return orderLocationResults.get(0).menus;
    }

    //Set up recycler view, pass across the menu sections with food items grouped
    //use MenuItemListAdapter? (look at onbindviewholder)
    //NotFinished
    private void SetSummaryRecyclerView(ViewGroup rootView, MenuResult menu) {
        RecyclerView recyclerView = rootView.findViewById(R.id.summary_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext(), LinearLayoutManager.VERTICAL, false));

    }


    //Quick and dirty attempt at getting ordered items and grouping them, will be changed to
    //be nicer and cleaner, will collect and group items by menu section
    //NOT FINISHED
    private void ItemCollecter(OrderLocationResult olr) {
        List<MenuItem> menu_Items = new List<MenuItem>() {
            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean contains(Object o) {
                return false;
            }

            @NonNull
            @Override
            public Iterator<MenuItem> iterator() {
                return null;
            }

            @NonNull
            @Override
            public Object[] toArray() {
                return new Object[0];
            }

            @NonNull
            @Override
            public <T> T[] toArray(@NonNull T[] ts) {
                return null;
            }

            @Override
            public boolean add(MenuItem menuItem) {
                return false;
            }

            @Override
            public boolean remove(Object o) {
                return false;
            }

            @Override
            public boolean containsAll(@NonNull Collection<?> collection) {
                return false;
            }

            @Override
            public boolean addAll(@NonNull Collection<? extends MenuItem> collection) {
                return false;
            }

            @Override
            public boolean addAll(int i, @NonNull Collection<? extends MenuItem> collection) {
                return false;
            }

            @Override
            public boolean removeAll(@NonNull Collection<?> collection) {
                return false;
            }

            @Override
            public boolean retainAll(@NonNull Collection<?> collection) {
                return false;
            }

            @Override
            public void clear() {

            }

            @Override
            public MenuItem get(int i) {
                return null;
            }

            @Override
            public MenuItem set(int i, MenuItem menuItem) {
                return null;
            }

            @Override
            public void add(int i, MenuItem menuItem) {

            }

            @Override
            public MenuItem remove(int i) {
                return null;
            }

            @Override
            public int indexOf(Object o) {
                return 0;
            }

            @Override
            public int lastIndexOf(Object o) {
                return 0;
            }

            @NonNull
            @Override
            public ListIterator<MenuItem> listIterator() {
                return null;
            }

            @NonNull
            @Override
            public ListIterator<MenuItem> listIterator(int i) {
                return null;
            }

            @NonNull
            @Override
            public List<MenuItem> subList(int i, int i1) {
                return null;
            }
        };
        //get menuResult list
        List<MenuResult> mr = olr.menus;
        if (mr.size() > 0) {
            //cycle through menuResults
            for (int i = 0; i < mr.size(); i++) {
                //retrieve each menusectionresult
                List<MenuSectionResult> msr = mr.get(i).menuSectionResults;
                if (msr.size() > 0) {
                    for (int j = 0; j < msr.size(); j++) {
                        //collect menuItems from list
                        List<MenuItem> mi = msr.get(j).menuItemList;
                        if (mi.size() > 0) {
                            for (int k = 0; k < mi.size(); k++) {
                                //Check if item is already in list, and if not add it
                                menu_Items.add(mi.get(k));
                            }
                        }
                    }
                }
            }
        }

    }


    private void FoodItemGrouper(OrderResult order) {
        List<String> groupedItems = new List<String>() {
            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean contains(Object o) {
                return false;
            }

            @NonNull
            @Override
            public Iterator<String> iterator() {
                return null;
            }

            @NonNull
            @Override
            public Object[] toArray() {
                return new Object[0];
            }

            @NonNull
            @Override
            public <T> T[] toArray(@NonNull T[] ts) {
                return null;
            }

            @Override
            public boolean add(String s) {
                return false;
            }

            @Override
            public boolean remove(Object o) {
                return false;
            }

            @Override
            public boolean containsAll(@NonNull Collection<?> collection) {
                return false;
            }

            @Override
            public boolean addAll(@NonNull Collection<? extends String> collection) {
                return false;
            }

            @Override
            public boolean addAll(int i, @NonNull Collection<? extends String> collection) {
                return false;
            }

            @Override
            public boolean removeAll(@NonNull Collection<?> collection) {
                return false;
            }

            @Override
            public boolean retainAll(@NonNull Collection<?> collection) {
                return false;
            }

            @Override
            public void clear() {

            }

            @Override
            public String get(int i) {
                return null;
            }

            @Override
            public String set(int i, String s) {
                return null;
            }

            @Override
            public void add(int i, String s) {

            }

            @Override
            public String remove(int i) {
                return null;
            }

            @Override
            public int indexOf(Object o) {
                return 0;
            }

            @Override
            public int lastIndexOf(Object o) {
                return 0;
            }

            @NonNull
            @Override
            public ListIterator<String> listIterator() {
                return null;
            }

            @NonNull
            @Override
            public ListIterator<String> listIterator(int i) {
                return null;
            }

            @NonNull
            @Override
            public List<String> subList(int i, int i1) {
                return null;
            }
        }
        List<Integer> groupedItemCount = new List<Integer>() {
            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean contains(Object o) {
                return false;
            }

            @NonNull
            @Override
            public Iterator<Integer> iterator() {
                return null;
            }

            @NonNull
            @Override
            public Object[] toArray() {
                return new Object[0];
            }

            @NonNull
            @Override
            public <T> T[] toArray(@NonNull T[] ts) {
                return null;
            }

            @Override
            public boolean add(Integer integer) {
                return false;
            }

            @Override
            public boolean remove(Object o) {
                return false;
            }

            @Override
            public boolean containsAll(@NonNull Collection<?> collection) {
                return false;
            }

            @Override
            public boolean addAll(@NonNull Collection<? extends Integer> collection) {
                return false;
            }

            @Override
            public boolean addAll(int i, @NonNull Collection<? extends Integer> collection) {
                return false;
            }

            @Override
            public boolean removeAll(@NonNull Collection<?> collection) {
                return false;
            }

            @Override
            public boolean retainAll(@NonNull Collection<?> collection) {
                return false;
            }

            @Override
            public void clear() {

            }

            @Override
            public Integer get(int i) {
                return null;
            }

            @Override
            public Integer set(int i, Integer integer) {
                return null;
            }

            @Override
            public void add(int i, Integer integer) {

            }

            @Override
            public Integer remove(int i) {
                return null;
            }

            @Override
            public int indexOf(Object o) {
                return 0;
            }

            @Override
            public int lastIndexOf(Object o) {
                return 0;
            }

            @NonNull
            @Override
            public ListIterator<Integer> listIterator() {
                return null;
            }

            @NonNull
            @Override
            public ListIterator<Integer> listIterator(int i) {
                return null;
            }

            @NonNull
            @Override
            public List<Integer> subList(int i, int i1) {
                return null;
            }
        }
        //retrieve list of ordered food items
        List<OrderLocationResult> orderLocationResults = order.orderLocationResults;


        List<String> orderedItems;
        groupedItems.add(orderedItems.get(0).getLocationText());
        groupedItemCount.add(1);
        for (int i = 1; i<orderedItems.size(); i++) {
            int index = groupedItems.contains(orderedItems.get(i).getText());
            if (index == -1) {
                groupedItems.add(orderedItems.get(i).getText());
                groupedItemCount.add(1);
            }
            else {
                groupedItemCount.set(index, groupedItemCount.get(index) + 1);
            }
        }
    }
}
