#ALTER USER 'root'@'localhost' IDENTIFIED BY 'qwer4396';
DROP DATABASE IF EXISTS lib_mng;
CREATE DATABASE IF NOT EXISTS lib_mng;
USE lib_mng;
CREATE TABLE User
(
    userId      INT AUTO_INCREMENT PRIMARY KEY,
    username    VARCHAR(255)           NOT NULL UNIQUE,
    password    VARCHAR(255)           NOT NULL,
    email       VARCHAR(255) UNIQUE,
    money       DECIMAL(10, 2) DEFAULT 0,
    avatar      MEDIUMBLOB,
    # 借阅权限, 1表示正常,0表示不能借阅所有书籍
    # TODO borrowPerms修改为能借阅书籍的数量
    borrowPerms INT            DEFAULT 3,
    userRole    ENUM ('user', 'admin') NOT NULL
);
# 不同ISBN的书籍信息
CREATE TABLE BookInfo
(
    isbn        VARCHAR(17) PRIMARY KEY,
    title       VARCHAR(255) NOT NULL,
    author      VARCHAR(255),
    cover       MEDIUMBLOB,
    description VARCHAR(1024),
    # 同一个ISBN的书放一起
    location    VARCHAR(255)
);

# 同一个ISBN号的书籍实体
CREATE TABLE BookInstance
(
    instanceId   INT AUTO_INCREMENT PRIMARY KEY,
    isbn         VARCHAR(17),
    # 借阅状态, 0为未借阅, 1为已借阅
    borrowStatus INT      DEFAULT 0,
    addTime      DATETIME DEFAULT CURRENT_TIMESTAMP,
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
    # lateRetDate       DATE DEFAULT NULL,
    # 借阅的审批状态,0为未审批, 1为同意, 2为拒绝
    # borrowAprvStatus  INT  DEFAULT 0,
    # 延期的审批状态,Null为未申请, 0为未审批, 1为同意, 2为拒绝
    # lateRetAprvStatus INT  DEFAULT NULL,

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

CREATE TABLE Bill
(
    billId      CHAR(36) PRIMARY KEY,
    userId      INT            NOT NULL,
    -- 0表示扣费，1表示充值
    billSubject ENUM('recharge', 'penalty') NOT NULL,
    billAmount  DECIMAL(10, 2) NOT NULL,
    billDate    DATETIME DEFAULT CURRENT_TIMESTAMP,
    -- 0表示未结算，1表示已结算
    billStatus  INT      DEFAULT 0,
    FOREIGN KEY (userId) REFERENCES User (userId),
    INDEX (userId),
    INDEX (billDate)
);




-- 启用事件调度器
SET GLOBAL event_scheduler = ON;

DELIMITER //
-- 创建事件以更新借阅权限
CREATE EVENT IF NOT EXISTS update_borrow_perms_event
    ON SCHEDULE EVERY 1 DAY -- 或根据需要调整频率
    DO
    BEGIN
        -- 更新用户借阅权限
        UPDATE User u
        SET u.borrowPerms = 1
        WHERE NOT EXISTS (
            -- 仅选中那些没有任何未过期处罚记录的用户
            SELECT 1
            FROM Penalty p
            WHERE p.userId = u.userId
              AND p.endDate >= CURRENT_DATE()
        )
          AND u.borrowPerms = 0; -- 仅更新当前不能借阅的用户
    END//
DELIMITER ;

DELIMITER //
-- 创建每日自动扣款事件
CREATE EVENT IF NOT EXISTS auto_deduct_fees_event
    ON SCHEDULE EVERY 1 DAY
        STARTS CURRENT_TIMESTAMP
    DO
    BEGIN
        -- 查找逾期未还的借阅记录并扣除对应用户的余额
        UPDATE User
        SET money = money - 1
        WHERE userId IN (
            SELECT userId
            FROM Borrowing
            WHERE dueDate < CURRENT_DATE() AND returnDate IS NULL
        );

        -- 为每个逾期用户生成一条扣费账单
        INSERT INTO Bill (billId, userId, billSubject, billAmount, billDate, billStatus)
        SELECT UUID(), b.userId, 'penalty', -1, NOW(), 1
        FROM Borrowing b
        WHERE b.dueDate < CURRENT_DATE() AND b.returnDate IS NULL;
    END//
DELIMITER ;

