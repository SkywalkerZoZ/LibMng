# ALTER USER 'root'@'localhost' IDENTIFIED BY 'qwer4396';
DROP DATABASE IF EXISTS lib_mng;
CREATE DATABASE IF NOT EXISTS lib_mng;
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
    # 防止一本书被多个人借用
    instanceId        INT,
    # 借阅日期
    borrowDate        DATE NOT NULL,
    # 借阅到期日期
    dueDate           DATE NOT NULL,
    # 归还时间
    returnDate        DATE DEFAULT NULL,
    # 延期日期, 为NULL则不延期
    lateRetDate       DATE DEFAULT NULL,
    # 借阅的审批状态,0为未审批, 1为同意, 2为拒绝
    borrowAprvStatus  INT  DEFAULT 0,
    # 延期的审批状态,Null为未申请, 0为未审批, 1为同意, 2为拒绝
    lateRetAprvStatus INT  DEFAULT NULL,
    FOREIGN KEY (userId) REFERENCES User (userId),
    FOREIGN KEY (instanceId) REFERENCES BookInstance (instanceId) ON DELETE CASCADE
);
CREATE TABLE Reservation
(
    rsvId  INT AUTO_INCREMENT PRIMARY KEY,
    userId INT,
    # 是否要unique
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

SET GLOBAL event_scheduler = ON;
CREATE EVENT update_borrow_perms_event
    ON SCHEDULE EVERY 1 DAY -- 或根据需要调整频率
    DO
    BEGIN
        UPDATE User u
        SET u.borrowPerms = 1
        WHERE NOT EXISTS (
            -- 仅选中那些没有任何未过期处罚记录的用户
            SELECT 1
            FROM Penalty p
            WHERE p.userId = u.userId
              AND p.endDate >= CURRENT_DATE())
          AND u.borrowPerms = 0; -- 仅更新当前不能借阅的用户
    END;




