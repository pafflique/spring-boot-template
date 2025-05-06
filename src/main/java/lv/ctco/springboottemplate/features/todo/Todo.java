package lv.ctco.springboottemplate.features.todo;

import java.time.Instant;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "todos")
public record Todo(
    @Id String id,
    String title,
    String description,
    boolean completed,
    String createdBy,
    String updatedBy,
    Instant createdAt,
    Instant updatedAt) {}
