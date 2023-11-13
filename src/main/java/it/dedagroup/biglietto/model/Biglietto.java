package it.dedagroup.biglietto.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
    private long id;
    @Column
    private LocalDate dataAcquisto;
    @Column
    private double prezzo;
    @Column
    private String seriale;
    @Column
    private boolean cancellato;
    @Version
    private long version;

    @Column
    private long idUtente;
}
