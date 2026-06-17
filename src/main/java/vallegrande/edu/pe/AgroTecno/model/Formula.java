package vallegrande.edu.pe.AgroTecno.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "formula", schema = "dbo") // Asegúrate de poner el schema correcto
public class Formula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "formula_id")
    private Integer formulaId;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "description", columnDefinition = "VARCHAR(MAX)")
    private String description;

    @Column(name = "standard_batch", precision = 12, scale = 2)
    private BigDecimal standardBatch;

    @Column(name = "unit", length = 1, columnDefinition = "CHAR(1)")
    private String unit;

    @Column(name = "production_time")
    private Integer productionTime;

    @Column(name = "preparation_cost", precision = 12, scale = 2)
    private BigDecimal preparationCost;

    @Column(name="suggested_price", precision = 12, scale = 2)
    private BigDecimal suggestedPrice;

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
