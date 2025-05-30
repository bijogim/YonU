package com.example.demo.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Submenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int submenuId;

    private int menuId;

    private String name;
}