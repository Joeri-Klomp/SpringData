package be.vdab.springdata.domain;

import javax.persistence.*;

@Entity
@Table(name = "werknemers")
//@NamesEntityGraph om het n+1 probleem te voorkomen, dat kan opduiken bij findByVoornaamStartingWith() in WerknemerRepository
@NamedEntityGraph(name = "Werknemer.metFiliaal", attributeNodes = @NamedAttributeNode("filiaal"))
public class Werknemer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String voornaam;
    private String familienaam;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "filiaalId")
    private Filiaal filiaal;

    public Werknemer(String voornaam, String familienaam, Filiaal filiaal) {
        this.voornaam = voornaam;
        this.familienaam = familienaam;
        this.filiaal = filiaal;
    }

    protected Werknemer() {}

    public long getId() {
        return id;
    }

    public String getVoornaam() {
        return voornaam;
    }

    public String getFamilienaam() {
        return familienaam;
    }

    public Filiaal getFiliaal() {
        return filiaal;
    }
}
