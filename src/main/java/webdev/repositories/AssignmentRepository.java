package webdev.repositories;

import org.springframework.data.repository.CrudRepository;

import webdev.models.Assignment;

public interface AssignmentRepository 
		extends CrudRepository<Assignment, Integer>{

}
