package edu.uprm.ece.icom4035.polynomial;

import java.util.Iterator;
import java.util.StringTokenizer;

import edu.uprm.ece.icom4035.list.ArrayList;
import edu.uprm.ece.icom4035.polynomial.Term;

/**
 * @author Edwin O. Badillo Mendez
 * 
 */
public class PolynomialImp implements Polynomial {
	private ArrayList<Term> terms;

	/**
	 * Constructor method for PolynomialImp
	 * 
	 * @param string
	 *            Polynomial in string form
	 */
	public PolynomialImp(String string) {
		// TODO Auto-generated constructor stub
		terms = new ArrayList<Term>(10);
		// receive string polynomial
		fromString(string);
	}

	/**
	 * Constructor method for PolynomialImp
	 */
	public PolynomialImp() {
		terms = new ArrayList<Term>(10);
	}

	/**
	 * Returns an iterator over a set of elements of type Term.
	 * 
	 * @return iterator
	 */
	public Iterator<Term> iterator() {
		return terms.iterator();
	}

	/**
	 * Addition Method: This method adds the current polynomial with the
	 * received polynomial P2
	 * 
	 * @param P2
	 *            Polynomial to add
	 * @return A polynomial composed of the sum of both polynomials
	 */
	@Override
	public Polynomial add(Polynomial P2) {
		if (P2.equals(new PolynomialImp("0"))) {
			return this;
		}
		PolynomialImp result = new PolynomialImp();

		ArrayList<Term> temp = new ArrayList<Term>(10);

		// Passing received Polynomial to an arraylist
		for (Term e : P2) {
			temp.add(e);
		}
		double equalSum = 0.0;
		for (int i = 0, j = 0; i < temp.size() || j < this.terms.size();) {

			if (i != temp.size() && j != this.terms.size()) {

				// if the exponents are the same we need to add the coefficients
				if (temp.get(i).getExponent() == this.terms.get(j)
						.getExponent()) {
					equalSum = temp.get(i).getCoefficient()
							+ this.terms.get(j).getCoefficient();
					// if sum coefficient is zero do nothing
					if (equalSum != 0) {
						result.addTerm(new TermImp(equalSum, this.terms.get(j)
								.getExponent()));
					}
					i++;
					j++;
				} else if (temp.get(i).getExponent() > this.terms.get(j)
						.getExponent()) {
					result.addTerm(temp.get(i));
					i++;
				} else if (this.terms.get(j).getExponent() > temp.get(i)
						.getExponent()) {
					result.addTerm(this.terms.get(j));
					j++;
				}
			}

			// for different sized polynomials we check which one we have
			// finished adding
			else {
				if (i == temp.size()) {
					result.addTerm(this.terms.get(j));
					j++;

				} else if (j == this.terms.size()) {
					result.addTerm(temp.get(i));
					i++;
				}
			}
		}
		return result;
	}

	/**
	 * Subtraction Method: This method subtracts the current polynomial with the
	 * received polynomial P2
	 * 
	 * @param P2
	 *            Polynomial to subtract
	 * @return A polynomial composed of the difference of both polynomials
	 */
	@Override
	public Polynomial subtract(Polynomial P2) {

		return this.add(P2.multiply(-1));
	}

	/**
	 * Polynomial Multiplication Method: This method multiplies the current
	 * polynomial with the received polynomial P2
	 * 
	 * @param P2
	 *            Polynomial to multiply
	 * @return A polynomial composed of the multiplication of both polynomials
	 */
	@Override
	public Polynomial multiply(Polynomial P2) {

		// if we multiply by a polynomial of 0 the result is 0
		if (P2.equals(new PolynomialImp("0"))) {
			return new PolynomialImp("0");
		}

		Polynomial temp2 = new PolynomialImp("0");
		Polynomial temp;
		for (Term p : this.terms) {
			for (Term p2 : P2) {
				temp = new PolynomialImp();

				// multiplying terms
				temp.addTerm(new TermImp(p.getCoefficient()
						* p2.getCoefficient(), p.getExponent()
						+ p2.getExponent()));

				// adding like terms
				temp2 = temp2.add(temp);
			}
		}
		return temp2;

	}

	/**
	 * Constant Multiplication Method: This method multiplies the current
	 * polynomial with the constant c
	 * 
	 * @param c
	 *            Constant to multiply
	 * @return A polynomial composed of the difference of both polynomials
	 */
	@Override
	public Polynomial multiply(double c) {
		PolynomialImp temp = new PolynomialImp();

		// if we multiply by 0 then the resulting polynomial should be 0
		if (c == 0) {
			temp.addTerm(new TermImp(0, 0));
		} else {
			for (Term e : this) {

				// multiplying the coefficient by the constant c
				temp.addTerm(new TermImp(e.getCoefficient() * c, e
						.getExponent()));
			}
		}
		return temp;
	}

