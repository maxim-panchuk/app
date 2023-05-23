package com.mpanchuk.app.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "item_to_add")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ItemToAdd {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private Integer price;

    @Column(name = "city")
    private String cityName;

}
