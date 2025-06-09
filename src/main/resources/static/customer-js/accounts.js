// Mock Account Data (same as in dashboard.js)
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
        currency: "USD",
        interestRate: "0.5%",
        openedDate: new Date("2023-01-15"),
        status: "active",
        branch: "Main Branch",
        lastActivity: new Date("2023-04-25")
    }
];

// Mock Transaction Data (same as in dashboard.js)
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
        status: "completed",
        description: "ATM withdrawal",
        createdAt: new Date("2023-04-28")
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
        createdAt: new Date("2023-04-25")
    }
];


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

// Initialize Accounts Page
document.addEventListener('DOMContentLoaded', function() {
    // Set welcome message with user name
    document.querySelector('.welcome-text').textContent = `Welcome, John Doe`;

    // Render accounts
    renderAccounts();

    // Setup logout button
    document.getElementById('logout-button').addEventListener('click', function() {
        console.log('Logout clicked');
        alert('Logging out...');
    });

    // Setup new account button
    document.getElementById('new-account-button').addEventListener('click', function() {
        alert('This feature is coming soon!');
    });

    // Setup modal close button
    document.querySelectorAll('.close-modal').forEach(button => {
        button.addEventListener('click', function() {
            closeModal('account-details-modal');
        });
    });

    // Close modal when clicking outside
    window.addEventListener('click', function(event) {
        if (event.target.classList.contains('modal')) {
            closeModal(event.target.id);
        }
    });
});

function renderAccounts() {
    const accountsContainer = document.querySelector('.accounts-grid.detailed');
    accountsContainer.innerHTML = '';

    // Calculate total balance across all accounts
    const totalBalance = accounts.reduce((sum, account) => sum + account.balance, 0);
    document.querySelector('.total-balance').textContent = formatCurrency(totalBalance);

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

    // Add event listeners to view details buttons
    document.querySelectorAll('.view-details').forEach(button => {
        button.addEventListener('click', function() {
            const accountId = this.getAttribute('data-account-id');
            openAccountDetailsModal(accountId);
        });
    });

    // Add event listeners to transfer buttons
    document.querySelectorAll('.transfer-button').forEach(button => {
        button.addEventListener('click', function() {
            window.location.href = "/transfer";
        });
    });

    // Add event listeners to statement buttons
    document.querySelectorAll('.statement-button').forEach(button => {
        button.addEventListener('click', function() {
            const accountId = this.getAttribute('data-account-id');
            alert(`Statement for account ${maskAccountNumber(getAccountById(accountId).accountNumber)} will be generated soon!`);
        });
    });
}

function maskAccountNumber(accountNumber) {
    return "****" + accountNumber.slice(-4);
}

function openAccountDetailsModal(accountId) {
    const account = getAccountById(accountId);
    const accountTransactions = getAccountTransactions(accountId);

    const modalContent = document.getElementById('account-details-content');

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

// Utility Functions
function formatCurrency(amount) {
    return new Intl.NumberFormat('en-US', {
        style: 'currency',
        currency: 'USD',
    }).format(amount);
}

function formatDate(date) {
    if (!date) return 'N/A';

    // Handle different date formats
    if (typeof date === 'string') {
        date = new Date(date);
    }

    return new Intl.DateTimeFormat('en-US', {
        month: 'short',
        day: 'numeric',
        year: 'numeric',
    }).format(date);
}

function capitalizeFirstLetter(string) {
    if (!string) return '';
    return string.charAt(0).toUpperCase() + string.slice(1).toLowerCase();
}

function getStatusBadgeClass(status) {
    switch(status?.toLowerCase()) {
        case 'active': return 'status-active';
        case 'inactive': return 'status-inactive';
        case 'suspended': return 'status-suspended';
        case 'closed': return 'status-closed';
        default: return 'status-inactive';
    }
}

// Initialize Accounts Page
document.addEventListener('DOMContentLoaded', function() {
    initializeEventListeners();
});

function initializeEventListeners() {
    // Setup new account button
    const newAccountButton = document.getElementById('new-account-button');
    if (newAccountButton) {
        newAccountButton.addEventListener('click', function() {
            openModal('new-account-modal');
        });
    }

    // Setup modal close buttons
    document.querySelectorAll('.close-modal').forEach(button => {
        button.addEventListener('click', function() {
            const modal = button.closest('.modal');
            if (modal) {
                closeModal(modal.id);
            }
        });
    });

    // Close modal when clicking outside
    window.addEventListener('click', function(event) {
        if (event.target.classList.contains('modal')) {
            closeModal(event.target.id);
        }
    });

    // Setup new account form submission
    const newAccountForm = document.getElementById('new-account-form');
    if (newAccountForm) {
        newAccountForm.addEventListener('submit', handleNewAccountSubmission);
    }

    // Setup view details buttons
    setupViewDetailsButtons();
}

function setupViewDetailsButtons() {
    document.querySelectorAll('.view-details').forEach(button => {
        button.addEventListener('click', function() {
            const accountId = this.getAttribute('data-account-id');
            if (accountId) {
                fetchAccountDetails(accountId);
            }
        });
    });

    // Setup transfer buttons
    document.querySelectorAll('.transfer-button').forEach(button => {
        button.addEventListener('click', function() {
            const accountId = this.getAttribute('data-account-id');
            // Redirect to transfer page with account context
            window.location.href = `/customer/transfer?fromAccount=${accountId}`;
        });
    });
}

// Handle new account form submission
function handleNewAccountSubmission(event) {
    event.preventDefault();

    const formData = new FormData(event.target);
    const accountType = formData.get('accountType');

    if (!accountType) {
        showFormMessage('Please select an account type', 'error');
        return;
    }

    const requestData = {
        accountType: accountType,
        balance: 0.00 // Default balance as per backend logic
    };

    // Show loading state
    const submitButton = event.target.querySelector('button[type="submit"]');
    const originalText = submitButton.textContent;
    submitButton.textContent = 'Creating Account...';
    submitButton.disabled = true;

    // Make API call to create account
    fetch('/customer/accounts/create', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'X-Requested-With': 'XMLHttpRequest'
        },
        body: JSON.stringify(requestData)
    })
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            return response.json();
        })
        .then(data => {
            if (data.success) {
                showFormMessage(data.message, 'success');
                // Reset form
                event.target.reset();
                // Reload page after 2 seconds to show new account
                setTimeout(() => {
                    window.location.reload();
                }, 2000);
            } else {
                showFormMessage(data.message || 'Failed to create account', 'error');
            }
        })
        .catch(error => {
            console.error('Error creating account:', error);
            showFormMessage('An error occurred while creating the account. Please try again.', 'error');
        })
        .finally(() => {
            // Restore button state
            submitButton.textContent = originalText;
            submitButton.disabled = false;
        });
}

