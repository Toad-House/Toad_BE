package toad.toad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import toad.toad.service.MaterialCompanyService;

@Controller
@RequestMapping("/company")
public class MaterialCompanyController {
    private final MaterialCompanyService materialCompanyService;

    @Autowired
    public MaterialCompanyController (MaterialCompanyService materialCompanyService) {
        this.materialCompanyService = materialCompanyService;
    }


}
