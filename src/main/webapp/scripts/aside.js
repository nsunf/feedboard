const loginFrm = document.loginFrm;
const signupFrm = document.signupFrm;

function loginFormCheck() {
	const userId = loginFrm.user_id; 
	const userPw = loginFrm.user_pw; 

	if (userId.value == "" || userId.value.length < 6) {
		alert("enter id 6~13");
		userId.focus();
		return false;
	} else if (userPw.value == "" || userId.value.length < 6) {
		alert("enter password 6~13");
		userPw.focus();
		return false;
	}
	
	return true;
}

function signupFormCheck() {
	const userId = signupFrm.user_id;
	const userPw = signupFrm.user_pw;
	const userConf = signupFrm.user_conf;
	const userName = signupFrm.user_name;
	const userSsn = signupFrm.user_ssn;
	const userPhone = signupFrm.user_phone;
	
	if (userId.value == "" || userId.value.length < 6) {
		alert("enter id 6~13");
		userId.focus();
		return false;
	} else if (userPw.value == "" || userPw.value.length < 6) {
		alert("enter password 6~13");
		userPw.focus();
		return false;
	} else if (userConf.value != userPw.value) {
		alert("enter password confirm");
		userConf.focus();
		return false;
	} else if (userName.value == "" || userName.value.length < 2) {
		alert("enter name");
		userName.focus();
		return false;
	} else if (userSsn.value == "" || userSsn.value.length != 14) {
		alert("enter ssn '991212-1234567'");
		userSsn.focus();
		return false;
	} else if (userPhone.value == "" || userPhone.value.length < 9) {
		alert("enter phone includes '-'");
		userPhone.focus();
		return false;
	}
	return true;
}

document.querySelector(".login-btn").addEventListener("click", e => {
	e.preventDefault();
	if (loginFormCheck())
		loginFrm.submit();
})

document.querySelector(".sign-btn").addEventListener("click", e => {
	e.preventDefault();
	if (signupFormCheck())
		signupFrm.submit();
});