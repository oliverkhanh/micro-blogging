package com.khanhdd.micro_blogging_service.service;

import com.khanhdd.common_service.dto.FeedItem;
import com.khanhdd.common_service.dto.request.PostResponse;
import com.khanhdd.micro_blogging_service.feign_client.NotificationServiceClient;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    private final NotificationServiceClient notificationClient;

    public NotificationService(NotificationServiceClient notificationClient) {
        this.notificationClient = notificationClient;
    }

    @Async
    public void callApiToNotificationService(PostResponse postResponse) {
        FeedItem feedItem = toFeedItem(postResponse);

        notificationClient.sendNotification(feedItem);
    }

    private FeedItem toFeedItem(PostResponse postResponse) {
        FeedItem feedItem = new FeedItem();
        feedItem.setAuthorId(postResponse.getUserId());
        feedItem.setPostId(postResponse.getId());
        feedItem.setCreatedAt(postResponse.getCreatedAt());

        return feedItem;
    }
}
