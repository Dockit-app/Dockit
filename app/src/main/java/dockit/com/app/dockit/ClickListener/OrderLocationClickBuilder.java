package dockit.com.app.dockit.ClickListener;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by michael on 28/07/18.
 */

public class OrderLocationClickBuilder {

    public void setOnClickListener(RecyclerView view) {
        view.addOnItemTouchListener(new RecyclerViewClickListener(view.getContext(), view, new RecyclerViewClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
    }
}
