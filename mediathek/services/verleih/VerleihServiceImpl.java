package de.uni_hamburg.informatik.swt.se2.mediathek.services.verleih;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.uni_hamburg.informatik.swt.se2.mediathek.fachwerte.Datum;
import de.uni_hamburg.informatik.swt.se2.mediathek.materialien.Kunde;
import de.uni_hamburg.informatik.swt.se2.mediathek.materialien.Verleihkarte;
import de.uni_hamburg.informatik.swt.se2.mediathek.materialien.Vormerkkarte;
import de.uni_hamburg.informatik.swt.se2.mediathek.materialien.medien.Medium;
import de.uni_hamburg.informatik.swt.se2.mediathek.services.AbstractObservableService;
import de.uni_hamburg.informatik.swt.se2.mediathek.services.kundenstamm.KundenstammService;
import de.uni_hamburg.informatik.swt.se2.mediathek.services.medienbestand.MedienbestandService;

/**
 * Diese Klasse implementiert das Interface VerleihService. Siehe dortiger
 * Kommentar.
 * 
 * @author SE2-Team
 * @version SoSe 2017
 */
public class VerleihServiceImpl extends AbstractObservableService implements VerleihService {
	/**
	 * Diese Map speichert für jedes eingefügte Medium die dazugehörige
	 * Verleihkarte. Ein Zugriff auf die Verleihkarte ist dadurch leicht über die
	 * Angabe des Mediums möglich. Beispiel: _verleihkarten.get(medium)
	 */
	private Map<Medium, Verleihkarte> _verleihkarten;

	/**
	 * Übung 6 Diese Map speichert für jedes vorgemerkte Medium die dazugehörige
	 * Vormerkkarte. Ein Zugriff auf die Vormerkkarte ist dadurch leicht über die
	 * Angabe des Mediums möglich. Beispiel: _vormerkkarten.get(medium)
	 */
	private Map<Medium, Vormerkkarte> _vormerkkarten;

	/**
	 * Der Medienbestand.
	 */
	private MedienbestandService _medienbestand;

	/**
	 * Der Kundenstamm.
	 */
	private KundenstammService _kundenstamm;

	/**
	 * Der Protokollierer für die Verleihvorgänge.
	 */
	private VerleihProtokollierer _protokollierer;

	/**
	 * Konstruktor. Erzeugt einen neuen VerleihServiceImpl.
	 * 
	 * @param kundenstamm
	 *            Der KundenstammService.
	 * @param medienbestand
	 *            Der MedienbestandService.
	 * @param initialBestand
	 *            Der initiale Bestand.
	 * 
	 * @require kundenstamm != null
	 * @require medienbestand != null
	 * @require initialBestand != null
	 */
	public VerleihServiceImpl(KundenstammService kundenstamm, MedienbestandService medienbestand,
			List<Verleihkarte> initialBestand) {
		assert kundenstamm != null : "Vorbedingung verletzt: kundenstamm  != null";
		assert medienbestand != null : "Vorbedingung verletzt: medienbestand  != null";
		assert initialBestand != null : "Vorbedingung verletzt: initialBestand  != null";
		_verleihkarten = erzeugeVerleihkartenBestand(initialBestand);
		_kundenstamm = kundenstamm;
		_medienbestand = medienbestand;
		_protokollierer = new VerleihProtokollierer();

		// _vormerkkarten als HashMap definieren
		_vormerkkarten = new HashMap<Medium, Vormerkkarte>();
	}

	/**
	 * Erzeugt eine neue HashMap aus dem Initialbestand.
	 */
	private HashMap<Medium, Verleihkarte> erzeugeVerleihkartenBestand(List<Verleihkarte> initialBestand) {
		HashMap<Medium, Verleihkarte> result = new HashMap<Medium, Verleihkarte>();
		for (Verleihkarte verleihkarte : initialBestand) {
			result.put(verleihkarte.getMedium(), verleihkarte);
		}
		return result;
	}

	@Override
	public List<Verleihkarte> getVerleihkarten() {
		return new ArrayList<Verleihkarte>(_verleihkarten.values());
	}

	@Override
	public boolean istVerliehen(Medium medium) {
		assert mediumImBestand(medium) : "Vorbedingung verletzt: mediumExistiert(medium)";
		return _verleihkarten.get(medium) != null;
	}

