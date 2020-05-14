package com.userRed.redesigned.auth;

import org.springframework.stereotype.Repository;

import java.util.Optional;

/** this class is suppose to talk with the database*/
@Repository("UsersRepository")
public class FakeRedAppUserDAO implements RedesignedApplicationUserDAO {

    @Override
    public Optional<RedsignedApplicationUser> selecAppUser(String username) {
        return Optional.empty();
    }
}
