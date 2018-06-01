package de.uni_hamburg.informatik.swt.se2.mediathek.materialien;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import de.uni_hamburg.informatik.swt.se2.mediathek.fachwerte.Kundennummer;
import de.uni_hamburg.informatik.swt.se2.mediathek.materialien.medien.CD;
import de.uni_hamburg.informatik.swt.se2.mediathek.materialien.medien.Medium;

public class VormerkkarteTest {
	private Vormerkkarte _karte;
	private Kunde _kunde;
	private Kunde _kunde1;
	private Kunde _kunde2;
	private Kunde _kunde3;
	private Medium _medium;

	public VormerkkarteTest() {
		_kunde = new Kunde(new Kundennummer(123456), "ich", "du");
		_kunde1 = new Kunde(new Kundennummer(123457), "ic", "d");
		_kunde2 = new Kunde(new Kundennummer(123458), "ih", "u");
		_kunde3 = new Kunde(new Kundennummer(123459), "ch", "d");

		_medium = new CD("aa", "bb", "cc", 123);
		_karte = new Vormerkkarte(_medium, _kunde);
	}

	@Test
	public void testegetFormatiertenString() throws Exception {
		assertNotNull(_karte.getFormatiertenString());
	}

	@Test
	public void testeKonstruktor() throws Exception {
		assertEquals(_kunde, _karte.getErstenVormerker());
		assertEquals(_medium, _karte.getMedium());
	}

	@Test
	public void testgetErstenVormerker() {
		Vormerkkarte karte1 = new Vormerkkarte(_medium, _kunde);
		assertEquals(_kunde, karte1.getErstenVormerker());
	}

	@Test
	public void testfuegeKundenHinzu() {
		Vormerkkarte karte1 = new Vormerkkarte(_medium, _kunde);
		karte1.fuegeKundenHinzu(_kunde1);
		assert (karte1.getAlleVormerker().contains(_kunde1));
	}

	@Test
	public void testistVormerkenMoeglich() {
		Vormerkkarte karte1 = new Vormerkkarte(_medium, _kunde1);
		assertTrue(karte1.istVormerkenMoeglich(_kunde));
		karte1.fuegeKundenHinzu(_kunde2);
		karte1.fuegeKundenHinzu(_kunde3);
		assertFalse(karte1.istVormerkenMoeglich(_kunde));
	}

	@Test
    public void testgetMedium()
    {
      Vormerkkarte karte1 = new Vormerkkarte(_medium, _kunde);
      assertEquals(_medium, karte1.getMedium());  
    }
}
