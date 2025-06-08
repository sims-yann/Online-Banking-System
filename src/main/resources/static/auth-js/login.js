document.addEventListener('DOMContentLoaded', function() {
    const loginForm = document.getElementById('login-form');

    loginForm.addEventListener('submit', function(event) {
        event.preventDefault();

        const email = document.getElementById('email').value;
        const password = document.getElementById('password').value;

        // Mock user data (in a real app this would be verified by the server)
        const users = [
            { email: 'admin@bankingsystem.com', password: 'admin123', name: 'Admin User', role: 'admin' },
            { email: 'john@example.com', password: 'password123', name: 'John Doe', role: 'customer' }
        ];

        const user = users.find(u => u.email === email && u.password === password);

        if (user) {
            // Store user info in localStorage (in a real app, this would be a JWT token)
            localStorage.setItem('currentUser', JSON.stringify({
                name: user.name,
                email: user.email,
                role: user.role
            }));

            // Show success message
            showMessage('Login successful! Redirecting...', 'success');

            // Redirect based on user role
            setTimeout(() => {
                if (user.role === 'admin') {
                    window.location.href = '../admin-html/admin-dashboard.html';
                } else {
                    window.location.href = '../user/user-html/userdashboard.html';
                }
            }, 1000);
        } else {
            // Show error message
            showMessage('Invalid email or password. Please try again.', 'error');
        }
    });

    function showMessage(text, type) {
        // Create message element
        const message = document.createElement('div');
        message.textContent = text;
        message.style.padding = '0.75rem';
        message.style.marginBottom = '1rem';
        message.style.borderRadius = '0.375rem';
        message.style.textAlign = 'center';

        if (type === 'success') {
            message.style.backgroundColor = '#d1fae5';
            message.style.color = '#047857';
        } else {
            message.style.backgroundColor = '#fee2e2';
            message.style.color = '#b91c1c';
        }

        // Insert before the form
        const form = document.getElementById('login-form');
        form.parentNode.insertBefore(message, form);

        // Remove after 3 seconds
        setTimeout(() => {
            message.remove();
        }, 3000);
    }
});
