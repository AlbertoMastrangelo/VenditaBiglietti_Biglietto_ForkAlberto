package it.dedagroup.biglietto.controller;

import it.dedagroup.biglietto.dto.request.BigliettoDTORequest;
import it.dedagroup.biglietto.mapper.BigliettoMapper;
import it.dedagroup.biglietto.model.Biglietto;
import it.dedagroup.biglietto.service.def.BigliettoServiceDef;
import lombok.AllArgsConstructor;
import static org.springframework.http.HttpStatus.*;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import static it.dedagroup.biglietto.util.UtilPath.*;
import java.time.LocalDate;
import java.util.List;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("/biglietto")
@AllArgsConstructor
@Tag(name = "Biglietto Controller", description = "In questo controller andremo ad " +
        "aggiungere, modificare, eliminare o cercare i vari biglietti tramite i propri attributi")
public class BigliettoController {
	
    private final BigliettoServiceDef bigliettoServiceDef;
    
    private final BigliettoMapper bMap;
    
    @Operation(summary = "metodo per salvare un Biglietto", description = "In questo EndPoint prendiamo un oggetto BigliettoDTORequest e lo salviamo nel DB tramite la repository")
    @ApiResponses(value = {
    	       @ApiResponse(responseCode = "200", description = "Biglietto salvato correttamente", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = BigliettoDTORequest.class))),
    	       @ApiResponse(responseCode = "400", description = "Errato inserimento del JSON, ci viene restituito come risposta un errore", content = @Content(mediaType = MediaType.ALL_VALUE))
    	})
    @PostMapping(INSERT_BIGLIETTO_PATH)
    public ResponseEntity<Biglietto> saveBiglietto(@RequestBody BigliettoDTORequest biglietto){
        return ResponseEntity.status(CREATED).body(bigliettoServiceDef.saveBiglietto(bMap.fromDTORequestToBiglietto(biglietto)));
    }
    
    @Operation(summary = "metodo per modificare un Biglietto", description = "In questo EndPoint prendiamo un oggetto BigliettoDTORequest già esistente e lo modifichiamo per poi risalvarlo nel DB")
    @ApiResponses(value = {
    	       @ApiResponse(responseCode = "200", description = "Biglietto modificato correttamente", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = BigliettoDTORequest.class))),
    	       @ApiResponse(responseCode = "400", description = "Errato inserimento del JSON, ci viene restituito come risposta un errore", content = @Content(mediaType = MediaType.ALL_VALUE))
    	})
    @PostMapping(MODIFY_BIGLIETTO_PATH)
    public ResponseEntity<Biglietto> modifyBiglietto(@RequestBody BigliettoDTORequest biglietto){
        return ResponseEntity.status(OK).body(bigliettoServiceDef.modifyBiglietto(bMap.fromDTORequestToBiglietto(biglietto)));
    }
    
    @Operation(summary = "metodo per eliminare un Biglietto", description = "In questo EndPoint selezioniamo il biglietto tramite L'id inserito nel "
    						+ "PathVariable, per poi settargli il cancellato a true e aggiornare il DB tramite repository")
    @ApiResponses(value = {
    	       @ApiResponse(responseCode = "200", description = "Biglietto non più disponibile", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = BigliettoDTORequest.class))),
    	       @ApiResponse(responseCode = "400", description = "Biglietto non trovato, Errato inserimento del PathVariable, ci viene restituito come risposta un errore", content = @Content(mediaType = MediaType.ALL_VALUE))
    	})
    @PostMapping(DELETE_BIGLIETTO_PATH+"/{id}")
    public ResponseEntity<String> deleteByBiglietto(@PathVariable("id") long id_biglietto){
        bigliettoServiceDef.deleteByBiglietto(id_biglietto);
        return ResponseEntity.status(NO_CONTENT).body("Biglietto cancellato con successo");
    }
    
    @Operation(summary = "metodo per cercare un Biglietto", description = "In questo EndPoint cerchiamo il biglietto(se esiste) tramite L'id inserito nel "
			+ "PathVariable, per poi ritornare l'oggetto selezionato tramite repository")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Biglietto trovato tramite l'ID", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = BigliettoDTORequest.class))),
			@ApiResponse(responseCode = "400", description = "Biglietto non trovato, errato inserimento del PathVariable, ci viene restituito come risposta un errore", content = @Content(mediaType = MediaType.ALL_VALUE))
				})
    @GetMapping(FIND_BY_ID_PATH+"/{id}")
    public ResponseEntity<Biglietto> findById(@PathVariable("id")long id_biglietto){
        return ResponseEntity.status(OK).body(bigliettoServiceDef.findById(id_biglietto));
    }
    
    @Operation(summary = "metodo cercare tutti i Biglietti", description = "In questo EndPoint ritorniamo una lista di biglietti tramite repository")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Lista di Biglietti trovati", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = BigliettoDTORequest.class))),					
			@ApiResponse(responseCode = "400", description = "Biglietti non trovati, ci viene restituito come risposta un errore", content = @Content(mediaType = MediaType.ALL_VALUE))
				})
    @GetMapping(FIND_ALL_PATH)
    public ResponseEntity<List<Biglietto>> findAll(){
        return ResponseEntity.status(OK).body(bigliettoServiceDef.findAll());
    }
    
    @Operation(summary = "metodo per cercare un Biglietto tramite l'Utente", description = "In questo EndPoint cerchiamo il biglietto tramite L'ID inserito nel "
			+ "PathVariable, per poi ritornare l'oggetto selezionato tramite repository")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Biglietto trovato tramite ID e appartenente all'utente con ID inserito", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = BigliettoDTORequest.class))),
			@ApiResponse(responseCode = "400", description = "Biglietto non trovato, errato inserimento dei RequestParam, ci viene restituito come risposta un errore", content = @Content(mediaType = MediaType.ALL_VALUE))
				})
    @GetMapping(FIND_BY_ID_AND_UTENTE_ID_PATH)
    public ResponseEntity<Biglietto> findByIdAndIdUtente(@RequestParam("id_biglietto") long id_biglietto,@RequestParam("id_utente") long id_utente){
        return ResponseEntity.status(OK).body(bigliettoServiceDef.findByIdAndIdUtente(id_biglietto, id_utente));
    }
    
    @Operation(summary = "metodo per cercare un Biglietto tramite serialeBiglietto ", description = "In questo EndPoint cerchiamo il biglietto tramite il numero seriale del biglietto"
			+ " inserito nel PathVariable")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Biglietto trovato tramite numero seriale inserito", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = BigliettoDTORequest.class))),
			@ApiResponse(responseCode = "400", description = "Biglietto non trovato, errato inserimento del PathVariable, ci viene restituito come risposta un errore", content = @Content(mediaType = MediaType.ALL_VALUE))
				})
    @GetMapping(FIND_BY_SERIALE_PATH+"/{seriale}")
    public ResponseEntity<Biglietto> findBySeriale(@PathVariable("seriale") String seriale){
        return ResponseEntity.status(OK).body(bigliettoServiceDef.findBySeriale(seriale));
    }
    
    @Operation(summary = "metodo che ritorna una lista di Biglietti con prezzo maggiore o uguale al prezzo inserito", description = "Questo EndPoint ritorna una lista di biglietti con prezzo uguale o superiore al prezzo in serito traminte pathVariable")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Lista di biglietti trovata con prezzo superiore a quello inserito", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = BigliettoDTORequest.class))),
			@ApiResponse(responseCode = "400", description = "Lista di biglietti non trovata tramite prezzo superiore, errato inserimento del PathVariable, ci viene restituito come risposta un errore", content = @Content(mediaType = MediaType.ALL_VALUE))
				})
    @GetMapping(FIND_ALL_BY_PREZZO_IS_GREATER_THAN_EQUAL_PATH+"/{prezzo}")
    public ResponseEntity<List<Biglietto>> findAllByPrezzoIsGreaterThanEqual(@PathVariable("prezzo") double prezzo){
        return ResponseEntity.status(OK).body(bigliettoServiceDef.findAllByPrezzoIsGreaterThanEqual(prezzo));
    }
    
    @Operation(summary = "metodo che ritorna una lista di Biglietti con prezzo uguale o inferiore al prezzo inserito", description = "Questo EndPoint ritorna una lista di biglietti con prezzo uguale o inferiore al prezzo in serito traminte pathVariable")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Lista di biglietti trovata con prezzo inferiore a quello inserito", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = BigliettoDTORequest.class))),
			@ApiResponse(responseCode = "400", description = "Lista di biglietti non trovata tramite prezzo inferiore, errato inserimento del PathVariable, ci viene restituito come risposta un errore", content = @Content(mediaType = MediaType.ALL_VALUE))
				})
    @GetMapping(FIND_ALL_BY_PREZZO_IS_LESS_THAN_EQUAL_PATH+"/{prezzo}")
    public ResponseEntity<List<Biglietto>> findAllByPrezzoIsLessThanEqual(@PathVariable("prezzo") double prezzo){
        return ResponseEntity.status(OK).body(bigliettoServiceDef.findAllByPrezzoIsLessThanEqual(prezzo));
    }
    
    @Operation(summary = "metodo che ritorna una lista di Biglietti tramide l'ID Utente inserito", description = "Questo EndPoint ritorna una lista di biglietti tramide l'ID Utente inserito nel pathVariable")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Lista di biglietti trovata tramide l'ID Utente inserito", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = BigliettoDTORequest.class))),
			@ApiResponse(responseCode = "400", description = "Lista di biglietti non trovata tramite l'ID Utente inserito, errato inserimento del PathVariable, ci viene restituito come risposta un errore", content = @Content(mediaType = MediaType.ALL_VALUE))
				})
    @GetMapping(FIND_ALL_BY_ID_UTENTE_PATH+"/{id}")
    public ResponseEntity<List<Biglietto>> findAllByIdUtente(@PathVariable("id") long id_utente){
        return ResponseEntity.status(OK).body(bigliettoServiceDef.findAllByIdUtente(id_utente));
    }
    
    @Operation(summary = "metodo che ritorna una lista di Biglietti tramide la data di acquisto inserita", description = "Questo EndPoint ritorna una lista di biglietti tramide la data di acquisto inserita nel pathVariable")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Lista di biglietti trovata tramide la data di acquisto inserita", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = BigliettoDTORequest.class))),
			@ApiResponse(responseCode = "400", description = "Lista di biglietti non trovata tramite la data di acquisto inserita, errato inserimento del PathVariable, ci viene restituito come risposta un errore", content = @Content(mediaType = MediaType.ALL_VALUE))
				})
    @GetMapping(FIND_ALL_BY_DATA_ACQUISTO+"/{data}")
    public ResponseEntity<List<Biglietto>> findAllByDataAcquisto(@PathVariable("data") LocalDate dataAcquisto){
        return ResponseEntity.status(OK).body(bigliettoServiceDef.findAllByDataAcquisto(dataAcquisto));
    }
}
