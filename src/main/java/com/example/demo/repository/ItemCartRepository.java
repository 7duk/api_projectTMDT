package com.example.demo.repository;

import com.example.demo.entity.ItemCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemCartRepository extends JpaRepository<ItemCart,Integer> {
    @Query(value = "SELECT * FROM item_cart WHERE item_cart.USER_ID = :USER_ID AND item_cart.IS_DELETE = :IS_DELETE",nativeQuery = true)
    List<ItemCart> getCartsByUser(@Param("USER_ID") Integer userId, @Param("IS_DELETE") Integer isDelete);
    @Modifying
    @Query(value = "INSERT INTO item_cart(USER_ID,ITEM_DETAIL_ID,QUANTITY) VALUES(:USER_ID,:ITEM_DETAIL_ID,:QUANTITY)" ,nativeQuery = true)
    void saveItemInCart(@Param("USER_ID") Integer userId,@Param("ITEM_DETAIL_ID") Integer itemDetailId,@Param("QUANTITY") Integer quantity);
//    @Modifying
//    @Query(value = "UPDATE item_cart SET item_cart.IS_DELETE = :IS_DELETE WHERE item_cart.ID = :ID",nativeQuery = true)
//    Integer removeItemInCarts(@Param("IS_DELETE") Integer isDelete,@Param("ID") Integer id);
    @Modifying
    @Query(value = "DELETE FROM item_cart  WHERE item_cart.ID = :ID",nativeQuery = true)
    Integer removeItemInCarts(@Param("ID") Integer id);
    @Modifying
    @Query(value = "DELETE FROM item_cart  WHERE item_cart.USER_ID = :USER_ID",nativeQuery = true)
    void removeAllItemInCartsByUserID(@Param("USER_ID")Integer userDd);
}
