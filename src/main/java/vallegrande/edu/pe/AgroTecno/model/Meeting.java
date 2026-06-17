package vallegrande.edu.pe.AgroTecno.model;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "meeting", schema = "dbo")
public class Meeting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meeting_id")
    private Integer meetingId;

    @Column(name = "meeting_date", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate meetingDate;

    @Column(name = "meeting_time", nullable = false)
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime meetingTime;

    @Column(name = "fruit_quality", length = 50)
    private String fruitQuality;

    @Column(name = "observation", columnDefinition = "TEXT")
    private String observation;

    @Column(name = "visit_status", nullable = false, length = 1, columnDefinition = "CHAR(1)")
    private String visitStatus;

    @Column(name = "production_order_id", nullable = false)
    private Integer productionOrderId;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id", nullable = false)
    private Client client;

    @Column(name = "farm_id", nullable = false)
    private Integer farmId;
}
