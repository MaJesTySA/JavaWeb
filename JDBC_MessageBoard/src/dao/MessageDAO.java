package dao;

import bean.Message;
import common.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO {
    /*
    page:当前页码
    pageSize:每页记录
     */
    public List<Message> getMessages(int page, int pageSize) {
        List<Message> messages = new ArrayList<>();
        Connection conn = ConnectionUtil.getConnection();
        String sql = "SELECT * FROM message ORDER BY create_time desc limit ?,?";//从第m条开始，取出总共n条记录
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, (page - 1) * pageSize);
            stmt.setInt(2, pageSize);
            rs = stmt.executeQuery();
            while (rs.next()) {
                messages.add(new Message(rs.getLong("id"),
                        rs.getLong("user_id"),
                        rs.getString("username"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getTimestamp("create_time")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionUtil.release(rs, stmt, conn);
        }
        return messages;
    }

    public List<Message> getMyMessages(int page, int pageSize,String username) {
        List<Message> messages = new ArrayList<>();
        Connection conn = ConnectionUtil.getConnection();
        String sql = "SELECT * FROM message WHERE username= ? ORDER BY create_time desc limit ?,?";//从第m条开始，取出总共n条记录
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1,username);
            stmt.setInt(2, (page - 1) * pageSize);
            stmt.setInt(3, pageSize);
            rs = stmt.executeQuery();
            while (rs.next()) {
                messages.add(new Message(rs.getLong("id"),
                        rs.getLong("user_id"),
                        rs.getString("username"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getTimestamp("create_time")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionUtil.release(rs, stmt, conn);
        }
        return messages;
    }

    public int countMessages() {
        Connection conn = ConnectionUtil.getConnection();
        String sql = "SELECT COUNT(*) total FROM message";
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionUtil.release(rs, stmt, conn);
        }
        return 0;
    }

    public int countMyMessages(String username) {
        Connection conn = ConnectionUtil.getConnection();
        String sql = "SELECT COUNT(*) total FROM message WHERE username = ?";
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1,username);
            rs = stmt.executeQuery();
            while (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionUtil.release(rs, stmt, conn);
        }
        return 0;
    }

    public boolean save(Message message) {
        Connection conn = ConnectionUtil.getConnection();
        String sql = "INSERT INTO message(user_id,username,title,content,create_time) VALUES (?,?,?,?,?)";
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, message.getUserId());
            stmt.setString(2, message.getUsername());
            stmt.setString(3, message.getTitle());
            stmt.setString(4, message.getContent());
            stmt.setTimestamp(5, new Timestamp((message.getCreateTime().getTime())));
            stmt.execute();
        } catch (SQLException e) {
            System.out.println("保存留言失败");
            e.printStackTrace();
            return false;
        } finally {
            ConnectionUtil.release(null, stmt, conn);
        }
        return true;
    }
}
