package it.dedagroup.biglietto.dto.response;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class BigliettoDTOResponse {
	
	 private LocalDate dataAcquisto;
	 private double prezzo;
	 private String seriale;
	 private long version;
	 private long idUtente;
}
