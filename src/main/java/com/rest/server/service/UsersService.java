package com.rest.server.service;

import com.rest.server.helper.UsersDTO;
import org.springframework.web.multipart.MultipartFile;

public interface UsersService {

    public Object save(UsersDTO usersDTO)throws Exception;
    public Object update(Long pid)throws Exception;
    public Object delete(Long pid)throws Exception;
    public Object search(String email)throws Exception;
    public Object getAll()throws Exception;
    public Object login(String email,String password)throws Exception;
    public Object setProfile(String email,MultipartFile multipartFile)throws Exception;
    public Object accountVeryfy(String veryfyCode)throws Exception;
}
