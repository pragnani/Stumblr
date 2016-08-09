package com.rnsolutions.stumblr.dwr;

import java.util.List;

import com.rnsolutions.stumblr.entity.Post;
import com.rnsolutions.stumblr.service.PostService;

/**
 * DWR for fetching more posts
 *
 * @author bsneade
 */
public class PostDwr {

    private PostService postService;

    public void setPostService(PostService postService) {
        this.postService = postService;
    }

    public List<Post> getMorePosts(int offset) {

        //we only want 5, but we need to know if we need to "more post" link
        return postService.getPosts(offset, 5);

    }

}
