package ru.skypro.homework.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import uk.co.jemos.podam.common.PodamExclude;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "comments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk", nullable = false)
    private Integer pk;
    @Column(name = "author_image")
    private String authorImage;
    @Column(name = "author_first_name", nullable = false)
    private String authorFirstName;
    @Column(name = "created_at")
    @PodamExclude
    private Instant createdAt;
    @Column(name = "text", nullable = false)
    private String text;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @PodamExclude
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "ad_id")
    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @PodamExclude
    private AdEntity ad;
}
