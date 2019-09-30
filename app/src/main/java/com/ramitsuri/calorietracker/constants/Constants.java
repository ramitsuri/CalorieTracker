package com.ramitsuri.calorietracker.constants;

import com.google.api.services.sheets.v4.SheetsScopes;

public class Constants {

    public static final String[] SCOPES = {SheetsScopes.SPREADSHEETS, SheetsScopes.DRIVE};
    public static final String TAG_ONE_TIME_BACKUP = "one_time_backup";
    public static final String TAG_SCHEDULED_BACKUP = "scheduled_backup";

    public class RequestCode {
        public static final int GOOGLE_SIGN_IN = 100;
    }

    public class Work {
        public static final String APP_NAME = "app_name";
        public static final String ACCOUNT_NAME = "account_name";
        public static final String ACCOUNT_TYPE = "account_type";
        public static final String SPREADSHEET_ID = "spreadsheet_id";
        public static final String SHEET_ID = "sheet_id";
    }
}
