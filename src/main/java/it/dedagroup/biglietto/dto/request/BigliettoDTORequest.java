package it.dedagroup.biglietto.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class BigliettoDTORequest {
	
	@NotBlank(message = "La data non può essere vuota")
	private LocalDate dataAcquisto;
	
	@NotBlank(message = "Il prezzo non può essere vuoto")
	@Min(value = 0, message = "Il prezzo non può essere minore di 0")
	private double prezzo;
	
	@NotBlank(message = "Il numero Seriale non può essere vuoto")
	private String seriale;
	
    @NotBlank(message = "L'ID utente non può essere vuoto")
	@Min(value = 1, message = "L'ID non può essere minore di 1")
    private long idUtente;


}
