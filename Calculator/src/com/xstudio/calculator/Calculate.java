package com.xstudio.calculator;

import java.math.BigDecimal;

public class Calculate {
	private int scale = 4;
	
	public void setScale(int scale) {
		this.scale = scale;
	}
	
	public static double add(BigDecimal num1,BigDecimal num2) {
		return num1.add(num2).doubleValue();
	}
	
	public static double sub(BigDecimal num1,BigDecimal num2) {
		return num1.subtract(num2).doubleValue();
	}
	
	public static double mul(BigDecimal num1,BigDecimal num2) {
		return num1.multiply(num2).doubleValue();
	}
	
	public static double div(BigDecimal num1,BigDecimal num2, int scale) {
		return num1.divide(num2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
}
