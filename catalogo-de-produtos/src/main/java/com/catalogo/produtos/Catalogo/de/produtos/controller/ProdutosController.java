package com.catalogo.produtos.Catalogo.de.produtos.controller;

import com.catalogo.produtos.Catalogo.de.produtos.dto.ProductDto;
import com.catalogo.produtos.Catalogo.de.produtos.models.Product;
import com.catalogo.produtos.Catalogo.de.produtos.service.ProductService;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("products")
public class ProdutosController {

    @Autowired
    ProductService productService;

    @GetMapping("/{id}")
    public ResponseEntity<Product> findById(@PathVariable Long id) {
        var product = productService.findById(id);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(product);
    }

    @GetMapping
    public ResponseEntity<List<Product>> find() {
        return ResponseEntity.ok(productService.find());
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<Product> create(@RequestBody @Valid ProductDto dto) {
        Product product = dto.dtoToProduct();
        Product productSaved = productService.create(product);
        var uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .buildAndExpand(productSaved.getId()).toUri();

        return ResponseEntity.created(uri).body(productSaved);
    }

    @PutMapping("{id}")
    public ResponseEntity<Product> update(@PathVariable Long id, @Valid @RequestBody ProductDto productDto) {
        var product = productService.findById(id);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        Product productSaved = productService.update(productDto, product);
        return ResponseEntity.ok(productSaved);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Product> delete(@PathVariable Long id) {
        var product = productService.findById(id);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        productService.delete(product);
        return ResponseEntity.ok().build();
    }

    @GetMapping("search")
    public ResponseEntity<List<Product>> search(@RequestParam(required = false) BigDecimal min_price, @RequestParam(required = false) BigDecimal max_price, @RequestParam(required = false) String q) {
        List<Product> products = productService.search(min_price, max_price, q);
       if (products.isEmpty()) {
           return ResponseEntity.notFound().build();
       }
       return ResponseEntity.ok().body(products);
    }
}
