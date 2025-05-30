package com.example.demo.service;

import com.example.demo.domain.Submenu;
import com.example.demo.repository.SubmenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubmenuService {

    @Autowired
    private SubmenuRepository submenuRepository;

    public List<Submenu> getSubmenusByMenuId(int menuId) {
        return submenuRepository.findByMenuId(menuId);
    }
}