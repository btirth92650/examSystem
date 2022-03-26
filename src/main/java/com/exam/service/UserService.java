package com.exam.service;


import com.exam.helper.UserFoundException;
import com.exam.model.User;
import com.exam.model.UserRole;

import java.util.Set;

public interface UserService {

    // creating user
    public User createUser(User user, Set<UserRole> userRoles) throws Exception;

    //get user by username
    public User getUser(String username);

    //delete user by userid
    public void  deleteUser(long userId);

   // create order by orderid
    public  void createOrder(long orderId);

}
