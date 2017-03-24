package thegroup.calculator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    /*
     *  This is the home page. This page should have a welcome heading and buttons leading to all the other pages.
     *   - Williams should create the layout. Marco will link all the buttons.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //Send to the basic calculator
    public void sendBasicCalculator(View v){
        Intent sendToBasic = new Intent(this,BasicCalculatorActivity.class);
        startActivity(sendToBasic);
    }

    //Send to the financial calculator
    public void sendFinancialCalculator(View v){
        Intent sendToFinance = new Intent(this,FinancialCalculator.class);
        startActivity(sendToFinance);
    }

    //send to the network calculator
    public void sendNetworkCalculator(View v){
        Intent sendToNetwork = new Intent(this,NetworkMain.class);
        startActivity(sendToNetwork);
    }

    //sent to the about us page.
    public void sendAboutUs(View v){
        Intent sendToAboutUs = new Intent(this,AboutUs.class);
        startActivity(sendToAboutUs);
    }

    //Leave in case we decide to add an inverted colors option later for dark rooms.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
