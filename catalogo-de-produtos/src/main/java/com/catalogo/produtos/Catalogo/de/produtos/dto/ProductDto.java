package com.catalogo.produtos.Catalogo.de.produtos.dto;

import com.catalogo.produtos.Catalogo.de.produtos.models.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    @NotBlank(message = "name must not be blank")
    private String name;

    @NotBlank(message = "description must not be blank")
    private String description;

    @NotNull(message = "price is required")
    @Positive(message = "price must be positive")
    private BigDecimal price;


    public Product dtoToProduct() {
        return new Product(name, description, price);
    }
}
