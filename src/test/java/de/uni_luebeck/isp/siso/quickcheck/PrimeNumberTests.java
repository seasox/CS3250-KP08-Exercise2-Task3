package de.uni_luebeck.isp.siso.quickcheck;

import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.generator.InRange;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(JUnitQuickcheck.class)
public class PrimeNumberTests {
    
    @Property
    public void primeFactorsShouldBePrimeNumbers(@InRange(min = "2", max = "999999999") Long a) {
        for (long number: PrimeCalculator.primeFactorization(a)) {
            // assertions derived from PrimeCalculator.isPrime. We do not use the actual implementation from
            // PrimeCalculator to be independent of possible bugs introduced in isPrime
            assertTrue(number == 2 || number < 2 || number % 2 != 0);
            for (int i = 3; i * i <= number; i += 2)
                assertNotEquals(0, number % i);
        }
    }

    @Property
    public void primeFactorsShouldMultiplyToNumber(@InRange(min = "2", max = "999999999") Long a) {
        long result = 1;
        for (long factor: PrimeCalculator.primeFactorization(a)) {
            result *= factor;
        }
        assertEquals((long)a, result);
    }

    @Property
    public void primeFactorsShouldBeInAscendingOrder(@InRange(min = "2", max = "999999999") Long a) {
        List<Long> primeFactors = PrimeCalculator.primeFactorization(a);
        for (int i = 0; i < primeFactors.size() - 1; ++i) {
            assertTrue(primeFactors.get(i) <= primeFactors.get(i+1));
        }
    }
}