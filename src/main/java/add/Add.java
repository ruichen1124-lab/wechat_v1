package add;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/Add")
public class Add extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Add() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.getRequestDispatcher("/add.html").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");

		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String password_confirm = request.getParameter("password_confirm");

		String msg = "";

		if (password.equals(password_confirm)) {

			System.out.println(email + "|" + password + "|" + password_confirm);

			String url = "jdbc:mysql://localhost/wechat_v1";
			String user = "root";
			String passwordConnect = "";

			try {
				Class.forName("com.mysql.cj.jdbc.Driver");

				Connection conn = DriverManager.getConnection(url, user, passwordConnect);

				String sql = "INSERT INTO `user_v2` (`email`, `PASSWORD`, `NAME`, `birthday`, `gender`, `STATUS`, `updateTime`) "
						+ "VALUES ('" + email + "', '" + password + "', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP())";

				Statement createStatement = conn.createStatement();

				createStatement.executeUpdate(sql);

				conn.close();

				msg = "ユーザー登録しました。<br><a href='login.html'>BACK</a>";

			} catch (Exception e) {

				e.printStackTrace();
				msg = "データベースのアクセスできない";

			}

		} else {
			msg = "パスワードが一致しません。";
		}

		response.getWriter().append(msg);
	}
}