package dockit.com.app.dockit.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import dockit.com.app.dockit.Entity.Decorator.MandatoryItemView;
import dockit.com.app.dockit.Entity.Decorator.MenuItemView;
import dockit.com.app.dockit.Entity.Decorator.SummaryItemView;
import dockit.com.app.dockit.R;

public class SummaryListAdapter extends RecyclerView.Adapter<SummaryListAdapter.SummaryItemViewHolder> {

    class SummaryItemViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout menuSection;
        private TextView name;
        private CardView menuItem;
        private TextView description;
        private RelativeLayout menuItemBackground;
        private TextView options;

        public SummaryItemViewHolder(View itemView) {
            super(itemView);

            this.menuSection = itemView.findViewById(R.id.menu_section);
            this.name = itemView.findViewById(R.id.name);
            this.menuItem = itemView.findViewById(R.id.menu_item);
            this.description = itemView.findViewById(R.id.description);
            this.menuItemBackground = itemView.findViewById(R.id.menu_item_background);
            this.options = itemView.findViewById(R.id.options);
        }
    }

    List<SummaryItemView> menuItems;
    LayoutInflater inflater;

    public SummaryListAdapter(Context context, List<SummaryItemView> menuItems) {
        this.menuItems = menuItems;
        inflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public SummaryItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.order_summary_list, parent, false);
        return new SummaryItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SummaryItemViewHolder holder, int position) {
        SummaryItemView menuItem = menuItems.get(position);

        if(menuItem.isSection()) {
            holder.menuItem.setVisibility(View.GONE);
            holder.menuSection.setVisibility(View.VISIBLE);
            holder.name.setText(menuItem.getDescription());
        }
        else {
            holder.menuSection.setVisibility(View.GONE);
            holder.menuItem.setVisibility(View.VISIBLE);
            holder.description.setText(Integer.toString(menuItem.getCount()) + " x " + menuItem.getDescription());

            String options = "";
            for(MandatoryItemView mandatoryItemView : menuItem.mandatoryItemViewList) {
                options = options.concat(Integer.toString(mandatoryItemView.getCount()) + " x " + mandatoryItemView.getName() + "  ");
            }

            holder.options.setText(options);

            if(position % 2 != 0 && !menuItem.isSection()) {
                holder.menuItemBackground.setBackgroundColor(Color.LTGRAY);
            }
            else {
                holder.menuItemBackground.setBackgroundColor(Color.WHITE);
            }
        }

    }

    @Override
    public int getItemCount() {
        if(menuItems != null) {
            return menuItems.size();
        }
        return 0;
    }

}
