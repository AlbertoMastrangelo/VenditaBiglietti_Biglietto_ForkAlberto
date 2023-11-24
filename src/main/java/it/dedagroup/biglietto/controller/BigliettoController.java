package it.dedagroup.biglietto.controller;

import it.dedagroup.biglietto.model.Biglietto;
import it.dedagroup.biglietto.service.def.BigliettoServiceDef;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;

import static it.dedagroup.biglietto.path.UtilPath.*;
import static org.springframework.http.HttpStatus.*;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@AllArgsConstructor
@Validated
@Tag(name = "Biglietto Controller", description = "In questo controller andremo ad " +
        "aggiungere, modificare, eliminare o cercare i vari biglietti tramite i propri attributi")
public class BigliettoController {

    private final BigliettoServiceDef bigliettoServiceDef;

    @Operation(summary = "Metodo per salvare un Biglietto", description = "In questo EndPoint prendiamo un oggetto BigliettoDTORequest e lo salviamo nel DB tramite la repository")
    @ApiResponses(value = {
    	       @ApiResponse(responseCode = "200", description = "Biglietto salvato correttamente", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Biglietto.class))),
    	       @ApiResponse(responseCode = "400", description = "Errato inserimento del JSON, ci viene restituito come risposta un errore", content = @Content(mediaType = MediaType.ALL_VALUE))
    	})
    @PostMapping(INSERT_BIGLIETTO_PATH)
    public ResponseEntity<Biglietto> saveBiglietto(@RequestBody Biglietto biglietto){
        return ResponseEntity.status(CREATED).body(bigliettoServiceDef.saveBiglietto(biglietto));
    }

    @Operation(summary = "Metodo per modificare un Biglietto", description = "In questo EndPoint prendiamo un oggetto BigliettoDTORequest già esistente e lo modifichiamo per poi risalvarlo nel DB")
    @ApiResponses(value = {
    	       @ApiResponse(responseCode = "200", description = "Biglietto modificato correttamente", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Biglietto.class))),
    	       @ApiResponse(responseCode = "400", description = "Errato inserimento del JSON, ci viene restituito come risposta un errore", content = @Content(mediaType = MediaType.ALL_VALUE))
    	})
    @PostMapping(MODIFY_BIGLIETTO_PATH)
    public ResponseEntity<Biglietto> modifyBiglietto(@RequestBody Biglietto biglietto){
        return ResponseEntity.status(OK).body(bigliettoServiceDef.modifyBiglietto(biglietto));
    }

    @Operation(summary = "Metodo per eliminare un Biglietto", description = "In questo EndPoint selezioniamo il biglietto tramite L'id inserito nel "
    						+ "PathVariable, per poi settargli il cancellato a true e aggiornare il DB tramite repository")
    @ApiResponses(value = {
    	       @ApiResponse(responseCode = "200", description = "Biglietto non più disponibile", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = String.class))),
    	       @ApiResponse(responseCode = "400", description = "Biglietto non trovato, Errato inserimento del PathVariable, ci viene restituito come risposta un errore", content = @Content(mediaType = MediaType.ALL_VALUE))
    	})
    @PostMapping(DELETE_BIGLIETTO_PATH+"/{id}")
    public ResponseEntity<String> deleteByBiglietto(@PathVariable("id") @Positive(message = "Inserisci un id positivo") @Min(value = 1, message = "Inserisci un valore maggiore o uguale a 1") long id_biglietto){
        bigliettoServiceDef.deleteByBiglietto(id_biglietto);
        return ResponseEntity.status(NO_CONTENT).body("Biglietto cancellato con successo");
    }

    @Operation(summary = "Metodo per cercare un Biglietto", description = "In questo EndPoint cerchiamo il biglietto(se esiste) tramite L'id inserito nel "
			+ "PathVariable, per poi ritornare l'oggetto selezionato tramite repository")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Biglietto trovato tramite l'ID", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Biglietto.class))),
			@ApiResponse(responseCode = "404", description = "Biglietto non trovato, errato inserimento del PathVariable, ci viene restituito come risposta un errore", content = @Content(mediaType = MediaType.ALL_VALUE))
				})
    @PostMapping(FIND_BY_ID_PATH+"/{id}")
    public ResponseEntity<Biglietto> findById(@PathVariable("id") @Positive(message = "Inserire un id positivo") @Min(value = 1, message = "Inserire un id di valore minimo a 1") long id_biglietto){
        return ResponseEntity.status(OK).body(bigliettoServiceDef.findById(id_biglietto));
    }

    @Operation(summary = "Metodo cercare tutti i Biglietti", description = "In questo EndPoint ritorniamo una lista di biglietti tramite repository")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Lista di Biglietti trovati", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Biglietto[].class))),
			@ApiResponse(responseCode = "400", description = "Biglietti non trovati, ci viene restituito come risposta un errore", content = @Content(mediaType = MediaType.ALL_VALUE))
				})
    @GetMapping(FIND_ALL_PATH)
    public ResponseEntity<List<Biglietto>> findAll(){
        return ResponseEntity.status(OK).body(bigliettoServiceDef.findAll());
    }
    @Operation(summary = "Metodo per cercare una lista di Biglietti tramite l'ID del prezzo settore evento", description = "In questo EndPoint cerchiamo il biglietto tramite l'ID del prezzo settore evento inserito nel "
            + "PathVariable, in caso venisse trovato verra' restituito come risultato ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Biglietto trovato tramite ID e appartenente all'utente con ID inserito", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Biglietto[].class))),
            @ApiResponse(responseCode = "404", description = "Biglietto non trovato, errato inserimento dei RequestParam, ci viene restituito come risposta un errore", content = @Content(mediaType = MediaType.ALL_VALUE))
    })
    @PostMapping(FIND_ALL_BY_ID_PREZZO_SETTORE_EVENTO_PATH+"/{id_pse}")
    public ResponseEntity<List<Biglietto>> findAllByIdPrezzoSettoreEventoOrderByPrezzoAsc(@PathVariable("id_pse") @Positive(message = "Inserire un id positivo") @Min(value = 1, message = "Inserire un id di valore maggiore o uguale a 1") long id_prezzoSettoreEvento){
        return ResponseEntity.status(OK).body(bigliettoServiceDef.findAllByIdPrezzoSettoreEventoOrderByPrezzoAsc(id_prezzoSettoreEvento));
    }
    @Operation(summary = "Metodo per cercare un Biglietto tramite serialeBiglietto ", description = "In questo EndPoint cerchiamo il biglietto tramite il numero seriale del biglietto"
            + " inserito nel PathVariable")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Biglietto trovato tramite numero seriale inserito", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Biglietto.class))),
            @ApiResponse(responseCode = "404", description = "Biglietto non trovato, errato inserimento del PathVariable, ci viene restituito come risposta un errore", content = @Content(mediaType = MediaType.ALL_VALUE))
    })
    @PostMapping(FIND_BY_SERIALE_PATH+"/{seriale}")
    public ResponseEntity<Biglietto> findBySeriale(@PathVariable("seriale") @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Inserire un seriale contenente solo numeri e lettere") @NotBlank(message = "Inserire un valore nel campo: seriale") String seriale){
        return ResponseEntity.status(OK).body(bigliettoServiceDef.findBySeriale(seriale));
    }

    @Operation(summary = "Metodo che ritorna una lista di Biglietti con prezzo maggiore o uguale al prezzo inserito", description = "Questo EndPoint ritorna una lista di biglietti con prezzo uguale o superiore al prezzo in serito traminte pathVariable")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Lista di biglietti trovata con prezzo superiore a quello inserito", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Biglietto[].class))),
			@ApiResponse(responseCode = "400", description = "Lista di biglietti non trovata tramite prezzo superiore, errato inserimento del PathVariable, ci viene restituito come risposta un errore", content = @Content(mediaType = MediaType.ALL_VALUE))
				})
    @GetMapping(FIND_ALL_BY_PREZZO_IS_GREATER_THAN_EQUAL_PATH+"/{prezzo}")
    public ResponseEntity<List<Biglietto>> findAllByPrezzoIsGreaterThanEqual(@PathVariable("prezzo") @Positive(message = "Inserire un valore numerico positivo per il prezzo") @Min(value = 0, message =  "Inserire un valore numerico maggiore di o uguale a 0 per il prezzo") double prezzo){
        return ResponseEntity.status(OK).body(bigliettoServiceDef.findAllByPrezzoIsGreaterThanEqual(prezzo));
    }

    @Operation(summary = "Metodo che ritorna una lista di Biglietti con prezzo uguale o inferiore al prezzo inserito", description = "Questo EndPoint ritorna una lista di biglietti con prezzo uguale o inferiore al prezzo in serito traminte pathVariable")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Lista di biglietti trovata con prezzo inferiore a quello inserito", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Biglietto[].class))),
			@ApiResponse(responseCode = "400", description = "Lista di biglietti non trovata tramite prezzo inferiore, errato inserimento del PathVariable, ci viene restituito come risposta un errore", content = @Content(mediaType = MediaType.ALL_VALUE))
				})
    @GetMapping(FIND_ALL_BY_PREZZO_IS_LESS_THAN_EQUAL_PATH+"/{prezzo}")
    public ResponseEntity<List<Biglietto>> findAllByPrezzoIsLessThanEqual(@PathVariable("prezzo") @Positive(message = "Inserire un valore numerico positivo per il prezzo") @Min(value = 0, message =  "Inserire un valore numerico maggiore di o uguale a 0 per il prezzo") double prezzo){
        return ResponseEntity.status(OK).body(bigliettoServiceDef.findAllByPrezzoIsLessThanEqual(prezzo));
    }

    @Operation(summary = "Metodo che ritorna una lista di Biglietti tramide l'ID Utente inserito", description = "Questo EndPoint ritorna una lista di biglietti tramide l'ID Utente inserito nel pathVariable")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Lista di biglietti trovata tramide l'ID Utente inserito", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Biglietto[].class))),
			@ApiResponse(responseCode = "400", description = "Lista di biglietti non trovata tramite l'ID Utente inserito, errato inserimento del PathVariable, ci viene restituito come risposta un errore", content = @Content(mediaType = MediaType.ALL_VALUE))
				})
    @PostMapping(FIND_ALL_BY_ID_UTENTE_PATH+"/{id}")
    public ResponseEntity<List<Biglietto>> findAllByIdUtente(@PathVariable("id") @Positive(message = "Inserire un valore numerico positivo per l'id") @Min(value = 1, message =  "Inserire un valore numerico maggiore di o uguale a 0 per il prezzo")long id_utente){
        return ResponseEntity.status(OK).body(bigliettoServiceDef.findAllByIdUtente(id_utente));
    }

    @Operation(summary = "Metodo che ritorna una lista di Biglietti tramide la data di acquisto inserita", description = "Questo EndPoint ritorna una lista di biglietti tramide la data di acquisto inserita nel pathVariable")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Lista di biglietti trovata tramide la data di acquisto inserita", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Biglietto[].class))),
			@ApiResponse(responseCode = "400", description = "Lista di biglietti non trovata tramite la data di acquisto inserita, errato inserimento del PathVariable, ci viene restituito come risposta un errore", content = @Content(mediaType = MediaType.ALL_VALUE))
				})
    @GetMapping(FIND_ALL_BY_DATA_ACQUISTO+"/{data}")
    public ResponseEntity<List<Biglietto>> findAllByDataAcquisto(@PathVariable("data") @NotBlank(message = "Valorizzare il campo della data d'acquisto") @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Inserisci una data valida") LocalDate dataAcquisto){
        return ResponseEntity.status(OK).body(bigliettoServiceDef.findAllByDataAcquisto(dataAcquisto));
    }
    @Operation(summary = "Metodo per contare i biglietti con ID prezzo settore evento inserito e la data di acquisto presente", description = "Questo EndPoint ritorna il conteggio dei biglietti tramite id prezzo settore evento inserito nel pathVariablela e data di acquisto presente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Viene restituito il conteggio dei biglietti con ID prezzo settore evento inserito e la data di acquisto presente", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Integer.class))),
    })
    @PostMapping(COUNT_BY_ID_PREZZO_SETTORE_EVENTO_AND_DATA_ACQUISTO_IS_NOT_NULL +"/{id}")
    public ResponseEntity<Integer> countByIdPrezzoSettoreEventoAndDataAcquistoIsNotNull(@PathVariable("id") @Positive(message = "Inserire un valore numerico positivo per l'id") @Min(value = 1, message =  "Inserire un valore numerico maggiore di o uguale a 0 per il prezzo")long id_prezzoSettoreEvento){
        return ResponseEntity.status(OK).body(bigliettoServiceDef.countByIdPrezzoSettoreEventoAndDataAcquistoIsNotNull(id_prezzoSettoreEvento));
    }
    @Operation(summary = "Metodo per trovare i valori distinti biglietti con ID prezzo settore evento inserito e la data di acquisto presente", description = "Questo EndPoint ritorna il conteggio dei biglietti tramite id prezzo settore evento inserito nel pathVariablela e data di acquisto presente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Viene restituito il conteggio dei biglietti con ID prezzo settore evento inserito e la data di acquisto presente", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Double[].class))),
    })
    @PostMapping(FIND_DISTINCT_PREZZO_BIGLIETTO_PATH+"/{id}")
    public ResponseEntity<List<Double>> findDistinctPrezzoBiglietto(@PathVariable("id") @Positive(message = "Inserire un valore numerico positivo per l'id") @Min(value = 1, message =  "Inserire un valore numerico maggiore di o uguale a 0 per il prezzo")long id_prezzoSettoreEvento){
        return ResponseEntity.status(OK).body(bigliettoServiceDef.findDistinctPrezzoBigliettoByIdPrezzoSettoreEvento(id_prezzoSettoreEvento));
    }
    @Operation(summary = "Metodo per trovare i biglietti tramite un id prezzo settore evento presente nella lista passata in input", description = "Questo EndPoint ci ritorna una lista di biglietti che possiedono uno degli id prezzo settore evento presenti nella lista in input")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Viene restituita una lista di biglietti che hanno l'id prezzo settore evento corrispondente ad uno dei vari id passati in input", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Biglietto[].class)))
    })
    @PostMapping(FIND_ALL_BY_ID_PREZZO_SETTORE_EVENTO_IN_IDS)
    public ResponseEntity<List<Biglietto>> findAllByIdPrezzoSettoreEventoIn(@RequestBody @NotBlank(message = "Valorizzare la lista di id prezzo-settore-evento") List<Long> idsPrezzoSettoreEvento){
        return ResponseEntity.status(OK).body(bigliettoServiceDef.findAllByIdPrezzoSettoreEventoIn(idsPrezzoSettoreEvento));
    }
    @Operation(summary = "Metodo per trovare il biglietto tramite id", description = "Questo EndPoint ci permette di trovare un biglietto tramite id e che non sia cancellato")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Il biglietto e' stato trovato tramite id e non e' cancellato", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Biglietto.class))),
            @ApiResponse(responseCode = "404", description = "Viene restituito un'errore perche' non e' presente un biglietto con l'id inserito e che non sia cancellato", content = @Content(mediaType = MediaType.ALL_VALUE))
    })
    @PostMapping(FIND_BY_ID_AND_NOT_CANCELLATO_PATH+"/{id}")
    public ResponseEntity<Biglietto> findByIdAndIsCancellatoFalse(@PathVariable("id") @Positive(message = "Inserire un valore numerico positivo per l'id") @Min(value = 1, message =  "Inserire un valore numerico maggiore di o uguale a 0 per il prezzo") long id_biglietto){
        return ResponseEntity.status(OK).body(bigliettoServiceDef.findByIdAndIsCancellatoFalse(id_biglietto));
    }
    @Operation(summary = "Metodo per trovare tutti i biglietti disponibili nel sistema", description = "Questo EndPoint ci permette di trovare tutti i biglietti presenti nel sistema e che non siano cancellati")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ritorna una lista di biglietti presenti nel sistema e che non siano cancellati", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Biglietto[].class))),
    })
    @GetMapping(FIND_ALL_NOT_CANCELLATO_PATH)
    public ResponseEntity<List<Biglietto>> findAllByIsCancellatoFalse(){
        return ResponseEntity.status(OK).body(bigliettoServiceDef.findAllByIsCancellatoFalse());
    }
    @Operation(summary = "Metodo per trovare un biglietto tramite seriale", description = "Questo EndPoint ci permette di trovare il biglietto tramite seriale e che non sia cancellato")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ritorna un biglietto trovato tramite seriale e che non sia cancellato", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Biglietto.class))),
            @ApiResponse(responseCode = "404", description = "Viene restituito un'errore perche' non e' presente un biglietto con il seriale inserito in input e che non sia cancellato", content = @Content(mediaType = MediaType.ALL_VALUE))
    })
    @PostMapping(FIND_BY_SERIALE_AND_NOT_CANCELLATO_PATH+"/{seriale}")
    public ResponseEntity<Biglietto> findBySerialeAndIsCancellatoFalse(@PathVariable("seriale") @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Inserire un seriale contenente solo numeri e lettere") @NotBlank(message = "Inserire un valore nel campo: seriale") String seriale){
        return ResponseEntity.status(OK).body(bigliettoServiceDef.findBySerialeAndIsCancellatoFalse(seriale));
    }
    @Operation(summary = "Metodo per trovare tutti i biglietti con prezzo maggiore o uguale al prezzo inserito in input", description = "Questo EndPoint ci permette di trovare tutti i biglietti con prezzo maggiore o uguale al prezzo inserito dall'utente e che non siano cancellati")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Viene restituita una lista di biglietti con prezzo maggiore o uguale al prezzo inserito dall'utente e che non siano cancellati", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Biglietto[].class)))
    })
    @GetMapping(FIND_ALL_BY_PREZZO_IS_GREATER_THAN_EQUAL_AND_NOT_CANCELLATO_PATH+"/{prezzo}")
    public ResponseEntity<List<Biglietto>> findAllByPrezzoIsGreaterThanEqualAndIsCancellatoFalse(@PathVariable("prezzo") @Positive(message = "Inserire un valore numerico positivo per il prezzo") @Min(value = 0, message =  "Inserire un valore numerico maggiore di o uguale a 0 per il prezzo") double prezzo){
        return ResponseEntity.status(OK).body(bigliettoServiceDef.findAllByPrezzoIsGreaterThanEqualAndIsCancellatoFalse(prezzo));
    }
    @Operation(summary = "Metodo per trovare tutti i biglietti con prezzo minore o uguale al prezzo inserito in input", description = "Questo EndPoint ci permette di trovare tutti i biglietti con prezzo minore o uguale al prezzo inserito dall'utente e che non siano cancellati")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Viene restituita una lista di biglietti con prezzo minore o uguale al prezzo inserito dall'utente e che non siano cancellati", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Biglietto[].class)))
    })
    @GetMapping(FIND_ALL_BY_PREZZO_IS_LESS_THAN_EQUAL_AND_NOT_CANCELLATO_PATH+"/{prezzo}")
    public ResponseEntity<List<Biglietto>> findAllByPrezzoIsLessThanEqualAndIsCancellatoFalse(@PathVariable("prezzo") @Positive(message = "Inserire un valore numerico positivo per il prezzo") @Min(value = 0, message =  "Inserire un valore numerico maggiore di o uguale a 0 per il prezzo")double prezzo){
        return ResponseEntity.status(OK).body(bigliettoServiceDef.findAllByPrezzoIsLessThanEqualAndIsCancellatoFalse(prezzo));
    }
    @Operation(summary = "Metodo per trovare tutti i biglietti con id utente inserito", description = "Questo EndPoint ci permette di trovare tutti i biglietti con id utente inserito in input e che non siano cancellati")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Viene restituita una lista di biglietti che hanno un'id utente uguale a quello inserito nella richiesta", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Biglietto[].class)))
    })
    @PostMapping(FIND_ALL_BY_ID_UTENTE_AND_NOT_CANCELLATO_PATH+"/{id}")
    public ResponseEntity<List<Biglietto>> findAllByIdUtenteAndIsCancellatoFalse(@PathVariable("id") @Positive(message = "Inserire un valore numerico positivo per l'id") @Min(value = 1, message =  "Inserire un valore numerico maggiore di o uguale a 0 per il prezzo") long id_utente ){
        return ResponseEntity.status(OK).body(bigliettoServiceDef.findAllByIdUtenteAndIsCancellatoFalse(id_utente));
    }
    @Operation(summary = "Metodo per trovare tutti i biglietti con data d'acquisto richiesta", description = "Questo EndPoint ci permette di trovare tutti i biglietti con data d'acquisto inserita in input e che non siano cancellati")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Viene restituita una lista di biglietti con data d'acquisto corrispondente a quella inserita e che non siano cancellati", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Biglietto[].class))),
    })
    @GetMapping(FIND_ALL_BY_DATA_ACQUISTO_AND_NOT_CANCELLATO_PATH+"/{data-acquisto}")
    public ResponseEntity<List<Biglietto>> findAllByDataAcquistoAndIsCancellatoFalse(@PathVariable("data-acquisto") @NotBlank(message = "Valorizzare il campo della data d'acquisto") @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Inserisci una data valida") LocalDate dataAcquisto){
        return ResponseEntity.status(OK).body(bigliettoServiceDef.findAllByDataAcquistoAndIsCancellatoFalse(dataAcquisto));
    }
    @Operation(summary = "Metodo per trovare i biglietti tramite un Map di parametri", description = "Questo EndPoint cerca i biglietti filtrati tramite una criteria query che richiede un Map di parametri dei biglietti")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Viene restituita una lista di biglietti con i parametri inseriti in input", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Biglietto[].class)))
    })
    @PostMapping(FIND_ALL_BIGLIETTI_FILTRATI)
    public ResponseEntity<List<Biglietto>> findAllBigliettiFiltrati(@RequestBody @NotBlank(message = "Valorizzare la mappa dei parametri biglietto") Map<String,String> parametriBiglietto){
        return ResponseEntity.status(OK).body(bigliettoServiceDef.findAllBigliettiFiltrati(parametriBiglietto));
    }
}