	@Override
	public boolean istVerleihenMoeglich(Kunde kunde, List<Medium> medien) {
		assert kundeImBestand(kunde) : "Vorbedingung verletzt: kundeImBestand(kunde)";
		assert medienImBestand(medien) : "Vorbedingung verletzt: medienImBestand(medien)";

		boolean entleiherIstVormerker = true;

		for (Medium m : medien) {
			if (_vormerkkarten.containsKey(m)) {
				entleiherIstVormerker = _vormerkkarten.get(m).getErstenVormerker().equals(kunde);
			}
		}
		return sindAlleNichtVerliehen(medien) && entleiherIstVormerker;
	}

	@Override
	public void nimmZurueck(List<Medium> medien, Datum rueckgabeDatum) throws ProtokollierException {
		assert sindAlleVerliehen(medien) : "Vorbedingung verletzt: sindAlleVerliehen(medien)";
		assert rueckgabeDatum != null : "Vorbedingung verletzt: rueckgabeDatum != null";

		for (Medium medium : medien) {
			Verleihkarte verleihkarte = _verleihkarten.get(medium);
			_verleihkarten.remove(medium);
			_protokollierer.protokolliere(VerleihProtokollierer.EREIGNIS_RUECKGABE, verleihkarte);
		}

		informiereUeberAenderung();
	}

	@Override
	public boolean sindAlleNichtVerliehen(List<Medium> medien) {
		assert medienImBestand(medien) : "Vorbedingung verletzt: medienImBestand(medien)";
		boolean result = true;
		for (Medium medium : medien) {
			if (istVerliehen(medium)) {
				result = false;
			}
		}
		return result;
	}

	@Override
	public boolean sindAlleVerliehenAn(Kunde kunde, List<Medium> medien) {
		assert kundeImBestand(kunde) : "Vorbedingung verletzt: kundeImBestand(kunde)";
		assert medienImBestand(medien) : "Vorbedingung verletzt: medienImBestand(medien)";

		boolean result = true;
		for (Medium medium : medien) {
			if (!istVerliehenAn(kunde, medium)) {
				result = false;
			}
		}
		return result;
	}
	
	// Übung 6: Vormerkung stornieren_Methode
	@Override
	public void storniereVormerkung(Medium medium, Kunde kunde) {
		assert mediumImBestand(medium) : "Vorbedingung verletzt: mediumImBestand(medium)";
		assert istVorgemerkt(medium) : "Vorbedingung verletzt: istVorgemerkt(medium)";
		assert kundeImBestand(kunde) : "Vorbedingung verletzt: kundeImBestand(kunde)";
		assert kunde != null : "Vorbedingung verletzt: kunde != null";
		assert medium != null : "Vorbedingung verletzt: medium != null";

		_vormerkkarten.get(medium).entferneVormerker(kunde);
		if (_vormerkkarten.get(medium).getAlleVormerker().size() == 0) {
			_vormerkkarten.remove(medium);
		}

		informiereUeberAenderung();
	}F

	@Override
	public boolean istVerliehenAn(Kunde kunde, Medium medium) {
		assert kundeImBestand(kunde) : "Vorbedingung verletzt: kundeImBestand(kunde)";
		assert mediumImBestand(medium) : "Vorbedingung verletzt: mediumImBestand(medium)";

		return istVerliehen(medium) && getEntleiherFuer(medium).equals(kunde);
	}

	@Override
	public boolean sindAlleVerliehen(List<Medium> medien) {
		assert medienImBestand(medien) : "Vorbedingung verletzt: medienImBestand(medien)";

		boolean result = true;
		for (Medium medium : medien) {
			if (!istVerliehen(medium)) {
				result = false;
			}
		}
		return result;
	}

	@Override
	public void verleiheAn(Kunde kunde, List<Medium> medien, Datum ausleihDatum) throws ProtokollierException {
		assert kundeImBestand(kunde) : "Vorbedingung verletzt: kundeImBestand(kunde)";
		assert sindAlleNichtVerliehen(medien) : "Vorbedingung verletzt: sindAlleNichtVerliehen(medien) ";
		assert ausleihDatum != null : "Vorbedingung verletzt: ausleihDatum != null";
		assert istVerleihenMoeglich(kunde, medien) : "Vorbedingung verletzt:  istVerleihenMoeglich(kunde, medien)";

		for (Medium medium : medien) {
			Verleihkarte verleihkarte = new Verleihkarte(kunde, medium, ausleihDatum);

			_verleihkarten.put(medium, verleihkarte);
			_protokollierer.protokolliere(VerleihProtokollierer.EREIGNIS_AUSLEIHE, verleihkarte);
		}
		// Was passiert wenn das Protokollieren mitten in der Schleife
		// schief geht? informiereUeberAenderung in einen finally Block?
		informiereUeberAenderung();
	}

