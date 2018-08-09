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

import dockit.com.app.dockit.Entity.MenuItem;
import dockit.com.app.dockit.R;

/**
 * Created by michael on 28/07/18.
 */

public class MenuItemListAdapter extends RecyclerView.Adapter<MenuItemListAdapter.MenuItemViewHolder> {

    class MenuItemViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout menuItem;
        private TextView description;
        private TextView ingredients;

        public MenuItemViewHolder(View itemView) {
            super(itemView);

            this.description = itemView.findViewById(R.id.description);
            this.ingredients = itemView.findViewById(R.id.ingredients);
            this.menuItem = itemView.findViewById(R.id.menu_item);
        }
    }

    List<MenuItem> menuItems;
    LayoutInflater inflater;

    public MenuItemListAdapter(Context context, List<MenuItem> menuItems) {
        this.menuItems = menuItems;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MenuItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = inflater.inflate(R.layout.menu_item_list_item, parent, false);
        return new MenuItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuItemViewHolder holder, int position) {
        MenuItem menuItem = menuItems.get(position);
        holder.description.setText(menuItem.getDescription());
        if(menuItem.getIngredients() != null)
            holder.ingredients.setText("("+menuItem.getIngredients()+")");

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

    public MenuItem getItemAtPosition(int position) {
        return menuItems.get(position);
    }
}
