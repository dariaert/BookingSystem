const namePattern = /^[а-яА-ЯёЁ]+$/;
const passwordPattern = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[!@#$%^&*])[A-Za-z\d!@#$%^&*]{8,}$/;

function validateField(id, pattern, errorMessage) {
    const value = document.getElementById(id).value;
    const errorDiv = document.getElementById(id + '_error');
    if (!pattern.test(value)) {
        errorDiv.textContent = errorMessage;
        errorDiv.style.color = '#575757';
        errorDiv.style.fontSize = '12px';
        return false;
    } else {
        errorDiv.textContent = '';
        return true;
    }
}

function validatePassword() {
    const password = document.getElementById('password').value;
    const passwordHint = document.getElementById('password-hint');
    const passwordError = document.getElementById('password_error');
    if (!passwordPattern.test(password)) {
        passwordError.style.color = '#575757';
        passwordError.style.fontSize = '12px';
        passwordError.textContent = 'Пароль не соответствует требованиям.';
        return false;
    } else {
        passwordHint.style.color = 'green';
        passwordError.textContent = '';
        return true;
    }
}

function validatePasswordConfirmation() {
    const password = document.getElementById('password').value;
    const passwordConfirmation = document.getElementById('password_confirmation').value;
    const passwordConfirmationError = document.getElementById('password_confirmation_error');
    if (password !== passwordConfirmation) {
        passwordConfirmationError.textContent = 'Пароли не совпадают.';
        passwordConfirmationError.style.color = '#575757';
        passwordConfirmationError.style.fontSize = '12px';
        return false;
    } else {
        passwordConfirmationError.textContent = '';
        return true;
    }
}

// Обработчик отправки формы
document.getElementById('registerForm').addEventListener('submit', function(event) {
    let isValid = true;

    // Проверка каждого поля
    isValid &= validateField('FIO', namePattern, 'Имя должно содержать только кириллические символы.');
    isValid &= validatePassword();
    isValid &= validatePasswordConfirmation();

    // Если валидация не прошла, отменяем отправку формы
    if (!isValid) {
        event.preventDefault();
    }
});

// Слушатели для ввода
document.getElementById('FIO').addEventListener('input', function() {
    validateField('FIO', namePattern, 'Имя должно содержать только кириллические символы.');
});
document.getElementById('password').addEventListener('input', function() {
    validatePassword();
});
document.getElementById('password_confirmation').addEventListener('input', function() {
    validatePasswordConfirmation();
});