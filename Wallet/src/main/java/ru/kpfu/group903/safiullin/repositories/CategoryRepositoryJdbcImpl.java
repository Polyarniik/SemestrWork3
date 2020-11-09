package ru.kpfu.group903.safiullin.repositories;

import ru.kpfu.group903.safiullin.exceptions.DatabaseException;
import ru.kpfu.group903.safiullin.models.Category;
import ru.kpfu.group903.safiullin.utils.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

public class CategoryRepositoryJdbcImpl implements CategoryRepository {
    //language=SQL
    private static final String SQL_SAVE_CATEGORY = "INSERT INTO category VALUES (name=?, color=?, iconname=?)";
    //language=SQL
    private static final String SQL_FIND_ALL = "SELECT * FROM category GROUP BY category.id";
    //language=SQl
    private static final String SQL_FIND_CATEGORY_BY_ID = "SELECT * FROM category WHERE id=?";
    //language=SQL
    private static final String SQL_GET_RECORD_CATEGORY =
            "SELECT * FROM record " +
                    "INNER JOIN category on record.category_id = category.id " +
                    "WHERE record.id=?";


    private final JdbcTemplate template;

    private final RowMapper<Category> categoryRowMapper = row -> Category.builder()
            .id(row.getInt("id"))
            .name(row.getString("name"))
            .color(row.getString("color"))
            .iconName(row.getString("iconName"))
            .build();

    public CategoryRepositoryJdbcImpl(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    @Override
    public void save(Category category) {
        template.update(SQL_SAVE_CATEGORY, category.getId(), category.getName(), category.getColor(), category.getIconName());
    }

    @Override
    public void update(Category entity) {
    }

    @Override
    public void delete(Category entity) {
    }

    @Override
    public Category findById(Long id) {
        List<Category> list = template.queryForList(SQL_FIND_CATEGORY_BY_ID, categoryRowMapper, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<Category> findAll() {
        return template.queryForList(SQL_FIND_ALL, categoryRowMapper);
    }

    @Override
    public Category getRecordCategory(int recordID) throws DatabaseException {
        List<Category> list = template.queryForList(SQL_GET_RECORD_CATEGORY, categoryRowMapper, recordID);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }
}
