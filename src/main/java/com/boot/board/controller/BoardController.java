package com.boot.board.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
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
import com.boot.board.service.BoardService;
import com.boot.board.service.UserService;
import com.boot.board.service.WeatherService;
import com.boot.board.util.PaginationUtil;
import com.boot.board.util.Utils;


@Controller
public class BoardController {
	
    @Autowired Utils util;
	@Autowired BoardService boardservice;
	@Autowired UserService userservice;
	@Autowired PasswordEncoder encoder;
	@Autowired WeatherService weatherService;
    @Autowired private HttpSession session;
	
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index() {
		System.out.println("인덱스 페이지 호출");
		return "test";
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
	@RequestMapping("/signUp") 
	public String signup() {
		return "/signup";
	}
	
	@PostMapping("/signUpPro") 
	  public String signupPro(Model model, User user) {
	     	
	
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
	      
	      model.addAttribute("signupSuccess", true); // 가입 성공 여부를 모델에 추가
	      return "/index";
	}
	
	
	 @PostMapping("/signIn")
	    public String signIn(User user, Model model, HttpSession session) {

		 	if (!userservice.userExist(user.getUsername())) {
		 		model.addAttribute("errorId", "존재하는 하지않는 ID 입니다.");
	            return "index";
	        }

	        if (!userservice.passMatch(user.getUsername(), user.getPassword())) {
	        	model.addAttribute("errorPassword", "비밀번호 불일치");
	            return "index";
	        }
	        
		 	User userexist= userservice.infoUser(user);
	        session.setAttribute("loggedInUser", user.getUsername());
	        session.setAttribute("userAuth", userexist.getuAuth());
	        
	        return "redirect:/board/search";
	    }
	
	  @GetMapping("/logout")
	    public String logout(HttpServletRequest request) {
	        HttpSession session = request.getSession(false); // 세션이 존재하지 않으면 null 반환

	        if (session != null) {
	            session.invalidate(); // 세션 무효화
	        }

	        return "redirect:/"; // 홈 페이지로 리디렉션
	    }

//	@GetMapping(value = "/board/list")   
//	public String boardList(Model model, 
//			@RequestParam(defaultValue = "1") int page,
//            @RequestParam(defaultValue = "10") int size,
//            HttpSession session) {
//		String loggedInUser = (String) session.getAttribute("loggedInUser");
//		List<Board> list = boardservice.selectBoardList(page, size);
//		
//        int totalBoards = boardservice.countBoard();
//        int totalPages = (int) Math.ceil((double) totalBoards / size);
//
//        int startPage = Math.max(1, page - 4);
//        int endPage = Math.min(startPage + 5, totalPages);
//
//        List<Integer> pageNumbers = new ArrayList<>();
//        for (int i = startPage; i <= endPage; i++) {
//            pageNumbers.add(i);
//        }
//        model.addAttribute("boards", list);
//        model.addAttribute("size", size);
//        model.addAttribute("nowPage", page);  // 현재 페이지 번호
//        model.addAttribute("startPage", startPage);  // 시작 페이지 번호
//        model.addAttribute("endPage", endPage);  // 끝 페이지 번호
//        model.addAttribute("totalPages", totalPages);  // 전체 페이지 수
//        model.addAttribute("pageNumbers", pageNumbers);  // 페이지 번호 목록
//        model.addAttribute("loggedInUser", loggedInUser); // 접속중인 유저 id 
//		return "board_list";
//	}
	
	
	@GetMapping(value = "/board/search")
	public String boardSearch(Model model, 
			@RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            HttpSession session, Search search) throws IOException {
		
        String lat = "35.8722";
        String lon = "128.6025";

	    Map<String, Object> weatherData = weatherService.getCurrentWeather(lat, lon);
	    model.addAttribute("weatherData", weatherData);
		
		String loggedInUser = (String) session.getAttribute("loggedInUser");
		String userAuth = (String) session.getAttribute("userAuth");
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
        model.addAttribute("loggedInUser", loggedInUser); // 접속중인 유저 id 
        model.addAttribute("userAuth", userAuth); // 접속중인 유저 id 
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
		System.out.println(boardservice.isAuthor( board));
		model.addAttribute("board", boardservice.infoBoard(board));
		return "board-edit";
	}

	@RequestMapping(value = "/board/editpro")
    @PreAuthorize("hasRole('ROLE_ADMIN') or @boardService.isAuthor(#board)")
	public String boardEditPro(Model model, Board board) {
		String check=boardservice.infoBoard(board).getbWriter();
		if(session.getAttribute("loggedInUser")!=check) {
			model.addAttribute("warning","warning");
			return "redirect:/board/search";
		}
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
	
	@GetMapping(value = "/userInfo") // 답글
	public String userInfo(Model model, User user, HttpSession session) {
		model.addAttribute("user", userservice.infoUser(user));
		model.addAttribute("boardcount", boardservice.countBoardbyuser(user.getUsername()));
		//board.setbWriter((String) session.getAttribute("loggedInUser"));
		return "user-info"; // 이전화면으로 리다이렉트
	}
	
	
	@GetMapping(value = "/user/list")
	public String userList(Model model, 
			@RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            HttpSession session) {
		String loggedInUser = (String) session.getAttribute("loggedInUser");
	    String userAuth = (String) session.getAttribute("userAuth");
	    if (!"ROLE_ADMIN".equals(userAuth)) {
	        return "redirect:/board/search"; 
	    }
		
		
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
	
	
	
	
	
