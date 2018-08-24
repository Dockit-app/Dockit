package dockit.com.app.dockit.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import dockit.com.app.dockit.Entity.Decorator.SummaryItemView;
import dockit.com.app.dockit.Entity.MenuItem;
import dockit.com.app.dockit.Entity.Result.MenuResult;
import dockit.com.app.dockit.Entity.Result.MenuSectionResult;
import dockit.com.app.dockit.Entity.Result.OrderLocationResult;
import dockit.com.app.dockit.Entity.Result.OrderResult;
import dockit.com.app.dockit.Repository.OrderRepository;

public class OrderSummaryViewModel extends AndroidViewModel {
    private LiveData<List<OrderResult>> liveOrderResults;
    private int maxOrderLocations = 50; //TODO: Persist in DB
    private int orderId = 1;
    List<String> groupedItems;

    private OrderRepository orderRepository;

    public OrderSummaryViewModel(@NonNull Application application) {
        super(application);

        this.orderRepository = new OrderRepository(application);

        liveOrderResults = orderRepository.getLiveAll();
    }


//    public LiveData<OrderResult> RetrieveOrderInfo(int id) {
//        return orderRepository.retrieveOrder(id);
//    }

    public String GetTable(OrderResult order) {
        return order.getOrderTable();
    }

    public String GetComments(OrderResult order) {
        return order.getComments();
    }

    public String GetCovers(OrderResult order) {
        List<OrderLocationResult> orderLocationResults = order.orderLocationResults;
        return Integer.toString(orderLocationResults.size());
    }

    public List<MenuResult> GetMenu(OrderResult order) {
        List<OrderLocationResult> orderLocationResults = order.orderLocationResults;
        return orderLocationResults.get(0).menus;
    }




    //Quick and dirty attempt at getting ordered items and grouping them, will be changed to
    //be nicer and cleaner, will collect and group items by menu section

    //Grouping plan,
    //orderResult has a list of OrderLocationResult olr
    //each olr contains list of MenuResults mr (a list of menus)
    //each mr contains a list of MenuSectionResult items (msr) (the results of each menu section)
    //each msr contains a list of menu items mi
    //so
    public List<SummaryItemView> GroupMenu(OrderResult order) {
       List<SummaryItemView> groupMenu = new List<SummaryItemView>() {
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
           public Iterator<SummaryItemView> iterator() {
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
           public boolean add(SummaryItemView summaryItemView) {
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
           public boolean addAll(@NonNull Collection<? extends SummaryItemView> collection) {
               return false;
           }

           @Override
           public boolean addAll(int i, @NonNull Collection<? extends SummaryItemView> collection) {
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
           public SummaryItemView get(int i) {
               return null;
           }

           @Override
           public SummaryItemView set(int i, SummaryItemView summaryItemView) {
               return null;
           }

           @Override
           public void add(int i, SummaryItemView summaryItemView) {

           }

           @Override
           public SummaryItemView remove(int i) {
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
           public ListIterator<SummaryItemView> listIterator() {
               return null;
           }

           @NonNull
           @Override
           public ListIterator<SummaryItemView> listIterator(int i) {
               return null;
           }

           @NonNull
           @Override
           public List<SummaryItemView> subList(int i, int i1) {
               return null;
           }
       };
        // 1. retrieve olr list
        List<OrderLocationResult> olr = order.orderLocationResults;
        // 2. loop through olr list, retrieve list of mr
        //Will retrieve menu name here for multiple menu items
        for (int i = 0; i<olr.size(); i++) {
            List<MenuResult> mr = olr.get(i).menus;

            // 3. loop through mr, retrieve list of msr
            // BUT on 1st mr loop create section headings and not on any subsequent loops,
            //even if person doesn't order off certain menu there is a null item saved
            for (int j = 0; j<mr.size(); j++) {
                List<MenuSectionResult> msr = mr.get(j).menuSectionResults;
                // 4. loop through msr, retrieve list of menuItems mi
                for (int k = 0; k<msr.size(); k++) {
                    SummaryItemView sectionView = new SummaryItemView(msr.get(k));
                    if (!groupMenu.contains(sectionView)) {
                        groupMenu.add(sectionView);
                    }

                    List<MenuItem> mi = msr.get(k).menuItemList;

                    // 5. loop through mi, creating MenuItemView for each item and adding to the list
                    // after the correct section heading
                    for (int l = 0; l<mi.size(); l++) {
                        SummaryItemView menuItem = new SummaryItemView(mi.get(l));
                        int index = groupMenu.indexOf(menuItem);
                        //if groupMenu does not already contain this item
                        if (index == -1) {
                            int sectionIndex = groupMenu.indexOf(sectionView);
                            groupMenu.add(sectionIndex+1, menuItem);
                        }
                        //if it doesn't, use index to retrieve item and double its count
                        else {
                            groupMenu.get(index).incrementCount();
                        }
                    }
                }
            }

        }

        return groupMenu;
    }
}
