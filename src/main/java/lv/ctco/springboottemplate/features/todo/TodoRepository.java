package lv.ctco.springboottemplate.features.todo;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends MongoRepository<Todo, String> {
  List<Todo> findByTitleContainingIgnoreCase(String title);
}
