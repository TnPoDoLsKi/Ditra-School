package com.ditra.ditraschool.core.user;

import at.favre.lib.bytes.Bytes;
import at.favre.lib.crypto.bcrypt.BCrypt;
import com.ditra.ditraschool.core.user.models.LoginModel;
import com.ditra.ditraschool.core.user.models.LoginResponseModel;
import com.ditra.ditraschool.core.user.models.RegisterModel;
import com.ditra.ditraschool.utils.ErrorResponseModel;
import com.ditra.ditraschool.utils.Validators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Optional;

@Service
public class UserService  {

  @Autowired
  UserRepository userRepository;
  Validators validators = new Validators();


  public ResponseEntity<?> register(RegisterModel registerModel){

    if (userRepository.findUserByEmail(registerModel.getEmail()) != null) {
      ErrorResponseModel errorResponseModel = new ErrorResponseModel(HttpStatus.BAD_REQUEST.value(),600,"Email already exist");
      return new ResponseEntity<>(errorResponseModel, HttpStatus.BAD_REQUEST);
    }

    if (!validators.emailValidator(registerModel.getEmail())){
      ErrorResponseModel errorResponseModel = new ErrorResponseModel(HttpStatus.BAD_REQUEST.value(),601,"Email non valid");
      return new ResponseEntity<>(errorResponseModel, HttpStatus.BAD_REQUEST);
    }

    if (!validators.passwordValidator(registerModel.getPassword())){
      ErrorResponseModel errorResponseModel = new ErrorResponseModel(HttpStatus.BAD_REQUEST.value(),602,"Password non valid \n a digit must occur at least once\n at least one lowercase letter\n at least one digit i.e. 0-9\n at least one capital letter\n the length of password from minimum 6 letters to maximum 16 letters");
      return new ResponseEntity<>(errorResponseModel, HttpStatus.BAD_REQUEST);
    }

    if (!validators.usernameValidator(registerModel.getFullName())){
      ErrorResponseModel errorResponseModel =new ErrorResponseModel(HttpStatus.BAD_REQUEST.value(),604,"fullName non valid \nA regexp for general username entry. Which doesn't allow special characters other than underscore. Username must be of length ranging(3-30). starting letter should be a number or a character.\n");
      return new ResponseEntity<>(errorResponseModel, HttpStatus.BAD_REQUEST);
    }

    String bcryptHashPassword = BCrypt.withDefaults().hashToString(10, registerModel.getPassword().toCharArray());
    User user = new User(registerModel.getFullName(), bcryptHashPassword, registerModel.getEmail());
    user = userRepository.save(user);

    return new ResponseEntity<>(user, HttpStatus.OK);
  }

  public ResponseEntity<?> login(LoginModel loginModel, HttpSession session){

    User user = userRepository.findUserByEmail(loginModel.getEmail());

    if (user == null){
      ErrorResponseModel errorResponseModel = new ErrorResponseModel(HttpStatus.BAD_REQUEST.value(),605,"Email doesn't exist");
      return new ResponseEntity<>(errorResponseModel, HttpStatus.BAD_REQUEST);
    }

    BCrypt.Result result = BCrypt.verifyer().verify(loginModel.getPassword().toCharArray(), user.getPassword());

    if (!result.verified){
      ErrorResponseModel errorResponseModel = new ErrorResponseModel(HttpStatus.BAD_REQUEST.value(),607,"Wrong password");
      return new ResponseEntity<>(errorResponseModel, HttpStatus.BAD_REQUEST);
    }

    String token= tokenGenerator();
    user.setToken(token);
    userRepository.save(user);
    session.setAttribute("Token",token);

    return new ResponseEntity<>(new LoginResponseModel(token), HttpStatus.OK);
  }

  public Optional<UserDetails> findByTokenAndSuspended(String token, Boolean suspended) {

    if(token == null)
      return Optional.empty();

    Optional<User> user = userRepository.findByTokenAndSuspended(token,suspended);

    if(!user.isPresent())
      return Optional.empty();

    User user1 = user.get();
    UserDetails userDetails= new org.springframework.security.core.userdetails.User(user1.getId().toString(), user1.getPassword(), true, true, true, true,
        AuthorityUtils.createAuthorityList(user1.getRole()));

    return Optional.of(userDetails);
  }

  public ResponseEntity<?> logout(Principal principal) {

    long userId = Long.valueOf(principal.getName());

    User user = userRepository.findUserById(userId);
    user.setToken(null);

    userRepository.save(user);

    return new ResponseEntity<>(HttpStatus.OK);
  }

  public ResponseEntity<?> verifyAccess() {
    return new ResponseEntity<>(HttpStatus.OK);
  }

  public String tokenGenerator() {
    Bytes b = Bytes.wrap(Bytes.random(64)).hashSha256();
    b.copy().reverse();
    String token = b.encodeHex();
    return token;
  }
}
