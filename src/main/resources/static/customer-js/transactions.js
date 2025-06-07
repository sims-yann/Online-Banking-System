// Mock Account Data
const accounts = [
    {
        id: "acc1",
        userId: "user123",
        accountNumber: "1234567890",
        type: "checking",
        balance: 2250.00,
        currency: "USD",
        interestRate: "0.01%",
        openedDate: new Date("2023-01-15"),
        status: "active"
    },
    {
        id: "acc2",
        userId: "user123",
        accountNumber: "9876543210",
        type: "savings",
        balance: 3000.00,
        currency: "USD",
        interestRate: "0.5%",
        openedDate: new Date("2023-01-15"),
        status: "active"
    }
];

// Extended Mock Transaction Data
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
        createdAt: new Date("2023-05-10T14:32:15"),
        merchant: "Amazon",
        category: "Shopping",
        reference: "TRX1234567"
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
        createdAt: new Date("2023-05-05T09:15:22"),
        merchant: "ABC Company",
        category: "Income",
        reference: "SAL05052023"
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
        createdAt: new Date("2023-05-02T00:00:00"),
        merchant: "Netflix",
        category: "Entertainment",
        reference: "SUB789456"
    },
    {
        id: "tr4",
        fromAccountId: "acc1",
        toAccountId: "",
        amount: 200.00,
        currency: "USD",
        type: "withdraw",
        status: "completed",
        description: "ATM withdrawal",
        createdAt: new Date("2023-04-28T16:45:33"),
        merchant: "ATM",
        category: "Cash",
        reference: "ATM456789"
    },
    {
        id: "tr5",
        fromAccountId: "acc1",
        toAccountId: "acc2",
        amount: 500.00,
        currency: "USD",
        type: "transfer",
        status: "completed",
        description: "Transfer to savings",
        createdAt: new Date("2023-04-25T11:20:45"),
        category: "Savings",
        reference: "INT987654"
    },
    {
        id: "tr6",
        fromAccountId: "external4",
        toAccountId: "acc1",
        amount: 75.00,
        currency: "USD",
        type: "deposit",
        status: "completed",
        description: "Reimbursement from friend",
        createdAt: new Date("2023-04-20T18:12:30"),
        merchant: "Jane Smith",
        category: "Income",
        reference: "P2P123456"
    },
    {
        id: "tr7",
        fromAccountId: "acc1",
        toAccountId: "external5",
        amount: 60.25,
        currency: "USD",
        type: "transfer",
        status: "completed",
        description: "Utility bill payment",
        createdAt: new Date("2023-04-15T09:30:00"),
        merchant: "City Power & Water",
        category: "Bills",
        reference: "BILL563412"
    },
    {
        id: "tr8",
        fromAccountId: "acc1",
        toAccountId: "external6",
        amount: 89.99,
        currency: "USD",
        type: "transfer",
        status: "completed",
        description: "Mobile phone bill",
        createdAt: new Date("2023-04-10T14:25:18"),
        merchant: "Mobile Carrier",
        category: "Bills",
        reference: "MOB789123"
    },
    {
        id: "tr9",
        fromAccountId: "external7",
        toAccountId: "acc2",
        amount: 1000.00,
        currency: "USD",
        type: "deposit",
        status: "completed",
        description: "Bonus payment",
        createdAt: new Date("2023-04-05T10:15:45"),
        merchant: "ABC Company",
        category: "Income",
        reference: "BON246813"
    },
    {
        id: "tr10",
        fromAccountId: "acc1",
        toAccountId: "external8",
        amount: 250.75,
        currency: "USD",
        type: "transfer",
        status: "completed",
        description: "Car insurance payment",
        createdAt: new Date("2023-03-25T16:40:22"),
        merchant: "Insure Co",
        category: "Insurance",
        reference: "INS579246"
    }
];

// Utility Functions
function formatCurrency(amount) {
    return new Intl.NumberFormat('en-US', {
        style: 'currency',
        currency: 'USD',
    }).format(amount);
}

