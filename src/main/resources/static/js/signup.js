const signUpButton = document.getElementById('signUp');
const signInButton = document.getElementById('signIn');
const container = document.getElementById('container');
var errorExId = '<%= request.getAttribute("errorExId") != null ? request.getAttribute("errorExId") : "" %>';

signUpButton.addEventListener('click', () => {
  container.classList.add("right-panel-active");
});

signInButton.addEventListener('click', () => {
  container.classList.remove("right-panel-active");
});


document.addEventListener('DOMContentLoaded', function () {
   const signupForm = document.getElementById('signupForm');
    const checkUsernameForm = document.getElementById('checkUsernameForm');
    const password = document.getElementById('password');
    const confirmPassword = document.getElementById('confirmPassword');
    const error = document.getElementById('errormatch');
    const errorUsername = document.getElementById('errorusername');
    const errorUsernameCheck = document.getElementById('errorUsernameCheck');
    const usernameField = document.getElementById('username');
    const checkUsernameField = document.getElementById('checkUsername');
    

    signupForm.addEventListener('submit', function (e) {
        if (password.value !== confirmPassword.value) {
            e.preventDefault();
            error.style.display = 'block';
        } else {
            error.style.display = 'none';
        }
    });
    
    
    
    
    
  /*   checkUsernameForm.addEventListener('submit', function (e) {
        e.preventDefault(); // 폼 기본 제출 동작 막기

        fetch('/checkUsername', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(checkUsernameField.value)
        })
        .then(response => {
            if (response.status === 409) {
                errorUsernameCheck.style.display = 'block'; // 중복 오류 메시지 표시
            } else if (response.status === 200) {
                errorUsernameCheck.style.display = 'none'; // 오류 메시지 숨기기
            }
        })
        .catch(error => {
            console.error('Error checking username:', error);
            // 오류 처리
        });
    });*/
});

