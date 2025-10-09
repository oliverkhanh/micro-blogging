package com.khanhdd.common_service.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class FeedItem {
    private Long authorId;

    private Long postId;

    private LocalDateTime createdAt; // optional, nếu muốn lưu timestamp
}
