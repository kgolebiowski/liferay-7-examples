package net.mypieceofthe.liferay.examples.frontentjsreact;

import com.liferay.portal.kernel.servlet.PortalWebResources;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.servlet.ServletContext;

@Component(immediate = true, service = PortalWebResources.class)
public class ReactWebResource implements PortalWebResources {

    public static final String RESOURCE_TYPE_REACTJS = "reactjs";

    @Override
    public String getContextPath() {
        return _servletContext.getContextPath();
    }

    @Override
    public long getLastModified() {
        return _bundle.getLastModified();
    }

    @Override
    public String getResourceType() {
        /*
            Using "return PortalWebResourceConstants.RESOURCE_TYPE_JS;" will actually replace Liferay "frontend-js-web"
            module with this one, causing hundredths of errors like:

            15:28:25,541 WARN  [http-nio-8080-exec-5][code_jsp:172] {code="404", msg="ProxyServlet: /o/topheadjsresource-react-web/jquery/jquery.js", uri=/o/topheadjsresource-react-web/jquery/jquery.js}
            15:28:25,546 WARN  [http-nio-8080-exec-1][code_jsp:172] {code="404", msg="ProxyServlet: /o/topheadjsresource-react-web/lexicon/bootstrap.js", uri=/o/topheadjsresource-react-web/lexicon/bootstrap.js}
            15:28:25,549 WARN  [http-nio-8080-exec-2][code_jsp:172] {code="404", msg="ProxyServlet: /o/topheadjsresource-react-web/loader/config.js", uri=/o/topheadjsresource-react-web/loader/config.js}
            15:28:25,553 WARN  [http-nio-8080-exec-2][code_jsp:172] {code="404", msg="ProxyServlet: /o/topheadjsresource-react-web/lodash/lodash.js", uri=/o/topheadjsresource-react-web/lodash/lodash.js}

            However, using custom value (not coming from PortalWebResourceConstants does not add any Javascript imports at all)
         */
        return RESOURCE_TYPE_REACTJS;
    }

    @Override
    public ServletContext getServletContext() {
        return _servletContext;
    }

    @Activate
    protected void activate(BundleContext bundleContext) {
        _bundle = bundleContext.getBundle();
    }

    @Reference(
            target = "(osgi.web.symbolicname=topheadjsresource-react-web)",
            unbind = "-"
    )
    protected void setServletContext(ServletContext servletContext) {
        _servletContext = servletContext;
    }

    private Bundle _bundle;
    private ServletContext _servletContext;
}
