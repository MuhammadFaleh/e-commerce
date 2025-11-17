package com.capstone1.e_commerce.Service;

// case 1: success
// case 2: no merchant
// case 3: no product
// case 4: no user
// case 5: no stock


import com.capstone1.e_commerce.Model.Product;
import com.capstone1.e_commerce.Model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@SuppressWarnings("LombokGetterMayBeUsed")
@Service
@RequiredArgsConstructor
public class UserService {
    ArrayList<User> users = new ArrayList<>();
    private final ProductService productService;
    private final MerchantService merchantService;
    private final MerchantStockService merchantStockService;
    //crud
    public ArrayList<User> getUsers(){
        return users;
    }

    public boolean createUser(User user){
        if(userExist(user.getId())){
            return false; // user already exists
        }
        users.add(user);
        return true;
    }

    public int updateUser(String id, User user){
        if(!id.equalsIgnoreCase(user.getId())){
            return 0; //user id doesn't match entered id
        }
        for (int i = 0; i<users.size(); i++){
            if(id.equalsIgnoreCase(users.get(i).getId())){
                users.set(i, user);
                return 1;
            }
        }
        return 2; // user not found
    }

    public boolean deleteUser(String id){
        for (int i = 0; i < users.size(); i++) {
            if(users.get(i).getId().equalsIgnoreCase(id)){
                users.remove(i);
                return true;
            }
        }
        return false;
    }

    //logic
    public boolean userExist(String id){
        return users.stream().anyMatch(e-> e.getId().equalsIgnoreCase(id));
    }

    //extra
    public String buyProduct(String productID, String merchantID, String userID) {
        Product product = productService.productExist(productID);
        if(product == null){
            return "product";
        }
        for (int i = 0; i < users.size() ; i++) {
            if(users.get(i).getId().equalsIgnoreCase(userID)){
                if(users.get(i).getBalance() >= product.getPrice()){
                    if(merchantStockService.stockRemains(merchantID))
                }
            }
        }
    }

    public ArrayList<Product> findCheapestItem(){}

}