// Fetch account details from backend
function fetchAccountDetails(accountId) {
    fetch(`/customer/accounts/${accountId}`, {
        method: 'GET',
        headers: {
            'X-Requested-With': 'XMLHttpRequest'
        }
    })
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            return response.json();
        })
        .then(data => {
            if (data.success) {
                displayAccountDetails(data.account);
            } else {
                alert(data.message || 'Failed to fetch account details');
            }
        })
        .catch(error => {
            console.error('Error fetching account details:', error);
            alert('An error occurred while fetching account details. Please try again.');
        });
}

// Display account details in modal
function displayAccountDetails(accountDetails) {
    const modalContent = document.getElementById('account-details-content');

    if (!modalContent) {
        console.error('Account details modal content not found');
        return;
    }

    // Extract account information from the details object
    const accountType = accountDetails.type || 'Unknown';
    const balance = accountDetails.balance || accountDetails.balace || 0; // Note: backend has typo 'balace'
    const status = accountDetails.status || 'Unknown';
    const createdDate = accountDetails.createdDate || accountDetails.createdAt;

    modalContent.innerHTML = `
        <div class="account-details">
            <div class="account-header">
                <h3>${capitalizeFirstLetter(accountType)} Account</h3>
                <div class="status-badge ${getStatusBadgeClass(status)}">${capitalizeFirstLetter(status)}</div>
            </div>
            
            <div class="account-balance-large">${formatCurrency(balance)}</div>

            <div class="account-info-grid">
                <div class="account-info-item">
                    <div class="account-info-label">Account Status</div>
                    <div class="account-info-value">${capitalizeFirstLetter(status)}</div>
                </div>
                <div class="account-info-item">
                    <div class="account-info-label">Account Type</div>
                    <div class="account-info-value">${capitalizeFirstLetter(accountType)}</div>
                </div>
                <div class="account-info-item">
                    <div class="account-info-label">Current Balance</div>
                    <div class="account-info-value">${formatCurrency(balance)}</div>
                </div>
                <div class="account-info-item">
                    <div class="account-info-label">Opening Date</div>
                    <div class="account-info-value">${formatDate(createdDate)}</div>
                </div>
            </div>

            ${status?.toLowerCase() === 'inactive' ? `
                <div class="account-notice">
                    <div class="notice-icon">⚠️</div>
                    <div class="notice-content">
                        <h4>Account Pending Approval</h4>
                        <p>Your account is currently inactive and pending admin approval. You will be able to use this account once it has been approved.</p>
                    </div>
                </div>
            ` : ''}

            <div class="account-actions-modal">
                ${status?.toLowerCase() === 'active' ? `
                    <button class="action-button transfer-button" onclick="redirectToTransfer()">
                        <span>💸</span>
                        Make Transfer
                    </button>
                ` : ''}
                <button class="action-button view-button" onclick="window.print()">
                    <span>🖨️</span>
                    Print Details
                </button>
            </div>
        </div>
    `;

    openModal('account-details-modal');
}

// Redirect to transfer page
function redirectToTransfer() {
    window.location.href = '/customer/transfer';
}

// Show form messages
function showFormMessage(message, type) {
    const messageElement = document.getElementById('form-message');
    if (messageElement) {
        messageElement.textContent = message;
        messageElement.className = `form-message ${type}`;
        messageElement.style.display = 'block';

        // Hide message after 5 seconds for success messages
        if (type === 'success') {
            setTimeout(() => {
                messageElement.style.display = 'none';
            }, 5000);
        }
    }
}

// Modal functions
function openModal(modalId) {
    const modal = document.getElementById(modalId);
    if (modal) {
        modal.classList.add('active');
        document.body.style.overflow = 'hidden'; // Prevent background scrolling
    }
}

function closeModal(modalId) {
    const modal = document.getElementById(modalId);
    if (modal) {
        modal.classList.remove('active');
        document.body.style.overflow = ''; // Restore scrolling

        // Clear form messages when closing modal
        const formMessage = modal.querySelector('.form-message');
        if (formMessage) {
            formMessage.style.display = 'none';
        }
    }
}

// Auto-setup event listeners when DOM content is loaded
document.addEventListener('DOMContentLoaded', function() {
    // Re-setup view details buttons after page load (in case they're dynamically generated)
    setTimeout(setupViewDetailsButtons, 100);
});