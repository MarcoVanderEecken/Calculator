package thegroup.calculator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class FinancialCalculator extends Activity {

    /*
     * This screen should calculate a desposit's end value if invested at a yearly interest for a specified number of months.
     * - Keagan is in charge of this. Marco helping with the display/getting the input.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_financial_calculator);

        //Reference all required buttons/fields.
        final Button compoundInterest = (Button)findViewById(R.id.compoundInterest);
        Button simpleInterest = (Button)findViewById(R.id.simpleInterest);
        Button clear = (Button)findViewById(R.id.clear);

        final EditText enterDeposit = (EditText)findViewById(R.id.enterDeposit);
        final EditText enterInterest = (EditText)findViewById(R.id.enterInterest);
        final EditText enterPeriod = (EditText)findViewById(R.id.enterPeriod);

        final TextView nowDisplay = (TextView)findViewById(R.id.nowDisplay);
        final TextView futureDisplay = (TextView)findViewById(R.id.futureDisplay);

        //Currency always requires two 0s.
        final NumberFormat format = new DecimalFormat("0.00");

        //Clear the text for the user.
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterDeposit.setText(null);
                enterInterest.setText(null);
                enterPeriod.setText(null);
                nowDisplay.setText("R ");
                futureDisplay.setText("R ");
            }
        });

        //Calculate the compound interest
        compoundInterest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Defining all variables
                double futureValue, interest, period/* in months */, deposit;

                try {
                    deposit = Integer.parseInt(enterDeposit.getText().toString());
                } catch (NumberFormatException e) {
                    deposit = 1;
                }
                try {
                    interest = Integer.parseInt(enterInterest.getText().toString());
                } catch (NumberFormatException e) {
                    interest = 1;
                }
                try {
                    period = Integer.parseInt(enterPeriod.getText().toString());
                } catch (NumberFormatException e) {
                    period = 1;
                    //Just change the setText to 1.
                }

                ///int yearPeriod = period /12;

                futureValue = deposit * Math.pow((1 + interest / 1200), period);
                nowDisplay.setText("R" + format.format(deposit));
                futureDisplay.setText("R" + (format.format(futureValue)).toString());
            }

        });

        //Calculate the simple interest.
        simpleInterest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Defining all variables
                double deposit, interest, period, futureValue;

                try {
                    deposit = Integer.parseInt(enterDeposit.getText().toString());
                } catch (NumberFormatException e) {
                    deposit = 1;
                }
                try {
                    interest = Integer.parseInt(enterInterest.getText().toString());
                } catch (NumberFormatException e) {
                    interest = 1;
                }
                try {
                    period = Integer.parseInt(enterPeriod.getText().toString());
                } catch (NumberFormatException e) {
                    period = 1;
                }

                futureValue = deposit * (1 + (interest / 1200) * (period));
                nowDisplay.setText("R" + format.format(deposit));
                futureDisplay.setText("R" + (format.format(futureValue)).toString());
            }

        });

    }

    //Home button.
    public void sendHome(View v){
        Intent toHome = new Intent(this,MainActivity.class);
        startActivity(toHome);
    }
}
