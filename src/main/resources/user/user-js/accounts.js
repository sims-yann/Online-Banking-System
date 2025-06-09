// Mock Account Data (used as fallback or initial state)
const accounts = [
    {
        id: "acc1",
        userId: "user123",
        accountNumber: "1234567890",
        type: "checking",
        balance: 2250.00,
        currency: "XAF",
        interestRate: "0.01%",
        openedDate: new Date("2023-01-15"),
        status: "active",
        branch: "Main Branch",
        lastActivity: new Date("2023-05-10")
    },
    {
        id: "acc2",
        userId: "user123",
        accountNumber: "9876543210",
        type: "savings",
        balance: 3000.00,
        currency: "XAF",
        interestRate: "0.5%",
        openedDate: new Date("2023-01-15"),
        status: "active",
        branch: "Main Branch",
        lastActivity: new Date("2023-04-25")
    }
];

// Mock Transaction Data
const transactions = [
    {
        id: "txn1",
        fromAccountId: "acc1",
        toAccountId: "acc2",
        amount: 500.00,
        description: "Transfer to Savings",
        type: "transfer",
        status: "completed",
        createdAt: new Date("2023-05-01")
    },
    {
        id: "txn2",
        fromAccountId: "external",
        toAccountId: "acc1",
        amount: 1200.00,
        description: "Salary Deposit",
        type: "deposit",
        status: "completed",
        createdAt: new Date("2023-05-05")
    },
    {
        id: "txn3",
        fromAccountId: "acc1",
        toAccountId: "external",
        amount: 150.00,
        description: "Grocery Shopping",
        type: "withdrawal",
        status: "completed",
        createdAt: new Date("2023-05-08")
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
    // Ensure date is a Date object
    const dateObj = date instanceof Date ? date : new Date(date);
    return new Intl.DateTimeFormat('en-CM', {
        month: 'short',
        day: 'numeric',
        year: 'numeric',
    }).format(dateObj);
}

function getAccountById(id) {
    return accounts.find(account => account.id === id);
}

function getAccountTransactions(accountId) {
    return transactions.filter(transaction =>
        transaction.fromAccountId === accountId || transaction.toAccountId === accountId
    );
}

function isIncoming(transaction, accountId) {
    return transaction.toAccountId === accountId;
}

function capitalizeFirstLetter(string) {
    return string.charAt(0).toUpperCase() + string.slice(1);
}

function generateAccountNumber() {
    let accountNumber;
    do {
        accountNumber = Math.floor(1000000000 + Math.random() * 9000000000).toString();
    } while (accounts.some(account => account.accountNumber === accountNumber));
    return accountNumber;
}

function generateAccountId() {
    return 'acc' + Date.now(); // More unique than length-based
}

function showToast(message) {
    const toast = document.getElementById('toast-notification');
    const toastMessage = document.getElementById('toast-message');

    if (!toast || !toastMessage) {
        console.error('Toast elements not found');
        return;
    }

    toastMessage.textContent = message;
    toast.classList.add('active');
    setTimeout(() => {
        toast.classList.remove('active');
    }, 3000);
}

function sanitizeInput(input) {
    if (typeof input !== 'string') return input;
    return input.replace(/[<>'"]/g, '');
}

// Initialize Accounts Page
document.addEventListener('DOMContentLoaded', async function() {
    // Set welcome message with user name
    const welcomeElement = document.querySelector('.welcome-text');
    if (welcomeElement) {
        welcomeElement.textContent = `Welcome, John Doe`;
    }

    // Try to fetch accounts from backend, fall back to mock data
    await loadAccounts();

    // Render accounts
    renderAccounts();

    // Setup event listeners
    setupEventListeners();
});

async function loadAccounts() {
    try {
        const response = await fetch('/api/accounts', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        });

        if (response.ok) {
            const fetchedAccounts = await response.json();
            accounts.length = 0; // Clear mock data
            // Ensure dates are properly converted
            fetchedAccounts.forEach(account => {
                account.openedDate = new Date(account.openedDate);
                account.lastActivity = new Date(account.lastActivity);
            });
            accounts.push(...fetchedAccounts);
        } else {
            console.log('Using mock data - backend not available');
        }
    } catch (error) {
        console.log('Using mock data - network error:', error.message);
    }
}

function setupEventListeners() {
    // Setup logout button
    const logoutButton = document.getElementById('logout-button');
    if (logoutButton) {
        logoutButton.addEventListener('click', function() {
            if (confirm('Are you sure you want to logout?')) {
                console.log('Logout clicked');
                // Redirect to login page or perform logout action
                window.location.href = '/login.html';
            }
        });
    }

    // Setup new account button
    const newAccountButton = document.getElementById('new-account-button');
    if (newAccountButton) {
        newAccountButton.addEventListener('click', function() {
            openModal('new-account-modal');
            const form = document.getElementById('new-account-form');
            if (form) form.reset();
            hideError();
        });
    }

    // Setup new account form submission
    const newAccountForm = document.getElementById('new-account-form');
    if (newAccountForm) {
        newAccountForm.addEventListener('submit', function(event) {
            event.preventDefault();
            createNewAccount();
        });
    }

    // Setup modal close buttons
    document.querySelectorAll('.close-modal').forEach(button => {
        button.addEventListener('click', function() {
            const modal = button.closest('.modal');
            if (modal) closeModal(modal.id);
        });
    });

    // Close modal when clicking outside
    window.addEventListener('click', function(event) {
        if (event.target.classList.contains('modal')) {
            closeModal(event.target.id);
        }
    });
}

async function createNewAccount() {
    const accountTypeElement = document.getElementById('account-type');
    const initialDepositElement = document.getElementById('initial-deposit');

    if (!accountTypeElement || !initialDepositElement) {
        showError('Form elements not found');
        return;
    }

    const accountType = sanitizeInput(accountTypeElement.value);
    const initialDepositValue = sanitizeInput(initialDepositElement.value);
    const initialDeposit = parseFloat(initialDepositValue);

    // Validation
    if (!accountType) {
        showError('Please select an account type.');
        return;
    }
    if (isNaN(initialDeposit) || initialDeposit < 0) {
        showError('Please enter a valid initial deposit amount (minimum XAF 0).');
        return;
    }

    hideError();

    // Prepare new account data
    const newAccount = {
        id: generateAccountId(),
        userId: "user123",
        accountNumber: generateAccountNumber(),
        type: accountType,
        balance: initialDeposit,
        currency: "XAF",
        interestRate: accountType === "checking" ? "0.01%" : "0.5%",
        openedDate: new Date(),
        status: "active",
        branch: "Main Branch",
        lastActivity: new Date()
    };

    try {
        // Try to send to backend
        const response = await fetch('/api/accounts', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                ...newAccount,
                openedDate: newAccount.openedDate.toISOString(),
                lastActivity: newAccount.lastActivity.toISOString()
            })
        });

        if (response.ok) {
            const createdAccount = await response.json();
            // Ensure frontend-compatible date format
            createdAccount.openedDate = new Date(createdAccount.openedDate);
            createdAccount.lastActivity = new Date(createdAccount.lastActivity);
            accounts.push(createdAccount);
        } else {
            // Backend error, but still add to local array for demo
            accounts.push(newAccount);
            console.log('Account created locally - backend error');
        }
    } catch (error) {
        // Network error, add to local array for demo
        accounts.push(newAccount);
        console.log('Account created locally - network error:', error.message);
    }

    // Update UI
    renderAccounts();
    closeModal('new-account-modal');
    showToast(`${capitalizeFirstLetter(accountType)} account created successfully!`);
}

