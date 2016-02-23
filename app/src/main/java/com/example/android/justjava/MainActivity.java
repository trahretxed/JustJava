package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.text.NumberFormat;


/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 2;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    /**
     *
     * @param savedInstanceState
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText getName = (EditText) findViewById(R.id.name);
        String name = getName.getText().toString();
        CheckBox getWhippedCream = (CheckBox) findViewById(R.id.whipped_cream);
        boolean hasWhippedCream = getWhippedCream.isChecked();
        CheckBox getChocolate = (CheckBox) findViewById(R.id.chocolate);
        boolean hasChocolate = getChocolate.isChecked();
        int totalPrice = calculatePrice(hasWhippedCream, hasChocolate);
        String orderSummary = createOrderSummary(totalPrice, name, hasWhippedCream, hasChocolate);
        Log.v("MainActivity", orderSummary);
        sendEmail(orderSummary, name);
    }

    /**
     *
     * This method will calculate the total price
     *
     * @param whippedCream
     * @param chocolate
     * @return total price
     */
    private int calculatePrice(boolean whippedCream, boolean chocolate) {
        int basePrice = 5;
        if (whippedCream) {
            basePrice = basePrice + 1;
        }
        if (chocolate) {
            basePrice = basePrice + 2;
        }
        return quantity * basePrice;
    }

    /**
     *
     * This method is used to generate the order summary
     *
     * @param price
     * @param name
     * @param whippedCream
     * @param chocolate
     * @return order summary message
     */
    private String createOrderSummary(int price, String name, boolean whippedCream, boolean chocolate) {
        String orderSummary = getString(R.string.order_summary_name, name);
        orderSummary += getString(R.string.order_summary_whipped_cream, whippedCream);
        orderSummary += getString(R.string.order_summary_chocolate, chocolate);
        orderSummary += getString(R.string.order_summary_quantity, quantity);
        orderSummary += getString(R.string.order_summary_price, NumberFormat.getCurrencyInstance().format(price));
        orderSummary += getString(R.string.order_summary_thanks);
        return orderSummary;
    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {

        if (quantity >= 100) {
            error(getString(R.string.too_many));
            return;
        } else {
            quantity++;
        }
        displayQuantity();
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {

        if (quantity <= 1) {
            error(getString(R.string.too_few));
            return;
        } else {
            quantity--;
        }
        displayQuantity();
    }

    /**
     *
     * This method is called to display an error to the user.
     *
     * @param text
     */
    private void error(CharSequence text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }

    /**
     *
     * This method is used to display the given quantity on the screen.
     *
     */
    private void displayQuantity() {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + quantity);
    }

    /**
     *
     * This method is used to send an email of the order.
     *
     * @param message
     * @param name
     */
    private void sendEmail(String message, String name) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "JustJava order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }


}