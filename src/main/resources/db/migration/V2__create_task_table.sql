CREATE TABLE TASK (
    ID TEXT PRIMARY KEY NOT NULL,
    NAME TEXT NOT NULL,
    DESCRIPTION TEXT,
    ASSIGNEE_ID TEXT,
    FOREIGN KEY (ASSIGNEE_ID) REFERENCES USERS(ID) ON DELETE SET NULL
);
