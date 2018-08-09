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

import java.util.List;

import dockit.com.app.dockit.Entity.Decorator.OrderLocationView;
import dockit.com.app.dockit.Entity.OrderLocation;
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

    private List<OrderLocationView> orderLocationViews;
    private LayoutInflater layoutInflater;
    private OrderLocation selectedOrderLocation;

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

        if (orderLocationViews != null) {
            final OrderLocationView orderLocationView = orderLocationViews.get(position);
            String textValue = orderLocationView.getLocationText();
            holder.textView.setText(textValue);

            //Is currently selected
            if(orderLocationView.isSelected()) {
                holder.cardView.setCardBackgroundColor(Color.BLUE);
                holder.textView.setTextColor(Color.WHITE);
            }
            //Already exists
            else if(orderLocationView.isCreated()) {
                holder.cardView.setCardBackgroundColor(Color.RED);
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
        if(orderLocationViews != null) {
            return orderLocationViews.size();
        }
        return 0;
    }

    public void setOrderLocationViews(List<OrderLocationView> orderLocationViews) {
        this.orderLocationViews = orderLocationViews;
    }

    public OrderLocationView getItemAtPosition(int position) {
        if(orderLocationViews != null) {
            return orderLocationViews.get(position);
        }
        return null;
    }
}
