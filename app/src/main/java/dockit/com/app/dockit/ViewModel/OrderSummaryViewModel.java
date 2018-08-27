package dockit.com.app.dockit.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import dockit.com.app.dockit.Entity.Decorator.MandatoryItemView;
import dockit.com.app.dockit.Entity.Decorator.SummaryItemView;
import dockit.com.app.dockit.Entity.MandatoryItem;
import dockit.com.app.dockit.Entity.MenuItem;
import dockit.com.app.dockit.Entity.Result.MenuItemResult;
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
    //Currently works for one menu, need to make work for multiple

    //Grouping plan,
    //orderResult has a list of OrderLocationResult olr
    //each olr contains list of MenuResults mr (a list of menus)
    //each mr contains a list of MenuSectionResult items (msr) (the results of each menu section)
    //each msr contains a list of menu items mi
    //so
    public List<SummaryItemView> GroupMenuItems(OrderResult order) {
        List<SummaryItemView> groupMenu = new ArrayList<SummaryItemView>();
        List<OrderLocationResult> orderLocationResults = order.orderLocationResults;
        // 2. loop through olr list, retrieve list of mr
        //Will retrieve menu name here for multiple menu items
        for (int i = 0; i<orderLocationResults.size(); i++) {
            List<MenuResult> menuResults = orderLocationResults.get(i).menus;

            // 3. loop through mr, retrieve list of msr
            // BUT on 1st mr loop create section headings and not on any subsequent loops,
            //even if person doesn't order off certain menu there is a null item saved
            for (int j = 0; j<menuResults.size(); j++) {
                List<MenuSectionResult> menuSectionResults = menuResults.get(j).menuSectionResults;
                // 4. loop through msr, retrieve list of menuItems mi
                for (int k = 0; k<menuSectionResults.size(); k++) {
                    SummaryItemView sectionView = new SummaryItemView(menuSectionResults.get(k));
                    int sectionIndex = SummarySearch(groupMenu, sectionView);

                    if (sectionIndex == -1) {
                        groupMenu.add(sectionView);
                    }

                    List<MenuItemResult> menuItems = menuSectionResults.get(k).menuItemResultList;


                    // 5. loop through mi, creating MenuItemView for each item and adding to the list
                    // after the correct section heading
                    for (int l = 0; l<menuItems.size(); l++) {
                        SummaryItemView summaryItemView = new SummaryItemView(menuItems.get(l));
                        summaryItemView.setSectionName(sectionView.getDescription());
                        if(summaryItemView.isSelected()) {
                            int index = SummarySearch(groupMenu, summaryItemView);
                            //if groupMenu does not already contain this item
                            if (index == -1) {
                                sectionIndex = SummarySearch(groupMenu, sectionView);
                                groupMenu.add(sectionIndex + 1, summaryItemView);
                                setMandatoryItemView(summaryItemView, menuItems.get(l));
                            }
                            //if it does, use index to retrieve item and double its count
                            else {
                                groupMenu.get(index).incrementCount();
                                setMandatoryItemView(groupMenu.get(index), menuItems.get(l));
                            }
                        }


                    }
                }
            }

        }

        return groupMenu;
    }

    //Searches the Summary List for an item, and if found returns the index, if not found returns -1
    private int SummarySearch(List<SummaryItemView> summary, SummaryItemView item) {
        for (int i = 0; i < summary.size(); i++) {
            SummaryItemView next = summary.get(i);
            if (next.getSectionName().equals(item.getSectionName()) && next.getDescription().contentEquals(item.getDescription())) {
                return i;
            }
        }
        return -1;
    }

    private void setMandatoryItemView(SummaryItemView summaryItemView, MenuItemResult menuItemResult) {

        for(MandatoryItem mandatoryItem : menuItemResult.mandatoryItems) {
            if(mandatoryItem.isSelected()) {
                MandatoryItemView mandatoryItemView = new MandatoryItemView();
                mandatoryItemView.setName(mandatoryItem.getName());
                mandatoryItemView.setCount(mandatoryItemView.getCount() + 1);

                if(summaryItemView.mandatoryItemViewList.size() == 0) {
                    summaryItemView.mandatoryItemViewList.add(mandatoryItemView);
                }
                else {
                    for (int idx = 0; idx < summaryItemView.mandatoryItemViewList.size(); idx ++) {
                        MandatoryItemView mandatoryItemViewExisting = summaryItemView.mandatoryItemViewList.get(idx);
                        if (mandatoryItemViewExisting.getName().equals(mandatoryItemView.getName())) {
                            mandatoryItemViewExisting.setCount(mandatoryItemViewExisting.getCount() + 1);
                            break;
                        } else {
                            summaryItemView.mandatoryItemViewList.add(mandatoryItemView);
                            break;
                        }
                    }
                }
            }
        }
    }

}
