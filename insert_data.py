from datetime import datetime, timedelta
import mysql.connector
import os

def insert_book_with_cover(mysql_config, book_data, asset_folder):
    """
    Insert books into the MySQL database along with their cover images.
    
    Args:
    - mysql_config (dict): Configuration dictionary for MySQL connection.
    - book_data (list): A list of tuples, each representing book info.
                        Each tuple must be in the format:
                        (isbn, title, author, description, image_filename, location)
    - asset_folder (str): The folder where the image files are stored.
    """
    # Connect to the MySQL database
    conn = mysql.connector.connect(**mysql_config)
    cursor = conn.cursor()
    # Insert each book and its cover into the database
    for isbn, title, author, description, image_filename, location in book_data:
        image_path = os.path.join(asset_folder, image_filename)
        print(image_path)
        with open(image_path, 'rb') as file:
            blob = file.read()
        try:
            cursor.execute('''
            INSERT INTO bookinfo (isbn, title, author, description, cover, location) VALUES (%s, %s, %s, %s, %s, %s)
            ''', (isbn, title, author, description, blob, location))
        except mysql.connector.Error as err:
            print("Something went wrong: {}".format(err))

    conn.commit()
    cursor.close()
    conn.close()
    print("Books and covers have been inserted successfully.")

# def insert_others(mysql_config,users_data,book_instances_data,borrowing_data,reservations_data):
def insert_others(mysql_config,users_data,book_instances_data):
    try:
        conn = mysql.connector.connect(**mysql_config)
        cursor = conn.cursor()

        # Insert users
        insert_users = """
        INSERT INTO user (username, password, email, userRole, money)
        VALUES (%s, %s, %s, %s, %s)
        """
        
        cursor.executemany(insert_users, users_data)

        # Insert book instances
        insert_book_instances = """
        INSERT INTO BookInstance (isbn, borrowStatus, addTime)
        VALUES (%s, %s, %s)
        """
        current_time = datetime.now()
        
        cursor.executemany(insert_book_instances, book_instances_data)

#         # Insert borrowing details
#         insert_borrowing = """
#         INSERT INTO Borrowing (userId, instanceId, borrowDate, dueDate)
#         VALUES (%s, %s, %s, %s)
#         """
#
#         cursor.executemany(insert_borrowing, borrowing_data)
#
#         # Insert reservations
#         insert_reservations = """
#         INSERT INTO Reservation (userId, isbn)
#         VALUES (%s, %s)
#         """
#
#         cursor.executemany(insert_reservations, reservations_data)

        # Commit changes
        conn.commit()
        print("All data inserted successfully.")

    except mysql.connector.Error as err:
        print("Error:", err)
    finally:
        if conn.is_connected():
            cursor.close()
            conn.close()
            print("Database connection is closed.")


