/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huytm_models;

import huytm_db.MyConnection;
import huytm_dtos.DriverDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Kay
 */
public class DriverDAO {

    private Connection conn = null;
    private PreparedStatement preStm = null;
    private ResultSet rs = null;

    private void closeConnection() throws Exception {
        if (rs != null) {
            rs.close();
        }
        if (preStm != null) {
            preStm.close();
        }
        if (conn != null) {
            conn.close();
        }
    }

    public boolean addDriver(DriverDTO dto) throws Exception {
        boolean check = false;
        try {
            String sql = "insert into Driver(DriverID, Name, LicenseNumber, Age, "
                    + "LicenseType, isWorking) values(?,?,?,?,?,1)";
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, dto.getDriverID());
            preStm.setString(2, dto.getName());
            preStm.setString(3, dto.getLicenseNumber());
            preStm.setInt(4, dto.getAge());
            preStm.setString(5, dto.getLicenseType());
            check = preStm.executeUpdate() > 0;
        } finally {
            closeConnection();
        }
        return check;
    }

    public boolean updateDriver(DriverDTO dto) throws Exception {
        boolean check = false;
        try {
            String sql = "update Driver set Name = ?, LicenseNumber = ?, Age = ? , LicenseType = ? where DriverID = ? and"
                    + " DriverID not in (select DriverID from Booking where isBooked = 1)";
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(5, dto.getDriverID());
            preStm.setString(1, dto.getName());
            preStm.setString(2, dto.getLicenseNumber());
            preStm.setInt(3, dto.getAge());
            preStm.setString(4, dto.getLicenseType());
            check = preStm.executeUpdate() > 0;
        } finally {
            closeConnection();
        }
        return check;
    }

    public boolean deleteDriver(String driverID) throws Exception {
        boolean check = false;
        try {
            String sql = "update set isWorking = 0 from Driver where DriverID = ? and"
                    + " DriverID not in (select DriverID from Booking where isBooked = 1)";
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            check = preStm.executeUpdate() > 0;

        } finally {
            closeConnection();
        }
        return check;
    }

    public DriverDTO viewDetail(String driverID) throws Exception {
        DriverDTO dto = null;
        try {
            String sql = "select Name, LicenseNumber, Age, LicenseType from Driver where DriverID = ?";
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, driverID);
            rs = preStm.executeQuery();
            if (rs.next()) {
                String name = rs.getString("Name");
                String licenseNumber = rs.getString("LicenseNumber");
                int age = rs.getInt("Age");
                String licenseType = rs.getString("LicenseType");
                dto = new DriverDTO(driverID, name, licenseNumber, licenseType, age, true);
            }
        } finally {
            closeConnection();
        }
        return dto;
    }

    public List<DriverDTO> listByType(String licenseType) throws Exception {
        List<DriverDTO> result = null;
        DriverDTO dto = null;
        try {
            String sql = "select DriverID, Name, Age from Driver where LicenseType = ?"
                    + " and DriverID not in (select DriverID from Booking where isBooked = 1 and DriverID != NULL)";
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, licenseType);
            rs = preStm.executeQuery();
            result = new ArrayList<>();
            while (rs.next()) {
                String driverID = rs.getString("DriverID");
                String name = rs.getString("Name");
                int age = rs.getInt("Age");
                dto = new DriverDTO(driverID, name, licenseType, age);
                result.add(dto);
            }
        } finally {
            closeConnection();
        }
        return result;
    }

    public String getTypeByCar(String carID) throws Exception {
        String type = "B";
        try {
            String sql = "select Seat from Car where carID = ?";
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, carID);
            rs = preStm.executeQuery();
            if (rs.next()) {
                switch (rs.getInt("Seat")) {
                    case 2:
                        type = "B";
                        break;
                    case 4:
                        type = "B";
                        break;
                    case 7:
                        type = "B";
                        break;
                    case 16:
                        type = "C";
                        break;
                    case 45:
                        type = "E";
                        break;

                }
            }
        }finally{
            closeConnection();
        }
        return type;
    }

    public double getPriceByType(String driverID) throws Exception {
        double price = 0;
        try {
            driverID = driverID.trim();
            String sql = " select licenseType from Driver where DriverID = ?";
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, driverID);
            rs = preStm.executeQuery();
            if (rs.next()) {
                switch (rs.getString("licenseType")) {
                    case "B":
                        price = 10;
                        break;
                    case "C":
                        price = 20;
                        break;
                    case "D":
                        price = 30;
                        break;
                    case "E":
                        price = 40;
                        break;

                }
            }
        } finally {
            closeConnection();
        }
        return price;
    }

}
