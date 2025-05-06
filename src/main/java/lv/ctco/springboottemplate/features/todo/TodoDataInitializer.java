package lv.ctco.springboottemplate.features.todo;

import java.time.Instant;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TodoDataInitializer {

  @Bean
  CommandLineRunner initDatabase(TodoRepository todoRepository) {
    return args -> {
      todoRepository.deleteAll(); // Clear existing data

      var now = Instant.now();
      var todos =
          List.of(
              new Todo(
                  null, "Buy groceries", "Milk, eggs, bread", false, "system", "system", now, now),
              new Todo(
                  null,
                  "Call dentist",
                  "Schedule annual checkup",
                  true,
                  "system",
                  "system",
                  now,
                  now),
              new Todo(
                  null,
                  "Fix bug in production",
                  "High priority issue #123",
                  false,
                  "system",
                  "system",
                  now,
                  now),
              new Todo(
                  null,
                  "Write documentation",
                  "Update API docs",
                  false,
                  "system",
                  "system",
                  now,
                  now),
              new Todo(
                  null,
                  "Plan vacation",
                  "Research destinations",
                  true,
                  "system",
                  "system",
                  now,
                  now));

      todoRepository.saveAll(todos);
      System.out.println("Initialized database with " + todos.size() + " todo items");
    };
  }
}
