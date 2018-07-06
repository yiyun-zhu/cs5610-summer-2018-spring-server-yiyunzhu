package webdev.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import webdev.models.Assignment;
import webdev.models.Lesson;
import webdev.models.Widget;
import webdev.repositories.AssignmentRepository;
import webdev.repositories.LessonRepository;

@RestController
@CrossOrigin(origins = "*", maxAge=3600)
public class AssignmentService {
	@Autowired
	LessonRepository lessonRepository;
	@Autowired
	AssignmentRepository assiRepo;
	
	// find all assignments
	@GetMapping("api/assignment")
	public List<Assignment> findAllAssignments() {
		return (List<Assignment>) assiRepo.findAll();
	}
	
	// find all assignments
	@GetMapping("api/assignment/{assignmentId}")
	public Assignment findAssignmentById(
					@PathVariable("assignmentId")int assiId) {
		Optional<Assignment> data = assiRepo.findById(assiId);
		if (data.isPresent()) {
			return data.get();
		}
		return null;
	}
	// find assignments for a lesson
	@GetMapping("api/lesson/{lessonId}/assignment")
	public List<Assignment> findAssignmentsForLesson(
					@PathVariable("lessontId")int lessonId) {
		List<Assignment> result = new ArrayList<Assignment>();
		Optional<Lesson> data = lessonRepository.findById(lessonId);
		if (data.isPresent()) {
			Lesson lesson = data.get();
			List<Widget> widgets =  lesson.getWidgets();
			for (Widget w : widgets) {
				if (w.getWidgetType() == "Assignment" ) {
					result.add((Assignment)w);
				}
			}
		}
		return result;
	}
	// delete
	@DeleteMapping("api/assignment/{assignmentId}")
	public void DeleteAssignmentById(
					@PathVariable("assignmentId")int assiId) {
		Optional<Assignment> data = assiRepo.findById(assiId);
		if (data.isPresent()) {
			assiRepo.deleteById(assiId);
		}
	}
	
	// create Assignment
	@PostMapping("/api/lesson/{lessonId}/assignment")
	public Assignment createNewAssignment(
					@PathVariable("lessonId")int lessonId,
					@RequestBody Assignment newAssi) {
		Optional<Lesson> data = lessonRepository.findById(lessonId);
		if (data.isPresent()) {
			Lesson lesson = data.get();
			newAssi.setWidgetType("Assignment");
			newAssi.setLesson(lesson);
			return assiRepo.save(newAssi);
		}
		return null;
	}
	
	// update Assignment
	@PutMapping("api/assignment/{assignmentId}/update")
	public Assignment updateExam(
					@PathVariable("assignmentId") int assiId,
					@RequestBody Assignment newAssi) {
		Optional<Assignment> data = assiRepo.findById(assiId);
		if (data.isPresent()) {
			Assignment assi = data.get();
			assi.setTitle(newAssi.getTitle());
			assi.setDescription(newAssi.getDescription());
			assi.setPoints(newAssi.getPoints());
			return assiRepo.save(assi);
		}
		return null;
	}

}
