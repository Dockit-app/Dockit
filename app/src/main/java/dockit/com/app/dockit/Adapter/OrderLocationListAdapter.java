package dockit.com.app.dockit.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import dockit.com.app.dockit.Entity.OrderLocation;
import dockit.com.app.dockit.R;

/**
 * Created by michael on 25/07/18.
 */

public class OrderLocationListAdapter extends RecyclerView.Adapter<OrderLocationListAdapter.OrderLocationViewHolder> {

    class OrderLocationViewHolder extends RecyclerView.ViewHolder {

        private final TextView textView;
        private final View itemView;

        public OrderLocationViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.order_location_text);
            this.itemView = itemView;
        }
    }

    private List<OrderLocation> orderLocations;
    private LayoutInflater layoutInflater;

    public OrderLocationListAdapter(Context context, List<OrderLocation> orderLocations) {
        layoutInflater = LayoutInflater.from(context);
        this.orderLocations = orderLocations;
    }

    @NonNull
    @Override
    public OrderLocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = layoutInflater.inflate(R.layout.order_location_list_item, parent, false);

        return new OrderLocationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final @NonNull OrderLocationViewHolder holder,final int position) {

        final OrderLocation orderLocation = orderLocations.get(position);
        String textValue = orderLocation.getLocationText();
        holder.textView.setText(textValue);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Update text to number
                if(!orderLocation.isSelected()) {
                    orderLocation.setLocationText(Integer.toString(orderLocation.getLocationNumber()));
                    holder.textView.setText(orderLocation.getLocationText());
                    orderLocation.setSelected(true);

                    notifyDataSetChanged();

                    //TODO: create new menu
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        if(orderLocations != null) {
            return orderLocations.size();
        }
        return 0;
    }

}
