package co.edu.unal.software_engineering.labs.controller;

import co.edu.unal.software_engineering.labs.model.*;
import co.edu.unal.software_engineering.labs.pojo.RegisterCoursePOJO;
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
public class CourseController{

    private final CourseService courseService;
    private final UserService userService;
    private final RoleService roleService;


    public  CourseController(CourseService courseService, UserService userService,
                             RoleService roleService){
        this.courseService = courseService;
        this.userService = userService;
        this.roleService = roleService;
    }

    @Bean
    public PasswordEncoder passwordEncode( ){
        return new BCryptPasswordEncoder( );
    }


    @PostMapping( value = { "/registro/curso" } )
    public ResponseEntity registerCourse(@RequestBody RegisterCoursePOJO coursePOJO){

        Course existingCourse = courseService.findByCourseName( coursePOJO.getCourseName( ) );
        User existingUser = userService.findByUsername( coursePOJO.getUsername( ) );
        Role teacherRole = roleService.findById( 2 );

        if(existingCourse != null || !courseService.isRightCourse( coursePOJO ) || existingUser == null){
            return new ResponseEntity( HttpStatus.BAD_REQUEST );
        }else if (!passwordEncode( ).matches( coursePOJO.getPassword( ), existingUser.getPassword())
                || !existingUser.getRoles().contains(teacherRole)){
            return new ResponseEntity( HttpStatus.UNAUTHORIZED );
        }
        Course newCourse = new Course();
        newCourse.setCourseName(coursePOJO.getCourseName());
        newCourse.setDurationHours(coursePOJO.getDurationHours());
        courseService.save( newCourse );
        return new ResponseEntity( HttpStatus.CREATED );
    }


}
