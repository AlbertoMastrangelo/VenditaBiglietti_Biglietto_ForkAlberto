package it.dedagroup.biglietto.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Biglietto {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private LocalDate dataAcquisto;
    @Column
    private double prezzo;
    @Column(unique = true)
    private String seriale;
    @Column
    private boolean isCancellato;
    @Version
    private long version;
    @Column
    private long idUtente;
    @Column(nullable = false)
    private long idPrezzoSettoreEvento;

    public Biglietto(LocalDate dataAcquisto, double prezzo, String seriale, long version, long idUtente, long idPrezzoSettoreEvento) {
        this.dataAcquisto = dataAcquisto;
        this.prezzo = prezzo;
        this.seriale = seriale;
        this.version = version;
        this.idUtente = idUtente;
        this.idPrezzoSettoreEvento = idPrezzoSettoreEvento;
    }
}