if __name__ == "__main__":
    # MySQL connection configuration
    mysql_config = {
        'user': 'root',
        'password': 'qwer4396',
        'host': 'localhost',
        'database': 'lib_mng',
        'raise_on_warnings': True,
        'charset': 'binary'
    }

    # Define the path to your assets folder
    assets_path = './assets'

    # Define book data including the filenames of the cover images
    book_info = [
        ('9787115426406', 'Advanced Mathematics', 'Tongji University Mathematics Department', 'Advanced mathematics textbook', '高等数学.jpg', 'A-1'),
        ('9787302023685', 'Data Structures', 'Yan Weimin, Wu Weimin', 'Data structures textbook', '数据结构.jpg', 'A-2'),
        ('9787111573692', 'Modern Operating Systems', 'Andrew S. Tanenbaum', 'Operating systems textbook', '现代操作系统.jpg', 'A-3'),
        ('9787560611112', 'Principles of Compilation', 'Liu Jian', 'Compilation principles textbook', '编译原理.jpg', 'A-4'),
        # google未收录
#         ('978-7-54-463720-6', 'College English', 'Li Yinhua, Wang Deming', 'College English textbook', '大学英语.jpg', 'A-5'),
        ('9781328869333', '1984', 'George Orwell', 'A utopian social science fiction novel', '1984.jpg', 'A-6'),
        ('9787549596751', 'Dream of the Red Chamber', 'Cao Xueqin', 'A chapter-based novel describing the dark feudal society', '红楼梦.jpg', 'A-7'),
        ('9787551813587', 'Journey to the West', 'Wu Chengen', 'A lengthy mythological novel in chapters', '西游记.jpg', 'A-8'),
        # 返回两个结果
        ('9787020008742', 'Water Margin', 'Shi Naian', 'A chapter-based novel describing heroic legends', '水浒传.jpg', 'A-9'),
        ('9787807611288', 'Romance of the Three Kingdoms', 'Luo Guanzhong', 'A lengthy historical novel in chapters', '三国演义.jpg', 'B-1'),
        ('9787213046322', 'Those Things in the Ming Dynasty', 'Dangnian Mingyue', 'A story about the history of the Ming Dynasty', '明朝那些事儿.jpg', 'B-2'),
        ('9787301167205', 'The Republic', 'Plato', 'A work discussing the problems of an ideal state', '理想国.jpg', 'B-3'),
        ('9787300051208', 'Politics', 'Aristotle', 'A work studying political regimes', '政治学.jpg', 'B-4'),
        ('9787100003438', 'General Theory of Law', 'Justinian', 'A legally effective work', '法学总论.jpg', 'B-5'),
        ('9787506778435', 'Compendium of Materia Medica', 'Li Shizhen', 'A great work on medical science', '本草纲目.jpg', 'B-6'),
        ('9787010001722', 'Das Kapital', 'Karl Marx', 'A work that reveals the laws of capitalist society development through deep analysis of the capitalist mode of production', '资本论.jpg', 'B-7'),
        ('9789620865053', 'One Thousand and One Nights', 'Arab', 'A gem of Arabian folklore literature', '一千零一夜.jpg', 'B-8'),
        ('9780743477123', 'Hamlet', 'Shakespeare', 'The story of Prince Hamlet avenging his father in Denmark', '哈姆雷特.jpg', 'B-9'),
        ('9787500142362', 'Robinson Crusoe', 'Defoe', 'A novel showing the emerging bourgeoisie', '鲁滨逊漂流记.jpg', 'C-1'),
        ('9789575457068', 'Les Misérables', 'Hugo', 'A literature of sincere humanitarianism', '悲惨世界.jpg', 'C-2')
    ]

    users_data = [
            ('jia', '1234', '123456789@qq.com', 'admin', 100),
            ('lisi', '123456', '987654321@qq.com', 'user', 0),
            ('wangwu', '123', '333222@qq.com', 'user', 0)
        ]
    
    current_datetime = datetime.now()
    current_date = datetime.now().date()
    book_instances_data = [
            ('9787115426406', 0, current_datetime),
            ('9787115426406', 0, current_datetime),
            ('9787500142362', 0, current_datetime),
            ('9787500142362', 0, current_datetime),
            ('9780743477123', 0, current_datetime),
            ('9780743477123', 0, current_datetime),
            ('9789620865053', 0, current_datetime),
            ('9787807611288', 0, current_datetime),
            ('9787807611288', 0, current_datetime),
            ('9781328869333', 0, current_datetime),
            ('9781328869333', 0, current_datetime),
            ('9781328869333', 0, current_datetime),
            ('9781328869333', 0, current_datetime),
            ('9781328869333', 0, current_datetime),
            ('9781328869333', 0, current_datetime),
            ('9781328869333', 0, current_datetime),
            ('9781328869333', 0, current_datetime),
            ('9781328869333', 0, current_datetime),
            ('9787111573692', 0, current_datetime),
            ('9787111573692', 0, current_datetime)
        ]
#     borrowing_data = [
#             (2, 3, current_date, current_date + timedelta(days=7)),
#             (2, 1, current_date, current_date + timedelta(days=8)),
#             (2, 6, current_date, current_date + timedelta(days=9)),
#             (3, 7, current_date, current_date + timedelta(days=10)),
#             (3, 5, current_date, current_date + timedelta(days=11))
#         ]
#     reservations_data = [
#             (2, '978-0-30-727211-9'),
#             (3, '978-7-11-121382-6')
#         ]
    insert_book_with_cover(mysql_config, book_info, assets_path)
    # Error: 1452 (23000): Cannot add or update a child row: a foreign key constraint fails (`lib_mng`.`borrowing`, CONSTRAINT `borrowing_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`))
    insert_others(mysql_config,users_data,book_instances_data)
#     #     insert_others(mysql_config,users_data,book_instances_data,borrowing_data,reservations_data)
