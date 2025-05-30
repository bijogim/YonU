package com.yonu.community.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Submenu")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Submenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer submenuId;

    @Column(nullable = false)
    private Integer menuId;

    @Column(nullable = false)
    private String name;
}