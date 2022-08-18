package com.todo.todo.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import com.todo.todo.domain.AppUser;
import com.todo.todo.repository.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class UserService implements UserDetailsService{
	Logger logger = LoggerFactory.getLogger(UserService.class);
	@Autowired
	private UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	public UserService(PasswordEncoder passwordEncoder){
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		AppUser user = userRepository.findByUsername(username);
		if(user == null){
			throw new UsernameNotFoundException("Appuser Not Found");
		}
		else{
			Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
			authorities.add(new SimpleGrantedAuthority(user.getRole()));
			return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
		}
	}

	public AppUser saveUser(AppUser user){
		AppUser existingUser = userRepository.findByUsername(user.getUsername());
		if(existingUser != null){
			logger.info("User exists");
		}
		else{
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRole("ROLE_USER");
            AppUser savedUser = userRepository.save(user);
			return savedUser;
		}
		return existingUser;
	}

    public AppUser getUser(String username) {
        logger.info("Fetching user {}", username);
        return userRepository.findByUsername(username);
    }

    public List<AppUser> getUsers() {
        logger.info("Fetching all users");
        return userRepository.findAll();
    }

    public void updateUser(AppUser newUser, String id){
        if(userRepository.findById(id).isPresent()){
            userRepository.findById(id)
                .map(user -> {
                    user.setId(id);
                    if(newUser.getUsername() == null){
                        user.setUsername(user.getUsername());
                    }
                    else{
                        user.setUsername(newUser.getUsername());
                    }
                    if(newUser.getPassword() == null){
                        user.setPassword(user.getPassword());
                    }
                    else{
                        user.setPassword(passwordEncoder.encode(newUser.getPassword()));
                    }
                    return userRepository.saveAndFlush(user);
                })
            .orElseGet(() -> {
                newUser.setId(id);
                return userRepository.saveAndFlush(newUser);
            });
        }
        else{
            logger.error("User Not Found");
        }
    }

    public void deleteUser(String userId){
        userRepository.deleteById(userId);
    }
    
    public Map<String, String> userById(String id) {
        Map<String, String> response = new HashMap<>();
        if(userRepository.findById(id).isPresent()){
            AppUser user = userRepository.findById(id).get();
            response.put("userId", user.getId());
            response.put("username", user.getUsername());
        }
        return response;
    }
}
