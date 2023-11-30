package com.tiffin.tiffinqnq;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

public class PaymentActivityy extends AppCompatActivity implements PaymentResultListener {
Button btnpay;
TextView payid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_activityy);
        btnpay=findViewById(R.id.btnpay);
        payid=findViewById(R.id.paiid);

        Checkout.preload(getApplicationContext());
        btnpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payment();
            }
        });
    }

    private void payment() {
        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_d0P0dPTmRrFoD6");
        checkout.setImage(R.drawable.logo);

        /**
         * Reference to current activity
         */
        final Activity activity = this;

        /**
         * Pass your payment options to the Razorpay Checkout as a JSONObject
         */
        try {
            JSONObject options = new JSONObject();

            options.put("name", "Tiffinqnq");
            options.put("description", "Reference No. #123456");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.jpg");
            //options.put("order_id", "order_DBJOWzybf0sJbb");//from response of step 3.
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
            options.put("amount", "500000");//pass amount in currency subunits
            options.put("prefill.email", "sahanijee@gmail.com");
            options.put("prefill.contact","9006577512");
            JSONObject retryObj = new JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);

            checkout.open(activity, options);

        } catch(Exception e) {
            Log.e("TAG", "Error in starting Razorpay Checkout", e);
        }
    }

    @Override
    public void onPaymentSuccess(String s) {
        payid.setText(" Successful Pay ID: "+s);
    }

    @Override
    public void onPaymentError(int i, String s) {
        payid.setText("Cause is: "+s);

    }
}