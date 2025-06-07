document.addEventListener('DOMContentLoaded', function() {
    const registerForm = document.getElementById('register-form');

    registerForm.addEventListener('submit', function(event) {
        event.preventDefault();

        const name = document.getElementById('name').value;
        const email = document.getElementById('email').value;
        const phone = document.getElementById('phone').value;
        const password = document.getElementById('password').value;
        const confirmPassword = document.getElementById('confirmPassword').value;

        // Basic validation
        if (password !== confirmPassword) {
            showMessage('Passwords do not match', 'error');
            return;
        }

        // Mock user data check (in a real app this would be verified by the server)
        const existingUsers = [
            { email: 'admin@bankingsystem.com' },
            { email: 'john@example.com' }
        ];

        const emailExists = existingUsers.some(user => user.email === email);
        if (emailExists) {
            showMessage('Email already in use', 'error');
            return;
        }

        // In a real app, this would be an API call to create a new user
        // For demo purposes, just showing success message
        showMessage('Registration successful! You can now log in.', 'success');

        // Redirect to login page after successful registration
        setTimeout(() => {
            window.location.href = '/login';
        }, 2000);
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
        const form = document.getElementById('register-form');
        form.parentNode.insertBefore(message, form);

        // Remove after 3 seconds
        setTimeout(() => {
            message.remove();
        }, 3000);
    }
});
