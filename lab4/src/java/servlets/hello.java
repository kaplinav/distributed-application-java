
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.Cookie;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class hello extends HttpServlet {
    
    private boolean isNumber(String input) {
        return input.matches("-?\\d+(\\.\\d+)?");
    }
    
    private void processUserInput(PrintWriter out, HttpServletRequest request, HttpServletResponse response) {
        String input = request.getParameter("userInput");
        Cookie[] cookies = null;
        cookies = request.getCookies();
        
        if (input == null || input.isEmpty()){
            out.println("<h4>empty input. please try again...</h4>");
        /* input is number */
        } else if (isNumber(input)) {
            float sum = 0;
            
            if (cookies != null) {
                for (Cookie item :cookies)
                    sum += (item.getName().equals("numbersSum")) ? Float.parseFloat(item.getValue()) : 0; 
            }
            
            sum += Float.parseFloat(input);
            out.println("<h4>" + sum + "</h4>");
            response.addCookie(new Cookie("numbersSum", "" + sum));
        /* input is text */
        } else {
            if (cookies != null) {
                for (Cookie item :cookies)
                    if (item.getName().equals("textSum")) {
                        input += ":" + item.getValue();
                    } 
            }
            
            String[] textSum = input.split(":");
            
            for (String item : textSum)
                out.println("<h4>" + item + "</h4>");
            
            response.addCookie(new Cookie("textSum", input));
        }
    }
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Practice 4</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Practice 4</h1>");
            out.println("<h3>simple web application</h3>");
            out.println("<form action = \"hello\">");
            out.println("<table cellspacing=\"0\" cellpadding=\"4\">");
            out.println("<tr>");
            out.println("<td align=\"left\">Value:</td>");
            out.println("<td><input type =\"text\" maxlength=\"80\" name=\"userInput\"></td>");
            out.println("<td><input type =\"submit\" value=\"send\"></td>");
            out.println("</tr>");
            out.println("</table>");
            out.println("</form>");
            /* create response according to user input */
            processUserInput(out, request, response);
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
