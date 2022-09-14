package com.sharkit.nextmonday.configuration.database;

import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class NextMondayMigration {

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(final SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE diary_task ADD COLUMN user_id TEXT");
            database.execSQL("ALTER TABLE diary_folder_template ADD COLUMN user_id TEXT");
            database.execSQL("ALTER TABLE diary_notate ADD COLUMN user_id TEXT");
        }
    };
}
