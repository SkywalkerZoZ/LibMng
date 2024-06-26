package com.xdc5.libmng;

import com.xdc5.libmng.entity.*;
import com.xdc5.libmng.mapper.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class MapperTests {

    @Autowired
    private UserMapper userMapper;

//    @Test
//    public void testAddAdminUser() {
//        User user = new User();
//        user.setUsername("jia");
//        user.setPassword("123456");
////        user.setUserRole("user");
//        user.setUserRole("admin");
////        user.setEmail("test@gmail.com");
//
//        int rowsAffected = userMapper.addUser(user);
//        System.out.println("新增用户成功，受影响的行数：" + rowsAffected);
//        System.out.println("新插入的用户ID：" + user.getUserId());
//    }
//    @Test
//    public void testAddUser() {
//        User user = new User();
//        user.setUsername("user");
//        user.setPassword("123456");
//        user.setUserRole("user");
//
////        user.setEmail("test@gmail.com");
//
//        int rowsAffected = userMapper.addUser(user);
//        System.out.println("新增用户成功，受影响的行数：" + rowsAffected);
//        System.out.println("新插入的用户ID：" + user.getUserId());
//    }
//
//    @Test
//    public void testUpdateUser() {
//        User user=new User();
//        user.setUsername("testUser");
//        user = userMapper.getUsers(user).get(0);
//        user.setPassword("newPassword666");
//        int rowsAffected = userMapper.updateUser(user);
//        System.out.println("更新用户成功，受影响的行数：" + rowsAffected);
//    }
//
//    @Test
//    public void testDeleteUserById() {
//        int rowsAffected = userMapper.delUserById(1);
//        System.out.println("删除用户成功，受影响的行数：" + rowsAffected);
//    }
//
//    @Test
//    public void testDeleteUserByName() {
//        int rowsAffected = userMapper.delUserByName("testUser2");
//        System.out.println("删除用户成功，受影响的行数：" + rowsAffected);
//    }
//
//    @Test
//    public void testGetAllUsers() {
//        List<User> users = userMapper.getUsers(new User());
//        for (User user : users) {
//            System.out.println(user);
//        }
//    }
//    @Test
//    public void testGetUserById(){
//        User user=userMapper.getUserById(3);
//        System.out.println(user);
//    }
//
//    @Autowired
//    private BookInfoMapper bookInfoMapper;
//
//    @Test
//    public void testAddBookInfo() {
//        BookInfo book = new BookInfo();
//        //book.setIsbn("123456789");
//        book.setIsbn("978-3-16-148410-0");
//        book.setTitle("Test Book");
//        book.setAuthor("Test Author");
//        book.setDescription("Test Description");
//        int rowsAffected = bookInfoMapper.addBookInfo(book);
//        System.out.println("添加书籍目录成功，受影响的行数：" + rowsAffected);
//    }
//
//    @Test
//    public void testDelBookInfoByISBN() {
//        String isbn = "123456789"; // Assuming the book with this ISBN exists
//        int rowsAffected = bookInfoMapper.delBookInfoByISBN(isbn);
//        System.out.println("删除书籍目录成功，受影响的行数：" + rowsAffected);
//    }
//
//    @Test
//    public void testGetBooks() {
//        // Assuming there are some books in the database
//        List<BookInfo> books = bookInfoMapper.getBookInfo(new BookInfo());
//        System.out.println("获取到的书籍目录信息：");
//        for (BookInfo book : books) {
//            System.out.println(book);
//        }
//    }
//
//    @Test
//    public void testUpdateBookInfo() {
//        BookInfo book = new BookInfo();
//        book.setIsbn("123456789"); // Assuming the book with this ISBN exist
//        book.setTitle("Updated Test Book");
//        book.setAuthor("Updated Test Author");
//        book.setDescription("Updated Test Description");
//        int rowsAffected = bookInfoMapper.updateBookInfo(book);
//        System.out.println("更新书籍目录成功，受影响的行数：" + rowsAffected);
//    }
//
//
//    @Autowired
//    private BookInstanceMapper bookInstanceMapper;
//
//    @Test
//    public void testAddBookInstance() {
//        BookInstance bookInstance = new BookInstance();
//        bookInstance.setIsbn("978-3-16-148410-2");
//        bookInstance.setBorrowStatus(0);
//        int rowsAffected = bookInstanceMapper.addBookInstance(bookInstance);
//    }
//
//    @Test
//    public void testDelBookInstanceByInstanceId() {
//        int instanceId = 1; // Assuming the book instance with this ID exists
//        int rowsAffected = bookInstanceMapper.delBookInstanceById(instanceId);
//    }
//
//    @Test
//    public void testGetBookInstances() {
//        // Assuming there are some book instances in the database
//        List<BookInstance> bookInstances = bookInstanceMapper.getBookInstances(new BookInstance());
//        for (BookInstance bookInstance : bookInstances) {
//            System.out.println(bookInstance);
//        }
//    }
//
//    @Autowired
//    private BorrowingMapper borrowingMapper;
//
//    @Test
//    public void testAddBorrowing() {
//        Borrowing borrowing = new Borrowing();
//        borrowing.setUserId(2);
//        borrowing.setInstanceId(4);
//        borrowing.setBorrowDate(LocalDate.now());
//        borrowing.setDueDate(LocalDate.now().plusDays(7));
//        // 设置其他属性值
//        int rowsAffected = borrowingMapper.addBorrowing(borrowing);
//    }
//
//    @Test
//    public void testDelBorrowingByRecordId() {
//        int borrowingId = 1; // 假设存在此借阅记录的ID
//        int rowsAffected = borrowingMapper.delBorrowingById(borrowingId);
//    }
//
//    @Test
//    public void testGetBorrowing() {
//        Borrowing borrowing = new Borrowing();
//        borrowing.setUserId(1);
//        // 设置其他查询条件
//        List<Borrowing> borrowings = borrowingMapper.getBorrowing(borrowing);
//        for (Borrowing b : borrowings) {
//            System.out.println(b);
//        }
//    }
//
//    @Test
//    public void testUpdateBorrowing() {
//        Borrowing borrowing = new Borrowing();
//        borrowing.setBorrowingId(1);
//        borrowing.setLateRetDate(LocalDate.now().plusDays(9));
//        // 设置其他属性值
//        int rowsAffected = borrowingMapper.updateBorrowing(borrowing);
//    }
//
//    @Autowired
//    private ReservationMapper reservationMapper;
//
//    @Test
//    public void testAddReservation() {
//        Reservation reservation = new Reservation();
//        reservation.setUserId(1);
//        reservation.setIsbn("123456789"); // 假设需要预订的图书的 ISBN 号
//        int rowsAffected = reservationMapper.addReservation(reservation);
//
//    }
//
//    @Test
//    public void testDelReservationByRsvId() {
//        int rsvId = 1; // 假设存在此预订记录的ID
//        int rowsAffected = reservationMapper.delReservationById(rsvId);
//    }
//
//    @Test
//    public void testGetReservation() {
//        Reservation reservation = new Reservation();
//        reservation.setUserId(1);
//        // 设置其他查询条件
//        List<Reservation> reservations = reservationMapper.getReservation(reservation);
//        for (Reservation r : reservations) {
//            System.out.println(r);
//        }
//    }
//
//    @Test
//    public void testUpdateReservation() {
//        Reservation reservation = new Reservation();
//        reservation.setRsvId(1); // 假设需要更新的预订记录ID
//        reservation.setIsbn("123456789"); // 假设需要更新的 ISBN 号
//        int rowsAffected = reservationMapper.updateReservation(reservation);
//    }
//
//    @Autowired
//    private PenaltyMapper penaltyMapper;
//
//    @Test
//    public void testAddPenalty() {
//        Penalty penalty = new Penalty();
//        penalty.setAdminId(1);
//        penalty.setUserId(1);
//        penalty.setEndDate(LocalDate.now().plusDays(7)); // 设置处罚结束日期为当前日期的7天后
//        int rowsAffected = penaltyMapper.addPenalty(penalty);
//    }
//
//    @Test
//    public void testDelPenaltyByPenaltyId() {
//        int penaltyId = 1; // 假设存在此处罚记录的ID
//        int rowsAffected = penaltyMapper.delPenaltyById(penaltyId);
//    }
//
//    @Test
//    public void testGetPenalty() {
//        Penalty penalty = new Penalty();
//        penalty.setUserId(1);
//        // 设置其他查询条件
//        List<Penalty> penalties = penaltyMapper.getPenalty(penalty);
//        for (Penalty p : penalties) {
//            System.out.println(p);
//        }
//    }
//
//    @Test
//    public void testUpdatePenalty() {
//        Penalty penalty = new Penalty();
//        penalty.setPenaltyId(1); // 假设需要更新的处罚记录ID
//        penalty.setReason("Test Reason");
//        penalty.setEndDate(LocalDate.now().plusDays(14)); // 假设需要更新的处罚结束日期为当前日期的14天后
//        int rowsAffected = penaltyMapper.updatePenalty(penalty);
//    }
//
//    @Test
//    public void testGetBorrowAprv() {
//        // 测试获取 borrowAprvStatus 为 0 的借阅记录
//        List<Borrowing> approvedBorrowings = borrowingMapper.getBorrowAprv(0);
//        for (Borrowing borrowing : approvedBorrowings) {
//            System.out.println(borrowing);
//        }
//
//        // 测试获取 lateRetAprvStatus 不为 0 的借阅记录
//        List<Borrowing> lateApprovedBorrowings = borrowingMapper.getBorrowAprv(1);
//        for (Borrowing borrowing : lateApprovedBorrowings) {
//            System.out.println(borrowing);
//        }
//    }
}
