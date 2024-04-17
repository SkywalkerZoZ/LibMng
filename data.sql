INSERT INTO user (username, password, email, userRole)
VALUES ('zhangsan', '1234', '123456789@qq.com', 'admin'),
       ('lisi', '123456', '987654321@qq.com', 'user'),
       ('wangwu', '123', '333222@qq.com', 'user');

INSERT INTO bookinfo (isbn, title, author, description)
VALUES ('978-7-04-036307-7', 'Advanced Mathematics', 'Tongji University Mathematics Department',
        'Advanced mathematics textbook'),
       ('978-7-11-537885-0', 'Data Structures', 'Yan Weimin, Wu Weimin', 'Data structures textbook'),
       ('978-7-11-121382-6', 'Modern Operating Systems', 'Andrew S. Tanenbaum', 'Operating systems textbook'),
       ('978-7-11-137725-4', 'Principles of Compilation', 'Liu Jian', 'Compilation principles textbook'),
       ('978-7-54-463720-6', 'College English', 'Li Yinhua, Wang Deming', 'College English textbook'),
       ('978-0-30-727211-9', '1984', 'George Orwell', 'A utopian social science fiction novel'),
       ('978-7-02-000220-7', 'Dream of the Red Chamber', 'Cao Xueqin',
        'A chapter-based novel describing the dark feudal society'),
       ('978-7-30-252142-6', 'Journey to the West', 'Wu Chengen', 'A lengthy mythological novel in chapters'),
       ('978-7-02-001501-6', 'Water Margin', 'Shi Naian', 'A chapter-based novel describing heroic legends'),
       ('978-7-02-000872-8', 'Romance of the Three Kingdoms', 'Luo Guanzhong',
        'A lengthy historical novel in chapters'),
       ('978-7-55-960215-2', 'Those Things in the Ming Dynasty', 'Dangnian Mingyue',
        'A story about the history of the Ming Dynasty'),
       ('978-7-5080-9235-5', 'The Republic', 'Plato', 'A work discussing the problems of an ideal state'),
       ('978-7-56-952101-6', 'Politics', 'Aristotle', 'A work studying political regimes'),
       ('7-100-02270-3', 'General Theory of Law', 'Justinian', 'A legally effective work'),
       ('978-7-20-006699-9', 'Compendium of Materia Medica', 'Li Shizhen', 'A great work on medical science'),
       ('978-7-01-019167-6', 'Das Kapital', 'Karl Marx',
        'A work that reveals the laws of capitalist society development through deep analysis of the capitalist mode of production'),
       ('978-7-80-593998-8', 'One Thousand and One Nights', 'Arab', 'A gem of Arabian folklore literature'),
       ('978-7-50-014646-9', 'Hamlet', 'Shakespeare', 'The story of Prince Hamlet avenging his father in Denmark'),
       ('978-7-53-871984-0', 'Robinson Crusoe', 'Defoe', 'A novel showing the emerging bourgeoisie'),
       ('978-7-54-477734-6', 'Les Misérables', 'Hugo', 'A literature of sincere humanitarianism');

INSERT INTO BookInstance (isbn, borrowStatus, addTime)
VALUES ('978-7-04-036307-7', 1, NOW()),
       ('978-7-04-036307-7', 0, NOW()),
       ('978-7-11-121382-6', 1, NOW()),
       ('978-7-54-463720-6', 0, NOW()),
       ('978-7-54-463720-6', 1, NOW()),
       ('978-0-30-727211-9', 1, NOW()),
       ('978-0-30-727211-9', 1, NOW());

INSERT INTO Borrowing (userId, instanceId, borrowDate, dueDate)
VALUES (2, 3, NOW(), DATE_ADD(NOW(), INTERVAL 7 DAY)),
       (2, 1, NOW(), DATE_ADD(NOW(), INTERVAL 8 DAY)),
       (2, 6, NOW(), DATE_ADD(NOW(), INTERVAL 9 DAY)),
       (3, 7, NOW(), DATE_ADD(NOW(), INTERVAL 10 DAY)),
       (3, 5, NOW(), DATE_ADD(NOW(), INTERVAL 11 DAY));

INSERT INTO Reservation (userId, isbn)
VALUES (2, '978-0-30-727211-9'),
       (3, '978-7-11-121382-6');