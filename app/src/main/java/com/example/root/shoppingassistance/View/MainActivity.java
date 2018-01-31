package com.example.root.shoppingassistance.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.root.shoppingassistance.Database.DatabaseConnector;
import com.example.root.shoppingassistance.R;

public class MainActivity extends AppCompatActivity {

    DatabaseConnector dbCon;

    public MainActivity() {
        dbCon=DatabaseConnector.getInstance(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void addTest(View view){
        //dbCon.addProperty();
    }

    public void viewTest(View view){
        //ArrayList<String> items = dbCon.viewAllItems();
        //xtTest.setText(items.get(0));
    }

    public void startShopping(View view){
        Intent myIntent = new Intent(this, ShoppingAssistanceView.class);
        startActivity(myIntent);
    }
}
