package com.capstone1.e_commerce.Controller;

import com.capstone1.e_commerce.Api.ApiResponse;
import com.capstone1.e_commerce.Model.MerchantStock;
import com.capstone1.e_commerce.Model.Product;
import com.capstone1.e_commerce.Service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class productController {
    private final ProductService productService;

    //crud
    @GetMapping("/get-products")
    public ResponseEntity<?> getProducts(){
        ArrayList<Product> products = productService.getProducts();
        if(products.isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("no products entered yet"));
        }
        return ResponseEntity.status(200).body(products);
    }

    @PostMapping("/create-product")
    public ResponseEntity<?> createProduct(@RequestBody @Valid Product product, Errors errors){
        // 0 exist / 2 no category 1 good
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }
        int status = productService.createProducts(product);
        switch (status){
            case 1->{return ResponseEntity.status(200).body(new ApiResponse("product created successfully"));}
            case 2->{return ResponseEntity.status(400).body(new ApiResponse("category doesn't exist"));}
            default -> {return ResponseEntity.status(400).body(new ApiResponse("product already exist"));}
        }
    }

    @PutMapping("/update-product/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable String id, @RequestBody @Valid Product product, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }
        int status = productService.updateProduct(id, product);
        switch (status){
            case 1-> {return ResponseEntity.status(200).body(new ApiResponse("product updated successfully"));}
            case -2->{return ResponseEntity.status(400).body(new ApiResponse("product doesn't exist"));}
            case -3->{return ResponseEntity.status(400).body(new ApiResponse("category doesn't exist"));}
            default -> {return ResponseEntity.status(400).body(new ApiResponse("look up id doesn't match product id"));}
        }
    }

    @DeleteMapping("/delete-product/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable String id){
        if(productService.deleteProduct(id)){
            return ResponseEntity.status(200).body(new ApiResponse("product deleted successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("product id wasn't found"));
    }

    //extra
    @GetMapping("/get-product-range-category/{min}/{max}/{categoryID}/{sort}")
    public ResponseEntity<?> getProductByRangeCategory(@PathVariable double min , @PathVariable double max,
                                                       @PathVariable String categoryID, @PathVariable String sort){
        ArrayList<Product> products = productService.productCategoryPrice(min,max,categoryID, sort);
        if(products.isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("no products found"));
        }
        return ResponseEntity.status(200).body(products);
    }
}
