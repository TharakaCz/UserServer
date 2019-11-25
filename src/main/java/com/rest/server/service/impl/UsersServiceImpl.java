package com.rest.server.service.impl;

import com.rest.server.helper.UsersDTO;
import com.rest.server.model.Users;
import com.rest.server.repository.UsersRepository;
import com.rest.server.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.internet.MimeMessage;
/*import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;*/
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class UsersServiceImpl implements UsersService {

    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private JavaMailSender javaMailSender;
    private Date date;
    /*private static String UPLOADED_FOLDER="./uploard/profile";*/
    private String veryfyKey;

    @Override
    public Object save(UsersDTO usersDTO) throws Exception {
        Users users = usersRepository.findByEmail(usersDTO.getEmail());

        if (users != null){
            return "This Email All Ready Registerd !";
        }else {
           if (usersDTO.getPassword().equals(usersDTO.getConfirmPassword())){
               users = new Users();
               MimeMessage message = javaMailSender.createMimeMessage();
               MimeMessageHelper messageHelper = new MimeMessageHelper(message,true);

               users.setBdate(date = new SimpleDateFormat("dd/MM/yyyy").parse(usersDTO.getBdate()));
               users.setEmail(usersDTO.getEmail());
               users.setGender(usersDTO.getGender());
               users.setName(usersDTO.getName());
               users.setPassword(Base64.getEncoder().encode(usersDTO.getPassword().getBytes()));
               users.setRegdate(new Date());
               users.setRole("CLIENT");
               users.setStatus("PENDING");
               veryfyKey = UUID.randomUUID().toString();
               users.setVeryfyCode(veryfyKey);
               if (usersRepository.save(users) != null){

                   messageHelper.setTo(usersDTO.getEmail());
                   messageHelper.setSubject("Succsess Registation");
                   messageHelper.setText("<h4>Hellow :"+" "+usersDTO.getName()+"</h4><br>" +
                           "<h5><b>Thanks For Join With Us .<b></h5><br><br>" +
                           "<p>Your Account Verification Code Is :-"+" "+veryfyKey+"</p><br>" +
                           "Have a Nice Day .",true);
                   javaMailSender.send(message);

                   return "Registation Compleat Pleace Check Your Email";
               }else {
                   return "System Error Occurd !";
               }

           }else {
               return "Password And Confirm Password Not Match Try Again !";
           }
        }
    }

    @Override
    public Object update(Long pid) throws Exception {
        Users users = usersRepository.findById(pid).get();

        UsersDTO usersDTO = getUser(users);
        return usersDTO;
    }

    @Override
    public Object delete(Long pid) throws Exception {

       /* Users users = usersRepository.deleteUser(pid);

        if (users != null){
            return "User Delete Succsess";
        }else {
            return "System Error Occures";
        }*/
       return null;
    }

    @Override
    public Object search(String email) throws Exception {

        Users users = usersRepository.findByEmail(email);

        UsersDTO usersDTO = getUser(users);
        return usersDTO;
    }

    @Override
    public Object getAll() throws Exception {

        List<Users>users = usersRepository.findAll();
        ArrayList<UsersDTO>usersDTOS = new ArrayList<>();
        users.forEach(each->{
            try {
                usersDTOS.add(getUser(each));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return usersDTOS;
    }

    @Override
    public Object login(String email, String password) throws Exception {

        if (!email.equals(null) && !password.equals(null)){
            Users users = usersRepository.findByEmailAndPassword(email,Base64.getEncoder().encode(password.getBytes()));
            if (users.getRole() == "ADMIN"){
                UsersDTO usersDTO = getUser(users);
                return usersDTO;
            }else {
                return null;
            }
        }else {
            return "You Are Not Authorized User !";
        }

    }

    @Override
    public Object setProfile(@RequestParam("email") String email,@RequestParam("file") MultipartFile multipartFile) throws Exception {

       if (multipartFile.getContentType().equals("image/png") || multipartFile.getContentType().equals("image/jpeg")){
           Users users = usersRepository.findByEmail(email);
           if (users != null){
               if (users.getStatus().equals("VERYFID")){
                  users.setImage(Base64.getEncoder().encode(multipartFile.getBytes()));
                  users.setImageType(multipartFile.getContentType());
                   if (usersRepository.save(users) != null){
                       return "Profile Picture Uplording Succsess";
                   }else {
                       return "System Error Occurd !";
                   }
               }else {
                   return "Account Veryfy Pleace";
               }
           }else {
               return "Email Not Registerd !";
           }
       }
       return "Uploard Valid Image Format Image";
    }

    @Override
    public Object accountVeryfy(String veryfyCode) throws Exception {

       Users users = usersRepository.findByVeryfyCode(veryfyCode);

       if (users != null){
           users.setStatus("VERYFID");
           if (usersRepository.save(users) != null){
               return "Account Veryfy Sccsessfully";
           }else {
               return "System Error Occurd !";
           }
       }else {
           return "User Cann't Be Find !";
       }
    }

    private UsersDTO getUser(Users users)throws Exception{

        if(users != null){
            UsersDTO usersDTO = new UsersDTO();
            usersDTO.setPid(users.getPid());
            usersDTO.setBdate(String.valueOf(users.getBdate()));
            usersDTO.setEmail(users.getEmail());
            usersDTO.setGender(users.getGender());
            usersDTO.setName(users.getName());
            usersDTO.setRegdate(users.getRegdate());
            usersDTO.setImage(Base64.getDecoder().decode(users.getImage()));
            usersDTO.setImageType(users.getImageType());
            return usersDTO;
        }else {
            return null;
        }
    }
}
