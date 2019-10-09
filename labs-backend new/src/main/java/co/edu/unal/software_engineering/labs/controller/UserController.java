package co.edu.unal.software_engineering.labs.controller;

import co.edu.unal.software_engineering.labs.model.Role;
import co.edu.unal.software_engineering.labs.model.User;
import co.edu.unal.software_engineering.labs.pojo.LoginUserPOJO;
import co.edu.unal.software_engineering.labs.pojo.RegisterUserPOJO;
import co.edu.unal.software_engineering.labs.service.RoleService;
import co.edu.unal.software_engineering.labs.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.Collections;
import java.util.List;


@CrossOrigin
@RestController
public class UserController{

    private final UserService userService;

    private final RoleService roleService;

    public UserController( UserService userService, RoleService roleService ){
        this.userService = userService;
        this.roleService = roleService;
    }


    @Bean
    public PasswordEncoder passwordEncoder( ){
        return new BCryptPasswordEncoder( );
    }

    @PostMapping( value = { "/registro/{roleId}" } )
    public ResponseEntity register( @PathVariable Integer roleId,
                                    @RequestBody RegisterUserPOJO userPOJO ){
        Role role = roleService.findById( roleId );
        User existingUser = userService.findByUsername( userPOJO.getUsername( ) );
        if( role == null || existingUser != null || !userService.isRightUser( userPOJO ) ){
            return new ResponseEntity( HttpStatus.BAD_REQUEST );
        }
        User newUser = new User( );
        newUser.setNames( userPOJO.getNames( ).toUpperCase( ) );
        newUser.setSurnames( userPOJO.getSurnames( ).toUpperCase( ) );
        newUser.setUsername( userPOJO.getUsername( ).toLowerCase( ) );
        newUser.setPassword( passwordEncoder( ).encode( userPOJO.getPassword( ) ) );
        newUser.setRoles( Collections.singletonList( role ) );
        userService.save( newUser );
        return new ResponseEntity( HttpStatus.CREATED );
    }

    @PostMapping( value = { "/registro/nuevo-rol/{roleId}" } )
    public ResponseEntity registerRoleToUser( @PathVariable Integer roleId, @RequestBody LoginUserPOJO userPOJO ){
        Role role = roleService.findById( roleId );
        User existingUser = userService.findByUsername( userPOJO.getUsername( ) );
        if( role == null || existingUser == null || existingUser.getRoles( ).contains( role ) ){
            return new ResponseEntity( HttpStatus.BAD_REQUEST );
        }else if( !passwordEncoder( ).matches( userPOJO.getPassword( ), existingUser.getPassword( ) ) ){
            return new ResponseEntity( HttpStatus.UNAUTHORIZED );
        }
        existingUser.addRole( role );
        userService.save( existingUser );
        return new ResponseEntity( HttpStatus.CREATED );
    }

    @PostMapping( value = { "/ingreso" } )
    public ResponseEntity logIn(  @RequestBody LoginUserPOJO userPOJO ){
        User existingUser = userService.findByUsername( userPOJO.getUsername( ) );

        if( existingUser == null ){
            return new ResponseEntity( HttpStatus.BAD_REQUEST );
        }else if( !passwordEncoder( ).matches( userPOJO.getPassword( ), existingUser.getPassword( ) ) ){
            return new ResponseEntity( HttpStatus.UNAUTHORIZED );
        }
        return new ResponseEntity( HttpStatus.ACCEPTED );
    }


    @GetMapping( value = { "/users" } )
    public List<User> getAllRoles( ){
        return userService.getAll( );
    }

}