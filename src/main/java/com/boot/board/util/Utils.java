package com.boot.board.util;

import org.springframework.stereotype.Component;

@Component
public class Utils {
    
	
	public int convertTemp(double k) {
    	double c = k-273.15;
		return (int)c;
    }
}
