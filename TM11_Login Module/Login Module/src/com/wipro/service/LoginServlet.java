package com.wipro.service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wipro.DBUtil.DBConnection;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		boolean isValidUser=false;
		try {
		Connection con = DBConnection.getConnection();
		PreparedStatement pstmt = con.prepareStatement("select * from user_table where username=? and password =?");
		pstmt.setString(1, username);
		pstmt.setString(2, password);
		ResultSet rs = pstmt.executeQuery();
		if(rs.next()) {
			isValidUser = true;
		}
		pstmt.close();
		con.close();
		}
		catch(Exception e) {
			System.out.println("Could not connect...");
		}
		if(isValidUser) {
			request.setAttribute("username", username);
			request.getRequestDispatcher("success.jsp").forward(request,response);
		}
		else {
			request.setAttribute("msg", "Invalid Credentials! Try again...");
			request.getRequestDispatcher("LoginPage.jsp").forward(request, response);
		}
	}

}
