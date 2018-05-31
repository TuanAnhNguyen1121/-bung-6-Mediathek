package de.uni_hamburg.informatik.swt.se2.mediathek.materialien;

import java.util.*;

import de.uni_hamburg.informatik.swt.se2.mediathek.materialien.medien.Medium;

/**
 * Mit Hilfe von Vormerkkarte werden beim Verleih eines Mediums alle relevanten
 * Daten notiert.
 * 
 * Sie beantwortet die folgenden Fragen: Welches Medium wurde reserviert? Wer
 * hat das Medium reserviert? Wie viele Kunden werden gelistet?
 * 
 * ähnlich wie Verleihkarte, Um die Verwaltung der Karten kümmert sich der
 * VerleihService
 * 
 * @author SE2-Team
 * @version SoSe 2017
 */
public class Vormerkkarte {
	private List<Kunde> _vormerker;
	private final int maximalevormerker = 3;
	private final Medium _medium;

	/**
	 * Initialisiert eine neue Vormerkkarte mit den gegebenen Daten.
	 * 
	 * @param medium
	 *            Ein verliehenes Medium.
	 * @param kunde
	 *            der erste Vormerker .
	 * @require medium != null
	 * @require kunde != null
	 */
	public Vormerkkarte(Medium medium, Kunde kunde) {
		assert kunde != null : "Vorbedingung verletzt: kunde != null";
		assert medium != null : "Vorbedingung verletzt: medium != null";
		_medium = medium;
		_vormerker = new ArrayList<Kunde>(maximalevormerker);
		_vormerker.add(kunde);

	}

	/**
	 * Gibt den ersten Vormerker zurück.
	 * 
	 */
	public Kunde getErstenVormerker() {
		return _vormerker.get(0);
	}

	/**
	 * Überprüft , ob der Kunde bereits ein Vormerker ist.
	 * 
	 * @param kunde
	 *            Kunde, für den geprüft werden soll, ob er das Medium bereits
	 *            vorgemerkt hat
	 * @require kunde != null
	 */
	private boolean istVorgemerktAn(Kunde kunde) {
		assert kunde != null : "Vorbedingung verletzt: kunde != null";
		for (int i = 0; i < _vormerker.size(); i++) {
			if (_vormerker.get(i).equals(kunde)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Überprüft, ob der Kunde das Medium vormerken kann
	 * @param kunde
	 *            Kunde, für den geprüft werden soll, ob er das Medium vormerken kann
	 * @return
	 */
	public boolean istVormerkenMoeglich(Kunde kunde) {
		assert kunde != null : "Vorbedingung verletzt: kunde != null";
		return (_vormerker.size() < 3 && !istVorgemerktAn(kunde));
	}

	/**
	 * Gibt eine Liste mit allen Vormerkern zurück
	 * @ensure result != null
	 */
	public List<Kunde> getAlleVormerker() {
		return _vormerker;

	}

	/**
	 * Fügt den neuen Vormerker ein .
	 * 
	 * @require kunde != null
	 * @require istVormerkenMoeglich(kunde)
	 */
	public void fuegeKundenHinzu(Kunde kunde) {
		assert kunde != null : "Vorbedingung verletzt: kunde != null";
		assert istVormerkenMoeglich(kunde) : "Vorbedingung verletzt: Medium nicht vormerkbar";
		_vormerker.add(kunde);
	}

	/**
	 * Entfernt den Vormerker.
	 * 
	 * @require _vormerker.contains(kunde) ; kunde ist innerhalb der vormerkarte
	 */
	public void entferneVormerker(Kunde kunde) {
		assert _vormerker.contains(kunde) : "Vorbedingung verletzt: _vormerker.contains(kunde)";

		_vormerker.remove(kunde);

	}

	/**
	 * @return Eine formatierte Stringrepäsentation der Vormerkkarte. Enthält
	 *         Zeilenumbrüche.
	 * @ensure result != null
	 */
	public String getFormatiertenString() {
		return _medium.getFormatiertenString() + "vorgemerkt von" + _vormerker.toString();
	}

	/**
	 * @return Medium
	 * @ensure result != null
	 */
	public Medium getMedium() {
		return _medium;
	}

}
