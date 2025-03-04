package com.example.demo.api;

import com.example.demo.dto.PostRequest;
import com.example.demo.dto.PostResponse;
import com.example.demo.dto.PostUpdateRequest;
import com.example.demo.dto.SimpleResponse;
import com.example.demo.service.PostService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/post")
public class PostApi {
    private final PostService postService;

    public PostApi(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public SimpleResponse createPost(@RequestBody PostRequest postRequest){
        return postService.createPost(postRequest);
    }

    @PutMapping("/{postId}")
    public SimpleResponse updatePost(@PathVariable Long postId , @RequestBody PostUpdateRequest postUpdateRequest){
       return postService.updatePost(postId, postUpdateRequest);
    }

    @GetMapping("/{postId}")
    public PostResponse getPost(@PathVariable Long postId){
        return postService.getPost(postId);
    }

    @GetMapping
    public List<PostResponse> getOwnPostAndSubscriptionsPost(){
        return postService.getOwnPostAndSubscriptionsPost();
    }

    @PutMapping("/chooseUser/{userIdToTag}/chooseYourPost/{postId}/chooseImageYouWantToTag/{imageId}")
    public SimpleResponse tagPersonInPost(@PathVariable Long userIdToTag,
                                          @PathVariable Long postId,
                                          @PathVariable Long imageId){
        return postService.tagPersonInPost(userIdToTag,postId,imageId);
    }
}
