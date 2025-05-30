package com.yonu.community.controller;

import com.yonu.community.entity.Submenu;
import com.yonu.community.repository.SubmenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/submenus")
public class SubmenuController {

    private final SubmenuRepository submenuRepository;

    @Autowired
    public SubmenuController(SubmenuRepository submenuRepository) {
        this.submenuRepository = submenuRepository;
    }

    @GetMapping
    public List<Submenu> getAllSubmenus() {
        return submenuRepository.findAll();
    }
}