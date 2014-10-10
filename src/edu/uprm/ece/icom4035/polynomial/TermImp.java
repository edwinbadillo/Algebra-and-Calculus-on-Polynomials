package edu.uprm.ece.icom4035.polynomial;

import java.util.StringTokenizer;
import edu.uprm.ece.icom4035.list.ArrayList;
import edu.uprm.ece.icom4035.list.List;

/**
 * 
 * @author Edwin O. Badillo Mendez
 *
 */
public class TermImp implements Term {

	private double coefficient;
	private int exponent;

	/**
	 * Constructor method for the Term Implementation
	 * @param coefficient The coefficient of the current term
	 * @param exponent The exponent of the current term
	 */
	public TermImp(double coefficient, int exponent) {
		super();
		this.coefficient = coefficient;
		this.exponent = exponent;
	}

	/**
	 * Method to be used to get the value of the coefficient for the current term
	 * @return The coefficient of the term
	 */
	public double getCoefficient() {
		// TODO Auto-generated method stub
		return coefficient;
	}

	/**
	 * Method to be used to get the value of the exponent for the current term
	 * @return The exponent of the term
	 */
	public int getExponent() {
		// TODO Auto-generated method stub
		return exponent;
	}

	/**
	 * Evaluate Method: evaluates the current term with the given x parameter
	 * @param x The value in which to evaluate the term
	 * @return The coefficient of the term
	 */
	public double evaluate(double x) {
		// TODO Auto-generated method stub
		return coefficient * Math.pow(coefficient, exponent);
	}

	/**
	 * toString Method: Converts term into a string
	 * @return The term in string form
	 */
	public String toString() {
		//Given by professor
		if (this.exponent == 0) {
			return String.format("%.2f", this.coefficient);

		} else if (this.exponent == 1) {
			return String.format("%.2fx", this.coefficient);
		} else {
			return String.format("%.2fx^%d", this.coefficient, this.exponent);
		}
	}

	/**
	 * From String Method: Converts a string into a term
	 * @param str The term in string form
	 * @return The given string in term form
	 */
	public static Term fromString(String str) {
		//given by professor
		String temp = new String(str);
		TermImp result = null;
		if (temp.contains("x^")) {
			// handle term with the form ax^n
			StringTokenizer strTok = new StringTokenizer(temp, "x^");
			List<String> list = new ArrayList<String>(2);
			while (strTok.hasMoreElements()) {
				list.add((String) strTok.nextElement());
			}

			if (list.size() == 0) {
				throw new IllegalArgumentException(
						"Argument string is formatter illegally.");
			} else if (list.size() == 1) {
				// term if of the form x^n, where n is the exponent
				Integer expo = Integer.parseInt(list.get(0));
				result = new TermImp(1, expo);
			} else {
				// term if of the form ax^n, where a, (a != 1) is the
				// coefficient and n is the exponent
				Double coeff = Double.parseDouble(list.get(0));
				Integer expo = Integer.parseInt(list.get(1));
				result = new TermImp(coeff, expo);
			}
		} else if (temp.contains("x")) {
			// handle value with exponent == 1
			StringTokenizer strTok = new StringTokenizer(temp, "x");
			List<String> list = new ArrayList<String>(2);
			while (strTok.hasMoreElements()) {
				list.add((String) strTok.nextElement());
			}
			if (list.size() == 0) {
				// term is of the form x, with coefficient = 1 and exponent = 1
				result = new TermImp(1.0, 1);
			} else {
				// term is of the form ax, with coefficient = a and exponent = 1
				Double coeff = Double.parseDouble(list.get(0));
				result = new TermImp(coeff, 1);
			}
		} else {
			// handle numeric value
			result = new TermImp(Double.parseDouble(temp), 0);
		}
		return result;
	}

}
