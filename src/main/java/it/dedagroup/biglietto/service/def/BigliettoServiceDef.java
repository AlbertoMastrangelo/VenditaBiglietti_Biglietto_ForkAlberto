package it.dedagroup.biglietto.service.def;

import it.dedagroup.biglietto.model.Biglietto;

import java.time.LocalDate;
import java.util.List;

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


    public Biglietto findByIdAndCancellatoFalse(long id_biglietto);
    public List<Biglietto> findAllByCancellatoFalse();
    public Biglietto findByIdAndIdUtenteAndCancellatoFalse(long id_biglietto, long id_utente);
    public Biglietto findBySerialeAndCancellatoFalse(String seriale);
    public List<Biglietto> findAllByPrezzoIsGreaterThanEqualAndCancellatoFalse(double prezzo);
    public List<Biglietto> findAllByPrezzoIsLessThanEqualAndCancellatoFalse(double prezzo);
    public List<Biglietto> findAllByIdUtenteAndCancellatoFalse(long id_utente);
    public List<Biglietto> findAllByDataAcquistoAndCancellatoFalse(LocalDate dataAcquisto);



}
