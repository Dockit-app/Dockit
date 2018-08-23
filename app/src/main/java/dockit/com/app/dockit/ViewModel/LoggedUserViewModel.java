package dockit.com.app.dockit.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import dockit.com.app.dockit.Activity.Ordering;
import dockit.com.app.dockit.Entity.Order;
import dockit.com.app.dockit.Repository.LoggedUserRepository;

public class LoggedUserViewModel extends AndroidViewModel {

    private LoggedUserRepository loggedUserRepository;

    public LoggedUserViewModel(Application application) {
        super(application);
        loggedUserRepository = new LoggedUserRepository(application);
    }

    //Shows list of existing tables to select and send selected to Ordering activity
    public void listExistingTables(Context c, ArrayList<String> tables) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(c, android.R.layout.select_dialog_item, tables);
        AlertDialog dialog = new AlertDialog.Builder(c)
                .setTitle("Select Table:")
                .setAdapter(adapter, (dialogInterface, i) -> {
                    Intent intent = new Intent(c, Ordering.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("table", adapter.getItem(i));
                    c.startActivity(intent);
                    String tableSelected = adapter.getItem(i);
                    Toast.makeText(c, "You selected " + tableSelected + " and went to Ordering Activity", Toast.LENGTH_LONG).show();

                })
                .create();
        dialog.show();

    }

    public void createTableNumber(String table, Context c) {
        //Database returns all order_tables that start with the table number and an observer catch the result
        createNewTable(table).observe((LifecycleOwner) c, orders -> {
            String testTable = table;
            char alphabet = 'A';
            //If there are results, search for the first one not used
            if (orders != null) {
                for (Order orderTables : orders) {
                    if (testTable.equals(orderTables.getOrderTable())) { //find
                        alphabet++;
                        testTable = table + alphabet;
                        break;
                    }
                }
            }
            //the free table is sent to Ordering to create a new order
            Log.d("my", testTable);
            Toast.makeText(c, "You created table number " + testTable + " and went to Ordering Activity", Toast.LENGTH_LONG).show();
            Intent i = new Intent(c, Ordering.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.putExtra("table", testTable);
            c.startActivity(i);
        });


    }

    public LiveData<List<Order>> getTables(){
        return loggedUserRepository.getExistingTables();
    }

    private LiveData<List<Order>> createNewTable(String table){
        return loggedUserRepository.getTablesWithInputNumber(table);
    }


}