	@Override
	public boolean kundeImBestand(Kunde kunde) {
		return _kundenstamm.enthaeltKunden(kunde);
	}

	@Override
	public boolean mediumImBestand(Medium medium) {
		return _medienbestand.enthaeltMedium(medium);
	}

	@Override
	public boolean medienImBestand(List<Medium> medien) {
		assert medien != null : "Vorbedingung verletzt: medien != null";
		assert !medien.isEmpty() : "Vorbedingung verletzt: !medien.isEmpty()";

		boolean result = true;
		for (Medium medium : medien) {
			if (!mediumImBestand(medium)) {
				result = false;
				break;
			}
		}
		return result;
	}

	@Override
	public List<Medium> getAusgelieheneMedienFuer(Kunde kunde) {
		assert kundeImBestand(kunde) : "Vorbedingung verletzt: kundeImBestand(kunde)";
		List<Medium> result = new ArrayList<Medium>();
		for (Verleihkarte verleihkarte : _verleihkarten.values()) {
			if (verleihkarte.getEntleiher().equals(kunde)) {
				result.add(verleihkarte.getMedium());
			}
		}
		return result;
	}

	@Override
	public Kunde getEntleiherFuer(Medium medium) {
		assert istVerliehen(medium) : "Vorbedingung verletzt: istVerliehen(medium)";
		Verleihkarte verleihkarte = _verleihkarten.get(medium);
		return verleihkarte.getEntleiher();
	}

	@Override
	public Verleihkarte getVerleihkarteFuer(Medium medium) {
		assert istVerliehen(medium) : "Vorbedingung verletzt: istVerliehen(medium)";
		return _verleihkarten.get(medium);
	}

	@Override
	public List<Verleihkarte> getVerleihkartenFuer(Kunde kunde) {
		assert kundeImBestand(kunde) : "Vorbedingung verletzt: kundeImBestand(kunde)";
		List<Verleihkarte> result = new ArrayList<Verleihkarte>();
		for (Verleihkarte verleihkarte : _verleihkarten.values()) {
			if (verleihkarte.getEntleiher().equals(kunde)) {
				result.add(verleihkarte);
			}
		}
		return result;
	}

	// Übung 6 Vormerken_Methoden
	@Override
	public void merkeVor(List<Medium> medien, Kunde kunde) {
		assert istVormerkenMoeglich(medien, kunde) : "Vorbedingung verletzt: istVormerkenMoeglich(medium, kunde)";
		assert kundeImBestand(kunde) : "Vorbedingung verletzt: kundeImBestand(kunde)";
		assert medienImBestand(medien) : "Vorbedingung verletzt: mediumImBestand(medium)";
		assert kunde != null : "Vorbedingung verletzt: kunde != null";
		assert medien != null : "Vorbedingung verletzt: medien != null";

		for (Medium medium : medien) {
			// Prüft,ob diese Map eine Zuordnung für den angegebenen Schlüssel (medium)
			// enthält.
			// Wenn ja, gibt den Wert (Verleihkarte) zurück, dem der angegebene Schlüssel
			// zugeordnet wurde. Dann wird die Methode neuVormerken für diese Verleihkarte
			// durchgeführt.
			if (_vormerkkarten.containsKey(medium)) {
				_vormerkkarten.get(medium).fuegeKundenHinzu(kunde);
			}
			// Wenn nicht, füge medium in eine neue Vormerkarte.
			else {
				_vormerkkarten.put(medium, new Vormerkkarte(medium, kunde));
			}
		}

		informiereUeberAenderung();
	}

	@Override
	public boolean istVorgemerkt(Medium medium) {
		assert mediumImBestand(medium) : "Vorbedingung verletzt: mediumImBestand(medium)";

		return _vormerkkarten.containsKey(medium);
	}

