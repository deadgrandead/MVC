package com.deadgrandead.repository;

import com.deadgrandead.model.Post;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
@Repository
public class PostRepositoryImpl implements PostRepository {

    private final ConcurrentHashMap<Long, Post> posts = new ConcurrentHashMap<>();
    private final AtomicLong currentId = new AtomicLong(0);

    @Override
    public List<Post> all() {
        return new ArrayList<>(posts.values());
    }

    @Override
    public Optional<Post> getById(long id) {
        return Optional.ofNullable(posts.get(id));
    }

    @Override
    public Post save(Post post) {
        if (post.getId() == 0) {
            long newId = currentId.incrementAndGet();
            post.setId(newId);
            posts.put(newId, post);
            return post;
        } else {
            if (posts.containsKey(post.getId())) {
                posts.put(post.getId(), post);
                return post;
            } else {
                return null;
            }
        }
    }

    @Override
    public void removeById(long id) {
        posts.remove(id);
    }
}
