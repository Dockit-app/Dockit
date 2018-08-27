package dockit.com.app.dockit.Tasks;

import java.util.List;

import dockit.com.app.dockit.Entity.Decorator.SummaryItemView;

public class ValidateOrder {


    public int ValidateOrder(List<SummaryItemView> menu) {
        int lowestSection = 100; //temporary value
        int sectionIndex = 0;
        int currentSectionCount = 0;
        int currentSectionIndex = 0;
        boolean allSectionsEqual = true;
        for (int i = 0; i < menu.size(); i++) {
            SummaryItemView item = menu.get(i);
            if (item.isSection()) {
                if (i != 0) {
                    if (currentSectionCount < lowestSection) {
                        if (currentSectionIndex != 0) { allSectionsEqual = false; }
                        lowestSection = currentSectionCount;
                        sectionIndex = currentSectionIndex;
                    }
                    currentSectionCount = 0;
                    currentSectionIndex = i;
                }

            }
            else {
                currentSectionCount += item.getCount();
            }
        }
        if (allSectionsEqual) {
            return -1;
        }
        else {
            return sectionIndex;
        }
    }

    //take in group menu
    //loop through the results
    //for each section, retrieve the count for each item (including null items)
    //Also keep the index of that section
    //at end of each section compare total count to the previous section total count
    //if it is lower set the lowest section count to the total count
    //and save the index of said section
    //if not, continue

    //at end of loop, return index of section with the lowest count
    //the viewmodel will set the sectionItem.setHighlighted to true

}
