package com.dersugarcia.calculator.operations;

import com.dersugarcia.calculator.interfaces.IOperation;

public class Multiply implements IOperation {

	@Override
	public float apply(float num1, float num2) {
		return num1 * num2;
	}

	public char getSymbol() {
		return '*';
	}
}
