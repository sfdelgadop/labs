package co.edu.unal.software_engineering.labs.controller;

import co.edu.unal.software_engineering.labs.model.Role;
import co.edu.unal.software_engineering.labs.pojo.RegisterRolePOJO;
import co.edu.unal.software_engineering.labs.service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class RoleController{

    private RoleService roleService;


    public RoleController( RoleService roleService ){
        this.roleService = roleService;
    }

    @PostMapping( value = { "/registro/nuevo-rol" } )
    public ResponseEntity registerRole(@RequestBody RegisterRolePOJO rolePOJO){
        Role existingRole = roleService.findByRoleName(rolePOJO.getRoleName());
        if(existingRole != null){
            return new ResponseEntity( HttpStatus.BAD_REQUEST );
        }
        Role newRol = new Role();
        newRol.setRoleName(rolePOJO.getRoleName());
        newRol.setId(roleService.getAll().size()+1);
        roleService.save(newRol);
        return new ResponseEntity( HttpStatus.CREATED );
    }



    @GetMapping( value = { "/roles" } )
    public List<Role> getAllRoles( ){
        return roleService.getAll( );
    }
}
