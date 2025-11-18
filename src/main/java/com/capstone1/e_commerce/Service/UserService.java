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
    private final MerchantStockService merchantStockService;

    //crud
    public ArrayList<User> getUsers(){
        return users;
    }

    public boolean createUser(User user){
        if(userExist(user.getId()) != null){
            return false; // user already exists
        }
        users.add(user);
        return true;
    }

    public boolean updateUser(String id, User user){
        if(!id.equalsIgnoreCase(user.getId())){
            return false; //user id doesn't match entered id
        }
        for (int i = 0; i<users.size(); i++){
            if(id.equalsIgnoreCase(users.get(i).getId())){
                users.set(i, user);
                return true;
            }
        }
        return false; // user not found
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
    public User userExist(String id){
        return users.stream().filter(e-> e.getId().equalsIgnoreCase(id)).findFirst().orElse(null);
    }

    //extra
    public int buyProduct(String productID, String merchantID, String userID) {
        Product product = productService.productExist(productID);
        if(product == null){
            return -3; // no product
        }
        for (int i = 0; i < users.size() ; i++) {
            if(users.get(i).getId().equalsIgnoreCase(userID)){
                if(users.get(i).getBalance() >= product.getPrice()){
                    int stockStatus = merchantStockService.stockHandle(merchantID, productID,true);
                    if(stockStatus == 1){
                        users.get(i).setBalance(users.get(i).getBalance() - product.getPrice());
                        return 1;
                    }else return stockStatus; // -5 no stock , -2 no merchant found
                }
                return -1; // no balance
            }
        }
        return -4; // no user found
    }

    public int addBalance(String id, double balance){
        if(balance <=0){
            return -1;
        }
        for (int i = 0; i < users.size() ; i++) {
            if(users.get(i).getId().equalsIgnoreCase(id)){
                users.get(i).setBalance(users.get(i).getBalance() + balance);
                return 1;
            }
        }
        return -4; // no user found
    }

    public int returnProduct(String productID, String merchantID, String userID){
        Product product = productService.productExist(productID);
        if(product == null){
            return -3; // no product
        }
        for (int i = 0; i < users.size(); i++) {
            if(users.get(i).getId().equalsIgnoreCase(userID)){
                int stockStatus = merchantStockService.stockHandle(merchantID, productID,false);
                if(stockStatus == 1){
                    users.get(i).setBalance(users.get(i).getBalance() + product.getPrice());
                    return 1;
                }else return stockStatus; //  -2 no merchant found
            }
        }
        return -4; // no user found
    }


//    public ArrayList<Product> findCheapestItem(){}

}
