package co.edu.unal.software_engineering.labs.controller;

import co.edu.unal.software_engineering.labs.model.*;
import co.edu.unal.software_engineering.labs.pojo.RegisterInCoursePOJO;
import co.edu.unal.software_engineering.labs.service.*;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class AssociationController {
    private final CourseService courseService;
    private final UserService userService;
    private final RoleService roleService;
    private final PeriodService periodService;
    private final AssociationService associationService;

    public AssociationController(CourseService courseService, UserService userService,
                                 RoleService roleService, PeriodService periodService,
                                 AssociationService associationService){
        this.courseService = courseService;
        this.userService = userService;
        this.roleService = roleService;
        this.periodService = periodService;
        this.associationService = associationService;
    }

    @Bean
    public PasswordEncoder passwordCode( ){
        return new BCryptPasswordEncoder( );
    }


    @PostMapping( value = { "/registro/curso/{periodId}" } )
    public ResponseEntity registerInCourse(@PathVariable Integer periodId,
                                           @RequestBody RegisterInCoursePOJO coursePOJO){

        Role role = roleService.findByRoleName(coursePOJO.getRoleName());
        Period period = periodService.findById(periodId);
        Course existingCourse = courseService.findByCourseName( coursePOJO.getCourseName( ) );
        User existingUser = userService.findByUsername( coursePOJO.getUsername( ) );

        if(role == null || period == null || existingCourse == null || existingUser == null ||
                !existingUser.getRoles( ).contains( role )){
            return new ResponseEntity( HttpStatus.BAD_REQUEST );
        }else if (!passwordCode( ).matches( coursePOJO.getPassword( ), existingUser.getPassword())){
            return new ResponseEntity( HttpStatus.UNAUTHORIZED );
        }

        UserRole.UserRolePK userRolePK = new UserRole.UserRolePK(existingUser,role);
        UserRole userRole = new UserRole();
        userRole.setUserRolePK(userRolePK);
        Association newAssociation = new Association();
        newAssociation.setCourse(existingCourse);
        newAssociation.setUserRole(userRole);
        newAssociation.setPeriod(period);
        newAssociation.setRole(role);
        associationService.save(newAssociation);
        return new ResponseEntity( HttpStatus.CREATED );
    }

}
