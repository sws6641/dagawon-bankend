package com.bankle.common.asis.domain.service;

import com.bankle.common.asis.domain.entity.FlMaster;
import com.bankle.common.asis.domain.entity.NotiMaster;
import com.bankle.common.asis.domain.repositories.NotiRepository;
import com.bankle.common.asis.infra.BoardRequest;
import com.bankle.common.asis.infra.BoardResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class BoardService {

    private final FlService flService;
    private final NotiRepository notiRepository;

    /**
     * 공지사항 저장
     * @param board
     * @return
     */
    public NotiMaster newBoard(BoardRequest board){

        //File 없로드
        FlMaster flMaster = flService.uploadFile(board.getFiles(), null);
        //공지사항 저장
        NotiMaster notiMaster = board.getNotiMaster();
        notiMaster.setAtthFlMKey(flMaster.getFlMKey());

        return notiRepository.save(notiMaster);
    }

    /**
     * 게시판 조회
     * @param notiMKey
     * @return
     */
    @Transactional
    public BoardResponseDto getBoard(Long notiMKey){

        NotiMaster noti = notiRepository.findByNotiMKey(notiMKey).orElse(null);
        if(noti == null)
            return null;

        BoardResponseDto dto = BoardResponseDto.of(noti);
        dto.setFiles(flService.getFiles(noti.getAtthFlMKey()));

        return dto;
    }

    /**
     * 게시판 리스트
     * @param start
     * @param end
     * @param sort
     * @return
     */
    public Page<NotiMaster> getBoards(Integer start, Integer end, String sort){
        start = (start == null) ? 0 : start;
        end = (end == null) ? 5 : end;

        Pageable pageable;
        if(!StringUtils.hasText(sort)){
            Sort sorting = Sort.by("regDtm").descending();
            pageable = PageRequest.of(start, end, sorting);
        }else{
            Sort sorting = Sort.by(sort).descending();
            pageable = PageRequest.of(start, end, sorting);
        }

        Page<NotiMaster> boards = notiRepository.findAll(pageable);
        boards.stream().forEach(b -> b.setFiles(flService.getFiles(b.getAtthFlMKey())));

        return boards;

    }

    /**
     * 게시판 수정
     * @param board
     * @return
     */
    public NotiMaster save(BoardRequest board) {
        NotiMaster noti = board.getNotiMaster();
        if(!StringUtils.hasText(String.valueOf(noti.getNotiMKey())))
            throw new RuntimeException("잘못된 게시판번호 입니다. ");

        NotiMaster vo = notiRepository.findByNotiMKey(noti.getNotiMKey()).orElse(null);
        if(vo == null)
            throw new RuntimeException("잘못된 게시판번호 입니다. ");

        vo.setNotiTtl(noti.getNotiTtl());
        vo.setNotiCnts(noti.getNotiCnts());

        NotiMaster save = notiRepository.save(vo);

        if(vo.getAtthFlMKey() == null){
            log.info("기존에 등록된 파일이 없습니다.");
        }else {
            FlMaster master = flService.getFiles(vo.getAtthFlMKey());
            save.setFiles(
                    flService.uploadFile(board.getFiles(), master.getFlMKey()));
        }


        return save;
    }
}
