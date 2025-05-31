package com.example.demo.controller;

import com.example.demo.domain.Submenu;
import com.example.demo.repository.SubmenuRepository;
import com.example.demo.service.SubmenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/submenus")
public class SubmenuController {

    @Autowired
    private SubmenuService submenuService;

    @Autowired
    private SubmenuRepository submenuRepository;

    // ✅ 특정 menuId에 해당하는 Submenu 목록 조회
    @GetMapping("/{menuId}")
    public ResponseEntity<List<Submenu>> getSubmenus(@PathVariable int menuId) {
        List<Submenu> submenus = submenuService.getSubmenusByMenuId(menuId);
        return ResponseEntity.ok(submenus);
    }

    // ✅ 전체 Submenu 목록 조회
    @GetMapping
    public ResponseEntity<List<Submenu>> getAllSubmenus() {
        List<Submenu> submenus = submenuRepository.findAll();
        return ResponseEntity.ok(submenus);
    }
}
