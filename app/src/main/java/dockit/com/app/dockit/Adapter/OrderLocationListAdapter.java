package dockit.com.app.dockit.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import dockit.com.app.dockit.Entity.OrderLocation;
import dockit.com.app.dockit.Entity.Result.OrderLocationResult;
import dockit.com.app.dockit.R;

/**
 * Created by michael on 25/07/18.
 */

public class OrderLocationListAdapter extends RecyclerView.Adapter<OrderLocationListAdapter.OrderLocationViewHolder> {

    class OrderLocationViewHolder extends RecyclerView.ViewHolder {

        private final TextView textView;
        private final CardView cardView;

        public OrderLocationViewHolder(View itemView) {
            super(itemView);
            this.textView = itemView.findViewById(R.id.order_location_text);
            this.cardView = itemView.findViewById(R.id.order_location_card);
        }
    }

    private List<OrderLocation> orderLocations;
    private LayoutInflater layoutInflater;
    private int selectedOrderLocationId = 0;

    public OrderLocationListAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public OrderLocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = layoutInflater.inflate(R.layout.order_location_list_item, parent, false);

        return new OrderLocationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final @NonNull OrderLocationViewHolder holder,final int position) {

        if (orderLocations != null) {
            final OrderLocation orderLocation = orderLocations.get(position);
            String textValue = orderLocation.getLocationText();
            holder.textView.setText(textValue);

            //Is currently selected
            if(orderLocation.getSelected() == 1) {
                holder.cardView.setCardBackgroundColor(Color.BLACK);
                holder.textView.setTextColor(Color.WHITE);
            }
            //Already exists
            else if(orderLocation.getId() != null && orderLocation.getId() > 0) {
                holder.cardView.setCardBackgroundColor(Color.DKGRAY);
                holder.textView.setTextColor(Color.WHITE);
            }
            //New
            else {
                holder.cardView.setCardBackgroundColor(Color.WHITE);
                holder.textView.setTextColor(Color.BLACK);
            }
        }

    }
    @Override
    public int getItemCount() {
        if(orderLocations != null) {
            return orderLocations.size();
        }
        return 0;
    }

    public boolean setOrderLocationResults(List<OrderLocationResult> orderLocationResults) {
        boolean isValidList = false;
        for(OrderLocationResult orderLocationResult : orderLocationResults) {
            if(orderLocationResult.getSelected() == 1) {
                isValidList = true;
                selectedOrderLocationId = orderLocationResult.getId();
            }

            if(orderLocations != null) {
                for (int idx = 0; idx < orderLocations.size(); idx++) {
                    if (orderLocations.get(idx).getLocationNumber().equals(orderLocationResult.getLocationNumber())) {
                        orderLocations.set(idx, new OrderLocation(orderLocationResult));
                    }
                }
            }
        }

        if(isValidList) {
            notifyDataSetChanged();
        }

        return isValidList;

    }

    public boolean setOrderLocations(List<OrderLocation> orderLocations) {
        boolean isValidList = false;

        for(OrderLocation orderLocation : orderLocations) {
            if(orderLocation.getSelected() == 1) {
                isValidList = true;
                if(orderLocation.getId() != null) {
                    selectedOrderLocationId = orderLocation.getId();
                }
            }

        }

        if(isValidList) {
            this.orderLocations = orderLocations;
            notifyDataSetChanged();
        }

        return isValidList;

    }

    public OrderLocation getItemAtPosition(int position) {
        if(orderLocations != null) {
            return orderLocations.get(position);
        }
        return null;
    }

    public void setFirstSelectedOrderLocationId(int id) {

        selectedOrderLocationId = id;
    }

    public int getSelectedOrderLocationId() {
        return selectedOrderLocationId;
    }


//    public void notifyCustomChanged() {
//        notifyDataSetChanged();
//        for(OrderLocation orderLocation : orderLocations) {
//            if(orderLocation.getSelected() == 1) {
//                selectedOrderLocationId = orderLocation.getId();
//            }
//        }
//    }
}
