package dockit.com.app.dockit.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import dockit.com.app.dockit.Entity.Decorator.OptionsItemView;
import dockit.com.app.dockit.Entity.Decorator.SummaryItemView;
import dockit.com.app.dockit.Entity.IngredientItem;
import dockit.com.app.dockit.Entity.MandatoryItem;
import dockit.com.app.dockit.Entity.OptionalItem;
import dockit.com.app.dockit.Entity.Result.MenuItemResult;
import dockit.com.app.dockit.Entity.Result.MenuResult;
import dockit.com.app.dockit.Entity.Result.MenuSectionResult;
import dockit.com.app.dockit.Entity.Result.OrderLocationResult;
import dockit.com.app.dockit.Entity.Result.OrderResult;
import dockit.com.app.dockit.Repository.OrderRepository;
import dockit.com.app.dockit.Tasks.ValidateOrder;

public class OrderSummaryViewModel extends AndroidViewModel {
    private LiveData<List<OrderResult>> liveOrderResults;
    private int maxOrderLocations = 50; //TODO: Persist in DB
    private int orderId = 1;
    List<String> groupedItems;

    private OrderRepository orderRepository;
    private ValidateOrder validateOrder;

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
                            }
                            //if it does, use index to retrieve item and double its count
                            else {
                                summaryItemView = groupMenu.get(index);
                                groupMenu.get(index).incrementCount();

                            }
                        }
                        setOptionsItemView(summaryItemView, menuItems.get(l));

                    }
                }
            }

        }
        return groupMenu;
    }

    public void ValidOrder (List<SummaryItemView> menu) {
        int index = validateOrder.ValidateOrder(menu);
        if (index == -1) {

        }
        else {
            menu.get(index).setHighlighting(true);
        }
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

    private void setOptionsItemView(SummaryItemView summaryItemView, MenuItemResult menuItemResult) {

        boolean isMandatory = false;
        for(MandatoryItem mandatoryItem : menuItemResult.mandatoryItems) {

            if(mandatoryItem.isSelected()) {
                isMandatory = true;

                if(menuItemResult.ingredientItems.size() > 0) {
                    for(IngredientItem ingredientItem : menuItemResult.ingredientItems) {
                        if(!ingredientItem.isSelected()) {
                            String appendedName = mandatoryItem.getName() + " No " + ingredientItem.getName();
                            mandatoryItem.setName(appendedName);
                        }
                    }
                }

                if(menuItemResult.optionalItems.size() > 0) {
                    for(OptionalItem optionalItem : menuItemResult.optionalItems) {
                        if(optionalItem.isSelected()) {
                            String appendedName = mandatoryItem.getName() + " " + optionalItem.getName();
                            mandatoryItem.setName(appendedName);
                        }
                    }
                }

                OptionsItemView optionsItemView = new OptionsItemView();
                optionsItemView.setName(mandatoryItem.getName());
                optionsItemView.setCount(optionsItemView.getCount() + 1);

                if(summaryItemView.optionsItemViewList.size() == 0) {
                    summaryItemView.optionsItemViewList.add(optionsItemView);
                }
                else {
                    for (int idx = 0; idx < summaryItemView.optionsItemViewList.size(); idx ++) {
                        OptionsItemView optionsItemViewExisting = summaryItemView.optionsItemViewList.get(idx);
                        if (optionsItemViewExisting.getName().equals(optionsItemView.getName())) {
                            optionsItemViewExisting.setCount(optionsItemViewExisting.getCount() + 1);
                            break;
                        } else {
                            summaryItemView.optionsItemViewList.add(optionsItemView);
                            break;
                        }
                    }
                }
            }
        }
        if(!isMandatory) {
            setOptionalItemView(summaryItemView, menuItemResult);
        }
    }

    private void setOptionalItemView(SummaryItemView summaryItemView, MenuItemResult menuItemResult) {

        boolean isOptionalItems = false;
        OptionsItemView optionsItemView = new OptionsItemView();
        for(OptionalItem optionalItem : menuItemResult.optionalItems) {
            if(optionalItem.isSelected()) {
                isOptionalItems = true;
                String appendedName = optionsItemView.getName() + " " + optionalItem.getName();
                optionsItemView.setName(appendedName);
            }
        }

        for(IngredientItem ingredientItem : menuItemResult.ingredientItems) {
            if(!ingredientItem.isSelected()) {
                isOptionalItems = true;
                String appendedName = optionsItemView.getName() + " No " + ingredientItem.getName();
                optionsItemView.setName(appendedName);


            }
        }

        if(isOptionalItems) {
            optionsItemView.setCount(optionsItemView.getCount() + 1);
        }
        groupOptionsItemView(summaryItemView, optionsItemView);

    }

    private void groupOptionsItemView(SummaryItemView summaryItemView, OptionsItemView optionsItemView) {
        if(summaryItemView.optionsItemViewList.size() == 0) {
            summaryItemView.optionsItemViewList.add(optionsItemView);
        }
        else {
            for (int idx = 0; idx < summaryItemView.optionsItemViewList.size(); idx ++) {
                OptionsItemView optionsItemViewExisting = summaryItemView.optionsItemViewList.get(idx);
                if (optionsItemViewExisting.getName().equals(optionsItemView.getName())) {
                    optionsItemViewExisting.setCount(optionsItemViewExisting.getCount() + 1);
                    break;
                } else {
                    summaryItemView.optionsItemViewList.add(optionsItemView);
                    break;
                }
            }
        }
    }

}
