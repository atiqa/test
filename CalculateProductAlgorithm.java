import java.math.BigInteger;
import java.util.Scanner;


public class CalculateProductAlgorithm {

	interface CalculateSumOfProduct {
		BigInteger doCalculate(BigInteger n, BigInteger c);
	}

	interface CalculateProduct {
		BigInteger doCalculate(BigInteger n, BigInteger c);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
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

		Scanner scanner = new Scanner(System.in);
	    while (true) {
	        System.out.println("Insert input (example: 5,2      or exit)");
	        String input = scanner.nextLine();
	        if(input.equals("exit")){
	        	scanner.close();
	            break;
	        }
	        String[] array = input.split(",");
	        System.out.println("result=" + sum.doCalculate(new BigInteger(array[0]), new BigInteger(array[1])));
	    }
	}

}
