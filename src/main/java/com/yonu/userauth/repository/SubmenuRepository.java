package com.yonu.userauth.repository;

import com.yonu.userauth.domain.Submenu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubmenuRepository extends JpaRepository<Submenu, Integer> {
    List<Submenu> findByMenuId(int menuId);
}
