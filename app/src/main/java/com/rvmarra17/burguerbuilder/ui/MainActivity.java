package com.rvmarra17.burguerbuilder.ui;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.rvmarra17.burguerbuilder.R;
import com.rvmarra17.burguerbuilder.core.BurguerConfigurator;
import com.rvmarra17.burguerbuilder.core.ItemArrayAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create the burguer configuration
        this.cfgBurguer = new BurguerConfigurator();        // Create callback for the button allowing to check prices
        Button btPrices = (Button) this.findViewById(R.id.btPrices);
        btPrices.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                MainActivity.this.showPricesDialog();
            }
        });        // Create callback for the button allowing to select ingredients
        Button btIngredients = (Button) this.findViewById(R.id.btIngredients);
        btIngredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.this.showIngredients();
            }
        });
        this.showFixedIngredients();
        this.updateTotals();
    }

    private BurguerConfigurator cfgBurguer;

    private void showFixedIngredients() {
        ListView lvFixedIngredients = (ListView) this.findViewById(R.id.lvFixedIngredients);
        String[] fixedIngredients = new String[]{
                String.format("%4.2f€ %s", BurguerConfigurator.FIXED_COSTS[0], BurguerConfigurator.FIXED_INGREDIENTS[0]), String.format("%4.2f€ %s", BurguerConfigurator.FIXED_COSTS[1], BurguerConfigurator.FIXED_INGREDIENTS[1])
        };
        lvFixedIngredients.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, fixedIngredients));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void showPricesDialog() {
        final int NUM_INGREDIENTS = BurguerConfigurator.INGREDIENTS.length;
        final String[] ingredientsWithPrices = new String[NUM_INGREDIENTS];
        final TextView lblData = new TextView(this);
        AlertDialog.Builder dlg = new AlertDialog.Builder(this);
        dlg.setTitle(this.getResources().getString(R.string.lblPrices));        // Build list with prices
        for (int i = 0; i < ingredientsWithPrices.length; ++i) {
            ingredientsWithPrices[i] = String.format("%4.2f€ %s", BurguerConfigurator.COSTS[i], BurguerConfigurator.INGREDIENTS[i]);
        }
        lblData.setText(String.join("\n", ingredientsWithPrices));
        lblData.setPadding(10, 10, 10, 10);
        dlg.setView(lblData);
        dlg.setPositiveButton("Ok", null);
        dlg.create().show();
    }

    private void showIngredients() {
        final int NUM_ITEMS = this.cfgBurguer.getSelected().length;
        final boolean[] selections = this.cfgBurguer.getSelected();
        final ListView lvIngredients = (ListView) this.findViewById(R.id.lvIngredients);        // Create list
        ArrayList<String> ingredients = new ArrayList<>();
        for (int i = 0; i < NUM_ITEMS; ++i) {
            if (selections[i]) {
                ingredients.add(String.format("%4.2f€ %s", BurguerConfigurator.COSTS[i], BurguerConfigurator.INGREDIENTS[i]));
            }
        }
        lvIngredients.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ingredients));
        lvIngredients.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int listPos = MainActivity.this.cfgBurguer.getGlobalPosOfSelected(i);
                MainActivity.this.cfgBurguer.setSelected(listPos, false);
                MainActivity.this.showIngredients();
                MainActivity.this.updateTotals();
                return true;
            }
        });
    }

    private void updateTotals() {
        final TextView lblTotal = (TextView) this.findViewById(R.id.lblTotal);
        lblTotal.setText(String.format("%4.2f", MainActivity.this.cfgBurguer.calculateCost()));
    }

    /*private void creaLista() {
        final ListView lvItems = this.findViewById(R.id.lvItems);
        this.adapterList = new ItemArrayAdapter(this, this.items);
        lvItems.setAdapter(this.adapterList);
    }*/


}