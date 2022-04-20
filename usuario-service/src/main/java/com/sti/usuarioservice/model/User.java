package com.sti.usuarioservice.model;

import com.sti.usuarioservice.model.status.ModelStatus;
import lombok.*;

import javax.persistence.*;
import java.util.UUID;

/**
 * User class to represent User entity.
 * @author Laurent Caceres
 * @version 1.0.0
 */
@Entity
@Table(name = "t_users")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class User {

    @Id
    @Column(name = "user_id", nullable = false, unique = true, length = 64)
    private String userId;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "user_email", nullable = false)
    private String userEmail;

    @Column(name = "user_status", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private ModelStatus userStatus;

    /**
     * Adds fields which are not populated by User DTO.
     * @return
     */
    public static User buildFromDto(User user){
        user.setUserId(UUID.randomUUID().toString());
        user.setUserStatus(ModelStatus.ACTIVE);
        return user;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null) return false;
        if(o.getClass() != this.getClass()) return false;
        User user = (User) o;
        return this.userId == user.userId
                && (this.userName.equals(user.userName))
                && (this.userEmail.equals(user.userEmail));
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + (this.userName == null ? 0 : this.userName.hashCode());
        hash = 31 * hash + (this.userId == null ? 0 : this.userId.hashCode());
        hash = 31 * hash + (this.userEmail == null ? 0 : this.userEmail.hashCode());
        return hash;
    }

    private void setUserId(final String userId){
        this.userId = userId;
    }

    public void setUserStatus(ModelStatus modelStatus){
        this.userStatus = modelStatus;
    }
}
