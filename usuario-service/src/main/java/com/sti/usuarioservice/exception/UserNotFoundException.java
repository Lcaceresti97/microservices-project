package com.sti.usuarioservice.exception;


import com.sti.usuarioservice.model.User;

/**
 * User 404 status exception.
 * @author Laurent Caceres
 */
public class UserNotFoundException extends ResourceNotFoundException {

    /**
     *
     * @param field
     * @param fieldValue
     * @return
     */
    public static ResourceNotFoundException
            buildUserNotFoundExceptionForField(String field, String fieldValue){
        return resourceNotFoundExceptionOf(User.class, field, fieldValue);
    }

    /**
     *
     * @param userId
     * @return
     */
    public static ResourceNotFoundException
            buildUserNotFoundExceptionForId(String userId){
        return resourceNotFoundExceptionOf(User.class, "userId", userId);
    }

    /**
     *
     * @param searchParams
     * @return ResourceNotFoundException instance
     */
    public static ResourceNotFoundException buildUserNotFoundException(String... searchParams){
        return resourceNotFoundExceptionOf(User.class, searchParams);
    }

}