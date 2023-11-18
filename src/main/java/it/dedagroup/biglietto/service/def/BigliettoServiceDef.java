package it.dedagroup.biglietto.service.def;

import it.dedagroup.biglietto.model.Biglietto;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BigliettoServiceDef {
    public Biglietto saveBiglietto(Biglietto biglietto);
    public Biglietto modifyBiglietto(Biglietto biglietto);
    public void deleteByBiglietto(long id_biglietto);

    public Biglietto findById(long id_biglietto);
    public List<Biglietto> findAll();
    public Biglietto findByIdAndIdUtente(long id_biglietto, long id_utente);
    public Biglietto findBySeriale(String seriale);
    public List<Biglietto> findAllByPrezzoIsGreaterThanEqual(double prezzo);
    public List<Biglietto> findAllByPrezzoIsLessThanEqual(double prezzo);
    public List<Biglietto> findAllByIdUtente(long id_utente);
    public List<Biglietto> findAllByDataAcquisto(LocalDate dataAcquisto);
    public Biglietto findByIdAndIdPrezzoSettoreEvento(long id_biglietto, long id_prezzoSettoreEvento);
    public List<Biglietto> findAllByIdPrezzoSettoreEventoOrderByPrezzoAsc(long id_prezzoSettoreEvento);

    public int countByIdPrezzoSettoreEventoAndDataAcquistoIsNotNull(long id_prezzoSettoreEvento);
    public List<Double> findDistinctPrezzoBigliettoByIdPrezzoSettoreEvento(long id_prezzoSettoreEvento);

    public Biglietto findByIdAndIsCancellatoFalse(long id_biglietto);
    public List<Biglietto> findAllByIsCancellatoFalse();
    public Biglietto findByIdAndIdUtenteAndIsCancellatoFalse(long id_biglietto, long id_utente);
    public Biglietto findBySerialeAndIsCancellatoFalse(String seriale);
    public List<Biglietto> findAllByPrezzoIsGreaterThanEqualAndIsCancellatoFalse(double prezzo);
    public List<Biglietto> findAllByPrezzoIsLessThanEqualAndIsCancellatoFalse(double prezzo);
    public List<Biglietto> findAllByIdUtenteAndIsCancellatoFalse(long id_utente);
    public List<Biglietto> findAllByDataAcquistoAndIsCancellatoFalse(LocalDate dataAcquisto);
}
