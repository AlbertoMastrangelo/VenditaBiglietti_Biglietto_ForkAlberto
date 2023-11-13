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

    public Optional<Biglietto> findByIdAndCancellatoFalse(long id_biglietto);
    public List<Biglietto> findAllByCancellatoFalse();
    public Optional<Biglietto> findByIdAndIdUtenteAndCancellatoFalse(long id_biglietto, long id_utente);
    public Optional<Biglietto> findBySerialeAndCancellatoFalse(String seriale);
    public List<Biglietto> findAllByPrezzoIsGreaterThanEqualAndCancellatoFalse(double prezzo);
    public List<Biglietto> findAllByPrezzoIsLessThanEqualAndCancellatoFalse(double prezzo);
    public List<Biglietto> findAllByIdUtenteAndCancellatoFalse(long id_utente);
    public List<Biglietto> findAllByDataAcquistoAndCancellatoFalse(LocalDate dataAcquisto);

}
