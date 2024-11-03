package org.chukotka.school.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.chukotka.school.validator.CurrentYear;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "schoolboy")
public class Schoolboy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotNull(message = "Поле не должно быть пустым")
    @Pattern(regexp = "([А-ЯЁ][а-яА-ЯёЁ\\-]+\\s)[А-ЯЁ][а-яё]+",
            message = "Должен быть формат \"Фамилия Имя\" на русском языке")
    @Column(name = "name")
    private String name;

    @NotNull(message = "Поле не должно быть пустым")
   // @AverageRating
    @Column(name = "schoolboycol")
    private Double schoolboycol;

    @NotNull(message = "Поле не должно быть пустым")
    @Email
    private String email;

    @Min(value = 6, message = "Возраст должен быть больше 6")
    @CurrentYear
    @Column(name = "year")
    private int year;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date")
    private Date createDate;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modify_date")
    private Date modifyDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @JoinColumn(name = "schoolboycol_id", referencedColumnName = "id")
    private Classes klass;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Schoolboy schoolboy = (Schoolboy) o;
        return year == schoolboy.year && Objects.equals(name, schoolboy.name) &&
                Objects.equals(schoolboycol, schoolboy.schoolboycol) && Objects.equals(email, schoolboy.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, schoolboycol, email, year);
    }
}
