import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigInteger;
import java.util.Scanner;

import org.junit.Test;


public class CalculateProductAlgorithm {

	interface CalculateSumOfProduct {
		BigInteger doCalculate(BigInteger n, BigInteger c);
	}

	interface CalculateProduct {
		BigInteger doCalculate(BigInteger n, BigInteger c);
	}
	
	public static BigInteger calculateSumOfProduct(BigInteger N, BigInteger C) {
		CalculateProduct firstToLast = (BigInteger first, BigInteger last) -> {
			if(first.compareTo(last) > 0) return BigInteger.ZERO;
			first = first.compareTo(BigInteger.ZERO) <= 0? BigInteger.ONE : first;
			BigInteger result = BigInteger.ONE;
			for(BigInteger i = first; i.compareTo(last) <= 0; i = i.add( BigInteger.ONE )) {
				result = result.multiply(i);
			}
			return result;
		};
		CalculateProduct product = (BigInteger n, BigInteger c) ->  { return c.compareTo(n.subtract(BigInteger.ONE)) > 0? firstToLast.doCalculate(BigInteger.ONE, n.subtract(BigInteger.ONE)) : firstToLast.doCalculate(n.subtract(c), n.subtract(BigInteger.ONE)); };

		CalculateSumOfProduct sum = (BigInteger n, BigInteger c) -> { 
			BigInteger result = BigInteger.ZERO;
			for(BigInteger i = BigInteger.ONE; i.compareTo(n) <= 0; i = i.add( BigInteger.ONE )) {
				result = result.add(product.doCalculate(i, c));
			}
			return result;
			
		};
		return sum.doCalculate(N, C);
	}
	
	@Test
	public void runTests() {
	      assertEquals(calculateSumOfProduct(new BigInteger("5"), new BigInteger("2")), new BigInteger("21"));
	      assertEquals(calculateSumOfProduct(new BigInteger("100"), new BigInteger("10")), new BigInteger("513946235090696089113"));
	      assertTrue(calculateSumOfProduct(new BigInteger("1000000"), new BigInteger("200")).toString().startsWith("4876116127"));

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		

		Scanner scanner = new Scanner(System.in);
	    while (true) {
	        System.out.println("Insert input (example: 5,2      or exit)");
	        String input = scanner.nextLine();
	        if(input.equals("exit")){
	        	scanner.close();
	            break;
	        }
	        String[] array = input.split(",");
	        System.out.println("result=" + calculateSumOfProduct(new BigInteger(array[0]), new BigInteger(array[1])));
	    }
	}

}
