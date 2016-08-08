/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rnsolutions.stumblr.webmvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.ParameterizableViewController;

import com.rnsolutions.stumblr.entity.LinkPost;
import com.rnsolutions.stumblr.service.PostService;

/**
 * <p>NewTextPostController class.</p>
 *
 * @author bsneade
 * @version $Id: $
 */
public class NewLinkPostController extends ParameterizableViewController {

    private static final Logger LOGGER = LoggerFactory.getLogger(NewLinkPostController.class);

    private PostService postService;

    /**
     * <p>Setter for the field <code>postService</code>.</p>
     *
     * @param postService
     *            a {@link com.rnsolutions.stumblr.service.PostService} object.
     */
    public void setPostService(PostService postService) {
        this.postService = postService;
    }

    /** {@inheritDoc} */
    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (!StringUtils.isBlank(request.getParameter("link"))) {
            //save the new post
            final LinkPost linkPost = new LinkPost();
            linkPost.setLink(request.getParameter("link"));
            linkPost.setTitle(request.getParameter("title"));

            LOGGER.info("handleRequestInternal: link{} title{}", request.getParameter("link"), request.getParameter("title"));
            postService.saveOrUpdate(linkPost);
            return new ModelAndView("redirect:/homepage.blr");
        }
        return super.handleRequestInternal(request, response);
    }

}
