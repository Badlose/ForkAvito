package ru.skypro.homework.entity;

import lombok.*;
import ru.skypro.homework.dto.Role;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    @Size(min = 3, max = 10)
    private String firstName;
    @NonNull
    @Size(min = 3, max = 10)
    private String lastName;
    @NonNull
    private String phone;
    @NonNull
    @Enumerated(EnumType.STRING)
    private Role role;

    private String image;

}
