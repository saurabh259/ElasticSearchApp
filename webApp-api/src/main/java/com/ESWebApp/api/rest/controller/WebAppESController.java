package com.ESWebApp.api.rest.controller;


import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import com.WebAppES.dto.PostListingResponse;
import com.WebAppES.dto.PostRequest;
import com.WebAppES.posts.model.PostBo;
import com.WebAppES.service.PostService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;


@Api(value = "WebAppES", description = "Rest APIs")
@RestController
@RequestMapping(value = "/api")
public class WebAppESController {
	
	@Autowired	
	private PostService postService;
	
	@ApiOperation(value = "Add post", position = 2)
	@ApiResponse(code = 200, message = "Successfully added post to ElasticSearch")
	@RequestMapping(value = "addPost", method = RequestMethod.POST)
	
	public ResponseEntity<Void> addPost(@RequestBody PostRequest postRequest , UriComponentsBuilder uriBuilder) {
		
		PostBo postBo = new PostBo();
		postBo.setId(postRequest.getPostId());
		postBo.setPostData(postRequest.getPostData());
		postBo.setUserName(postRequest.getUserName());
		
		postService.addPost(postBo);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uriBuilder.path("/post/{id}").buildAndExpand(postBo.getId()).toUri());
	    return new ResponseEntity<Void>(headers, HttpStatus.CREATED);

	}

	@ApiOperation(value = "List all posts", position = 3)
	@ApiResponse(code = 200, message = "Retrieve posts from ElasticSearch")
	@RequestMapping(value = "getPosts", method = RequestMethod.GET , produces = "application/json")
	
	public ResponseEntity<PostListingResponse> getPosts(){
		PostListingResponse response = new PostListingResponse();
		response.setPostList(postService.listAllPosts());
	    return new ResponseEntity<PostListingResponse>(response, HttpStatus.FOUND);

	}
	
	@ApiOperation(value = "Search posts", position = 4)
	@ApiResponse(code = 200, message = "Search posts using ElasticSearch")
	@RequestMapping(value = "searchPosts", method = RequestMethod.GET , produces = "application/json")
	
	public ResponseEntity<PostListingResponse> searchPosts(
			@RequestParam(value = "searchText", required = false) String searchText){

		PostListingResponse response = new PostListingResponse();
		response.setPostList(postService.searchPosts(searchText));
	    return new ResponseEntity<PostListingResponse>(response, HttpStatus.OK);

	}
}
