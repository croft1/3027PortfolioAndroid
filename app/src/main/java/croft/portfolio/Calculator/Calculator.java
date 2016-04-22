package croft.portfolio.Calculator;


/**
 * Created by Michaels on 8/3/2016.
 */

public class Calculator {

    double firstNumber;
    double secondNumber;
    String operation;
    double answer;

    public double getAnswer() {
        return answer;
    }

    public void setAnswer(double answer) {
        this.answer = answer;
    }

    public String getOperation() {
        return operation;
    }

    public double getSecondNumber() {
        return secondNumber;
    }

    public double getFirstNumber() {
        return firstNumber;
    }


    //calcualtor constructor is called when we have valid inputs. For every single calculation that is required, we create and delete an object

    public Calculator(double firstNumber, double secondNumber, String operation){
        setFirstNumber(firstNumber);
        setSecondNumber(secondNumber);
        setOperation(operation);
        answer = performOperation();
    }



    public double performOperation(){

        double resultOfCalculation = 123456789;         ///just a random default


        //detect which operation button is selected to do that operation
        switch(operation) {
            case CalculatorMain.ADDITION_SYMBOL:{
                resultOfCalculation = add();
                break;
            }
            case CalculatorMain.DIVISION_SYMBOL:{
                if(canDivide()){
                    resultOfCalculation = divide();
                }
                break;
            }
            case CalculatorMain.SUBTRACTION_SYMBOL:{
                resultOfCalculation = subtract();
                break;
            }
            case CalculatorMain.MULTIPLICATION_SYMBOL:{
                resultOfCalculation = multiply();
                break;
            }
        }
        return roundToTwoDecimalPlaces(resultOfCalculation);
    }


    public double add(){
        return firstNumber + secondNumber;
    }

    public double subtract(){
        return secondNumber - firstNumber;
    }

    public double multiply(){
        return firstNumber * secondNumber;
    }

    public boolean canDivide(){
        boolean canDivide = true;       //simply checks if its by zero
        if(firstNumber == 0 || secondNumber == 0){
            canDivide = false;
        }
        return canDivide;
    }

    public double divide(){
        return firstNumber / secondNumber;
    }

    public double roundToTwoDecimalPlaces(double n){        //simple algorithm for rounding off
        return Math.round(n * 100.0) / 100.0;


    }

    public void setFirstNumber(double n){ firstNumber = n; }
    public void setSecondNumber(double n){ secondNumber = n; }
    public void setOperation(String o){ operation = o.toLowerCase(); }




}

