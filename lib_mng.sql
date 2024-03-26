# ALTER USER 'root'@'localhost' IDENTIFIED BY 'qwer4396';
DROP DATABASE lib_mng;
CREATE DATABASE lib_mng;
USE lib_mng;
CREATE TABLE User
(
    userId      INT AUTO_INCREMENT PRIMARY KEY,
    username    VARCHAR(255)           NOT NULL UNIQUE,
    password    VARCHAR(255)           NOT NULL,
    email       VARCHAR(255) UNIQUE,
    avatar      MEDIUMBLOB,
    # 借阅权限, 1表示正常,0表示不能借阅所有书籍
    borrowPerms INT DEFAULT 1,
    userRole    ENUM ('user', 'admin') NOT NULL
);
# 不同ISBN的书籍信息
CREATE TABLE BookInfo
(
    isbn        VARCHAR(17) PRIMARY KEY,
    title       VARCHAR(255) NOT NULL,
    author      VARCHAR(255),
    cover       MEDIUMBLOB,
    description VARCHAR(255),
    # 同一个ISBN的书放一起
    location    VARCHAR(255)
);

# 同一个ISBN号的书籍实体
CREATE TABLE BookInstance
(
    instanceId   INT AUTO_INCREMENT PRIMARY KEY,
    isbn         VARCHAR(17),
    # 借阅状态, 0为未借阅, 1为已借阅
    borrowStatus INT DEFAULT 0,
    addTime      DATETIME,
    FOREIGN KEY (isbn) REFERENCES BookInfo (isbn)
);

CREATE TABLE Borrowing
(
    borrowingId       INT AUTO_INCREMENT PRIMARY KEY,
    userId            INT,
    #防止一本书被多个人借用
    instanceId        INT UNIQUE,
    # 借阅日期
    borrowDate        DATE NOT NULL,
    # 借阅到期日期
    dueDate           DATE NOT NULL,
    # 延期日期, 为NULL则不延期
    lateRetDate       DATE DEFAULT NULL,
    # 借阅的审批状态,0为未审批, 1为已审批
    borrowAprvStatus  INT  DEFAULT 0,
    # 延期的审批状态,0为未审批, 1为已审批
    lateRetAprvStatus INT  DEFAULT 0,
    FOREIGN KEY (userId) REFERENCES User (userId),
    FOREIGN KEY (instanceId) REFERENCES BookInstance (instanceId) ON DELETE CASCADE
);
CREATE TABLE Reservation
(
    rsvId  INT AUTO_INCREMENT PRIMARY KEY,
    userId INT,
    isbn   VARCHAR(17),
    FOREIGN KEY (userId) REFERENCES User (userId),
    FOREIGN KEY (isbn) REFERENCES BookInfo (isbn) ON DELETE CASCADE
);
CREATE TABLE Penalty
(
    penaltyId   INT AUTO_INCREMENT PRIMARY KEY,
    adminId     INT,
    userId      INT,
    reason      VARCHAR(255),
    penaltyDate DATE NOT NULL,
    endDate     DATE NOT NULL,
    FOREIGN KEY (adminId) REFERENCES User (userId),
    FOREIGN KEY (userId) REFERENCES User (userId)
);


INSERT INTO User (username, password, email, userRole)
VALUES ('jia', '123456', 'admin@example.com', 'admin');

INSERT INTO User (username, password, email, userRole)
VALUES ('user1', 'password1', 'user1@example.com', 'user'),
       ('user2', 'password2', 'user2@example.com', 'user'),
       ('user3', 'password3', 'user3@example.com', 'user');

INSERT INTO BookInfo (isbn, title, author, description)
VALUES ('978-3-16-148410-0', 'Book 1', 'Author 1', 'Description of Book 1'),
       ('978-3-16-148410-1', 'Book 2', 'Author 2', 'Description of Book 2'),
       ('978-3-16-148410-2', 'Book 3', 'Author 3', 'Description of Book 3'),
       ('978-3-16-148410-3', 'Book 4', 'Author 4', 'Description of Book 4'),
       ('978-3-16-148410-4', 'Book 5', 'Author 5', 'Description of Book 5'),
       ('978-3-16-148410-5', 'Book 6', 'Author 6', 'Description of Book 6'),
       ('978-3-16-148410-6', 'Book 7', 'Author 7', 'Description of Book 7');

INSERT INTO BookInstance (isbn)
VALUES ('978-3-16-148410-0'),
       ('978-3-16-148410-0'),
       ('978-3-16-148410-0'),
       ('978-3-16-148410-1'),
       ('978-3-16-148410-1'),
       ('978-3-16-148410-1'),
       ('978-3-16-148410-2'),
       ('978-3-16-148410-2'),
       ('978-3-16-148410-2'),
       ('978-3-16-148410-3'),
       ('978-3-16-148410-3'),
       ('978-3-16-148410-3'),
       ('978-3-16-148410-4'),
       ('978-3-16-148410-4'),
       ('978-3-16-148410-4'),
       ('978-3-16-148410-5'),
       ('978-3-16-148410-5'),
       ('978-3-16-148410-5'),
       ('978-3-16-148410-6'),
       ('978-3-16-148410-6'),
       ('978-3-16-148410-6');

INSERT INTO Borrowing (userId, instanceId, borrowDate, dueDate)
VALUES (2, 1, NOW(), DATE_ADD(NOW(), INTERVAL 7 DAY)),
       (2, 4, NOW(), DATE_ADD(NOW(), INTERVAL 8 DAY)),
       (2, 7, NOW(), DATE_ADD(NOW(), INTERVAL 9 DAY)),
       (3, 10, NOW(), DATE_ADD(NOW(), INTERVAL 10 DAY)),
       (3, 13, NOW(), DATE_ADD(NOW(), INTERVAL 11 DAY)),
       (3, 16, NOW(), DATE_ADD(NOW(), INTERVAL 12 DAY));
