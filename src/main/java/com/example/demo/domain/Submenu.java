package com.example.demo.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Submenu") // 테이블 이름 명시
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
