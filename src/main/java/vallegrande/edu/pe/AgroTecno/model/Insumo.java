package vallegrande.edu.pe.AgroTecno.model;

import java.math.BigDecimal;
import java.time.LocalDate;
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

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "supply", schema = "dbo")
public class Insumo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "supply_id")
    private Integer idInsumo;

    @Column(name = "name", length = 50, nullable = false)
    private String nombre;

    @Column(name = "description", length = 255)
    private String descripcion;

    @Column(name = "current_stock", nullable = false)
    private BigDecimal stockActual;

    @Column(name = "minimum_stock", nullable = false)
    private BigDecimal stockMinimo;

    @Column(name = "unit", length = 5, nullable = false)
    private String medida;

    @Column(name = "expiration_date")
    private LocalDate fechaCaducidad;

    @Column(name = "location")
    private String ubicacion;

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
