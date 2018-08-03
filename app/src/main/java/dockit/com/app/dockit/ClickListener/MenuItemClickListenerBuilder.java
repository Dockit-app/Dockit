package dockit.com.app.dockit.ClickListener;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by michael on 03/08/18.
 */

public class MenuItemClickListenerBuilder {

    public void setOnClickListener(RecyclerView view) {
        view.addOnItemTouchListener(new RecyclerViewClickListener(view.getContext(), view, new RecyclerViewClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if(view.getTag() != null && view.getTag().equals("isClicked")) {
                    view.setBackgroundColor(Color.WHITE);
                    view.setTag("notClicked");
                }
                else {
                    view.setBackgroundColor(Color.BLUE);
                    view.setTag("isClicked");
                }
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
    }
}
