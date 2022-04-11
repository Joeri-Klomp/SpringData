package be.vdab.springdata.repositories;

import be.vdab.springdata.domain.Werknemer;
import be.vdab.springdata.projections.AantalWerknemersPerFamilienaam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WerknemerRepository extends JpaRepository<Werknemer, Long> {
    List<Werknemer> findByFiliaalGemeente(String gemeente);
    //Na findBy staat Filiaal: het attribuut filiaal van de entity Werknemer. Daarna staat Gemeente: het attribuut
    //gemeente van de entity Filiaal. Spring Data maakt een implementatie die de werknemers zoekt die behoren tot filialen
    //uit een bepaalde gemeente. Je geeft die gemeente mee in de parameter gemeente.

    @EntityGraph(value = "Werknemer.metFiliaal")    //comment deze hele line en vergelijk de console output om te zien
                                                    //wat het n+1 probleem is en hoe de EntityGraph er mee omgaat
    //Je verwijst naar de named entity graph Werknemer.metFiliaal (zie domain class Werknemer). Spring Data zal daarmee
    //rekening houden bij het uitvoeren van de method. Dit om het n+1 probleem te voorkomen. In plaats van per filiaal
    //een extra select statement door te sturen, stuurt JPA nu één SQL statement naar de database, met left outer join
    List<Werknemer> findByVoornaamStartingWith(String woord);

    //Als je de gebruiker veel data toont, is het gebruikelijk niet alle data in één keer te tonen. Voorbeeld: de
    //gebruiker wil alle werknemers zien. Je toont hem de eerste 20 werknemers. Je toont hem ook een knop Volgende. Als
    //hij daarop klikt ziet hij de volgende werknemers, etc. Het zou niet performant zijn alle werknemers uit de database
    //te lezen als je er slechts 20 toont. Bij MySQL kan je het keyword limit gebruiken: select … from werknemers limit 20.
    //Je erft in WerknemerRepository van JpaRepository een method die dit gebruikt:
    Page<Werknemer> findAll(Pageable pageable);
    //De parameter heeft als type Pageable. Je definieert daarin:
    //• het volgnummer van de pagina die je wil
    //• en hoeveel werknemers de pagina maximaal mag bevatten.
    //Het return type is Page<Werknemer>. Dit bevat
    //• de werknemers op de gevraagde pagina
    //• en extra informatie zoals: is dit de laatste pagina ?

    List<AantalWerknemersPerFamilienaam> findAantalWerknemersPerFamilienaam();
    //De method naam is gelijk aan de named query die je toevoegde aan orm.xml. Als je de method oproept, zal Spring
    //data dus die named query uitvoeren. Spring Data ziet dat het returntype een List met objecten is die de interface
    //AantalWerknemersPerFamilienaam implementeren. Spring Data maakt at runtime een class die deze interface
    //implementeert en vult de List met objecten van die class.
}
