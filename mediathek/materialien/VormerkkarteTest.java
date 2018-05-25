package de.uni_hamburg.informatik.swt.se2.mediathek.materialien;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import de.uni_hamburg.informatik.swt.se2.mediathek.fachwerte.Datum;
import de.uni_hamburg.informatik.swt.se2.mediathek.fachwerte.Geldbetrag;
import de.uni_hamburg.informatik.swt.se2.mediathek.fachwerte.Kundennummer;
import de.uni_hamburg.informatik.swt.se2.mediathek.materialien.medien.CD;
import de.uni_hamburg.informatik.swt.se2.mediathek.materialien.medien.Medium;

public class VormerkkarteTest
{
    private Vormerkkarte _karte;
    private Kunde _kunde;
      private Kunde _kunde;
    private Kunde _kunde1;
    private Kunde _kunde2;
    private Kunde _kunde3;
    private Medium _medium;

    public VormerkkarteTest()
    {
        _kunde = new Kunde(new Kundennummer(123456), "ich", "du");
        _kunde1 = new Kunde(new Kundennummer(123457), "ic", "d");
        _kunde2 = new Kunde(new Kundennummer(123458), "ih", "u");
        _kunde3 = new Kunde(new Kundennummer(123459), "ch", "d");

        _medium = new CD("aa", "bb", "cc", 123);
        _karte = new Verleihkarte(_medium);
    }

    @Test
    public void testegetFormatiertenString() throws Exception
    {
        assertNotNull(_karte.getFormatiertenString());
    }

    @Test
    public void testeKonstruktor() throws Exception
    {
        assertEquals(_kunde, _karte.getEntleiher());
        assertEquals(_medium, _karte.getMedium());
        assertEquals(_datum, _karte.getAusleihdatum());
    }

    @Test
    public void testgetErstenVormerker()
    {
      karte1 = new Vormerkkarte(_medium);
     _vormerker.add(_kunde);
     assertEquals(_kunde, _karte.getErstenVormerker());
    }

    @Test
    public void testmerkeVor()
    {
      Vormerkkarte karte1 = new Vormerkkarte(_medium);
      int groesse = _vormerker.size();
      karte1.merkeVor(_kunde);
      assertEquals(groesse+1, _vormerker.size());
      assertEquals(_kunde, _vormerker.get(_vormerker.size()-1));
      groesse= _vormerker.size();
      karte1.merkeVor(_kunde);
      assertEquals(groesse, _vormerker.size())
    }
  
  
    @Test
    public void testistVormerkenMoeglich()
    {
      Vormerkkarte karte1 = new Vormerkkarte(_medium);
      assertTrue(karte1.istVormerkenMoeglich());
      karte1.merkeVor(_kunde1);
      karte1.merkeVor(_kunde2);
      karte1.merkeVor(_kunde3);
      assertFalse(karte1.istVormerkenMoeglich);      
    }
  
    @Test
    public void testgetMedium()
    {
      Vormerkkarte karte1 = new Vormerkkarte(_medium);
      assertEquals(_medium, karte1.getMedium());  
    }
}
