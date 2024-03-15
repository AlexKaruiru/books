package com.books.controller;

import com.books.model.BlogModel;
import com.books.model.CommentModel;
import com.books.service.BlogService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/blogs")
@RequiredArgsConstructor
public class BlogController {

    private final BlogService blogService;

    @PostMapping
    @Operation(summary = "Create a new blog")
    public ResponseEntity<Void> createBlog(@RequestHeader("X-AUTH-TOKEN") String token, @RequestBody BlogModel blogModel) {
        blogService.saveBlog(token, blogModel);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{blogId}")
    @Operation(summary = "Update an existing blog")
    public ResponseEntity<Void> updateBlog(@RequestHeader("X-AUTH-TOKEN") String token, @PathVariable Long blogId, @RequestBody BlogModel blogModel) {
        blogService.updateBlog(token, blogId, blogModel);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{blogId}")
    @Operation(summary = "Delete a blog")
    public ResponseEntity<Void> deleteBlog(@RequestHeader("X-AUTH-TOKEN") String token, @PathVariable Long blogId) {
        blogService.deleteBlog(token, blogId);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    @Operation(summary = "Get all blogs with comments")
    public ResponseEntity<List<BlogModel>> getAllBlogsWithComments() {
        List<BlogModel> blogs = blogService.findAllBlogsWithComments();
        return ResponseEntity.ok(blogs);
    }

    @PostMapping("/{blogId}/comments")
    @Operation(summary = "Create a new comment for a blog")
    public ResponseEntity<Void> createComment(@RequestHeader("X-AUTH-TOKEN") String token, @PathVariable Long blogId, @RequestBody CommentModel commentModel) {
        blogService.saveComment(token, blogId, commentModel);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/comments/{commentId}")
    @Operation(summary = "Update an existing comment")
    public ResponseEntity<Void> updateComment(@RequestHeader("X-AUTH-TOKEN") String token, @PathVariable Long commentId, @RequestBody CommentModel commentModel) {
        blogService.updateComment(token, commentId, commentModel);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/comments/{commentId}")
    @Operation(summary = "Delete a comment")
    public ResponseEntity<Void> deleteComment(@RequestHeader("X-AUTH-TOKEN") String token, @PathVariable Long commentId) {
        blogService.deleteComment(token, commentId);
        return ResponseEntity.ok().build();
    }
}
