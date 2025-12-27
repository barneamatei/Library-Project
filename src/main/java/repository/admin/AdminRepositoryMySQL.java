package repository.admin;

import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Table;
import model.User;
import model.builder.UserBuilder;
import repository.security.RightsRolesRepository;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.kernel.pdf.PdfDocument;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.FileOutputStream;
public class AdminRepositoryMySQL implements AdminRepository{
    private final Connection connection;
    private final RightsRolesRepository rightsRolesRepository;


    public AdminRepositoryMySQL(Connection connection,RightsRolesRepository rightsRolesRepository) {
        this.connection = connection;
        this.rightsRolesRepository=rightsRolesRepository;
    }
    @Override
    public List<User> employee_list() {
        List<User> employeeList=new ArrayList<>();
        try{
            Statement statement=connection.createStatement();
            String sql="SELECT u.* FROM user u JOIN user_role ur ON u.id = ur.user_id WHERE ur.role_id = 2;";
            ResultSet resultSet=statement.executeQuery(sql);
            while(resultSet.next())
            {
                User user=new UserBuilder()
                        .setId(resultSet.getInt("id"))
                        .setUsername(resultSet.getString("username"))
                        .setPassword(resultSet.getString("password"))
                        .setRoles(rightsRolesRepository.findRolesForUser(resultSet.getLong("id")))
                        .build();
                employeeList.add(user);
            }
        }catch(SQLException e)
        {
            e.printStackTrace();

        }
        return employeeList;
    }

    @Override
    public boolean employee_remove(User user) {
        String sql="DELETE FROM user WHERE username = ?;";
        try(PreparedStatement preparedStatement=connection.prepareStatement(sql)){
            preparedStatement.setString(1,user.getUsername());
            preparedStatement.executeUpdate();
        }catch(SQLException e)
        {
            e.printStackTrace();
            return false;
        }
     return true;
    }
    public boolean add_employee(User user) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO user (id, username, password) VALUES (null, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.executeUpdate();

            ResultSet rs = preparedStatement.getGeneratedKeys();
            rs.next();
            long userId = rs.getLong(1);
            user.setId(userId);

            rightsRolesRepository.addRolesToUser(user, user.getRoles());

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public User return_employee(String username) {
        User user = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM user WHERE username = ?"
            );
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = new UserBuilder()
                        .setId(resultSet.getInt("id"))
                        .setUsername(resultSet.getString("username"))
                        .setPassword(resultSet.getString("password"))
                        //.setRoles(rightsRolesRepository.findRolesForUser(resultSet.getLong("id")))
                        .build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
    public boolean make_pdf(String username){
        ByteArrayOutputStream byteArrayOutputStream=new com.itextpdf.io.source.ByteArrayOutputStream();
        try{
            PdfWriter writer=new PdfWriter(byteArrayOutputStream);
            PdfDocument pdf=new PdfDocument(writer);
            Document document=new Document(pdf);
            Table table=new Table(2);
            table.addHeaderCell(new Cell().add(new Paragraph("Username")));
            table.addHeaderCell(new Cell().add(new Paragraph("Book Title")));
            PreparedStatement preparedStatement=connection.prepareStatement(
                    "SELECT * FROM sales WHERE username=?"
            );
            preparedStatement.setString(1,username);
            ResultSet resultSet= preparedStatement.executeQuery();
            while(resultSet.next())
            {
                String bookname=resultSet.getString("bookname");
                String user = resultSet.getString("username");
                table.addCell(new Cell().add(new Paragraph(user)));
                table.addCell(new Cell().add(new Paragraph(bookname)));
            }
            document.add(new Paragraph("Sales Report for User: " + username));
            document.add(table);
            document.close();

            try (FileOutputStream fileOut = new FileOutputStream("sales_report.pdf")) {
                byteArrayOutputStream.writeTo(fileOut);
            }catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }

    }

}
