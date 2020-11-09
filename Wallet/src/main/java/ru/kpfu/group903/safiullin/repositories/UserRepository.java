package ru.kpfu.group903.safiullin.repositories;

import ru.kpfu.group903.safiullin.exceptions.DatabaseException;
import ru.kpfu.group903.safiullin.models.User;

public interface UserRepository extends CrudRepository<User> {

    User findByEmail(String email);

}
