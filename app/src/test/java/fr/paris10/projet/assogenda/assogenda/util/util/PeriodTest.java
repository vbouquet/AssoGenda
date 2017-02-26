package fr.paris10.projet.assogenda.assogenda.util.util;

import org.junit.Test;

import fr.paris10.projet.assogenda.assogenda.util.Period;
import fr.paris10.projet.assogenda.assogenda.util.PeriodException;

import static org.junit.Assert.*;

public class PeriodTest {
    @Test(expected = PeriodException.class)
    public void testNegativePeriodException() throws Exception {
        Period period = new Period(-10, 20);
    }

    @Test(expected = PeriodException.class)
    public void testReversePeriodException() throws Exception {
        Period period = new Period(24, 0);
    }

    @Test(expected = PeriodException.class)
    public void testNullPeriodException() throws Exception {
        Period period = new Period(20, 20);
    }

    @Test
    public void testNoException() throws Exception {
        Period period = new Period(0, 24);
        assertEquals(period.begin, 0);
        assertEquals(period.end, 24);
    }
}