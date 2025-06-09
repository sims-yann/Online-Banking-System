// Mock Account Data
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
        status: "active"
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
        status: "active"
    }
];

// Filter transactions for only deposits and withdrawals
const transactions = [
    {
        id: "tr2",
        fromAccountId: "external2",
        toAccountId: "acc2",
        amount: 1500.00,
        currency: "XAF",
        type: "deposit",
        method: "direct-deposit",
        status: "completed",
        description: "Salary deposit",
        createdAt: new Date("2023-05-05")
    },
    {
        id: "tr4",
        fromAccountId: "acc1",
        toAccountId: "",
        amount: 200.00,
        currency: "XAF",
        type: "withdraw",
        method: "atm",
        status: "completed",
        description: "ATM withdrawal",
        createdAt: new Date("2023-04-28")
    },
    {
        id: "tr6",
        fromAccountId: "external4",
        toAccountId: "acc1",
        amount: 75.00,
        currency: "XAF",
        type: "deposit",
        method: "check",
        checkNumber: "12345",
        status: "completed",
        description: "Reimbursement from friend",
        createdAt: new Date("2023-04-20")
    },
    {
        id: "tr9",
        fromAccountId: "external7",
        toAccountId: "acc2",
        amount: 1000.00,
        currency: "XAF",
        type: "deposit",
        method: "direct-deposit",
        status: "completed",
        description: "Bonus payment",
        createdAt: new Date("2023-04-05")
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
    return new Intl.DateTimeFormat('en-CM', {
        month: 'short',
        day: 'numeric',
        year: 'numeric',
    }).format(date);
}

function getAccountById(id) {
    return accounts.find(account => account.id === id);
}

function getAccountDisplay(accountId) {
    const account = getAccountById(accountId);
    if (!account) return "External Account";
    return `${account.type.charAt(0).toUpperCase() + account.type.slice(1)} (${account.accountNumber.slice(-4)})`;
}

function capitalizeFirstLetter(string) {
    return string.charAt(0).toUpperCase() + string.slice(1);
}

// Initialize Deposit/Withdraw Page
document.addEventListener('DOMContentLoaded', function() {
    // Set welcome message with user name
    document.querySelector('.welcome-text').textContent = `Welcome, John Doe`;

    // Initialize operation tabs
    initializeOperationTabs();

    // Initialize forms
    initializeDepositForm();
    initializeWithdrawForm();

    // Render recent activity
    renderRecentActivity();

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

    // Setup buttons in the modal
    document.getElementById('new-transaction-button').addEventListener('click', function() {
        closeModal('success-modal');
        resetForms();
    });

    document.getElementById('view-account-button').addEventListener('click', function() {
        window.location.href = "accounts.html";
    });

    // Close modal when clicking outside
    window.addEventListener('click', function(event) {
        if (event.target.classList.contains('modal')) {
            closeModal(event.target.id);
        }
    });
});

function initializeOperationTabs() {
    const operationButtons = document.querySelectorAll('.operation-button');

    operationButtons.forEach(button => {
        button.addEventListener('click', function() {
            // Remove active class from all buttons and forms
            document.querySelectorAll('.operation-button').forEach(btn => {
                btn.classList.remove('active');
            });

            document.querySelectorAll('.operation-form').forEach(form => {
                form.classList.remove('active');
            });

            // Add active class to clicked button and corresponding form
            this.classList.add('active');
            const operation = this.getAttribute('data-operation');
            document.getElementById(`${operation}-form-container`).classList.add('active');
        });
    });
}

function initializeDepositForm() {
    const depositAccountSelect = document.getElementById('deposit-account');
    const depositMethodSelect = document.getElementById('deposit-method');

    // Populate account dropdown
    depositAccountSelect.innerHTML = '<option value="" disabled selected>Select account</option>';
    accounts.forEach(account => {
        const option = document.createElement('option');
        option.value = account.id;
        option.textContent = `${capitalizeFirstLetter(account.type)} (${account.accountNumber.slice(-4)}) - ${formatCurrency(account.balance)}`;
        depositAccountSelect.appendChild(option);
    });

    // Add event listener for account selection
    depositAccountSelect.addEventListener('change', function() {
        const selectedAccount = getAccountById(this.value);
        if (selectedAccount) {
            document.getElementById('deposit-account-balance').textContent = `Current Balance: ${formatCurrency(selectedAccount.balance)}`;
        }
    });

    // Add event listener for method selection
    depositMethodSelect.addEventListener('change', function() {
        const method = this.value;

        // Hide all method-specific fields
        document.getElementById('check-details').classList.add('hidden');
        document.getElementById('wire-details').classList.add('hidden');

        // Show relevant method-specific fields
        if (method === 'check') {
            document.getElementById('check-details').classList.remove('hidden');
        } else if (method === 'wire') {
            document.getElementById('wire-details').classList.remove('hidden');
        }
    });

    // Setup form submission
    document.getElementById('deposit-form').addEventListener('submit', function(e) {
        e.preventDefault();

        const accountId = document.getElementById('deposit-account').value;
        const method = document.getElementById('deposit-method').value;
        const amount = parseFloat(document.getElementById('deposit-amount').value);
        const description = document.getElementById('deposit-description').value || 'Deposit';

        // Create new transaction
        const newTransaction = {
            id: `tr${transactions.length + 1}`,
            fromAccountId: `external-${Date.now()}`,
            toAccountId: accountId,
            amount,
            currency: "XAF",
            type: "deposit",
            method,
            status: "completed",
            description,
            createdAt: new Date()
        };

        // Add check number if check deposit
        if (method === 'check') {
            newTransaction.checkNumber = document.getElementById('check-number').value;
        }

        // Add sending bank if wire transfer
        if (method === 'wire') {
            newTransaction.sendingBank = document.getElementById('sending-bank').value;
        }

        // Update account balance
        const accountIndex = accounts.findIndex(acc => acc.id === accountId);
        accounts[accountIndex].balance += amount;

        // Add to transactions array
        transactions.unshift(newTransaction);

        // Update UI
        renderRecentActivity();

        // Show success message
        showTransactionSuccess(newTransaction, 'deposit');
    });
}

function initializeWithdrawForm() {
    const withdrawAccountSelect = document.getElementById('withdraw-account');
    const withdrawMethodSelect = document.getElementById('withdraw-method');

    // Populate account dropdown
    withdrawAccountSelect.innerHTML = '<option value="" disabled selected>Select account</option>';
    accounts.forEach(account => {
        const option = document.createElement('option');
        option.value = account.id;
        option.textContent = `${capitalizeFirstLetter(account.type)} (${account.accountNumber.slice(-4)}) - ${formatCurrency(account.balance)}`;
        withdrawAccountSelect.appendChild(option);
    });

    // Add event listener for account selection
    withdrawAccountSelect.addEventListener('change', function() {
        const selectedAccount = getAccountById(this.value);
        if (selectedAccount) {
            document.getElementById('withdraw-account-balance').textContent = `Available Balance: ${formatCurrency(selectedAccount.balance)}`;
        }
    });

    // Add event listener for method selection
    withdrawMethodSelect.addEventListener('change', function() {
        const method = this.value;

        // Hide all method-specific fields
        document.getElementById('cashier-check-details').classList.add('hidden');
        document.getElementById('withdrawal-wire-details').classList.add('hidden');

        // Show relevant method-specific fields
        if (method === 'check') {
            document.getElementById('cashier-check-details').classList.remove('hidden');
        } else if (method === 'wire') {
            document.getElementById('withdrawal-wire-details').classList.remove('hidden');
        }
    });

    // Setup form submission
    document.getElementById('withdraw-form').addEventListener('submit', function(e) {
        e.preventDefault();

        const accountId = document.getElementById('withdraw-account').value;
        const method = document.getElementById('withdraw-method').value;
        const amount = parseFloat(document.getElementById('withdraw-amount').value);
        const description = document.getElementById('withdraw-description').value || 'Withdrawal';

        const selectedAccount = getAccountById(accountId);
        if (selectedAccount.balance < amount) {
            alert("Insufficient funds in the selected account.");
            return;
        }

        // Create new transaction
        const newTransaction = {
            id: `tr${transactions.length + 1}`,
            fromAccountId: accountId,
            toAccountId: "",
            amount,
            currency: "XAF",
            type: "withdraw",
            method,
            status: "completed",
            description,
            createdAt: new Date()
        };

        // Add payee name if cashier's check
        if (method === 'check') {
            newTransaction.payeeName = document.getElementById('payee-name').value;
        }

        // Add wire transfer details
        if (method === 'wire') {
            newTransaction.recipientBank = document.getElementById('recipient-bank').value;
            newTransaction.recipientAccount = document.getElementById('recipient-account').value;
            newTransaction.recipientName = document.getElementById('recipient-name').value;
        }

        // Update account balance
        const accountIndex = accounts.findIndex(acc => acc.id === accountId);
        accounts[accountIndex].balance -= amount;

        // Add to transactions array
        transactions.unshift(newTransaction);

        // Update UI
        renderRecentActivity();

        // Show success message
        showTransactionSuccess(newTransaction, 'withdraw');
    });
}

function renderRecentActivity() {
    const tableBody = document.getElementById('recent-activity-table');
    tableBody.innerHTML = '';

    // Sort transactions by date (newest first)
    const sortedTransactions = [...transactions]
        .filter(t => t.type === 'deposit' || t.type === 'withdraw')
        .sort((a, b) => b.createdAt.getTime() - a.createdAt.getTime());

    sortedTransactions.slice(0, 5).forEach(transaction => {
        const row = document.createElement('tr');

        // Date
        const dateCell = document.createElement('td');
        dateCell.textContent = formatDate(transaction.createdAt);
        row.appendChild(dateCell);

        // Description
        const descCell = document.createElement('td');
        descCell.textContent = transaction.description;
        row.appendChild(descCell);

        // Account
        const accountCell = document.createElement('td');
        const accountId = transaction.type === 'deposit' ? transaction.toAccountId : transaction.fromAccountId;
        accountCell.textContent = getAccountDisplay(accountId);
        row.appendChild(accountCell);

        // Type
        const typeCell = document.createElement('td');
        const typeBadge = document.createElement('span');
        typeBadge.className = 'badge';

        if (transaction.type === 'deposit') {
            typeBadge.className += ' badge-secondary';
        } else {
            typeBadge.className += ' badge-destructive';
        }

        typeBadge.textContent = capitalizeFirstLetter(transaction.type);
        typeCell.appendChild(typeBadge);
        row.appendChild(typeCell);

        // Amount
        const amountCell = document.createElement('td');
        amountCell.className = transaction.type === 'deposit' ? 'amount-positive' : 'amount-negative';
        amountCell.textContent = (transaction.type === 'deposit' ? '+' : '-') + formatCurrency(transaction.amount);
        row.appendChild(amountCell);

        // Status
        const statusCell = document.createElement('td');
        const statusBadge = document.createElement('span');
        statusBadge.className = 'badge badge-default';
        statusBadge.textContent = capitalizeFirstLetter(transaction.status);
        statusCell.appendChild(statusBadge);
        row.appendChild(statusCell);

        tableBody.appendChild(row);
    });
}

function showTransactionSuccess(transaction, type) {
    const successTitle = document.getElementById('success-title');
    const successMessage = document.getElementById('success-message');
    const transactionDetails = document.getElementById('transaction-details');

    successTitle.textContent = type === 'deposit' ? 'Deposit Successful' : 'Withdrawal Successful';

    if (type === 'deposit') {
        successMessage.textContent = `Your deposit of ${formatCurrency(transaction.amount)} was processed successfully.`;
    } else {
        successMessage.textContent = `Your withdrawal of ${formatCurrency(transaction.amount)} was processed successfully.`;
    }

    const accountId = type === 'deposit' ? transaction.toAccountId : transaction.fromAccountId;
    const account = getAccountById(accountId);

    let detailsHTML = `
      <p>
        <span class="label">Account:</span>
        <span class="value">${getAccountDisplay(accountId)}</span>
      </p>
      <p>
        <span class="label">Amount:</span>
        <span class="value">${formatCurrency(transaction.amount)}</span>
      </p>
      <p>
        <span class="label">Date:</span>
        <span class="value">${formatDate(transaction.createdAt)}</span>
      </p>
      <p>
        <span class="label">New Balance:</span>
        <span class="value">${formatCurrency(account.balance)}</span>
      </p>
    `;

    if (transaction.method) {
        detailsHTML += `
        <p>
          <span class="label">Method:</span>
          <span class="value">${capitalizeFirstLetter(transaction.method.replace('-', ' '))}</span>
        </p>
      `;
    }

    if (transaction.description) {
        detailsHTML += `
        <p>
          <span class="label">Description:</span>
          <span class="value">${transaction.description}</span>
        </p>
      `;
    }

    if (transaction.checkNumber) {
        detailsHTML += `
        <p>
          <span class="label">Check Number:</span>
          <span class="value">${transaction.checkNumber}</span>
        </p>
      `;
    }

    if (transaction.sendingBank) {
        detailsHTML += `
        <p>
          <span class="label">Sending Bank:</span>
          <span class="value">${transaction.sendingBank}</span>
        </p>
      `;
    }

    if (transaction.payeeName) {
        detailsHTML += `
        <p>
          <span class="label">Payee:</span>
          <span class="value">${transaction.payeeName}</span>
        </p>
      `;
    }

    transactionDetails.innerHTML = detailsHTML;

    // Show modal
    openModal('success-modal');
}

function resetForms() {
    document.getElementById('deposit-form').reset();
    document.getElementById('withdraw-form').reset();
    document.getElementById('deposit-account-balance').textContent = '';
    document.getElementById('withdraw-account-balance').textContent = '';
    document.getElementById('check-details').classList.add('hidden');
    document.getElementById('wire-details').classList.add('hidden');
    document.getElementById('cashier-check-details').classList.add('hidden');
    document.getElementById('withdrawal-wire-details').classList.add('hidden');
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