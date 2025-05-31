package com.example.demo.repository;

import com.example.demo.domain.Submenu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubmenuRepository extends JpaRepository<Submenu, Integer> {
    List<Submenu> findByMenuId(int menuId);
}