function showError(message) {
    const errorElement = document.getElementById('form-error');
    if (errorElement) {
        errorElement.textContent = message;
        errorElement.style.display = 'block';
    }
}

function hideError() {
    const errorElement = document.getElementById('form-error');
    if (errorElement) {
        errorElement.style.display = 'none';
    }
}

function renderAccounts() {
    const accountsContainer = document.querySelector('.accounts-grid.detailed');
    if (!accountsContainer) {
        console.error('Accounts container not found');
        return;
    }

    accountsContainer.innerHTML = '';

    // Calculate total balance across all accounts
    const totalBalance = accounts.reduce((sum, account) => sum + account.balance, 0);
    const totalBalanceElement = document.querySelector('.total-balance');
    if (totalBalanceElement) {
        totalBalanceElement.textContent = formatCurrency(totalBalance);
    }

    accounts.forEach(account => {
        const accountCard = document.createElement('div');
        accountCard.className = 'account-card detailed';

        accountCard.innerHTML = `
            <div class="account-header-detailed">
                <div>
                    <h3 class="account-type">${capitalizeFirstLetter(account.type)} Account</h3>
                    <p class="account-number">Account No: ${maskAccountNumber(account.accountNumber)}</p>
                </div>
                <div class="account-balance">${formatCurrency(account.balance)}</div>
            </div>

            <div class="account-info">
                <div class="account-info-item">
                    <div class="account-info-label">Account Status</div>
                    <div class="account-info-value">${capitalizeFirstLetter(account.status)}</div>
                </div>
                <div class="account-info-item">
                    <div class="account-info-label">Interest Rate</div>
                    <div class="account-info-value">${account.interestRate}</div>
                </div>
                <div class="account-info-item">
                    <div class="account-info-label">Opened Date</div>
                    <div class="account-info-value">${formatDate(account.openedDate)}</div>
                </div>
                <div class="account-info-item">
                    <div class="account-info-label">Last Activity</div>
                    <div class="account-info-value">${formatDate(account.lastActivity)}</div>
                </div>
            </div>

            <div class="account-actions-row">
                <button class="action-button view-button view-details" data-account-id="${account.id}">
                    <span>👁️</span>
                    View Details
                </button>
                <button class="action-button transfer-button" data-account-id="${account.id}">
                    <span>💸</span>
                    Transfer
                </button>
                <button class="action-button statement-button" data-account-id="${account.id}">
                    <span>📄</span>
                    Statement
                </button>
            </div>
        `;

        accountsContainer.appendChild(accountCard);
    });

    // Add event listeners to action buttons
    addAccountActionListeners();
}

