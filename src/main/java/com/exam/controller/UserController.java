package com.exam.controller;

import com.exam.helper.UserFoundException;
import com.exam.model.Role;
import com.exam.model.User;
import com.exam.model.UserRole;
import com.exam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    //creating user
    @PostMapping("/")
    public User createUser(@RequestBody User user) throws Exception {

        user.setProfile("default.png");
        //encoding password with bcryptpasswordencoder

        user.setPassword(this.bCryptPasswordEncoder.encode(user.getPassword()));

        Set<UserRole> roles=new HashSet<>();

        Role role=new Role();
        role.setRoleId(45L);
        role.setRoleName("NORMAL");

        UserRole userRole=new UserRole();
        userRole.setUser(user);
        userRole.setRole(role);

        roles.add(userRole);

                return this.userService.createUser(user, roles);
    }

    // getting user
    @GetMapping("/{username}")
    public  User getUser(@PathVariable("username")String username){
        return this.userService.getUser(username);
    }

    // delete the user by id
    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable("userId") Long userId){
        this.userService.deleteUser(userId);
    }

    @ExceptionHandler(UserFoundException.class)
    public ResponseEntity<?> exceptionHandler(UserFoundException ex){ return ResponseEntity.ok(ex.getMessage());}


// creating order for a payment

//    @PostMapping("/create_order")
//    @ResponseBody
//    public String createOrder(@RequestBody Map<String,Object> data) throws Exception
//    {
//        //System.out.println("hey order function executed.");
//        System.out.println(data);
//        int amt=Integer.parseInt(data.get("amount").toString());
//
//        var client=new RazorpayClient("rzp_test_FQLkDF6ltuohVP","QnVQ8JusP3nxspRUTBQuoPGS");
//
//        JSONObject ob=new JSONObject();
//        ob.put("amount",amt*100);
//        ob.put("currency","INR");
//        ob.put("receipt","txn_123456789");
//
//        //creating new order
//        Order order = client.Orders.create(ob);
//        System.out.println(order);
//
//        return "done";
//    }
}
