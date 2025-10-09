package com.khanhdd.micro_blogging_service.service;

import com.khanhdd.common_service.dto.FeedItem;
import com.khanhdd.common_service.dto.request.PostResponse;
import com.khanhdd.micro_blogging_service.feign_client.FanoutServiceClient;
import org.springframework.stereotype.Service;

@Service
public class FanoutService {
  private final FanoutServiceClient fanoutClient;

  public FanoutService(FanoutServiceClient fanoutClient) {
    this.fanoutClient = fanoutClient;
  }

  public void callApiToFanoutService(PostResponse postResponse) {
    FeedItem feedItem = toFeedItem(postResponse);
    fanoutClient.fanout(feedItem);
  }

  private FeedItem toFeedItem(PostResponse postResponse) {
    FeedItem feedItem = new FeedItem();
    feedItem.setAuthorId(postResponse.getUserId());
    feedItem.setPostId(postResponse.getId());
    feedItem.setCreatedAt(postResponse.getCreatedAt());

    return feedItem;
  }
}
