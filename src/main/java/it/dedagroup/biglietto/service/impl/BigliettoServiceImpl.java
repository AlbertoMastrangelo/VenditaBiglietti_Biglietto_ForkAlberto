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
        b.setIsCancellato(biglietto.isIsCancellato());
        b.setVersion(biglietto.getVersion());
        return repo.save(b);
    }
    
    @Transactional(rollbackOn = ResponseStatusException.class)
    @Override
    public void deleteByBiglietto(long id_biglietto) {
        Biglietto b = findById(id_biglietto);
        b.setIsCancellato(true);
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
    //0105ABRCTG
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
    public Biglietto findByIdAndIdPrezzoSettoreEvento(long id_biglietto, long id_prezzoSettoreEvento) {
        return repo.findByIdAndIdPrezzoSettoreEvento(id_biglietto, id_prezzoSettoreEvento).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Errore nel trovare il biglietto tramite id: "+id_biglietto+" e id prezzo settore evento: "+id_prezzoSettoreEvento));
    }

    @Override
    public List<Biglietto> findAllByIdPrezzoSettoreEvento(long id_prezzoSettoreEvento) {
        return repo.findAllByIdPrezzoSettoreEvento(id_prezzoSettoreEvento);
    }

    @Override
    public int countByIdPrezzoSettoreEventoAndDataAcquistoIsNotNull(long id_prezzoSettoreEvento) {
        return repo.countByIdPrezzoSettoreEventoAndDataAcquistoIsNotNull(id_prezzoSettoreEvento);
    }

    @Override
    public Biglietto findByIdAndIsCancellatoFalse(long id_biglietto) {
        return repo.findByIdAndIsCancellatoFalse(id_biglietto).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Errore nel trovare il biglietto tramite id: "+id_biglietto));
    }

    @Override
    public List<Biglietto> findAllByIsCancellatoFalse() {
        return repo.findAllByIsCancellatoFalse();
    }

    @Override
    public Biglietto findByIdAndIdUtenteAndIsCancellatoFalse(long id_biglietto, long id_utente) {
        return repo.findByIdAndIdUtenteAndIsCancellatoFalse(id_biglietto,id_utente).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Errore nel trovare il biglietto con id: "+id_biglietto+" e id utente: "+id_utente));
    }

    @Override
    public Biglietto findBySerialeAndIsCancellatoFalse(String seriale) {
        return repo.findBySerialeAndIsCancellatoFalse(seriale).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Errore nel trovare il biglietto con seriale: "+seriale));
    }

    @Override
    public List<Biglietto> findAllByPrezzoIsGreaterThanEqualAndIsCancellatoFalse(double prezzo) {
        return repo.findAllByPrezzoIsGreaterThanEqualAndIsCancellatoFalse(prezzo);
    }

    @Override
    public List<Biglietto> findAllByPrezzoIsLessThanEqualAndIsCancellatoFalse(double prezzo) {
        return repo.findAllByPrezzoIsLessThanEqualAndIsCancellatoFalse(prezzo);
    }

    @Override
    public List<Biglietto> findAllByIdUtenteAndIsCancellatoFalse(long id_utente) {
        return repo.findAllByIdUtenteAndIsCancellatoFalse(id_utente);
    }

    @Override
    public List<Biglietto> findAllByDataAcquistoAndIsCancellatoFalse(LocalDate dataAcquisto) {
        return repo.findAllByDataAcquistoAndIsCancellatoFalse(dataAcquisto);
    }
}