function formatDate(date) {
    return new Intl.DateTimeFormat('en-US', {
        month: 'short',
        day: 'numeric',
        year: 'numeric',
    }).format(date);
}

function formatTime(date) {
    return new Intl.DateTimeFormat('en-US', {
        hour: 'numeric',
        minute: 'numeric',
        hour12: true
    }).format(date);
}

function formatDateTime(date) {
    return `${formatDate(date)} at ${formatTime(date)}`;
}

function getAccountById(id) {
    return accounts.find(account => account.id === id);
}

function getAccountDisplay(accountId) {
    const account = getAccountById(accountId);
    if (!account) return "External Account";
    return `${account.type.charAt(0).toUpperCase() + account.type.slice(1)} (${account.accountNumber.slice(-4)})`;
}

function isIncoming(transaction, userAccountIds) {
    return userAccountIds.includes(transaction.toAccountId) &&
        (!transaction.fromAccountId || !userAccountIds.includes(transaction.fromAccountId));
}

function capitalizeFirstLetter(string) {
    if (!string) return '';
    return string.charAt(0).toUpperCase() + string.slice(1);
}

// State variables
let currentPage = 1;
const pageSize = 5;
let filteredTransactions = [...transactions];
const userAccountIds = accounts.map(account => account.id);

// Initialize Transactions Page
document.addEventListener('DOMContentLoaded', function() {
    // Set welcome message with user name
    document.querySelector('.welcome-text').textContent = `Welcome, John Doe`;

    // Initialize filters
    initializeFilters();

    // Initial render of transactions
    renderTransactions(filteredTransactions);

    // Setup pagination
    setupPagination();

    // Setup filter actions
    document.getElementById('search-button').addEventListener('click', applyFilters);
    document.getElementById('reset-filters').addEventListener('click', resetFilters);

    // Setup export button
    document.getElementById('export-button').addEventListener('click', function() {
        alert('Exporting transactions to CSV file...');
    });

    // Setup logout button
    document.getElementById('logout-button').addEventListener('click', function() {
        console.log('Logout clicked');
        alert('Logging out...');
    });

    // Setup modal close button
    document.querySelectorAll('.close-modal').forEach(button => {
        button.addEventListener('click', function() {
            document.getElementById('transaction-details-modal').classList.remove('active');
        });
    });

    // Close modal when clicking outside
    window.addEventListener('click', function(event) {
        if (event.target.classList.contains('modal')) {
            event.target.classList.remove('active');
        }
    });
});

function initializeFilters() {
    // Populate account select
    const accountSelect = document.getElementById('filter-account');
    accounts.forEach(account => {
        const option = document.createElement('option');
        option.value = account.id;
        option.textContent = `${capitalizeFirstLetter(account.type)} (${account.accountNumber.slice(-4)})`;
        accountSelect.appendChild(option);
    });
}

function applyFilters() {
    const accountFilter = document.getElementById('filter-account').value;
    const typeFilter = document.getElementById('filter-type').value;
    const dateFilter = document.getElementById('filter-date').value;

    let filtered = [...transactions];

    // Apply account filter
    if (accountFilter !== 'all') {
        filtered = filtered.filter(transaction =>
            transaction.fromAccountId === accountFilter || transaction.toAccountId === accountFilter
        );
    }

    // Apply type filter
    if (typeFilter !== 'all') {
        filtered = filtered.filter(transaction => transaction.type === typeFilter);
    }

    // Apply date filter
    if (dateFilter !== 'all') {
        const now = new Date();
        let startDate;

        switch (dateFilter) {
            case 'today':
                startDate = new Date(now);
                startDate.setHours(0, 0, 0, 0);
                break;
            case 'week':
                startDate = new Date(now);
                startDate.setDate(now.getDate() - 7);
                break;
            case 'month':
                startDate = new Date(now);
                startDate.setDate(now.getDate() - 30);
                break;
            case 'year':
                startDate = new Date(now);
                startDate.setFullYear(now.getFullYear() - 1);
                break;
        }

        filtered = filtered.filter(transaction => transaction.createdAt >= startDate);
    }

    // Update filtered transactions and render
    filteredTransactions = filtered;
    currentPage = 1;
    renderTransactions(filteredTransactions);
    setupPagination();
}

