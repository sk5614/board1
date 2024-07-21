package com.boot.board.domain;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


public class User implements UserDetails {

	
	
	
	private static final long serialVersionUID = 1L;
	


	private String username; //u_id
	private String password; 
	private String uDate;
	private String uAuth;
	
	
	private boolean isAccountNonExpired;
	private boolean isAccountNonLocked;
	private boolean isCredentialsNonExpired;
	private boolean isEnabled;
	private Collection<? extends GrantedAuthority> authorities;
	

	public String getuDate() {
		return uDate;
	}

	public void setuDate(String uDate) {
		this.uDate = uDate;
	}

	public String getuAuth() {
		return uAuth;
	}

	public void setuAuth(String uAuth) {
		this.uAuth = uAuth;
	}
	
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        if (uAuth == null || uAuth.isEmpty()) {
//            throw new IllegalArgumentException("Authority cannot be null or empty");
//        }
//        return Collections.singletonList(new SimpleGrantedAuthority(uAuth));
//    }
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (authorities == null || authorities.isEmpty()) {
            throw new IllegalArgumentException("Authority cannot be null or empty");
        }
        return authorities;
    }

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}
	
	@Override
	public boolean isAccountNonExpired() {   // 계정 만료 여부 boolean 으로 반환  유효기간 관리용
		return isAccountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {  // 사용자 계쩡 잠긴 여부 반환  , 로그인 여러번 실패시 계정 잠그는 기능 추가할떄 
		return isAccountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {  // 자격증명(비밀번호등) 만료여부 반환, 비밀번호 변경 주기 강제할떄 사용 
		return isCredentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {  // 계정 활성화 여부본환 , 관리자가 계정 활성화 조정할 떄 사용 
		return isEnabled;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setAccountNonExpired(boolean isAccountNonExpired) {
		this.isAccountNonExpired = isAccountNonExpired;
	}

	public void setAccountNonLocked(boolean isAccountNonLocked) {
		this.isAccountNonLocked = isAccountNonLocked;
	}

	public void setCredentialsNonExpired(boolean isCredentialsNonExpired) {
		this.isCredentialsNonExpired = isCredentialsNonExpired;
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

	
	
}
