//package kr.co.anbu.controller;
//
//import kr.co.anbu.domain.service.BoardService;
//import kr.co.anbu.infra.BoardRequest;
//import kr.co.anbu.infra.Response;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequiredArgsConstructor
//@Slf4j
//@RequestMapping("/api/board")
//public class BoardRestController {
//
//    private final BoardService notiService;
//
//    private final Response response;
//
//    /**
//     * 게시판 조회
//     * @param notiMKey
//     * @return
//     * @throws Exception
//     */
//    @GetMapping("/{notiMKey}")
//    public ResponseEntity<?> board(@PathVariable Long notiMKey) throws Exception{
//
//        return response.success(notiService.getBoard(notiMKey),
//                "success", HttpStatus.OK);
//
//    }
//
//    /**
//     * 공지사항 리스트 조회
//     * @param start
//     * @param end
//     * @return
//     * @throws Exception
//     */
//    @GetMapping("")
//    public ResponseEntity<?> boards(@RequestParam Integer start,
//                                    @RequestParam Integer end,
//                                    @RequestParam String sort) throws Exception{
//        return response.success(notiService.getBoards(start,end,sort),
//                "success", HttpStatus.OK);
//    }
//
//}