function resetFilters() {
    document.getElementById('filter-account').value = 'all';
    document.getElementById('filter-type').value = 'all';
    document.getElementById('filter-date').value = 'all';

    filteredTransactions = [...transactions];
    currentPage = 1;
    renderTransactions(filteredTransactions);
    setupPagination();
}

function setupPagination() {
    const prevButton = document.getElementById('prev-page');
    const nextButton = document.getElementById('next-page');
    const pageInfo = document.getElementById('page-info');

    const totalPages = Math.ceil(filteredTransactions.length / pageSize);

    // Update page info
    pageInfo.textContent = `Page ${currentPage} of ${totalPages || 1}`;

    // Update button states
    prevButton.disabled = currentPage === 1;
    nextButton.disabled = currentPage === totalPages || totalPages === 0;

    // Add event listeners
    prevButton.onclick = () => {
        if (currentPage > 1) {
            currentPage--;
            renderTransactions(filteredTransactions);
            setupPagination();
        }
    };

    nextButton.onclick = () => {
        if (currentPage < totalPages) {
            currentPage++;
            renderTransactions(filteredTransactions);
            setupPagination();
        }
    };
}

function renderTransactions(transactionsToRender) {
    const tableBody = document.getElementById('transactions-table-body');
    const noTransactions = document.getElementById('no-transactions');

    tableBody.innerHTML = '';

    if (transactionsToRender.length === 0) {
        tableBody.style.display = 'none';
        noTransactions.style.display = 'block';
        return;
    }

    tableBody.style.display = '';
    noTransactions.style.display = 'none';

    // Paginate transactions
    const startIndex = (currentPage - 1) * pageSize;
    const paginatedTransactions = transactionsToRender.slice(startIndex, startIndex + pageSize);

    paginatedTransactions.forEach(transaction => {
        const row = document.createElement('tr');
        const incoming = isIncoming(transaction, userAccountIds);

        // Date & Time
        const dateCell = document.createElement('td');
        const dateDiv = document.createElement('div');
        dateDiv.textContent = formatDate(transaction.createdAt);

        const timeDiv = document.createElement('div');
        timeDiv.className = 'transaction-time';
        timeDiv.textContent = formatTime(transaction.createdAt);

        dateCell.appendChild(dateDiv);
        dateCell.appendChild(timeDiv);
        row.appendChild(dateCell);

        // Description
        const descCell = document.createElement('td');
        const descDiv = document.createElement('div');
        descDiv.className = 'transaction-description';
        descDiv.textContent = transaction.description;

        if (transaction.merchant) {
            const subDiv = document.createElement('div');
            subDiv.className = 'transaction-subtitle';
            subDiv.textContent = transaction.merchant;
            descDiv.appendChild(subDiv);
        }

        descCell.appendChild(descDiv);
        row.appendChild(descCell);

        // Account
        const accountCell = document.createElement('td');
        const accountToShow = incoming ? transaction.toAccountId : transaction.fromAccountId;
        accountCell.textContent = getAccountDisplay(accountToShow);
        row.appendChild(accountCell);

        // Type
        const typeCell = document.createElement('td');
        const typeBadge = document.createElement('span');
        typeBadge.className = 'badge';

        if (transaction.type === 'deposit') {
            typeBadge.className += ' badge-secondary';
        } else if (transaction.type === 'withdraw') {
            typeBadge.className += ' badge-destructive';
        } else {
            typeBadge.className += ' badge-default';
        }

        typeBadge.textContent = capitalizeFirstLetter(transaction.type);
        typeCell.appendChild(typeBadge);
        row.appendChild(typeCell);

        // Amount
        const amountCell = document.createElement('td');
        amountCell.className = incoming || transaction.type === 'deposit'
            ? 'amount-positive'
            : 'amount-negative';
        amountCell.textContent = (incoming || transaction.type === 'deposit' ? '+' : '-') +
            formatCurrency(transaction.amount);
        row.appendChild(amountCell);

        // Status
        const statusCell = document.createElement('td');
        const statusBadge = document.createElement('span');
        statusBadge.className = 'badge badge-default';
        statusBadge.textContent = capitalizeFirstLetter(transaction.status);
        statusCell.appendChild(statusBadge);
        row.appendChild(statusCell);

        // Actions
        const actionsCell = document.createElement('td');
        const actionButtons = document.createElement('div');
        actionButtons.className = 'action-buttons';

        const viewButton = document.createElement('button');
        viewButton.className = 'action-button';
        viewButton.textContent = 'View Details';
        viewButton.addEventListener('click', () => showTransactionDetails(transaction));

        actionButtons.appendChild(viewButton);
        actionsCell.appendChild(actionButtons);
        row.appendChild(actionsCell);

        tableBody.appendChild(row);
    });
}

