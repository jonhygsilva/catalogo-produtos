package com.catalogo.produtos.Catalogo.de.produtos.service;

import com.catalogo.produtos.Catalogo.de.produtos.dto.ProductDto;
import com.catalogo.produtos.Catalogo.de.produtos.models.Product;
import com.catalogo.produtos.Catalogo.de.produtos.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    private void setProduct(ProductDto productDto, Product product) {
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
    }

    public Product create(Product product) {
        return productRepository.save(product);
    }

    public List<Product> find() {
        return productRepository.findAll();
    }

    public Product findById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public void delete(Product product) {
        productRepository.delete(product);
    }

    public Product update(ProductDto productDto, Product product) {
        setProduct(productDto, product);
        return productRepository.save(product);
    }

    public List<Product> search(Long min_price, Long max_price, String q) {
        return  productRepository.findAll((Specification<Product>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (min_price != null) {
                predicates.add(criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), min_price)));
            }
            if (max_price != null) {
                predicates.add(criteriaBuilder.and(criteriaBuilder.lessThanOrEqualTo(root.get("price"), max_price)));
            }
            if (q != null) {
                predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("name"), q)));
                predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("description"), q)));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }
}
