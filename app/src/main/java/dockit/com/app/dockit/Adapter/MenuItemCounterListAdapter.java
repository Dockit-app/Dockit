package dockit.com.app.dockit.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import dockit.com.app.dockit.Entity.Decorator.MenuItemView;
import dockit.com.app.dockit.Entity.MenuItem;
import dockit.com.app.dockit.Entity.Result.MenuItemResult;
import dockit.com.app.dockit.R;

/**
 * Created by michael on 28/07/18.
 */

public class MenuItemCounterListAdapter extends RecyclerView.Adapter<MenuItemCounterListAdapter.MenuItemViewHolder> {

    class MenuItemViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout menuItem;
        private TextView description;
        private TextView ingredients;
        private LinearLayout itemCounter;
        private TextView counter;


        public MenuItemViewHolder(View itemView) {
            super(itemView);

            this.description = itemView.findViewById(R.id.description);
            this.ingredients = itemView.findViewById(R.id.ingredients);
            this.menuItem = itemView.findViewById(R.id.menu_item);
            this.itemCounter = itemView.findViewById(R.id.item_counter);
            this.counter = itemView.findViewById(R.id.counter);
        }
    }

    List<MenuItemView> menuItems;
    LayoutInflater inflater;
    private boolean counter = false;
    private boolean counterChecked = false;

    public MenuItemCounterListAdapter(Context context, List<MenuItemView> menuItems) {
        this.menuItems = menuItems;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MenuItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = inflater.inflate(R.layout.menu_item_list, parent, false);
        return new MenuItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuItemViewHolder holder, int position) {

        MenuItemView menuItem = menuItems.get(position);
        holder.description.setText(menuItem.getDescription());

        if(menuItem.isSection()) {
            holder.ingredients.setVisibility(View.GONE);
            holder.itemCounter.setVisibility(View.GONE);
        }
        else {
            if(counterChecked) {
                if (counter) {
                    holder.itemCounter.setVisibility(View.VISIBLE);
                    int counterValue = menuItem.getCounter() == null ? 0 : menuItem.getCounter();
                    holder.counter.setText((Integer.toString(counterValue)));
                } else {
                    holder.itemCounter.setVisibility(View.INVISIBLE);
                }
            }

            holder.ingredients.setVisibility(View.VISIBLE);
            holder.ingredients.setText("("+menuItem.getIngredients()+")");
        }

        if(menuItem.isSelected()) {
            holder.menuItem.setBackgroundColor(Color.BLUE);
        }
        else {
            holder.menuItem.setBackgroundColor(Color.WHITE);
        }


    }

    @Override
    public int getItemCount() {
        if(menuItems != null) {
            return menuItems.size();
        }
        return 0;
    }

    public void setCounter(boolean counter) {
        counterChecked = true;
        this.counter = counter;
    }

    public MenuItemResult getItemAtPosition(int position) {
        return menuItems.get(position);
    }
}
