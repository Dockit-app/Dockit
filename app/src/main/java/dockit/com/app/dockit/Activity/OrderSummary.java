package dockit.com.app.dockit.Activity;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.View;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import java.util.List;

import dockit.com.app.dockit.Adapter.SummaryListAdapter;

import dockit.com.app.dockit.Entity.Decorator.SummaryItemView;
import dockit.com.app.dockit.Entity.Result.OrderResult;
import dockit.com.app.dockit.R;

import dockit.com.app.dockit.Tasks.SharedPreferencesManager;
import dockit.com.app.dockit.ViewModel.OrderSummaryViewModel;

public class OrderSummary extends AppCompatActivity {

    private OrderSummaryViewModel orderSummaryViewModel;


    private String covers;
    private String server;
    private int orderId = 0;
    private String tableName;
    TextView tableText, serverText, coversText, timeText;

    SummaryListAdapter summaryListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary);

        OrderResult orderResult = (OrderResult)getIntent().getSerializableExtra("OrderResult");
        orderId = orderResult.getId();
        setOrderSummaryViewModel(orderResult);
        tableName = orderSummaryViewModel.GetTable(orderResult);
        covers = orderSummaryViewModel.GetCovers(orderResult);


        String time = orderResult.getTimeStamp();

        tableText = findViewById(R.id.table_text);
        tableText.setText(tableName);

        server = SharedPreferencesManager.getInstance(this).getUsername();
        serverText = findViewById(R.id.server_text);
        serverText.setText(server);

        coversText = findViewById(R.id.covers_text);
        coversText.setText(covers);

        timeText = findViewById(R.id.time_text);
        timeText.setText(getTimefromTimestamp(time));

        setSendOrderButton();


        SetSummaryRecyclerView(orderResult);


    }

    private void setOrderSummaryViewModel(final OrderResult order) {
        orderSummaryViewModel = ViewModelProviders.of(this).get(OrderSummaryViewModel.class);
    }


    private void SetSummaryRecyclerView(OrderResult orderResult) {
        RecyclerView recyclerView = findViewById(R.id.summary_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        List<SummaryItemView> groupMenu = orderSummaryViewModel.GroupMenuItems(orderResult);
        groupMenu = orderSummaryViewModel.ValidOrder(groupMenu); //REMOVE THIS LINE WHEN IMPLEMENTING VALIDATION
        groupMenu = orderSummaryViewModel.removeEmptySections(groupMenu);
        summaryListAdapter = new SummaryListAdapter(this, groupMenu);
        recyclerView.setAdapter(summaryListAdapter);
    }

    private void setSendOrderButton() {
        final FloatingActionButton fab = findViewById(R.id.fab);
        orderSummaryViewModel.isOrderSpaced().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean isSpaced) {
                if (isSpaced) {
                    fab.setTag(0);
                }
                else {
                    fab.setTag(1);

                }

                orderSummaryViewModel.isOrderSpaced().removeObserver(this);
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View v) {
                final int status = (Integer) v.getTag();
                if (status == 0) {
                    fab.setForeground(getResources().getDrawable(R.drawable.tick_icon));
                    String newTime = (String) timeText.getText();
                    newTime += "\n(+5 mins) ";
                    timeText.setText(newTime);
                    timeText.setTextColor(Color.RED);
                    timeText.setTextSize(22);
                    fab.setTag(1);
                    //startTableSelectionActivity();
                }
                else if (status == 1) {
                    startTableSelectionActivity();
                }
            }
        });
    }

    private void startTableSelectionActivity() {
        Intent i = new Intent(this, TableSelection.class);
        startActivity(i);
    }

    private String getTimefromTimestamp(String timeStamp){
        SimpleDateFormat format = new SimpleDateFormat("hh:mm");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(format.parse(timeStamp));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return format.format(c.getTime());
    }


}
