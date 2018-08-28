package dockit.com.app.dockit.Activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import dockit.com.app.dockit.Entity.Order;
import dockit.com.app.dockit.Entity.Result.OrderResult;
import dockit.com.app.dockit.R;
import dockit.com.app.dockit.Tasks.SharedPreferencesManager;
import dockit.com.app.dockit.ViewModel.TableSelectionViewModel;

import static android.content.Intent.FLAG_ACTIVITY_NEW_DOCUMENT;

public class TableSelection extends AppCompatActivity {
    private Button newOrder, existingOrder;
    private TextView nameUser;
    private TableSelectionViewModel tableSelectionViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_user);

        //retrieve username from Shared Preference
        String name = SharedPreferencesManager.getInstance(this).getUsername();

        //Update the textView to show the name of the user
        nameUser = findViewById(R.id.nameUser);
        nameUser.setText(name);

        //buttons shows dialogs
        newOrder = findViewById(R.id.newOrder);
        existingOrder = findViewById(R.id.existingOrders);

        tableSelectionViewModel = ViewModelProviders.of(this).get(TableSelectionViewModel.class);

        setClickListeners();

    }

    private void setClickListeners() {
        newOrder.setOnClickListener(v -> {
            //Create table number and send to Ordering activity

            final EditText tableNumberEditText = new EditText(this);
            tableNumberEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("Table number:")
                    .setView(tableNumberEditText)
                    .setPositiveButton("New Order", (dialog1, which) -> {
                        //Get user input and send value to createTableNumber
                        String numberTable = String.valueOf(tableNumberEditText.getText().toString());

                        setTableValidationObserver(numberTable);
                    })
                    .setNegativeButton("Cancel", null)
                    .create();
            dialog.show();

        });

        existingOrder.setOnClickListener(v -> tableSelectionViewModel.getTables().observe(this, orders -> {

            ArrayList<String> tables = new ArrayList<>();

            //If there are results, transforms in an Array[string]
            if (orders != null) {
                for (Order orderTables : orders) {
                    tables.add(orderTables.getOrderTable());
                }
            }
            listExistingTables(tables);
        }));
    }

    //Shows list of existing tables to select and send selected to Ordering activity
    private void listExistingTables(ArrayList<String> tables) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.select_dialog_item, tables);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Select Table:")
                .setAdapter(adapter, (dialogInterface, i) -> {
                    String tableSelected = adapter.getItem(i);
                    getOrderFromTable(tableSelected);
                    Log.i(this.getClass().getSimpleName(), "Create ordering activity from existing");
                })
                .create();
        dialog.show();
    }

    private void setTableValidationObserver(String table) {

        tableSelectionViewModel.createNewTable(table).observe(this, new Observer<List<Order>>() {
            @Override
            public void onChanged(@Nullable List<Order> orders) {
                String updatedTable = table;
                char alphabet = 'A';
                //If there are results, search for the first one not used
                if (orders != null) {
                    for (Order orderTables : orders) {
                        if (updatedTable.equals(orderTables.getOrderTable())) { //find
                            alphabet++;
                            updatedTable = table + alphabet;
                            break;
                        }
                    }
                }
                tableSelectionViewModel.isTableNameValidated.setValue(true);
                tableSelectionViewModel.createNewTable(updatedTable).removeObserver(this);
            }
        });

        tableSelectionViewModel.isTableNameValidated.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean isValidated) {
                if(isValidated) {
                    createOrderActivity(table, null);
                    tableSelectionViewModel.isTableNameValidated.removeObserver(this);
                }
            }
        });
    }

    private void getOrderFromTable(String table){
        tableSelectionViewModel.getOrderSelected(table).observe(this, new Observer<List<OrderResult>>() {
            @Override
            public void onChanged(@Nullable List<OrderResult> orders) {
                if (orders != null){
                    for (OrderResult order:orders) {
                        createOrderActivity(null, order);
                    }
                }
                tableSelectionViewModel.getOrderSelected(table).removeObserver(this);
            }
        });
    }

    public void createOrderActivity(String table, OrderResult orderResult) {
        //the free table is sent to Ordering to create a new order
        Log.i(this.getClass().getSimpleName(), "Create ordering activity from new");
        Toast.makeText(this, "Order for table " + table, Toast.LENGTH_LONG).show();
        Intent i = new Intent(this, Ordering.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.setFlags(FLAG_ACTIVITY_NEW_DOCUMENT);
        i.putExtra("table", table);
        i.putExtra("Order", orderResult);
        startActivity(i);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }




}