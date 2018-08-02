package dockit.com.app.dockit.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import dockit.com.app.dockit.Entity.MenuItem;
import dockit.com.app.dockit.R;

/**
 * Created by michael on 28/07/18.
 */

public class MenuItemListAdapter extends RecyclerView.Adapter<MenuItemListAdapter.MenuItemViewHolder> {

    class MenuItemViewHolder extends RecyclerView.ViewHolder {

        private TextView description;

        public MenuItemViewHolder(View itemView) {
            super(itemView);

            this.description = itemView.findViewById(R.id.description);
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
    }

    @Override
    public int getItemCount() {
        if(menuItems != null) {
            return menuItems.size();
        }
        return 0;
    }
}
