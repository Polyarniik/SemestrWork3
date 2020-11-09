package ru.kpfu.group903.safiullin.repositories;

import ru.kpfu.group903.safiullin.exceptions.DatabaseException;
import ru.kpfu.group903.safiullin.models.Category;

public interface CategoryRepository extends CrudRepository<Category> {
    Category getRecordCategory(int recordID) throws DatabaseException;
}
