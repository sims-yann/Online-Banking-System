// Mock Data
const transactions = [
    {
        id: "tr1",
        fromAccountId: "acc1",
        toAccountId: "external1",
        amount: 120.50,
        currency: "USD",
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
        currency: "USD",
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
        currency: "USD",
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
        currency: "USD",
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
        currency: "USD",
        type: "transfer",
        status: "failed",
        description: "Transfer to savings",
        createdAt: new Date("2023-04-25")
    },
    {
        id: "tr6",
        fromAccountId: "external4",
        toAccountId: "acc1",
        amount: 350.00,
        currency: "USD",
        type: "deposit",
        status: "pending",
        description: "Check deposit",
        createdAt: new Date("2023-05-15")
    },
    {
        id: "tr7",
        fromAccountId: "acc2",
        toAccountId: "external5",
        amount: 75.25,
        currency: "USD",
        type: "transfer",
        status: "completed",
        description: "Utility payment",
        createdAt: new Date("2023-05-12")
    }
];

// Variables
let selectedTransactionId = null;
let filteredTransactions = [...transactions];
let startDate = null;
let endDate = null;

// Utility Functions
function formatCurrency(amount) {
    return new Intl.NumberFormat('en-US', {
        style: 'currency',
        currency: 'USD',
    }).format(amount);
}

