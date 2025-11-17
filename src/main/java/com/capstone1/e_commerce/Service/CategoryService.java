package com.capstone1.e_commerce.Service;

import com.capstone1.e_commerce.Model.Category;
import com.capstone1.e_commerce.Model.Merchant;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

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

    public int updateCategory(String id, Category category){
        if(!id.equalsIgnoreCase(category.getId())){
            return 0; // Category id doesn't match entered id
        }
        for (int i = 0; i< categories.size(); i++){
            if(id.equalsIgnoreCase(categories.get(i).getId())){
                categories.set(i, category);
                return 1;
            }
        }
        return 2; // category not found
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
}
