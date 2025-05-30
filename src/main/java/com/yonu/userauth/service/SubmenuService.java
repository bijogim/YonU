package com.yonu.userauth.service;

import com.yonu.userauth.domain.Submenu;
import com.yonu.userauth.repository.SubmenuRepository;
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
