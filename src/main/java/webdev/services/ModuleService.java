package webdev.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.*;

import webdev.models.Course;
import webdev.models.Module;
import webdev.repositories.CourseRepository;
import webdev.repositories.ModuleRepository;

@RestController
@CrossOrigin(origins="*", maxAge=3600)
public class ModuleService {
	@Autowired
	CourseRepository courseRepository;
	@Autowired
	ModuleRepository moduleRepository;
	
	@GetMapping("/api/course/{courseId}/module") 
	public Iterable<Module> findAllModulesForCourse(
				@PathVariable("courseId")int courseId) {
		Optional<Course> data = courseRepository.findById(courseId);
		if(data.isPresent()) {
			Course course = data.get();
			return course.getModules();
		}
		return null;
	}
	
	@PostMapping("/api/course/{courseId}/module")
	public Module createModule(
					@PathVariable("courseId")int courseId,
					@RequestBody Module newModule) {
		Optional<Course> data = courseRepository.findById(courseId);
		if(data.isPresent()) {
			Course course = data.get();
			newModule.setCourse(course);
			return moduleRepository.save(newModule);
		}
		return null;
	}
	
	@DeleteMapping("/api/module/{mId}")
	public void deleteModule(
					@PathVariable("mId")int moduleId) {
		moduleRepository.deleteById(moduleId);
	}

	@GetMapping("/api/module")
	public Iterable<Module> findAllModules() {
		return moduleRepository.findAll();
	}
}
