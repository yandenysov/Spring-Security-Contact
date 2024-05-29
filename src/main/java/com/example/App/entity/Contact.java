package com.example.App.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "contacts")
public class Contact implements Serializable {

    // Під час серіалізації, середовище виконання
    // Java створює номер версії для класу (SerialVersionUID),
    // так що воно може десереалізувати його пізніше.
    // Якщо під час десеріалізації, SerialVersionUID не відповідний,
    // процес завершиться з винятком InvalidClassException в потоці main.
    //
    // serialVersionUID — універсальний ідентифікатор версії
    // для Serializable класу. Десеріалізація використовує це число,
    // щоб гарантувати, що завантажений клас точно відповідає
    // серіалізованому об’єкту.
    // Якщо відповідності не знайдено, створюється виняткова ситуація
    // InvalidClassException.
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // nullable = false - поле не може бути null
    @Column(nullable = false)
    private String name;
    // unique = true - поле має бути унікальним
    // у таблиці бази даних
    @Column(nullable = false)
    private String phone;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    // @ManyToMany з JPA та вказує, що зв’язок між сутностями
    // Contact та Role є «багато-до-багатьох».
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    // @JoinTable з JPA, визначає деталі таблиці об’єднання,
    // яка використовується для реалізації зв’язку «багато-до-багатьох»
    // між сутностями Contact та Role.
    // Визначає назву таблиці об’єднання (contacts_roles) і імена стовпців
    // зовнішнього ключа в таблиці об’єднання (CONTACT_ID і ROLE_ID).
    @JoinTable(
            name = "contacts_roles",
            joinColumns = {@JoinColumn(name = "CONTACT_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "ROLE_ID", referencedColumnName = "ID")})
    private List<Role> roles = new ArrayList<>();

}
