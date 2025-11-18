package com.capstone1.e_commerce.Controller;

import com.capstone1.e_commerce.Api.ApiResponse;
import com.capstone1.e_commerce.Model.Category;
import com.capstone1.e_commerce.Model.User;
import com.capstone1.e_commerce.Service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    // CRUD
    @GetMapping("/get-category")
    public ResponseEntity<?> getCategories(){
        ArrayList<Category> categories = categoryService.getCategories();
        if(categories.isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("please enter categories to show them"));
        }
        return ResponseEntity.status(200).body(categories);
    }

    @PostMapping("/create-category")
    public ResponseEntity<?> createCategory(@RequestBody @Valid Category category, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }
        if(categoryService.createCategory(category)) {
            return ResponseEntity.status(200).body(new ApiResponse("category created successfully"));
        }return ResponseEntity.status(400).body(new ApiResponse("category already exist"));
    }

    @PutMapping("/update-category/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable String id, @RequestBody @Valid Category category, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }
        if (categoryService.updateCategory(id, category)) {
            return ResponseEntity.status(200).body(new ApiResponse("category updated successfully"));
        }return ResponseEntity.status(400).body(new ApiResponse("category id doesn't exist"));
    }

    @DeleteMapping("/delete-category/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable String id){
        if(categoryService.deleteCategory(id)){
            return ResponseEntity.status(200).body(new ApiResponse("category deleted successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("category id doesn't exist"));
    }

    //extra


}
