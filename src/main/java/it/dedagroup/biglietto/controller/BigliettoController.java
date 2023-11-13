package it.dedagroup.biglietto.controller;

import it.dedagroup.biglietto.model.Biglietto;
import it.dedagroup.biglietto.service.def.BigliettoServiceDef;
import lombok.AllArgsConstructor;
import static org.springframework.http.HttpStatus.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static it.dedagroup.biglietto.util.UtilPath.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/biglietto")
@AllArgsConstructor
public class BigliettoController {
    private final BigliettoServiceDef bigliettoServiceDef;
    @PostMapping(INSERT_BIGLIETTO_PATH)
    public ResponseEntity<Biglietto> saveBiglietto(Biglietto biglietto){
        return ResponseEntity.status(CREATED).body(bigliettoServiceDef.saveBiglietto(biglietto));
    }
    @PostMapping(MODIFY_BIGLIETTO_PATH)
    public ResponseEntity<Biglietto> modifyBiglietto(Biglietto biglietto){
        return ResponseEntity.status(OK).body(bigliettoServiceDef.modifyBiglietto(biglietto));
    }
    @PostMapping(DELETE_BIGLIETTO_PATH+"/{id}")
    public ResponseEntity<String> deleteByBiglietto(@PathVariable("id") long id_biglietto){
        bigliettoServiceDef.deleteByBiglietto(id_biglietto);
        return ResponseEntity.status(NO_CONTENT).body("Biglietto cancellato con successo");
    }
    @GetMapping(FIND_BY_ID_PATH+"/{id}")
    public ResponseEntity<Biglietto> findById(@PathVariable("id")long id_biglietto){
        return ResponseEntity.status(OK).body(bigliettoServiceDef.findById(id_biglietto));
    }
    @GetMapping(FIND_ALL_PATH)
    public ResponseEntity<List<Biglietto>> findAll(){
        return ResponseEntity.status(OK).body(bigliettoServiceDef.findAll());
    }
    @GetMapping(FIND_BY_ID_AND_UTENTE_ID_PATH)
    public ResponseEntity<Biglietto> findByIdAndIdUtente(@RequestParam("id_biglietto") long id_biglietto,@RequestParam("id_utente") long id_utente){
        return ResponseEntity.status(OK).body(bigliettoServiceDef.findByIdAndIdUtente(id_biglietto, id_utente));
    }
    @GetMapping(FIND_BY_SERIALE_PATH+"/{seriale}")
    public ResponseEntity<Biglietto> findBySeriale(@PathVariable("seriale") String seriale){
        return ResponseEntity.status(OK).body(bigliettoServiceDef.findBySeriale(seriale));
    }
    @GetMapping(FIND_ALL_BY_PREZZO_IS_GREATER_THAN_EQUAL_PATH+"/{prezzo}")
    public ResponseEntity<List<Biglietto>> findAllByPrezzoIsGreaterThanEqual(@PathVariable("prezzo") double prezzo){
        return ResponseEntity.status(OK).body(bigliettoServiceDef.findAllByPrezzoIsGreaterThanEqual(prezzo));
    }
    @GetMapping(FIND_ALL_BY_PREZZO_IS_LESS_THAN_EQUAL_PATH+"/{prezzo}")
    public ResponseEntity<List<Biglietto>> findAllByPrezzoIsLessThanEqual(@PathVariable("prezzo") double prezzo){
        return ResponseEntity.status(OK).body(bigliettoServiceDef.findAllByPrezzoIsLessThanEqual(prezzo));
    }
    @GetMapping(FIND_ALL_BY_ID_UTENTE_PATH+"/{id}")
    public ResponseEntity<List<Biglietto>> findAllByIdUtente(@PathVariable("id") long id_utente){
        return ResponseEntity.status(OK).body(bigliettoServiceDef.findAllByIdUtente(id_utente));
    }
    @GetMapping(FIND_ALL_BY_DATA_ACQUISTO+"/{data}")
    public ResponseEntity<List<Biglietto>> findAllByDataAcquisto(@PathVariable("data") LocalDate dataAcquisto){
        return ResponseEntity.status(OK).body(bigliettoServiceDef.findAllByDataAcquisto(dataAcquisto));
    }
}
