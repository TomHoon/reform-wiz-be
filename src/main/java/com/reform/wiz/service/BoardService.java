package com.reform.wiz.service;

import java.util.List;
import java.util.Map;
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

  // 글 전체조회
  public List<BoardDTO> getAll() {
    List<BoardDTO> list = boardRepository.findAll()
        .stream()
        .map(ele -> new BoardDTO(ele))
        .collect(Collectors.toList());

    return list;
  }

  // 글 페이지당 조회
  public PageResponseDTO<BoardDTO> getAllByPage(Pageable pageable, Map<String, Object> map) {
    String title = (String) map.get("title");
    String content = (String) map.get("content");
    int page = (int) map.get("page");
    int size = (int) map.get("size");

    Page<BoardEntity> result = boardRepository.findByTitleContainingOrContentContaining(title, content, pageable);
    Page<BoardDTO> dtoPage = result.map(BoardDTO::new);

    int totalPages = dtoPage.getTotalPages();

    int currentGroup = (page - 1) / 10;
    int startPage = currentGroup * 10 + 1;
    int endPage = Math.min(startPage + 9, totalPages);

    boolean hasPrevGroup = startPage > 1;
    boolean hasNextGroup = endPage < totalPages;

    PageResponseDTO<BoardDTO> pageRes = new PageResponseDTO<>(dtoPage);
    pageRes.setHasNextGroup(hasNextGroup);
    pageRes.setHasPrevGroup(hasPrevGroup);
    pageRes.setStartPage(startPage);
    pageRes.setEndPage(endPage);

    return pageRes;
  }

  // 글 등록
  public BoardDTO register(BoardDTO dto) {
    Long mno = dto.getMemberId();
    MemberEntity memberEntity = memberRepository.findById(mno).orElseThrow();

    BoardEntity be = dto.toEntity(dto, memberEntity);

    BoardEntity result = boardRepository.save(be);

    return new BoardDTO(result);
  }

  // 글 단건조회
  public BoardDTO getOne(Long bno) {
    Optional<BoardEntity> res = boardRepository.findById(bno);
    BoardEntity e = res.orElseThrow(() -> new EntityNotFoundException("요청하신 " + bno + "를 찾을 수 없습니다."));

    BoardDTO dto = new BoardDTO(e);

    return dto;
  }

  // 글 삭제
  public BoardDTO deleteBoard(Long bno) {
    BoardEntity e = boardRepository.findById(bno).orElseThrow();
    e.changeIsDel(true);

    boardRepository.save(e);

    return new BoardDTO(e);
  }

  // 글 수정
  public BoardDTO updateBoard(BoardDTO dto) {
    BoardEntity e = boardRepository.findById(dto.getBno()).orElseThrow();
    e.updateBoard(dto);

    boardRepository.save(e);

    return new BoardDTO(e);
  }

  // 글 조회(유저별)
  public PageResponseDTO<BoardDTO> getBoardByMemberId(String memberId, Pageable page) {
    Page<BoardEntity> result = boardRepository.findByMemberEntity_MemberId(memberId, page);
    Page<BoardDTO> list = result.map(BoardDTO::new);

    return new PageResponseDTO<BoardDTO>(list);
  }

}
