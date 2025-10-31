package com.example.demo.service;

import com.example.demo.model.Customer;
import com.example.demo.model.FavoriteRestaurant;
import com.example.demo.model.UserInfo;
import com.example.demo.repository.CustomerRepo;
import com.example.demo.repository.FavoriteRestaurantRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FavoriteRestaurantService {

    @Autowired
    FavoriteRestaurantRepo favoriteRestaurantRepo;

    @Autowired
    CustomerRepo customerRepo;

    public FavoriteRestaurant addFavorite(int restaurantId) {
        Optional<Customer> opt = customerRepo.findById(UserInfo.customer_id);
        if (opt.isEmpty()) return null;
        List<FavoriteRestaurant> restaurant = favoriteRestaurantRepo.findByRestaurantId(restaurantId);
        if (!restaurant.isEmpty()) return null;
        FavoriteRestaurant favorite = new FavoriteRestaurant(opt.get(), restaurantId);
        return favoriteRestaurantRepo.save(favorite);
    }

    public boolean deleteFavoriteByRestaurantId(int restaurantId) {
        Optional<Customer> customerOpt = customerRepo.findById(UserInfo.customer_id);
        if (customerOpt.isEmpty()) return false;

        FavoriteRestaurant fav = favoriteRestaurantRepo.findByCustomerIdAndRestaurantId(customerOpt.get().getCustomer_id(), restaurantId);
        if (fav == null) return false;

        favoriteRestaurantRepo.delete(fav);
        return true;
    }

    public List<FavoriteRestaurant> allFavorites() {
        return favoriteRestaurantRepo.findByCustomerId(UserInfo.customer_id);
    }

    public FavoriteRestaurant updateFavorite(int favoriteId, int newRestaurantId) {
        Optional<FavoriteRestaurant> opt = favoriteRestaurantRepo.findById(favoriteId);
        if (opt.isEmpty()) return null;

        opt.get().setRestaurantId(newRestaurantId);
        favoriteRestaurantRepo.save(opt.get());
        return opt.get();
    }

    public boolean isFavorite(int restaurantId) {
        Optional<Customer> customerOpt = customerRepo.findById(UserInfo.customer_id);
        if (customerOpt.isEmpty()) return false;

        FavoriteRestaurant fav = favoriteRestaurantRepo.findByCustomerIdAndRestaurantId(customerOpt.get().getCustomer_id(), restaurantId);
        return fav !=null;
    }
}