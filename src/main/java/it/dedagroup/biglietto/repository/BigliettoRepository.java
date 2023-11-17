package it.dedagroup.biglietto.repository;

import it.dedagroup.biglietto.model.Biglietto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BigliettoRepository extends JpaRepository<Biglietto,Long> {
    public Optional<Biglietto> findByIdAndIdUtente(long id_biglietto, long id_utente);
    public Optional<Biglietto> findBySeriale(String seriale);
    public List<Biglietto> findAllByPrezzoIsGreaterThanEqual(double prezzo);
    public List<Biglietto> findAllByPrezzoIsLessThanEqual(double prezzo);
    public List<Biglietto> findAllByIdUtente(long id_utente);
    public List<Biglietto> findAllByDataAcquisto(LocalDate dataAcquisto);
    public Optional<Biglietto> findByIdAndIdPrezzoSettoreEvento(long id_biglietto, long id_prezzoSettoreEvento);
    public List<Biglietto> findAllByIdPrezzoSettoreEvento(long id_prezzoSettoreEvento);

    public int countByIdPrezzoSettoreEventoAndDataAcquistoIsNotNull(long id_prezzoSettoreEvento);
    //TODO discutere del disdire il biglietto
    public int countByIdPrezzoSettoreEventoAndDataAcquistoIsNull(long id_prezzoSettoreEvento);

    public Optional<Biglietto> findByIdAndIsCancellatoFalse(long id_biglietto);
    public List<Biglietto> findAllByIsCancellatoFalse();
    public Optional<Biglietto> findByIdAndIdUtenteAndIsCancellatoFalse(long id_biglietto, long id_utente);
    public Optional<Biglietto> findBySerialeAndIsCancellatoFalse(String seriale);
    public List<Biglietto> findAllByPrezzoIsGreaterThanEqualAndIsCancellatoFalse(double prezzo);
    public List<Biglietto> findAllByPrezzoIsLessThanEqualAndIsCancellatoFalse(double prezzo);
    public List<Biglietto> findAllByIdUtenteAndIsCancellatoFalse(long id_utente);
    public List<Biglietto> findAllByDataAcquistoAndIsCancellatoFalse(LocalDate dataAcquisto);

}
