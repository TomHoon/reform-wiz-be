package com.reform.wiz.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.reform.wiz.dto.BoardDTO;
import com.reform.wiz.dto.PageResponseDTO;
import com.reform.wiz.entity.BoardEntity;
import com.reform.wiz.entity.MemberEntity;
import com.reform.wiz.repository.BoardRepository;
import com.reform.wiz.repository.MemberRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class BoardService {

  private final BoardRepository boardRepository;
  private final MemberRepository memberRepository;

  public List<BoardDTO> getAll() {
    List<BoardDTO> list = boardRepository.findAll()
        .stream()
        .map(ele -> new BoardDTO(ele))
        .collect(Collectors.toList());

    return list;
  }

  public BoardDTO register(BoardDTO dto) {
    Long mno = dto.getMemberId();
    MemberEntity memberEntity = memberRepository.findById(mno).orElseThrow();

    BoardEntity be = dto.toEntity(dto, memberEntity);

    BoardEntity result = boardRepository.save(be);

    return new BoardDTO(result);
  }

  public BoardDTO getOne(Long bno) {
    Optional<BoardEntity> res = boardRepository.findById(bno);
    BoardEntity e = res.orElseThrow(() -> new EntityNotFoundException("요청하신 " + bno + "를 찾을 수 없습니다."));

    BoardDTO dto = new BoardDTO(e);

    return dto;
  }

  public PageResponseDTO<BoardDTO> getAllByPage(Pageable pageable) {
    // 페이지당 조회
    Page<BoardEntity> result = boardRepository.findAll(pageable);

    Page<BoardDTO> dtoPage = result.map(BoardDTO::new);

    return new PageResponseDTO<>(dtoPage);
  }

  public BoardDTO deleteBoard(Long bno) {
    BoardEntity e = boardRepository.findById(bno).orElseThrow();
    e.changeIsDel(true);

    boardRepository.save(e);

    return new BoardDTO(e);
  }

  public BoardDTO updateBoard(BoardDTO dto) {
    BoardEntity e = boardRepository.findById(dto.getBno()).orElseThrow();
    e.updateBoard(dto);

    boardRepository.save(e);

    return new BoardDTO(e);
  }
}
