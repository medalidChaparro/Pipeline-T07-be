package vallegrande.edu.pe.AgroTecno.model;

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
@Table(name = "address", schema = "dbo")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Integer addressId;

    @Column(name = "department", length = 30, nullable = false)
    private String department;

    @Column(name = "province", length = 30, nullable = false)
    private String province;

    @Column(name = "district", length = 30, nullable = false)
    private String district;

    @Column(name = "settlement_type", length = 1, nullable = false)
    private String settlementType;

    @Column(name = "settlement_name", length = 20, nullable = false)
    private String settlementName;

    @Column(name = "street_type", length = 1, nullable = false)
    private String streetType;

    @Column(name = "street_name", length = 20, nullable = false)
    private String streetName;

    @Column(name = "reference", columnDefinition = "TEXT")
    private String reference;

}
