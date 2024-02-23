package ra.project5.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ra.project5.model.dto.response.CategoriesResponse;
import ra.project5.service.CategoriesService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/catalogs")
public class PermitAllCatalogsController {
    @Autowired
    private CategoriesService categoriesService;
    @GetMapping
    public ResponseEntity<List<CategoriesResponse>> findAllByStatus(){
        return new ResponseEntity<>(categoriesService.findAllStatus(), HttpStatus.OK);
    }
}