function formatDateTime(date) {
    return new Intl.DateTimeFormat('en-US', {
        year: 'numeric',
        month: 'short',
        day: 'numeric',
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
    document.querySelector('.welcome-text').textContent = `Welcome, Admin`;

    // Render transactions table
    renderTransactionsTable();

    // Setup search functionality
    const searchInput = document.getElementById('search-transactions');
    searchInput.addEventListener('input', function() {
        filterTransactions();
    });

    // Setup date filters
    document.getElementById('start-date').addEventListener('change', function() {
        startDate = this.value ? new Date(this.value) : null;
        filterTransactions();
    });

    document.getElementById('end-date').addEventListener('change', function() {
        endDate = this.value ? new Date(this.value) : null;
        endDate?.setHours(23, 59, 59, 999); // Set to end of day
        filterTransactions();
    });

    // Setup clear dates button
    document.getElementById('clear-dates').addEventListener('click', function() {
        document.getElementById('start-date').value = '';
        document.getElementById('end-date').value = '';
        startDate = null;
        endDate = null;
        filterTransactions();
    });

    // Setup logout button
    document.getElementById('logout-button').addEventListener('click', function() {
        console.log('Logout clicked');
        alert('Logging out...');
        window.location.href = "/login";
    });

    // Setup transaction actions dropdown functionality
    setupTransactionActionsDropdown();

    // Close dropdown when clicking elsewhere
    window.addEventListener('click', function(event) {
        const dropdown = document.getElementById('transaction-actions-dropdown');
        if (!event.target.matches('.actions-button') && !dropdown.contains(event.target)) {
            closeActionsDropdown();
        }
    });
});

function filterTransactions() {
    const searchTerm = document.getElementById('search-transactions').value.toLowerCase();

    filteredTransactions = transactions.filter(transaction => {
        // Match search term
        const matchesSearch =
            searchTerm === '' ||
            transaction.description.toLowerCase().includes(searchTerm) ||
            transaction.id.toLowerCase().includes(searchTerm);

        // Match date range
        let matchesDateRange = true;
        if (startDate) {
            matchesDateRange = transaction.createdAt >= startDate;
        }

        if (matchesDateRange && endDate) {
            matchesDateRange = transaction.createdAt <= endDate;
        }

        return matchesSearch && matchesDateRange;
    });

    renderTransactionsTable();
}

function renderTransactionsTable() {
    const tableBody = document.querySelector('#transactions-table tbody');
    tableBody.innerHTML = '';

    filteredTransactions.forEach(transaction => {
        const tr = document.createElement('tr');

        // Transaction ID cell
        const tdId = document.createElement('td');
        tdId.innerHTML = `<span class="transaction-id">#${transaction.id}</span>`;

        // Transaction type cell with badge
        const tdType = document.createElement('td');
        const typeBadge = document.createElement('span');
        typeBadge.className = `badge ${
            transaction.type === 'deposit' ? 'badge-primary' :
                transaction.type === 'withdraw' ? 'badge-destructive' : 'badge-outline'
        }`;
        typeBadge.textContent = capitalizeFirstLetter(transaction.type);
        tdType.appendChild(typeBadge);

        // Amount cell
        const tdAmount = document.createElement('td');
        tdAmount.textContent = formatCurrency(transaction.amount);

        // Description cell
        const tdDescription = document.createElement('td');
        tdDescription.textContent = transaction.description;

        // Date & time cell
        const tdDateTime = document.createElement('td');
        tdDateTime.textContent = formatDateTime(transaction.createdAt);

        // Status cell with badge
        const tdStatus = document.createElement('td');
        const statusBadge = document.createElement('span');
        statusBadge.className = `badge ${
            transaction.status === 'completed' ? 'badge-primary' :
                transaction.status === 'pending' ? 'badge-secondary' : 'badge-destructive'
        }`;
        statusBadge.textContent = capitalizeFirstLetter(transaction.status);
        tdStatus.appendChild(statusBadge);

        // Actions cell with dropdown button
        const tdActions = document.createElement('td');
        const actionsButton = document.createElement('button');
        actionsButton.className = 'actions-button';
        actionsButton.setAttribute('data-transaction-id', transaction.id);
        actionsButton.innerHTML = '⋮';
        tdActions.appendChild(actionsButton);

        // Append all cells to the row
        tr.appendChild(tdId);
        tr.appendChild(tdType);
        tr.appendChild(tdAmount);
        tr.appendChild(tdDescription);
        tr.appendChild(tdDateTime);
        tr.appendChild(tdStatus);
        tr.appendChild(tdActions);

        // Append the row to the table body
        tableBody.appendChild(tr);
    });

    // Re-attach event listeners to action buttons
    document.querySelectorAll('.actions-button').forEach(button => {
        button.addEventListener('click', function(e) {
            e.stopPropagation();
            selectedTransactionId = this.getAttribute('data-transaction-id');
            showActionsDropdown(this);
        });
    });
}

function setupTransactionActionsDropdown() {
    // View transaction details
    document.getElementById('view-transaction-btn').addEventListener('click', function() {
        alert(`View details for transaction ID: ${selectedTransactionId}`);
        closeActionsDropdown();
    });

    // Approve transaction
    document.querySelector('.approve-transaction-btn').addEventListener('click', function() {
        const transaction = transactions.find(t => t.id === selectedTransactionId);

        // In a real app, this would be an API call
        // For demo, we'll just update the local data
        const transactionIndex = transactions.findIndex(t => t.id === selectedTransactionId);
        transactions[transactionIndex].status = 'completed';

        alert(`Transaction ${selectedTransactionId} has been approved`);
        filterTransactions(); // Re-apply filters and render
        closeActionsDropdown();
    });

    // Reject transaction
    document.querySelector('.reject-transaction-btn').addEventListener('click', function() {
        const transaction = transactions.find(t => t.id === selectedTransactionId);

        // In a real app, this would be an API call
        // For demo, we'll just update the local data
        const transactionIndex = transactions.findIndex(t => t.id === selectedTransactionId);
        transactions[transactionIndex].status = 'failed';

        alert(`Transaction ${selectedTransactionId} has been rejected`);
        filterTransactions(); // Re-apply filters and render
        closeActionsDropdown();
    });
}

function showActionsDropdown(buttonElement) {
    const dropdown = document.getElementById('transaction-actions-dropdown');
    const transaction = transactions.find(t => t.id === selectedTransactionId);

    // Show/hide pending transaction actions based on status
    const pendingActions = document.getElementById('pending-transaction-actions');
    pendingActions.style.display = transaction.status === 'pending' ? 'block' : 'none';

    // Position the dropdown next to the button
    const buttonRect = buttonElement.getBoundingClientRect();
    dropdown.style.top = `${buttonRect.bottom}px`;
    dropdown.style.left = `${buttonRect.left - 160 + buttonRect.width}px`; // Align right edge

    // Show the dropdown
    dropdown.classList.add('active');
}

function closeActionsDropdown() {
    const dropdown = document.getElementById('transaction-actions-dropdown');
    dropdown.classList.remove('active');
    selectedTransactionId = null;
}
