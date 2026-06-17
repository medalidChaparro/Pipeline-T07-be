package vallegrande.edu.pe.AgroTecno.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sales_order", schema = "dbo")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Integer orderId;

    @Column(name = "referral_guide", length = 20)
    private String referralGuide;

    @Column(name = "entry_date", nullable = false)
    @JsonFormat(pattern = "dd_MM_yyyy HH:mm_ss")
    private LocalDateTime entryDate;

    @Column(name = "delivery_date")
    @JsonFormat(pattern = "dd_MM_yyyy HH:mm_ss")
    private LocalDateTime deliveryDate;

    @Column(name = "order_status", length = 1, columnDefinition = "CHAR(1)")
    private String orderStatus;

    @Column(name = "status", nullable = false)
    private Boolean status; // Mapea perfectamente con el campo BIT de la base de datos

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id", nullable = false)
    private Client client;

    // Relación limpia en cascada con los detalles
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<OrderDetail> details = new ArrayList<>();
}
