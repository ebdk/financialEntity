package com.uade.financialEntity.utils;

public class MathUtils {

	public static Integer getPercentage(Integer value, Integer percentage) {
		return (int) (value * (percentage / 100.0f)); //10% of the salary;
	}

}
