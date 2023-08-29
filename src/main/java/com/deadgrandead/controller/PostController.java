package com.deadgrandead.controller;

import com.deadgrandead.exception.NotFoundException;
import com.deadgrandead.model.Post;
import com.deadgrandead.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService service;

    public PostController(PostService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Post>> all() {
        return ResponseEntity.ok(service.all());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable long id) {
        try {
            return ResponseEntity.ok(service.getById(id));
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found");
        }
    }

    @PostMapping
    public ResponseEntity<Post> save(@RequestBody Post post) {
        return ResponseEntity.ok(service.save(post));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> removeById(@PathVariable long id) {
        try {
            service.removeById(id);
            return ResponseEntity.ok("Post removed successfully");
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found");
        }
    }
}
