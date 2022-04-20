package com.sti.usuarioservice.repository;

import com.sti.usuarioservice.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for User entity.
 * @author Laurent Caceres
 * @version 1.0.0
 */
@Repository
public interface UserRepository extends JpaRepository<User, String> {

        /**
         * Find Paginated user by name.
         * @param userName
         * @param pageable
         * @return
         */
    Page<User> findByUserNameContaining(String userName, Pageable pageable);
}
