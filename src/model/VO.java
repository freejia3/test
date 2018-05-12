package model;

import java.util.ArrayList;
import java.util.Date;

public class VO {
	int id, gid, lev, seq, cnt;
	Date reg_date;
	String title, pname, pw, content, upfile;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getGid() {
		return gid;
	}
	public void setGid(int gid) {
		this.gid = gid;
	}
	public int getLev() {
		return lev;
	}
	public void setLev(int lev) {
		this.lev = lev;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public int getCnt() {
		return cnt;
	}
	public void setCnt(int cnt) {
		this.cnt = cnt;
	}
	public Date getReg_date() {
		return reg_date;
	}
	public void setReg_date(Date reg_date) {
		this.reg_date = reg_date;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getUpfile() {
		if(upfile==null)
			upfile="";
		return upfile;
	}
	public void setUpfile(String upfile) {
		this.upfile = upfile;
	}
	
	public boolean isImg() {
		if(upfile==null || upfile.equals(""))
			return false;
		
		ArrayList<String> imgs = new ArrayList<>();
		imgs.add("bmp");
		imgs.add("png");
		imgs.add("jpg");
		imgs.add("jpeg");
		imgs.add("gif");
		
		String exp = upfile.substring(upfile.lastIndexOf(".")+1).toLowerCase();
		
		return imgs.contains(exp);
	}
	
	
}
