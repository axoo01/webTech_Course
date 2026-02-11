package api.practice.q5_task_api.taskManagement;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private List<Task> tasks = new ArrayList<>();

    public TaskController() {
        
        tasks.add(new Task(1L, "Finish assignment 5", "Complete task management API", false, "HIGH", "2026-02-12"));
        tasks.add(new Task(2L, "Gym session", "Leg day", true, "MEDIUM", "2026-02-10"));
        tasks.add(new Task(3L, "Read Spring Boot docs", "Chapter on REST controllers", false, "LOW", "2026-02-15"));
        tasks.add(new Task(4L, "Buy groceries", "Milk, eggs, bread", false, "HIGH", "2026-02-11"));
        tasks.add(new Task(5L, "Call mom", "Weekly check-in", true, "LOW", "2026-02-09"));
    }

    @GetMapping
    public List<Task> getAll() {
        return tasks;
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<Task> getById(@PathVariable Long taskId) {
        return tasks.stream()
                .filter(t -> t.getTaskId().equals(taskId))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/status")
    public List<Task> getByStatus(@RequestParam boolean completed) {
        return tasks.stream()
                .filter(t -> t.isCompleted() == completed)
                .collect(Collectors.toList());
    }

    @GetMapping("/priority/{priority}")
    public List<Task> getByPriority(@PathVariable String priority) {
        return tasks.stream()
                .filter(t -> t.getPriority().equalsIgnoreCase(priority))
                .collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task newTask) {
        tasks.add(newTask);
        return ResponseEntity.status(201).body(newTask);
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<Task> updateTask(@PathVariable Long taskId, @RequestBody Task updated) {
        return tasks.stream()
                .filter(t -> t.getTaskId().equals(taskId))
                .findFirst()
                .map(t -> {
                    t.setTitle(updated.getTitle());
                    t.setDescription(updated.getDescription());
                    t.setCompleted(updated.isCompleted());
                    t.setPriority(updated.getPriority());
                    t.setDueDate(updated.getDueDate());
                    return ResponseEntity.ok(t);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{taskId}/complete")
    public ResponseEntity<Task> markCompleted(@PathVariable Long taskId) {
        return tasks.stream()
                .filter(t -> t.getTaskId().equals(taskId))
                .findFirst()
                .map(t -> {
                    t.setCompleted(true);
                    return ResponseEntity.ok(t);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId) {
        boolean removed = tasks.removeIf(t -> t.getTaskId().equals(taskId));
        return removed ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
