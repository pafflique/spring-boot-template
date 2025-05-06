package lv.ctco.springboottemplate.features.todo;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class TodoService {
  private final TodoRepository todoRepository;

  public TodoService(TodoRepository todoRepository) {
    this.todoRepository = todoRepository;
  }

  public List<Todo> getAllTodos() {
    return todoRepository.findAll();
  }

  public Optional<Todo> getTodoById(String id) {
    return todoRepository.findById(id);
  }

  public List<Todo> searchTodos(String title) {
    return todoRepository.findByTitleContainingIgnoreCase(title);
  }

  public Todo createTodo(String title, String description, boolean completed, String createdBy) {
    var now = Instant.now();
    var todo = new Todo(null, title, description, completed, createdBy, createdBy, now, now);
    return todoRepository.save(todo);
  }

  public Optional<Todo> updateTodo(
      String id, String title, String description, boolean completed, String updatedBy) {
    return todoRepository
        .findById(id)
        .map(
            existingTodo -> {
              var updatedTodo =
                  new Todo(
                      existingTodo.id(),
                      title,
                      description,
                      completed,
                      existingTodo.createdBy(),
                      updatedBy,
                      existingTodo.createdAt(),
                      Instant.now());
              return todoRepository.save(updatedTodo);
            });
  }

  public boolean deleteTodo(String id) {
    if (todoRepository.existsById(id)) {
      todoRepository.deleteById(id);
      return true;
    }
    return false;
  }
}
