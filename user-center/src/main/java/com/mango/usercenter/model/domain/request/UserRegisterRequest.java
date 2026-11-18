package com.mango.usercenter.model.domain.request;

import lombok.Data;

import java.io.Serializable;

/**
 * User Registration Request Body
 *
 * @author Baowen
 */
@Data
public class UserRegisterRequest implements Serializable {

    private static final long serialVersionUID = -3023285374576517481L;

    private String userAccount;

    private String userPassword;

    private String checkPassword;

    private String username;

    private String noteCode;
}
