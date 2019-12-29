/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huytm_models;

import huytm_db.MyConnection;
import huytm_dtos.DiscountDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Kay
 */
public class DiscountDAO {

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

    public boolean addDiscount(DiscountDTO dto) throws Exception {
        boolean check = false;
        try {
            String sql = "insert into Discount(DiscountCode,Name,ExperyDate,DiscountValue,RemainNumber)"
                    + " values(?,?,?,?,?)";
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, dto.getDiscountCode());
            preStm.setString(2, dto.getName());
            preStm.setDate(3, java.sql.Date.valueOf(dto.getExperyDate()));
            preStm.setDouble(4, dto.getDiscountValue());
            preStm.setInt(5, dto.getRemainNumber());
            check = preStm.executeUpdate() > 0;
        } finally {
            closeConnection();
        }
        return check;
    }

    public DiscountDTO viewDetail(String discountCode) throws Exception {
        DiscountDTO dto = null;
        try {
            String sql = "select Name,ExperyDate,DiscountValue,RemainNumber from Discount where DiscountCode = ?";
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, discountCode);
            rs = preStm.executeQuery();
            if (rs.next()) {
                String name = rs.getString("Name");
                LocalDate expDate = rs.getDate("ExperyDate").toLocalDate();
                double discountValue = rs.getDouble("DiscountValue");
                int remainNumber = rs.getInt("RemainNumber");
                dto = new DiscountDTO(discountCode, name, expDate, discountValue, remainNumber);
            }
        } finally {
            closeConnection();
        }
        return dto;
    }
    
    public List<DiscountDTO> listAll() throws Exception{
        List<DiscountDTO> result = null;
        DiscountDTO dto = null;
        try{
            String sql = "select DiscountCode, Name, DiscountValue from Discount";{
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            rs = preStm.executeQuery();
            result = new ArrayList<>();
            while(rs.next()){
                String discountCode = rs.getString("DiscountCode");
                String name = rs.getString("Name");
                double discountValue = rs.getDouble("DiscountValue");
                dto = new DiscountDTO(discountCode, name, discountValue);
                result.add(dto);
            }
        }
        } finally{
            closeConnection();
        }
        return result;
    }
}