function addAccountActionListeners() {
    // View details buttons
    document.querySelectorAll('.view-details').forEach(button => {
        button.addEventListener('click', function() {
            const accountId = this.getAttribute('data-account-id');
            openAccountDetailsModal(accountId);
        });
    });

    // Transfer buttons
    document.querySelectorAll('.transfer-button').forEach(button => {
        button.addEventListener('click', function() {
            window.location.href = "transfer.html";
        });
    });

    // Statement buttons
    document.querySelectorAll('.statement-button').forEach(button => {
        button.addEventListener('click', function() {
            const accountId = this.getAttribute('data-account-id');
            const account = getAccountById(accountId);
            if (account) {
                showToast(`Statement for account ${maskAccountNumber(account.accountNumber)} will be generated soon!`);
            }
        });
    });
}

function maskAccountNumber(accountNumber) {
    if (!accountNumber || accountNumber.length < 4) return accountNumber;
    return "****" + accountNumber.slice(-4);
}

function openAccountDetailsModal(accountId) {
    const account = getAccountById(accountId);
    if (!account) {
        showToast('Account not found');
        return;
    }

    const accountTransactions = getAccountTransactions(accountId);
    const modalContent = document.getElementById('account-details-content');

    if (!modalContent) {
        console.error('Modal content element not found');
        return;
    }

    modalContent.innerHTML = `
        <div class="account-details">
            <h3>${capitalizeFirstLetter(account.type)} Account</h3>
            <p>Account No: ${maskAccountNumber(account.accountNumber)}</p>
            <div class="account-balance-large">${formatCurrency(account.balance)}</div>

            <div class="account-info">
                <div class="account-info-item">
                    <div class="account-info-label">Status</div>
                    <div class="account-info-value">${capitalizeFirstLetter(account.status)}</div>
                </div>
                <div class="account-info-item">
                    <div class="account-info-label">Interest Rate</div>
                    <div class="account-info-value">${account.interestRate}</div>
                </div>
                <div class="account-info-item">
                    <div class="account-info-label">Branch</div>
                    <div class="account-info-value">${account.branch}</div>
                </div>
                <div class="account-info-item">
                    <div class="account-info-label">Opened Date</div>
                    <div class="account-info-value">${formatDate(account.openedDate)}</div>
                </div>
            </div>

            <div class="account-transactions">
                <h3>Recent Transactions</h3>
                ${accountTransactions.length > 0 ? `
                    <div class="transactions-table-container">
                        <table class="transactions-table">
                            <thead>
                                <tr>
                                    <th>Date</th>
                                    <th>Description</th>
                                    <th>Type</th>
                                    <th>Amount</th>
                                </tr>
                            </thead>
                            <tbody>
                                ${accountTransactions.map(transaction => `
                                    <tr>
                                        <td>${formatDate(transaction.createdAt)}</td>
                                        <td>${transaction.description}</td>
                                        <td>${capitalizeFirstLetter(transaction.type)}</td>
                                        <td class="${isIncoming(transaction, accountId) ? 'amount-positive' : 'amount-negative'}">
                                            ${isIncoming(transaction, accountId) ? '+' : '-'}${formatCurrency(transaction.amount)}
                                        </td>
                                    </tr>
                                `).join('')}
                            </tbody>
                        </table>
                    </div>
                ` : `
                    <div class="activity-empty">
                        No recent transactions for this account.
                    </div>
                `}
            </div>
        </div>
    `;

    openModal('account-details-modal');
}

function openModal(modalId) {
    const modal = document.getElementById(modalId);
    if (modal) {
        modal.classList.add('active');
        document.body.style.overflow = 'hidden';
    }
}

function closeModal(modalId) {
    const modal = document.getElementById(modalId);
    if (modal) {
        modal.classList.remove('active');
        document.body.style.overflow = '';
    }
}