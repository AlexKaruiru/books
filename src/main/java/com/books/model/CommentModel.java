package com.books.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentModel {
    private Long commentId;
    private Long blogId;
    private Long userId;
    private String userNickname;
    private String content;
    private LocalDateTime creationDate;
    private LocalDateTime lastModifiedDate;
    private boolean edited;
}
