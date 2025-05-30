package com.example.demo.controller;

import com.example.demo.domain.Submenu;
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

    @GetMapping("/{menuId}")
    public ResponseEntity<List<Submenu>> getSubmenus(@PathVariable int menuId) {
        List<Submenu> submenus = submenuService.getSubmenusByMenuId(menuId);
        return ResponseEntity.ok(submenus);
    }
}
