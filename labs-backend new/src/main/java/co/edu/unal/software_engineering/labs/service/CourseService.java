package co.edu.unal.software_engineering.labs.service;
import co.edu.unal.software_engineering.labs.model.Course;
import co.edu.unal.software_engineering.labs.pojo.RegisterCoursePOJO;
import co.edu.unal.software_engineering.labs.repository.CourseRepository;
import org.springframework.stereotype.Service;


@Service
public class CourseService{

    private final CourseRepository courseRepository;

    public CourseService( CourseRepository courseRepository ){
        this.courseRepository = courseRepository;
    }

    public Course findByCourseName( String courseName ){
        return courseRepository.findByCourseName((courseName));
    }

    public void save(Course course){
        courseRepository.save(course);
    }

    public boolean isRightCourse(RegisterCoursePOJO course){
        boolean correctness = course.getCourseName( ) != null && course.getDurationHours() != null;
        if(correctness){
            correctness = !course.getCourseName().trim().isEmpty();
        }
        return correctness;
    }


}
