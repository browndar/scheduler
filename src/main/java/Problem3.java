import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: dbrown
 * Date: 6/11/15
 * Time: 10:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class Problem3{

    public static void main( String[] args ) throws IOException {
        long number = 600851475143l;
        System.out.println("is two prime?: " + isPrime(2l));
        try {
            System.out.println("Largest prime factor of " + number + ": " + largestPrimeFactor(number));
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public static boolean isPrime(Long number) {
        for (long i = 2l; i < number; i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean isFactor (long factor, long number) {
        return (number % factor == 0);
    }

    public static long largestPrimeFactor(long number) throws Exception {
        for (long i = 2; i < number; i++) {
            if (isFactor(i,number) && isPrime(number / i)) {
                return (number /i);
            }
        }
        throw new Exception("FUCK THIS");
    }
}
