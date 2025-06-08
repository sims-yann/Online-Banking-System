const API_URL = '/api/users';
let users = [];
let editingUserId = null;

const usersTableBody = document.querySelector('#usersTable tbody');
const searchInput = document.getElementById('searchInput');
const addUserBtn = document.getElementById('addUserBtn');
const userModal = new bootstrap.Modal(document.getElementById('userModal'));
const userForm = document.getElementById('userForm');
const userModalLabel = document.getElementById('userModalLabel');

// Sidebar navigation logic
const usersManagementSection = document.getElementById('usersManagementSection');
const transactionsManagementSection = document.getElementById('transactionsManagementSection');
const usersLink = document.getElementById('usersLink');
const transactionsLink = document.getElementById('transactionsLink');

usersLink.addEventListener('click', (e) => {
    e.preventDefault();
    usersLink.classList.add('active');
    transactionsLink.classList.remove('active');
    usersManagementSection.classList.remove('d-none');
    transactionsManagementSection.classList.add('d-none');
});

transactionsLink.addEventListener('click', (e) => {
    e.preventDefault();
    usersLink.classList.remove('active');
    transactionsLink.classList.add('active');
    usersManagementSection.classList.add('d-none');
    transactionsManagementSection.classList.remove('d-none');
    fetchAndRenderTransactions().then(r => {});
});

// Transactions Management Logic
const TRANSACTIONS_API_URL = '/transactions/all';
let allTransactions = [];
const transactionsTableBody = document.querySelector('#transactionsTable tbody');
const transactionSearchInput = document.getElementById('transactionSearchInput');
const transactionDateRange = document.getElementById('transactionDateRange');

// Fetch and render users
async function fetchUsers(query = '') {
    let url = API_URL;
    if (query) url += `?search=${encodeURIComponent(query)}`;
    const res = await fetch(url);
    users = await res.json();
    renderUsers();
}

function renderUsers() {
    usersTableBody.innerHTML = '';
    if (users.length === 0) {
        usersTableBody.innerHTML = '<tr><td colspan="6" class="text-center">No users found.</td></tr>';
        return;
    }
    users.forEach(user => {
        const tr = document.createElement('tr');
        tr.innerHTML = `
            <td><span class="user-avatar"><i class="bi bi-person"></i></span> ${user.name}</td>
            <td>${user.email}</td>
            <td><span class="role-badge">${user.role}</span></td>
            <td><span class="status-badge">${user.status}</span></td>
            <td>${formatDate(user.createdOn)}</td>
            <td>
                <button class="ellipsis-btn" title="Actions" data-id="${user.id}"><i class="bi bi-three-dots-vertical"></i></button>
                <div class="dropdown-menu d-none" id="dropdown-${user.id}">
                    <a class="dropdown-item edit-user" href="#" data-id="${user.id}">Edit</a>
                    <a class="dropdown-item delete-user" href="#" data-id="${user.id}">Delete</a>
                </div>
            </td>
        `;
        usersTableBody.appendChild(tr);
    });
}

function formatDate(dateStr) {
    if (!dateStr) return '';
    const date = new Date(dateStr);
    const options = { year: 'numeric', month: 'short', day: 'numeric' };
    return date.toLocaleDateString(undefined, options);
}

// Search functionality
searchInput.addEventListener('input', (e) => {
    fetchUsers(e.target.value.trim());
});

// Add User button
addUserBtn.addEventListener('click', () => {
    editingUserId = null;
    userModalLabel.textContent = 'Add New User';
    userForm.reset();
    document.getElementById('userId').value = '';
    userModal.show();
});

// Handle table actions (edit/delete)
usersTableBody.addEventListener('click', (e) => {
    if (e.target.closest('.ellipsis-btn')) {
        const id = e.target.closest('.ellipsis-btn').dataset.id;
        showDropdownMenu(id, e.target.closest('.ellipsis-btn'));
    } else if (e.target.classList.contains('edit-user')) {
        e.preventDefault();
        const id = e.target.dataset.id;
        openEditModal(id);
    } else if (e.target.classList.contains('delete-user')) {
        e.preventDefault();
        const id = e.target.dataset.id;
        if (confirm('Are you sure you want to delete this user?')) {
            deleteUser(id).then(r => {});
        }
    }
});

document.addEventListener('click', (e) => {
    // Hide all dropdowns if clicking outside
    document.querySelectorAll('.dropdown-menu').forEach(menu => menu.classList.add('d-none'));
});

