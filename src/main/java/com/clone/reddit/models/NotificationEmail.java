package com.clone.reddit.models;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
 
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationEmail implements Serializable{
	private static final long serialVersionUID = 1L;
	
    private String subject;
    private String recipient;
    private String body;
}