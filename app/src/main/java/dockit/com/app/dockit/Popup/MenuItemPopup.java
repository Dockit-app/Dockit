package dockit.com.app.dockit.Popup;

import android.content.Context;
import android.graphics.Point;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.List;

import dockit.com.app.dockit.Adapter.IngredientSwitchListAdapter;
import dockit.com.app.dockit.Adapter.OptionalSwitchListAdapter;
import dockit.com.app.dockit.Entity.IngredientItem;
import dockit.com.app.dockit.Entity.MandatoryItem;
import dockit.com.app.dockit.Entity.OptionalItem;
import dockit.com.app.dockit.Entity.Result.MenuItemResult;
import dockit.com.app.dockit.R;
import dockit.com.app.dockit.ViewModel.MenuItemViewModel;

/**
 * Created by michael on 22/08/18.
 */

public class MenuItemPopup {

    public static void openMandatoryPopup(Context context,View parentView, MenuItemResult menuItemResult, MenuItemViewModel menuItemViewModel) {

        LayoutInflater inflater = LayoutInflater.from(context);
        int menuResourceId = R.layout.menu_item_mandatory;
        View menuOptions = inflater.inflate(menuResourceId, null, false);
        Display display = parentView.getDisplay();
        Point size = new Point();
        display.getSize(size);

        final PopupWindow popupWindow = new PopupWindow(menuOptions, size.x / 2, size.y / 5, true);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAtLocation(parentView, Gravity.CENTER, 0,0);

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                ((View) parentView).setAlpha(1f);
            }
        });


        RadioGroup radioGroup = menuOptions.findViewById(R.id.options);

        setRadioButtonListener(radioGroup, menuItemResult, menuItemViewModel, inflater, popupWindow, parentView);


        ((View)parentView).setAlpha(0.3f);
    }

    private static void setRadioButtonListener(RadioGroup radioGroup, MenuItemResult menuItemResult, MenuItemViewModel menuItemViewModel, LayoutInflater inflater, final PopupWindow popupWindow, final View parentView) {
        for(MandatoryItem mandatoryItem : menuItemResult.mandatoryItems) {
            RadioButton radioButton = (RadioButton)inflater.inflate(R.layout.radio_button, null);
            radioButton.setGravity(Gravity.CENTER);
            radioButton.setText(mandatoryItem.getName());
            radioButton.setId(mandatoryItem.getId());
            radioButton.setChecked(mandatoryItem.isSelected());
            radioGroup.addView(radioButton);
        }

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {


                for(MandatoryItem mandatoryItem : menuItemResult.mandatoryItems) {

                    if(checkedId == mandatoryItem.getId()) {
                        mandatoryItem.setSelected(true);
                    }
                    else {
                        mandatoryItem.setSelected(false);
                    }
                }

                menuItemViewModel.updateMandatory(menuItemResult.mandatoryItems);
            }
        });
    }

    public static void openOptionalPopup(Context context, View parentView, MenuItemResult menuItemResult, MenuItemViewModel menuItemViewModel) {
        LayoutInflater inflater = LayoutInflater.from(context);

        View menuOptions = inflater.inflate(R.layout.menu_item_optional, null, false);

        Display display = parentView.getDisplay();
        Point size = new Point();
        display.getSize(size);

        PopupWindow popupWindow = new PopupWindow(menuOptions, size.x / 2, size.y / 3, true);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAtLocation(parentView, Gravity.CENTER, 0,0);

        setOptionalRecyclerView(menuOptions, menuItemResult.optionalItems, menuItemViewModel);
        setIngredientRecyclerView(menuOptions, menuItemResult.ingredientItems, menuItemViewModel);

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {

                ((View)parentView).setAlpha(1f);
            }
        });

        ((View)parentView).setAlpha(0.3f);
    }

    private static void setOptionalRecyclerView(View menuOptions, List<OptionalItem> optionalItemList, MenuItemViewModel menuItemViewModel) {
        RecyclerView recyclerView = menuOptions.findViewById(R.id.optional_item_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(menuOptions.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setAdapter(new OptionalSwitchListAdapter(menuOptions.getContext(), optionalItemList, menuItemViewModel));
    }

    private static void setIngredientRecyclerView(View menuOptions, List<IngredientItem> ingredientItemList, MenuItemViewModel menuItemViewModel) {
        RecyclerView recyclerView = menuOptions.findViewById(R.id.ingredient_item_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(menuOptions.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setAdapter(new IngredientSwitchListAdapter(menuOptions.getContext(), ingredientItemList, menuItemViewModel));
    }

    public static void openMandatoryCounterPopup(Context context, View parentView) {
        LayoutInflater inflater = LayoutInflater.from(context);

        int menuResourceId = R.layout.menu_item_mandatory_multiple;

        View menuOptions = inflater.inflate(menuResourceId, null, false);

        Display display = parentView.getDisplay();
        Point size = new Point();
        display.getSize(size);

        PopupWindow popupWindow = new PopupWindow(menuOptions, size.x / 2, size.y / 5, true);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAtLocation(parentView, Gravity.CENTER, 0,0);

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                ((View)parentView).setAlpha(1f);
            }
        });

        ((View)parentView).setAlpha(0.3f);
    }
}
