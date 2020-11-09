package ru.kpfu.group903.safiullin.repositories;

import ru.kpfu.group903.safiullin.exceptions.DatabaseException;
import ru.kpfu.group903.safiullin.models.Bank;
import ru.kpfu.group903.safiullin.utils.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

public class BankRepositoryJdbcImpl implements BankRepository {

    //language=SQL
    private static final String SQL_SAVE_BANK = "INSERT INTO bank VALUES (name=?, color=?)";
    //language=SQl
    private static final String SQL_FIND_BANK_BY_ID = "SELECT * FROM bank WHERE id=?";
    //language=SQL
    private static final String SQL_GET_BANK_BY_NAME = "SELECT * FROM bank WHERE name=?";
    //language=SQl
    private static final String SQL_GET_USER_BANKS =
                            "SELECT *, amount FROM bank " +
                            "LEFT JOIN user_bank on bank.id = user_bank.bank_id " +
                            "LEFT JOIN \"user\" on \"user\".id = user_bank.user_id " +
                            "WHERE user_id=?";
        //language=SQl
    private static final String SQL_GET_NOT_USER_BANKS =
                            "SELECT * FROM bank " +
                            "LEFT JOIN user_bank on bank.id = user_bank.bank_id " +
                            "LEFT JOIN \"user\" on \"user\".id = user_bank.user_id " +
                            "WHERE user_id<>?";
    //language=SQL
    private static final String SQL_GET_USER_BANK_BY_ID =
                                "SELECT *, amount FROM bank " +
                                "LEFT JOIN user_bank on bank.id = user_bank.bank_id " +
                                "LEFT JOIN \"user\" on \"user\".id = user_bank.user_id " +
                                "WHERE user_id=? AND bank_id=?";
    //language=SQL
    private static final String SQL_CHANGE_BANK_AMOUNT = "UPDATE user_bank SET amount = ? WHERE user_id=? and bank_id=?";
    //language=SQL
    private static final String SQL_ADD_BANK_FOR_USER = "INSERT INTO user_bank(user_id, bank_id, amount) VALUES (?, ?, ?)";
    //language=SQL
    private static final  String SQL_GET_ALL_BANKS = "SELECT * FROM bank";

    private final JdbcTemplate template;

    private final RowMapper<Bank> bankRowMapper = row -> Bank.builder()
            .id(row.getInt("id"))
            .name(row.getString("name"))
            .color(row.getString("color"))
            .build();

    public BankRepositoryJdbcImpl(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    @Override
    public void save(Bank entity) {
    }

    @Override
    public void update(Bank entity) {
    }

    @Override
    public void delete(Bank entity) {
    }

    @Override
    public Bank findById(Long id) {
        List<Bank> list = template.queryForList(SQL_FIND_BANK_BY_ID, bankRowMapper, id);
        if (list.isEmpty()) return null;
        return list.get(0);
    }

    @Override
    public List<Bank> findAll() {
        return template.queryForList(SQL_GET_ALL_BANKS, bankRowMapper);
    }


    @Override
    public List<Bank> getBanksByUser(Long userId) {
        RowMapper<Bank> rowMapper = row -> Bank.builder()
                .id(row.getInt("id"))
                .name(row.getString("name"))
                .color(row.getString("color"))
                .amount(row.getFloat("amount"))
                .build();
        return template.queryForList(SQL_GET_USER_BANKS, rowMapper, userId);
    }

    @Override
    public Bank getBankByName(String bankName) {
        List<Bank> list = template.queryForList(SQL_GET_BANK_BY_NAME, bankRowMapper, bankName);
        if (list.isEmpty()) return null;
        return list.get(0);
    }

    @Override
    public Bank getUserBankByID(Long userId, Long bankId) {
        RowMapper<Bank> rowMapper = row -> Bank.builder()
                .id(row.getInt("id"))
                .name(row.getString("name"))
                .color(row.getString("color"))
                .amount(row.getFloat("amount"))
                .build();
        List<Bank> list = template.queryForList(SQL_GET_USER_BANK_BY_ID, rowMapper, userId, bankId);
        if (list.isEmpty()) return null;
        return list.get(0);
    }

    @Override
    public List<Bank> getUserBanks(Long userId) {
        return template.queryForList(SQL_GET_USER_BANKS, bankRowMapper, userId);
    }

    @Override
    public void addBankForUser(Long userID, Long bankID, float amount) {
        template.update(SQL_ADD_BANK_FOR_USER, userID, bankID, amount);
    }

    @Override
    public void changeBankAmount(Long userID, Long bankID, float amount) {
        template.update(SQL_CHANGE_BANK_AMOUNT, amount, userID, bankID);
    }
}
