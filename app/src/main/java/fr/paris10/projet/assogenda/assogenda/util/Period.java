package fr.paris10.projet.assogenda.assogenda.util;

/**
 * Created by valentin on 22/01/17.
 */

public class Period {
    private int begin;
    private int end;

    public Period(int begin, int end) throws PeriodException{
        if(begin > end || begin == end)
            throw new PeriodException("Begin must be lower or different than end");
        if(begin < 0 || begin > 24 || end < 0 || begin > 24 || begin > end)
            throw new PeriodException("A period can't exceed 24hours or have negative values");
        this.begin = begin;
        this.end = end;
    }
}
