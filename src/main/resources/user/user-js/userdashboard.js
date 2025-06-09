// Mock User Data
const currentUser = {
    id: "user123",
    name: "John Doe",
    email: "john@example.com"
};

// Mock Account Data
const accounts = [
    {
        id: "acc1",
        userId: "user123",
        accountNumber: "1234567890",
        type: "checking",
        balance: 2250.00,
        currency: "XAF",
        createdAt: new Date("2023-01-15"),
        status: "active"
    },
    {
        id: "acc2",
        userId: "user123",
        accountNumber: "9876543210",
        type: "savings",
        balance: 3000.00,
        currency: "XAF",
        createdAt: new Date("2023-01-15"),
        status: "active"
    }
];

// Mock Transaction Data
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
        status: "completed",
        description: "ATM withdrawal",
        createdAt: new Date("2023-04-28")
    },
    {
        id: "tr5",
        fromAccountId: "acc1",
        toAccountId: "acc2",
        amount: 500.00,
        currency: "XAF",
        type: "transfer",
        status: "completed",
        description: "Transfer to savings",
        createdAt: new Date("2023-04-25")
    }
];

// Utility Functions
function formatCurrency(amount) {
    return new Intl.NumberFormat('en-CM', {
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

function getAccountById(id) {
    return accounts.find(account => account.id === id);
}

function formatAccountNumber(accountId) {
    const account = getAccountById(accountId);
    if (!account) return "External Account";
    return `${account.type.charAt(0).toUpperCase() + account.type.slice(1)} (${account.accountNumber.slice(-4)})`;
}

function isIncoming(transaction, userAccountIds) {
    return userAccountIds.includes(transaction.toAccountId) &&
        (!transaction.fromAccountId || !userAccountIds.includes(transaction.fromAccountId));
}

// Initialize Dashboard
document.addEventListener('DOMContentLoaded', function() {
    // Set welcome message with user name
    document.querySelector('.welcome-text').textContent = `Welcome, ${currentUser.name}`;

    // Initialize the spending chart
    initializeSpendingChart();

    // Render transactions table
    renderTransactions();

    // Setup logout button
    document.getElementById('logout-button').addEventListener('click', function() {
        console.log('Logout clicked');
        // In a real app, this would call a logout function and redirect
        alert('Logging out...');
        // window.location.href = '/login';
    });
});

// Initialize Spending Chart
function initializeSpendingChart() {
    const ctx = document.getElementById('spendingChart').getContext('2d');

    // Process transactions to get monthly data
    const monthlyData = getMonthlyData();

    // Create chart using Chart.js
    const chart = new Chart(ctx, {
        type: 'bar',
        data: {
            labels: monthlyData.map(d => d.month),
            datasets: [
                {
                    label: 'Income',
                    data: monthlyData.map(d => d.income),
                    backgroundColor: '#4CAF50',
                },
                {
                    label: 'Expenses',
                    data: monthlyData.map(d => d.expenses),
                    backgroundColor: '#F44336',
                }
            ]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            scales: {
                y: {
                    beginAtZero: true,
                    ticks: {
                        callback: function(value) {
                            return 'XAF' + value;
                        }
                    }
                }
            },
            plugins: {
                tooltip: {
                    callbacks: {
                        label: function(context) {
                            return context.dataset.label + ': XAF' + context.raw;
                        }
                    }
                }
            }
        }
    });
}

// Get monthly data for the chart
function getMonthlyData() {
    const last6Months = [];
    for (let i = 5; i >= 0; i--) {
        const date = new Date();
        date.setMonth(date.getMonth() - i);
        last6Months.push({
            month: date.toLocaleString('default', { month: 'short' }),
            fullDate: date,
        });
    }

    const userAccountIds = accounts.map(account => account.id);

    return last6Months.map(monthData => {
        const monthStart = new Date(monthData.fullDate);
        monthStart.setDate(1);
        monthStart.setHours(0, 0, 0, 0);

        const monthEnd = new Date(monthData.fullDate);
        monthEnd.setMonth(monthEnd.getMonth() + 1);
        monthEnd.setDate(0);
        monthEnd.setHours(23, 59, 59, 999);

        // Filter transactions for this month
        const monthTransactions = transactions.filter(t =>
            t.createdAt >= monthStart && t.createdAt <= monthEnd
        );

        // Calculate income (incoming transactions)
        const income = monthTransactions
            .filter(t => userAccountIds.includes(t.toAccountId) &&
                (!t.fromAccountId || !userAccountIds.includes(t.fromAccountId)))
            .reduce((sum, t) => sum + t.amount, 0);

        // Calculate expenses (outgoing transactions)
        const expenses = monthTransactions
            .filter(t => userAccountIds.includes(t.fromAccountId) &&
                (!t.toAccountId || !userAccountIds.includes(t.toAccountId)))
            .reduce((sum, t) => sum + t.amount, 0);

        return {
            month: monthData.month,
            income,
            expenses
        };
    });
}

// Render transactions table
function renderTransactions() {
    const userAccountIds = accounts.map(account => account.id);

    // Sort transactions by date (newest first)
    const sortedTransactions = [...transactions]
        .sort((a, b) => b.createdAt.getTime() - a.createdAt.getTime());

    const tableBody = document.getElementById('transactionsTableBody');

    // Clear existing rows
    tableBody.innerHTML = '';

    // Add transaction rows
    sortedTransactions.forEach(transaction => {
        const incoming = isIncoming(transaction, userAccountIds);
        const row = document.createElement('tr');

        // Date cell
        const dateCell = document.createElement('td');
        dateCell.textContent = formatDate(transaction.createdAt);
        dateCell.style.fontWeight = '500';
        row.appendChild(dateCell);

        // Description cell
        const descCell = document.createElement('td');
        const descText = document.createElement('div');
        descText.textContent = transaction.description;

        const descSubtext = document.createElement('div');
        descSubtext.style.fontSize = '14px';
        descSubtext.style.color = '#666';

        if (transaction.type === 'transfer') {
            descSubtext.textContent = `${incoming ? 'From: ' : 'To: '} ${incoming
                ? formatAccountNumber(transaction.fromAccountId)
                : formatAccountNumber(transaction.toAccountId)}`;
        } else {
            descSubtext.textContent = transaction.type === 'deposit' ? 'Deposit' : 'Withdrawal';
        }

        descCell.appendChild(descText);
        descCell.appendChild(descSubtext);
        row.appendChild(descCell);

        // Type cell
        const typeCell = document.createElement('td');
        const typeBadge = document.createElement('span');
        typeBadge.className = 'badge';

        if (transaction.type === 'transfer') {
            typeBadge.className += ' badge-outline';
        } else if (transaction.type === 'deposit') {
            typeBadge.className += ' badge-secondary';
        } else {
            typeBadge.className += ' badge-destructive';
        }

        const typeIcon = document.createElement('span');
        typeIcon.className = 'badge-icon';
        if (transaction.type === 'transfer') {
            typeIcon.textContent = '↔️';
        } else if (transaction.type === 'deposit') {
            typeIcon.textContent = '⬇️';
        } else {
            typeIcon.textContent = '⬆️';
        }

        const typeText = document.createTextNode(
            transaction.type.charAt(0).toUpperCase() + transaction.type.slice(1)
        );

        typeBadge.appendChild(typeIcon);
        typeBadge.appendChild(typeText);
        typeCell.appendChild(typeBadge);
        row.appendChild(typeCell);

        // Amount cell
        const amountCell = document.createElement('td');
        amountCell.className = incoming || transaction.type === 'deposit'
            ? 'amount-positive'
            : 'amount-negative';
        amountCell.textContent = (incoming ? '+' : '-') + formatCurrency(transaction.amount);
        row.appendChild(amountCell);

        // Status cell
        const statusCell = document.createElement('td');
        const statusBadge = document.createElement('span');
        statusBadge.className = 'badge';

        if (transaction.status === 'completed') {
            statusBadge.className += ' badge-default';
        } else if (transaction.status === 'pending') {
            statusBadge.className += ' badge-secondary';
        } else {
            statusBadge.className += ' badge-destructive';
        }

        statusBadge.textContent = transaction.status;
        statusCell.appendChild(statusBadge);
        row.appendChild(statusCell);

        // Add row to table
        tableBody.appendChild(row);
    });
}
