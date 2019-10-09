package co.edu.unal.software_engineering.labs.service;

import co.edu.unal.software_engineering.labs.model.Role;
import co.edu.unal.software_engineering.labs.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class RoleService{

    private final RoleRepository roleRepository;

    public RoleService( RoleRepository roleRepository ){
        this.roleRepository = roleRepository;
    }

    public Role findById( Integer id ){
        return roleRepository.findById( id ).orElse( null );
    }

    public Role findByRoleName(String roleName){
        return roleRepository.findByRoleName(roleName);
    }

    public void save( Role role ){
        roleRepository.save( role );
    }

    public List<Role> getAll( ){
        return roleRepository.findAll( );
    }

}
