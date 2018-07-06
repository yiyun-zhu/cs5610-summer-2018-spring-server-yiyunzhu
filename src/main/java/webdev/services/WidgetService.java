package webdev.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import webdev.models.Lesson;
import webdev.models.Widget;
import webdev.repositories.LessonRepository;
import webdev.repositories.WidgetRepository;

@RestController
@CrossOrigin(origins = "*", maxAge=3600)
public class WidgetService {

	@Autowired
	WidgetRepository repository;
	@Autowired
	LessonRepository lessonRepository;

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
	

}
