package ra.project5.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.project5.advice.CustomException;
import ra.project5.model.dto.request.CategoriesPatchRequest;
import ra.project5.model.dto.request.CategoriesPutRequest;
import ra.project5.model.dto.request.CategoriesRequest;
import ra.project5.model.dto.response.CategoriesResponse;
import ra.project5.service.CategoriesService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/admin/categories")

public class AdminCategoriesController {
    @Autowired
    private CategoriesService categoriesService;

    @GetMapping
    public ResponseEntity<Map<String,Object>> findAll(

            @PageableDefault(page = 0,size = 3,sort = "catalogName", direction = Sort.Direction.ASC)Pageable pageable) {
        return new ResponseEntity<>(categoriesService.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping("/{catalogId}")
    public ResponseEntity<CategoriesResponse> findById(@PathVariable Long catalogId) throws CustomException {
        return new ResponseEntity<>(categoriesService.findById(catalogId),HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CategoriesResponse> save(@Valid @RequestBody CategoriesRequest categoriesRequest) throws CustomException {
        return  new ResponseEntity<>(categoriesService.save(categoriesRequest),HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<CategoriesResponse> update(@Valid @RequestBody CategoriesPutRequest categoriesPutRequest) throws CustomException {
        return new ResponseEntity<>(categoriesService.update(categoriesPutRequest),HttpStatus.OK);
    }

    @PatchMapping
    public ResponseEntity<CategoriesResponse> block(@RequestBody CategoriesPatchRequest categoriesPatchRequest) throws CustomException {
        return new ResponseEntity<>(categoriesService.block(categoriesPatchRequest), HttpStatus.OK);
    }
}
