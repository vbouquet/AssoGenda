package fr.paris10.projet.assogenda.assogenda.model;

public class Agenda {

    public int dayStart;
    public int yearStart;
    public int hourStart;
    public int minStart;

    public int monthEnd;
    public int dayEnd;
    public int yearEnd;
    public int hourEnd;
    public int minEnd;

    public void initDate(String dateStart, String dateEnd) {

        dayStart = Integer.parseInt(dateStart.substring(6, 8));
        yearStart = Integer.parseInt(dateStart.substring(12, 16));
        hourStart = Integer.parseInt(dateStart.substring(0, 2));
        minStart = Integer.parseInt(dateStart.substring(3, 5));

        monthEnd = Integer.parseInt(dateEnd.substring(9, 11));
        dayEnd = Integer.parseInt(dateEnd.substring(6, 8));
        yearEnd = Integer.parseInt(dateEnd.substring(12, 16));
        hourEnd = Integer.parseInt(dateEnd.substring(0, 2));
        minEnd = Integer.parseInt(dateEnd.substring(3, 5));
    }
}
