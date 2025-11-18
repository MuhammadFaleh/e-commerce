package com.capstone1.e_commerce.Service;

import com.capstone1.e_commerce.Model.Category;
import com.capstone1.e_commerce.Model.Product;
import com.capstone1.e_commerce.Model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

@SuppressWarnings("LombokGetterMayBeUsed")
@Service
@RequiredArgsConstructor
public class ProductService {
    ArrayList<Product> products = new ArrayList<>();
    private final CategoryService categoryService;

    public ArrayList<Product> getProducts(){
        return products;
    }

    public int createProducts(Product product){
        if(productExist(product.getId()) != null){
            return 0; // product already exists
        }else if (categoryService.categoryExist(product.getCategoryID())) {
            products.add(product);
            return 1;
        }
        return 2; // category doesn't exist
    }

    public int updateProduct(String id, Product product){
        if(!id.equalsIgnoreCase(product.getId())){
            return 0; //product id doesn't match entered id
        }
        if(categoryService.categoryExist(product.getCategoryID())) {
            for (int i = 0; i < products.size(); i++) {
                if (id.equalsIgnoreCase(products.get(i).getId())) {
                    products.set(i, product);
                    return 1;
                }
            }
            return -2; // product not found
        }
        return -3; // category doesn't exist
    }

    public boolean deleteProduct(String id){
        for (int i = 0; i < products.size(); i++) {
            if(products.get(i).getId().equalsIgnoreCase(id)){
                products.remove(i);
                return true;
            }
        }
        return false;
    }

    //logic
    public Product productExist(String id){
        return products.stream().filter(e-> e.getId().equalsIgnoreCase(id)).findFirst().orElse(null);
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
    public ArrayList<Product> productCategoryPrice(double min, double max, String categoryID, String sort){
        ArrayList<Product> matchedProducts = new ArrayList<>();
        for (Product product : products){
            if(product.getCategoryID().equalsIgnoreCase(categoryID)){
                if(product.getPrice() >= min && product.getPrice() <=max){
                    matchedProducts.add(product);
                }
            }
        }

        return sortAscDes(sort, matchedProducts);
    }


}