	// Übung 6 Vormerkung ist möglich, wenn jedes Medium kann von maximal 3
	// unterschiedlichen Kunden vorgemerkt werden.
	@Override
	public boolean istVormerkenMoeglich(Medium medium, Kunde kunde) {
		assert mediumImBestand(medium) : "Vorbedingung verletzt: mediumImBestand(medium)";
		assert kundeImBestand(kunde) : "Vorbedingung verletzt: kundeImBestand(kunde)";
		assert kunde != null : "Vorbedingung verletzt: kunde != null";
		assert medium != null : "Vorbedingung verletzt: medium != null";

		if (istVorgemerkt(medium)) {
			boolean entleiherNichtVormerker = true;

			if (istVerliehen(medium)) {
				entleiherNichtVormerker = !getEntleiherFuer(medium).equals(kunde);
			}

			return (_vormerkkarten.get(medium).getAlleVormerker().size() < 3) && entleiherNichtVormerker
					&& !_vormerkkarten.get(medium).getAlleVormerker().contains(kunde);
		} else {
			if (istVerliehen(medium)) {
				return !getEntleiherFuer(medium).equals(kunde);
			}

			return true;
		}
	}

	@Override
	public boolean istVormerkenMoeglich(List<Medium> medien, Kunde kunde) {
		assert medienImBestand(medien) : "Vorbedingung verletzt: mediumImBestand(medium)";
		assert kundeImBestand(kunde) : "Vorbedingung verletzt: kundeImBestand(kunde)";
		assert kunde != null : "Vorbedingung verletzt: kunde != null";
		assert medien != null : "Vorbedingung verletzt: medium != null";

		for (Medium m : medien) {
			if (!istVormerkenMoeglich(m, kunde)) {
				return false;
			}
		}

		return true;
	}

	// @Override
	// public List<Vormerkkarte> getVormerkkartenFuer(Kunde kunde)
	// {
	// List<Vormerkkarte> karten = new LinkedList<>();
	//
	// for (Vormerkkarte vk : _vormerkkarten.values())
	// {
	// if (vk.getAlleVormerker()
	// .contains(kunde))
	// {
	// karten.add(vk);
	// }
	// }
	//
	// return karten;
	// }

	// Übung 6 von 25.05.18 weiter unten
	@Override
	public Vormerkkarte getVormerkkarte(Medium medium) {
		assert medium != null : "Vorbedingung verletzt: medium != null";

		return _vormerkkarten.get(medium);

	}
	// @Override
	// public boolean istVorgemerkt(Medium medium)
	// {
	// assert medium != null : "Vorbedingung verletzt: medium != null"
	// //TODO
	// return ;
	//
	//
	// }
	// @Override
	// public boolean istVormerkenMoeglich(Medium medium, Kunde kunde)
	// {
	// assert kunde != null : "Vorbedingung verletzt: kunde != null";
	// assert medium != null : "Vorbedingung verletzt: medium != null";
	// assert medienImBestand(medium) : "Vorbedingung verletzt:
	// mediumImBestand(medium)";
	// assert kundeImBestand(kunde) : "Vorbedingung verletzt:
	// kundeImBestand(kunde)";
	//
	// if (getEntleiherFuer(medium).equals(kunde))
	//
	// {
	// return false;
	// }
	//
	// else
	//
	// if (_vormerkkarten.get(medium).getAlleVormerker().size() < 3
	// && !_vormerkkarten.get(medium).getAlleVormerker().contains(kunde))
	// {
	// return true;
	// }
	// else
	//
	// return false;
	// }
	//

	@Override
	public boolean sindAlleNichtVorgemerkt(List<Medium> medien) {
		for (Medium medium : medien) {
			if (istVorgemerkt(medium)) {
				return false;
			}
		}
		return true;
	}


	@Override
	public boolean sindAlleVorgemerkt(List<Medium> medien) {
		for (Medium medium : medien) {
			if (!istVorgemerkt(medium)) {
				return false;
			}
		}
		return true;
	}

	//
	@Override
	public List<Vormerkkarte> getVormerkkartenFuer(Kunde kunde) {
		assert kundeImBestand(kunde) : "Vorbedingung verletzt: kundeImBestand(kunde)";
		List<Vormerkkarte> kartelist = new ArrayList<Vormerkkarte>();

		for (Vormerkkarte vormerkkarte : _vormerkkarten.values()) {
			if (vormerkkarte.getAlleVormerker().equals(kunde)) {
				kartelist.add(vormerkkarte);
			}
		}
		return kartelist;
	}
}
