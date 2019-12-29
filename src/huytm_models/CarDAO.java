/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huytm_models;

import huytm_db.MyConnection;
import huytm_dtos.CarDTO;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Kay
 */
public class CarDAO implements Serializable {

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

    public boolean addCar(CarDTO dto) throws Exception {
        boolean check = false;
        try {
            String sql = "insert into Car(CarID, ImageLink, Model, Brand, Seat, PricePerDay, isUsing values(?,?,?,?,?,?,?)";
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, dto.getCarID());
            preStm.setString(2, dto.getImageLink());
            preStm.setString(3, dto.getModel());
            preStm.setString(4, dto.getBrand());
            preStm.setInt(5, dto.getSeat());
            preStm.setDouble(6, dto.getPdPrice());
            preStm.setBoolean(7, true);
            check = preStm.executeUpdate() > 0;
        } finally {
            closeConnection();
        }

        return check;
        //check duplicate 
    }

    public boolean updateCar(CarDTO dto) throws Exception {
        boolean check = false;
        try {
            String sql = "update Car set ImageLink = ?, Model = ?, Brand = ?, Seat = ?, PricePerDay = ?  where CarID = ? and ? not in (select CarID from Booking where isBooked = 1)";
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, dto.getImageLink());
            preStm.setString(2, dto.getModel());
            preStm.setString(3, dto.getBrand());
            preStm.setInt(4, dto.getSeat());
            preStm.setDouble(5, dto.getPdPrice());
            preStm.setString(6, dto.getCarID());
            preStm.setString(7, dto.getCarID());
            check = preStm.executeUpdate() > 0;

        } finally {
            closeConnection();
        }
        return check;
        //true = ok, false = "update failed"
    }

    public boolean deleteCar(String carID) throws Exception {
        boolean check = false;
        try {
            String sql = "update Car set isUsing = 0  where CarID = ? and ? not in (select CarID from Booking where isBooked = 1)";
            preStm = conn.prepareStatement(sql);

            preStm.setString(1, carID);
            preStm.setString(2, carID);
            check = preStm.executeUpdate() > 0;

        } finally {
            closeConnection();
        }
        return check;
        //true = ok, false = "Delete Failed"
    }

    public CarDTO viewDetailCar(String carID) throws Exception {
        CarDTO dto = new CarDTO();
        try {
            String sql = "select ImageLink, Model, Brand, Seat, PricePerDay from Car where CarID = ?";
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, carID);
            rs = preStm.executeQuery();
            if (rs.next()) {
                String imageLink = rs.getString("ImageLink");
                String model = rs.getString("Model");
                String brand = rs.getString("Brand");
                int seat = rs.getInt("Seat");
                double pdPrice = rs.getDouble("PricePerDay");
                dto = new CarDTO(carID, imageLink, model, brand, seat, pdPrice, true);
            }
        } finally {
            closeConnection();
        }
        return dto;
    }

    public List<CarDTO> listCarBySeat(int seat) throws Exception {
        List<CarDTO> carList = null;
        CarDTO car = null;
        String carID, imageLink, model, brand = null;
        int carSeat;
        double pdPrice;
        CarDTO dto = null;
        try {
            String sql = "";
            conn = MyConnection.getMyConnection();
            if (seat == 0) {
                sql = "select CarID, ImageLink, Model, Brand, Seat, PricePerDay from Car where isUsing = 1  and CarID not in (select CarID from Booking where isBooked = 1)";
                preStm = conn.prepareStatement(sql);
            } else {
                sql = "select CarID, ImageLink, Model, Brand, Seat, PricePerDay from Car where Seat = ? and isUsing = 1  and CarID not in (select CarID from Booking where isBooked = 1)";
                preStm = conn.prepareStatement(sql);
                preStm.setInt(1, seat);
            }
            rs = preStm.executeQuery();
            carList = new ArrayList<>();
            while (rs.next()) {
                carID = rs.getString("CarID");
                imageLink = rs.getString("ImageLink");
                model = rs.getString("Model");
                brand = rs.getString("Brand");
                carSeat = rs.getInt("Seat");
                pdPrice = rs.getDouble("PricePerDay");
                dto = new CarDTO(carID, imageLink, model, brand, carSeat, pdPrice, true);
                carList.add(dto);
            }
        } finally {
            closeConnection();
        }

        return carList;
    }
    
}
