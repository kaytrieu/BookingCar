/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huytm_models;

import huytm_db.MyConnection;
import huytm_dtos.BookingDTO;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Kay
 */
public class BookingDAO implements Serializable {

    private Connection conn = null;
    private PreparedStatement preStm = null;
    private ResultSet rs = null;

    private void closeConnection() throws Exception {
        try {
            if (rs != null) {
                rs.close();
            }
            if (preStm != null) {
                preStm.close();
            }
            if (conn != null) {
                conn.close();
            }
        } finally {
        }
    }

    public boolean createBooking(BookingDTO dto) throws Exception {
        boolean check = false;
        try {
            dto.setTotalPrice(getTotal(dto));
            dto.setBookingID(getBookingId());
            String sql = "insert into Booking(BookingID, CarID, CustomerID, DriverID, MakerID, PickDate, "
                    + "DropDate, DiscountCode, MemberDiscount, TotalPrice, BookingTime,"
                    + " isBooked) values(?,?,?,?,?,?,?,?,?,?,?,?)";
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, dto.getBookingID());
            preStm.setString(2, dto.getCarID());
            preStm.setString(3, dto.getCustomerID());
            if (!dto.getDriverID().equals("")) {
                preStm.setString(4, dto.getDriverID());
            } else{
                preStm.setString(4, null);
            }
            preStm.setString(5, dto.getMakerID());
            preStm.setDate(6, java.sql.Date.valueOf(dto.getPickDate()));
            preStm.setDate(7, java.sql.Date.valueOf(dto.getDropDate()));
            preStm.setString(8, dto.getDiscountCode());
            preStm.setDouble(9, new CustomerDAO().getMemberDiscount(dto.getCustomerID()));
            preStm.setDouble(10, dto.getTotalPrice());
            preStm.setString(11, dto.getBookingTime());
            preStm.setBoolean(12, true);
            check = preStm.executeUpdate() > 0;
        } finally {
            closeConnection();
        }
        return check;
        //remember to catch duplicate in controller
    }

    public static void main(String[] args) {
        BookingDTO dto = new BookingDTO("B011", "A001", "C001", "D001", "S001", "CK50D", LocalDateTime.now().toString(), LocalDate.of(2019, 03, 16), LocalDate.now(), 0, 0, true);
        try {
            new BookingDAO().createBooking(dto);
        } catch (Exception ex) {
            Logger.getLogger(BookingDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public double getTotal(BookingDTO dto) throws Exception {
        double price = 0;

        try {
            String sql = "select PricePerDay from Car where CarID = ?";
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, dto.getCarID());
            rs = preStm.executeQuery();
            if (rs.next()) {
                double pdPrice = rs.getDouble("PricePerDay");

                long diffDay = ChronoUnit.DAYS.between(dto.getPickDate(), dto.getDropDate()) + 1;
                double memberDiscount = new CustomerDAO().getMemberDiscount(dto.getCustomerID());
                price += (pdPrice * diffDay * (1 - memberDiscount));
                sql = "select discountValue from Discount where DiscountCode = ?";
                preStm = conn.prepareStatement(sql);
                preStm.setString(1, dto.getDiscountCode());
                rs = preStm.executeQuery();
                if (rs.next()) {
                    price -= rs.getDouble("discountValue");
                }
                double driverFee = new DriverDAO().getPriceByType(dto.getDriverID());
                if (driverFee != 0) {
                    price += driverFee * diffDay;
                }
            }
        } finally {
            closeConnection();
        }
        return price;
    }

    public boolean returnCar(String bookingID) throws Exception {
        boolean check = false;
        try {
            String sql = "update Booking set isBooking = false where carID = ?";
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, bookingID);
            check = preStm.executeUpdate() > 0;
        } finally {
            closeConnection();
        }
        return check;
    }

    public BookingDTO viewDetail(String bookingID) throws Exception {
        BookingDTO dto = null;
        try {
            String sql = "select CarID, CustomerID, DriverID, MakerID, PickDate,"
                    + " DropDate, DiscountCode, MemberDiscount, TotalPrice, BookingTime,"
                    + " isBooked from Booking where BookingID = ?";
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, bookingID);
            rs = preStm.executeQuery();
            if (rs.next()) {
                String carID = rs.getString("CarID");
                String customerID = rs.getString("CustomerID");
                String driverID = rs.getString("DriverID");
                String makerID = rs.getString("MakerID");
                LocalDate pickDate = rs.getDate("PickDate").toLocalDate();
                LocalDate dropDate = rs.getDate("DropDate").toLocalDate();
                String discountCode = rs.getString("DiscountCode");
                double memberDiscount = rs.getDouble("MemberDiscount");
                double totalPrice = rs.getDouble("TotalPrice");
                String bookingTime = rs.getString("BookingTime");
                boolean isBooked = rs.getBoolean("isBooked");
                dto = new BookingDTO(bookingID, carID, customerID, driverID, makerID, discountCode, bookingTime, pickDate, dropDate, memberDiscount, totalPrice, isBooked);

            }
        } finally {
            closeConnection();
        }
        return dto;
    }

    public List<BookingDTO> listAllBooking() throws Exception {
        List<BookingDTO> result = null;
        BookingDTO dto = null;
        try {
            String sql = "select BookingID, pickDate, dropDate, totalPrice, isBooked from Booking";
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            rs = preStm.executeQuery();
            result = new ArrayList<>();
            while (rs.next()) {
                String bookingID = rs.getString("BookingID");
                LocalDate pickDate = rs.getDate("PickDate").toLocalDate();
                LocalDate dropDate = rs.getDate("DropDate").toLocalDate();
                double totalPrice = rs.getDouble("TotalPrice");
                boolean isBooked = rs.getBoolean("isBooked");
                dto = new BookingDTO(bookingID, pickDate, dropDate, totalPrice, isBooked);
                result.add(dto);
            }

        } finally {
            closeConnection();
        }
        return result;
    }

    private String getBookingId() throws Exception {
        String id = "B";
        try {
            String sql = "Select count(BookingID) as Total from Booking";
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            rs = preStm.executeQuery();
            if (rs.next()) {
                int total = rs.getInt("Total") + 1;
                if (total < 10) {
                    id += "00" + total;
                } else if (total < 100) {
                    id += "0";
                } else {
                    id += total + "";
                }
            }

        } finally {
            closeConnection();
        }
        return id;
    }

}
