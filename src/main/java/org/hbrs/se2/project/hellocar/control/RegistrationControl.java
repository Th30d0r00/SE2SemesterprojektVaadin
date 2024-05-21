package org.hbrs.se2.project.hellocar.control;

import org.hbrs.se2.demo.registration.RegistrationResult;
import org.hbrs.se2.project.hellocar.dao.UserDAO;
import org.hbrs.se2.project.hellocar.dtos.UserDTO;

public class RegistrationControl
{
    public RegistrationResult registerUser(UserDTO userDTO){

        RegistrationResult result = new RegistrationResult();
        UserDAO userDAO = new UserDAO();

        //check if User with this Email already exists
        UserDTO existingUser = userDAO.FindUserByEmail(userDTO.getEmail());
        if(existingUser == null)
        {
            result.setSuccess(false);
            result.setMessage("User with email " + userDTO.getEmail() + " already exists");
        }
        else
        {
            result.setSuccess(false);
            result.setMessage("User sucessfully registered");
        }
        return result;
    }
}
