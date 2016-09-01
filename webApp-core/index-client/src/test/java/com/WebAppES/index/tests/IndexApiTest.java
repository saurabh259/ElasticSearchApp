package com.WebAppES.index.tests;

import java.util.List;

import javax.annotation.Resource;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.WebAppES.index.dao.impl.IndexESDaoImpl;
import com.WebAppES.posts.model.PostBo;
import com.fasterxml.jackson.core.JsonProcessingException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:app-index.xml"})
public class IndexApiTest {
	@BeforeClass
	public static void beforeClass() {
	
	} 
	
	@Resource
	private IndexESDaoImpl indexDao;
	
	
	public void addIndex()
	{
		PostBo post = new PostBo();
		post.setId("10018");
		post.setPostData("JavaTester X");
		post.setUserName("JavaTester X");
			indexDao.addToIndex(post);
	}
	
	
	public void getAllData()
	{
		List<PostBo> postbo =indexDao.getAllPosts();
		
		for(PostBo post:postbo){
			System.out.println("User:"+post.getUserName()+"::Post:"+post.getPostData());
		}
	}
	
	@Test
	public void searchPost()
	{
		List<PostBo> postbo =indexDao.searchPosts("3");
		
		for(PostBo post:postbo){
			System.out.println("User:"+post.getUserName()+"::Post:"+post.getPostData());
		}
		
	}
	

}
