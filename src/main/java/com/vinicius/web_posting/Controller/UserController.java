package com.vinicius.web_posting.Controller;

import com.vinicius.web_posting.DTO.UserDTO;
import com.vinicius.web_posting.Model.Like;
import com.vinicius.web_posting.Model.User;
import com.vinicius.web_posting.Service.LikeService;
import com.vinicius.web_posting.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST})
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private LikeService likeService;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getUsers() {
        return ResponseEntity.ok(userService.getAllUsers().stream().map(UserDTO::new).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        User findedUser = userService.getUserById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return ResponseEntity.ok(new UserDTO(findedUser));
    }

}
