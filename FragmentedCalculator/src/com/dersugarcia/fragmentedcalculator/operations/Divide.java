package com.dersugarcia.fragmentedcalculator.operations;

import com.dersugarcia.fragmentedcalculator.interfaces.IOperation;

public class Divide implements IOperation {

	@Override
	public float apply(float num1, float num2) {
		if (num2==0) {
			//DivideByZeroException
			return 0;
		}
		return num1 / num2;
	}
	
	public char getSymbol() {
		return '/';
	}

}
