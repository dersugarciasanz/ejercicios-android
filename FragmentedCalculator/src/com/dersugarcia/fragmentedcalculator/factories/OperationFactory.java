package com.dersugarcia.fragmentedcalculator.factories;

import com.dersugarcia.fragmentedcalculator.operations.*;


public abstract class OperationFactory {
	
	public static Add getAddInstance() {
		return new Add();
	}
	public static Substract getSubstractInstance() {
		return new Substract();
	}
	public static Multiply getMultiplyInstance() {
		return new Multiply();
	}
	public static Divide getDivideInstance() {
		return new Divide();
	}
}
