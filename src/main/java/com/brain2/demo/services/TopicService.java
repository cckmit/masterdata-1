package com.brain2.demo.services;

import com.brain2.demo.models.Post;
import com.brain2.demo.models.Tag;
import com.brain2.demo.models.TopicTags;
import com.brain2.demo.models.TopicTags.TopicTagKey;
import com.brain2.demo.records.PostTransport;
import com.brain2.demo.repos.TopicTagsRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Merges updates to object, used for for patch rest for example
 */
@Service
public class TopicService {
    @Autowired
    TopicTagsRepo topicTagsRepo;

    public void addTopicTag(final PostTransport postTransport, final Post post, Tag tag) {
        topicTagsRepo.findByTag_IdAndTopic_Id(tag.getId(), postTransport.topicID()).orElseGet(() -> {
            final TopicTags topicTag = new TopicTags();
            topicTag.setTag(tag);
            topicTag.setTopic(post.getTopic());
            topicTag.setId(TopicTagKey.newTopicTagKey(post.getTopic().getId(), tag.getId()));
            return topicTagsRepo.save(topicTag);
        });
    }

}