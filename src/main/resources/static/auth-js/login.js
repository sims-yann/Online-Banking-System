document.addEventListener('DOMContentLoaded', function() {
    const loginForm = document.getElementById('login-form');

    // loginForm.addEventListener('submit', function(event) {
    //     event.preventDefault();
    //
    //     const email = document.getElementById('email').value;
    //     const password = document.getElementById('password').value;
    //
    //    console.log("email"+email);
    //    console.log("password"+password)
    // });

    // function showMessage(text, type) {
    //     // Create message element
    //     const message = document.createElement('div');
    //     message.textContent = text;
    //     message.style.padding = '0.75rem';
    //     message.style.marginBottom = '1rem';
    //     message.style.borderRadius = '0.375rem';
    //     message.style.textAlign = 'center';
    //
    //     if (type === 'success') {
    //         message.style.backgroundColor = '#d1fae5';
    //         message.style.color = '#047857';
    //     } else {
    //         message.style.backgroundColor = '#fee2e2';
    //         message.style.color = '#b91c1c';
    //     }
    //
    //     // Insert before the form
    //     const form = document.getElementById('login-form');
    //     form.parentNode.insertBefore(message, form);
    //
    //     // Remove after 3 seconds
    //     setTimeout(() => {
    //         message.remove();
    //     }, 3000);
    // }
});
