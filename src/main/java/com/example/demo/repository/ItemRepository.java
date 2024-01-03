package com.example.demo.repository;

import com.example.demo.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {
    String INSERT_ITEM = "INSERT INTO item(NAME, ITEM_GROUP_ID, BUY_PRICE, SELL_PRICE, DESCRIPTION, LAST_UPDATE_AT) " +
            "VALUES (:#{#item.getName()}, :#{#item.getIgId()}, :#{#item.getBuyPrice()}, :#{#item.getSellPrice()}, :#{#item.getDescription()}, :#{#item.getLastUpdateAt()})";


    @Query(value = "SELECT * FROM item WHERE item.ITEM_GROUP_ID = :igId AND item.IS_DELETE = :isDel LIMIT :LIMIT OFFSET :OFFSET", nativeQuery = true)
    List<Item> getItemsByIgIdAndIsDelete(@Param("igId") Integer igId, @Param("isDel") Integer isDel,@Param("LIMIT")Integer limit,@Param("OFFSET") Integer offSet);

    @Query(value = "SELECT COUNT(*) FROM item WHERE item.ITEM_GROUP_ID = :igId AND item.IS_DELETE = :isDel", nativeQuery = true)
    int getCountItemByItemGr(@Param("igId") Integer igId, @Param("isDel") Integer isDel);


    @Query(value = "SELECT * FROM item WHERE item.`NAME` LIKE %:search% AND item.IS_DELETE = :isDel", nativeQuery = true)
    List<Item> getItemsBySearchAndIsDelete(@Param("search") String search, @Param("isDel") Integer isDel);
    @Query(value = "SELECT * FROM item WHERE item.IS_DELETE = :isDel AND item.ITEM_GROUP_ID <> 9 ORDER BY item.DISCOUNT DESC LIMIT 24", nativeQuery = true)
    List<Item> getItemByDiscountsNo9AndIsDelete(@Param("isDel") Integer isDel);

    @Query(value = "SELECT * FROM item WHERE item.IS_DELETE = :isDel AND item.ITEM_GROUP_ID = 9 ORDER BY item.DISCOUNT DESC LIMIT 6", nativeQuery = true)
    List<Item> getItemByDiscounts9AndIsDelete(@Param("isDel") Integer isDel);

    @Query(value = "SELECT * FROM item WHERE item.IS_DELETE = :isDel ORDER BY item.DISCOUNT DESC LIMIT 24", nativeQuery = true)
    List<Item> getItemByDiscountsAndIsDelete(@Param("isDel") Integer isDel);

    @Query(value = "SELECT * FROM item WHERE item.IS_DELETE = :isDel ORDER BY item.LAST_UPDATE_AT DESC LIMIT 24", nativeQuery = true)
    List<Item> getItemNewByDayAndIsDelete(@Param("isDel") Integer isDel);

    @Modifying
    @Query(value = INSERT_ITEM, nativeQuery = true)
    void saveItem(@Param("item") Item item);

    @Modifying
    @Query(value = "UPDATE item SET `NAME` = :#{#item.getName()}, " +
            "ITEM_GROUP_ID = :#{#item.getIgId()}, " +
            "BUY_PRICE = :#{#item.getBuyPrice()}, " +
            "SELL_PRICE = :#{#item.getSellPrice()}, " +
            "DESCRIPTION = :#{#item.getDescription()}, " +
            "LAST_UPDATE_AT = :#{#item.getLastUpdateAt()} " +
            "WHERE ID = :itemId", nativeQuery = true)
    void updateItem(@Param("item") Item item,@Param("itemId") Integer itemId);

    @Modifying
    @Query(value = "UPDATE item  SET item.IS_DELETE = 1 WHERE item.ID =:id",nativeQuery = true)
    int deleteItem(@Param("id") Integer itemId);

    @Modifying
    @Query(value = "UPDATE item  SET item.DISCOUNT = :discount,item.LAST_UPDATE_AT= :lastUpAt WHERE item.ID =:id",nativeQuery = true)
    int updateDiscountItem(@Param("id") Integer itemId, @Param("discount")Integer discount,@Param("lastUpAt") LocalDateTime lastUpAt);

    @Modifying
    @Query(value = "UPDATE item  SET item.DISCOUNT = :discount,item.LAST_UPDATE_AT= :lastUpAt WHERE item.ITEM_GROUP_ID =:igID",nativeQuery = true)
    void updateDiscountItemsWithItemGroupId(@Param("igID") Integer igID, @Param("discount")Integer discount,@Param("lastUpAt") LocalDateTime lastUpAt);

    @Modifying
    @Query(value = "UPDATE item  SET item.NAME = :name,item.LAST_UPDATE_AT= :lastUpAt WHERE item.ID =:id",nativeQuery = true)
    int updateNameItem(@Param("id") Integer itemId, @Param("name")String name,@Param("lastUpAt")LocalDateTime lastUpAt);

    @Modifying
    @Query(value = "UPDATE item  SET item.DESCRIPTION = :description,item.LAST_UPDATE_AT= :lastUpAt WHERE item.ID =:id",nativeQuery = true)
    int updateDescriptionItem(@Param("id") Integer itemId, @Param("description")String name,@Param("lastUpAt")LocalDateTime lastUpAt);

    @Query(value = "SELECT ID FROM item WHERE item.NAME = :name",nativeQuery = true)
    Integer getIdByName(@Param("name") String name);

    @Query(value = "SELECT * FROM item WHERE item.id = :id",nativeQuery = true)
    Optional<Item> getItemById(@Param("id") Integer id);

    @Query(value = "SELECT * FROM item WHERE item.`NAME` LIKE %:search% AND item.IS_DELETE = :isDel", nativeQuery = true)
    List<Item> getAllItemsBySearchAndIsDelete(@Param("search") String search, @Param("isDel") Integer isDel);

}
