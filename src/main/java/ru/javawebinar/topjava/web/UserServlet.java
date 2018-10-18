package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.repository.mock.InMemoryMealRepositoryImpl;
import ru.javawebinar.topjava.repository.mock.InMemoryUserRepositoryImpl;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class UserServlet extends HttpServlet {
    //private static final Logger log = getLogger(UserServlet.class);
    private static final Logger log = getLogger(UserServlet.class);

    private UserRepository repository;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        repository = new InMemoryUserRepositoryImpl();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        Integer id = Integer.valueOf(request.getParameter("id"));

        User user = repository.get(id);

        user.setEmail(request.getParameter("email"));
        user.setName(request.getParameter("name"));

        repository.save(user);

        /*
                id.isEmpty() ? null : Integer.valueOf(id),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));*/

        response.sendRedirect("users");

    }



        @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("forward to users");

        if(!repository.isAdmin(SecurityUtil.authUserId())){
            request.getRequestDispatcher("/").forward(request, response);
            return;
        }

        String action = request.getParameter("action");

        switch(action == null? "all": action){

            case "update":
                request.setAttribute("action", "update");

                Integer id = Integer.valueOf(request.getParameter("id"));

                User user = repository.get(id);
                request.setAttribute("user", user);

                request.getRequestDispatcher("/userForm.jsp").forward(request, response);
                break;
            case "delete":
                repository.delete(Integer.parseInt(request.getParameter("id")));
                break;
            case "all":
            default:
                List<User> users = repository.getAll();

                request.setAttribute("users", users);

                request.getRequestDispatcher("/users.jsp").forward(request, response);
        }

    }
}
