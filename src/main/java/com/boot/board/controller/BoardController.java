package com.boot.board.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


import com.boot.board.domain.Board;
import com.boot.board.domain.Pagination;
import com.boot.board.domain.Search;
import com.boot.board.domain.User;
import com.boot.board.security.SecurityUtils;
import com.boot.board.service.BoardService;
import com.boot.board.service.UserService;
import com.boot.board.service.WeatherService;
import com.boot.board.util.PaginationUtil;
import com.boot.board.util.TempUtils;


@Controller
public class BoardController {
	
    @Autowired TempUtils util;
	@Autowired BoardService boardService;
	@Autowired UserService userservice;
	@Autowired PasswordEncoder encoder;
	@Autowired WeatherService weatherService;
	

    private final SecurityUtils securityUtils;  // 순환참조 오류때문에 생성자 주입 사용 

    @Autowired
    public BoardController(SecurityUtils securityUtils) {
        this.securityUtils = securityUtils;
    }

    
	
	@RequestMapping(value = "/", method = RequestMethod.GET)  // 시작페이지 
	public String index() {
		System.out.println("인덱스 페이지 호출");
		return "index";
	}
	
	
	
	@PostMapping("/signUpPro")   // 회원가입 
	  public String signup(Model model, User user) {
	     	
			System.out.println("이게맞나?"+userservice.userExist(user.getUsername()));
		  if (userservice.userExist(user.getUsername())) {
		 		model.addAttribute("errorExId", "이미 존재하는 id 입니다");
	            return "index";
	       }
	 	
		  String encodedPassword = encoder.encode(user.getPassword());  //비밀번호 암호화
	      
	      user.setPassword(encodedPassword);
	      user.setAccountNonExpired(true);
	      user.setEnabled(true);
	      user.setAccountNonLocked(true);
	      user.setCredentialsNonExpired(true);
	      user.setAuthorities(AuthorityUtils.createAuthorityList("ROLE_USER")); // 유저 권한세팅 ROLE_USER auth에 저장    
	      
	      userservice.createUser(user);
	      userservice.createAuthorities(user);
	      System.out.println(user.getAuthorities());
	      
	      model.addAttribute("signupSuccess", true); // 가입 성공 여부를 모델에 추가
	      return "/index";
	}
	

    @PostMapping("/signIn")   //로그인 
    public String signIn(HttpServletRequest request, Model model) {
        String errorId = (String) request.getAttribute("errorId");
        String errorPassword = (String) request.getAttribute("errorPassword");

        model.addAttribute("errorId", errorId);
        model.addAttribute("errorPassword", errorPassword);

        return "index";  // 로그인 실패 시 "index.jsp" 파일을 반환
    }
	
	
	    @GetMapping("/logout")  // 로그아웃 
	    public String logout(Model model) {
	        return "index";  // index.jsp로 이동
	    }	  
	
	@GetMapping(value = "/board/search")   // 게시글 목록 
	public String boardSearch(Model model, 
			@RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
             Search search) throws IOException {
	  
		 // 현재 인증된 사용자 정보 가져오기
		securityUtils.addAuthenticatedUserDetails(model);	 //로그인 유저 정보 전달 
		
		
        String lat = "35.8722";
        String lon = "128.6025"; // 대구 좌표값 

	    Map<String, Object> weatherData = weatherService.getCurrentWeather(lat, lon);
	    model.addAttribute("weatherData", weatherData); // 날씨 정보 
		
		List<Board> list = boardService.searchBoard(page, size, search);
		int totalBoards = boardService.countSearchBoard(search);
        
        Pagination pagination =PaginationUtil.getPagination(page, size, totalBoards);
        
		
        model.addAttribute("boards", list);
        model.addAttribute("size", pagination.getSize());
        model.addAttribute("nowPage", pagination.getNowPage()); // 현재 페이지 번호
        model.addAttribute("startPage", pagination.getStartPage()); // 시작 페이지 번호
        model.addAttribute("endPage", pagination.getEndPage()); // 끝 페이지 번호
        model.addAttribute("totalPages", pagination.getTotalPages());  // 전체 페이지 수
        model.addAttribute("pageNumbers", pagination.getPageNumbers());  // 페이지 번호 목록
		return "board-list";
	}
	
	@GetMapping(value = "/board/write")   // 글작성  form으로 
	public String boardWrite(Model model) {
		securityUtils.addAuthenticatedUserDetails(model);
		return "board-write";
	}

