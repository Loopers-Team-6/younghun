package com.loopers.domain.catalog.brand.embeded;


import com.loopers.domain.catalog.product.Product;
import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Embeddable
@Getter
public class Products {
  @OneToMany(mappedBy = "brandId")
  private List<Product> products;

  protected Products() {
  }

  private Products(List<Product> products) {
    this.products = products;
  }

  public static Products of() {
    return new Products(new ArrayList<>());
  }

  public static Products of(List<Product> products) {
    return new Products(products);
  }

  public void add(Product product) {
    this.products.add(product);
  }

}
