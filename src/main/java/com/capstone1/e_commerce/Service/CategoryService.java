package com.capstone1.e_commerce.Service;

import com.capstone1.e_commerce.Model.Category;
import com.capstone1.e_commerce.Model.Merchant;
import com.capstone1.e_commerce.Repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categories;

    public List<Category> getCategories(){
        return categories.findAll();
    }

    public boolean createCategory(Category category){
        if(getCategoryByID(category.getId()) != null){
            return false; // category already exists
        }
        categories.save(category);
        return true;
    }

    public boolean updateCategory(Integer id, Category category){
        Category category1 = getCategoryByID(id);
        if(category1 != null){
            category1.setName(category.getName());
            categories.save(category1);
            return true;
        }
        return false; // category not found
    }

    public boolean deleteCategory(Integer id){
        Category category1 = getCategoryByID(id);
        if(category1 != null){
            categories.delete(category1);
            return true;
        }
        return false; // category not found
    }

    public Category getCategoryByID(Integer id){
        if(id == null){
            return null;
        }
        return categories.findAll().stream().filter(e-> e.getId().equals(id)).findFirst().orElse(null);
    }
    //extra

}
