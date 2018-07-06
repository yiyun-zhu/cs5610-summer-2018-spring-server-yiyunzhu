package webdev.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import webdev.models.Assignment;
import webdev.models.Exam;
import webdev.models.Lesson;
import webdev.models.Widget;
import webdev.repositories.AssignmentRepository;
import webdev.repositories.ExamRepository;
import webdev.repositories.LessonRepository;
import webdev.repositories.WidgetRepository;

@RestController
@CrossOrigin(origins = "*", maxAge=3600)
public class WidgetService {

	@Autowired
	WidgetRepository repository;
	@Autowired
	LessonRepository lessonRepository;
	@Autowired
	ExamRepository examRepo;
	@Autowired
	AssignmentRepository assiRepo;
	
	@GetMapping("/api/widget/{widgetId}")
	public Widget findWidgetById(@PathVariable("widgetId")int widgetId) {
		Optional<Widget> data = repository.findById(widgetId);
		if (data.isPresent()) {
			return data.get();
		}
		return null;
	}
	
	@GetMapping("/api/widget")
	public List<Widget> findAllWidgets() {
		return (List<Widget>)repository.findAll();
	}
	
	@GetMapping("/api/lesson/{lessonId}/widget")
	public List<Widget> findAllWidgetsForLesson(@PathVariable("lessonId")int lessonId) {
		Optional<Lesson> data = lessonRepository.findById(lessonId);
		if (data.isPresent()) {
			Lesson lesson = data.get();
			return lesson.getWidgets();
		}
		return null;
	}
	
	@PostMapping("/api/lesson/{lessonId}/widget/save")
	public void saveAllWidgets(
			@PathVariable("lessonId")int lessonId,
			@RequestBody List<Widget> widgets) {
		Optional<Lesson> data = lessonRepository.findById(lessonId);
		if (data.isPresent()) {
			Lesson lesson = data.get();
			List<Widget> listOfWidgets = lesson.getWidgets();
			for(Widget widget : listOfWidgets) {
				repository.delete(widget);
			}
			for(Widget widget : widgets) {
				widget.setLesson(lesson);
				repository.save(widget);
			}
		}
	}
	
	@DeleteMapping("/api/widget/{widgetId}")
	public void deleteWidgetById(@PathVariable("widgetId")int widgetId) {
		repository.deleteById(widgetId);
	}
	
	@PutMapping("/api/widget/{widgetId")
	public Widget updateWidgetById(@PathVariable("widgetId")int widgetId) {
		Optional<Widget> data = repository.findById(widgetId);
		if (data.isPresent()) {
			return repository.save(data.get());
		}
		return null;
	}
	
	// Assignment4//////////////////////////////////////////////////////////////
	// create Exam
	@PostMapping("/api/lesson/{lessonId}/exam")
	public Exam createNewExam(
					@PathVariable("lessonId")int lessonId,
					@RequestBody Exam newExam) {
		Optional<Lesson> data = lessonRepository.findById(lessonId);
		if (data.isPresent()) {
			Lesson lesson = data.get();
			newExam.setWidgetType("Exam");
			newExam.setLesson(lesson);
			return examRepo.save(newExam);
		}
		return null;
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
	// update Exam information
	@PutMapping("api/exam/{examId}/update")
	public Exam updateExam(
					@PathVariable("examId") int examId,
					@RequestBody Exam newExam) {
		Optional<Exam> data = examRepo.findById(examId);
		if (data.isPresent()) {
			Exam exam = data.get();
			exam.setTitle(newExam.getTitle());
			exam.setDescription(newExam.getDescription());
			return examRepo.save(exam);
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
