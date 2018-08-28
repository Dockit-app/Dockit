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

import java.util.List;

import dockit.com.app.dockit.Entity.Decorator.MenuItemView;
import dockit.com.app.dockit.Entity.MenuItem;
import dockit.com.app.dockit.Entity.Result.MenuItemResult;
import dockit.com.app.dockit.R;

/**
 * Created by michael on 28/07/18.
 */

public class MenuItemListAdapter extends RecyclerView.Adapter<MenuItemListAdapter.MenuItemViewHolder> {

    class MenuItemViewHolder extends RecyclerView.ViewHolder {

        private RelativeLayout menuSection;
        private TextView name;
        private CardView menuItem;
        private RelativeLayout menuItemBackground;
        private TextView description;
        private TextView ingredients;


        public MenuItemViewHolder(View itemView) {
            super(itemView);

            this.menuSection= itemView.findViewById(R.id.menu_section);
            this.name = itemView.findViewById(R.id.name);
            this.description = itemView.findViewById(R.id.description);
            this.ingredients = itemView.findViewById(R.id.ingredients);
            this.menuItem = itemView.findViewById(R.id.menu_item);
            this.menuItemBackground = itemView.findViewById(R.id.menu_item_background);
        }
    }

    List<MenuItemView> menuItems;
    LayoutInflater inflater;

    public MenuItemListAdapter(Context context, List<MenuItemView> menuItems) {
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

        if(menuItem.isSection()) {
            holder.menuItem.setVisibility(View.GONE);
            holder.menuSection.setVisibility(View.VISIBLE);
            holder.name.setText(menuItem.getDescription());
        }
        else {
            holder.menuSection.setVisibility(View.GONE);
            holder.menuItem.setVisibility(View.VISIBLE);
            holder.description.setText(menuItem.getDescription());
            holder.ingredients.setText("  "+menuItem.getIngredients());

            if(position % 2 != 0 && !menuItem.isSection()) {
                holder.menuItemBackground.setBackgroundColor(Color.LTGRAY);
            }
            else {
                holder.menuItemBackground.setBackgroundColor(Color.WHITE);
            }
        }

        if(menuItem.isSelected()) {
            holder.menuItem.setBackgroundColor(Color.BLACK);
        }
        else {
            if(position % 2 != 0 && !menuItem.isSection()) {
                holder.menuItem.setBackgroundColor(Color.LTGRAY);
            }
            else {
                holder.menuItem.setBackgroundColor(Color.WHITE);
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

    public MenuItemResult getItemAtPosition(int position) {
        return menuItems.get(position);
    }
}
