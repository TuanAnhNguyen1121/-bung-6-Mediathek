package de.uni_hamburg.informatik.swt.se2.mediathek.materialien;

import java.util.List;

import de.uni_hamburg.informatik.swt.se2.mediathek.materialien.medien.Medium;
/**
 * Mit Hilfe von Vormerkkarte werden beim Verleih eines Mediums alle relevanten
 * Daten notiert.
 * 
 * Sie beantwortet die folgenden Fragen: Welches Medium wurde reserviert? Wer
 * hat das Medium reserviert? Wie viele Kunden werden gelistet? 
 * 
 *  ähnlich wie Verleihkarte, Um die Verwaltung der Karten kümmert sich der VerleihService
 * 
 * @author SE2-Team
 * @version SoSe 2017
 */
public class Vormerkkarte
{
    private List<Kunde> _vormerker;
    private final Medium _medium;
  /**
     * Initialisert eine neue Vormerkkarte  mit den gegebenen Daten.
     * @param medium Ein verliehene Medium.
     * @require medium != null
     * @ensure #getMedium() == medium
     */
    public Vormerkkarte(Medium medium)
    {
        _medium = medium;
        _vormerker = new ArrayList<Kunde>(3);
    }
    /**
     * Gibt das erste Vormerker zurück.
     * @return Die Kunde, die erste in die Liste ist
     * @ensure result != null
     */
    public Kunde getErstenVormerker()
    {
        return _vormerker.get(0);
    }
    /**
     * Überprüfen , ob die Anzahl der Vormerkern über 3 liegt
     * und wenn die Kunde bereits in der Liste steht.
     * @return Boolean
     * @ensure result != null
     */
    public boolean istVormerkenMöglich(Kunde kunde)
    {
        if ((_vormerker.size()<3)&&(!istVerliehenAn(kunde, _medium)
                                    {
                                        return true;
                                    }
        else 
                                    {
                                        return false;
                                    }
                                    
    }
   /**
     * Überprüfen , ob die Anzahl der Vormerkern über 3 liegt
     * und wenn die Kunde bereits in der Liste steht.
     * @return Boolean
     * @ensure result != null
     */
    public List<Kunde> getAlleVormerker()
    {
        return _vormerker;

    }
   /**
     * Überprüfen , ob die Anzahl der Vormerkern über 3 liegt
     * und wenn die Kunde bereits in der Liste steht.
     * fuge die neue Vormerker/kunde ein .
     */
    public void neueVormerker(Kunde kunde)
    {   if 

    }
   /**
     * @return Eine formatierte Stringrepäsentation der Vormerkkarte. Enthält
     *         Zeilenumbrüche.
     * @ensure result != null
     */
    public String getFormatiertenString()
    {
        return _medium.getFormatiertenString() + "vorgemerkt von" + _vormerker.toString();
    }
   /**
     *@return Medium 
     *@ensure result != null
     */
    public Medium getMedium()
    {
        return _medium;
    }

}
