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

import com.rnsolutions.stumblr.entity.QuotePost;
import com.rnsolutions.stumblr.service.PostService;

import junit.framework.Assert;

@RunWith(MockitoJUnitRunner.class)
public class NewQuotePostControllerTest {
	NewQuotePostController newQuotePostController;
	
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
		newQuotePostController = new NewQuotePostController();
		newQuotePostController.setPostService(postService);
		MockitoAnnotations.initMocks(NewQuotePostControllerTest.class);
	}
	
	@Test
	public void testHandleRequestInternal() throws Exception{
		
		String quote = "TestQuote";
		String source = "TEST";
		
		Mockito.when(request.getParameter("quote")).thenReturn(quote);
		Mockito.when(request.getParameter("source")).thenReturn(source);
		Mockito.doNothing().when(postService).saveOrUpdate(any(QuotePost.class));
		
		ModelAndView acutal = newQuotePostController.handleRequestInternal(request, response);
		
		Assert.assertEquals(acutal.getViewName(), "redirect:/homepage.blr");
		Mockito.verify(request,times(3)).getParameter("quote");
		Mockito.verify(request,times(2)).getParameter("source");
		Mockito.verify(postService).saveOrUpdate(any(QuotePost.class));
		
		
		
		
	}


}
