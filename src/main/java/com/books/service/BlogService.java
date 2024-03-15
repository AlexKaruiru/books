package com.books.service;

import com.backend.config.JwtProvider;
import com.backend.mapper.BlogMapper;
import com.backend.model.BlogModel;
import com.backend.model.CommentModel;
import com.backend.vo.UserVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogService {

    private final BlogMapper blogMapper;
    private final JwtProvider jwtProvider;

    @Transactional
    public void saveBlog(String token, BlogModel blogModel) {
        if (!jwtProvider.validateToken(token)) {
            throw new RuntimeException("Token has expired. Please log in again.");
        }

        UserVo userVo = jwtProvider.getUserVo(token);
        blogModel.setUserId(userVo.getUserId());

        blogMapper.saveBlog(blogModel);
    }

    @Transactional
    public void updateBlog(String token, Long blogId, BlogModel blogModel) {
        if (!jwtProvider.validateToken(token)) {
            throw new RuntimeException("Token has expired. Please log in again.");
        }

        UserVo userVo = jwtProvider.getUserVo(token);
        BlogModel existingBlog = blogMapper.findBlogById(blogId);
        if (existingBlog == null || !existingBlog.getUserId().equals(userVo.getUserId())) {
            throw new RuntimeException("Unauthorized access to update the blog.");
        }

        blogModel.setUserId(existingBlog.getUserId());
        blogModel.setBlogId(blogId);
        blogMapper.updateBlog(blogModel);
    }


    @Transactional
    public void deleteBlog(String token, Long blogId) {
        if (!jwtProvider.validateToken(token)) {
            throw new RuntimeException("Token has expired. Please log in again.");
        }

        UserVo userVo = jwtProvider.getUserVo(token);
        BlogModel existingBlog = blogMapper.findBlogById(blogId);
        if (existingBlog == null || !existingBlog.getUserId().equals(userVo.getUserId())) {
            throw new RuntimeException("Unauthorized access to delete the blog.");
        }

        blogMapper.deleteAllCommentsByBlogId(blogId);

        blogMapper.deleteBlog(blogId);
    }


    public List<BlogModel> findAllBlogsWithComments() {
        List<BlogModel> blogs = blogMapper.findAllBlogs();
        for (BlogModel blog : blogs) {
            List<CommentModel> comments = blogMapper.findAllCommentsByBlogId(blog.getBlogId());
            blog.setComments(comments);
        }
        return blogs;
    }

    @Transactional
    public void saveComment(String token, Long blogId, CommentModel commentModel) {
        if (!jwtProvider.validateToken(token)) {
            throw new RuntimeException("Token has expired. Please log in again.");
        }

        UserVo userVo = jwtProvider.getUserVo(token);
        commentModel.setUserId(userVo.getUserId());
        commentModel.setBlogId(blogId);

        blogMapper.saveComment(commentModel);
    }

    @Transactional
    public void updateComment(String token, Long commentId, CommentModel commentModel) {
        if (!jwtProvider.validateToken(token)) {
            throw new RuntimeException("Token has expired. Please log in again.");
        }

        UserVo userVo = jwtProvider.getUserVo(token);
        CommentModel existingComment = blogMapper.findCommentById(commentId);
        if (existingComment == null || !existingComment.getUserId().equals(userVo.getUserId())) {
            throw new RuntimeException("Unauthorized access to update the comment.");
        }

        commentModel.setCommentId(commentId);
        blogMapper.updateComment(commentModel);
    }

    @Transactional
    public void deleteComment(String token, Long commentId) {
        if (!jwtProvider.validateToken(token)) {
            throw new RuntimeException("Token has expired. Please log in again.");
        }

        UserVo userVo = jwtProvider.getUserVo(token);
        CommentModel existingComment = blogMapper.findCommentById(commentId);
        if (existingComment == null || !existingComment.getUserId().equals(userVo.getUserId())) {
            throw new RuntimeException("Unauthorized access to delete the comment.");
        }

        blogMapper.deleteComment(commentId);
    }
}
