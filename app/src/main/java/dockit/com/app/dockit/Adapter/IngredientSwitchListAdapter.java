package dockit.com.app.dockit.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

import java.util.List;

import dockit.com.app.dockit.Entity.IngredientItem;
import dockit.com.app.dockit.Entity.OptionalItem;
import dockit.com.app.dockit.R;
import dockit.com.app.dockit.ViewModel.MenuItemViewModel;

public class IngredientSwitchListAdapter extends RecyclerView.Adapter<IngredientSwitchListAdapter.OptionalSwitchViewHolder> {

    class OptionalSwitchViewHolder extends RecyclerView.ViewHolder {

        Switch switchView;

        public OptionalSwitchViewHolder(View itemView) {
            super(itemView);

            switchView = itemView.findViewById(R.id.optional_switch);

        }
    }

    List<IngredientItem> ingredientItems;
    LayoutInflater inflater;
    MenuItemViewModel menuItemViewModel;

    public IngredientSwitchListAdapter(Context context, List<IngredientItem> ingredientItems, MenuItemViewModel menuItemViewModel) {
        this.ingredientItems = ingredientItems;
        inflater = LayoutInflater.from(context);
        this.menuItemViewModel = menuItemViewModel;
    }


    @NonNull
    @Override
    public OptionalSwitchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.optional_switch, parent, false);
        return new OptionalSwitchViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OptionalSwitchViewHolder holder, int position) {
        IngredientItem ingredientItem = ingredientItems.get(position);

        holder.switchView.setText(ingredientItem.getName());
        holder.switchView.setChecked(ingredientItem.isSelected());

        holder.switchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ingredientItem.setSelected(!ingredientItem.isSelected());

                menuItemViewModel.updateIngredient(ingredientItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(ingredientItems != null) {
            return ingredientItems.size();
        }
        return 0;
    }

}
