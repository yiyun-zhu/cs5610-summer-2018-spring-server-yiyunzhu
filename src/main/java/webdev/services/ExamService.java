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

import webdev.models.*;
import webdev.repositories.*;

@RestController
@CrossOrigin(origins = "*", maxAge=3600)
public class ExamService {
	@Autowired
	LessonRepository lessonRepo;
	@Autowired
	ExamRepository examRepo;

	// find exam by Id
	@GetMapping("/api/exam/{examId}")
	public Exam findExamById(
					@PathVariable("examId")int examId) {
		Optional<Exam> data = examRepo.findById(examId);
		if (data.isPresent()) {
			return data.get();
		}
		return null; 
	}
	// find assignments for a lesson
	@GetMapping("api/lesson/{lessonId}/exam")
	public List<Exam> findExamsForLesson(
					@PathVariable("lessontId")int lessonId) {
		List<Exam> result = new ArrayList<Exam>();
		Optional<Lesson> data = lessonRepo.findById(lessonId);
		if (data.isPresent()) {
			Lesson lesson = data.get();
			List<Widget> widgets =  lesson.getWidgets();
			for (Widget w : widgets) {
				if (w.getWidgetType() == "Exam" ) {
					result.add((Exam)w);
				}
			}
		}
		return result;
	}
	// find all exams
	@GetMapping("/api/exam/")
	public List<Exam> findAllQExams() {
		return (List<Exam>) examRepo.findAll();
	}
	// delete
	@DeleteMapping("api/exam/{examId}")
	public void DeleteExamById(
			@PathVariable("assignmentId")int examId) {
		Optional<Exam> data = examRepo.findById(examId);
		if (data.isPresent()) {
			examRepo.deleteById(examId);
		}
	}
	// create Exam
	@PostMapping("/api/lesson/{lessonId}/exam")
	public Exam createNewExam(
					@PathVariable("lessonId")int lessonId,
					@RequestBody Exam newExam) {
		Optional<Lesson> data = lessonRepo.findById(lessonId);
		if (data.isPresent()) {
			Lesson lesson = data.get();
			newExam.setWidgetType("Exam");
			newExam.setLesson(lesson);
			return examRepo.save(newExam);
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
	

}
