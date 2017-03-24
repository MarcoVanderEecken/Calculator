package thegroup.calculator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class NetworkSecond extends Activity{

    /*
     * This screen should display all the subnet/broadcast addresses possible from the subnet entered previously.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_second);

        //Create arraylist to hold the arraylist sent from the previous screen.
        ArrayList<Integer> networkAddress = new ArrayList<Integer>();
        ArrayList<Integer> broadcastAddress = new ArrayList<Integer>();

        Intent getNetMain = getIntent();

        //Get the arraylist from the previous screen.
        try{
            networkAddress = getNetMain.getIntegerArrayListExtra("networkAddress");
            broadcastAddress = getNetMain.getIntegerArrayListExtra("broadcastAddress");
        }catch(NullPointerException e){
            //In case the Array is empty.
            networkAddress.add(0);
            broadcastAddress.add(0);
        }

        //Need to use the table to display the two arrays. One array per row.
        TableLayout tableLayout = (TableLayout)findViewById(R.id.tableLayout);

        //Clear the table? --> solved by clearing the previous arraylist. User is forced one screen back which clears the activity.

        //Display the ArrayLists<Integer>.
        for(int i = 0; i < networkAddress.size(); i++){
            TableRow row = new TableRow(this);  //Create a new row
            int subnetID = networkAddress.get(i); //get the specific array entry to display
            int broadcast = broadcastAddress.get(i);
            TextView subnetting = new TextView(this); //create a textView in the row to display the array entry.
            subnetting.setTextSize(20); //increase text size.
            subnetting.setText("" + subnetID); //display text.
            TextView broadcasting = new TextView(this);
            broadcasting.setTextSize(20);
            broadcasting.setText("-" + broadcast); //"-" to indicate that the range is from subnet to broadcast.
            row.addView(subnetting); //display the TextView on the screen.
            row.addView(broadcasting); //display the TextView on the screen.
            tableLayout.addView(row);
        }


        //reset the arraylists in order to not print out the previously sent arraylists as well.
        networkAddress.clear();
        broadcastAddress.clear();
    }
}
