package gr.university.eshop.repository.specification;

import org.springframework.data.jpa.domain.Specification;
import gr.university.eshop.model.Product;

public class ProductSpecification {

    public static Specification<Product> descriptionContains(String description) {
        return(root, query, criteriaBuilder) -> {
            if(description == null || description.isBlank()) {
                return null;
            }
            
            return criteriaBuilder.like(
                criteriaBuilder.lower(root.get("description")), 
                "%"+description.toLowerCase()+"%");
        };     
    }

    public static Specification<Product> brandContains(String brand) {
        return(root, query, criteriaBuilder) -> {
            if(brand == null || brand.isBlank()) {
                return null;
            }
            
            return criteriaBuilder.like(
                criteriaBuilder.lower(root.get("brand")), 
                "%"+brand.toLowerCase()+"%");
        };     
    }

    public static Specification<Product> typeContains(String type) {
        return(root, query, criteriaBuilder) -> {
            if(type == null || type.isBlank()) {
                return null;
            }
            
            return criteriaBuilder.like(
                criteriaBuilder.lower(root.get("type")), 
                "%"+type.toLowerCase()+"%");
        };
    }

   public static Specification<Product> minPrice(Double minPrice) {
        return(root, query, criteriaBuilder) ->
            minPrice == null ? null : criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice);  
   }
   
   public static Specification<Product> maxPrice(Double maxPrice) {
        return(root, query, criteriaBuilder) ->
            maxPrice == null ? null : criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice);  
   }
}
