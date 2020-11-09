package ru.kpfu.group903.safiullin.repositories;

import ru.kpfu.group903.safiullin.models.Record;
import ru.kpfu.group903.safiullin.services.DateService;
import ru.kpfu.group903.safiullin.utils.JdbcTemplate;

import javax.sql.DataSource;
import java.text.ParseException;
import java.util.List;

public class RecordRepositoryJdbcImpl implements RecordRepository {

    //language=SQL
    private static final String SQL_SAVE_RECORD = "INSERT INTO record(bank_id, category_id, user_id, value, descriptions, date) " +
            "VALUES (?, ?, ?, ?, ? ,?) ";
    //language=SQL
    private static final String SQL_UPDATE_RECORD = "UPDATE record " +
            "SET bank_id=?, category_id=?, user_id=?, value=?, descriptions=?, date=?" +
            "WHERE id=?";
    //language=SQL
    private static final String SQL_DELETE_RECORD = "DELETE FROM record WHERE id=?";
    //language=SQL
    private static final String SQL_GET_ALL_RECORDS = "SELECT * FROM record";
    //language=SQL
    private static final String SQL_GET_RECORD_BY_ID = "SELECT * FROM record WHERE id=?";
    //language=SQL
    private static final String SQL_GET_RECORD_BANK = "SELECT * FROM record INNER JOIN bank on record.bank_id = bank.id";
    //language=SQl
    private static final String SQL_FIND_RECORD_BY_ID = "SELECT * FROM record WHERE id=?";
    //language=SQL
    private static final String SQL_GET_USER_RECORDS =
            "SELECT * FROM record " +
                    "LEFT JOIN \"user\" on \"user\".id = record.user_id " +
                    "WHERE user_id=? ORDER BY date DESC";

    private final JdbcTemplate template;
    private CategoryRepository categoryRepository;
    private BankRepository bankRepository;
    private UserRepository userRepository;

    private final RowMapper<Record> recordRowMapper = row -> Record.builder()
            .id(row.getLong("id"))
            .bank(bankRepository.findById(row.getLong("bank_id")))
            .category(categoryRepository.findById(row.getLong("category_id")))
            .user(userRepository.findById(row.getLong("user_id")))
            .sum(row.getFloat("value"))
            .description(row.getString("descriptions"))
            .date(new DateService().toString(row.getLong("date")))
            .build();

    public RecordRepositoryJdbcImpl(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
        this.bankRepository = new BankRepositoryJdbcImpl(dataSource);
        this.categoryRepository = new CategoryRepositoryJdbcImpl(dataSource);
        this.userRepository = new UserRepositoryJdbcImpl(dataSource);
    }

    @Override
    public void save(Record record) throws ParseException {
        template.update(SQL_SAVE_RECORD, record.getBank().getId(), record.getCategory().getId(), record.getUser().getId(),
                record.getSum(), record.getDescription(), new DateService().toUnix(record.getDate()));
    }

    @Override
    public void update(Record record) throws ParseException {
        template.update(SQL_UPDATE_RECORD, record.getBank().getId(), record.getCategory().getId(), record.getUser().getId(),
                record.getSum(), record.getDescription(), new DateService().toUnix(record.getDate()), record.getId());
    }

    @Override
    public void delete(Record record) {
        template.update(SQL_DELETE_RECORD, record.getId());
    }

    @Override
    public Record findById(Long id) {
        List<Record> list = template.queryForList(SQL_GET_RECORD_BY_ID, recordRowMapper, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);

    }

    @Override
    public List<Record> findAll() {
        return template.queryForList(SQL_GET_ALL_RECORDS, recordRowMapper);
    }

    @Override
    public List<Record> getRecordsByUser(Long userID) {
        return template.queryForList(SQL_GET_USER_RECORDS, recordRowMapper, userID);
    }

    @Override
    public Record getRecordByID(Long recordID) {
         return template.queryForList(SQL_GET_RECORD_BY_ID, recordRowMapper, recordID).get(0);
    }
}
