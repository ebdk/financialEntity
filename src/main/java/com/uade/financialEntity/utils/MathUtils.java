package com.uade.financialEntity.utils;

import java.util.Random;

public class MathUtils {

	public static Integer generateRandomNumber(Integer min, Integer max) {
		Random r = new Random();
		return r.nextInt(max - min) + min;
	}

	public static Integer getPercentage(Integer value, Integer percentage) {
		return (int) (value * (percentage / 100.0f)); //10% of the salary;
	}

	public static Integer getPercantageBewteenTwoRandom(Integer value, Integer min, Integer max) {
		return getPercentage(value, generateRandomNumber(min, max));
	}

}
