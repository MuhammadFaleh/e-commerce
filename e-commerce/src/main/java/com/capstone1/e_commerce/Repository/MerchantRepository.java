package com.capstone1.e_commerce.Repository;

import com.capstone1.e_commerce.Model.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MerchantRepository extends JpaRepository<Merchant,Integer> {
}
