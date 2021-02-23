package com.sri.eGameScoreAPI.entity;
	
	import java.util.Set;

	import javax.persistence.Column;
	import javax.persistence.Entity;
	import javax.persistence.GeneratedValue;
	import javax.persistence.GenerationType;
	import javax.persistence.Id;
	import javax.persistence.OneToMany;

	import com.sri.eGameScoreAPI.util.AccountLevel;

	@Entity
	public class Coach {
		
		private Long id;
		private String teamName;
		private String firstName;
		private String lastName;
		private String address;
		private String city;
		private String state;
		private String zip;
		private String phoneNumber;
		private String email;
		private Set<Member> members;
		
		private AccountLevel level;
		
		private String username;
		private String password;
		
		
		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		public Long getId() {
			return id;
		}
		
		public void setId(Long id) {
			this.id = id;
		}
		
		public String getTeamName() {
			return teamName;
		}
		
		public void setTeamName(String teamName) {
			this.teamName = teamName;
		}

		public String getFirstName() {
			return firstName;
		}
		
		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}
		
		public String getLastName() {
			return lastName;
		}
		
		public void setLastName(String lastName) {
			this.lastName = lastName;
		}
		
		public String getAddress() {
			return address;
		}
		
		public void setAddress(String address) {
			this.address = address;
		}
		
		public String getCity() {
			return city;
		}
		
		public void setCity(String city) {
			this.city = city;
		}
		
		public String getState() {
			return state;
		}
		
		public void setState(String state) {
			this.state = state;
		}
		
		public String getZip() {
			return zip;
		}
		
		public void setZip(String zip) {
			this.zip = zip;
		}
		
		public String getPhoneNumber() {
			return phoneNumber;
		}

		public void setPhoneNumber(String phoneNumber) {
			this.phoneNumber = phoneNumber;
		}

		@Column(unique=true)
		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public AccountLevel getLevel() {
			return level;
		}
		
		public void setLevel(AccountLevel level) {
			this.level = level;
		}
		
		@Column(unique=true)
		public String getUsername() {
			return username;
		}
		
		public void setUsername(String username) {
			this.username = username;
		}

		public String getPassword() {
			return password;
		}
		
		public void setPassword(String password) {
			this.password = password;
		}

		@OneToMany(mappedBy = "coach")
		public Set<Member> getMembers() {
			return members;
		}

		public void setMembers(Set<Member> members) {
			this.members = members;
		}
}
