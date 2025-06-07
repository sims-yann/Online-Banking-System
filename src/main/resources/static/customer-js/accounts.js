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

function openModal(modalId) {
    const modal = document.getElementById(modalId);
    modal.classList.add('active');
    document.body.style.overflow = 'hidden'; // Prevent background scrolling
}

function closeModal(modalId) {
    const modal = document.getElementById(modalId);
    modal.classList.remove('active');
    document.body.style.overflow = ''; // Restore scrolling
}