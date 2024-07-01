package com.boot.board.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.boot.board.domain.Board;
import com.boot.board.domain.User;
import com.boot.board.service.BoardService;
import com.boot.board.service.UserService;

@Controller
public class BoardController {

	@Autowired BoardService boardservice;
	@Autowired UserService userservice;
	@Autowired PasswordEncoder encoder;
	
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index() {
		System.out.println("인덱스 페이지 호출");
		return "/index";
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
	  public String signupPro(User user) {
	     
		  String encodedPassword = encoder.encode(user.getPassword());  //비밀번호 암호화
	      
	      user.setPassword(encodedPassword);
	      user.setAccountNonExpired(true);
	      user.setEnabled(true);
	      user.setAccountNonLocked(true);
	      user.setCredentialsNonExpired(true);
	      user.setAuthorities(AuthorityUtils.createAuthorityList("ROLE_USER")); // 유저 권한세팅 ROLE_USER auth에 저장    
	      
	      userservice.createUser(user);
	      userservice.createAuthorities(user);
	      
	      return "/index";
	}
	
	
	 @PostMapping("/signIn")
	    public String signIn(User user, Model model, HttpSession session) {
	        if (!userservice.userExist(user.getUsername())) {
	         //   model.addAttribute("error-id", "아이디 불일치");
	            return "/index";
	        }

	        if (!userservice.passMatch(user.getUsername(), user.getPassword())) {
	           // model.addAttribute("error-password", "비밀번호 불일치");
	            return "/index";
	        }
	        
	        session.setAttribute("loggedInUser", user.getUsername());

	        return "redirect:/board/list";
	    }
	
	  @GetMapping("/logout")
	    public String logout(HttpServletRequest request) {
	        HttpSession session = request.getSession(false); // 세션이 존재하지 않으면 null 반환

	        if (session != null) {
	            session.invalidate(); // 세션 무효화
	        }

	        return "redirect:/"; // 홈 페이지로 리디렉션
	    }

	@GetMapping(value = "/board/list")
	public String boardList(Model model, 
			@RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            HttpSession session) {
		String loggedInUser = (String) session.getAttribute("loggedInUser");
		List<Board> list = boardservice.selectBoardList(page, size);
		
        int totalBoards = boardservice.countBoard();
        int totalPages = (int) Math.ceil((double) totalBoards / size);

        int startPage = Math.max(1, page - 4);
        int endPage = Math.min(startPage + 5, totalPages);

        List<Integer> pageNumbers = new ArrayList<>();
        for (int i = startPage; i <= endPage; i++) {
            pageNumbers.add(i);
        }
        model.addAttribute("boards", list);
        model.addAttribute("size", size);
        model.addAttribute("nowPage", page);  // 현재 페이지 번호
        model.addAttribute("startPage", startPage);  // 시작 페이지 번호
        model.addAttribute("endPage", endPage);  // 끝 페이지 번호
        model.addAttribute("totalPages", totalPages);  // 전체 페이지 수
        model.addAttribute("pageNumbers", pageNumbers);  // 페이지 번호 목록
        model.addAttribute("loggedInUser", loggedInUser); // 접속중인 유저 id 
		return "board_list";
	}

	@GetMapping(value = "/board/write")
	public String boardWrite() {
		return "board_write";
	}

	@PostMapping(value = "/board/writepro")
	public String boardWritePro(Board board,HttpSession session) {
		board.setbWriter((String) session.getAttribute("loggedInUser"));
		boardservice.writeBoard(board);
		return "redirect:/board/list";
	}

	@GetMapping(value = "/board/info")
	public String boardInfo(Model model, Board board,HttpSession session) {
		model.addAttribute("board", boardservice.infoBoard(board));
//		String loggedInUser = (String) session.getAttribute("loggedInUser");
//		model.addAttribute("loggedInUser", loggedInUser); // 접속중인 유저 id 
		return "board_info";
	}

	@GetMapping(value = "/board/delete")
	public String boardDelete(Model model, Board board) {
		boardservice.deleteBoard(board);
		return "redirect:/board/list";
	}

	@GetMapping(value = "/board/edit")
	public String boardEdit(Model model, Board board) {
		model.addAttribute("board", boardservice.infoBoard(board));
		return "board_edit";
	}

	@RequestMapping(value = "/board/editpro")
	public String boardEditPro(Board board) {
		boardservice.editBoard(board);
		return "redirect:/board/info?bId=" + board.getbId();
	}

	@GetMapping(value = "/board/reply")
	public String boardReply(Model model, Board board) {
		model.addAttribute("board", boardservice.infoBoard(board));
		return "/board_reply";
	}

	@PostMapping(value = "/board/replypro") // 답글
	public String replyBoardPro(Model model, Board board, HttpSession session) {
		model.addAttribute("board", boardservice.infoBoard(board));
		board.setbWriter((String) session.getAttribute("loggedInUser"));
		boardservice.replyBoard(board);
		return "redirect:/board/list"; // 이전화면으로 리다이렉트
	}
	
	@RequestMapping(value = "/userInfo") // 답글
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
		List<User> list = userservice.userList(page, size);
		
        int totalBoards = userservice.countUser();
        int totalPages = (int) Math.ceil((double) totalBoards / size);

        int startPage = Math.max(1, page - 4);
        int endPage = Math.min(startPage + 5, totalPages);

        List<Integer> pageNumbers = new ArrayList<>();
        for (int i = startPage; i <= endPage; i++) {
            pageNumbers.add(i);
        }
        model.addAttribute("users", list);
        model.addAttribute("size", size);
        model.addAttribute("nowPage", page);  // 현재 페이지 번호
        model.addAttribute("startPage", startPage);  // 시작 페이지 번호
        model.addAttribute("endPage", endPage);  // 끝 페이지 번호
        model.addAttribute("totalPages", totalPages);  // 전체 페이지 수
        model.addAttribute("pageNumbers", pageNumbers);  // 페이지 번호 목록
        model.addAttribute("loggedInUser", loggedInUser); // 접속중인 유저 id 
		return "user-list";
	}
	
