package com.dersugarcia.fragmentedcalculator;

import com.dersugarcia.fragmentedcalculator.factories.OperationFactory;
import com.dersugarcia.fragmentedcalculator.interfaces.IOperation;

public final class Calculator {

	private static Calculator instance = null;
	private String display;
	private float result;
	private IOperation operation;
	private boolean isNewOperation;
	
	private Calculator() {
		reset();
	}
	
	public static Calculator getInstance() {
		if(instance == null) {
			instance = new Calculator();
		}
		return instance;
	}
	
	public String getDisplay() {
		return display;
	}
	
	public char getOperation() {
		return operation.getSymbol();
	}
	
	public void inputNumber(char num) {
		resetDisplay();
		if(num != '.' || !display.contains(".")) {
			display += num;
		}
	}
	
	private void resetDisplay() {
		if(isNewOperation) {
			display="";
			isNewOperation = false;
		}
	}

	public void inputOperation(char op) {
		preprocessOperation();
		switch (op) {
		case '+':
			operation = OperationFactory.getAddInstance();
			break;
		case '-':
			operation = OperationFactory.getSubstractInstance();
			break;
		case '*':
			operation = OperationFactory.getMultiplyInstance();
			break;
		case ':':
			operation = OperationFactory.getDivideInstance();
			break;
		default:
//			throw new InvalidOperationException();
		}
	}
	
	private void preprocessOperation() {
		if(result==0) {
			result = Float.parseFloat(display);
		} else if(operation!=null) {
			applyOperation();
		}
		isNewOperation=true;
	}
	
	private void applyOperation() {
		result = operation.apply(result, Float.parseFloat(display));
		display = Float.toString(result);
	}
	
	public void inputEquals() {
		if(operation!=null) {
			applyOperation();
			operation = null;
			isNewOperation = true;
		}
	}
	
	public void inputDelete() {
		reset();
	}
	
	private void reset() {
		operation = null;
		display = "0";
		isNewOperation = true;
		result = 0;
	}

}