function showTransactionDetails(transaction) {
    const modalContent = document.getElementById('transaction-details-content');
    const incoming = isIncoming(transaction, userAccountIds);

    let iconEmoji = '↔️';
    if (transaction.type === 'deposit') {
        iconEmoji = '⬇️';
    } else if (transaction.type === 'withdraw') {
        iconEmoji = '⬆️';
    }

    const fromAccount = getAccountDisplay(transaction.fromAccountId);
    const toAccount = getAccountDisplay(transaction.toAccountId);

    modalContent.innerHTML = `
      <div class="transaction-detail-header">
        <div class="transaction-type-icon">${iconEmoji}</div>
        <div class="transaction-amount ${incoming || transaction.type === 'deposit' ? 'amount-positive' : 'amount-negative'}">
          ${(incoming || transaction.type === 'deposit' ? '+' : '-')}${formatCurrency(transaction.amount)}
        </div>
      </div>

      <div class="transaction-details-grid">
        <div class="transaction-detail-item">
          <div class="transaction-detail-label">Transaction Type</div>
          <div class="transaction-detail-value">${capitalizeFirstLetter(transaction.type)}</div>
        </div>
        <div class="transaction-detail-item">
          <div class="transaction-detail-label">Status</div>
          <div class="transaction-detail-value">${capitalizeFirstLetter(transaction.status)}</div>
        </div>
        <div class="transaction-detail-item">
          <div class="transaction-detail-label">Date & Time</div>
          <div class="transaction-detail-value">${formatDateTime(transaction.createdAt)}</div>
        </div>
        <div class="transaction-detail-item">
          <div class="transaction-detail-label">Reference</div>
          <div class="transaction-detail-value">${transaction.reference || 'N/A'}</div>
        </div>
        <div class="transaction-detail-item">
          <div class="transaction-detail-label">From</div>
          <div class="transaction-detail-value">${fromAccount}</div>
        </div>
        <div class="transaction-detail-item">
          <div class="transaction-detail-label">To</div>
          <div class="transaction-detail-value">${toAccount || 'N/A'}</div>
        </div>
        ${transaction.merchant ? `
          <div class="transaction-detail-item">
            <div class="transaction-detail-label">Merchant</div>
            <div class="transaction-detail-value">${transaction.merchant}</div>
          </div>
        ` : ''}
        ${transaction.category ? `
          <div class="transaction-detail-item">
            <div class="transaction-detail-label">Category</div>
            <div class="transaction-detail-value">${transaction.category}</div>
          </div>
        ` : ''}
      </div>

      <div class="transaction-details-notes">
        <h4>Description</h4>
        <p>${transaction.description || 'No description available'}</p>
      </div>
    `;

    // Show modal
    document.getElementById('transaction-details-modal').classList.add('active');
}
