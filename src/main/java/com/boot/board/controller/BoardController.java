package com.boot.board.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
	@Autowired BoardService boardservice;
	@Autowired UserService userservice;
	@Autowired PasswordEncoder encoder;
	@Autowired WeatherService weatherService;
	

    private final SecurityUtils securityUtils;

    @Autowired
    public BoardController(SecurityUtils securityUtils) {
        this.securityUtils = securityUtils;
    }

    
    
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index() {
		System.out.println("인덱스 페이지 호출");
		return "index";
	}
	
	
//    @PostMapping("/checkUsername")
//    public ResponseEntity<String> checkUsername(@RequestBody String username) {
//        boolean exists = userservice.userExist(username);
//        if (exists) {
//            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists");
//        }
//        return ResponseEntity.ok("Username available");
//    }
//	
	
	@PostMapping("/signUpPro") 
	  public String signup(Model model, User user) {
	     	
	
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
	

    @PostMapping("/signIn")
    public String signIn(HttpServletRequest request, Model model) {
        String errorId = (String) request.getAttribute("errorId");
        String errorPassword = (String) request.getAttribute("errorPassword");

        model.addAttribute("errorId", errorId);
        model.addAttribute("errorPassword", errorPassword);

        return "index";  // 로그인 실패 시 "index.jsp" 파일을 반환
    }
	
	
	    @GetMapping("/logout")
	    public String logout(Model model) {
	        return "index";  // index.jsp로 이동
	    }	  
	
	@GetMapping(value = "/board/search")
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
		
		List<Board> list = boardservice.searchBoard(page, size, search);
		int totalBoards = boardservice.countSearchBoard(search);
        
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
	
	@GetMapping(value = "/board/write")
	public String boardWrite() {
		return "board-write";
	}

	@PostMapping(value = "/board/writepro")
	public String boardWritePro(Board board,HttpSession session) {
		board.setbWriter((String) session.getAttribute("loggedInUser"));
		boardservice.writeBoard(board);
		return "redirect:/board/search";
	}

	@GetMapping(value = "/board/info")
	public String boardInfo(Model model, Board board,HttpSession session) {
		String loggedInUser = (String) session.getAttribute("loggedInUser");
		String userAuth = (String) session.getAttribute("userAuth");
		model.addAttribute("board", boardservice.infoBoard(board));
		model.addAttribute("loggedInUser", loggedInUser); // 접속중인 유저 id 
	    model.addAttribute("userAuth", userAuth); 
	    
	    System.out.println(boardservice.isAuthor(board));
		return "board-info";
	}

	@RequestMapping(value = "/board/delete")
    @PreAuthorize("hasRole('ROLE_ADMIN') or @boardService.isAuthor(#board)")
	public String boardDelete(Model model, Board board) {
		boardservice.deleteBoard(board);
		return "redirect:/board/search";
	}

	@GetMapping(value = "/board/edit")
    @PreAuthorize("hasRole('ROLE_ADMIN') or @boardService.isAuthor(#board)")
	public String boardEdit(Model model, Board board) {
//		String check=boardservice.infoBoard(board).getbWriter();
//		String checkS=(String) session.getAttribute("loggedInUser");
//		if(!checkS.equals(check)) {
//			model.addAttribute("warning","warning");
//			return "redirect:/board/search";
//		}
		  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication.getAuthorities());
		  if (authentication != null) {
            model.addAttribute("authorities", authentication.getAuthorities());
        }
		System.out.println(boardservice.isAuthor(board));
		model.addAttribute("board", boardservice.infoBoard(board));
		return "board-edit";
	}

	@RequestMapping(value = "/board/editpro")
   @PreAuthorize("hasRole('ROLE_ADMIN') or @boardService.isAuthor(#board)")
	public String boardEditPro(Model model, Board board) {
//		String check=boardservice.infoBoard(board).getbWriter();
//		if((String)session.getAttribute("loggedInUser")!=check) {
//			model.addAttribute("warning","warning");
//			return "redirect:/board/search";
//		}
		boardservice.editBoard(board);

		return "redirect:/board/info?bId=" + board.getbId();
	}

	@GetMapping(value = "/board/reply")
	public String boardReply(Model model, Board board) {
		model.addAttribute("board", boardservice.infoBoard(board));
		return "board-reply";
	}

	@PostMapping(value = "/board/replypro") // 답글
	public String replyBoardPro(Model model, Board board, HttpSession session) {
		model.addAttribute("board", boardservice.infoBoard(board));
		board.setbWriter((String) session.getAttribute("loggedInUser"));
		boardservice.replyBoard(board);
		return "redirect:/board/search"; // 이전화면으로 리다이렉트
	}
	
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping(value = "/userInfo") // 답글
	public String userInfo(Model model, User user, HttpSession session) {
		model.addAttribute("user", userservice.infoUser(user));
		model.addAttribute("boardcount", boardservice.countBoardbyuser(user.getUsername()));
		//board.setbWriter((String) session.getAttribute("loggedInUser"));
		return "user-info"; // 이전화면으로 리다이렉트
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping(value = "/user/list")
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
	
	@RequestMapping(value = "/user/edit")
	@ResponseBody
	public String userEdit(@RequestBody  User user) {
		userservice.editAuthority(user);
		  return "success";
	}
	
	
	
	 
	

	
    	  
}
	
	
	
	
	
