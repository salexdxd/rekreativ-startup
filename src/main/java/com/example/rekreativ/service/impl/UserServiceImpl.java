package com.example.rekreativ.service.impl;

import com.example.rekreativ.auth.AuthUser;
import com.example.rekreativ.dto.UserDTO;
import com.example.rekreativ.error.exceptions.ObjectAlreadyExistsException;
import com.example.rekreativ.error.exceptions.ObjectNotFoundException;
import com.example.rekreativ.model.Role;
import com.example.rekreativ.model.User;
import com.example.rekreativ.repository.UserRepository;
import com.example.rekreativ.service.RoleService;
import com.example.rekreativ.service.UserService;
import com.example.rekreativ.util.ValidatorUtil;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Slf4j
@Transactional
public class UserServiceImpl implements UserDetailsService, UserService {

    private final UserRepository userRepository;

    private final RoleService roleService;

    private final PasswordEncoder passwordEncoder;

    private final ValidatorUtil validatorUtil;

    public UserServiceImpl(UserRepository userRepository,
                           RoleService roleService,
                           PasswordEncoder passwordEncoder,
                           ValidatorUtil validatorUtil) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.validatorUtil = validatorUtil;
    }

    @PostConstruct
    public void initRoleAndUser() {

        if(!roleService.existsByName("ROLE_ADMIN")) {
            Role adminRole = new Role("ROLE_ADMIN");
            roleService.initSave(adminRole);
        }

        if(!roleService.existsByName("ROLE_USER")) {
            Role userRole = new Role("ROLE_USER");
            roleService.initSave(userRole);
        }

        if(userRepository.findByUsername("admin").isEmpty()) {
            Role adminRole = roleService.findByName("ROLE_ADMIN");

            final String adminpass = "admin";
            User adminUser = new User(
                    null,
                    "admin",
                    passwordEncoder.encode(adminpass));
            adminUser.getRoles().add(adminRole);

            userRepository.save(adminUser);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException(String.format("Username '%s' was not found",username)));

        return new AuthUser(user);
    }

    //need to validate email, roles etc
    public void addRoleToUser(String username, String roles){
        User user = findUserByUsername(username);

        Role role = roleService.findByName(roles);
        user.getRoles().add(role);
//		log.info("Adding role {} to the user {}", roles, email);
    }

    //--------------------Save User with default User role--------------------
    public User saveUser(User user) {
		log.info("Saving new user {} to the database", user.getUsername());

        if(userRepository.findByUsername(user.getUsername()).isPresent()){

            log.debug("user already exists with username: {}", user.getUsername());
            throw new ObjectAlreadyExistsException(User.class, user.getUsername());
        }

        Role role = roleService.findByName("ROLE_USER");

        User newUser = new User();
        newUser.getRoles().add(role);
        newUser.setUsername(user.getUsername());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));

        validatorUtil.userValidator(newUser);

        return userRepository.save(newUser);
    }

    @Override
    public User initSave(User user) {
        Role role = roleService.findByName("ROLE_USER");

        User newUser = new User();
        newUser.getRoles().add(role);
        newUser.setUsername(user.getUsername());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(newUser);
    }

    public void deleteUserById(Long id) {
        User user = findUserById(id);

        userRepository.deleteById(user.getId());
    }

    public void delete(User user) {
        User existingUser = findUserById(user.getId());

        userRepository.delete(existingUser);
    }

    public Iterable<UserDTO> findAll(){
        Iterable<User> users = userRepository.findAll();

        ModelMapper modelMapper = new ModelMapper();

        return StreamSupport.stream(users.spliterator(), false)
                .map(u -> modelMapper.map(u, UserDTO.class))
                .collect(Collectors.toList());
    }

    public Page<User> findAllPageable(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public User findUserById(Long id) {
        Optional<User> user = userRepository.findById(id);

        if(user.isEmpty()){

            log.debug("User not found with id: {}", id);
            throw new ObjectNotFoundException(User.class, id);
        }

        return user.get();
    }

    @Override
    public boolean existsById(Long id) {

        return userRepository.existsById(id);
    }

    @Override
    public boolean existsByUsername(String username) {

        return userRepository.findByUsername(username).isPresent();
    }

    public User findUserByUsername(String username){
        Optional<User> user = userRepository.findByUsername(username);

        if(user.isEmpty()){

            log.debug("user not found with username: {}", username);
            throw new ObjectNotFoundException(User.class, username);
        }

        return user.get();
    }

}
