package thegroup.calculator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class NetworkMain extends AppCompatActivity {

    /*
     * This is a subnet network calculator. It should have an option of the user entering an ipv4 address and a subnet.
     *      It needs to display the subnet ID/Mask, Broadcast address. Also implement showing
     *      the full range of possible subnets and broadcasts.
     *      - Marco is in charge of this.
     */

    //Defined here so one can access it from any Intents as well..
    public int networkPortion = 0, subSlash = 0, range = 0, amountNetworks, amountHosts;
    ArrayList<Integer> networkAddress = new ArrayList<>();
    ArrayList<Integer> broadcastAddress = new ArrayList<>();
    ArrayList<Integer> networkAddress2 = new ArrayList<>();
    ArrayList<Integer> broadcastAddress2 = new ArrayList<>();

    //Defining all variables (in case one wants to access from intents).
    int octet1, octet2, octet3, octet4, x = 0, y = 0;
    String networkClassification="",subnetIdentification ="", subMask;
    String octet1String = "000", octet2String = "000", octet3String= "000", octet4String = "000";
    String binaryNumber = "";
    int octet3holder, octet4holder, max = 128, holder = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_main);

        //Reference all the EditTexts
        final EditText octetOne = (EditText)findViewById(R.id.octetOne);
        final EditText octetTwo = (EditText)findViewById(R.id.octetTwo);
        final EditText octetThree = (EditText)findViewById(R.id.octetThree);
        final EditText octetFour = (EditText)findViewById(R.id.octetFour);
        final EditText subnetSlash = (EditText)findViewById(R.id.slashSubnet); //Defined earlier.

        //Reference all the buttons
        Button calcNetwork = (Button)findViewById(R.id.calc);
        Button clearFields = (Button)findViewById(R.id.clearButton);

        //Reference all the TextViews
        final TextView lastUsableRange = (TextView)findViewById(R.id.outputLastUsable);
        final TextView firstUsableRange = (TextView)findViewById(R.id.outputFirstUsable);
        final TextView subnetMask = (TextView)findViewById(R.id.subnetMask);
        final TextView networkClass = (TextView)findViewById(R.id.networkClass);

        //Network IP Calculator Button. This one should calculate/display all the networking stuff based on previous input.
        calcNetwork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //parse all the Strings into integers. Made the exception catcher so one does not have to enter all of them if 0.
                //Added advantage is if text is entered, it will not break the app, it will be taken as zero. Add note to bottom of page for user.
                try {
                    octet1 = Integer.parseInt(octetOne.getText().toString());
                } catch (NumberFormatException e) {
                    octet1 = 0; //In case no input has been entered or the user is lazy.
                }

                try {
                    octet2 = Integer.parseInt(octetTwo.getText().toString());
                } catch (NumberFormatException e) {
                    octet2 = 0; //In case no input has been entered or the user is lazy.
                }

                try {
                    octet3 = Integer.parseInt(octetThree.getText().toString());
                } catch (NumberFormatException e) {
                    octet3 = 0; //In case no input has been entered or the user is lazy.
                }

                try {
                    octet4 = Integer.parseInt(octetFour.getText().toString());
                } catch (NumberFormatException e) {
                    octet4 = 0; //In case no input has been entered or the user is lazy.
                }

                try {
                    subSlash = Integer.parseInt(subnetSlash.getText().toString());
                } catch (NumberFormatException e) {
                    subSlash = 24;  //Default subnet for class C.
                }

                //Resetting all variables
                x = 0; y = 0;
                networkClassification="";subnetIdentification =""; subMask = "";
                octet1String = "000"; octet2String = "000"; octet3String= "000"; octet4String = "000";
                binaryNumber = "";
                octet3holder = 0; octet4holder = 0; max = 128; holder = 0;


                //Check class, also assign subnetID at the same time. Lot's of info from: http://www.vlsm-calc.net/ipclasses.php due to overview
                if(octet1 < 128){ //Class A. 0-127
                    networkClassification = "A (Public)";
                    subnetIdentification = "255.0.0.0";
                    if(octet1 == 10) networkClassification = "A (Private)"; //This is to sort out if it is a private class.
                    else if(octet1 == 127 && octet2 == 0 && octet3 == 0 && octet4 == 1) subnetIdentification = "localhost";
                    else if(octet1 == 0 && octet2 == 0 && octet3 == 0 && octet4 == 0) subnetIdentification = "no particular address.";
                    else if(octet1 == 0) {subnetIdentification = "not a valid IP Address"; networkClassification = "Does not exist";}
                }
                else if(octet1 < 192 ){ //Class B. 128-191
                    networkClassification = "B (Public)";
                    subnetIdentification = "255.255.0.0";
                    if(octet1 == 172 && octet2 > 15 && octet2 < 32) networkClassification = "B (Private)";
                }
                else if(octet1 < 224) { //Class C. 192- 223
                    networkClassification = "C (Public)";
                    subnetIdentification = "255.255.255.0";
                    if(octet1 == 192 && octet2 == 168) networkClassification = "C (Private)";
                }
                else if(octet1 < 240) { //Class D. 224 - 239
                    networkClassification = "D";
                    subnetIdentification = "Reserved for Multicasting";
                }
                else if(octet1 < 256) { //Class E. 240 - 254
                    networkClassification = "E";
                    subnetIdentification = "Experimental; used for research";
                }

                //A catcher to check if the IP is actually valid. Has to be after all the other identifications as it should overwrite bad values.
                if(octet1 < 0 || octet1> 255 || octet2 < 0 || octet2> 255 ||
                        octet3 < 0 || octet3> 255 || octet4 < 0 || octet4> 255){
                    networkClassification = "Not a valid IP Address";
                    subnetIdentification = "Does not exist.";
                }

                //Print out class(Public/Private) and Default Subnet Mask
                networkClass.setText("Class: " + networkClassification);
                subnetMask.setText("Default Subnet Mask: " + subnetIdentification);

                //Subnet calculation suggestion for class C.
                if(subSlash > 24){
                    x = 0;
                    y = 0;
                    x = subSlash / 8;       //So you know what octet you are dealing with.
                    y = (subSlash - x*8);   //Network portion borrowed.
                    networkPortion = y;     //For the array list.
                }

                //Subnet calculation suggestion for class B
                if(subSlash > 16 && subSlash <24){
                    x = 0;
                    y = 0;
                    x = subSlash / 8;
                    y = (subSlash - x*8);
                    networkPortion = y;
                }

                //converting octets to strings. Should always have 3 digits
                //Octet 1
                octet1String = "";
                if(octet1 < 10) octet1String = "00" + octet1;
                else if (octet1 < 100) octet1String = "0" + octet1;
                else octet1String = "" + octet1;

                //Octet 2
                octet2String = "";
                if(octet2 < 10) octet2String = "00" + octet2;
                else if (octet2 < 100) octet2String = "0" + octet2;
                else octet2String = "" + octet2;

                //Octet 3
                octet3String ="";
                if(octet3 < 10) octet3String = "00" + octet3;
                else if (octet3 < 100) octet3String = "0" + octet3;
                else octet3String = "" + octet3;

                //Octet 4
                octet4String = "";
                if(octet4 < 10) octet4String = "00" + octet4;
                else if(octet4 < 100) octet4String = "0" + octet4;
                else octet4String = "" + octet4;


                //if it is a class C network address.
                if(x == 3 && networkClassification.equals("C (Public)")){
                    //Calculate the last octet subnet.
                    binaryNumber = "";
                    max = 128;
                    octet4holder = octet4;

                    //convert to binary.
                    for(int j = 0; j < 8 /*Due to 8 digits max for 255 8*/; j++){
                        if(octet4holder%2 == 1){
                            binaryNumber='1' + binaryNumber;
                        }
                        else binaryNumber='0' + binaryNumber;
                        octet4holder/= 2;
                    }

                    //Calculate the binary numbers that were used for the subnet back into decimal.
                    while(y>0){
                        if(binaryNumber.startsWith("1")) {
                            holder+=max;
                            max/=2;
                        }
                        else max/=2;
                        binaryNumber = binaryNumber.substring(1); //removes the first number after use.
                        y--;
                    }
                    max = 128;
                    octet4String = Integer.toString(holder); //convert the holder into a string for display.
                    if(octet4String.length() == 2) octet4String = "0" + octet4String; //Format.
                    else if(octet4String.length() == 1) octet4String = "00" + octet4String; //Format.

                    subMask = octet1String + "." + octet2String + "." + octet3String + "." + octet4String;
                    octet4String = "";
                    subnetMask.setText("Default ID: " + subMask);
                }

                //if it is a class B network address.
                else if(x == 2 && networkClassification.equals("B (Public)")){
                    //Calculate the last octet subnet.
                    binaryNumber = "";
                    max = 128;
                    octet3holder = octet3;
                    //convert to binary.
                    for(int j = 0; j < 8 /*Due to 8 digits max for 255 8*/; j++){
                        if(octet3holder%2 == 1){
                            binaryNumber='1' + binaryNumber;
                        }
                        else binaryNumber='0' + binaryNumber;
                        octet3holder/= 2;
                    }

                    //Calculate the binary numbers that were used for the subnet back into decimal.
                    while(y>0){
                        if(binaryNumber.startsWith("1")) {
                            holder+=max;
                            max = max / 2;
                        }
                        else if(binaryNumber.startsWith("0")) {max = max / 2;}

                        binaryNumber = binaryNumber.substring(1); //removes the first number after use.
                        y--;
                    }
                    max = 128;
                    octet3String = Integer.toString(holder); //convert the holder into a string for display.
                    if(octet3String.length() == 2) octet3String = "0" + octet3String;  //Format.
                    else if(octet3String.length() == 1) octet3String = "00" + octet3String; //Format.

                    subMask = octet1String + "." + octet2String + "." +  octet3String + ".000";
                    octet4String = "";
                    subnetMask.setText("Default ID: " + subMask);
                }

                //Safety catch for user entering invalid slash address.
                if(networkClassification.equals("A (Public)") && x!=1 || networkClassification.equals("B (Public") && x!=2 || networkClassification.equals("C (Public") && x!=3){
                    subnetMask.setText("Default Subnet Mask: " + subnetIdentification);
                }

                //Not sure how to handle class A as has not been taught in Class yet.
                if(networkClassification.equals("A (Public)")) subnetMask.setText("Default Subnet Mask: " + subnetIdentification);


                //Calculating the range.
                //The network portion is the 2^(n) - 2 piece (Cisco), but we would like to include the first and last range.

                //Calculate the range.
                if(networkPortion != 0){
                    //Calculate the number of networks (2^ (bits borrowed by network).
                    amountNetworks = (int) Math.pow(2.0,(double)(networkPortion));
                    //if(amountNetworks < 1) amountNetworks = 1; //safety catch while testing.
                    //Calculate number of hosts.
                    amountHosts = (x + 1) * 8 - amountNetworks; //I have not displayed this, but can later be added.
                    //Calculate the range.
                    range = 256 / amountNetworks;  //I have not displayed this, but can later be added.
                }

                firstUsableRange.setText("Subnet Address");
                lastUsableRange.setText("Broadcast Address");

                //Calculate all the network addresses and broadcast addresses of the subnet entered.
                if(range != 0){
                    int total = 0;
                    while(total < 255){
                        networkAddress.add(total);
                        broadcastAddress.add(total + range - 1);
                        total += range;
                    }

                    //Variables to contain the first and last of the range in which the IP is.
                    int firstRange = 0, lastRange, counter = -1;

                    //Print the correct network address corresponding to the IP.
                    for(int number: networkAddress){
                        //Class C
                        if(x == 3) {
                            if(number < octet4){
                                firstRange = number;
                                counter++;
                            }
                            else break;
                        }
                        //Class B
                        else if(x == 2) {
                            if(number < octet3){
                                firstRange = number;
                                counter++;
                            }
                            else break;
                        }
                    }
                    firstUsableRange.setText(String.valueOf(firstRange));

                    //Print the correct broadcast address corresponding to the IP.
                    try{lastRange = broadcastAddress.get(counter);}
                    catch(Exception e) {lastRange = 255;}

                    lastUsableRange.setText(String.valueOf(lastRange));
                    range = 0;
                    networkAddress.clear();
                    broadcastAddress.clear();
                }

            }

        });

        //Easy way for the user to reset the fields.
        clearFields.setOnClickListener(new View.OnClickListener() {
            //We want the clear button to clear all the fields.
            @Override
            public void onClick(View v) {
                octetOne.setText(null);
                octetTwo.setText(null);
                octetThree.setText(null);
                octetFour.setText(null);
                subnetSlash.setText(null);
                subnetMask.setText("Default Subnet Mask");
                networkClass.setText("Network Class");
                firstUsableRange.setText("Subnet Address");
                lastUsableRange.setText("Broadcast Address");
            }
        });

    }

    //Should send to another screen that displays all possible subnet/broadcast addresses with given subnet.
    public void sendNetworkSecond(View v){
        Intent intent = new Intent(this, NetworkSecond.class);

        //get the user's entered subnet.
        final EditText subnetSlash = (EditText)findViewById(R.id.slashSubnet);
        try {
            subSlash = Integer.parseInt(subnetSlash.getText().toString());
        } catch (NumberFormatException e) {
            subSlash = 24;  //Default subnet.
        }

        //Subnet calculation suggestion for class C.
        if(subSlash > 24){
            x = 0;
            y = 0;
            x = subSlash / 8;
            y = (subSlash - x*8);
            networkPortion = y;
        }

        //Subnet calculation suggestion for class B
        if(subSlash > 16 && subSlash <24){
            x = 0;
            y = 0;
            x = subSlash / 8;
            y = (subSlash - x*8);
            networkPortion = y;
        }

        //Calculate the range.
        if(networkPortion != 0){
            //Calculate the number of networks.
            amountNetworks = (int) Math.pow(2.0,(double)(networkPortion));
            //Calculate number of hosts.
            amountHosts = (x + 1) * 8 - amountNetworks;
            //Calculate the range.
            range = 256 / amountNetworks;
        }


        //Calculate network address and broadcast.
        if(range != 0){
            int total = 0;
            while(total < 255) {
                networkAddress2.add(total);
                broadcastAddress2.add(total + range - 1);
                total += range;
            }
        }

        //Send over to next screen. Make sure that there is a range, else there would be nothing to send.
        if(range!= 0){
            intent.putIntegerArrayListExtra("networkAddress", networkAddress2);
            intent.putIntegerArrayListExtra("broadcastAddress", broadcastAddress2);
        }

        //So that the button will only work if a range is entered.
        if(range!= 0)startActivity(intent);
        else{}

        //reset the network/broadcast address.
        networkAddress2.clear();
        broadcastAddress2.clear();
    }

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
