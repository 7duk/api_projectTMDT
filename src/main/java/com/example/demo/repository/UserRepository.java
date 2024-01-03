package com.example.demo.repository;


import com.example.demo.entity.Provider;
import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Integer> {
     String INSERT_USER = "INSERT INTO  user(FIRST_NAME,LAST_NAME,DATE_OF_BIRTH,ADDRESS,IMAGE,PHONE,GENDER,EMAIL,SALT,PASSWORD,ROLE_ID)\n " +
            "\tVALUES(:FIRST_NAME,:LAST_NAME,:DATE_OF_BIRTH,:ADDRESS,:IMAGE,:PHONE,:GENDER,:EMAIL,:SALT,:PASSWORD,:ROLE_ID) ";
    String INSERT_USER_GG ="INSERT INTO  user(FIRST_NAME,LAST_NAME,IMAGE,EMAIL,SALT,PASSWORD,ROLE_ID,STATUS_ACCOUNT,PROVIDER)\n " +
            "\tVALUES(:FIRST_NAME,:LAST_NAME,:IMAGE,:EMAIL,:SALT,:PASSWORD,:ROLE_ID,:STATUS_ACCOUNT,:PROVIDER) ";
     @Modifying
    @Query(value = INSERT_USER,nativeQuery = true)
    void saveUser(@Param("FIRST_NAME") String firstName,
                    @Param("LAST_NAME") String lastName,
                    @Param("DATE_OF_BIRTH")LocalDate dateOfBirth,
                    @Param("ADDRESS") String address,
                    @Param("IMAGE") String image,
                    @Param("PHONE") String phone,
                    @Param("GENDER") Integer gender,
                    @Param("EMAIL") String email,
                    @Param("SALT") String salt,
                    @Param("PASSWORD") String password,
                    @Param("ROLE_ID") Integer roleId);
    @Modifying
    @Query(value = INSERT_USER_GG,nativeQuery = true)
    void saveUserGG(@Param("FIRST_NAME") String firstName,
                  @Param("LAST_NAME") String lastName,
                  @Param("IMAGE") String image,
                  @Param("EMAIL") String email,
                  @Param("SALT") String salt,
                  @Param("PASSWORD") String password,
                  @Param("ROLE_ID") Integer roleId, @Param("STATUS_ACCOUNT")Integer statusAccount,@Param("PROVIDER")String provider);
    @Query(value = "SELECT * FROM user WHERE user.EMAIL=:EMAIL AND PROVIDER=:PROVIDER",nativeQuery = true)
    Optional<User> findUserByEmail(@Param("EMAIL") String mail,@Param("PROVIDER") String provider);

    @Query(value = "SELECT * FROM user WHERE user.ID=:ID",nativeQuery = true)
    Optional<User> findUserByID(@Param("ID") Integer ID);

    @Query(value = "SELECT COUNT(*) FROM `user`", nativeQuery = true)
    int getTotalUser();

    @Query(value = "SELECT * FROM user ORDER BY CASE WHEN :SORT = 'DESC' THEN ID END DESC, CASE WHEN :SORT = 'ASC' THEN ID END ASC LIMIT :LIMIT OFFSET :OFFSET",nativeQuery = true)
    List<User> findUsers(@Param("LIMIT")Integer limit, @Param("OFFSET") Integer offSet, @Param("SORT")String sort);

    @Modifying
    @Query(value = "UPDATE  user SET user.STATUS_ACCOUNT = :status WHERE user.EMAIL =:EMAIL",nativeQuery = true)
    Integer updateStatusAccount(@Param("status") Integer status,@Param("EMAIL") String email);

    @Modifying
    @Query(value = "UPDATE  user SET user.PASSWORD = :PASSWORD WHERE user.EMAIL =:EMAIL",nativeQuery = true)
    Integer updatePassword(@Param("PASSWORD") String  password,@Param("EMAIL") String email);


    @Modifying
    @Query(value = "UPDATE  user SET user.FIRST_NAME = :FIRST_NAME , user.LAST_NAME = :LAST_NAME ," +
            "user.DATE_OF_BIRTH = :DATE_OF_BIRTH , user.ADDRESS = :ADDRESS , user.PHONE = :PHONE ," +
            "user.GENDER = :GENDER , user.EMAIL = :EMAIL , user.IMAGE = :IMAGE " +
            "  WHERE user.ID =:ID",nativeQuery = true)
    Integer updateUser(@Param("ID") Integer  id,@Param("FIRST_NAME") String firstName,@Param("LAST_NAME") String lastName
            ,@Param("DATE_OF_BIRTH") String dateOfBirth,@Param("ADDRESS") String address,@Param("PHONE") String phone,
                       @Param("GENDER") Integer gender,@Param("EMAIL") String email,@Param("IMAGE") String image);

}
