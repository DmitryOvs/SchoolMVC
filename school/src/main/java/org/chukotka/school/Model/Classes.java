package org.chukotka.school.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import java.util.List;
import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "classes")
public class Classes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotBlank(message = "Поле не должно быть пустым")
    @Size(min = 1, max = 3, message = "Номер класса не может быть меньше одного знака и больше трех")
    @Column(name = "name")
    private String name;

    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @OneToMany(mappedBy = "klass", fetch = FetchType.EAGER)
    private List<Schoolboy> pupils;

    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @OneToOne(mappedBy = "glass")
    private Schedule schedule;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Classes classes = (Classes) o;
        return id == classes.id && Objects.equals(name, classes.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