	/**
	 * Derivative Method: This method calculates the derivative of the current
	 * polynomial
	 * 
	 * @return A polynomial composed of the derivative
	 */
	public Polynomial derivative() {
		PolynomialImp temp = new PolynomialImp();
		Term derivativeTerm;
		for (int i = 0; i < terms.size(); i++) {

			// multiplying the coefficient by the exponent and lowering the
			// exponent by 1
			derivativeTerm = new TermImp(terms.get(i).getCoefficient()
					* terms.get(i).getExponent(),
					terms.get(i).getExponent() - 1);
			// if the coefficient is 0, like in case of deriving a constant, do
			// not add the term
			if (derivativeTerm.getCoefficient() != 0) {
				temp.addTerm(derivativeTerm);
			}
		}
		return temp;
	}

	/**
	 * Indefinite Integral Method: This method calculates the indefinite
	 * integral of the current polynomial, the constant is equaled to 1
	 * 
	 * @return A polynomial composed of the indefinite integral
	 */
	public Polynomial indefiniteIntegral() {
		PolynomialImp temp = new PolynomialImp();
		Term integralTerm;
		for (int i = 0; i < terms.size(); i++) {
			integralTerm = new TermImp((terms.get(i).getCoefficient())
					/ (terms.get(i).getExponent() + 1), terms.get(i)
					.getExponent() + 1);
			temp.addTerm(integralTerm);
		}
		// the unknown constant c is set to 1
		temp.addTerm(new TermImp(1, 0));

		return temp;
	}

	/**
	 * Definite Integral Method: This method calculates the value of the
	 * integral with range "a" to "b"
	 * 
	 * @param a
	 *            The beginning of the range to evaluate the integral
	 * @param b
	 *            The end of the range to evaluate the integral
	 * @return The result of the integral
	 */
	public double definiteIntegral(double a, double b) {
		// we use the definite integral and then evaluate it based of the
		// parameters given
		return this.indefiniteIntegral().evaluate(b)
				- this.indefiniteIntegral().evaluate(a);
	}

	/**
	 * Degree Method: returns the degree of the current polynomial
	 * 
	 * @return The degree of the Polynomial
	 */
	public int degree() {
		// the polynomial is given in order therefore the first exponent is the
		// highest degree
		return terms.get(0).getExponent();
	}

	/**
	 * Evaluate Method: This method solves the polynomial with a specified value
	 * of X
	 * 
	 * @param x
	 *            The value of x
	 * @return The result of evaluating the polynomial
	 */
	public double evaluate(double x) {
		double result = 0;

		// evaluating one term at a time given the x parameter
		for (Term e : this) {
			result += e.getCoefficient() * Math.pow(x, e.getExponent());
		}
		return result;
	}

	/**
	 * Equals Method: This method verifies that the current polynomial is the
	 * same as the received polynomial P2 and returns a boolean
	 * 
	 * @param P
	 *            Polynomial to check
	 * @return If the polynomials are the same
	 */
	@Override
	public boolean equals(Polynomial P) {
		return this.toString().equals(P.toString());
	}

	/**
	 * Add Term Method: Adds new terms to the Polynomial
	 * 
	 * @param nextTerm
	 *            The new term
	 */
	public void addTerm(Term nextTerm) {
		terms.add(nextTerm);
	}

	/**
	 * From String Method: Converts a string into a Polynomial composed of terms
	 * 
	 * @param str
	 *            The string to be converted
	 */
	public void fromString(String str) {
		// Method given by professor
		StringTokenizer strTok = new StringTokenizer(str, "+");
		String nextStr = null;
		Term nextTerm = null;
		this.terms.clear();
		while (strTok.hasMoreElements()) {
			nextStr = (String) strTok.nextElement();
			nextTerm = TermImp.fromString(nextStr);
			// private method to store a new term into a polynomial
			this.addTerm(nextTerm);
		}
	}

	/**
	 * To String Method: Converts a Polynomial composed of terms into a string
	 * 
	 * @return The polynomial in string form
	 */
	public String toString() {
		String polynomialString = "";
		if (terms.size() == 0) {
			return "0.00";
		}
		for (int i = 0; i < terms.size(); i++) {
			if (i < terms.size() - 1) {
				polynomialString += terms.get(i).toString() + "+";
			} else {
				polynomialString += terms.get(i).toString();
			}
		}
		return polynomialString;
	}

}
