package com.kopo.dao;

import java.util.List;

import com.kopo.dto.MemberVO;

public interface MemberDAO {

	public int insert(MemberVO vo) throws Exception;

	public List<MemberVO> select() throws Exception;

	public MemberVO selectOne() throws Exception;

	public int deleteOne(int size) throws Exception;

	public int selectStatic() throws Exception;

	public float selectTemperature() throws Exception;

	public int getRecordCount() throws Exception;
}
