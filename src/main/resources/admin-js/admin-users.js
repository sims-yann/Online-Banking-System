// Mock Data
const users = [
    {
        id: "user1",
        name: "John Doe",
        email: "john.doe@example.com",
        password: "password123",
        phone: "555-1234",
        role: "customer",
        createdAt: new Date("2023-04-15"),
        status: "active"
    },
    {
        id: "user2",
        name: "Jane Smith",
        email: "jane.smith@example.com",
        password: "password456",
        phone: "555-5678",
        role: "customer",
        createdAt: new Date("2023-04-20"),
        status: "active"
    },
    {
        id: "user3",
        name: "Alice Johnson",
        email: "alice.johnson@example.com",
        password: "password789",
        phone: "555-9012",
        role: "customer",
        createdAt: new Date("2023-05-01"),
        status: "inactive"
    },
    {
        id: "user4",
        name: "Bob Brown",
        email: "bob.brown@example.com",
        password: "password101112",
        phone: "555-3456",
        role: "customer",
        createdAt: new Date("2023-05-05"),
        status: "active"
    },
    {
        id: "admin1",
        name: "Admin User",
        email: "admin@example.com",
        password: "adminPass123",
        phone: "555-0000",
        role: "admin",
        createdAt: new Date("2023-01-01"),
        status: "active"
    }
];

// Variables
let selectedUserId = null;
let filteredUsers = [...users];

// Utility Functions
function formatDate(date) {
    return new Intl.DateTimeFormat('en-US', {
        year: 'numeric',
        month: 'short',
        day: 'numeric'
    }).format(date);
}

function capitalizeFirstLetter(string) {
    return string.charAt(0).toUpperCase() + string.slice(1);
}

// Page Initialization
document.addEventListener('DOMContentLoaded', function() {
    // Set welcome message
    document.querySelector('.welcome-text').textContent = `Welcome, ${users.find(user => user.role === 'admin').name}`;

    // Render users table
    renderUsersTable();

    // Setup search functionality
    const searchInput = document.getElementById('search-users');
    searchInput.addEventListener('input', function() {
        const searchTerm = this.value.toLowerCase();
        filteredUsers = users.filter(user =>
            user.name.toLowerCase().includes(searchTerm) ||
            user.email.toLowerCase().includes(searchTerm)
        );
        renderUsersTable();
    });

    // Setup add user button
    document.getElementById('add-user-button').addEventListener('click', function() {
        alert('Add new user functionality will be implemented soon');
    });

    // Setup logout button
    document.getElementById('logout-button').addEventListener('click', function() {
        console.log('Logout clicked');
        alert('Logging out...');
        window.location.href = "/login";
    });

    // Setup user actions dropdown functionality
    setupUserActionsDropdown();

    // Close dropdown when clicking elsewhere
    window.addEventListener('click', function(event) {
        const dropdown = document.getElementById('user-actions-dropdown');
        if (!event.target.matches('.actions-button') && !dropdown.contains(event.target)) {
            closeActionsDropdown();
        }
    });
});

