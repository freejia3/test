package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DAO {
	Connection con;
	PreparedStatement ptmt;
	ResultSet rs;
	String sql;
	
	public DAO() {
		try {
			Context init = new InitialContext();
			DataSource ds = (DataSource)init.lookup("java:comp/env/oooo");
			con = ds.getConnection();
			System.out.println("DB 접속 성공~");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public int totalCount() {
		try {
			sql = "select count(*) from mvcboard";
			ptmt = con.prepareStatement(sql);
			rs = ptmt.executeQuery();
			rs.next();
			return rs.getInt(1);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//close()안해요
		return 0;
	}
	
	public ArrayList<VO> list(int start, int end){
		ArrayList<VO> res = new ArrayList<>();
		try {
			
			sql = "select * from "
					+"(select rownum rnum, tt.* from "
					+"(select * from mvcb order by gid desc, seq) tt) "
					+"where rnum>= ? and rnum<= ?";
			ptmt = con.prepareStatement(sql);
			ptmt.setInt(1, start);
			ptmt.setInt(2, end);
			rs = ptmt.executeQuery();
			
			while(rs.next()) {
				
				VO vo = new VO();
				vo.setId(rs.getInt("id"));
				vo.setLev(rs.getInt("lev"));
				vo.setSeq(rs.getInt("seq"));
				vo.setTitle(rs.getString("title"));
				vo.setPname(rs.getString("pname"));
				vo.setReg_date(rs.getDate("reg_date"));
				res.add(vo);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			close();
		}
		System.out.println("list() 끝");
		return res;
	}
	
	public int insert(VO vo) {
		try {
			//gid, seq, lev : 답변 달때 꼭 필요한 컬럼.
			//cnt:조회수
			sql = "insert into mvcb "
					+ "(id, gid, seq, lev, cnt, reg_date, pname, pw, title, content, upfile)"
					+ " values (mvcb_Seq.nextval, mvcb_Seq.nextval, 0, 0, -1, sysdate, ?, ?, ?, ?, ?)";
			ptmt = con.prepareStatement(sql);
			ptmt.setString(1, vo.getPname());
			ptmt.setString(2, vo.getPw());
			ptmt.setString(3, vo.getTitle());
			ptmt.setString(4, vo.getContent());
			ptmt.setString(5, vo.getUpfile());

			ptmt.executeUpdate();

			sql="select max(id) from mvcb";
			ptmt = con.prepareStatement(sql);
			rs = ptmt.executeQuery();

			rs.next();

			return rs.getInt(1);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			close();
		}
		return 0;
	}
	
	//조회수 증가
	public void addCount(int id) {
		
		try {
			sql = "update mvcb set cnt = cnt+1 where id = ?";
			ptmt = con.prepareStatement(sql);

			ptmt.setInt(1, id);
			ptmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	//상세보기
	public VO detail(int id) {
		try {
			sql = "select * from mvcb where id = ?";
			ptmt = con.prepareStatement(sql);
			ptmt.setInt(1, id);
			rs = ptmt.executeQuery();

			if(rs.next()) {
				VO vo = new VO();
				vo.setId(id);
				vo.setGid(rs.getInt("gid"));
				vo.setLev(rs.getInt("lev"));
				vo.setSeq(rs.getInt("seq"));
				vo.setCnt(rs.getInt("cnt"));
				vo.setReg_date(rs.getTimestamp("reg_date"));
				vo.setTitle(rs.getString("title"));
				vo.setPname(rs.getString("pname"));
				vo.setContent(rs.getString("content"));
				vo.setUpfile(rs.getString("upfile"));
				return vo;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//close()하면안됨

		return null;
	}
	
	public VO sch(VO vo) {
		
		try {
		sql = "select * from mvcb where id=? and pw=?";
		ptmt = con.prepareStatement(sql);
		ptmt.setInt(1, vo.getId());
		ptmt.setString(2, vo.getPw());

		rs = ptmt.executeQuery();

		//칼럼이 있으면 파일명 읽어와서 res에 저장하고 res리턴
		if(rs.next()) {
			VO res = new VO();
			res.setUpfile(rs.getString("upfile"));

			return res;
		}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//close() 안해요 ModifyReg, DeleteReg 참고
		return null;
	}
	
	//modify 
	public void modify(VO vo) {
		try {
		sql = "update mvcb set pname=?, title=?, content=?, upfile=? where id=?";
		ptmt = con.prepareStatement(sql);
		ptmt.setString(1, vo.getTitle());
		ptmt.setString(2, vo.getPname());
		ptmt.setString(3, vo.getContent());
		ptmt.setString(4, vo.getUpfile());
		ptmt.setInt(5, vo.getId());
		ptmt.executeUpdate();
		System.out.println("DB modify 성공");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void close() {
		if(rs!=null)try {rs.close();} catch (SQLException e) {}
		if(ptmt!=null) try {ptmt.close();} catch (SQLException e) {}
		if(con!=null) try {con.close();} catch (SQLException e) {}
	}
	
}
