package com.dersugarcia.calculator;

import com.dersugarcia.calculator.factories.OperationFactory;
import com.dersugarcia.calculator.interfaces.IOperation;

public final class Calculator {

	private final static Calculator instance = new Calculator();
	private String output;
	private float result;
	private IOperation operation;
	private boolean isNewOperation;
	
	private Calculator() {
		operation = null;
		output = "";
		isNewOperation = false;
	}
	
	public static Calculator getInstance() {

		return instance;
	}
	
	public void touchNumber(String num) {
		resetOutput();
		output += num;
	}
	
	private void resetOutput() {
		if(isNewOperation) {
			output="";
			isNewOperation = false;
		}
	}

	public void touchOperation(char op) {
		switch (op) {
		case '+':
			preprocessOperation();
			operation = OperationFactory.getAddInstance();
			break;
		case '-':
			preprocessOperation();
			operation = OperationFactory.getSubstractInstance();
			break;
		case '*':
			preprocessOperation();
			operation = OperationFactory.getMultiplyInstance();
			break;
		case ':':
			preprocessOperation();
			operation = OperationFactory.getDivideInstance();
			break;
		case '=':
			applyOperation();
			operation = null;
			break;
		default:
//			throw new InvalidOperationException();
		}
	}
	
	private void preprocessOperation() {
		if(result==0) {
			result = Float.parseFloat(output);
		} else if(operation!=null) {
			applyOperation();
		}
		isNewOperation=true;
	}
	
	private void applyOperation() {
		result = operation.apply(result, Float.parseFloat(output));
		output = Float.toString(result);
	}
	
	public String getOutput() {
		return output;
	}
	//For debugging use
	@Override
	public String toString() {
		return "Calculadora{output: " + output + ", result: " + result + ", operation: " + operation.getClass().getSimpleName() + ", isNewOperation: " + isNewOperation + "}";
	}
}
