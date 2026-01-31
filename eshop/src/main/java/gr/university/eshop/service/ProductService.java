package gr.university.eshop.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import gr.university.eshop.model.Product;
import gr.university.eshop.repository.ProductRepository;
import gr.university.eshop.repository.specification.ProductSpecification;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepo;

    public Page<Product> search(
        String type,
        String brand,
        String description,
        Double minPrice,
        Double maxPrice,
        int page,
        int size //size here, refers to products per page
    ) {
        Specification<Product> specification = 
            Specification.where(ProductSpecification.descriptionContains(description))
                .and(ProductSpecification.brandContains(brand))
                .and(ProductSpecification.typeContains(type))
                .and(ProductSpecification.minPrice(minPrice))
                .and(ProductSpecification.maxPrice(maxPrice));
        
        //sort results by price and id, meaning the more expensive newer products come first
        Sort sort = Sort.by(
            Sort.Order.desc("price"),
            Sort.Order.desc("id")
        );

        Pageable pageable = PageRequest.of(page, size, sort);

        return productRepo.findAll(specification, pageable);
    }

}
