package ru.kpfu.group903.safiullin.repositories;

import ru.kpfu.group903.safiullin.models.User;
import ru.kpfu.group903.safiullin.utils.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

public class UserRepositoryJdbcImpl implements UserRepository {
    //language=SQl
    private static final String SQL_SAVE_USER = "INSERT INTO \"user\"(name, email, password) VALUES (?, ?, ?)";
    //language=SQl
    private static final String SQL_UPDATE_USER = "UPDATE \"user\" SET name=?, email=?, password=? WHERE id=?";
    //language=SQl
    private static final String SQL_DELETE_USER = "DELETE FROM \"user\" WHERE id=?";
    //language=SQL
    private static final String SQL_FIND_ALL = "SELECT * FROM \"user\"";
    //language=SQl
    private static final String SQL_FIND_USER_BY_ID = "SELECT * FROM \"user\" WHERE id=?";
    //language=SQl
    private static final String SQL_FIND_USER_BY_EMAIL = "SELECT * FROM \"user\" WHERE email=?";

    private final JdbcTemplate template;

    private final RowMapper<User> userRowMapper = row -> User.builder()
            .id(row.getInt("id"))
            .name(row.getString("name"))
            .email(row.getString("email"))
            .password(row.getString("password"))
            .build();

    public UserRepositoryJdbcImpl(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    @Override
    public void save(User user) {
        template.update(SQL_SAVE_USER, user.getName(), user.getEmail(), user.getPassword());
    }

    @Override
    public void update(User user) {
        template.update(SQL_UPDATE_USER, user.getName(), user.getEmail(), user.getPassword());
    }

    @Override
    public void delete(User user) {
        template.update(SQL_DELETE_USER, user.getId());
    }

    @Override
    public User findById(Long id) {
        List<User> list = template.queryForList(SQL_FIND_USER_BY_ID, userRowMapper, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<User> findAll() {
        return template.queryForList(SQL_FIND_ALL, userRowMapper);
    }

    @Override
    public User findByEmail(String email) {
        List<User> users = template.queryForList(SQL_FIND_USER_BY_EMAIL, userRowMapper, email);
        if (users.isEmpty()) {
            return null;
        }
        return users.get(0);
    }
}
