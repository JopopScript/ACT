package com.kopo.dao;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.kopo.dto.MemberVO;

@Repository
public class MemberDAOImpl implements MemberDAO {

	@Inject
	private SqlSession sqlSession;
	private static final String Namespace = "com.kopo.mapper.memberMapper";

	@Override
	public int insert(MemberVO vo) throws Exception {
		return sqlSession.insert(Namespace + ".insert", vo);
	}

	@Override
	public List<MemberVO> select() throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectList(Namespace + ".select");
	}

	@Override
	public float selectTemperature() throws Exception {
		return sqlSession.selectOne(Namespace + ".selectTemperature");

	}

	@Override
	public MemberVO selectOne() throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(Namespace + ".selectOne");
	}

	@Override
	public int deleteOne(int size) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.delete("deleteOne", size);
	}

	@Override
	public int selectStatic() throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(Namespace + ".selectStatic");
	}

	@Override
	public int getRecordCount() throws Exception {
		return sqlSession.selectOne(Namespace + ".getRecordCount");
	}
}
