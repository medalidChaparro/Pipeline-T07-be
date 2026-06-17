package vallegrande.edu.pe.AgroTecno.model;

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
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product_inventory")
public class ProductInventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_inventory_id")
    private Integer productInventoryId;

    // Relación directa con tu entidad Formula existente
    @ManyToOne
    @JoinColumn(name = "formula_formula_id", nullable = false, 
                foreignKey = @ForeignKey(name = "FK_product_inventory_formula"))
    private Formula formula;

    @Column(name = "gallon_capacity", nullable = false)
    private Integer gallonCapacity; // Aquí guardas la presentación: 1 o 20 (litros/galones)

    @Column(name = "stock_quantity", nullable = false)
    private Integer stockQuantity;

    @Column(name = "status", nullable = false)
    private Boolean status;

    @Column(name = "last_update")
    @JsonFormat(pattern = "dd_MM_yyyy HH:mm_ss")
    private LocalDateTime lastUpdate;

}