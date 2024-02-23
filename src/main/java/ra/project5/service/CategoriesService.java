package ra.project5.service;

import org.springframework.data.domain.Pageable;
import ra.project5.advice.CustomException;
import ra.project5.model.dto.request.CategoriesPatchRequest;
import ra.project5.model.dto.request.CategoriesPutRequest;
import ra.project5.model.dto.request.CategoriesRequest;
import ra.project5.model.dto.response.CategoriesResponse;

import java.util.List;
import java.util.Map;

public interface CategoriesService {
    Map<String,Object> findAll(Pageable pageable);
    CategoriesResponse findById(long catalogId) throws CustomException;
    CategoriesResponse save (CategoriesRequest categoriesRequest) throws CustomException;
    CategoriesResponse update(CategoriesPutRequest categoriesPutRequest) throws CustomException;
    CategoriesResponse block(CategoriesPatchRequest categoriesPatchRequest) throws CustomException;
    List<CategoriesResponse> findAllStatus();

}
