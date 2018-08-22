package dockit.com.app.dockit.ClickListener;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import dockit.com.app.dockit.Adapter.OrderLocationListAdapter;
import dockit.com.app.dockit.Entity.OrderLocation;
import dockit.com.app.dockit.ViewModel.OrderViewModel;

/**
 * Created by michael on 28/07/18.
 */

public class OrderLocationClickBuilder {

    private OrderViewModel orderViewModel;
    private OrderLocationListAdapter orderLocationListAdapter;
    private int orderId = 0;

    public OrderLocationClickBuilder(OrderViewModel orderViewModel, OrderLocationListAdapter orderLocationListAdapter) {
        this.orderViewModel = orderViewModel;
        this.orderLocationListAdapter = orderLocationListAdapter;
    }

    public void setOnClickListener(RecyclerView view) {
        view.addOnItemTouchListener(new RecyclerViewClickListener(view.getContext(), view, new RecyclerViewClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                OrderLocation orderLocation = orderLocationListAdapter.getItemAtPosition(position);
                if(orderLocation != orderViewModel.getLiveSelectedOrderLocation().getValue()) {

                    if (orderLocation.getId() == null) {
                        int locationNumber = position + 1;
                        Log.i(OrderLocationClickBuilder.class.getSimpleName(), "New location " + locationNumber + " clicked");
                        orderLocation.setLocationNumber(locationNumber);
                        orderLocation.setLocationText(Integer.toString(locationNumber));
                        orderLocation.setSelected(1);
                        orderLocation.setOrderId(orderId);
                        orderViewModel.createOrderLocation(view.getContext(), orderLocation);
                    } else {
                        orderLocation.setSelected(1);
                        orderLocation.setLocationText(orderLocation.getLocationText());
                        Log.i(OrderLocationClickBuilder.class.getSimpleName(), "Existing location " + orderLocation.getLocationNumber() + " clicked");
                        orderViewModel.updateOrderLocation(orderLocation);
                    }
                }

            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
}
