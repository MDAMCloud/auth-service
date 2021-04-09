package com.cloud.authservice.service;

import com.cloud.authservice.entity.Action;
import com.cloud.authservice.entity.Token;
import com.cloud.authservice.entity.User;
import com.cloud.authservice.exception.NoUserFoundException;
import com.cloud.authservice.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.cloud.authservice.security.SecurityConstants.EXPIRATION_TIME;
import static com.cloud.authservice.security.SecurityConstants.SECRET;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public User get(String id) throws NoUserFoundException {
        Optional<User> user = userRepository.findById(id);

        if (user.isPresent())
            return user.get();
        else {
            throw new NoUserFoundException();
        }
    }

    public User getByUsername(String username) throws NoUserFoundException {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            return user;
        } else {
            throw new NoUserFoundException();
        }
    }

    public List<User> getAll() {
        List<User> users = userRepository.findAll();
        return users;
    }

    public User add(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        User addedUser = userRepository.save(user);
        return addedUser;
    }

    public User update(User user) throws NoUserFoundException {
        Optional<User> userToUpdate = userRepository.findById(user.getId());
        User updatedUser = null;
        if (userToUpdate.isPresent()) {
            if (user.getPassword() != null)
                user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            updatedUser = userRepository.save(userToUpdate.get().mergeUser(user));
            return updatedUser;
        } else {
            throw new NoUserFoundException();
        }
    }

    public Token generateToken(User user) {
        Claims claims = Jwts.claims().setSubject(user.getUsername());
        claims.put("userId", user.getId() + "");
        claims.put("accountType", user.getAccountType());

        String tokenString =   Jwts.builder()
                                   .setClaims(claims)
                                   .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                                   .signWith(SignatureAlgorithm.HS512, SECRET)
                                   .compact();

        return new Token(tokenString);
    }

    public void delete(User user) {
        userRepository.delete(user);
    }
}