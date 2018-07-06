package webdev.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import webdev.models.Exam;
import webdev.models.Question;
import webdev.repositories.ExamRepository;
import webdev.repositories.QuestionRepository;

@RestController
@CrossOrigin(origins = "*", maxAge=3600)
public class BaseQuestionService {
	@Autowired
	ExamRepository examRepository;
	@Autowired
	QuestionRepository baseRepo;
	
	// find question by id
	@GetMapping("api/question/{questionId}")
	public Question findQuestionById
				(@PathVariable("questionId") int questionId) {
		Optional<Question> optionalQuestion = 
						baseRepo.findById(questionId);
		if (optionalQuestion.isPresent()) {
			return optionalQuestion.get();
		}
		return null;
	}
	// find all questions for an exam
	@GetMapping("/api/exam/{examId}/question")
	public List<Question> findAllQuestionsForExam(
				@PathVariable("examId") int examId) {
		Optional<Exam> optionalExam = examRepository.findById(examId);
		if(optionalExam.isPresent()) {
			Exam exam = optionalExam.get();
			List<Question> questions = exam.getQuestions();
//			int count = questions.size();
			return questions;
		}
		return null;
	}
}
