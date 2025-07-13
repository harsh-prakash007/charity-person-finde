package find_person.com.example.charity.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "persons")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int age;
    private String skinColor;
    private int height; // in cm
    private int weight; // in kg
    private String eyeColor;
    private String hairColor;
    private String birthMark;
    private String disability;
    private String contactInfo;
    private String reward;

    @Lob
    @Column(nullable = false)
    private byte[] photo;

    public String getPhysicalDescription() {
        return String.format("%dcm, %dkg, %s eyes, %s hair",
                height, weight, eyeColor, hairColor);
    }
}