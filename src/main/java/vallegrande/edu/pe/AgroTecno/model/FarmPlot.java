package vallegrande.edu.pe.AgroTecno.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "farm_plot", schema = "dbo")
public class FarmPlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "farm_id")
    private Integer farmId;

    @Column(name = "name", length = 40, nullable = false)
    private String name;

    @Column(name = "hectares")
    private BigDecimal hectares;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false, 
                foreignKey = @ForeignKey(name = "FK_farm_plot_customer"))
    private Client customer;

    @ManyToOne
    @JoinColumn(name = "address_id", nullable = false, 
                foreignKey = @ForeignKey(name = "FK_farm_plot_address"))
    private Address address;

    @Column(name = "status", nullable = false)
    private Boolean estado;

    // Auditoría
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "restored_at")
    private LocalDateTime restoredAt;

}
