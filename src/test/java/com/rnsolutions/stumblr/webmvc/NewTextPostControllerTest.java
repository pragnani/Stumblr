package com.rnsolutions.stumblr.webmvc;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;

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

import com.rnsolutions.stumblr.entity.TextPost;
import com.rnsolutions.stumblr.service.PostService;

import junit.framework.Assert;

@RunWith(MockitoJUnitRunner.class)
public class NewTextPostControllerTest {
	NewTextPostController newTextPostController;
	
	@Mock
	PostService postService;

	@Mock
	MockHttpServletRequest request;

	@Mock
	MockHttpServletResponse response;

	@Mock
	ModelAndView modelAndView;

	
	@Before
	public void setUp() throws Exception {
		newTextPostController = new NewTextPostController();
		newTextPostController.setPostService(postService);
		MockitoAnnotations.initMocks(NewTextPostControllerTest.class);
	}
	
	@Test
	public void testHandleRequestInternal() throws Exception{
		
		String text = "Testtext";
		String title = "TEST";
		
		Mockito.when(request.getParameter("postText")).thenReturn(text);
		Mockito.when(request.getParameter("title")).thenReturn(title);
		Mockito.doNothing().when(postService).saveOrUpdate(any(TextPost.class));
		
		ModelAndView acutal = newTextPostController.handleRequestInternal(request, response);
		
		Assert.assertEquals(acutal.getViewName(), "redirect:/homepage.blr");
		Mockito.verify(request,times(3)).getParameter("postText");
		Mockito.verify(request,times(2)).getParameter("title");
		Mockito.verify(postService).saveOrUpdate(any(TextPost.class));
		
	}


}
