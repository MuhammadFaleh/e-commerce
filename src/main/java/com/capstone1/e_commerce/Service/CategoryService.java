package com.capstone1.e_commerce.Service;

import com.capstone1.e_commerce.Model.Category;
import com.capstone1.e_commerce.Model.Merchant;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class CategoryService {
    ArrayList<Category> categories = new ArrayList<>();

    public ArrayList<Category> getCategories(){
        return categories;
    }

    public boolean createCategory(Category category){
        if(categoryExist(category.getId())){
            return false; // category already exists
        }
        categories.add(category);
        return true;
    }

    public boolean updateCategory(String id, Category category){
        if(!id.equalsIgnoreCase(category.getId())){
            return false; // Category id doesn't match entered id
        }
        for (int i = 0; i< categories.size(); i++){
            if(id.equalsIgnoreCase(categories.get(i).getId())){
                categories.set(i, category);
                return true;
            }
        }
        return false; // category not found
    }

    public boolean deleteCategory(String id){
        for (int i = 0; i < categories.size(); i++) {
            if(categories.get(i).getId().equalsIgnoreCase(id)){
                categories.remove(i);
                return true;
            }
        }
        return false;
    }

    //logic
    public boolean categoryExist(String id){
        return categories.stream().anyMatch(e-> e.getId().equalsIgnoreCase(id));
    }

    public Category getCategoryByID(String id){
        return categories.stream().filter(e-> e.getId().equalsIgnoreCase(id)).findFirst().orElse(null);
    }
    //extra

}
