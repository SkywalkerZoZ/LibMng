CREATE DATABASE lib_mng;
USE lib_mng;
CREATE TABLE User (
    userId INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255),
    avatar BLOB,
    borrowPerms INT DEFAULT 1,
    userRole ENUM('user', 'admin') NOT NULL
);
CREATE TABLE Book (
    isbn VARCHAR(17) PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255),
    cover BLOB,
    description VARCHAR(255),
    availNum INT DEFAULT 0,
    borrowedNum INT DEFAULT 0,
    CONSTRAINT availNumCheck CHECK (availNum >= 0),
    CONSTRAINT borrowedNumCheck CHECK (borrowedNum >= 0)
);
CREATE TABLE Borrowing (
    recordId VARCHAR(36) PRIMARY KEY,
    userId INT,
    isbn VARCHAR(17),
    # 借阅日期
    borrowDate DATE NOT NULL,
    # 借阅到期日期
    dueDate DATE NOT NULL,
    # 延期日期，为NULL则不延期
    lateRetDate DATE DEFAULT NULL,
    # 借阅的审批状态
    borrowAprvStatus INT DEFAULT 0,
    # 延期的审批状态
    lateRetAprvStatus INT DEFAULT 0,
    FOREIGN KEY (userId) REFERENCES User(userId),
    FOREIGN KEY (isbn) REFERENCES Book(isbn) ON DELETE CASCADE
);
CREATE TABLE Reservation (
    userId INT,
    isbn VARCHAR(17),
    FOREIGN KEY (userId) REFERENCES User(userId),
    FOREIGN KEY (isbn) REFERENCES Book(isbn) ON DELETE CASCADE
);
CREATE TABLE Penalty (
    recordId INT AUTO_INCREMENT PRIMARY KEY,
    adminId INT,
    userId INT,
    reason VARCHAR(255),
    penaltyDate DATE NOT NULL,
    endDate DATE NOT NULL,
    FOREIGN KEY (adminId) REFERENCES User(userId),
    FOREIGN KEY (userId) REFERENCES User(userId)
);