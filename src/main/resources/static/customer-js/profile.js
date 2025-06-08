// Mock User Data
const userData = {
    id: "user123",
    firstName: "John",
    lastName: "Doe",
    email: "john.doe@example.com",
    phone: "(555) 123-4567",
    address: {
        street: "123 Main Street",
        city: "Anytown",
        state: "CA",
        zip: "12345"
    },
    dob: "1985-06-15",
    ssn: "****-**-1234",
    joinedDate: new Date("2023-01-15"),
    lastLogin: new Date("2023-05-15"),
    preferences: {
        notifications: {
            security: {
                loginAttempts: {
                    email: true,
                    sms: true,
                    push: false
                },
                passwordChanges: {
                    email: true,
                    sms: true,
                    push: false
                }
            },
            transactions: {
                deposits: {
                    email: true,
                    sms: false,
                    push: true
                },
                withdrawals: {
                    email: true,
                    sms: true,
                    push: true
                },
                largeTransactions: {
                    email: true,
                    sms: true,
                    push: true
                }
            },
            balance: {
                lowBalance: {
                    email: true,
                    sms: true,
                    push: false
                }
            }
        },
        contact: {
            marketing: true,
            education: true,
            events: false,
            statementDelivery: "electronic",
            statementFrequency: "monthly"
        },
        security: {
            twoFactorAuth: true,
            tfaMethod: "sms"
        }
    }
};

// Utility Functions
function formatDate(date) {
    return new Intl.DateTimeFormat('en-US', {
        month: 'long',
        day: 'numeric',
        year: 'numeric',
    }).format(date);
}

// Initialize Profile Page
document.addEventListener('DOMContentLoaded', function() {
    // Set welcome message with user name
    document.querySelector('.welcome-text').textContent = `Welcome, ${userData.firstName} ${userData.lastName}`;

    // Set user data in profile header
    document.getElementById('user-full-name').textContent = `${userData.firstName} ${userData.lastName}`;
    document.getElementById('user-email').textContent = userData.email;
    document.getElementById('user-joined-date').textContent = formatDate(userData.joinedDate);
    document.getElementById('user-last-login').textContent = formatDate(userData.lastLogin);

    // Initialize tabs
    initializeTabs();

    // Setup form submissions
    initializeFormSubmissions();

    // Setup logout button
    document.getElementById('logout-button').addEventListener('click', function() {
        console.log('Logout clicked');
        alert('Logging out...');
    });

    // Setup close button for notification
    document.querySelectorAll('.notification-close').forEach(button => {
        button.addEventListener('click', function() {
            this.closest('.notification-popup').classList.remove('visible');
        });
    });
});

function initializeTabs() {
    const tabButtons = document.querySelectorAll('.tab-button');

    tabButtons.forEach(button => {
        button.addEventListener('click', function() {
            // Remove active class from all buttons and panes
            document.querySelectorAll('.tab-button').forEach(btn => {
                btn.classList.remove('active');
            });

            document.querySelectorAll('.tab-pane').forEach(pane => {
                pane.classList.remove('active');
            });

            // Add active class to clicked button and corresponding pane
            this.classList.add('active');
            const tabId = `${this.getAttribute('data-tab')}-tab`;
            document.getElementById(tabId).classList.add('active');
        });
    });
}

function initializeFormSubmissions() {
    // Personal Information Form
    document.getElementById('personal-form').addEventListener('submit', function(e) {
        e.preventDefault();

        // Update user data (in a real app, this would be an API call)
        userData.firstName = document.getElementById('first-name').value;
        userData.lastName = document.getElementById('last-name').value;
        userData.email = document.getElementById('email').value;
        userData.phone = document.getElementById('phone').value;
        userData.address.street = document.getElementById('address').value;
        userData.address.city = document.getElementById('city').value;
        userData.address.state = document.getElementById('state').value;
        userData.address.zip = document.getElementById('zip').value;
        userData.dob = document.getElementById('dob').value;

        // Update displayed name
        document.getElementById('user-full-name').textContent = `${userData.firstName} ${userData.lastName}`;
        document.getElementById('user-email').textContent = userData.email;
        document.querySelector('.welcome-text').textContent = `Welcome, ${userData.firstName} ${userData.lastName}`;

        // Show success notification
        showSuccessNotification('Personal information updated successfully');
    });

    // Password Form
    document.getElementById('password-form').addEventListener('submit', function(e) {
        e.preventDefault();

        const currentPassword = document.getElementById('current-password').value;
        const newPassword = document.getElementById('new-password').value;
        const confirmPassword = document.getElementById('confirm-password').value;

        // Basic validation
        if (!currentPassword || !newPassword || !confirmPassword) {
            alert("Please fill in all password fields");
            return;
        }

        if (newPassword !== confirmPassword) {
            alert("New password and confirmation do not match");
            return;
        }

        // In a real app, there would be API call to change the password
        console.log('Password change requested');

        // Reset form
        this.reset();

        // Show success notification
        showSuccessNotification('Password updated successfully');
    });

    // Two-Factor Authentication Toggle
    document.getElementById('tfa-toggle').addEventListener('change', function() {
        userData.preferences.security.twoFactorAuth = this.checked;
        document.querySelector('.tfa-settings').style.display = this.checked ? 'block' : 'none';
    });

    // Update TFA Settings
    document.getElementById('update-tfa-button').addEventListener('click', function() {
        const selectedMethod = document.querySelector('input[name="tfa-method"]:checked').value;
        userData.preferences.security.tfaMethod = selectedMethod;

        // Show success notification
        showSuccessNotification('Two-factor authentication settings updated');
    });

    // Session Management
    document.getElementById('signout-all-button').addEventListener('click', function() {
        // In a real app, this would call an API to invalidate all other sessions
        console.log('Sign out all other sessions requested');

        // Show success notification
        showSuccessNotification('All other sessions have been signed out');
    });

    // Session Sign Out buttons
    document.querySelectorAll('.session-item .text-button.danger').forEach(button => {
        button.addEventListener('click', function() {
            // In a real app, this would call an API to invalidate the specific session
            const sessionItem = this.closest('.session-item');
            sessionItem.style.display = 'none';

            // Show success notification
            showSuccessNotification('Session has been signed out');
        });
    });

    // Save Notification Preferences
    document.getElementById('save-notifications').addEventListener('click', function() {
        // In a real app, this would collect all notification preferences and send them to the API
        console.log('Notification preferences update requested');

        // Show success notification
        showSuccessNotification('Notification preferences updated successfully');
    });

    // Save Contact Preferences
    document.getElementById('save-contact').addEventListener('click', function() {
        // Update user preferences (in a real app, this would be an API call)
        userData.preferences.contact.marketing = document.getElementById('marketing-toggle').checked;
        userData.preferences.contact.education = document.getElementById('education-toggle').checked;
        userData.preferences.contact.events = document.getElementById('events-toggle').checked;
        userData.preferences.contact.statementDelivery = document.querySelector('input[name="statement-delivery"]:checked').value;
        userData.preferences.contact.statementFrequency = document.getElementById('statement-frequency').value;

        // Show success notification
        showSuccessNotification('Contact preferences updated successfully');
    });
}

function showSuccessNotification(message) {
    const notification = document.getElementById('success-notification');
    const messageElement = notification.querySelector('.notification-message');

    messageElement.textContent = message;
    notification.classList.add('visible');

    // Auto-hide after 5 seconds
    setTimeout(() => {
        notification.classList.remove('visible');
    }, 5000);
}
