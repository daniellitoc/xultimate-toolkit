package org.danielli.xultimate.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.danielli.xultimate.jdbc.support.incrementer.MySQLMaxValueIncrementer;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Servlet implementation class TestServlet
 */
@WebServlet("/TestServlet")
public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
		MySQLMaxValueIncrementer incrementer1 = context.getBean("primaryKey1Incrementer", MySQLMaxValueIncrementer.class);
		MySQLMaxValueIncrementer incrementer2 = context.getBean("primaryKey2Incrementer", MySQLMaxValueIncrementer.class);
		
		PrintWriter writer = response.getWriter();
		for (int i = 0 ; i < 5000; i++) {
			writer.println(incrementer1.nextLongValue());
		}
		for (int i = 0 ; i < 5000; i++) {
			writer.println(incrementer2.nextLongValue());
		}
	}

}
