package dockit.com.app.dockit.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

import java.util.List;

import dockit.com.app.dockit.Entity.OptionalItem;
import dockit.com.app.dockit.R;
import dockit.com.app.dockit.ViewModel.MenuItemViewModel;

public class OptionalSwitchListAdapter extends RecyclerView.Adapter<OptionalSwitchListAdapter.OptionalSwitchViewHolder> {

    class OptionalSwitchViewHolder extends RecyclerView.ViewHolder {

        Switch switchView;

        public OptionalSwitchViewHolder(View itemView) {
            super(itemView);

            switchView = itemView.findViewById(R.id.optional_switch);

        }
    }

    List<OptionalItem> optionalItems;
    LayoutInflater inflater;
    MenuItemViewModel menuItemViewModel;

    public OptionalSwitchListAdapter(Context context, List<OptionalItem> optionalItems, MenuItemViewModel menuItemViewModel) {
        this.optionalItems = optionalItems;
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
        OptionalItem optionalItem = optionalItems.get(position);

        holder.switchView.setText(optionalItem.getName());
        holder.switchView.setChecked(optionalItem.isSelected());

        holder.switchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                optionalItem.setSelected(!optionalItem.isSelected());

                menuItemViewModel.updateOptional(optionalItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(optionalItems != null) {
            return optionalItems.size();
        }
        return 0;
    }

}
