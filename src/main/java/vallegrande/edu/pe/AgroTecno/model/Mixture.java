package vallegrande.edu.pe.AgroTecno.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "mixture", schema = "dbo")
public class Mixture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mixture_id")
    private Integer mixtureId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "formula_id", nullable = false)
    private Formula formula;

    @Column(name = "default_quantity", nullable = false, precision = 12, scale = 2)
    private BigDecimal defaultQuantity;

    @Column(name = "percentage_quantity", nullable = false, precision = 12, scale = 2)
    private BigDecimal percentageQuantity;

    @Column(name = "production_time")
    private Integer productionTime;

    @Column(name = "status", nullable = false, length = 1)
    private String status;

    @Column(name = "registration_date", nullable = false)
    private LocalDateTime registrationDate;

    @Column(name = "total_cost", nullable = false, precision = 12, scale = 8)
    private BigDecimal totalCost;

    @OneToMany(mappedBy = "mixture", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MixtureDetail> details = new ArrayList<>();
}
