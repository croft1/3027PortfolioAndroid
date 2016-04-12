package croft.todo.Calculator;



        import java.math.*;

/**
 * Created by Michaels on 8/3/2016.
 */

public class Calculator {

    double firstNumber;
    double secondNumber;
    String operation;

    public Calculator(double firstNumber, double secondNumber, String operation){
        setFirstNumber(firstNumber);
        setSecondNumber(secondNumber);
        setOperation(operation);
    }

    public double performOperation(){

        double resultOfCalculation = 123456789;

        switch(operation) {
            case "+":{
                resultOfCalculation = add();
                break;
            }
            case "/":{
                if(canDivide()){
                    resultOfCalculation = divide();
                }else{
                    //TODO: check for divide by 0 on calc press

                }
                break;
            }
            case "-":{
                resultOfCalculation = subtract();
                break;
            }
            case "*":{
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
        boolean canDivide = true;
        if(firstNumber == 0 || secondNumber == 0){
            canDivide = false;
        }
        return canDivide;
    }

    public double divide(){
        return firstNumber / secondNumber;
    }

    public double roundToTwoDecimalPlaces(double n){
        return Math.round(n * 100.0) / 100.0;


    }

    public void setFirstNumber(double n){ firstNumber = n; }
    public void setSecondNumber(double n){ secondNumber = n; }
    public void setOperation(String o){ operation = o.toLowerCase(); }




}

