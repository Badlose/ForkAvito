package ru.skypro.homework.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "comments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentEntity {

    @NonNull
    private Integer author;
    @NonNull
    private String authorImage;
    @NonNull
    @Size(min = 3, max = 10)
    private String authorFirstName;
    @NonNull
    private Long createdAt;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pk;
    @NonNull
    private String text;
}
