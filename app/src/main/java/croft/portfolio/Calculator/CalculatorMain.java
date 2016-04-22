package croft.portfolio.Calculator;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import croft.portfolio.R;

//controller
public class CalculatorMain extends AppCompatActivity {

    String selectedOperation;
    private EditText firstNumberInput;
    private EditText secondNumberInput;
    private TextView firstNumView;
    private TextView secondNumView;
    private TextView opView;

    public static final String ADDITION_SYMBOL = "+";
    public static final String SUBTRACTION_SYMBOL = "-";
    public static final String MULTIPLICATION_SYMBOL = "*";
    public static final String DIVISION_SYMBOL = "/";

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculator_main);
        setTitle("Simple Calculator");

        //set up button, disable until you are allowed to calculate
        Button calcBtn = (Button) findViewById(R.id.calculate);
        calcBtn.setEnabled(false);
        calcBtn.setBackgroundColor(Color.parseColor("#ff5a595b"));

            //set up references to UI objects to uselater
        firstNumberInput = (EditText) findViewById(R.id.firstNumberInput);
        secondNumberInput = (EditText) findViewById(R.id.secondNumberInput);
        firstNumView = (TextView) findViewById(R.id.firstNumView);
        secondNumView = (TextView) findViewById(R.id.secondNumView);

        firstNumberInput.addTextChangedListener(w);
        secondNumberInput.addTextChangedListener(w);

        opView = (TextView) (findViewById(R.id.selOperView));
        final Button bAddition = (Button) findViewById(R.id.additionButton);
        final Button bSubtract = (Button) findViewById(R.id.subtractButton);
        final Button bMultiply = (Button) findViewById(R.id.multiplyButton);
        final Button bDivide = (Button) findViewById(R.id.divideButton);

        bAddition.setText(ADDITION_SYMBOL);
        bDivide.setText(DIVISION_SYMBOL);
        bMultiply.setText(MULTIPLICATION_SYMBOL);
        bSubtract.setText(SUBTRACTION_SYMBOL);


        //click listener is to detect when a click occurs
        View.OnClickListener opButtonListener = new View.OnClickListener(){
            @Override
            public void onClick(View v){        //anonymous on click method taking in a view of what operation button is clicked
                setSelectedOperation( ((Button) v).getText().toString());           //set operation to the currently chosen element
                //System.out.println(getSelectedOperation() + "onCLICKLISTENER WORKING");
                opView.setText(getSelectedOperation());
                if(calcEnableAllowed()){
                    enableCalculateButton();    //
                }
            }
        };

        bAddition.setOnClickListener(opButtonListener);
        bSubtract.setOnClickListener(opButtonListener);
        bMultiply.setOnClickListener(opButtonListener);
        bDivide.setOnClickListener(opButtonListener);

    }


    //text watcher sees the number inputs and changes its response depending on what is input
    private final TextWatcher w = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //TODO: set limits for the number of characters for input and output.
            if(calcEnableAllowed()){
                enableCalculateButton();
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            //allows calculation if requirements are met
            firstNumView.setText(firstNumberInput.getText().toString());
            secondNumView.setText(secondNumberInput.getText().toString());

        }
    };

    public void calculateButtonPressed(View v) {

        //get text from fields
        double firstNumber = Double.valueOf(firstNumberInput.getText().toString());
        double secondNumber = Double.valueOf(secondNumberInput.getText().toString());

        //ensures you can't divide by zero
        if(selectedOperation.toString() == DIVISION_SYMBOL &&                      //checking div by 0
                (secondNumber == 0|| firstNumber == 0)){

            Toast.makeText(getApplicationContext(), "Can't divide by zero", Toast.LENGTH_SHORT).show();

        }else{


            //create calculator object which is made to store the question data and perform it inside
            Calculator calc = new Calculator(firstNumber, secondNumber, selectedOperation);


            //form the calc object we update the UI
            TextView resultText = (TextView) findViewById(R.id.resultDisplayer);
            resultText.setText(String.valueOf(calc.getAnswer()));

            Double result = calc.roundToTwoDecimalPlaces(calc.performOperation());

            resultText.setText(String.valueOf(result));
        }


        //TODO: store history of calculations in a file, so user can retrieve them
    }



    public boolean calcEnableAllowed(){
    //method to call in certain parts to see if  calc button when parameters are met
        return selectedOperation != null &&                               //check empty or unselected
                secondNumberInput.getText().toString().length() > 0 &&
                firstNumberInput.getText().toString().length() > 0;


    }

    //simply enables button
    public void enableCalculateButton() {
        Button c = (Button) findViewById(R.id.calculate);                   //activate button
        c.setBackgroundColor(getPrimary());
        c.setEnabled(true);
    }

    //getters setters

    public int getAccent() {
        return ContextCompat.getColor(getApplicationContext(), R.color.colorAccent);
    }

    public int getPrimary() {
        return ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary);
    }

    public int getPrimDark() {
        return ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary);
    }

    public int getUnfocussed(){
        return ContextCompat.getColor(getApplicationContext(), Color.parseColor("#5A595B"));
    }

    public void setSelectedOperation(String operation) {
        selectedOperation = operation;
    }

    public String getSelectedOperation(){
        return selectedOperation;
    }
}





