package dockit.com.app.dockit.ListAdapter;

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
import dockit.com.app.dockit.Repository.OrderLocationRepository;

/**
 * Created by michael on 25/07/18.
 */

public class OrderLocationListAdapter extends RecyclerView.Adapter<OrderLocationListAdapter.OrderLocationViewHolder> {

    class OrderLocationViewHolder extends RecyclerView.ViewHolder {

        private final TextView textView;

        public OrderLocationViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.order_location_text);
        }
    }

    private List<OrderLocation> orderLocations;
    private LayoutInflater layoutInflater;
    private OrderLocationRepository orderLocationRepository;

    public OrderLocationListAdapter(Context context, int OrderId) {
        layoutInflater = LayoutInflater.from(context);
        orderLocationRepository = new OrderLocationRepository(context);
    }

    @NonNull
    @Override
    public OrderLocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = layoutInflater.inflate(R.layout.order_location_list_item, parent, false);
        return new OrderLocationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderLocationViewHolder holder, int position) {

        String textValue = Integer.toString(orderLocations.get(position).getLocationNumber());
        holder.textView.setText(textValue);

    }

    @Override
    public int getItemCount() {
        if(orderLocations != null) {
            return orderLocations.size();
        }
        return 0;
    }

}
