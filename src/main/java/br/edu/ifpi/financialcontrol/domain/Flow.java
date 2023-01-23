package br.edu.ifpi.financialcontrol.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Flow {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private String code;

    private String description;

    private String note;

    private BigDecimal value;

    @CreationTimestamp
    private OffsetDateTime date;

    @ManyToOne
    private Type type;

    @ManyToOne
    private Category category;

    @PrePersist
    private void generateCode(){
        this.code = UUID.randomUUID().toString();
    }

}
