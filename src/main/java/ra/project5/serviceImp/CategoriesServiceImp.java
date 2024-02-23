package ra.project5.serviceImp;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ra.project5.advice.CustomException;
import ra.project5.model.dto.request.CategoriesPatchRequest;
import ra.project5.model.dto.request.CategoriesPutRequest;
import ra.project5.model.dto.request.CategoriesRequest;
import ra.project5.model.dto.response.CategoriesResponse;
import ra.project5.model.dto.response.UserResponse;
import ra.project5.model.entity.Categories;
import ra.project5.model.entity.User;
import ra.project5.repository.CategoriesRepository;
import ra.project5.service.CategoriesService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CategoriesServiceImp implements CategoriesService {
    @Autowired
    private CategoriesRepository categoriesRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Map<String,Object> findAll(Pageable pageable) {
        Page<Categories> pageCategories =categoriesRepository.findAll(pageable);
        int totalPage = pageCategories.getTotalPages();
        List<Categories> listCategories = pageCategories.getContent();
        List<CategoriesResponse> listCategoriesResponse = listCategories.stream()
                .map(categories -> modelMapper.map(categories, CategoriesResponse.class)).toList();
        Map<String,Object> data = new HashMap<>();
        data.put("totalPage", totalPage);
        data.put("categories",listCategoriesResponse);
        return data;
    }

    @Override
    public CategoriesResponse findById(long catalogId) throws CustomException {
        Optional<Categories> optCatalog = categoriesRepository.findById(catalogId);
        if (optCatalog.isPresent()){
            return modelMapper.map(optCatalog.get(), CategoriesResponse.class);
        }
       throw new CustomException("CatalogId not found");
    }

    /**
     * @param categoriesRequest
     * @return
     * @throws CustomException
     */
    @Override
    public CategoriesResponse save(CategoriesRequest categoriesRequest) throws CustomException {

        if (categoriesRepository.existsByCatalogName(categoriesRequest.getCatalogName())){
            throw new CustomException("CatalogName exist");
        }
        categoriesRequest.setCatalogStatus(true);
        Categories categories = categoriesRepository.save(modelMapper.map(categoriesRequest, Categories.class));
        return modelMapper.map(categories, CategoriesResponse.class);
    }

    @Override
    public CategoriesResponse update(CategoriesPutRequest categoriesPutRequest) throws CustomException {
        Categories categories = categoriesRepository.findById(categoriesPutRequest.getCatalogId())
                .orElseThrow(()-> new CustomException("CatalogId not found"));

        if (!categories.getCatalogName().equalsIgnoreCase(categoriesPutRequest.getCatalogName()) && categoriesRepository.existsByCatalogName(categoriesPutRequest.getCatalogName())){
            throw new CustomException("CatalogName exist");
        }
            Categories categoriesUpdate = categoriesRepository.save(modelMapper.map(categoriesPutRequest, Categories.class));
            return  modelMapper.map(categoriesUpdate, CategoriesResponse.class);

    }

    @Override
    public CategoriesResponse block(CategoriesPatchRequest categoriesPatchRequest) throws CustomException {
        boolean checkExist = categoriesRepository.existsById(categoriesPatchRequest.getCatalogId());
        if (checkExist){
            Categories categoriesBlock = categoriesRepository.findById(categoriesPatchRequest.getCatalogId())
                    .orElseThrow(()-> new CustomException("Catalog not found"));
            categoriesBlock.setCatalogStatus(!categoriesBlock.isCatalogStatus());
            return modelMapper.map(categoriesRepository.save(categoriesBlock), CategoriesResponse.class);
        }
        throw new CustomException("CatalogId not found");
    }

    @Override
    public List<CategoriesResponse> findAllStatus() {
        List<Categories> listCategories = categoriesRepository.findAllByCatalogStatusTrue();
        List<CategoriesResponse> listCategoriesResponse = listCategories.stream()
                .map(categories -> modelMapper.map(categories, CategoriesResponse.class)).toList();
        return listCategoriesResponse;
    }
}
