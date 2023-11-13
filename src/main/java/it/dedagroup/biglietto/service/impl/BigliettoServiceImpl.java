package it.dedagroup.biglietto.service.impl;

import it.dedagroup.biglietto.model.Biglietto;
import it.dedagroup.biglietto.repository.BigliettoRepository;
import it.dedagroup.biglietto.service.def.BigliettoServiceDef;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
@Service
@AllArgsConstructor
public class BigliettoServiceImpl implements BigliettoServiceDef {
    private final BigliettoRepository repo;
    @Transactional(rollbackOn = ResponseStatusException.class)
    @Override
    public Biglietto saveBiglietto(Biglietto biglietto) {
        return repo.save(biglietto);
    }
    @Transactional(rollbackOn = ResponseStatusException.class)
    @Override
    public Biglietto modifyBiglietto(Biglietto biglietto) {
        Biglietto b = findById(biglietto.getId());
        b.setDataAcquisto(biglietto.getDataAcquisto());
        b.setPrezzo(biglietto.getPrezzo());
        b.setSeriale(biglietto.getSeriale());
        b.setCancellato(biglietto.isCancellato());
        b.setVersion(biglietto.getVersion());
        return repo.save(b);
    }
    @Transactional(rollbackOn = ResponseStatusException.class)
    @Override
    public void deleteByBiglietto(long id_biglietto) {
        Biglietto b = findById(id_biglietto);
        b.setCancellato(true);
        repo.save(b);
    }

    @Override
    public Biglietto findById(long id_biglietto) {
        return repo.findById(id_biglietto).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Errore nel trovare il biglietto tramite id: "+id_biglietto));
    }

    @Override
    public List<Biglietto> findAll() {
        return repo.findAll();
    }

    @Override
    public Biglietto findByIdAndIdUtente(long id_biglietto, long id_utente) {
        return repo.findByIdAndIdUtente(id_biglietto,id_utente).orElseThrow(() ->
                new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Errore nel trovare il biglietto con id: "+id_biglietto+" e id utente: "+id_utente)
        );
    }

    @Override
    public Biglietto findBySeriale(String seriale) {
        return repo.findBySeriale(seriale).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Errore nel trovare il biglietto con seriale: "+seriale));
    }

    @Override
    public List<Biglietto> findAllByPrezzoIsGreaterThanEqual(double prezzo) {
        return repo.findAllByPrezzoIsGreaterThanEqual(prezzo);
    }

    @Override
    public List<Biglietto> findAllByPrezzoIsLessThanEqual(double prezzo) {
        return repo.findAllByPrezzoIsLessThanEqual(prezzo);
    }

    @Override
    public List<Biglietto> findAllByIdUtente(long id_utente) {
        return repo.findAllByIdUtente(id_utente);
    }

    @Override
    public List<Biglietto> findAllByDataAcquisto(LocalDate dataAcquisto) {
        return repo.findAllByDataAcquisto(dataAcquisto);
    }

    @Override
    public Biglietto findByIdAndCancellatoFalse(long id_biglietto) {
        return repo.findByIdAndCancellatoFalse(id_biglietto).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Errore nel trovare il biglietto tramite id: "+id_biglietto));
    }

    @Override
    public List<Biglietto> findAllByCancellatoFalse() {
        return repo.findAllByCancellatoFalse();
    }

    @Override
    public Biglietto findByIdAndIdUtenteAndCancellatoFalse(long id_biglietto, long id_utente) {
        return repo.findByIdAndIdUtenteAndCancellatoFalse(id_biglietto,id_utente).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Errore nel trovare il biglietto con id: "+id_biglietto+" e id utente: "+id_utente));
    }

    @Override
    public Biglietto findBySerialeAndCancellatoFalse(String seriale) {
        return repo.findBySerialeAndCancellatoFalse(seriale).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Errore nel trovare il biglietto con seriale: "+seriale));
    }

    @Override
    public List<Biglietto> findAllByPrezzoIsGreaterThanEqualAndCancellatoFalse(double prezzo) {
        return repo.findAllByPrezzoIsGreaterThanEqualAndCancellatoFalse(prezzo);
    }

    @Override
    public List<Biglietto> findAllByPrezzoIsLessThanEqualAndCancellatoFalse(double prezzo) {
        return repo.findAllByPrezzoIsLessThanEqualAndCancellatoFalse(prezzo);
    }

    @Override
    public List<Biglietto> findAllByIdUtenteAndCancellatoFalse(long id_utente) {
        return repo.findAllByIdUtenteAndCancellatoFalse(id_utente);
    }

    @Override
    public List<Biglietto> findAllByDataAcquistoAndCancellatoFalse(LocalDate dataAcquisto) {
        return repo.findAllByDataAcquistoAndCancellatoFalse(dataAcquisto);
    }
}
