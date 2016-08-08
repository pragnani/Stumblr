package com.rnsolutions.stumblr.webmvc;

import java.util.Collections;
import java.util.List;

import org.hibernate.mapping.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.servlet.ModelAndView;

import com.rnsolutions.stumblr.dao.PostDao;
import com.rnsolutions.stumblr.entity.Post;
import com.rnsolutions.stumblr.service.DefaultPostService;
import com.rnsolutions.stumblr.service.PostService;

public class HomepageControllerTest {

    @InjectMocks
    PostService postService;

    @Mock
    MockHttpServletRequest request;

    @Mock
    MockHttpServletResponse response;
    
    @Mock
    HomepageController homepageController;
    
    @Mock
    PostDao postDao;

    @Before
    public void setUp() throws Exception {
        postService= new DefaultPostService();
        MockitoAnnotations.initMocks(HomepageControllerTest.class);
    }

    @Test
    public void testHandleRequestInternalHttpServletRequestHttpServletResponse() throws Exception {
        
         List<Post> posts = Collections.emptyList();
         ReflectionTestUtils.setField(postService, "postDao", postDao);
         Mockito.when(postService.getPosts(0, 6)).thenReturn(posts);
         
        
        ModelAndView handleRequestInternal = homepageController.handleRequestInternal(request, response);
        Mockito.verify(handleRequestInternal).addObject("postingList", posts);
        Assert.assertEquals( handleRequestInternal.getModelMap().get("postingList"), posts);
    }

}
