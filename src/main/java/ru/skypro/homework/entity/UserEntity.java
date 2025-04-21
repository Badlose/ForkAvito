package ru.skypro.homework.entity;

import lombok.*;
import ru.skypro.homework.dto.Role;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    @Size(min = 4, max = 32)
    private String username;

    @NonNull
    @Size(min = 8, max = 16)
    private String password;

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

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<AdEntity> ads;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CommentEntity> comments;

}
