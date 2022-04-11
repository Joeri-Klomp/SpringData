insert into werknemers(voornaam, familienaam, filiaalid)
values ('Joe', 'Dalton', (select id from filialen where naam = 'Alfa')),
       ('Jack', 'Dalton', (select id from filialen where naam = 'Bravo')),
       ('Lucky', 'Luke', (select id from filialen where naam = 'Charly'));