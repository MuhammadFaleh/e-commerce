package com.capstone1.e_commerce.Service;

import com.capstone1.e_commerce.Model.Category;
import com.capstone1.e_commerce.Model.Product;
import com.capstone1.e_commerce.Model.User;
import com.capstone1.e_commerce.Repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

@SuppressWarnings("LombokGetterMayBeUsed")
@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository products;
    private final CategoryService categoryService;

    public List<Product> getProducts(){
        return products.findAll();
    }

    public int createProducts(Product product){
        if(productExist(product.getId()) != null){
            return 0; // product already exists
        }else if (categoryService.getCategoryByID(product.getCategoryID()) != null) {
            products.save(product);
            return 1;
        }
        return 2; // category doesn't exist
    }

    public int updateProduct(Integer id, Product product){
        if(categoryService.getCategoryByID(product.getCategoryID()) != null ) {
            Product p = productExist(product.getId());
            if(p != null){
                p.setName(product.getName());
                p.setPrice(product.getPrice());
                p.setCategoryID(product.getCategoryID());
                products.save(p);
                return 1;
            }
            return -2; // product not found
        }
        return -3; // category doesn't exist
    }

    public boolean deleteProduct(Integer id){
        Product product = productExist(id);
        if(product !=null){
            products.delete(product);
            return true;
        }
        return false;
    }

    //logic
    public Product productExist(Integer id){
        return products.findAll().stream().filter(e-> e.getId().equals(id)).findFirst().orElse(null);
    }

    public ArrayList<Product> sortAscDes(String sort, ArrayList<Product> matchedProducts){
        if(sort.equalsIgnoreCase("asc")){ // low -> high
            matchedProducts.sort(Comparator.comparingDouble(Product::getPrice));
        }else if(sort.equalsIgnoreCase("des")){ // high -> low
            matchedProducts.sort(Comparator.comparingDouble(Product::getPrice).reversed());
        }

        return matchedProducts;
    }


    // extra
    public ArrayList<Product> productCategoryPrice(double min, double max, Integer categoryID, String sort){
        ArrayList<Product> matchedProducts = new ArrayList<>();
        for (Product product : products.findAll()){
            if(product.getCategoryID().equals(categoryID)){
                if(product.getPrice() >= min && product.getPrice() <=max){
                    matchedProducts.add(product);
                }
            }
        }

        return sortAscDes(sort, matchedProducts);
    }


}
