package com.kim.biz.member.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.kim.biz.common.JDBCUtil;
import com.kim.biz.member.MemberVO;

public class MemberDAO {
	private Connection conn;
	private PreparedStatement pstmt;
	
	final String sql_selectOne="SELECT * FROM MEMBER WHERE MID=? AND MPW=?";
	final String sql_selectAll="SELECT * FROM MEMBER";
	final String sql_insert="INSERT INTO MEMBER VALUES(?,?,?,?)";
	final String sql_update="UPDATE MEMBER SET MPW=? , NAME=?, ROLE=? WHERE MID=?";
	final String sql_delete="DELETE MEMBER WHERE MID=?";
	
	public boolean insertMember(MemberVO vo) {
		conn=JDBCUtil.connect();
		try {
			pstmt=conn.prepareStatement(sql_insert);
			pstmt.setString(1, vo.getMid());
			pstmt.setString(2, vo.getMpw());
			pstmt.setString(3, vo.getName());
			pstmt.setString(4, vo.getRole());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			JDBCUtil.disconnect(pstmt, conn);
		}
		return true;
	}
	public void deleteMember(MemberVO vo) {
		conn=JDBCUtil.connect();
		try {
			pstmt=conn.prepareStatement(sql_delete);
			pstmt.setString(1, vo.getMid());
			System.out.println("회원탈퇴!!");
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.disconnect(pstmt, conn);
		}
	}
	public void updateMember(MemberVO vo) {
		conn=JDBCUtil.connect();
		try {
			pstmt=conn.prepareStatement(sql_update);
			System.out.println("UPDATE에 들어왔니??");
			pstmt.setString(1, vo.getMpw());
			pstmt.setString(2, vo.getName());
			pstmt.setString(3, vo.getRole());
			pstmt.setString(4, vo.getMid());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.disconnect(pstmt, conn);
		}
	}
	public MemberVO selectOneMember(MemberVO vo) {
		System.out.println("시작!");
		conn=JDBCUtil.connect();
		try {
			pstmt=conn.prepareStatement(sql_selectOne);
			pstmt.setString(1, vo.getMid());
			pstmt.setString(2, vo.getMpw());
			ResultSet rs=pstmt.executeQuery();
			if(rs.next()) {
				MemberVO data=new MemberVO();
				data.setMid(rs.getString("MID"));
				data.setMpw(rs.getString("MPW"));
				data.setName(rs.getString("NAME"));
				data.setRole(rs.getString("ROLE"));
				System.out.println("로그인 성공! - 1");
				return data;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.disconnect(pstmt, conn);
		}
		System.out.println("로그인 실패! - 2");
		return null;
	}
	List<MemberVO> selectAllMember(MemberVO vo){
		List<MemberVO> datas=new ArrayList<MemberVO>();
		conn=JDBCUtil.connect();
		try {
			pstmt=conn.prepareStatement(sql_selectAll);
			ResultSet rs=pstmt.executeQuery();
			while(rs.next()) {
				MemberVO data=new MemberVO();
				data.setMid(rs.getString("MID"));
				data.setMpw(rs.getString("MPW"));
				data.setName(rs.getString("NAME"));
				data.setRole(rs.getString("ROLE"));
				datas.add(data);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.disconnect(pstmt, conn);
		}
		return datas;
	}
}
