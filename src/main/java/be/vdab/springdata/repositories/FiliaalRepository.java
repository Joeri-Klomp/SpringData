package be.vdab.springdata.repositories;

import be.vdab.springdata.domain.Filiaal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

//We erven over van JpaRepository, we geven het type van de entity mee (Filiaal), en het type van de variabele die hoort
//bij de primary key (Long)
//De interface erft handige methods, we gebruiken ze in de RepositoryTest. Enkele voorbeelden:
//count(), findById(), findAll(), findAll(sort), findAllById(List of Set), save(entity) die toevoegt of wijzigt als
//entity al bestaat, deleteById(id)
public interface FiliaalRepository extends JpaRepository<Filiaal, Long> {
    List<Filiaal> findByGemeente(String gemeente);
    //List als de query meerdere objecten teruggeeft, anders Optional<Filiaal>
    //Oproep begint altijd met findBy, gevolgd door het attribuut dat je in de where-clause wil
    //De parameter vermeld de gemeente waarvan je de filialen wil (wat je invult in de where-clause)
    //findByGemeente("Brussel") wordt geimplementeerd door Spring Data. Die stuurt volgende query naar de database:
    //select f from Filiaal f where f.gemeente = ?

    //Je kunt ook order by gebruiker:
    List<Filiaal> findByGemeenteOrderByNaam(String gemeente);

    List<Filiaal> findByOmzetGreaterThanEqual(BigDecimal vanaf);
    //Omzet is het attribuut van Filiaal die we in de where-clause willen
    //We willen de filialen waarvan de omzet groter of gelijk is aan een bepaalde waarde "vanaf"

    //Andere voorbeelden:
//    • findByOmzetIsNull → Filialen waarvan de omzet niet gekend is.
//    • findByOmzetBetween(BigDecimal van, BigDecimal tot) → Filialen waarvan de omzet ligt tussen van en tot.
//    • findByNaamStartingWith(String woord) → Filialen waarvan de naam begint met de letters in woord.
//    • findByNaamContaining(String woord) → Filialen waarvan de naam de letters in woord bevat.
//    • findByNaamIn(Set<String> namen) → Filialen waarvan de naam één van de namen in de verzameling namen is.
//    • findByNaamAndGemeente(String naam, String gemeente) → Filialen waarvan de naam gelijk is aan naam én de gemeente gelijk is aan gemeente.

    int countByGemeente(String gemeente);

    @Query("select avg(f.omzet) from Filiaal f")
    BigDecimal findGemiddeldeOmzet();
    List<Filiaal> findMetHoogsteOmzet();
    //Je geeft de method dezelfde naam als het einde van de naam van de named query. Spring Data maakt een implementatie die de named query oproept.
}
