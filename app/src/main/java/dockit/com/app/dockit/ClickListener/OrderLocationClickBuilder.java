package dockit.com.app.dockit.ClickListener;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import dockit.com.app.dockit.Adapter.OrderLocationListAdapter;
import dockit.com.app.dockit.Entity.Decorator.OrderLocationView;
import dockit.com.app.dockit.ViewModel.OrderViewModel;

/**
 * Created by michael on 28/07/18.
 */

public class OrderLocationClickBuilder {

    private OrderViewModel orderViewModel;
    private OrderLocationListAdapter orderLocationListAdapter;

    public OrderLocationClickBuilder(OrderViewModel orderViewModel, OrderLocationListAdapter orderLocationListAdapter) {
        this.orderViewModel = orderViewModel;
        this.orderLocationListAdapter = orderLocationListAdapter;
    }

    public void setOnClickListener(RecyclerView view) {
        view.addOnItemTouchListener(new RecyclerViewClickListener(view.getContext(), view, new RecyclerViewClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                OrderLocationView orderLocationView = orderLocationListAdapter.getItemAtPosition(position);

                if(!orderLocationView.isCreated()) {
                    int locationNumber = position + 1;
                    Log.d(OrderLocationClickBuilder.class.getSimpleName(), "New location " + locationNumber + " clicked");
                    orderLocationView.setLocationNumber(locationNumber);
                    orderLocationView.setLocationText(Integer.toString(locationNumber));
                    orderLocationView.setSelected(true);
                    orderLocationView.setCreated(true);
                    orderViewModel.createOrderLocation(view.getContext(), orderLocationView);
                }
                else {
                    orderLocationView.setSelected(true);
                    Log.d(OrderLocationClickBuilder.class.getSimpleName(), "Existing location " + orderLocationView.getLocationNumber() + " clicked");
                    orderViewModel.updateOrderLocationView(orderLocationView);
                }

                orderLocationListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
    }
}
