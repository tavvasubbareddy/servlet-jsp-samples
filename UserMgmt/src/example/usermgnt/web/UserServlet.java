package example.usermgnt.web;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import example.usermgnt.dao.UserDAO;
import example.usermgnt.model.User;

/**
 * This Servlet acts as a page controller for the application, handling all
 * requests from the user.
 */

@WebServlet("/") // avoids mappings in deployment descriptor
public class UserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO userDAO;
    Connection connection;

    public void init() {
	userDAO = new UserDAO();
	connection = (Connection) getServletContext().getAttribute("DBConnection");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	String action = request.getServletPath();

	try {
	    switch (action) {
	    case "/new":
		showNewForm(request, response);
		break;
	    case "/insert":
		insertUser(request, response);
		break;
	    case "/delete":
		deleteUser(request, response);
		break;
	    case "/edit":
		showEditForm(request, response);
		break;
	    case "/update":
		updateUser(request, response);
		break;
	    default:
		listUser(request, response);
		break;
	    }
	} catch (SQLException ex) {
	    throw new ServletException(ex);
	}
    }

    private void listUser(HttpServletRequest request, HttpServletResponse response)
	    throws SQLException, IOException, ServletException {
	List<User> listUser = userDAO.selectAllUsers(connection);
	request.setAttribute("listUser", listUser);
	RequestDispatcher dispatcher = request.getRequestDispatcher("user-list.jsp");
	dispatcher.forward(request, response); // redirect happens at server end and not visible to client
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	RequestDispatcher dispatcher = request.getRequestDispatcher("user-form.jsp");
	dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
	    throws SQLException, ServletException, IOException {
	int id = Integer.parseInt(request.getParameter("id"));
	User existingUser = userDAO.selectUser(id, connection);
	RequestDispatcher dispatcher = request.getRequestDispatcher("user-form.jsp");
	request.setAttribute("user", existingUser);
	dispatcher.forward(request, response);

    }

    private void insertUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
	String name = request.getParameter("name");
	String email = request.getParameter("email");
	String country = request.getParameter("country");
	User newUser = new User(name, email, country);
	userDAO.insertUser(newUser, connection);
	response.sendRedirect("list"); // redirection happens at client end and it's visible to client
    }

    private void updateUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
	int id = Integer.parseInt(request.getParameter("id"));
	String name = request.getParameter("name");
	String email = request.getParameter("email");
	String country = request.getParameter("country");

	User book = new User(id, name, email, country);
	userDAO.updateUser(book, connection);
	response.sendRedirect("list");
    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
	int id = Integer.parseInt(request.getParameter("id"));
	userDAO.deleteUser(id, connection);
	response.sendRedirect("list");
    }
}
