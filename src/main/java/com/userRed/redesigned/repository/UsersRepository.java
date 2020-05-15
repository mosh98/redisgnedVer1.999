//package com.userRed.redesigned.repository;
//
//import com.userRed.redesigned.model.Users;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.querydsl.QuerydslPredicateExecutor;
//import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
//
//import java.util.Optional;
//
//public interface UsersRepository extends JpaRepository<Users, Integer> {
//
//    Optional<Users> findByUsername(String username);
//
//    Optional<Users> findByEmail(String email);
//
//    boolean existsByUsername(String username);
//
//    @Query("select m from Users m where " +
//            "(?1 is null or upper(m.username) like concat('%', upper(?1), '%')) ")
//    Page<Users> getByQuery(String name, final Pageable pageable);
//
//    @Query("select m from Users m where " +
//            "(?1 is null or upper(m.username) like concat('%', upper(?1), '%')) ")
//    Page<Users> findAllByUsername(String name, final Pageable pageable);
//}
