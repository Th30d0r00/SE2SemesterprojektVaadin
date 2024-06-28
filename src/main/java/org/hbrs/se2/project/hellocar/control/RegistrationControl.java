package org.hbrs.se2.project.hellocar.control;

import org.hbrs.se2.demo.registration.RegistrationResult;
import org.hbrs.se2.project.hellocar.dao.UserDAO;
import org.hbrs.se2.project.hellocar.dtos.UserDTO;
import org.hbrs.se2.project.hellocar.services.db.exceptions.DatabaseLayerException;

public class RegistrationControl
{
    public RegistrationResult registerUser(UserDTO userDTO){

        RegistrationResult result = new RegistrationResult();
        UserDAO userDAO = new UserDAO();

        //check if User with this Email already exists
        try{
            UserDTO existingUser = userDAO.FindUserByEmail(userDTO.getEmail()); //handle DatabaseLayerException
            if(existingUser == null)
            {
                if(userDAO.AddUser(userDTO)){
                    result.setSuccess(true);
                    result.setMessage("User successfully registered.");
                }
                else{
                    result.setSuccess(false);
                    result.setMessage("Couldn't register user.");
                }
            }
            else
            {
                result.setSuccess(false);
                result.setMessage("User with email " + userDTO.getEmail() + " already exists");
            }
        }
        catch(DatabaseLayerException e){
            e.printStackTrace();
        }
        return result;
    }

}