function renderUsersTable() {
    const tableBody = document.querySelector('#users-table tbody');
    tableBody.innerHTML = '';

    filteredUsers.forEach(user => {
        const tr = document.createElement('tr');

        // User cell with avatar and name
        const tdUser = document.createElement('td');
        tdUser.innerHTML = `
        <div class="user-cell">
          <div class="user-cell-avatar">👤</div>
          ${user.name}
        </div>
      `;

        // Email cell
        const tdEmail = document.createElement('td');
        tdEmail.textContent = user.email;

        // Role cell with badge
        const tdRole = document.createElement('td');
        const roleBadge = document.createElement('span');
        roleBadge.className = `badge ${user.role === 'admin' ? 'badge-primary' : 'badge-outline'}`;
        roleBadge.textContent = user.role;
        tdRole.appendChild(roleBadge);

        // Status cell with badge
        const tdStatus = document.createElement('td');
        const statusBadge = document.createElement('span');
        statusBadge.className = `badge ${
            user.status === 'active' ? 'badge-success' : 'badge-danger'
        }`;
        statusBadge.textContent = user.status;
        tdStatus.appendChild(statusBadge);

        // Created date cell
        const tdCreated = document.createElement('td');
        tdCreated.textContent = formatDate(user.createdAt);

        // Actions cell with dropdown button
        const tdActions = document.createElement('td');
        const actionsButton = document.createElement('button');
        actionsButton.className = 'actions-button';
        actionsButton.setAttribute('data-user-id', user.id);
        actionsButton.innerHTML = '⋮';
        tdActions.appendChild(actionsButton);

        // Append all cells to the row
        tr.appendChild(tdUser);
        tr.appendChild(tdEmail);
        tr.appendChild(tdRole);
        tr.appendChild(tdStatus);
        tr.appendChild(tdCreated);
        tr.appendChild(tdActions);

        // Append the row to the table body
        tableBody.appendChild(tr);
    });

    // Re-attach event listeners to action buttons
    document.querySelectorAll('.actions-button').forEach(button => {
        button.addEventListener('click', function(e) {
            e.stopPropagation();
            selectedUserId = this.getAttribute('data-user-id');
            showActionsDropdown(this);
        });
    });
}

function setupUserActionsDropdown() {
    // View user details
    document.getElementById('view-user-btn').addEventListener('click', function() {
        alert(`View details for user ID: ${selectedUserId}`);
        closeActionsDropdown();
    });

    // Edit user
    document.getElementById('edit-user-btn').addEventListener('click', function() {
        alert(`Edit user ID: ${selectedUserId}`);
        closeActionsDropdown();
    });

    // Toggle user status (activate/deactivate)
    document.querySelector('.status-toggle-btn').addEventListener('click', function() {
        const user = users.find(u => u.id === selectedUserId);
        const newStatus = user.status === 'active' ? 'inactive' : 'active';
        const message = newStatus === 'active' ? 'activated' : 'deactivated';

        // In a real app, this would be an API call
        // For demo, we'll just update the local data
        const userIndex = users.findIndex(u => u.id === selectedUserId);
        users[userIndex].status = newStatus;
        filteredUsers = [...users]; // Update filtered users

        alert(`User ${user.name} has been ${message}`);
        renderUsersTable();
        closeActionsDropdown();
    });

    // Delete user
    document.querySelector('.delete-user-btn').addEventListener('click', function() {
        const user = users.find(u => u.id === selectedUserId);

        if (confirm(`Are you sure you want to delete user ${user.name}?`)) {
            // In a real app, this would be an API call
            // For demo, we'll just update the local data
            const userIndex = users.findIndex(u => u.id === selectedUserId);
            users.splice(userIndex, 1);
            filteredUsers = [...users]; // Update filtered users

            alert(`User ${user.name} has been deleted`);
            renderUsersTable();
        }

        closeActionsDropdown();
    });
}

function showActionsDropdown(buttonElement) {
    const dropdown = document.getElementById('user-actions-dropdown');
    const user = users.find(u => u.id === selectedUserId);

    // Update the status toggle button text based on current user status
    const statusToggleBtn = dropdown.querySelector('.status-toggle-btn');
    statusToggleBtn.textContent = user.status === 'active' ? 'Deactivate' : 'Activate';

    // Position the dropdown next to the button
    const buttonRect = buttonElement.getBoundingClientRect();
    dropdown.style.top = `${buttonRect.bottom}px`;
    dropdown.style.left = `${buttonRect.left - 160 + buttonRect.width}px`; // Align right edge

    // Show the dropdown
    dropdown.classList.add('active');
}

function closeActionsDropdown() {
    const dropdown = document.getElementById('user-actions-dropdown');
    dropdown.classList.remove('active');
    selectedUserId = null;
}
