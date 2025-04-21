package ru.skypro.homework.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "ads")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdEntity {

    @NonNull
    private Integer author;
    @NonNull
    private String image;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pk;
    @NonNull
    private Integer price;
    @NonNull
    private String title;
    @NonNull
    private String description;
}
