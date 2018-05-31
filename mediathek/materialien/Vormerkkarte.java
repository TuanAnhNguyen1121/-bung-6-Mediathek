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
     *@param die erste Vormerker . 
     * @require medium != null
     * @ensure #getMedium() == medium
     */
    public Vormerkkarte(Medium medium, Kunde kunde)
    {
        _medium = medium;
        _vormerker = new ArrayList<Kunde>(3);
        _vormerker.add(kunde);

    }
    /**
     * Gibt das  Vormerker zurück.
     *@param zwischen 1-3 zururck
     * @return Die Kunde, die erste in die index der Liste ist
     * @ensure result != null
     */
    public Kunde getVormerker(int i)
	    
    {	assert i != null: "Vorbedingung verleztz != null";
	assert i <= 3 : "Vorbedingung verletzt: index <= 3";
	assert i >=1 : "Vorbedingung verletzt: index <= 3";
     
        return _vormerker.get(i-1);
    }
    /**
     * Überprüfen , ob die Anzahl der Vormerkern über 3 liegt
     * und wenn die Kunde bereits in der Liste steht.
     * @return Boolean
     * @ensure result != null
     */
    public boolean istVormerkenMöglich(Kunde kunde)
    {assert kunde != null : "Vorbedingung verletzt: kunde != null";
     
        if ((_vormerker.size()<3)&&(!_vormerker.contains(kunde))
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
     * fuge die neue Vormerker/kunde ein .
     *@require kunde != null
     */
    public void neueVormerken(Kunde kunde)
    {  assert kunde != null : "Vorbedingung verletzt: kunde != null";

		_vormerker.add(kunde);
    }
    /**
     * fuge die neue Vormerker/kunde ein .
     *@require _vormerker.contains(kunde) ; kunde ist innerhalb der vormerkarte
    */                                
    public void entfernenVormerken(Kunde kunde)
    { assert kunde != null : "Vorbedingung verletzt: kunde != null";
      assert _vormerker.contains(kunde) : "Vorbedingung verletzt: _vormerker.contains(kunde)";

       _vormerker.remove(kunde);

    }   
   /**
     * @return Eine formatierte Stringrepäsentation der Vormerkkarte. Enthält
     *         Zeilenumbrüche.
     * @ensure result != null
     */
    public String getFormatiertenString()
    {
        return _medium.getMedium() + "vorgemerkt von" + _vormerker.getAlleVormerker();
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
