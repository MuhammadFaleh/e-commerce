package com.capstone1.e_commerce.Service;

// case 1: success
// case 2: no merchant
// case 3: no product
// case 4: no user
// case 5: no stock


import com.capstone1.e_commerce.Model.Product;
import com.capstone1.e_commerce.Model.User;
import com.capstone1.e_commerce.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("LombokGetterMayBeUsed")
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository users;

    private final ProductService productService;
    private final MerchantStockService merchantStockService;

    //crud
    public List<User> getUsers(){
        return users.findAll();
    }

    public boolean createUser(User user){
        if(userExist(user.getId()) != null){
            return false; // user already exists
        }
        users.save(user);
        return true;
    }

    public boolean updateUser(Integer id, User user){
        User u = userExist(id);
        if(u != null){
            u.setUsername(user.getUsername());
            u.setBalance(user.getBalance());
            u.setEmail(user.getEmail());
            u.setPassword(user.getPassword());
            u.setRole(user.getRole());
            users.save(u);
            return true;
        }
        return false; // user not found
    }

    public boolean deleteUser(Integer id){
        User user = userExist(id);
        if(user != null){
            users.delete(user);
            return true;
        }
        return false;
    }

    //logic
    public User userExist(Integer id){
        return users.findAll().stream().filter(e-> e.getId().equals(id)).findFirst().orElse(null);
    }

    //extra
    public int buyProduct(Integer productID, Integer merchantID, Integer userID) {
        Product product = productService.productExist(productID);
        if(product == null){
            return -3; // no product
        }
        User user = userExist(userID);
        if(user != null){
            if(user.getBalance() >= product.getPrice()){
                int stockStatus = merchantStockService.stockHandle(merchantID, productID,true);
                if(stockStatus == 1){
                    user.setBalance(user.getBalance()- product.getPrice());
                    users.save(user);
                    return 1;
                }else return stockStatus;
            }
            return -1;
        }
        return -4; // no user found
    }

    public int addBalance(Integer id, double balance){
        if(balance <=0){
            return -1;
        }
        User user = userExist(id);
        if(user != null){
            user.setBalance(user.getBalance() + balance);
            users.save(user);
            return 1;
        }
        return -4; // no user found
    }

    public int returnProduct(Integer productID, Integer merchantID, Integer userID){
        Product product = productService.productExist(productID);
        if(product == null){
            return -3; // no product
        }
        User user = userExist(userID);
        if(user != null){
            int stockStatus = merchantStockService.stockHandle(merchantID, productID,false);
            if(stockStatus == 1){
                user.setBalance(user.getBalance() + product.getPrice());
                users.save(user);
                return 1;
            }else return stockStatus; //  -2 no merchant found
        }
        return -4; // no user found
    }


//    public ArrayList<Product> findCheapestItem(){}

}