	@PostMapping(value = "/board/writepro") // 글작성 
	public String boardWritePro(Board board, Model model) {
		securityUtils.addAuthenticatedUserDetails(model);	 //로그인 유저 정보 전달 
		board.setbWriter((String)model.getAttribute("loggedInUser"));
		boardService.writeBoard(board);
		return "redirect:/board/search";
	}

	@GetMapping(value = "/board/info")   // 게시글 정보 
	public String boardInfo(Model model, Board board) {
		securityUtils.addAuthenticatedUserDetails(model);	 //로그인 유저 정보 전달 
		model.addAttribute("board", boardService.infoBoard(board));
	    System.out.println(boardService.isAuthor(board));
		return "board-info";
	}

	@RequestMapping(value = "/board/delete")  //게시글 삭제
    @PreAuthorize("hasRole('ROLE_ADMIN') or @boardService.isAuthor(#board)")
	public String boardDelete(Model model, Board board) {
		boardService.deleteBoard(board);
		return "redirect:/board/search";
	}

	@GetMapping(value = "/board/edit")  //게시글 수정 
    @PreAuthorize("hasRole('ROLE_ADMIN') or @boardService.isAuthor(#board)")
	public String boardEdit(Model model, Board board) {
		securityUtils.addAuthenticatedUserDetails(model);
		System.out.println(boardService.isAuthor(board));
		model.addAttribute("board", boardService.infoBoard(board));
		return "board-edit";
	}

	@RequestMapping(value = "/board/editpro") //게시글 수정 
    @PreAuthorize("hasRole('ROLE_ADMIN') or @boardService.isAuthor(#board)")
	public String boardEditPro(Model model, Board board) {
		securityUtils.addAuthenticatedUserDetails(model);
		boardService.editBoard(board);

		return "redirect:/board/info?bId=" + board.getbId();
	}

	@GetMapping(value = "/board/reply")  // 답글 
	public String boardReply(Model model, Board board) {
		securityUtils.addAuthenticatedUserDetails(model);
		model.addAttribute("board", boardService.infoBoard(board));
		return "board-reply";
	}

	@PostMapping(value = "/board/replypro") // 답글
	public String replyBoardPro(Model model, Board board, HttpSession session) {
		securityUtils.addAuthenticatedUserDetails(model);
		model.addAttribute("board", boardService.infoBoard(board));
		board.setbWriter((String) model.getAttribute("loggedInUser"));
		boardService.replyBoard(board);
		return "redirect:/board/search"; // 이전화면으로 리다이렉트
	}
	
	
	@GetMapping(value = "/user/Info") // 회원정보
	public String userInfo(Model model, User user, HttpSession session) {
		model.addAttribute("user", userservice.infoUser(user));
		model.addAttribute("boardcount", boardService.countBoardbyuser(user.getUsername()));
		return "user-info"; // 이전화면으로 리다이렉트
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping(value = "/user/list")   // 회원목록 
	public String userList(Model model, 
			@RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            HttpSession session) {
		String loggedInUser = (String) session.getAttribute("loggedInUser");
		
		
		List<User> list = userservice.userList(page, size);
        int totalBoards = userservice.countUser();
        
        Pagination pagination =PaginationUtil.getPagination(page, size, totalBoards);
        
        model.addAttribute("users", list);
        model.addAttribute("size", pagination.getSize());
        model.addAttribute("nowPage", pagination.getNowPage()); // 현재 페이지 번호
        model.addAttribute("startPage", pagination.getStartPage()); // 시작 페이지 번호
        model.addAttribute("endPage", pagination.getEndPage()); // 끝 페이지 번호
        model.addAttribute("totalPages", pagination.getTotalPages());  // 전체 페이지 수
        model.addAttribute("pageNumbers", pagination.getPageNumbers());  // 페이지 번호 목록
        model.addAttribute("loggedInUser", loggedInUser);
		return "user-list";
	}
	
	@RequestMapping(value = "/user/edit")  // 회원권한 수정 
	@ResponseBody
	public String userEdit(@RequestBody  User user) {
		userservice.editAuthority(user);
		  return "success";
	}
	
	
	@ExceptionHandler(AccessDeniedException.class)  // 접근 거부 처리 
	@ResponseStatus(HttpStatus.FORBIDDEN)
    public String handleAccessDeniedException(AccessDeniedException ex) {
        return "403Forbidden"; 
    }
 
	
    @GetMapping("/chat")
    public String chat() {
        return "chat";
    }

	
    	  
}
	
	
	
	
	
