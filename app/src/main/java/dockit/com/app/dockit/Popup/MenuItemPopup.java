package dockit.com.app.dockit.Popup;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

import dockit.com.app.dockit.R;

/**
 * Created by michael on 22/08/18.
 */

public class MenuItemPopup {

    public static void openMandatoryPopup(Context context, View parentView) {
        LayoutInflater inflater = LayoutInflater.from(context);

        int menuResourceId = R.layout.menu_item_mandatory;

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

    public static void openOptionalPopup(Context context, View parentView) {
        LayoutInflater inflater = LayoutInflater.from(context);

        View menuOptions = inflater.inflate(R.layout.menu_item_optional, null, false);

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
