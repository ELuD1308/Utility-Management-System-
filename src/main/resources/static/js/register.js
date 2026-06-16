document.addEventListener('DOMContentLoaded', () => {
    const registerForm = document.getElementById('registerForm');
    
    if (registerForm) {
        registerForm.addEventListener('submit', async (e) => {
            e.preventDefault();
            
            const fullName = document.getElementById('fullName').value;
            const firstName = document.getElementById('firstName').value;
            const lastName = document.getElementById('lastName').value;
            const email = document.getElementById('email').value;
            const password = document.getElementById('password').value;
            const phoneNumber = document.getElementById('phoneNumber').value;
            
            try {
                await register(fullName, firstName, lastName, email, password, phoneNumber);
                showMessage('message', 'Registration successful! Redirecting to login...', 'success');
                setTimeout(() => {
                    window.location.href = '/web/login';
                }, 2000);
            } catch (error) {
                showMessage('message', `Registration failed: ${error.message}`, 'error');
            }
        });
    }
});
