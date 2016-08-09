package com.rnsolutions.stumblr.webmvc;

import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.servlet.ModelAndView;

import com.rnsolutions.stumblr.entity.Post;
import com.rnsolutions.stumblr.service.PostService;

@RunWith(MockitoJUnitRunner.class)
public class HomepageControllerTest {

	@Mock
	PostService postService;

	@Mock
	MockHttpServletRequest request;

	@Mock
	MockHttpServletResponse response;

	HomepageController homepageController;

	@Mock
	ModelAndView modelAndView;

	@Before
	public void setUp() throws Exception {
		homepageController = new HomepageController();
		homepageController.setPostService(postService);
		MockitoAnnotations.initMocks(HomepageControllerTest.class);
	}

	@Test
	public void testHandleRequestInternalHttpServletRequestHttpServletResponse() throws Exception {
		List<Post> posts = Collections.emptyList();
		Mockito.when(postService.getPosts(0, 6)).thenReturn(posts);
		ModelAndView handleRequestInternal = homepageController.handleRequestInternal(request, response);
		Assert.assertEquals( handleRequestInternal.getModelMap().get("postingList"), posts);
	}

}
