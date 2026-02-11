package api.practice.bonus_q_api.userProfile;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserProfileController {

    private List<UserProfile> users = new ArrayList<>();

    public UserProfileController() {
        users.add(new UserProfile(1L, "john_doe", "john@example.com", "John Doe", 25, "Rwanda", "Software dev student", true));
        users.add(new UserProfile(2L, "axcel_rw", "axcel@ex.com", "Axcel Bro", 20, "Rwanda", "Spring Boot grinding 🔥", true));
        users.add(new UserProfile(3L, "sarah_ke", "sarah@example.com", "Sarah Keza", 22, "Kenya", "UI/UX designer", true));
        users.add(new UserProfile(4L, "paul_ug", "paul@example.com", "Paul Mukasa", 30, "Uganda", "Tech lead", false));
    }

    
    @GetMapping
    public ResponseEntity<ApiResponse<List<UserProfile>>> getAll() {
        return ResponseEntity.ok(new ApiResponse<>(true, "Users retrieved", users));
    }

    
    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserProfile>> getById(@PathVariable Long userId) {
        return users.stream()
                .filter(u -> u.getUserId().equals(userId))
                .findFirst()
                .map(user -> ResponseEntity.ok(new ApiResponse<>(true, "User found", user)))
                .orElse(ResponseEntity.ok(new ApiResponse<>(false, "User not found", null)));
    }

    
    @PostMapping
    public ResponseEntity<ApiResponse<UserProfile>> create(@RequestBody UserProfile newUser) {
        users.add(newUser);
        return ResponseEntity.status(201)
                .body(new ApiResponse<>(true, "User profile created successfully", newUser));
    }

    
    @PutMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserProfile>> update(@PathVariable Long userId, @RequestBody UserProfile updated) {
        return users.stream()
                .filter(u -> u.getUserId().equals(userId))
                .findFirst()
                .map(u -> {
                    u.setUsername(updated.getUsername());
                    u.setEmail(updated.getEmail());
                    u.setFullName(updated.getFullName());
                    u.setAge(updated.getAge());
                    u.setCountry(updated.getCountry());
                    u.setBio(updated.getBio());
                    u.setActive(updated.isActive());
                    return ResponseEntity.ok(new ApiResponse<>(true, "User updated", u));
                })
                .orElse(ResponseEntity.ok(new ApiResponse<>(false, "User not found", null)));
    }

    
    @PatchMapping("/{userId}/activate")
    public ResponseEntity<ApiResponse<UserProfile>> toggleActive(@PathVariable Long userId) {
        return users.stream()
                .filter(u -> u.getUserId().equals(userId))
                .findFirst()
                .map(u -> {
                    u.setActive(!u.isActive());
                    String msg = u.isActive() ? "User activated" : "User deactivated";
                    return ResponseEntity.ok(new ApiResponse<>(true, msg, u));
                })
                .orElse(ResponseEntity.ok(new ApiResponse<>(false, "User not found", null)));
    }

    
    @GetMapping("/search/username")
    public ResponseEntity<ApiResponse<List<UserProfile>>> searchByUsername(@RequestParam String username) {
        List<UserProfile> result = users.stream()
                .filter(u -> u.getUsername().toLowerCase().contains(username.toLowerCase()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(new ApiResponse<>(true, "Search results", result));
    }

    
    @GetMapping("/search/country/{country}")
    public ResponseEntity<ApiResponse<List<UserProfile>>> searchByCountry(@PathVariable String country) {
        List<UserProfile> result = users.stream()
                .filter(u -> u.getCountry().equalsIgnoreCase(country))
                .collect(Collectors.toList());
        return ResponseEntity.ok(new ApiResponse<>(true, "Users from " + country, result));
    }

    
    @GetMapping("/search/age-range")
    public ResponseEntity<ApiResponse<List<UserProfile>>> searchByAgeRange(@RequestParam int min, @RequestParam int max) {
        List<UserProfile> result = users.stream()
                .filter(u -> u.getAge() >= min && u.getAge() <= max)
                .collect(Collectors.toList());
        return ResponseEntity.ok(new ApiResponse<>(true, "Users aged " + min + "-" + max, result));
    }

   
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long userId) {
        boolean removed = users.removeIf(u -> u.getUserId().equals(userId));
        if (removed) {
            return ResponseEntity.ok(new ApiResponse<>(true, "User deleted", null));
        }
        return ResponseEntity.ok(new ApiResponse<>(false, "User not found", null));
    }
}
