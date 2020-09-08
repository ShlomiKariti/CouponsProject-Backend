package com.shlomi.coupons.beans;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;



@Entity
@Table(name = "customers")
public class Customer {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private long id;
	
	@Column(name = "Name", unique = false, nullable = false)
	private String name;
	
	@Column(name = "email", unique = true, nullable = false)
	private String email;

    @Column(name = "age", unique = false, nullable = false)
    private int age;
    
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @MapsId
    private User user;

    public long getId() {
        return user.getId();
    }

    public void setId(long id) {
        this.user.setId(id);
    }



    public String getPassword() {
        return user.getPassword();
    }

    public void setPassword(String password) {
        this.user.setPassword(password);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUsername() {
        return user.getUsername();
    }

    public void setUsername(String username) {
        this.user.setUsername(username);
    }

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", name=" + name + ", email=" + email + ", user=" + user + ", age=" + age + "]";
	}


}