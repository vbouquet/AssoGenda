package fr.paris10.projet.assogenda.assogenda.util;

/**
 * Created by valentin on 22/01/17.
 */

public class Period {
    public int begin;
    public int end;

    public Period(int begin, int end) throws PeriodException {
        if (begin > end || begin == end)
            throw new PeriodException("Begin must be inferior to the end of the period");
        if (begin < 0 || begin > 24)
            throw new PeriodException("Begin of period can't be negative of exceed 24 hours");
        if (end < 0 || end > 24)
            throw new PeriodException("End of a period can't be negative or exceed 24 hours");
        this.begin = begin;
        this.end = end;
    }
}
