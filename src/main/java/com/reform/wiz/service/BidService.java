package com.reform.wiz.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.reform.wiz.dto.BidDTO;
import com.reform.wiz.entity.BidEntity;
import com.reform.wiz.entity.BoardEntity;
import com.reform.wiz.entity.MemberEntity;
import com.reform.wiz.repository.BidRepository;
import com.reform.wiz.repository.BoardRepository;
import com.reform.wiz.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class BidService {

  private final BidRepository bidRepository;
  private final MemberRepository memberRepository;
  private final BoardRepository boardRepository;

  // 입찰 리스트 조회
  public List<BidDTO> getBidList(Long bno) {
    List<BidEntity> res = bidRepository.findByBoard_Bno(bno);
    List<BidDTO> list = res.stream().map(e -> new BidDTO(e)).collect(Collectors.toList());

    return list;
  }

  // 입찰수정
  public BidDTO modify(Long bidno, BidDTO dto) {
    BidEntity bidEntity = bidRepository.findById(bidno).orElseThrow();
    bidEntity.update(dto);

    bidRepository.save(bidEntity);
    return new BidDTO(bidEntity);
  }

  // 입찰취소
  public Boolean cancelBid(Long bidno) {

    try {
      bidRepository.deleteById(bidno);
      return true;
    } catch (Exception e) {
      return false;
    }

  }

  public Boolean requestBid(Long boardno, Long mno, BidDTO dto) {
    MemberEntity mEntity = memberRepository.findById(mno).orElseThrow();
    BoardEntity bEntity = boardRepository.findById(boardno).orElseThrow();

    BidEntity bidEntity = BidEntity.builder()
        .bidPrice(dto.getBidPrice())
        .memberEntity(mEntity)
        .boardEntity(bEntity)
        .build();

    try {
      BidEntity e = bidRepository.save(bidEntity);
      return true;
    } catch (Exception e) {
      return false;
    }

  }
}
