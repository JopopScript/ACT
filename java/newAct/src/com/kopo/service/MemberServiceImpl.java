package com.kopo.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.kopo.dao.MemberDAO;
import com.kopo.dto.MemberVO;

@Service("MemberService")
public class MemberServiceImpl implements MemberService {

	@Inject
	private MemberDAO dao;

	@Override
	public int insert(MemberVO vo) throws Exception {
		// TODO Auto-generated method stub
		return dao.insert(vo);
	}

	@Override
	public List<MemberVO> select() throws Exception {
		// TODO Auto-generated method stub
		return dao.select();
	}

	@Override
	public MemberVO selectOne() throws Exception {
		return dao.selectOne();
	}

	@Override
	public int deleteOne(int size) throws Exception {
		return dao.deleteOne(size);
	}

	@Override
	public float selectTemperature() throws Exception {
		return dao.selectTemperature();
	}

	@Override
	public int selectStatic() throws Exception {
		// TODO Auto-generated method stub
		return dao.selectStatic();
	}

	@Override
	public int getRecordCount() throws Exception {
		return dao.getRecordCount();
	}

}
