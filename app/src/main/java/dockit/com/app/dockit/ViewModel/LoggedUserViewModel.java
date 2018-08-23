package dockit.com.app.dockit.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

public class LoggedUserViewModel extends AndroidViewModel {

    public LoggedUserViewModel(Application application) {
        super(application);
    }

    //Show dialog to select table number of new order
    public void newTable(Context c) {
        final EditText tableNumberEditText = new EditText(c);
        AlertDialog dialog = new AlertDialog.Builder(c)
                .setTitle("Table number:")
                .setView(tableNumberEditText)
                .setPositiveButton("New Order", (dialog1, which) -> {
                    //Get user input and send value to createTableNumber
                    String numberTable = String.valueOf(tableNumberEditText.getText().toString());
                    //TODO: Send to activity Ordering
                    Toast.makeText(c, "You created " + numberTable + " and went to Ordering Activity", Toast.LENGTH_LONG).show();
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();
    }

    //Shows list of existing tables to select and send selected to Ordering activity
    public void listExistingTables(Context c) {
        final String[] option = {"Table 1", "Table 2", "Table 3", "Table 4"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(c, android.R.layout.select_dialog_item, option);
        AlertDialog dialog = new AlertDialog.Builder(c)
                .setTitle("Select Table:")
                .setAdapter(adapter, (dialogInterface, i) -> {
                    //TODO: Send table number to activity Ordering
                    //Intent i = new Intent(context, LoggedUser.class);
                    //            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    //            i.putExtra("table", adapter.getItem(i)));
                    //            context.startActivity(i);
                    String tableSelected = adapter.getItem(i);
                    Toast.makeText(c, "You selected " + tableSelected + " and went to Ordering Activity", Toast.LENGTH_LONG).show();

                })
                .create();
        dialog.show();

    }
}
