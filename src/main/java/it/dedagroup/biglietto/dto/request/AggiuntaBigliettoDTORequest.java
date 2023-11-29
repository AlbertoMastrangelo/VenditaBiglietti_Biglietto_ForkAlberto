package it.dedagroup.biglietto.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AggiuntaBigliettoDTORequest {
    private String email;
    private String nome;
    private String cognome;
    private double prezzo;
    private long idUtente;
    private long idPrezzoSettoreEvento;
}
