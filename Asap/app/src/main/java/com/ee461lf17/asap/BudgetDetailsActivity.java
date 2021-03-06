package com.ee461lf17.asap;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class BudgetDetailsActivity extends AppCompatActivity {

    HashMap<String, HashMap<String, List<String>>> budgetDetailsPassed;
    String budgetNamePassed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget_details);
        budgetDetailsPassed = (HashMap<String, HashMap<String, List<String>>>) getIntent().getSerializableExtra("Expense Details");
        budgetNamePassed = getIntent().getStringExtra("Current Budget");

        TableLayout tableLayout = (TableLayout) findViewById(R.id.budgetDetailTable);
        tableLayout.setStretchAllColumns(true);

        Set<String> expensesKeySet =  budgetDetailsPassed.keySet();
        boolean greyEntry = false;
        for(String s: expensesKeySet){
            HashMap<String, List<String>> curExpense =  budgetDetailsPassed.get(s);
            List<String> curDetails = curExpense.get(s);
            String curBudget = curDetails.get(2);
            if(curBudget.equals(budgetNamePassed)){
                TextView textView = new TextView(this);
                TextView textView1 = new TextView(this);
                TextView textView2 = new TextView(this);
                TextView textView3 = new TextView(this);


                TableRow tableRow = new TableRow(this);
                if(greyEntry){
                    greyEntry = false;
                    tableRow.setBackgroundColor(Color.parseColor("#E8EDEF"));
                }
                else{
                    greyEntry = true;
                }
                float density = this.getResources().getDisplayMetrics().density;
                tableRow.setPadding((int)density*5, (int)density*5, (int)density*5, (int)density*5);
                tableRow.setGravity(Gravity.LEFT);
                tableRow.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

                textView.setText(s);
                textView1.setText(curDetails.get(0));
                textView2.setText(curDetails.get(1));
                textView3.setText(curDetails.get(2));

                textView.setTextSize(18);
                textView1.setTextSize(18);
                textView2.setTextSize(18);
                textView3.setTextSize(18);


                textView.setGravity(Gravity.LEFT);
                textView1.setGravity(Gravity.LEFT);
                textView2.setGravity(Gravity.LEFT);
                textView3.setGravity(Gravity.LEFT);



                textView.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                textView1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                textView2.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                textView3.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));


                tableRow.addView(textView);
                tableRow.addView(textView1);
                tableRow.addView(textView2);
                tableRow.addView(textView3);


                tableLayout.addView(tableRow);
            }

        }
    }



}
