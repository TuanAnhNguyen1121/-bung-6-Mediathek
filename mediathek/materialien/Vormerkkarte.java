package de.uni_hamburg.informatik.swt.se2.mediathek.materialien;

import java.util.List;

import de.uni_hamburg.informatik.swt.se2.mediathek.materialien.medien.Medium;

public class Vormerkkarte
{
    private List<Kunde> _vormerker;
    private final Medium _medium;

    public Vormerkkarte(Medium medium)
    {
        _medium = medium;
        _vormerker = new ArrayList<Kunde>(3);
    }

    public Kunde getErstenVormerker()
    {
        return _vormerker.get(0);
    }

    public boolean istVormerkenMöglich()
    {
        return false;
    }

    public List<Kunde> getAlleVormerker()
    {
        return _vormerker;

    }

    public void merkeVor(Kunde kunde)
    {

    }

    public String getFormatiertenString()
    {
        return _medium.getFormatiertenString() + "vorgemerkt von" + _vormerker.toString();
    }

    public Medium getMedium()
    {
        return _medium;
    }

}
