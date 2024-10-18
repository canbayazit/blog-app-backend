package com.example.blog_backend.repository;

import com.example.blog_backend.core.repository.BaseRepository;
import com.example.blog_backend.entity.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends BaseRepository<UserEntity> {
    Optional<UserEntity> findByEmail(String email);

    // Security Service de findByEmail kullanınca user entity de roller lazy olduğu için roller gelmiş olmuyor
    // ve bu yüzden user.get().getRoles() dediğimizde veri gelmiyor 401 alıyoruz.
    // Bunun sebebi Security Service de UserRepository den findByEmail ile bir sorgu çalıştırdığımızda
    // Hibernate bir session açar ve veritabanından ilgili kullanıcıyı yükler.
    // Ancak bu session, metot tamamlanıp işlem bittiğinde kapanır. O yüzden user.get().getRoles() dediğimizde rolleri
    // lazy olarak yükleyemeyiz. Çünkü Lazy olan veriyi session açıkken çekebiliriz. Bu yüzden aşağıdaki gibi
    // custom query tanımladık. Fetch ile lazy olan role verisini yüklemesini söylüyoruz
    @Query("SELECT u FROM UserEntity u JOIN FETCH u.roles WHERE u.email = :email")
    Optional<UserEntity> findByEmailWithRoles(@Param("email") String email);
}
