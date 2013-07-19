package com.my120.market.filter;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * LoginFilter
 * 
 * @author cai.yc
 * 
 *         2012-11-24
 */
public class LoginFilter implements Filter {

    public static final String SESSION_USER_INFO_KEY = "MY120_MARKET_LOGIN_USER_INFO";

    private static FilterConfig filterConfig = null;
    private static ArrayList FilterArrayList = new ArrayList();
    private static String FilterList = new String("");
    private static String loginPage = new String("/login.htm");

    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
        FilterList = filterConfig.getInitParameter("FilterList");
        FilterArrayList = checkFilterList(FilterList);
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException,
            IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();
        String reqURI = req.getRequestURI().toString();

        // 判断用户请求URL是否需要登录方可访问，免过滤列表在web.xml配置
        boolean doFilter = true;
        int j = FilterArrayList.size();
        for (int i = 0; i < j; i++) {
            if (reqURI.indexOf(FilterArrayList.get(i).toString()) > -1) {
                doFilter = false;
                break;
            }
        }

        if (!doFilter) {
            // 访问模块免过滤
            chain.doFilter(request, response);
            return;
        } else {
            try {
                if ((session.getAttribute(SESSION_USER_INFO_KEY) == null)) {
                    // 未登录用户访问，转向登录页面
                    filterConfig.getServletContext().getRequestDispatcher(loginPage).forward(request, response);
                    return;
                } else {
                    chain.doFilter(request, response);
                    return;
                }
            } catch (Exception e) {
                filterConfig.getServletContext().getRequestDispatcher(loginPage).forward(request, response);

            }

        }

    }

    public void destroy() {
    }

    public ArrayList checkFilterList(String checkList) {
        ArrayList al = new ArrayList();
        int i = 0;
        while (checkList.indexOf(",") > 0) {
            i = checkList.indexOf(",");
            al.add(checkList.substring(0, i));
            checkList = checkList.substring(i + 1, checkList.length());
        }
        if (checkList.length() > 0) {
            al.add(checkList);
        }
        return al;
    }
}