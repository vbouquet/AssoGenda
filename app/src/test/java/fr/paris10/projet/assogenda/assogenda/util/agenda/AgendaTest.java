package fr.paris10.projet.assogenda.assogenda.util.agenda;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.paris10.projet.assogenda.assogenda.model.Agenda;

import static org.junit.Assert.*;

public class AgendaTest {

    private Agenda agenda;
    private String dateStart;
    private String dateEnd;

    @Before
    public void setUp() throws Exception {
        dateStart = "10:12 06/05/2017";
        dateEnd = "18:12 07/05/2017";
        agenda = new Agenda();
    }

    @After
    public void tearDown() throws Exception {
        dateStart = null;
        dateEnd = null;
    }

    @Test
    public void initDate() throws Exception {
        agenda.initDate(dateStart, dateEnd);
        assertEquals(agenda.dayStart, 6);
        assertEquals(agenda.yearStart, 2017);
        assertEquals(agenda.hourStart, 10);
        assertEquals(agenda.minStart, 12);
        assertEquals(agenda.dayEnd, 7);
        assertEquals(agenda.monthEnd, 5);
        assertEquals(agenda.yearEnd, 2017);
        assertEquals(agenda.hourEnd, 18);
        assertEquals(agenda.minEnd, 12);
    }
}