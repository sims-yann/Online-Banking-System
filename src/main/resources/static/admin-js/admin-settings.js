// Page Initialization
document.addEventListener('DOMContentLoaded', function() {
    // Set welcome message
    document.querySelector('.welcome-text').textContent = `Welcome, Admin`;

    // Setup tabs
    setupTabs();

    // Setup form submissions
    setupForms();

    // Setup logout button
    document.getElementById('logout-button').addEventListener('click', function() {
        console.log('Logout clicked');
        alert('Logging out...');
        window.location.href = "/login";
    });
});

function setupTabs() {
    const tabButtons = document.querySelectorAll('.tab-button');
    const tabContents = document.querySelectorAll('.tab-content');

    tabButtons.forEach(button => {
        button.addEventListener('click', function() {
            const tab = this.getAttribute('data-tab');

            // Hide all tab contents
            tabContents.forEach(content => {
                content.classList.remove('active');
            });

            // Remove active class from all buttons
            tabButtons.forEach(btn => {
                btn.classList.remove('active');
            });

            // Show the selected tab content
            document.getElementById(tab).classList.add('active');

            // Add active class to clicked button
            this.classList.add('active');
        });
    });
}

function setupForms() {
    // Security Settings Form
    document.getElementById('security-form').addEventListener('submit', function(e) {
        e.preventDefault();

        const sessionTimeout = document.getElementById('sessionTimeout').value;
        const requireStrongPassword = document.getElementById('strongPassword').checked;
        const twoFactorAuth = document.getElementById('twoFactor').checked;

        const settings = {
            sessionTimeout,
            requireStrongPassword,
            twoFactorAuth
        };

        // In a real app, this would be an API call to save settings
        console.log('Saving security settings:', settings);
        showSaveSuccess('Security settings');
    });

    // Transaction Settings Form - Now handled by Thymeleaf form submission
    // We'll just log the form submission for debugging
    document.getElementById('transaction-form').addEventListener('submit', function(e) {
        // Don't prevent default - let the form submit normally
        console.log('Transaction form submitted');
    });

    // Notification Settings Form
    document.getElementById('notification-form').addEventListener('submit', function(e) {
        e.preventDefault();

        const emailNotifications = document.getElementById('emailNotify').checked;
        const loginAlerts = document.getElementById('loginAlerts').checked;
        const transactionAlerts = document.getElementById('transactionAlerts').checked;

        const settings = {
            emailNotifications,
            loginAlerts,
            transactionAlerts
        };

        // In a real app, this would be an API call to save settings
        console.log('Saving notification settings:', settings);
        showSaveSuccess('Notification settings');
    });
}

function showSaveSuccess(settingType) {
    // Create a toast-like notification
    const toast = document.createElement('div');
    toast.className = 'toast-notification';
    toast.innerHTML = `
      <div class="toast-content">
        <div class="toast-icon">✓</div>
        <div class="toast-message">${settingType} have been saved successfully.</div>
      </div>
    `;

    document.body.appendChild(toast);

    // Show the toast
    setTimeout(() => {
        toast.classList.add('show');
    }, 100);

    // Hide and remove the toast after 3 seconds
    setTimeout(() => {
        toast.classList.remove('show');
        setTimeout(() => {
            document.body.removeChild(toast);
        }, 300);
    }, 3000);
}

// Add toast styles dynamically
const toastStyle = document.createElement('style');
toastStyle.textContent = `
    .toast-notification {
      position: fixed;
      bottom: 20px;
      right: 20px;
      transform: translateY(100px);
      opacity: 0;
      transition: all 0.3s ease;
      z-index: 1000;
    }

    .toast-notification.show {
      transform: translateY(0);
      opacity: 1;
    }

    .toast-content {
      display: flex;
      align-items: center;
      background-color: #4CAF50;
      color: white;
      padding: 12px 16px;
      border-radius: 6px;
      box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
      min-width: 250px;
    }

    .toast-icon {
      margin-right: 12px;
      font-size: 20px;
    }

    .toast-message {
      font-size: 14px;
    }
  `;
document.head.appendChild(toastStyle);
