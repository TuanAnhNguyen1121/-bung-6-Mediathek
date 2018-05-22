package de.uni_hamburg.informatik.swt.se2.mediathek.materialien;

import java.util.List;

import de.uni_hamburg.informatik.swt.se2.mediathek.materialien.medien.Medium;
/**
 * Vormerkarte klasse hat gegebene jobs  zugeordnet:
 * Medium , die von mehrere Kunden angefordet , ist hier gespeichert mit liste von deren Kunden
 * 
 */
public class Vormerkkarte
{
     // Eigenschaften einer Vormerkkarten
    private List<Kunde> _vormerker;
    private final Medium _medium;
/**
 * Initialisiert die Vormerkkarte mit gegebene Medium
 *@param medium, die vorgemerkt soll. 
 *@require medium =! null
 *@ensure getMedium() == medium 
 *
 */
    public Vormerkkarte(Medium medium)
    {
        _medium = medium;

    }
/**
 * Gibt den ersten Vormerker züruck
 *@return
 *@ensure
 */
    public Kunde getErstenVormerker()
    {
        return _vormerker.get(0);
    }
/**
 * Antwortet ob gegebenen Medium beim Vormerkung möglich 
 * Anzahl von Vormerkern bei gegebenen Medium ist : 3
 * @ensure 
 * @
 */
    public boolean istVormerkenMöglich()
    { int vormerkerAnzahl = _vormerker.size();
     if( vormerkerAnzahl < 3)
        {
         return true;
        }
        else 
        return false;
    }
/**
 * gibt liste von kunden, die gegebenen medium vorgemerkt haben
 *
 */
    public List<Kunde> getAlleVormerker()
    {
        return _vormerker;

    }
/**
 * merke eine Kunden in die List von Vormerkarte einer Medium. 
 * TODO : Kunde kann vorgemerkt wenn es unter 3 Vormerker.
 *
 */
    public void merkeVor(Kunde kunde)
    { if (istVormerkenMöglich() = true)
        {
        _vormerker.add(kunde);
        }
        else
        return false;
    }
/**
 *
 *
 */
    public String getFormatiertenString()
    {
        return "die Medium"+ " "+ getMedium() + "wird bei diesen Kunder vorgemerkt"+ " "+ getAlleVormerker() ;
    }
/**
 * gibt den gegebene Medium zuruck  :
 *
 */
    public Medium getMedium()
    {
        return _medium;
    }

}
