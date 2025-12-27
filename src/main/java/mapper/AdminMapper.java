package mapper;

import model.User;
import model.builder.UserBuilder;
import view.AdminDTO.AdminDTO;
import view.builder.AdminDTOBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AdminMapper {
    public static AdminDTO convertUsertoAdminDTO(User user)
    {
        return new AdminDTOBuilder().setId((int) user.getId()).setUsername(user.getUsername()).build();
    }
    public static User convertAdminDTOtoUSER(AdminDTO adminDTO)
    {
       return new UserBuilder().setId(adminDTO.getId()).setUsername(adminDTO.getUsername()).build();
    }
    public static List<AdminDTO> convertUserListtoAdminDTOLIST(List<User> users)
    {
        if(users==null)
        {
            return new ArrayList<>();
        }
        return users.parallelStream().map(AdminMapper::convertUsertoAdminDTO).collect(Collectors.toList());
    }

}
