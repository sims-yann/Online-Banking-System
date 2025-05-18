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

// Mock Transaction Data (transfers only)
const transfers = [
    {
        id: "tr1",
        fromAccountId: "acc1",
        toAccountId: "external1",
        recipientName: "Amazon",
        amount: 120.50,
        currency: "USD",
        type: "transfer",
        status: "completed",
        description: "Payment to Amazon",
        createdAt: new Date("2023-05-10")
    },
    {
        id: "tr5",
        fromAccountId: "acc1",
        toAccountId: "acc2",
        recipientName: "My Savings",
        amount: 500.00,
        currency: "USD",
        type: "transfer",
        status: "completed",
        description: "Transfer to savings",
        createdAt: new Date("2023-04-25")
    },
    {
        id: "tr6",
        fromAccountId: "acc1",
        toAccountId: "external2",
        recipientName: "John Smith",
        amount: 50.00,
        currency: "USD",
        type: "transfer",
        status: "completed",
        description: "Payment to friend",
        createdAt: new Date("2023-04-20")
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

function getAccountDisplay(account) {
    return `${account.type.charAt(0).toUpperCase() + account.type.slice(1)} (${account.accountNumber.slice(-4)})`;
}

// Initialize Transfer Page
document.addEventListener('DOMContentLoaded', function() {
    // Set welcome message with user name
    document.querySelector('.welcome-text').textContent = `Welcome, John Doe`;

    // Initialize form and tabs
    initializeTransferForms();
    initializeTabs();

    // Render recent transfers
    renderRecentTransfers();

    // Setup logout button
    document.getElementById('logout-button').addEventListener('click', function() {
        console.log('Logout clicked');
        alert('Logging out...');
    });

    // Setup modal close buttons
    document.querySelectorAll('.close-modal').forEach(button => {
        button.addEventListener('click', function() {
            closeModal('success-modal');
        });
    });

    // Setup new transfer button in modal
    document.getElementById('new-transfer-button').addEventListener('click', function() {
        closeModal('success-modal');
        resetTransferForms();
    });

    // Close modal when clicking outside
    window.addEventListener('click', function(event) {
        if (event.target.classList.contains('modal')) {
            closeModal(event.target.id);
        }
    });
});

function initializeTabs() {
    const tabButtons = document.querySelectorAll('.transfer-tab-button');

    tabButtons.forEach(button => {
        button.addEventListener('click', function() {
            // Remove active class from all buttons and forms
            document.querySelectorAll('.transfer-tab-button').forEach(btn => {
                btn.classList.remove('active');
            });

            document.querySelectorAll('.transfer-form-container').forEach(form => {
                form.classList.remove('active');
            });

            // Add active class to clicked button and corresponding form
            this.classList.add('active');
            const tabId = this.getAttribute('data-tab');
            document.getElementById(`${tabId}-transfer`).classList.add('active');
        });
    });
}

function initializeTransferForms() {
    // Populate account dropdowns for internal transfer
    const fromAccountSelect = document.getElementById('from-account');
    const toAccountSelect = document.getElementById('to-account');
    const externalFromAccountSelect = document.getElementById('external-from-account');

    // Clear existing options
    fromAccountSelect.innerHTML = '<option value="" disabled selected>Select account</option>';
    toAccountSelect.innerHTML = '<option value="" disabled selected>Select account</option>';
    externalFromAccountSelect.innerHTML = '<option value="" disabled selected>Select account</option>';

    // Add account options
    accounts.forEach(account => {
        const option = document.createElement('option');
        option.value = account.id;
        option.textContent = `${account.type.charAt(0).toUpperCase() + account.type.slice(1)} (${account.accountNumber.slice(-4)}) - ${formatCurrency(account.balance)}`;

        const optionClone1 = option.cloneNode(true);
        const optionClone2 = option.cloneNode(true);

        fromAccountSelect.appendChild(option);
        toAccountSelect.appendChild(optionClone1);
        externalFromAccountSelect.appendChild(optionClone2);
    });

    // Add event listeners for account selection
    fromAccountSelect.addEventListener('change', function() {
        const selectedAccount = getAccountById(this.value);
        if (selectedAccount) {
            document.getElementById('from-account-balance').textContent = `Available Balance: ${formatCurrency(selectedAccount.balance)}`;
        }
    });

    toAccountSelect.addEventListener('change', function() {
        const selectedAccount = getAccountById(this.value);
        if (selectedAccount) {
            document.getElementById('to-account-balance').textContent = `Available Balance: ${formatCurrency(selectedAccount.balance)}`;
        }
    });

    externalFromAccountSelect.addEventListener('change', function() {
        const selectedAccount = getAccountById(this.value);
        if (selectedAccount) {
            document.getElementById('external-from-account-balance').textContent = `Available Balance: ${formatCurrency(selectedAccount.balance)}`;
        }
    });

    // Setup form submissions
    document.getElementById('internal-transfer-form').addEventListener('submit', function(e) {
        e.preventDefault();

        const fromAccountId = document.getElementById('from-account').value;
        const toAccountId = document.getElementById('to-account').value;
        const amount = parseFloat(document.getElementById('amount').value);
        const description = document.getElementById('description').value;

        if (fromAccountId === toAccountId) {
            alert("Please select different accounts for from and to.");
            return;
        }

        const fromAccount = getAccountById(fromAccountId);
        if (fromAccount.balance < amount) {
            alert("Insufficient funds in the selected account.");
            return;
        }

        // Create new transfer
        const newTransfer = {
            id: `tr${transfers.length + 1}`,
            fromAccountId,
            toAccountId,
            recipientName: getAccountById(toAccountId).type,
            amount,
            currency: "USD",
            type: "transfer",
            status: "completed",
            description: description || "Transfer between accounts",
            createdAt: new Date()
        };

        // Update account balances
        const fromAccountIndex = accounts.findIndex(acc => acc.id === fromAccountId);
        const toAccountIndex = accounts.findIndex(acc => acc.id === toAccountId);

        accounts[fromAccountIndex].balance -= amount;
        accounts[toAccountIndex].balance += amount;

        // Add to transfers array
        transfers.unshift(newTransfer);

        // Show success message
        showTransferSuccess(newTransfer, getAccountById(fromAccountId), getAccountById(toAccountId));
    });

    document.getElementById('external-transfer-form').addEventListener('submit', function(e) {
        e.preventDefault();

        const fromAccountId = document.getElementById('external-from-account').value;
        const recipientName = document.getElementById('recipient-name').value;
        const recipientAccount = document.getElementById('recipient-account').value;
        const recipientBank = document.getElementById('recipient-bank').value;
        const amount = parseFloat(document.getElementById('external-amount').value);
        const description = document.getElementById('external-description').value;

        const fromAccount = getAccountById(fromAccountId);
        if (fromAccount.balance < amount) {
            alert("Insufficient funds in the selected account.");
            return;
        }

        // Create new transfer
        const newTransfer = {
            id: `tr${transfers.length + 1}`,
            fromAccountId,
            toAccountId: `external-${Date.now()}`,
            recipientName,
            recipientAccount,
            recipientBank,
            amount,
            currency: "USD",
            type: "transfer",
            status: "completed",
            description: description || `Payment to ${recipientName}`,
            createdAt: new Date()
        };

        // Update account balance
        const fromAccountIndex = accounts.findIndex(acc => acc.id === fromAccountId);
        accounts[fromAccountIndex].balance -= amount;

        // Add to transfers array
        transfers.unshift(newTransfer);

        // Show success message
        showTransferSuccess(newTransfer, getAccountById(fromAccountId));
    });
}

function renderRecentTransfers() {
    const tableBody = document.getElementById('recent-transfers-table');
    tableBody.innerHTML = '';

    // Sort transfers by date (newest first)
    const sortedTransfers = [...transfers].sort((a, b) =>
        b.createdAt.getTime() - a.createdAt.getTime()
    );

    sortedTransfers.slice(0, 5).forEach(transfer => {
        const row = document.createElement('tr');

        // Date
        const dateCell = document.createElement('td');
        dateCell.textContent = formatDate(transfer.createdAt);
        row.appendChild(dateCell);

        // From Account
        const fromCell = document.createElement('td');
        const fromAccount = getAccountById(transfer.fromAccountId);
        fromCell.textContent = fromAccount ? getAccountDisplay(fromAccount) : "External Account";
        row.appendChild(fromCell);

        // To Account
        const toCell = document.createElement('td');
        const toAccount = getAccountById(transfer.toAccountId);
        if (toAccount) {
            toCell.textContent = getAccountDisplay(toAccount);
        } else {
            toCell.textContent = transfer.recipientName || "External Account";
        }
        row.appendChild(toCell);

        // Amount
        const amountCell = document.createElement('td');
        amountCell.textContent = formatCurrency(transfer.amount);
        row.appendChild(amountCell);

        // Status
        const statusCell = document.createElement('td');
        const statusBadge = document.createElement('span');
        statusBadge.className = 'badge badge-default';
        statusBadge.textContent = transfer.status;
        statusCell.appendChild(statusBadge);
        row.appendChild(statusCell);

        tableBody.appendChild(row);
    });
}

function showTransferSuccess(transfer, fromAccount, toAccount) {
    const transferDetails = document.getElementById('transfer-details');

    let detailsHTML = `
      <p>
        <span class="label">From:</span>
        <span class="value">${getAccountDisplay(fromAccount)}</span>
      </p>
      <p>
        <span class="label">To:</span>
        <span class="value">${toAccount ? getAccountDisplay(toAccount) : transfer.recipientName}</span>
      </p>
      <p>
        <span class="label">Amount:</span>
        <span class="value">${formatCurrency(transfer.amount)}</span>
      </p>
      <p>
        <span class="label">Date:</span>
        <span class="value">${formatDate(transfer.createdAt)}</span>
      </p>
    `;

    if (transfer.description) {
        detailsHTML += `
        <p>
          <span class="label">Description:</span>
          <span class="value">${transfer.description}</span>
        </p>
      `;
    }

    transferDetails.innerHTML = detailsHTML;

    // Update UI elements
    renderRecentTransfers();

    // Show success modal
    openModal('success-modal');
}

function resetTransferForms() {
    document.getElementById('internal-transfer-form').reset();
    document.getElementById('external-transfer-form').reset();
    document.getElementById('from-account-balance').textContent = '';
    document.getElementById('to-account-balance').textContent = '';
    document.getElementById('external-from-account-balance').textContent = '';
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