function showDropdownMenu(id, btn) {
    document.querySelectorAll('.dropdown-menu').forEach(menu => menu.classList.add('d-none'));
    const menu = document.getElementById(`dropdown-${id}`);
    if (menu) {
        menu.classList.remove('d-none');
        const rect = btn.getBoundingClientRect();
        menu.style.position = 'absolute';
        menu.style.left = rect.left + 'px';
        menu.style.top = (rect.bottom + window.scrollY) + 'px';
        menu.style.zIndex = 2000;
    }
}

// Add/Edit User form submit
userForm.addEventListener('submit', async (e) => {
    e.preventDefault();
    const id = document.getElementById('userId').value;
    const user = {
        name: document.getElementById('userName').value,
        email: document.getElementById('userEmail').value,
        role: document.getElementById('userRole').value,
        status: document.getElementById('userStatus').value
    };
    if (id) {
        // Edit
        await fetch(`${API_URL}/${id}`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(user)
        });
    } else {
        // Add
        await fetch(API_URL, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(user)
        });
    }
    userModal.hide();
    fetchUsers(searchInput.value.trim());
});

async function openEditModal(id) {
    const user = users.find(u => u.id === id);
    if (!user) return;
    editingUserId = id;
    userModalLabel.textContent = 'Edit User';
    document.getElementById('userId').value = user.id;
    document.getElementById('userName').value = user.name;
    document.getElementById('userEmail').value = user.email;
    document.getElementById('userRole').value = user.role;
    document.getElementById('userStatus').value = user.status;
    userModal.show();
}

async function deleteUser(id) {
    await fetch(`${API_URL}/${id}`, { method: 'DELETE' });
    await fetchUsers(searchInput.value.trim());
}

async function fetchAndRenderTransactions() {
    const res = await fetch(TRANSACTIONS_API_URL);
    allTransactions = await res.json();
    renderTransactions();
}

function renderTransactions() {
    let filtered = allTransactions;
    const search = transactionSearchInput.value.trim().toLowerCase();
    if (search) {
        filtered = filtered.filter(t =>
            (t.description && t.description.toLowerCase().includes(search)) ||
            (t.id && t.id.toString().includes(search))
        );
    }
    // Date range filter
    transactionsTableBody.innerHTML = '';
    if (filtered.length === 0) {
        transactionsTableBody.innerHTML = '<tr><td colspan="7" class="text-center">No transactions found.</td></tr>';
        return;
    }
    filtered.forEach(t => {
        transactionsTableBody.innerHTML += `
            <tr>
                <td>#${t.id}</td>
                <td>${renderTypeBadge(t.transactionType)}</td>
                <td>$${Number(t.amount).toLocaleString(undefined, {minimumFractionDigits: 2})}</td>
                <td>${t.description || ''}</td>
                <td>${formatDateTime(t.createdAt)}</td>
                <td>${renderStatusBadge(t.status)}</td>
                <td><button class="ellipsis-btn" title="Actions"><i class="bi bi-three-dots-vertical"></i></button></td>
            </tr>
        `;
    });
}

function renderTypeBadge(type) {
    if (!type) return '';
    if (type.toLowerCase() === 'withdrawal' || type.toLowerCase() === 'withdraw') {
        return '<span class="badge bg-danger text-white">withdraw</span>';
    }
    if (type.toLowerCase() === 'deposit') {
        return '<span class="badge bg-light text-primary">deposit</span>';
    }
    return '<span class="badge bg-light text-dark">transfer</span>';
}

function renderStatusBadge(status) {
    if (!status) return '';
    return `<span class="status-badge">${status.toLowerCase()}</span>`;
}

function formatDateTime(dateStr) {
    if (!dateStr) return '';
    const date = new Date(dateStr);
    const options = { year: 'numeric', month: 'short', day: 'numeric', hour: '2-digit', minute: '2-digit' };
    return date.toLocaleDateString(undefined, options) + ', ' + date.toLocaleTimeString(undefined, { hour: '2-digit', minute: '2-digit' });
}

transactionSearchInput.addEventListener('input', renderTransactions);
// Date range picker logic
flatpickr(transactionDateRange, {
    mode: 'range',
    dateFormat: 'Y-m-d',
    onClose: function(selectedDates) {
        if (selectedDates.length === 2) {
            fetchTransactionsByDateRange(selectedDates[0], selectedDates[1]);
        } else {
            fetchAndRenderTransactions();
        }
    }
});

async function fetchTransactionsByDateRange(startDate, endDate) {
    // Convert to ISO string and set time to cover the full days
    const start = new Date(startDate);
    start.setHours(0,0,0,0);
    const end = new Date(endDate);
    end.setHours(23,59,59,999);
    const res = await fetch(`/transactions/range?start=${start.toISOString()}&end=${end.toISOString()}`);
    allTransactions = await res.json();
    renderTransactions();
}

// Initial load
fetchUsers(); 