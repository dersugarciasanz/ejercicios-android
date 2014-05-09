package com.dersugarcia.fragmentedcalculator.operations;

import com.dersugarcia.fragmentedcalculator.interfaces.IOperation;

public class Add implements IOperation {

	@Override
	public float apply(float num1, float num2) {
		return num1 + num2;
	}

	public char getSymbol() {
		return '+';
	}
}
