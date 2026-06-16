document.addEventListener('DOMContentLoaded', () => {
    const loginForm = document.getElementById('loginForm');
    
    if (loginForm) {
        loginForm.addEventListener('submit', async (e) => {
            e.preventDefault();
            
            const email = document.getElementById('email').value;
            const password = document.getElementById('password').value;
            
            try {
                await login(email, password);
                window.location.href = '/web/dashboard';
            } catch (error) {
                showMessage('message', `Login failed: ${error.message}`, 'error');
            }
        });
    }
});
