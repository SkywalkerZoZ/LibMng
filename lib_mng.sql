ALTER USER 'root'@'localhost' IDENTIFIED BY 'qwer4396';
DROP DATABASE lib_mng;
CREATE DATABASE lib_mng;
USE lib_mng;
CREATE TABLE User (
    userId INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255),
    avatar BLOB,
    # 借阅权限, 1表示正常,0表示不能借阅所有书籍
    borrowPerms INT DEFAULT 1,
    userRole ENUM('user', 'admin') NOT NULL
);
# 不同ISBN的书籍
CREATE TABLE BookCatalog(
    isbn VARCHAR(17) PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255),
    cover BLOB,
    description VARCHAR(255)
);

# 同一个ISBN号的书籍实体
CREATE TABLE BookInstance (
    instanceId INT AUTO_INCREMENT PRIMARY KEY,
    isbn VARCHAR(17),
    # 借阅状态, 0为未借阅, 1为已借阅
    borrowStatus INT DEFAULT 0,
    addDate DATETIME,
    FOREIGN KEY (isbn) REFERENCES BookCatalog(isbn)
);

CREATE TABLE Borrowing (
    recordId INT AUTO_INCREMENT PRIMARY KEY,
    userId INT,
    instanceId INT,
    # 借阅日期
    borrowDate DATE NOT NULL,
    # 借阅到期日期
    dueDate DATE NOT NULL,
    # 延期日期, 为NULL则不延期
    lateRetDate DATE DEFAULT NULL,
    # 借阅的审批状态,0为未审批, 1为已审批
    borrowAprvStatus INT DEFAULT 0,
    # 延期的审批状态,0为未审批, 1为已审批
    lateRetAprvStatus INT DEFAULT 0,
    FOREIGN KEY (userId) REFERENCES User(userId),
    FOREIGN KEY (instanceId) REFERENCES BookInstance(instanceId) ON DELETE CASCADE
);
CREATE TABLE Reservation (
    userId INT,
    isbn VARCHAR(17),
    FOREIGN KEY (userId) REFERENCES User(userId),
    FOREIGN KEY (isbn) REFERENCES BookCatalog(isbn) ON DELETE CASCADE
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