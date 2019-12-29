/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huytm_models;

import huytm_db.MyConnection;
import huytm_dtos.StaffDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Kay
 */
public class StaffDAO {

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

    public boolean updateStaff(StaffDTO dto) throws Exception {
        boolean check = false;
        try {
            String sql = "Update Staff set  Name= ?, IsManager= ?, Address= ?, Age= ?, isWorking= ? where staffID =?";
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, dto.getName());
            preStm.setBoolean(2, dto.isIsManager());
            preStm.setString(3, dto.getAddress());
            preStm.setInt(4, dto.getAge());
            preStm.setBoolean(5, true);
            preStm.setString(6, dto.getStaffID());
            check = preStm.executeUpdate() > 0;
        } finally {
            closeConnection();
        }
        return check;

    }

    public boolean deleteStaff(String staffID) throws Exception {
        boolean check = false;
        try {
            String sql = "update Staff set isWorking = 0 where StaffID = ?";
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, staffID);
            check = preStm.executeUpdate() > 0;
        } finally {
            closeConnection();
        }
        return check;
    }

    public int changePassword(String staffID, String oldPassword, String newPassword) throws Exception {
        int check = 0;
        try {
            String sql = "select password from staff where staffID = ?";
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, staffID);
            rs = preStm.executeQuery();
            if (rs.next()) {
                if (oldPassword.equals(rs.getString("password"))) {
                    sql = "update staff set password = ? where staffID = ?";
                    conn = MyConnection.getMyConnection();
                    preStm = conn.prepareStatement(sql);
                    preStm.setString(1, newPassword);
                    preStm.setString(2, staffID);
                    if (preStm.executeUpdate() > 0) {
                        check = 2;
                    }
                }
                else{
                    check = 1;
                }
            }
        } finally {
            closeConnection();
        }
        return check;
    }

    public boolean addStaff(StaffDTO dto) throws Exception {
        boolean check = false;
        try {
            String sql = "insert into Staff(StaffID, Password, Name, IsManager, Address, Age, isWorking) values(?,?,?,?,?,?,1)";
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, dto.getStaffID());
            preStm.setString(2, dto.getPassword());
            preStm.setString(3, dto.getName());
            preStm.setBoolean(4, dto.isIsManager());
            preStm.setString(5, dto.getAddress());
            preStm.setInt(6, dto.getAge());
            check = preStm.executeUpdate() > 0;
        } finally {
            closeConnection();
        }
        return check;
    }//check duplicate;

    public StaffDTO viewDetailByID(String staffID) throws Exception {
        StaffDTO dto = null;
        try {
            String sql = " Select Name, IsManager, Address, Age from Staff where StaffID = ?";
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, staffID);
            rs = preStm.executeQuery();
            if (rs.next()) {
                String name = rs.getString("name");
                boolean isManager = rs.getBoolean("isManager");
                String address = rs.getString("Address");
                int age = rs.getInt("Age");
                dto = new StaffDTO(staffID, name, address, isManager, age);
            }
        } finally {
            closeConnection();
        }
        return dto;
    }

    public List<StaffDTO> listAll() throws Exception {
        List<StaffDTO> result = null;
        StaffDTO dto = null;
        try {
            String sql = "Select StaffID, Name, IsManager, Age from Staff where isWorking = 1";
            result = new ArrayList<>();
            while (rs.next()) {
                String staffID = rs.getString("StaffID");
                String name = rs.getString("name");
                boolean isManager = rs.getBoolean("IsManager");
                int age = rs.getInt("Age");
                dto = new StaffDTO(staffID, name, "", isManager, age);
                result.add(dto);
            }
        } finally {
            closeConnection();
        }
        return result;
    }

    public String login(String id, String password) throws Exception {
        String type = "Error";
        try {
            String sql = "Select StaffID, IsWorking from Staff where StaffId = ? and Password = ?";
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, id);
            preStm.setString(2, password);
            rs = preStm.executeQuery();
            if (rs.next()) {
                if (!rs.getBoolean("IsWorking")) {
                    type = "Stop Working";
                } else {
                    type = rs.getString("StaffID");
                }
            }
        } finally {
            closeConnection();
        }
        return type;
    }
}
