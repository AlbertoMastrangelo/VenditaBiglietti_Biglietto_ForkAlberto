package it.dedagroup.biglietto.service.impl;

import it.dedagroup.biglietto.dto.request.AggiuntaBigliettoDTORequest;
import it.dedagroup.biglietto.mapper.BigliettoMapper;
import it.dedagroup.biglietto.model.Biglietto;
import it.dedagroup.biglietto.repository.BigliettoRepository;
import it.dedagroup.biglietto.repository.BigliettoCriteriaQuery;
import it.dedagroup.biglietto.service.def.BigliettoServiceDef;
import it.dedagroup.biglietto.utils.Utility;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class BigliettoServiceImpl implements BigliettoServiceDef {
	
    private final BigliettoRepository repo;
    private final BigliettoCriteriaQuery criteriaQuery;
    private final MailSenderService mailSenderService;
    private final BigliettoMapper bigliettoMapper;
    /**
     * Metodo per salvare un biglietto tramite microservizio
     * @param biglietto - Oggetto DTO Request per poter salvare un biglietto
     * @return {@link Biglietto} - viene restituito il biglietto salvato
     * @throws {@link Exception} - viene lanciata in caso in cui venisse salvato un biglietto con un version obsoleto
     */
    @Transactional(rollbackOn = Exception.class)
    @Override
    public Biglietto saveBiglietto(AggiuntaBigliettoDTORequest biglietto) {
        Biglietto bigliettoNuovo = bigliettoMapper.fromAggiuntaBigliettoDTOToBiglietto(biglietto);
        mailSenderService.inviaMessaggio(biglietto.getEmail(), biglietto.getNome(), biglietto.getCognome());
        return repo.save(bigliettoNuovo);
    }
    /**
     * Metodo per modificare un biglietto tramite microservizio
     * @param biglietto - Oggetto DTO Request per poter modificare un biglietto
     * @return {@link Biglietto} - viene restituito il biglietto modificato
     * @throws {@link Exception} - viene lanciata in caso in cui venisse modificato un biglietto con un version obsoleto
     */
    @Transactional(rollbackOn = Exception.class)
    @Override
    public Biglietto modifyBiglietto(Biglietto biglietto) {
        try {
            Biglietto b = findById(biglietto.getId());
            b.setDataAcquisto(biglietto.getDataAcquisto());
            b.setPrezzo(biglietto.getPrezzo());
            b.setSeriale(biglietto.getSeriale());
            b.setCancellato(biglietto.isCancellato());
            b.setVersion(biglietto.getVersion());
            return repo.save(b);
        } catch (OptimisticLockingFailureException e){
            throw new OptimisticLockingFailureException("Il biglietto e' stato modificato");
        }

    }
    /**
     * Metodo per eliminare un biglietto tramite microservizio
     * @param id_biglietto - parametro long, l'id del biglietto da eliminare
     * @return {@link String} - viene restituito un messaggio di rimozione avvenuta con successo
     * @throws {@link Exception} - viene lanciata in caso in cui venisse eliminato un biglietto con un version obsoleto
     */
    @Transactional(rollbackOn = Exception.class)
    @Override
    public void deleteByBiglietto(long id_biglietto) {
        try {
            Biglietto b = findById(id_biglietto);
            b.setCancellato(true);
            repo.save(b);
        } catch (OptimisticLockingFailureException e){
            throw new OptimisticLockingFailureException("Il biglietto e' stato modificato");
        }

    }
    /**
     * Metodo per trovare un biglietto tramite id
     * @param id_biglietto - parametro long, l'id del biglietto da trovare
     * @return {@link Biglietto} - viene restituito un messaggio del biglietto trovato tramite id
     * @throws {@link ResponseStatusException} - viene lanciata in caso in cui non venisse trovato il biglietto tramite l'id in input
     */
    @Override
    public Biglietto findById(long id_biglietto) {
        return repo.findById(id_biglietto).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Errore nel trovare il biglietto tramite id: "+id_biglietto));
    }
    /**
     * Metodo per trovare tutti i biglietti presenti nel sistema del microservizio
     * @return {@link Biglietto} - viene restituita una lista di tutti i biglietti presenti nel sistema
     */
    @Override
    public List<Biglietto> findAll() {
        return repo.findAll();
    }
    /**
     * Metodo per trovare un biglietto tramite seriale
     * @param seriale - parametro String, il seriale del biglietto da trovare
     * @return {@link Biglietto} - viene restituito il biglietto trovato tramite seriale
     * @throws {@link ResponseStatusException} - viene lanciata in caso in cui non venisse trovato il biglietto tramite il seriale in input
     */
    @Override
    public Biglietto findBySeriale(String seriale) {
        return repo.findBySeriale(seriale).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Errore nel trovare il biglietto con seriale: "+seriale));
    }
    /**
     * Metodo per trovare tutti i biglietti con prezzo maggiore o uguale al prezzo inserito in input
     * @param prezzo - parametro double, il prezzo del biglietto
     * @return {@link Biglietto} - viene restituito la lista di biglietto con prezzo maggiore o uguale al parametro inserito in input
     */
    @Override
    public List<Biglietto> findAllByPrezzoIsGreaterThanEqual(double prezzo) {
        return repo.findAllByPrezzoIsGreaterThanEqual(prezzo);
    }
    /**
     * Metodo per trovare tutti i biglietti con prezzo minore o uguale al prezzo inserito in input
     * @param prezzo - parametro double, il prezzo del biglietto
     * @return {@link Biglietto} - viene restituito la lista di biglietto con prezzo minore o uguale al parametro inserito in input
     */
    @Override
    public List<Biglietto> findAllByPrezzoIsLessThanEqual(double prezzo) {
        return repo.findAllByPrezzoIsLessThanEqual(prezzo);
    }
    /**
     * Metodo per trovare tutti i biglietti tramite l'id di un'utente in input
     * @param id_utente - parametro long, l'id dell'utente
     * @return {@link Biglietto} - viene restituita una lista di biglietti con l'id utente inserito in input
     */
    @Override
    public List<Biglietto> findAllByIdUtente(long id_utente) {
        return repo.findAllByIdUtente(id_utente);
    }
    /**
     * Metodo per trovare tutti i biglietti tramite la data di acquisto inserita in input
     * @param dataAcquisto - parametro LocalDate, la data di acquisto
     * @return {@link Biglietto} - viene restituita una lista di biglietti con la data d'acquisto inserita in input
     */
    @Override
    public List<Biglietto> findAllByDataAcquisto(LocalDate dataAcquisto) {
        return repo.findAllByDataAcquisto(dataAcquisto);
    }
    /**
     * Metodo per trovare tutti i biglietti tramite l'id di un prezzo settore evento ordinandoli per prezzo crescente
     * @param id_prezzoSettoreEvento - parametro long, l'id del prezzo settore evento
     * @return {@link Biglietto} - viene restituita una lista di biglietti con l'id prezzo settore evento inserito in input
     */
    @Override
    public List<Biglietto> findAllByIdPrezzoSettoreEventoOrderByPrezzoAsc(long id_prezzoSettoreEvento) {
        return repo.findAllByIdPrezzoSettoreEventoOrderByPrezzoAsc(id_prezzoSettoreEvento);
    }
    /**
     * Metodo per contare i biglietti con id prezzo settore evento inserito e data d'acquisto presente
     * @param id_prezzoSettoreEvento - parametro long, l'id del prezzo settore evento
     * @return - viene restituito un numero intero che segna il conteggio dei biglietti con l'id prezzo settore evento inserito in input e data d'acquisto presente
     */
    @Override
    public int countByIdPrezzoSettoreEventoAndDataAcquistoIsNotNull(long id_prezzoSettoreEvento) {
        return repo.countByIdPrezzoSettoreEventoAndDataAcquistoIsNotNull(id_prezzoSettoreEvento);
    }
    /**
     * Metodo per trovare i prezzi distinti dei biglietti e con id prezzo settore evento inserito in input
     * @param id_prezzoSettoreEvento - parametro long, l'id del prezzo settore evento
     * @return {@link Double} - viene restituita una lista dei prezzi distinti dei biglietti con id prezzo settore evento
     */
    @Override
    public List<Double> findDistinctPrezzoBigliettoByIdPrezzoSettoreEvento(long id_prezzoSettoreEvento) {
        return repo.findDistinctPrezzoBigliettoByIdPrezzoSettoreEvento(id_prezzoSettoreEvento);
    }
    /**
     * Metodo per trovare tutti i biglietti tramite una lista di id prezzo settore eventi
     * @param idsPrezzoSettoreEvento - parametro List<Long>, lista degli id di prezzo settore eventi
     * @return {@link Biglietto} - viene restituita una lista di biglietti
     */
    @Override
    public List<Biglietto> findAllByIdPrezzoSettoreEventoIn(List<Long> idsPrezzoSettoreEvento) {
        return repo.findAllByIdPrezzoSettoreEventoIn(idsPrezzoSettoreEvento);
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

    @Override
    public List<Biglietto> findAllBigliettiFiltrati(Map<String, String> parametriBiglietto) {
        return criteriaQuery.getBigliettiFiltrati(parametriBiglietto);
    }
}
