INSERT INTO bookinfo (isbn, title, author, description, location)
VALUES
    ('9787115426406', 'Advanced Mathematics', 'Tongji University Mathematics Department', 'Advanced mathematics textbook', 'A-1'),
    ('9787302023685', 'Data Structures', 'Yan Weimin, Wu Weimin', 'Data structures textbook', 'A-2'),
    ('9787111573692', 'Modern Operating Systems', 'Andrew S. Tanenbaum', 'Operating systems textbook', 'A-3'),
    ('9787560611112', 'Principles of Compilation', 'Liu Jian', 'Compilation principles textbook', 'A-4'),
    ('9781328869333', '1984', 'George Orwell', 'A utopian social science fiction novel', 'A-6'),
    ('9787549596751', 'Dream of the Red Chamber', 'Cao Xueqin', 'A chapter-based novel describing the dark feudal society', 'A-7'),
    ('9787551813587', 'Journey to the West', 'Wu Chengen', 'A lengthy mythological novel in chapters', 'A-8'),
    ('9787020008742', 'Water Margin', 'Shi Naian', 'A chapter-based novel describing heroic legends', 'A-9'),
    ('9787807611288', 'Romance of the Three Kingdoms', 'Luo Guanzhong', 'A lengthy historical novel in chapters', 'B-1'),
    ('9787213046322', 'Those Things in the Ming Dynasty', 'Dangnian Mingyue', 'A story about the history of the Ming Dynasty', 'B-2'),
    ('9787301167205', 'The Republic', 'Plato', 'A work discussing the problems of an ideal state', 'B-3'),
    ('9787300051208', 'Politics', 'Aristotle', 'A work studying political regimes', 'B-4'),
    ('9787100003438', 'General Theory of Law', 'Justinian', 'A legally effective work', 'B-5'),
    ('9787506778435', 'Compendium of Materia Medica', 'Li Shizhen', 'A great work on medical science', 'B-6'),
    ('9787010001722', 'Das Kapital', 'Karl Marx', 'A work that reveals the laws of capitalist society development through deep analysis of the capitalist mode of production', 'B-7'),
    ('9789620865053', 'One Thousand and One Nights', 'Arab', 'A gem of Arabian folklore literature', 'B-8'),
    ('9780743477123', 'Hamlet', 'Shakespeare', 'The story of Prince Hamlet avenging his father in Denmark', 'B-9'),
    ('9787500142362', 'Robinson Crusoe', 'Defoe', 'A novel showing the emerging bourgeoisie', 'C-1'),
    ('9789575457068', 'Les Misérables', 'Hugo', 'A literature of sincere humanitarianism', 'C-2');

INSERT INTO user (username, password, email, userRole, money)
VALUES
    ('jia', '1234', '123456789@qq.com', 'admin', 100),
    ('lisi', '123456', '987654321@qq.com', 'user', 0),
    ('wangwu', '123', '333222@qq.com', 'user', 0);


INSERT INTO BookInstance (isbn, borrowStatus, addTime)
VALUES
    ('9787115426406', 0, NOW()),
    ('9787115426406', 0, NOW()),
    ('9787500142362', 0, NOW()),
    ('9787500142362', 0, NOW()),
    ('9780743477123', 0, NOW()),
    ('9780743477123', 0, NOW()),
    ('9789620865053', 0, NOW()),
    ('9787807611288', 0, NOW()),
    ('9787807611288', 0, NOW()),
    ('9781328869333', 0, NOW()),
    ('9781328869333', 0, NOW()),
    ('9781328869333', 0, NOW()),
    ('9781328869333', 0, NOW()),
    ('9781328869333', 0, NOW()),
    ('9781328869333', 0, NOW()),
    ('9781328869333', 0, NOW()),
    ('9781328869333', 0, NOW()),
    ('9787111573692', 0, NOW()),
    ('9787111573692', 0, NOW());
