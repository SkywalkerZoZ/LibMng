CREATE DATABASE lib_mng;

USE lib_mng;

CREATE TABLE Users (
    UserID INT AUTO_INCREMENT PRIMARY KEY,
    Username VARCHAR(255) NOT NULL,
    Password VARCHAR(255) NOT NULL,
    Email VARCHAR(255),
    Avatar BLOB,
    BorrowPerms INT DEFAULT 1,
    UserRole ENUM('user', 'admin') NOT NULL
);

CREATE TABLE Books (
    ISBN VARCHAR(17) PRIMARY KEY,
    Title VARCHAR(255) NOT NULL,
    Author VARCHAR(255),
    Cover BLOB,
    Description VARCHAR(255),
    AvailNum INT DEFAULT 0,
    BorrowedNum INT DEFAULT 0
);

ALTER TABLE Books
ADD CONSTRAINT AvailNumCheck CHECK (AvailNum >= 0);

ALTER TABLE Books
ADD CONSTRAINT BorrowedNumCheck CHECK (BorrowedNum >= 0);

CREATE TABLE Borrowing (
    RecordID VARCHAR(36) PRIMARY KEY,
    UserID INT,
    ISBN VARCHAR(17),
    # 借阅日期
    BorrowDate DATE NOT NULL,
    # 借阅到期日期
    DueDate DATE NOT NULL,
    # 延期日期，为NULL则不延期
    LateRetDate DATE DEFAULT NULL,
    # 借阅的审批状态
    BorrowAprvStatus INT DEFAULT 0,
    # 延期的审批状态
    LateRetAprvStatus INT DEFAULT 0,
    FOREIGN KEY (UserID) REFERENCES Users(UserID),
    FOREIGN KEY (ISBN) REFERENCES Books(ISBN) ON DELETE CASCADE
);

CREATE TABLE Reservation (
    UserID INT,
    ISBN VARCHAR(17),
    FOREIGN KEY (UserID) REFERENCES Users(UserID),
    FOREIGN KEY (ISBN) REFERENCES Books(ISBN) ON DELETE CASCADE
);

CREATE TABLE Penalty (
    RecordID INT AUTO_INCREMENT PRIMARY KEY,
    AdminID INT,
    UserID INT,
    Reason VARCHAR(255),
    PenaltyDate DATE NOT NULL,
    EndDate DATE NOT NULL,
    FOREIGN KEY (AdminID) REFERENCES Users(UserID),
    FOREIGN KEY (UserID) REFERENCES Users(UserID)
);
