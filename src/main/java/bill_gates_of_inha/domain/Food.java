package bill_gates_of_inha.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Table(indexes = {@Index(columnList= "name")}, uniqueConstraints = {@UniqueConstraint(columnNames = {"name"})})
public class Food extends Date {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", nullable=false)
    private String name;
    @Column(nullable = false)
    private Double serving;
    @Column(nullable = false)
    private Double calorie;
    @Column(nullable = false)
    private Double carbohydrate;
    @Column(nullable = false)
    private Double protein;
    @Column(nullable = false)
    private Double fat;
    private Double sugars;
    private Double salts;
}
