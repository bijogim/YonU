package com.yonu.community.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Menu")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Menu {

    @Id
    private Integer menuId;

    @Column(nullable = false)
    private String name;
}
