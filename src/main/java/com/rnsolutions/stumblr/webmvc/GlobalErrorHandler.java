package com.rnsolutions.stumblr.webmvc;

import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

public class GlobalErrorHandler extends SimpleMappingExceptionResolver {

    private static final String UUID2 = "uuid";
    private static final String EXCEPTION2 = "exception";
    private static final String REQUEST = "Request: %s raised uuid{} exception{}";
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalErrorHandler.class);
    public static final String DEFAULT_ERROR_VIEW = "errorPage";

    /**
     * Also enable logging to this classe's logger by default.
     */
    public GlobalErrorHandler() {
        // Turn logging on by default
        setWarnLogCategory(getClass().getName());
    }

    /**
     * Override the default to generate a log message with dynamic content.
     */
    @Override
    public String buildLogMessage(Exception e, HttpServletRequest req) {
        return "MVC exception: " + e.getLocalizedMessage();
    }

    /**
     * This method uses the older API and gets passed the handler (typically the <tt>@Controller</tt>) that generated the exception.
     */
    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
            Exception exception) {

       
        // Get the ModelAndView to use
        ModelAndView mav = super.doResolveException(request, response, handler, exception);

        UUID uuid = UUID.randomUUID();
        mav.addObject("url", request.getRequestURL());
        mav.addObject("timestamp", new Date());
        mav.addObject("status", 500);
        mav.addObject(UUID2, uuid);
        mav.addObject(EXCEPTION2, exception);
        mav.setViewName(DEFAULT_ERROR_VIEW);
        LOGGER.error(String.format(REQUEST, request.getRequestURL()), uuid, exception);
        return mav;
    }
}
