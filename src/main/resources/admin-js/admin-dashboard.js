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

const accounts = [
    {
        id: "acc1",
        userId: "user1",
        accountNumber: "1234567890",
        type: "checking",
        balance: 2250.00,
        currency: "XAF",
        createdAt: new Date("2023-01-15"),
        status: "active"
    },
    {
        id: "acc2",
        userId: "user1",
        accountNumber: "9876543210",
        type: "savings",
        balance: 3000.00,
        currency: "XAF",
        createdAt: new Date("2023-01-15"),
        status: "active"
    },
    {
        id: "acc3",
        userId: "user2",
        accountNumber: "5678901234",
        type: "checking",
        balance: 1500.00,
        currency: "XAF",
        createdAt: new Date("2023-04-20"),
        status: "active"
    },
    {
        id: "acc4",
        userId: "user3",
        accountNumber: "1122334455",
        type: "savings",
        balance: 500.00,
        currency: "XAF",
        createdAt: new Date("2023-05-01"),
        status: "suspended"
    }
];

const transactions = [
    {
        id: "tr1",
        fromAccountId: "acc1",
        toAccountId: "external1",
        amount: 120.50,
        currency: "XAF",
        type: "transfer",
        status: "completed",
        description: "Payment to Amazon",
        createdAt: new Date("2023-05-10")
    },
    {
        id: "tr2",
        fromAccountId: "external2",
        toAccountId: "acc2",
        amount: 1500.00,
        currency: "XAF",
        type: "deposit",
        status: "completed",
        description: "Salary deposit",
        createdAt: new Date("2023-05-05")
    },
    {
        id: "tr3",
        fromAccountId: "acc1",
        toAccountId: "external3",
        amount: 45.99,
        currency: "XAF",
        type: "transfer",
        status: "completed",
        description: "Netflix subscription",
        createdAt: new Date("2023-05-02")
    },
    {
        id: "tr4",
        fromAccountId: "acc1",
        toAccountId: "",
        amount: 200.00,
        currency: "XAF",
        type: "withdraw",
        status: "pending",
        description: "ATM withdrawal",
        createdAt: new Date("2023-04-28")
    },
    {
        id: "tr5",
        fromAccountId: "acc3",
        toAccountId: "acc2",
        amount: 500.00,
        currency: "XAF",
        type: "transfer",
        status: "failed",
        description: "Transfer to savings",
        createdAt: new Date("2023-04-25")
    }
];

// Utility Functions
function formatCurrency(amount) {
    return new Intl.NumberFormat('en-XAF', {
        style: 'currency',
        currency: 'XAF',
    }).format(amount);
}

function formatDate(date) {
    return new Intl.DateTimeFormat('en-US', {
        month: 'short',
        day: 'numeric',
        year: 'numeric',
    }).format(date);
}

function formatDateTime(date) {
    return new Intl.DateTimeFormat('en-US', {
        month: 'short',
        day: 'numeric',
        year: 'numeric',
        hour: '2-digit',
        minute: '2-digit',
    }).format(date);
}

function capitalizeFirstLetter(string) {
    return string.charAt(0).toUpperCase() + string.slice(1);
}

// Page Initialization
document.addEventListener('DOMContentLoaded', function() {
    // Set welcome message
    document.querySelector('.welcome-text').textContent = `Welcome, ${users.find(user => user.role === 'admin').name}`;

    // Calculate statistics
    const totalUsers = users.length;
    const activeUsers = users.filter(user => user.status === 'active').length;
    const inactiveUsers = users.filter(user => user.status !== 'active').length;

    const totalAccounts = accounts.length;
    const activeAccounts = accounts.filter(account => account.status === 'active').length;
    const suspendedAccounts = accounts.filter(account => account.status === 'suspended').length;

    const totalTransactions = transactions.length;
    const pendingTransactions = transactions.filter(transaction => transaction.status === 'pending').length;
    const failedTransactions = transactions.filter(transaction => transaction.status === 'failed').length;

    // Update statistics on page
    document.getElementById('total-users').textContent = totalUsers;
    document.getElementById('active-users').textContent = activeUsers;
    document.getElementById('inactive-users').textContent = inactiveUsers;

    document.getElementById('total-accounts').textContent = totalAccounts;
    document.getElementById('active-accounts').textContent = activeAccounts;
    document.getElementById('suspended-accounts').textContent = suspendedAccounts;

    document.getElementById('total-transactions').textContent = totalTransactions;
    document.getElementById('pending-transactions').textContent = pendingTransactions;
    document.getElementById('failed-transactions').textContent = failedTransactions;

    // Load recent users
    loadRecentUsers();

    // Load recent transactions
    loadRecentTransactions();

    // Setup logout button
    document.getElementById('logout-button').addEventListener('click', function() {
        console.log('Logout clicked');
        alert('Logging out...');
        window.location.href = "login.html";
    });
});

function loadRecentUsers() {
    const recentUsersContainer = document.getElementById('recent-users');

    // Sort users by created date (newest first) and take the first 3
    const recentUsers = [...users]
        .sort((a, b) => b.createdAt - a.createdAt)
        .slice(0, 3);

    recentUsers.forEach(user => {
        const userItem = document.createElement('div');
        userItem.className = 'recent-item';

        userItem.innerHTML = `
        <div class="recent-user-info">
          <div class="user-avatar-small">👤</div>
          <div class="user-details">
            <span class="user-name">${user.name}</span>
            <span class="user-email">${user.email}</span>
          </div>
        </div>
        <div>
          <span class="user-status status-${user.status}">
            ${capitalizeFirstLetter(user.status)}
          </span>
        </div>
      `;

        recentUsersContainer.appendChild(userItem);
    });
}

function loadRecentTransactions() {
    const recentTransactionsContainer = document.getElementById('recent-transactions');

    // Sort transactions by created date (newest first) and take the first 3
    const recentTransactions = [...transactions]
        .sort((a, b) => b.createdAt - a.createdAt)
        .slice(0, 3);

    recentTransactions.forEach(transaction => {
        const transactionItem = document.createElement('div');
        transactionItem.className = 'recent-item';

        transactionItem.innerHTML = `
        <div>
          <span class="transaction-type">${capitalizeFirstLetter(transaction.type)}</span>
          <div class="transaction-date">${formatDateTime(transaction.createdAt)}</div>
        </div>
        <div style="text-align: right">
          <div class="transaction-amount">${formatCurrency(transaction.amount)}</div>
          <span class="transaction-status status-${transaction.status}">
            ${capitalizeFirstLetter(transaction.status)}
          </span>
        </div>
      `;

        recentTransactionsContainer.appendChild(transactionItem);
    });
}