	@PostMapping(value = "/user/edit")
	@ResponseBody
	public String userEdit(@RequestBody  User user) {
		userservice.editAuthority(user);
		  return "success";
	}
	
	
	
	
	  String apiUrl = "http://apis.data.go.kr/1360000/MidFcstInfoService/getMidLandFcst"; // API 엔드포인트 경로 추가
      String serviceKey = "m%2BxUA%2FWqAxyZ09zpr%2BhIrUZg9tM982bvlDNyq8yei8JrK4MawLu8GqvpJwjQLl5VNH5I03uk%2BOO4EJEinHCjOQ%3D%3D"; // U
	

      @Autowired
      private RestTemplate restTemplate;

      @GetMapping("/current")
      public String getCurrentWeather(Model model) {
          try {
              // API 호출을 위한 매개변수 설정
              UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiUrl)
                      .queryParam("serviceKey", serviceKey)
                      .queryParam("dataType", "JSON")
                      .queryParam("base_date", "20220101")
                      .queryParam("base_time", "0500")
                      .queryParam("nx", "60")
                      .queryParam("ny", "127");

              // API 호출 및 응답 받기
              ResponseEntity<String> response = restTemplate.getForEntity(
                      builder.toUriString(), String.class);

              // API 응답 데이터 처리
              if (response.getStatusCode() == HttpStatus.OK) {
                  // API 응답 데이터를 모델에 추가
                  model.addAttribute("weatherData", response.getBody());
                  return "whether"; // whether.jsp 파일의 경로와 이름
              } else {
                  model.addAttribute("error", "Failed to fetch weather data");
                  return "error"; // 에러 페이지로 이동
              }
          } catch (HttpServerErrorException e) {
              model.addAttribute("error", "Internal Server Error: " + e.getMessage());
              return "error"; // 에러 페이지로 이동
          }
      }
	
	
	
	
	
}