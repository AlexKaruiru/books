package com.books.mapper;

import com.backend.model.BlogModel;
import com.backend.model.CommentModel;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface BlogMapper {

  void saveBlog(BlogModel blogModel);
  void updateBlog(BlogModel blogModel);
  void deleteBlog(Long blogId);
  List<BlogModel> findAllBlogs();
  BlogModel findBlogById(Long blogId);

  void saveComment(CommentModel commentModel);
  void updateComment(CommentModel commentModel);
  void deleteComment(Long commentId);
  CommentModel findCommentById(Long commentId);
  List<CommentModel> findAllCommentsByBlogId(Long blogId); 
  void deleteAllCommentsByBlogId(Long blogId);
}
