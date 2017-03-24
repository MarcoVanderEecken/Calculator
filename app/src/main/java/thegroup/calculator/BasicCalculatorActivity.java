package thegroup.calculator;

/**
 * Created by Temporary on 9/11/2015.
 */

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;

 /*
  * This page should be a basic calculator with the button 0-9, +-* /
  *  - Vinnie is in charge of this page.
  */


public class BasicCalculatorActivity extends AppCompatActivity implements View.OnClickListener {
    //<editor-fold desc"Importing my buttons/displays from xml file">
    private Button basicButton1, basicButton2, basicButton3, basicButton4, basicButton5, basicButton6, basicButton7, basicButton8, basicButton9, basicButton0, basicButtonDelete, basicButtonAddition, basicButtonSubtract, basicButtonDivide, basicButtonMultiply, basicButtonAc, basicButtonComma, basicButtonEquals;
    private TextView input, output;
    Double newNumber;
//</editor-fold>

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basic_calculator);
        init();
    }

    //<editor-fold desc="Where I declare everything">
    private void init() {
        //first declare my buttons
        basicButton0 = (Button) findViewById(R.id.basicButton0);
        basicButton1 = (Button) findViewById(R.id.basicButton1);
        basicButton2 = (Button) findViewById(R.id.basicButton2);
        basicButton3 = (Button) findViewById(R.id.basicButton3);
        basicButton4 = (Button) findViewById(R.id.basicButton4);
        basicButton5 = (Button) findViewById(R.id.basicButton5);
        basicButton6 = (Button) findViewById(R.id.basicButton6);
        basicButton7 = (Button) findViewById(R.id.basicButton7);
        basicButton8 = (Button) findViewById(R.id.basicButton8);
        basicButton9 = (Button) findViewById(R.id.basicButton9);
        basicButtonAddition = (Button) findViewById(R.id.basicButtonAddition);
        basicButtonSubtract = (Button) findViewById(R.id.basicButtonSubtract);
        basicButtonDivide = (Button) findViewById(R.id.basicButtonDivide);
        basicButtonMultiply = (Button) findViewById(R.id.basicButtonMultiply);
        basicButtonAc = (Button) findViewById(R.id.basicButtonAc);
        basicButtonDelete = (Button) findViewById(R.id.basicButtonDelete);
        basicButtonEquals = (Button) findViewById(R.id.basicButtonEquals);
        basicButtonComma = (Button) findViewById(R.id.basicButtonComma);

        //then I declare the displays
        input = (TextView) findViewById(R.id.input);
        output = (TextView) findViewById(R.id.output);

        //then I make sure the buttons' responses do not fall back on each other
        basicButton0.setOnClickListener(this);
        basicButton1.setOnClickListener(this);
        basicButton2.setOnClickListener(this);
        basicButton3.setOnClickListener(this);
        basicButton4.setOnClickListener(this);
        basicButton5.setOnClickListener(this);
        basicButton6.setOnClickListener(this);
        basicButton7.setOnClickListener(this);
        basicButton8.setOnClickListener(this);
        basicButton9.setOnClickListener(this);
        basicButtonDelete.setOnClickListener(this);
        basicButtonSubtract.setOnClickListener(this);
        basicButtonMultiply.setOnClickListener(this);
        basicButtonAddition.setOnClickListener(this);
        basicButtonDivide.setOnClickListener(this);
        basicButtonEquals.setOnClickListener(this);
        basicButtonComma.setOnClickListener(this);
        basicButtonAc.setOnClickListener(this);


    }
//</editor-fold>

    //Variables necessary for operations
    int clear_flag = 0;
    String sign_flag = "";
    double total = 0.0;
    int last_button = 0;

    //Make sure the numbers will be displayed when pressed
    public void shownum(String num) {
        if (clear_flag == 1) {
            input.setText("");
            clear_flag = 0;
        } else if (input.getText() == "0") {
            input.setText("");
        }
        input.setText(input.getText() + num);
    }

    //Make sure the signs will display when pressed
    //the reason why if was left  blank is due to nothing being done afterwards
    public void showsign(String sign){
        if (last_button == R.id.basicButtonAddition || last_button == R.id.basicButtonSubtract || last_button == R.id.basicButtonMultiply || last_button == R.id.basicButtonDivide || last_button == R.id.basicButtonEquals) {
            //nothing
        }
        else {
            clear_flag = 1;
            Double newNumber = Double.parseDouble(input.getText().toString());
            if (sign_flag == "" || sign_flag== "="){
                total = newNumber;
                output.setText(total + "");
            }
            else if (sign_flag == "+"){
                total = total + newNumber;
                output.setText(total + "");

            }
            else if (sign_flag == "-"){
                total = total - newNumber;
                output.setText(total + "");
            }
            else if (sign_flag == "*"){
                total = total * newNumber;
                output.setText(total + "");
            }
            else if (sign_flag == "/"){
                total = total / newNumber;
                output.setText(total + "");
            }

        }
        sign_flag = sign;
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.basicButton0) {
            if(input.length() == 0) {} //do nothing
            else shownum("0");
        } else if (v.getId() == R.id.basicButton1) {
            shownum("1");
        } else if (v.getId() == R.id.basicButton2) {
            shownum("2");
        } else if (v.getId() == R.id.basicButton3) {
            shownum("3");
        } else if (v.getId() == R.id.basicButton4) {
            shownum("4");
        } else if (v.getId() == R.id.basicButton5) {
            shownum("5");
        } else if (v.getId() == R.id.basicButton6) {
            shownum("6");
        } else if (v.getId() == R.id.basicButton7) {
            shownum("7");
        } else if (v.getId() == R.id.basicButton8) {
            shownum("8");
        } else if (v.getId() == R.id.basicButton9) {
            shownum("9");
        } else if (v.getId() == R.id.basicButtonAddition) {
            showsign("+");
        } else if (v.getId() == R.id.basicButtonSubtract) {
            showsign("-");
        } else if (v.getId() == R.id.basicButtonMultiply) {
            showsign("*");
        } else if (v.getId() == R.id.basicButtonDivide) {
            showsign("/");
        } else if (v.getId() == R.id.basicButtonAc) {
            input.setText(null);
            output.setText(null);
            total = 0.0;
            sign_flag = "";
        } else if (v.getId() == R.id.basicButtonComma) {
            if (clear_flag == 1) {
                input.setText("");
                clear_flag = 0;
            }
            if (input.getText().toString().indexOf(".")<0){
                input.setText(input.getText() + ".");
            }
        }
        else if (v.getId() == R.id.basicButtonDelete){
            if (input.getText().toString().length()>0){
                int start = 0;
                int end = input.getText().toString().length()-1;
                String newText = input.getText().toString().substring(start,end);
                input.setText(newText);
            }

        }
        else if (v.getId() == R.id.basicButtonEquals){
            try{ newNumber = Double.parseDouble(input.getText().toString());}
            catch(Exception e){newNumber = 0.0;}

            if(sign_flag == "+"){
                total = total + newNumber;
                output.setText(total + "");
            }
            else if (sign_flag == "-"){
                total = total - newNumber;
                output.setText(total + "");
            }
            else if (sign_flag == "*"){
                total = total * newNumber;
                output.setText(total + "");
            }
            else if (sign_flag == "/"){
                total = total / newNumber;
                output.setText(total + "");
            }
            sign_flag = "=";
        }

    }
}

